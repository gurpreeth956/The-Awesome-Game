import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Main extends Application {

    Scene menuScene;
    Scene gameScene;
    Scene optionScene;

    static Pane root;

    private HashMap<KeyCode, Boolean> keys = new HashMap();
    Image charImage = new Image("file:src/Greenies.png");
    ImageView charIV = new ImageView(charImage);
    Character player = new Character(charIV, 200, 200);

    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projToRemove = new ArrayList<>();
    long timeOfLastProjectile = 0;

    private List<Enemy> enemies = new ArrayList();
    private List<Enemy> enemToRemove = new ArrayList();
    long hitTime = 0;

    Rectangle healthBarOutline;
    Rectangle actualHealth;
    Rectangle lostHealth;
    
    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    
    boolean gameplay = false;
    boolean pause = false;
    
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
	menuScene = new Scene(bp, screenSize.getWidth(), screenSize.getHeight());
	menuScene.getStylesheets().addAll(this.getClass().getResource("Menu.css").toExternalForm());

	//Game Scene
	root = new Pane();
	root.setId("backgroundtrial");

	Label healthLabel = new Label("Health: ");
	healthLabel.setFont(new Font("Arial", 20));
	healthLabel.toFront();
	healthBarOutline = new Rectangle(79, 9, 101, 22);
	healthBarOutline.setFill(Color.TRANSPARENT);
	healthBarOutline.setStroke(Color.BLACK);
	lostHealth = new Rectangle(80, 10, 99, 20);
	lostHealth.setFill(Color.RED);
	actualHealth = new Rectangle(80, 10, 99, 20);
	actualHealth.setFill(Color.GREEN);
	actualHealth.toFront();

	VBox health = new VBox(10);
	health.getChildren().addAll(healthLabel);
	health.setTranslateX(10);
	health.setTranslateY(10);

	root.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth);
	gameScene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
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
	optionScene = new Scene(opPane, screenSize.getWidth(), screenSize.getHeight());
	optionScene.getStylesheets().addAll(this.getClass().getResource("Menu.css").toExternalForm());
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
	
	//primaryStage.setMaximized(true);
	primaryStage.setTitle("The Awesome Game");
        //primaryStage.setFullScreen(true);
	primaryStage.setScene(menuScene);
	primaryStage.show();

	primaryStage.setOnCloseRequest(e -> {
            e.consume();
            pause = true;
            
            boolean close = AlertBox.exitDisplay("Exit Game", "Are you sure you want to exit?");
            if (close) {
                Platform.exit();
                gameplay = false;
                
                for (Enemy enemy : enemies) {
                    root.getChildren().removeAll(enemy);
                }
                enemies.clear();
            }
            
            pause = false;
        });
    }

    //This is where we update the gameplay 
    public void update() {
        if (gameplay && !pause) {
            if (player.getHealth() == 0) {
                Platform.exit();
            }

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

            if (Math.random() < 0.01) {
                createEnemy();
            }
            
            for (Projectile proj : projectiles) {
                updateProj(proj);
            }
            for (Enemy enemy : enemies) {
                updateEnemy(enemy);
            }

            projectiles.removeAll(projToRemove);
            projToRemove.clear();

            enemies.removeAll(enemToRemove);
            enemToRemove.clear();

            //to clear enemies (temporary)
            if (isPressed(KeyCode.P)) {
                for (Enemy enemy : enemies) {
                    root.getChildren().removeAll(enemy);
                }
                enemies.clear();
            }
        }
    }

    public void characterShooting() {
	long timeNow = System.currentTimeMillis();
	long time = timeNow - timeOfLastProjectile;

	if (isPressed(KeyCode.UP)) {
	    player.setCharacterView(128, 183);
	    player.setOffsetY(183);
	    if (time < 0 || time > 500) {
		createProjectile(0, -8);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.DOWN)) {
	    player.setCharacterView(128, 0);
	    player.setOffsetY(0);
	    if (time < 0 || time > 500) {
		createProjectile(0, 8);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.LEFT)) {
	    player.setCharacterView(128, 123);
	    player.setOffsetY(123);
	    if (time < 0 || time > 500) {
		createProjectile(-8, 0);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.RIGHT)) {
	    player.setCharacterView(128, 61);
	    player.setOffsetY(61);
	    if (time < 0 || time > 500) {
		createProjectile(8, 0);
		timeOfLastProjectile = timeNow;
	    }
	}
    }

    public void createProjectile(int x, int y) {
	Image image = new Image("file:src/shot.png");
	ImageView iv = new ImageView(image);
	Projectile proj = new Projectile(iv, player.getX() + 28, player.getY() + 16);
	proj.setVelocityX(x);
	proj.setVelocityY(y);

	root.getChildren().addAll(proj);
	proj.toBack();
	projectiles.add(proj);
    }

    public void updateProj(Projectile proj) {
	proj.move();

	for (Enemy enemy : enemies) {
	    if (proj.isColliding(enemy)) {
		enemy.hit();
		proj.setAlive(false);
	    }
	}

	if (proj.getTranslateX() <= 0 || proj.getTranslateX() >= gameScene.getWidth()) {
	    proj.setAlive(false);
	} else if (proj.getTranslateY() <= 0 || proj.getTranslateY() >= gameScene.getHeight()) {
	    proj.setAlive(false);
	}

	if (!proj.isAlive()) {
	    root.getChildren().remove(proj);
	    projToRemove.add(proj);
	}
    }

    public void createEnemy() {
	Image image = new Image("file:src/Redies.png");
	ImageView iv = new ImageView(image);
	Enemy enemy = new Enemy(iv, (int) (Math.random() * gameScene.getWidth()),
		(int) (Math.random() * gameScene.getHeight()));

	root.getChildren().add(enemy);
	enemies.add(enemy);
    }

    public void updateEnemy(Enemy enemy) {
	long timeNow = System.currentTimeMillis();
	long time = timeNow - hitTime;
	if (time < 0 || time > 1000) {
	    if (enemy.playerColliding(player)) {
		player.hit();

		root.getChildren().remove(actualHealth);
		actualHealth = new Rectangle(80, 10, player.getHealth() * 20, 20);
		actualHealth.setFill(Color.GREEN);
		root.getChildren().add(actualHealth);
	    }
	    hitTime = timeNow;
	}
	//if (!enemy.playerColliding(player)&&!enemy.enemyColliding(enemies)) {
	    if (player.getX() > enemy.getX() && player.getY() == enemy.getY()) { //right
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, gameScene.getWidth());
	    }
	    if (player.getX() < enemy.getX() && player.getY() == enemy.getY()) { //left
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, gameScene.getWidth());
	    }
	    if (player.getX() == enemy.getX() && player.getY() > enemy.getY()) { //down
		enemy.setCharacterView(0, 0);
		enemy.moveY(1, gameScene.getHeight());
	    }
	    if (player.getX() == enemy.getX() && player.getY() < enemy.getY()) { //up
		enemy.setCharacterView(0, 183);
		enemy.moveY(-1, gameScene.getHeight());
	    }

	    if (player.getX() > enemy.getX() && player.getY() < enemy.getY()) { //quadrant1
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, gameScene.getWidth());
		enemy.moveY(-1, gameScene.getHeight());
	    }
	    if (player.getX() < enemy.getX() && player.getY() < enemy.getY()) { //quadrant2
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, gameScene.getWidth());
		enemy.moveY(-1, gameScene.getHeight());
	    }
	    if (player.getX() > enemy.getX() && player.getY() > enemy.getY()) { //quadrant3
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, gameScene.getWidth());
		enemy.moveY(1, gameScene.getHeight());
	    }
	    if (player.getX() < enemy.getX() && player.getY() > enemy.getY()) { //quadrant4
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, gameScene.getWidth());
		enemy.moveY(1, gameScene.getHeight());
	    }
	//}
	if (enemy.getHealth() == 0) {
	    enemy.setAlive(false);
	}
	if (!enemy.isAlive()) {
	    enemToRemove.add(enemy);
	    root.getChildren().remove(enemy);
	}
    }

    public boolean isPressed(KeyCode key) {
	return keys.getOrDefault(key, false);
    }

    //Button Layouts
    public VBox addMenuButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);

	Button startBtn = new Button("Start");
	startBtn.setOnAction(e -> {
	    pStage.setScene(gameScene);
            //pStage.setFullScreen(true);
            gameplay = true;
	});

	Button optionsBtn = new Button("Options");
	optionsBtn.setOnAction(e -> {
	    pStage.setScene(optionScene);
            //pStage.setFullScreen(true);
	});

	Button exitBtn = new Button("Exit");
	exitBtn.setOnAction(e -> {
	    e.consume();
	    boolean close = AlertBox.exitDisplay("Exit Game", "Are you sure you want to exit?");
	    if (close) {
		Platform.exit();
	    }
	});

	vbox.getChildren().addAll(startBtn, optionsBtn, exitBtn);
	return vbox;
    }

    public VBox addOptionButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);

	CheckBox fullBox = new CheckBox("FullScreen");
	fullBox.setSelected(false);
	fullBox.setOnAction(e -> {
	    if (fullBox.isSelected()) {
		pStage.setFullScreen(true);
	    } else {
		pStage.setFullScreen(false);
	    }
	});

	CheckBox musicBox = new CheckBox("Music");
	musicBox.setSelected(false);
	musicBox.setOnAction(e -> {

	});

	Button backBtn = new Button("Back to Menu");
	backBtn.setOnAction(e -> {
	    pStage.setScene(menuScene);
            //pStage.setFullScreen(true);
	});

	vbox.getChildren().addAll(fullBox, musicBox, backBtn);
	return vbox;
    }

    public static void main(String[] args) {
	launch(args);
    }
}
