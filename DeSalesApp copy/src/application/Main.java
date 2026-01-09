/**
 * Main class for the DeSalesU application.
 * This class sets up the primary window and handles scene switching between
 * the Main Menu, Dining, Grades, and Schedule scenes.
 * 
 * @author Ian Henninger
 * 
 */

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class Main extends Application implements EventHandler<ActionEvent> 
{
    // Main window and scenes
    Stage window;
    Scene mainMenu, diningScene, grades, schedule;
    
    /**
     * Main method. Launches the JavaFX application.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    /**
     * Starts the JavaFX application by setting up the scenes and showing the window.
     * 
     * @param primaryStage the stage for this application
     */
    
    @Override
    public void start(Stage primaryStage) 
    {
        window = primaryStage;
        
        try 
        {
            // Create buttons for navigation between scenes
            Button Blunch = new Button("|| Dining ||");
            Blunch.setOnAction(e -> window.setScene(diningScene));
            Button Bgrades = new Button("|| Grades ||");
            Bgrades.setOnAction(e -> window.setScene(grades));
            Button Bschedule = new Button("|| Schedule ||");
            Bschedule.setOnAction(e -> window.setScene(schedule));
            Button BMainMenu = new Button("|| Main Menu ||");
            BMainMenu.setOnAction(e -> window.setScene(mainMenu));
            Button BMainMenu2 = new Button("|| Main Menu ||");
            BMainMenu2.setOnAction(e -> window.setScene(mainMenu));
            Button BMainMenu3 = new Button("|| Main Menu ||");
            BMainMenu3.setOnAction(e -> window.setScene(mainMenu));
            
            // Create the title label
            Label title = new Label("DeSalesU");
            title.getStyleClass().add("app-title");
            
            // Create and configure DeSales logo image
            Image logo = new Image(getClass().getResourceAsStream("/application/logo.png"));
            ImageView imageView = new ImageView(logo);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);
            HBox logoBox = new HBox(imageView);
            logoBox.setAlignment(Pos.CENTER);
            
            // Set up Main Menu layout
            HBox titleBox = new HBox(title);
            titleBox.setAlignment(Pos.CENTER);

            HBox menuButtons = new HBox(10);
            menuButtons.getChildren().addAll(Blunch, Bgrades, Bschedule, BMainMenu);
            menuButtons.setAlignment(Pos.CENTER);

            VBox MMVbox = new VBox(20);
            MMVbox.setStyle("-fx-background-color: #001f3f;");
            MMVbox.setAlignment(Pos.CENTER);
            MMVbox.getChildren().addAll(logoBox, titleBox, menuButtons);

            mainMenu = new Scene(MMVbox, 800, 400);
            mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set up Dining scene
            Dining diningModule = new Dining("Dining", null);
            VBox diningLayout = new VBox(10);
            diningLayout.getChildren().add(diningModule.getScene());
            diningLayout.getChildren().add(BMainMenu);
            diningScene = new Scene(diningLayout, 800, 600);
            diningScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set up Grades scene
            ClassGrades gradesModule = new ClassGrades("Grades", null);
            VBox MMGrades = new VBox(10);
            MMGrades.getChildren().add(gradesModule.getScene());
            MMGrades.getChildren().add(BMainMenu2);
            grades = new Scene(MMGrades, 800, 600);
            grades.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set up Schedule scene
            ClassSchedule scheduleModule = new ClassSchedule("Schedule", null);
            VBox MMSchedule = new VBox(10);
            MMSchedule.getChildren().add(scheduleModule.getScene());
            MMSchedule.getChildren().add(BMainMenu3);
            schedule = new Scene(MMSchedule, 800, 600);
            schedule.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set the default scene
            window.setScene(mainMenu);
            window.setTitle("DeSales - AAG");
            window.show();
            
        } 
        catch (Exception a) 
        {
            a.printStackTrace();
        }
    }
    
    /*
     * @param ActionEvent arg0 unused needed to override
     */
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}