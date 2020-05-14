/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab16_12;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author james.wang
 */

// TicTacToe
public class Lab16_12 extends Application {

    private int height = 3, width = 3;
    private char whoseTurn = 'X';
    private Cell[][] cell =  new Cell[height][width];
    private Label lblStatus = new Label("X's turn to play");
    
    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane(); 
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            pane.add(cell[i][j] = new Cell(), j, i);
          }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        
        Scene scene = new Scene(borderPane, 650, 350);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public class Cell extends Pane {
        private char token = ' ';

        public Cell() {
          setStyle("-fx-border-color: black"); 
          this.setPrefSize(2000, 2000);
          this.setOnMouseClicked(e -> handleMouseClick());
        }
        
        public char getToken() {
          return token;
        }
        
        public void setToken(char c) {
          token = c;

          if (token == 'X') {
            Line line1 = new Line(10, 10, 
              this.getWidth() - 10, this.getHeight() - 10);
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            Line line2 = new Line(10, this.getHeight() - 10, 
              this.getWidth() - 10, 10);
            line2.startYProperty().bind(
              this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));
            
            this.getChildren().addAll(line1, line2); 
          } else if (token == 'O') {
            Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
              this.getHeight() / 2, this.getWidth() / 2 - 10, 
              this.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(
              this.widthProperty().divide(2));
            ellipse.centerYProperty().bind(
                this.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(
                this.widthProperty().divide(2).subtract(10));        
            ellipse.radiusYProperty().bind(
                this.heightProperty().divide(2).subtract(10));   
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.WHITE);

            getChildren().add(ellipse);
          }
        }
        
        private void handleMouseClick() {
          
          if (token == ' ' && whoseTurn != ' ') {
            setToken(whoseTurn);
            
            if (isWon(whoseTurn)) {
              lblStatus.setText(whoseTurn + " won! The game is over");
              whoseTurn = ' ';
            } else if (isFull()) {
              lblStatus.setText("Draw! The game is over");
              whoseTurn = ' ';
            } else {
              whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
              lblStatus.setText(whoseTurn + "'s turn");
            }
          }
        }
    }
    
    public boolean isFull() {
        for (int i = 0; i < height; i++)
          for (int j = 0; j < width; j++)
            if (cell[i][j].getToken() == ' ')
              return false;

        return true;
    }
    
    public boolean isWon(char token) {
        for (int i = 0; i < height; i++) {
          if (cell[i][0].getToken() == token
              && cell[i][1].getToken() == token
              && cell[i][2].getToken() == token) {
            return true;
          }
        }

        for (int j = 0; j < width; j++) {
          if (cell[0][j].getToken() ==  token
              && cell[1][j].getToken() == token
              && cell[2][j].getToken() == token) {
            return true;
          }
        }

        if (cell[0][0].getToken() == token 
            && cell[1][1].getToken() == token        
            && cell[2][2].getToken() == token) {
          return true;
        }

        if (cell[0][2].getToken() == token
            && cell[1][1].getToken() == token
            && cell[2][0].getToken() == token) {
          return true;
        }

        return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }
    
}
