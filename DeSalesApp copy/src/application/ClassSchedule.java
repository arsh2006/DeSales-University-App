/**
 * ClassSchedule is a module that reads a student's class schedule 
 * from a text file ("schedule.txt") and displays it using JavaFX.
 *
 * <p>The schedule is expected to be in the format:
 * <pre>
 * CLASS CODE,,CLASS NAME,,ROOM,,TIME,,DAYS
 * Example:
 * Math 101,,Intro to Math,,Room 200,,1:00PM - 1:50PM,,Mon/Wed/Fri
 * </pre>
 *
 * <p>Each entry is styled using TextFlow to bold important properties such as 
 * class code, location, and day while keeping others normal.
 * 
 * <p>We unfortunately made a mistake with naming conventions and named the 
 * class meant to be "Schedule" as "Class". So think of Class as Schedule.
 *
 * @author Arshmaan Chahal
 * @since 2025-04-2
 * @updated 2025-04-26 
 */

package application;

import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the ClassSchedule module of the application.
 * Displays a formatted list of classes retrieved from a text file.
 */
public class ClassSchedule extends Module 
{
    /**
     * Constructs a new ClassSchedule module.
     *
     * @param n The name of the module.
     * @param i The image associated with the module.
     */
    public ClassSchedule(String n, Image i) 
    {
        super(n, i); // Calls the parent (Module) constructor
    }

    /**
     * Returns the JavaFX scene node that displays the student's class schedule.
     *
     * @return A Node representing the layout of the schedule.
     */
    @Override
    public Node getScene() 
    {
        // Create the main layout VBox
        VBox layout = new VBox(10);
        layout.getStyleClass().add("dining-root");

        // Add the title
        Label title = new Label("Your Schedule");
        title.getStyleClass().add("dining-header");
        layout.getChildren().add(title);
        
        // Get list of classes from schedule.txt
        List<Class> schedule = retrieveSchedule();

        // Check if schedule is empty
        if (schedule.isEmpty()) 
        {
            // If no classes found, display a message
            Label emptyMsg = new Label("No classes found in schedule.txt");
            emptyMsg.getStyleClass().add("meal-text");
            layout.getChildren().add(emptyMsg);
        } 
        else 
        {   
            // Loop through each class and display it
            for (Class c : schedule) 
            {
                // Create styled Text elements for each part of the class information
                Text classCode = new Text(c.getCode() + " – ");
                classCode.setFill(javafx.scene.paint.Color.WHITE);
                classCode.setFont(Font.font("System", FontWeight.BOLD, 14));

                Text className = new Text(c.getName() + " - ");
                className.setFill(javafx.scene.paint.Color.WHITE);
                className.setFont(Font.font("System", FontWeight.NORMAL, 14));

                Text location = new Text(c.getLocation() + " @ ");
                location.setFill(javafx.scene.paint.Color.WHITE);
                location.setFont(Font.font("System", FontWeight.BOLD, 14));

                Text time = new Text(c.getTime() + " ");
                time.setFill(javafx.scene.paint.Color.WHITE);
                time.setFont(Font.font("System", FontWeight.NORMAL, 14));

                Text day = new Text("(" + c.getDay() + ")");
                day.setFill(javafx.scene.paint.Color.WHITE);
                day.setFont(Font.font("System", FontWeight.BOLD, 14));

                // Combine all Text elements into a TextFlow
                TextFlow flow = new TextFlow(classCode, className, location, time, day);
                flow.getStyleClass().add("meal-text");

                // Add the TextFlow to the layout
                layout.getChildren().add(flow);
            }
        }

        return layout;
    }

    /**
     * Reads the class schedule from "schedule.txt" file and separates and defines
     * each property.
     * 
     * @return A list of Class objects representing the schedule.
     */
    private List<Class> retrieveSchedule() 
    {
        // Initialize an empty list to store classes
        List<Class> schedule = new ArrayList<>();

        // Try reading the file
        try (BufferedReader reader = new BufferedReader(new FileReader("schedule.txt")))
        {
            String line;

            // Read each line from the file
            while ((line = reader.readLine()) != null)
            {
                // Split the line into parts based on double commas (,,)
                String[] parts = line.split(",,", 5);

                // Ensure there are exactly 5 parts: code, name, location, time, day
                if (parts.length == 5) 
                {
                    String classCode = parts[0].trim();
                    String className = parts[1].trim();
                    String location = parts[2].trim();
                    String time = parts[3].trim();
                    String day = parts[4].trim();
                    
                    // Create a new Class object and add it to the list
                    schedule.add(new Class(classCode, className, location, time, day)); 
                }
            }
        } 
        
        // Handle any file reading exceptions
        catch (Exception e) 
        {
            System.out.println("Error reading schedule.txt:");
        }

        return schedule;
    }

    /**
     * Inner class representing an individual Class entry.
     */
    public static class Class 
    {
        // Fields for each class property
        private final String classCode;
        private final String className;
        private final String location;
        private final String time;
        private final String day;

        /**
         * Constructs a Class entry.
         *
         * @param classCode The code of the class (e.g., MA-101).
         * @param className The name/title of the class.
         * @param location  The classroom location.
         * @param time      The time of the class.
         * @param day       The days when the class occurs.
         */
        public Class(String classCode, String className, String location, String time, String day) 
        {
            this.classCode = classCode;
            this.className = className;
            this.location = location;
            this.time = time;
            this.day = day;
        }

        /** 
         * @return the class code 
         **/
        public String getCode() 
        {
            return classCode; 
        }

        /** 
         * @return the class name 
         **/
        public String getName()
        {
            return className; 
        }

        /**
         * @return the class location 
         **/
        public String getLocation() 
        { 
            return location; 
        }

        /** 
         * @return the class time 
         **/
        public String getTime() 
        {
            return time;
        }

        /**
         *  @return the class days 
         **/
        public String getDay()
        { 
            return day; 
        }
    }
}