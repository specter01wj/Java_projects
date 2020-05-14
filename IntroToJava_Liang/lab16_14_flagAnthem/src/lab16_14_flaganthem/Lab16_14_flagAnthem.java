/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab16_14_flaganthem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException; 

import java.io.File;

/**
 *
 * @author james.wang
 */

// FlagAnthem
public class Lab16_14_flagAnthem extends Application {

    private final static int NUMBER_OF_NATIONS = 7;
    private final static String URLBase = "";
    private int currentIndex = 0;
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Image[] images = new Image[NUMBER_OF_NATIONS];
        MediaPlayer[] mp = new MediaPlayer[NUMBER_OF_NATIONS];
        System.out.println(System.getProperty("user.dir"));
        
        for (int i = 0; i < NUMBER_OF_NATIONS; i++) {
            //images[i] = new Image( "image/" + i + ".gif");
            images[i] = new Image(new FileInputStream("image/" + i + ".gif"));
            //mp[i] = new MediaPlayer(new Media("anthem/" + i + ".mp3"));
            mp[i] = new MediaPlayer(new Media(new File("anthem/" + i + ".mp3").toURI().toString()));
        }
        
        Button btPlayPause = new Button(">");
        btPlayPause.setOnAction(e -> {
          if (btPlayPause.getText().equals(">")) {
            btPlayPause.setText("||");
            mp[currentIndex].pause();
          } 
          else {
            btPlayPause.setText(">");
            mp[currentIndex].play();
          }
        });
        
        ImageView imageView = new ImageView(images[currentIndex]);   
        ComboBox<String> cboNation = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList
          ("Denmark", "Germany", "China", "India", "Norway", "UK", "US");
        cboNation.getItems().addAll(items);
        cboNation.setValue(items.get(0));
        cboNation.setOnAction(e -> {
          mp[currentIndex].stop();
          currentIndex = items.indexOf(cboNation.getValue());
          imageView.setImage(images[currentIndex]);
          mp[currentIndex].play();
        });
        
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btPlayPause, 
          new Label("Select a nation: "), cboNation);
        hBox.setAlignment(Pos.CENTER);
        
        BorderPane pane = new BorderPane();
        pane.setCenter(imageView);
        pane.setBottom(hBox);
        
        Scene scene = new Scene(pane, 650, 350);
        primaryStage.setTitle("FlagAnthem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }
    
}
