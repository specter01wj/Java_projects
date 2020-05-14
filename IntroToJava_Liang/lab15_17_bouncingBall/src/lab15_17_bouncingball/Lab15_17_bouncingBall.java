/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab15_17_bouncingball;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 *
 * @author james.wang
 */
public class Lab15_17_bouncingBall extends Application {

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
      BallPane ballPane = new BallPane(); // Create a ball pane

      // Pause and resume animation
      ballPane.setOnMousePressed(e -> ballPane.pause());
      ballPane.setOnMouseReleased(e -> ballPane.play());

      // Increase and decrease animation   
      ballPane.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.UP) {
          ballPane.increaseSpeed();
        } 
        else if (e.getCode() == KeyCode.DOWN) {
          ballPane.decreaseSpeed();
        }
      });

      // Create a scene and place it in the stage
      Scene scene = new Scene(ballPane, 300, 250);
      primaryStage.setTitle("BounceBallControl"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.show(); // Display the stage

      // Must request focus after the primary stage is displayed
      ballPane.requestFocus();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
