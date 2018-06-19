//package pkg2dsidescroll; //(Ray's Package)

import javafx.animation.AnimationTimer;
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
    
    Scene menuScene;
    Scene gameScene;
    
    boolean gamePlay = false;
    
    @Override
    public void start(Stage primaryStage) {
       
        //Menu Scene
        Text title = new Text("The Awesome Game");
        title.setFont(Font.font("Arial", 40));
        VBox vbox = addButtons(primaryStage);
        vbox.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setTop(title);
        bp.setAlignment(title, Pos.CENTER);
        menuScene = new Scene(bp, 850, 650, Color.BLACK);
        
        
        //Game Scene
        Pane root = new Pane();
        
        
        gameScene = new Scene(root, 850, 650);
        
        if (gamePlay) {
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                
                } 
            };
            timer.start();
        }
        
        primaryStage.setTitle("The Awesome Game");
        primaryStage.setScene(menuScene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            
            boolean close = AlertBox.exitDisplay("Close Window", "Are you sure you want to exit?");
            if (close) Platform.exit();
        });
    }
    
    //This is where we will update the gameplay 
    public void update() {
        
    }
    
    public VBox addButtons(Stage pStage){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);
        
        Button startBtn = new Button();
        startBtn.setText("Start");
        startBtn.setOnAction(e -> {
            pStage.setScene(gameScene);
            gamePlay = true;
        });
        
        Button optionsBtn = new Button();
        optionsBtn.setText("Options");
        optionsBtn.setOnAction(e -> {
            
        });
        
        Button exitBtn = new Button();
        exitBtn.setText("Exit");
        exitBtn.setOnAction(e -> {
            Platform.exit();
        });
        
        vbox.getChildren().addAll(startBtn,optionsBtn,exitBtn);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}