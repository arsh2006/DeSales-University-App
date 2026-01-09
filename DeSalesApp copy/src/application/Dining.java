/**
 * The Dining class displays the meals for the current
 * day in the university's food court. Meals can be
 * selected from breakfast, brunch, lunch, or dinner.
 * Retrieves meal data from WebScraper class.
 * Subclass of the Module superclass.
 * 
 * @author Steven Counterman
*/

package application;

//Import statements
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import javafx.scene.control.Separator;
import javafx.collections.FXCollections;


public class Dining extends application.Module
{
	/**
	 * Constructor for dining module
	 * 
	 * @param n The display name for the module
	 * @param i The icon for the module (for future development)
	 */
	public Dining(String n, Image i)
	{
		super(n, i);
	}
	
	/**
	 * Builds and returns the GUI Layout for the Dining module
	 * 
	 * @return a Node representing the scene layout, wrapped in a scrollpane
	 */
	@Override
	public Node getScene()
	{
		//Vertical layout w/ 10px spacing
		//Apply custom css style class for background
		VBox layout = new VBox(10);
		layout.getStyleClass().add("dining-root");
		
		//Label to prompt meal time selection
		Label header = new Label("Select a Meal Time");
		header.getStyleClass().add("dining-header");
		
		//Create a combo box with meal time options
		ComboBox<String> mealSelector = new ComboBox<>(FXCollections.observableArrayList(
				"BREAKFAST", "BRUNCH", "LUNCH", "DINNER"
		));
		
		//Vertical layout to show meals
		VBox mealDisplay = new VBox(10);
		mealDisplay.getStyleClass().add("meal-display");
		
		//Assign event listener to evaluate meal time provided by combo box
		mealSelector.setOnAction(e -> {
			
			//Get selected meal time & all meals from webscraper
			String selectedTime = mealSelector.getValue();
			List<Meals> allMeals = WebScraper.retrieveAllMeals();
			
			//Call webscraper to sort the meals for the specific time
			List<Meals> selectedMeals = WebScraper.getMealsByTime(allMeals, selectedTime);
			
			//Clear display so only current selection is shown
			mealDisplay.getChildren().clear();
			
			//Check if list of meals is empty
			//Breaks out if lambda expression if true
			if (selectedMeals.isEmpty())
			{
				Label mealsEmpty = new Label("No meals available for " + selectedTime);
				mealsEmpty.getStyleClass().add("meal-text");
				mealDisplay.getChildren().add(mealsEmpty);
				return;
			}
			
			//Header above meal options displaying the selected time
			Label mealTimeLabel = new Label(selectedTime);
			mealTimeLabel.getStyleClass().add("meal-time");
			mealDisplay.getChildren().add(mealTimeLabel);
			
			//Create key value pairs of station to meal options for the station
			//.stream used to optimize processing by chaining .filter and .collect operations
			Map<String, List<Meals>> byStation = selectedMeals.stream()
					
					//Keep items that have a declared station (avoids null and blank stations)
					.filter(m -> m.getSection() != null && !m.getSection().isBlank())
					//Groups acceptable meal options and maps them to stations
					.collect(Collectors.groupingBy(m -> m.getSection().trim()));
			
			//Get keys from Map<> and loop over each station
			for (String station : byStation.keySet())
			{
				//Create a label for the station name
				Label stationLabel = new Label(station);
				stationLabel.getStyleClass().add("meal-category");
				
				//Add station label to display
				mealDisplay.getChildren().add(stationLabel);
				
				//Iterate through all meals in the station
				for (Meals m : byStation.get(station))
				{			
					//Create a label for the meal option and add it to the display
					Label item = new Label("- " + m.getName() + " (" + m.getCals() + " cals)");
					item.getStyleClass().add("meal-text");
					mealDisplay.getChildren().add(item);
				}
				
				//Add a separator after the completion of a station
				mealDisplay.getChildren().add(new Separator());
				
			}
		});
		
		//Add the header, combo box, and meal display to the layout
		layout.getChildren().addAll(header, mealSelector, mealDisplay);
		
		//Put layout in a scrollpane to allow for scrolling through content
		//Stretches vbox to scrollpane width
		ScrollPane scrollPane = new ScrollPane(layout);
		scrollPane.setFitToWidth(true);
		scrollPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		return scrollPane;
	}
}