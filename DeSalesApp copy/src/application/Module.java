/**
 * The Module class is an abstract superclass for all other
 * modules in this application. Each module has a name, an
 * optional icon, and has a method to retrieve its respective
 * scene. getScene() must be overridden in each subclass.
 * 
 * @author Steven Counterman
 */

package application;

import javafx.scene.image.Image;
import javafx.scene.Node;

public abstract class Module 
{
	protected String name;
	protected Image icon;
	
	/**
	 *Constructor used to instantiate a module
	 *
	 * @param n The display name for the module
	 * @param i The icon for the module (for future development)
	 */
	public Module(String n, Image i)
	{
		this.name = n;
		this.icon = i;
	}
	
	/**
	 * Returns the name of the module
	 * 
	 * @return The String name of the module
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the icon of the module
	 * 
	 * @return the Image type icon of the module
	 */
	public Image getIcon()
	{
		return icon;
	}
	
	/**
	 * Returns the JavaFX {@code Scene} associated with the module
	 * <p>
	 * Each subclass must implement this method to provide its own unique scene.
	 * 
	 * @return the {@code Scene} for this module
	 */
	public abstract Node getScene();
}