//package pkg2dsidescroll; //(Ray's Package)

import java.util.HashMap;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Main extends Application {
    
    Scene menuScene;
    Scene gameScene;
    Scene optionScene;
    
    static Pane root;
    
    private HashMap<KeyCode, Boolean> keys = new HashMap();
    Image charImage = new Image("file:src/Greenies.png"); //depends on where image is placed
    ImageView charIV = new ImageView(charImage);
    Character player = new Character(charIV, 200, 200);
    
    boolean gamePlay = true;
    
    @Override
    public void start(Stage primaryStage) {
        //Menu Scene
        Text title = new Text("The Awesome Game");
        title.setFont(Font.font("Arial", 40));
        VBox vbox = addMenuButtons(primaryStage);
        vbox.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
	bp.setId("menu");
        bp.setCenter(vbox);
        bp.setTop(title);
        bp.setAlignment(title, Pos.CENTER);
        menuScene = new Scene(bp, 850, 650, Color.BLACK);
	menuScene.getStylesheets().addAll(this.getClass().getResource("Menu.css").toExternalForm());
        
        //Game Scene
        root = new Pane();
        root.setId("backgroundtrial");
        root.getChildren().addAll(player);
        gameScene = new Scene(root, 850, 650);
        gameScene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        
        gameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));
        
        //Options Scene
        Text opTitle = new Text("Options");
        opTitle.setFont(Font.font("Arial", 40));
        VBox optionsBox = addOptionButtons(primaryStage);
        optionsBox.setAlignment(Pos.CENTER);
        BorderPane opPane = new BorderPane();
	opPane.setId("menu");
        opPane.setCenter(optionsBox);
        opPane.setTop(opTitle);
        opPane.setAlignment(opTitle, Pos.CENTER);
        optionScene = new Scene(opPane, 850, 650);
	optionScene.getStylesheets().addAll(this.getClass().getResource("Menu.css").toExternalForm());
        
        
        if (gamePlay) {
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
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
        if (isPressed(KeyCode.W)) {
            player.setCharacterView(0, 183);
            player.moveY(-2, gameScene.getHeight());
            player.setOffsetY(183);
            characterShooting();
        } else if (isPressed(KeyCode.S)) {
            player.setCharacterView(0, 0);
            player.moveY(2, gameScene.getHeight());
            player.setOffsetY(0);
            characterShooting();
        } else if (isPressed(KeyCode.A)) {
            player.setCharacterView(0, 123);
            player.moveX(-2, gameScene.getWidth());
            player.setOffsetY(123);
            characterShooting();
        } else if (isPressed(KeyCode.D)) {
            player.setCharacterView(0, 61);
            player.moveX(2, gameScene.getWidth());
            player.setOffsetY(61);
            characterShooting();
        } else {
            player.setCharacterView(0, player.getOffsetY());
            characterShooting();
        }
    }
    
    public void characterShooting() {
        if (isPressed(KeyCode.UP)) {
            player.setCharacterView(128, 183);
            player.setOffsetY(183);
        } else if (isPressed(KeyCode.DOWN)) {
            player.setCharacterView(128, 0);
            player.setOffsetY(0);
        } else if (isPressed(KeyCode.LEFT)) {
            player.setCharacterView(128, 123);
            player.setOffsetY(123);
        } else if (isPressed(KeyCode.RIGHT)) {
            player.setCharacterView(128, 61);
            player.setOffsetY(61);
        }
    }
    
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    
    //Button Layouts
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
        musicBox.setOnAction(e->{
            
        });
	
	Button backBtn = new Button("Back to Menu");
	backBtn.setOnAction(e->{
	    pStage.setScene(menuScene);
	});
        
        vbox.getChildren().addAll(fullBox, musicBox, backBtn);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
