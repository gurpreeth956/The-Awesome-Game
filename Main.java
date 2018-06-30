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
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

public class Main extends Application {

    Scene scene;
    static Pane gameRoot;
    static BorderPane menuRoot;
    static BorderPane optionsRoot;
    static BorderPane gameOptionsRoot;
    static BorderPane gameOverRoot;
    static VBox areYouSureRoot;
    static VBox exitRoot;

    Button yesExit = new Button("Yes");
    Button noExit = new Button("No");
    Button yesReturn = new Button("Yes");
    Button noReturn = new Button("No");

    private final HashMap<KeyCode, Boolean> keys = new HashMap();
    Image charImage = new Image("file:src/Greenies.png");
    ImageView charIV = new ImageView(charImage);
    
    Character player;
    Level level;

    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projToRemove = new ArrayList<>();
    private long timeOfLastProjectile = 0;

    private List<Enemy> enemies = new ArrayList();
    private List<Enemy> enemToRemove = new ArrayList();
    private long hitTime = 0;

    private List<Portal> portals = new ArrayList();
    private int portalCount = 0;

    Rectangle healthBarOutline;
    Rectangle actualHealth;
    Rectangle lostHealth;
    VBox health;
    VBox score;

    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    boolean gameplay = false;
    boolean pause = false;
    long pauseTime = 0;

    @Override
    public void start(Stage primaryStage) {

	//Menu Root
	Text title = new Text("The Awesome Game");
	title.setFont(Font.font("Arial", 40));
	VBox vbox = addMenuButtons(primaryStage);
	vbox.setAlignment(Pos.CENTER);
	menuRoot = new BorderPane();
	menuRoot.setId("menu");
	menuRoot.setCenter(vbox);
	menuRoot.setTop(title);
	menuRoot.setAlignment(title, Pos.CENTER);

	scene = new Scene(menuRoot, screenSize.getWidth(), screenSize.getHeight());
	scene.getStylesheets().addAll(this.getClass().getResource("Menu.css").toExternalForm());

	//Game Root
	gameRoot = new Pane();
	gameRoot.setId("backgroundtrial");
	gameRoot.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
	Label healthLabel = new Label("Health: ");
	healthLabel.setFont(new Font("Arial", 20));
	healthLabel.toFront();
	healthBarOutline = new Rectangle(79, 9, 101, 22);
	healthBarOutline.setFill(Color.TRANSPARENT);
	healthBarOutline.setStroke(Color.BLACK);
	lostHealth = new Rectangle(80, 10, 99, 21);
	lostHealth.setFill(Color.RED);
	actualHealth = new Rectangle(80, 10, 99, 21);
	actualHealth.setFill(Color.GREEN);
	health = new VBox(10);
	health.getChildren().addAll(healthLabel);
	health.setTranslateX(10);
	health.setTranslateY(10);
	Label scoreLabel = new Label("Score: " + Level.getScore());
	scoreLabel.setFont(new Font("Arial", 20));
	scoreLabel.toFront();
	score = new VBox(10);
	score.getChildren().addAll(scoreLabel);
	score.setTranslateX(screenSize.getWidth()-100);
	score.setTranslateY(10);

	//Options Root
	Text opTitle = new Text("Game Options");
	opTitle.setFont(Font.font("Arial", 40));
	VBox optionsBox = addOptionButtons(primaryStage);
	optionsBox.setAlignment(Pos.CENTER);
	optionsRoot = new BorderPane();
	optionsRoot.setId("menu");
	optionsRoot.setCenter(optionsBox);
	optionsRoot.setTop(opTitle);
	optionsRoot.setAlignment(opTitle, Pos.CENTER);

	//Game Options Root
	Text gameOpTitle = new Text("Game Options");
	gameOpTitle.setFont(Font.font("Arial", 40));
	VBox gameOptionsBox = addGameOptionsButtons(primaryStage);
	gameOptionsBox.setAlignment(Pos.CENTER);
	gameOptionsRoot = new BorderPane();
	gameOptionsRoot.setId("menu");
	gameOptionsRoot.setCenter(gameOptionsBox);
	gameOptionsRoot.setTop(gameOpTitle);
	gameOptionsRoot.setAlignment(gameOpTitle, Pos.CENTER);
        
        //Game Over Root
        Text gameOver = new Text("Game Over");
	gameOver.setFont(Font.font("Arial", 40));
	VBox gameOverBox = addGameOverButtons(primaryStage);
	gameOverBox.setAlignment(Pos.CENTER);
	gameOverRoot = new BorderPane();
	gameOverRoot.setId("menu");
	gameOverRoot.setCenter(gameOverBox);
	gameOverRoot.setTop(gameOver);
	gameOptionsRoot.setAlignment(gameOver, Pos.CENTER);

	//Exit Root
	exitRoot = new VBox(20);
	Label exitString = new Label("Are you sure you want to exit?");
	exitString.setFont(Font.font("Arial", 25));
	HBox exitButtons = new HBox(10);
	exitButtons.getChildren().addAll(yesExit, noExit);
	exitButtons.setAlignment(Pos.CENTER);
	exitRoot.getChildren().addAll(exitString, exitButtons);
	exitRoot.setId("menu");
	exitRoot.setAlignment(Pos.CENTER);

	//Are You Sure Root
	areYouSureRoot = new VBox(20);
	Label areYouSureString = new Label("Are you sure you want to return to the menu?");
	areYouSureString.setFont(Font.font("Arial", 25));
	HBox returnButtons = new HBox(10);
	returnButtons.getChildren().addAll(yesReturn, noReturn);
	returnButtons.setAlignment(Pos.CENTER);
	areYouSureRoot.getChildren().addAll(areYouSureString, returnButtons);
	areYouSureRoot.setId("menu");
	areYouSureRoot.setAlignment(Pos.CENTER);

	//Gameplay
	scene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
	scene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

	AnimationTimer timer = new AnimationTimer() {
	    @Override
	    public void handle(long now) {
		update(primaryStage);
	    }
	};
	timer.start();

	//Stage
	primaryStage.setTitle("The Awesome Game");
	primaryStage.setFullScreen(true);
	primaryStage.setFullScreenExitHint("");
	primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
	primaryStage.initStyle(StageStyle.UTILITY);
	primaryStage.setResizable(false);
	primaryStage.setScene(scene);
	primaryStage.show();

	primaryStage.setOnCloseRequest(e -> {
	    e.consume();
	    pause = true;
	    primaryStage.getScene().setRoot(exitRoot);

	    yesExit.setOnAction(eY -> {
		Platform.exit();
		gameplay = false;

		for (Enemy enemy : enemies) {
		    gameRoot.getChildren().removeAll(enemy);
		}
		enemies.clear();
	    });
	    noExit.setOnAction(eN -> {
		primaryStage.getScene().setRoot(gameRoot);
		pause = false;
	    });
	});
    }

    //This is where the gameplay is updated 
    public void update(Stage pStage) {
	long timeNow = System.currentTimeMillis();
	long time = timeNow - pauseTime;
	if (gameplay && !pause) {
	    if (player.getHealth() == 0) {
		//Platform.exit();
		pStage.getScene().setRoot(gameOverRoot); //need to change this to next level
		gameplay = false;
	    }
            
	    if(Level.getEnemiesLeft() == 0){
		Level.increaseLevel();
		level.increaseEnemies();
	    }
            
	    if (isPressed(KeyCode.W)) {
		player.setCharacterView(0, 183);
		player.moveY(-3, scene.getHeight());
		player.setOffsetY(183);
		characterShooting();

	    } else if (isPressed(KeyCode.S)) {
		player.setCharacterView(0, 0);
		player.moveY(3, scene.getHeight());
		player.setOffsetY(0);
		characterShooting();

	    } else if (isPressed(KeyCode.A)) {
		player.setCharacterView(0, 123);
		player.moveX(-3, scene.getWidth());
		player.setOffsetY(123);
		characterShooting();

	    } else if (isPressed(KeyCode.D)) {
		player.setCharacterView(0, 61);
		player.moveX(3, scene.getWidth());
		player.setOffsetY(61);
		characterShooting();

	    } else {
		player.setCharacterView(0, player.getOffsetY());
		characterShooting();
	    }
	    
	    while(portalCount < Level.getLevel()){
		createPortal();
                player.toFront();
		portalCount++;
	    }
	    
	    for(Portal portal : portals){
		if(portal.summon()){
		    createEnemy(portal);
		}
	    }

	    if (time < 0 || time > 150) {
		if (isPressed(KeyCode.ESCAPE)) {
		    pause = true;
		    pStage.getScene().setRoot(gameOptionsRoot);
		}
		pauseTime = timeNow;
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
		    gameRoot.getChildren().removeAll(enemy, enemy.healthBarOutline, enemy.lostHealth, enemy.actualHealth);
		}
		enemies.clear();
	    }
	} else if (pause) {
	    if (time < 0 || time > 150) {
		if (isPressed(KeyCode.ESCAPE)) {
		    pStage.getScene().setRoot(gameRoot);
		    pause = false;
		}
		pauseTime = timeNow;
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
		createProjectile(0, -9);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.DOWN)) {
	    player.setCharacterView(128, 0);
	    player.setOffsetY(0);
	    if (time < 0 || time > 500) {
		createProjectile(0, 9);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.LEFT)) {
	    player.setCharacterView(128, 123);
	    player.setOffsetY(123);
	    if (time < 0 || time > 500) {
		createProjectile(-9, 0);
		timeOfLastProjectile = timeNow;
	    }

	} else if (isPressed(KeyCode.RIGHT)) {
	    player.setCharacterView(128, 61);
	    player.setOffsetY(61);
	    if (time < 0 || time > 500) {
		createProjectile(9, 0);
		timeOfLastProjectile = timeNow;
	    }
	}
    }
    
    public void createPortal(){
	Image image = new Image("file:src/portal.png");
	ImageView iv = new ImageView(image);
	Portal portal = new Portal(iv, (int)scene.getWidth() - 36, (int)scene.getHeight() - 60);
	portal.toBack();
	gameRoot.getChildren().add(portal);
	portals.add(portal);
    }

    public void createProjectile(int x, int y) {
	Image image = new Image("file:src/shot.png");
	ImageView iv = new ImageView(image);
	Projectile proj = new Projectile(iv, player.getX() + 28, player.getY() + 16);
	proj.setVelocityX(x);
	proj.setVelocityY(y);

	gameRoot.getChildren().addAll(proj);
	proj.toBack();
	projectiles.add(proj);
    }

    public void updateProj(Projectile proj) {
	proj.move();

	for (Enemy enemy : enemies) {
	    if (proj.isColliding(enemy)) {
		enemy.hit();
		gameRoot.getChildren().remove(enemy.actualHealth);
		gameRoot.getChildren().add(enemy.updateHealth());
		proj.setAlive(false);
	    }
	}

	if (proj.getTranslateX() <= 0 || proj.getTranslateX() >= scene.getWidth()) {
	    proj.setAlive(false);
	} else if (proj.getTranslateY() <= 0 || proj.getTranslateY() >= scene.getHeight()) {
	    proj.setAlive(false);
	}

	if (!proj.isAlive()) {
	    gameRoot.getChildren().remove(proj);
	    projToRemove.add(proj);
	}
    }

    public void createEnemy(Portal portal) {
	Image image = new Image("file:src/Redies.png");
	ImageView iv = new ImageView(image);
	Enemy enemy = new Enemy(iv, portal.getX(), portal.getY(),3,1);

	gameRoot.getChildren().addAll(enemy, enemy.healthBarOutline, enemy.lostHealth, enemy.actualHealth);
	enemies.add(enemy);
        health.toFront();
        healthBarOutline.toFront();
        lostHealth.toFront();
        actualHealth.toFront();
    }

    public void updateEnemy(Enemy enemy) {
	long timeNow = System.currentTimeMillis();
	long time = timeNow - hitTime;
	if (enemy.playerColliding(player)) {
	    enemy.setCharacterView(128, 0);
	    if (time < 0 || time > 2000) {
		player.hit();
		gameRoot.getChildren().remove(actualHealth);
		actualHealth = new Rectangle(80, 10, player.getHealth() * 20, 21);
		actualHealth.setFill(Color.GREEN);
		gameRoot.getChildren().add(actualHealth);
                actualHealth.toFront();
		hitTime = timeNow;
	    }
	}

	if (!enemy.playerColliding(player)) {//&&!enemy.enemyColliding(enemies)) { //need to fix this
	    if (player.getX() > enemy.getX() && player.getY() == enemy.getY()) { //right
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, scene.getWidth());
	    }
	    if (player.getX() < enemy.getX() && player.getY() == enemy.getY()) { //left
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, scene.getWidth());
	    }
	    if (player.getX() == enemy.getX() && player.getY() > enemy.getY()) { //down
		enemy.setCharacterView(0, 0);
		enemy.moveY(1, scene.getHeight());
	    }
	    if (player.getX() == enemy.getX() && player.getY() < enemy.getY()) { //up
		enemy.setCharacterView(0, 183);
		enemy.moveY(-1, scene.getHeight());
	    }

	    if (player.getX() > enemy.getX() && player.getY() < enemy.getY()) { //quadrant1
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, scene.getWidth());
		enemy.moveY(-1, scene.getHeight());
	    }
	    if (player.getX() < enemy.getX() && player.getY() < enemy.getY()) { //quadrant2
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, scene.getWidth());
		enemy.moveY(-1, scene.getHeight());
	    }
	    if (player.getX() > enemy.getX() && player.getY() > enemy.getY()) { //quadrant3
		enemy.setCharacterView(0, 61);
		enemy.moveX(1, scene.getWidth());
		enemy.moveY(1, scene.getHeight());
	    }
	    if (player.getX() < enemy.getX() && player.getY() > enemy.getY()) { //quadrant4
		enemy.setCharacterView(0, 123);
		enemy.moveX(-1, scene.getWidth());
		enemy.moveY(1, scene.getHeight());
	    }
	}
        
	if (enemy.getHealth() == 0) {
	    enemy.setAlive(false);
	}
	if (!enemy.isAlive()) {
	    enemToRemove.add(enemy);
	    gameRoot.getChildren().removeAll(enemy, enemy.actualHealth, enemy.lostHealth, enemy.healthBarOutline);
	    Level.enemyBeat();
	    Level.scoreUp(enemy);
	    score.getChildren().clear();
	    score.getChildren().add(Level.updateScore());
	}
    }

    public boolean isPressed(KeyCode key) {
	return keys.getOrDefault(key, false);
    }

    public void clearAll() {
	projectiles.clear();
	projToRemove.clear();
	enemies.clear();
	enemToRemove.clear();
        portals.clear();
        portalCount = 0;
	Level.clearScore();
	score.getChildren().clear();
	score.getChildren().add(Level.updateScore());
	gameRoot.getChildren().clear();
    }

    //Button Layouts
    public VBox addMenuButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);

	Button startBtn = new Button("Start");
	startBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(gameRoot);
            level = new Level();
	    player = new Character(charIV, (int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
	    gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth,score);
            health.toFront();
	    score.toFront();
            healthBarOutline.toFront();
            lostHealth.toFront();
            actualHealth.toFront();
	    gameplay = true;
	});

	Button optionsBtn = new Button("Options");
	optionsBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(optionsRoot);
	});

	Button exitBtn = new Button("Exit");
	exitBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(exitRoot);

	    yesExit.setOnAction(eY -> {
		Platform.exit();
	    });
	    noExit.setOnAction(eN -> {
		pStage.getScene().setRoot(menuRoot);
	    });
	});

	vbox.getChildren().addAll(startBtn, optionsBtn, exitBtn);
	return vbox;
    }

    public VBox addOptionButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);

	CheckBox musicBox = new CheckBox("Music");
	musicBox.setSelected(false);
	musicBox.setOnAction(e -> {

	});

	Button backBtn = new Button("Back to Menu");
	backBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(menuRoot);
	});

	vbox.getChildren().addAll(musicBox, backBtn);
	return vbox;
    }

    public VBox addGameOptionsButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);

	CheckBox musicBox = new CheckBox("Music");
	musicBox.setSelected(false);
	musicBox.setOnAction(e -> {

	});

	Button gameBtn = new Button("Back to Game");
	gameBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(gameRoot);
	    pause = false;
	});

	Button backBtn = new Button("Back to Menu");
	backBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(areYouSureRoot);

	    yesReturn.setOnAction(eY -> {
		pStage.getScene().setRoot(menuRoot);
		clearAll();
		actualHealth = new Rectangle(80, 10, 99, 21);
		actualHealth.setFill(Color.GREEN);
		gameplay = false;
		pause = false;
	    });
	    noReturn.setOnAction(eN -> {
		pStage.getScene().setRoot(gameOptionsRoot);
	    });
	});

	Button exitBtn = new Button("Quit");
	exitBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(exitRoot);

	    yesExit.setOnAction(eY -> {
		Platform.exit();
		gameplay = false;
		clearAll();
	    });
	    noExit.setOnAction(eN -> {
		pStage.getScene().setRoot(gameOptionsRoot);
	    });
	});

	vbox.getChildren().addAll(musicBox, gameBtn, backBtn, exitBtn);
	return vbox;
    }

    public VBox addGameOverButtons(Stage pStage) {
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(15));
	vbox.setSpacing(10);
        
        Button newBtn = new Button("New Game");
        newBtn.setOnAction(e -> {
            pStage.getScene().setRoot(gameRoot);
            clearAll();
            level = new Level();
            actualHealth = new Rectangle(80, 10, 99, 20);
            actualHealth.setFill(Color.GREEN);
            player = new Character(charIV, (int)screenSize.getWidth() / 2, (int)screenSize.getHeight() / 2);
	    gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth);
            health.toFront();
            healthBarOutline.toFront();
            lostHealth.toFront();
            actualHealth.toFront();
	    gameplay = true;
	});

	Button backBtn = new Button("Back to Menu");
	backBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(areYouSureRoot);

	    yesReturn.setOnAction(eY -> {
		pStage.getScene().setRoot(menuRoot);
		clearAll();
		actualHealth = new Rectangle(80, 10, 99, 20);
		actualHealth.setFill(Color.GREEN);
		gameplay = false;
		pause = false;
	    });
	    noReturn.setOnAction(eN -> {
		pStage.getScene().setRoot(gameOptionsRoot);
	    });
	});

	Button exitBtn = new Button("Quit");
	exitBtn.setOnAction(e -> {
	    pStage.getScene().setRoot(exitRoot);

	    yesExit.setOnAction(eY -> {
		Platform.exit();
		gameplay = false;
		clearAll();
	    });
	    noExit.setOnAction(eN -> {
		pStage.getScene().setRoot(gameOptionsRoot);
	    });
	});
        
	vbox.getChildren().addAll(newBtn, backBtn, exitBtn);
	return vbox;
    }

    public static void main(String[] args) {
	launch(args);
    }
}
