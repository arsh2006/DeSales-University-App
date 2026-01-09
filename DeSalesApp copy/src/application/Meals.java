/**
 * The Meals class represents an individual meal item available
 * via DeSales University's dining website. Each meal includes a name,
 * meal time (breakfast, lunch, etc.), calorie count, and dining station.
 * 
 * Used by the Dining module for retrieving, organizing, and displaying
 * Instantiated by the WebScraper class during parsing
 * 
 * @author Steven Counterman
 */

package application;

public class Meals
{
	//Name of meal, meal time, calorie content, and dining station
	private String name;
	private String mealTime;
	private int calories;
	private String section;
	
	/**
	 * Constructor
	 * 
	 * @param n   The name of the meal item
	 * @param mT  The meal time the item is served (breakfast/lunch/etc.)
	 * @param c   The calorie count for the meal option
	 * @param sec The dining station (Le Bistro/Grill/etc.)
	 */
	public Meals(String n, String mT, int c, String sec)
	{
		this.name = n;
		this.mealTime = mT;
		this.calories = c;
		this.section = sec;
	}
	
	/**
	 * Retrieve the name of the meal
	 * 
	 * @return the meal name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retrieve the meal time
	 * 
	 * @return the meal time (Breakfast, Dinner, etc.)
	 */
	public String getMealTime()
	{
		return mealTime;
	}
	
	/**
	 * Retrieve the number of calories
	 * 
	 * @return number of calories
	 */
	public int getCals()
	{
		return calories;
	}
	
	/**
	 * Retrieve the associated dining station
	 * 
	 * @return the section name (Le Bistro, Grill, etc.)
	 */
	public String getSection()
	{
		return section;
	}
}