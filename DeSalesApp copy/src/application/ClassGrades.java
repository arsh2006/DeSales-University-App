/**
 * ClassGrades is a module that reads student grades 
 * from a text file ("grades.txt") and displays them formatted in a JavaFX scene.
 *
 * <p>The grades file must follow the format:
 * <pre>
 * CLASS NAME,,LETTER GRADE,,CREDITS
 * Example:
 * Calculus II,,A,,4
 * </pre>
 *
 * @author Micheal Vu
 * @since 2025-04-14
 * @updated 2025-04-26 
 */
package application;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the ClassGrades module for displaying student grades.
 */
public class ClassGrades extends Module 
{
    /**
     * Constructs a new ClassGrades module.
     * 
     * @param n The name of the module.
     * @param i The image associated with the module.
     */
    public ClassGrades(String n, Image i) 
    {
        super(n, i); // Call the superclass constructor (Module) with name and image
    }

    /**
     * Returns the JavaFX scene node that displays the student's grades.
     *
     * @return A Node representing the layout of the grades.
     */
    @Override
    public Node getScene() 
    {
        // Create a vertical layout with 10px spacing
        VBox layout = new VBox(10);
        layout.getStyleClass().add("dining-root"); // Add a CSS class for styling

        // Create and style the title label
        Label title = new Label("Your Grades");
        title.getStyleClass().add("dining-header");
        layout.getChildren().add(title); // Add title to the layout

        // Retrieve the list of grades from the file
        List<Grade> grades = retrieveGrades();

        // If no grades were found, display an empty message
        if (grades.isEmpty()) 
        {
            Label emptyMsg = new Label("No grades found in grades.txt");
            emptyMsg.getStyleClass().add("meal-text");
            layout.getChildren().add(emptyMsg);
        } 
        else 
        {
            // Loop through each grade and create a formatted text display
            for (Grade g : grades) 
            {
                Text className = new Text(g.getClassName() + ": ");
                className.setFill(Color.WHITE);
                className.setFont(Font.font("System", FontWeight.NORMAL, 14));

                Text grade = new Text(g.getGrade() + " ");
                grade.setFill(Color.WHITE);
                grade.setFont(Font.font("System", FontWeight.BOLD, 14)); // Bold the grade

                Text credits = new Text("(" + g.getCredits() + " credits)");
                credits.setFill(Color.WHITE);
                credits.setFont(Font.font("System", FontWeight.NORMAL, 14));

                // Combine className, grade, and credits into a TextFlow
                TextFlow flow = new TextFlow(className, grade, credits);
                flow.getStyleClass().add("meal-text");

                layout.getChildren().add(flow); // Add the flow to the layout
            }
        }

        // Return the completed layout
        return layout;
    }

    /**
     * Reads the student's grades from "grades.txt" file.
     * 
     * @return A list of Grade objects representing the student's grades.
     */
    private List<Grade> retrieveGrades() 
    {
        List<Grade> grades = new ArrayList<>(); // List to hold all grade entries

        try (BufferedReader reader = new BufferedReader(new FileReader("grades.txt")))
        {
            String line;
            // Read each line until the end of file
            while ((line = reader.readLine()) != null)
            {
                // Split the line into 3 parts using ',,' as the delimiter
                String[] parts = line.split(",,", 3);

                // Make sure exactly 3 parts were found
                if (parts.length == 3) 
                {
                    String className = parts[0].trim(); // Class name at index 0
                    char letterGrade = parts[1].trim().charAt(0); // Letter grade at index 1
                    int credits = Integer.parseInt(parts[2].trim()); // Credits at index 2

                    // Add a new Grade object to the list
                    grades.add(new Grade(className, letterGrade, credits));
                }
            }
        } 
        // Handle any exceptions during file reading
        catch (Exception e) 
        {
            System.out.println("Error reading grades.txt: " + e.getMessage());
        }

        return grades; // Return the list of grades
    }

    /**
     * Inner class representing a Grade entry.
     */
    public static class Grade 
    {
        private final String className;   // Name of the class
        private final char letterGrade;   // Letter grade (e.g., A, B, C)
        private final int credits;        // Number of credits for the class

        /**
         * Constructs a Grade entry.
         *
         * @param className The name of the class.
         * @param letterGrade The letter grade received.
         * @param credits The number of credits for the class.
         */
        public Grade(String className, char letterGrade, int credits) 
        {
            this.className = className;
            this.letterGrade = letterGrade;
            this.credits = credits;
        }

        /** 
         * @return the class name 
         */
        public String getClassName() 
        { 
        	return className; 
        }

        /** 
         * @return the letter grade 
         */
        public char getGrade() 
        { 
        	return letterGrade; 
        }

        /** 
         * @return the number of credits
         */
        public int getCredits() 
        {
        	return credits; 
        }
    }
}