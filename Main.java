package pkg2dsidescroll;

import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        Text title = new Text("Game Name");
        title.setFont(Font.font("Arial", 40));
        VBox vbox = addButtons();
        vbox.setAlignment(Pos.CENTER);
        
        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setTop(title);
        Scene scene = new Scene(bp, 850, 650, Color.BLACK);
        
        primaryStage.setTitle("Game Name");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public VBox addButtons(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);
        Button startBtn = new Button();
        startBtn.setText("Start");
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        Button optionsBtn = new Button();
        optionsBtn.setText("Options");
        optionsBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        Button exitBtn = new Button();
        exitBtn.setText("Exit");
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               Platform.exit();
            }
        });
        vbox.getChildren().addAll(startBtn,optionsBtn,exitBtn);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
