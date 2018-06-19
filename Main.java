//package pkg2dsidescroll; //(Ray's Package)

import javafx.animation.AnimationTimer;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class Main extends Application {
    
    Scene menuScene;
    Scene gameScene;
    Scene optionScene;
    
    boolean gamePlay = false;
    
    @Override
    public void start(Stage primaryStage) {
       
        //Menu Scene
        Text title = new Text("The Awesome Game");
        title.setFont(Font.font("Arial", 40));
        VBox vbox = addMenuButtons(primaryStage);
        vbox.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setTop(title);
        bp.setAlignment(title, Pos.CENTER);
        menuScene = new Scene(bp, 850, 650, Color.BLACK);
        
        //Game Scene
        Pane root = new Pane();
        gameScene = new Scene(root, 850, 650);
        
        //Options Scene
        Text opTitle = new Text("Options");
        opTitle.setFont(Font.font("Arial", 40));
        VBox optionsBox = addOptionButtons(primaryStage);
        optionsBox.setAlignment(Pos.CENTER);
        BorderPane opPane = new BorderPane();
        opPane.setCenter(optionsBox);
        opPane.setTop(opTitle);
        opPane.setAlignment(opTitle, Pos.CENTER);
        optionScene = new Scene(opPane, 850, 650);
        
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
            
            boolean close = AlertBox.exitDisplay("Exit Game", "Are you sure you want to exit?");
            if (close) Platform.exit();
        });
    }
    //This is where we will update the gameplay 
    public void update() {
        
    }
    
    public VBox addMenuButtons(Stage pStage){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);
        
        Button startBtn = new Button("Start");
        startBtn.setOnAction(e -> {
            pStage.setScene(gameScene);
            gamePlay = true;
        });
        
        Button optionsBtn = new Button("Options");
        optionsBtn.setOnAction(e -> {
            pStage.setScene(optionScene);
        });
        
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> {
            e.consume();
            boolean close = AlertBox.exitDisplay("Exit Game", "Are you sure you want to exit?");
            if (close) Platform.exit();
        });
        
        vbox.getChildren().addAll(startBtn,optionsBtn,exitBtn);
        return vbox;
    }
    
    public VBox addOptionButtons(Stage pStage){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);
        CheckBox fullBox = new CheckBox("FullScreen");
        fullBox.setSelected(false);
        fullBox.setOnAction(e->{
            if(fullBox.isSelected()){
                pStage.setFullScreen(true);
            }
            else{
                pStage.setFullScreen(false);
            }
        });
        CheckBox musicBox = new CheckBox("Music");
        musicBox.setSelected(false);
        vbox.getChildren().addAll(fullBox, musicBox);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}