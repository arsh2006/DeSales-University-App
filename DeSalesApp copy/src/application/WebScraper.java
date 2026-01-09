/**
 * The WebScraper class handles retrieving and parsing of
 * live meal data from DeSales University's dining services API
 * hosted on their sodexo website. It performs HTTP requests,
 * parses JSON responses using GSON, and instantiates the meal objects.
 * 
 * Contains methods to retrieve all meals for the current day and 
 * filter them by meal time.
 * 
 * Used by Dining module to supply meal information for display.
 * 
 * @author Steven Counterman
 */

package application;

//Import statements
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.net.URI;
import java.io.BufferedReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.annotations.SerializedName;

public class WebScraper
{
	/**
	 * Connects to the DeSales dining API and retrieves the meal data
	 * for the current date. Calls parseMeals method to parse JSON
	 * response and returns a list of all available meal options.
	 * 
	 * @return a List of Meals representing today's meal options
	 */
	public static List<Meals> retrieveAllMeals()
	{
		try
		{
			//Retrieves local date from default system clock
			//Formats date into yyyy-MM-dd
			LocalDate today = LocalDate.now();
			String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			//Initialize the URI to a string, create the URI
			//Convert URI to URL as JSON only uses URLs
			String uriString = "https://api-prd.sodexomyway.net/v0.2/data/menu/97450001/15680?date=" + formattedDate;
			URI uri = URI.create(uriString);
			URL url = uri.toURL();
			
			//Create the HTTP connection and set header fields
			//Note: the API will block connection without ALL of these headers
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Origin", "https://desales.sodexomyway.com");
			conn.setRequestProperty("Referer", "https://desales.sodexomyway.com/");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36");
			conn.setRequestProperty("api-key", "68717828-b754-420d-9488-4c37cb7d7ef7");
			conn.setRequestProperty("Authorization", "Bearer");
			conn.setRequestProperty("Content-Type", "application/json");
			
			//Create buffered reader to read data from the connection data stream
			//StringBuilder used to create a mutable sequence of characters
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder json = new StringBuilder();
			String line;
			
			//Iterate over input while line has data
			while ((line = reader.readLine()) != null)
			{
				//Add the current line of data to mutable StringBuilder object
				json.append(line);
			}
			
			//Close BufferedReader
			reader.close();
			
			//Pass JSON string to parseMeals() and
			//return the formatted List of meals
			return parseMeals(json.toString());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//Need to return declared return type to avoid compiler error
			return new ArrayList<>();
		}
	}
	
	/**
	 * Filters a List of meals so that only those matching
	 * the meal time are returned
	 * 
	 * @param allMeals the complete List of Meals to filter.
	 * @param mealTime String of meal time to filter by ("BREAKFAST", "LUNCH", etc.).
	 * @return a List of Meals matching the meal time
	 */
	public static List<Meals> getMealsByTime(List<Meals> allMeals, String mealTime)
	{
		//Turn list of all meals to stream of data (more efficient than looping)
		return allMeals.stream()
				
				//Filter on lambda expression using each meal's meal time
				.filter(m -> m.getMealTime().equalsIgnoreCase(mealTime))
				//Gather the filtered acceptable meals into a List of Meals objects
				.collect(Collectors.toList());
	}
	
	/**
	 * Parses a raw JSON string into a structured List of Meals objects
	 * 
	 * @param json the raw JSON string received from the dining API.
	 * @return List of Meals parsed from JSON data
	 */
	public static List<Meals> parseMeals(String json)
	{
		//Initialize Gson object that converts JSON objects to POJOs automatically
		Gson gson = new Gson();
		
		//Provide gson the json string and the Java type to map the data into
		//Creates an array of MealLevel[] array objects each containing their respective meals
		MealLevel[] mealLevels = gson.fromJson(json, MealLevel[].class);
		
		//Initialize a List of Meals objects as an ArrayList
		List<Meals> meals = new ArrayList<>();
		
		//Iterate over MealLevel array for each meal time inside
		for (MealLevel meal : mealLevels)
		{
			// Skip iteration if no stations for the meal time
			if (meal.stations == null)
				continue;
			
			//Initialize meal time string to uppercase version if there is an entry
			//Else, it will display unknown
			String mealTime = meal.name != null ? meal.name.toUpperCase() : "UNKNOWN";
			
			//Iterate over StationLevel array for each station
			for (StationLevel station : meal.stations)
			{
				//Skip station if no meal option inside
				if (station.items == null)
					continue;
				
				//Iterate over meal options for each station
				for (MenuItem item : station.items) 
				{
					//Instantiate values used to create and add Meals object to meals List
					String name = item.formalName != null ? item.formalName : "Unnamed";
					int cals = 0;
					
					//Try catch to handle exception if calories string
					//cannot be converted to Int
					try
					{
						cals = Integer.parseInt(item.calories);
					}
					//Ignores exception and keeps default cals as 0 from above
					catch (Exception ignored) {}
					
					//Instantiate Meals object and add to meals ArrayList
					meals.add(new Meals(name, mealTime, cals, station.name));
				}
			}
		}
		return meals;
	}
	
	//These static classes are used as GSON maps the JSON fields
	//directly into Java objects during deserialization. The fields
	//names match the JSON fields. Changed manually if necessary.
	//
	//The JSON data is multi-dimensional and these classes represent
	//each layer of the structure, allowing parsed data to be stored
	//at the appropriate layer
	
	/**
	 * MealLevel represents the outermost level of the multidimensional
	 * container from the dining API. It contains the meal time mapped 
	 * to the multiple stations in that meal time.
	 */
	private static class MealLevel
	{
		String name;
		
		//Map JSON 'groups' field to 'stations'
		//for self-documenting code
		@SerializedName("groups")
		StationLevel[] stations;
	}
	
	/**
	 * StationLevel represents the next highest level of the container.
	 * It contains the stations mapped to an array of MenuItem objects
	 * for that station.
	 */
	private static class StationLevel
	{
		String name;
		MenuItem[] items;
	}
	
	/**
	 * This represents the innermost data, which is an individual menu
	 * item within a station. It contains the item's name, description,
	 * and calorie count.
	 */
	private static class MenuItem
	{
		String formalName;
		String calories;
	}
}
