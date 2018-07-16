
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Main extends Application {

    Scene scene;
    static Pane gameRoot;
    static BorderPane menuRoot;
    static BorderPane shopRoot;
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

    Character player;
    Level level;
    Stairs toShopStair;
    Stairs decUpStair;
    Stairs toGameStair;

    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projToRemove = new ArrayList<>();
    private long timeOfLastProjectile = 0;
    
    private List<Projectile> enemyProj = new ArrayList<>();
    private List<Projectile> enemyProjToRemove = new ArrayList<>();

    private List<Enemy> enemies = new ArrayList();
    private List<Enemy> enemToRemove = new ArrayList();
    private long hitTime = 0;

    private List<Enemy> bosses = new ArrayList();

    private List<Portal> portals = new ArrayList();
    private int portalCount = 0;

    private List<Upgrades> shopUpgrades = new ArrayList();
    private List<Upgrades> upgradesToRemove = new ArrayList();
    private List<Upgrades> currentUpgrades = new ArrayList();
    HBox shopBox;
    private long shopTime = 0;

    Rectangle healthBarOutline;
    Rectangle actualHealth;
    Rectangle lostHealth;
    Rectangle shieldHealth;
    VBox health;
    VBox coinAndScore;
    Label coinLabel;
    Label scoreLabel;

    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    boolean gameplay = false;
    boolean pause = false;
    boolean couldGoToShop = true;
    boolean couldGoToMap = false;
    boolean shieldAdded = false;
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
        BorderPane.setAlignment(title, Pos.CENTER);

        scene = new Scene(menuRoot, screenSize.getWidth(), screenSize.getHeight());
        scene.getStylesheets().addAll(this.getClass().getResource("Design.css").toExternalForm());

        //Game Root
        gameRoot = new Pane();
        gameRoot.setId("backgroundgame");
        Label healthLabel = new Label("Health: ");
        healthLabel.setFont(new Font("Arial", 20));
        healthLabel.setTextFill(Color.WHITE);
        healthLabel.toFront();
        healthBarOutline = new Rectangle(screenSize.getWidth() - 121, 9, 102, 22);
        healthBarOutline.setFill(Color.TRANSPARENT);
        healthBarOutline.setStroke(Color.BLACK);
        lostHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
        lostHealth.setFill(Color.RED);
        actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
        actualHealth.setFill(Color.web("#00F32C"));
        shieldHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
        shieldHealth.setFill(Color.web("#00E8FF"));
        health = new VBox(10);
        health.getChildren().addAll(healthLabel);
        health.setTranslateX(screenSize.getWidth() - 200);
        health.setTranslateY(10);
        coinLabel = new Label("Coins: ");
        coinLabel.setFont(new Font("Arial", 20));
        coinLabel.setTextFill(Color.WHITE);
        scoreLabel = new Label("Score: ");
        scoreLabel.setFont(new Font("Arial", 20));
        scoreLabel.setTextFill(Color.WHITE);
        coinAndScore = new VBox(10);
        coinAndScore.getChildren().addAll(coinLabel, scoreLabel);
        coinAndScore.setTranslateX(10);
        coinAndScore.setTranslateY(10);

        //Shop Root
        shopRoot = new BorderPane();
        shopRoot.setId("backgroundshop");
        decUpStair = new Stairs("up", (int) screenSize.getWidth(), (int) screenSize.getHeight());
        toGameStair = new Stairs("shop", (int) screenSize.getWidth() - 100, (int) screenSize.getHeight() - 100);

        //Options Root
        Text opTitle = new Text("Game Options");
        opTitle.setFont(Font.font("Arial", 40));
        VBox optionsBox = addOptionButtons(primaryStage);
        optionsBox.setAlignment(Pos.CENTER);
        optionsRoot = new BorderPane();
        optionsRoot.setId("menu");
        optionsRoot.setCenter(optionsBox);
        optionsRoot.setTop(opTitle);
        BorderPane.setAlignment(opTitle, Pos.CENTER);

        //Game Options Root
        Text gameOpTitle = new Text("Game Options");
        gameOpTitle.setFont(Font.font("Arial", 40));
        VBox gameOptionsBox = addGameOptionsButtons(primaryStage);
        gameOptionsBox.setAlignment(Pos.CENTER);
        gameOptionsRoot = new BorderPane();
        gameOptionsRoot.setId("menu");
        gameOptionsRoot.setCenter(gameOptionsBox);
        gameOptionsRoot.setTop(gameOpTitle);
        BorderPane.setAlignment(gameOpTitle, Pos.CENTER);

        //Game Over Root
        VBox gameOverBox = addGameOverButtons(primaryStage);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverRoot = new BorderPane();
        gameOverRoot.setId("menu");
        gameOverRoot.setCenter(gameOverBox);

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
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
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
                Text gameOver = new Text("Game Over \n Score:  " + level.getScore());
                gameOver.setFont(Font.font("Arial", 40));
                gameOverRoot.setTop(gameOver);
                BorderPane.setAlignment(gameOver, Pos.CENTER);
                pStage.getScene().setRoot(gameOverRoot);
                gameplay = false;
            }

            if (isPressed(KeyCode.W)) {
                player.setCharacterView(0, 183);
                player.moveY(-player.getPlayerSpeed(), scene.getHeight());
                player.setOffsetY(183);
                characterShooting();

            } else if (isPressed(KeyCode.S)) {
                player.setCharacterView(0, 0);
                player.moveY(player.getPlayerSpeed(), scene.getHeight());
                player.setOffsetY(0);
                characterShooting();

            } else if (isPressed(KeyCode.A)) {
                player.setCharacterView(0, 123);
                player.moveX(-player.getPlayerSpeed(), scene.getWidth());
                player.setOffsetY(123);
                characterShooting();

            } else if (isPressed(KeyCode.D)) {
                player.setCharacterView(0, 61);
                player.moveX(player.getPlayerSpeed(), scene.getWidth());
                player.setOffsetY(61);
                characterShooting();

            } else {
                player.setCharacterView(0, player.getOffsetY());
                characterShooting();
            }

            while (portalCount < level.getLevel()) {
                createPortal();
                player.toFront();
                portalCount++;
            }

            for (Portal portal : portals) {
                if (portal.summon() && !level.isShopping() && level.getEnemiesSpawned() < level.getEnemiesToBeat()) {
                    //    if (level.getEnemiesLeft() == 1 && bosses.size() >= level.getLevel()) {//bosses.size part is temp so game doesnt crash after we run out of bosses
                    //	createBoss(portal);
                    //   } else {
                    //	if (level.getEnemiesToBeat() - level.getEnemiesSpawned() != 1||bosses.size() < level.getLevel()) {
                    createEnemy(portal);
                    //	}
                    //    }
                }
            }

            shoppingUpdate(pStage);
            shieldUpdate();

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
            for (Projectile proj : enemyProj) {
                updateEnemyProj(proj);
            }
            for (Enemy enemy : enemies) {
                updateEnemy(enemy);
            }

            projectiles.removeAll(projToRemove);
            projToRemove.clear();
            
            enemyProj.removeAll(enemyProjToRemove);
            enemyProjToRemove.clear();

            enemies.removeAll(enemToRemove);
            enemToRemove.clear();

            shopUpgrades.removeAll(upgradesToRemove);
            upgradesToRemove.clear();

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
                    if (!level.isShopping()) {
                        pStage.getScene().setRoot(gameRoot);
                    } else {
                        pStage.getScene().setRoot(shopRoot);
                    }
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
            if (time < 0 || time > player.getShootSpeed()) {
                createProjectile(0, -9);
                timeOfLastProjectile = timeNow;
            }

        } else if (isPressed(KeyCode.DOWN)) {
            player.setCharacterView(128, 0);
            player.setOffsetY(0);
            if (time < 0 || time > player.getShootSpeed()) {
                createProjectile(0, 9);
                timeOfLastProjectile = timeNow;
            }

        } else if (isPressed(KeyCode.LEFT)) {
            player.setCharacterView(128, 123);
            player.setOffsetY(123);
            if (time < 0 || time > player.getShootSpeed()) {
                createProjectile(-9, 0);
                timeOfLastProjectile = timeNow;
            }

        } else if (isPressed(KeyCode.RIGHT)) {
            player.setCharacterView(128, 61);
            player.setOffsetY(61);
            if (time < 0 || time > player.getShootSpeed()) {
                createProjectile(9, 0);
                timeOfLastProjectile = timeNow;
            }
        }
    }

    public void createPortal() {
        Portal portal = new Portal((int) scene.getWidth() - 36, (int) scene.getHeight() - 60);
        gameRoot.getChildren().add(portal);
        portal.toBack();
        portals.add(portal);
    }

    public void createProjectile(int x, int y) {
        Projectile proj = new Projectile("file:src/Sprites/Shot.png", player.getX() + 28, player.getY() + 16, 12, 12);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        gameRoot.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }

    public void updateProj(Projectile proj) {
        proj.move();

        for (Enemy enemy : enemies) {
            if (proj.enemyColliding(enemy)) {
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
    
    public void updateEnemyProj(Projectile proj){
        long timeNow = System.currentTimeMillis();
        long time = timeNow - hitTime;
        
        if(proj.playerColliding(player)){ //create enemy proj class
            proj.setAlive(false);
            if (time < 0 || time > 1000) {
                player.hit();

                if (player.hasShield()) {
                    gameRoot.getChildren().remove(shieldHealth);
                    shieldHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getShieldHealth() * 33 + 1, 22);
                    shieldHealth.setFill(Color.web("#00E8FF"));
                    gameRoot.getChildren().add(shieldHealth);
                    shieldHealth.toFront();
                } else {
                    gameRoot.getChildren().remove(actualHealth);
                    actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getHealth() * 20, 22);
                    actualHealth.setFill(Color.web("#00F32C"));
                    gameRoot.getChildren().add(actualHealth);
                    actualHealth.toFront();
                }
                
                hitTime = timeNow;
            }
        }
        
        if (!proj.playerColliding(player)) {
            proj.move();
        }
        
        if (proj.getTranslateX() <= 0 || proj.getTranslateX() >= scene.getWidth()) {
            proj.setAlive(false);
        } else if (proj.getTranslateY() <= 0 || proj.getTranslateY() >= scene.getHeight()) {
            proj.setAlive(false);
        }

        if (!proj.isAlive()) {
            gameRoot.getChildren().remove(proj);
            enemyProjToRemove.add(proj);
        }
    }

    public void createBoss(Portal portal) {
        Enemy enemy = bosses.get(level.getLevel() - 1);
        enemy.summon(portal);
        gameRoot.getChildren().addAll(enemy, enemy.healthBarOutline, enemy.lostHealth, enemy.actualHealth);
        coinAndScore.toFront();
        coinLabel.toFront();
        scoreLabel.toFront();
        enemies.add(enemy);
        health.toFront();
        healthBarOutline.toFront();
        lostHealth.toFront();
        actualHealth.toFront();
        level.enemySpawned();
        if (player.hasShield()) {
            shieldHealth.toFront();
        }
    }

    public void createEnemy(Portal portal) {
        Enemy enemy = level.generate();
        enemy.summon(portal);
        gameRoot.getChildren().addAll(enemy, enemy.healthBarOutline, enemy.lostHealth, enemy.actualHealth);
        coinAndScore.toFront();
        coinLabel.toFront();
        scoreLabel.toFront();
        enemies.add(enemy);
        health.toFront();
        healthBarOutline.toFront();
        lostHealth.toFront();
        actualHealth.toFront();
        level.enemySpawned();
        if (player.hasShield()) {
            shieldHealth.toFront();
        }
    }

    public void updateEnemy(Enemy enemy) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - hitTime;
        if (enemy.playerColliding(player)) {
            enemy.hitView(enemy);
            if (time < 0 || time > 1000) {
                player.hit();

                if (player.hasShield()) {
                    gameRoot.getChildren().remove(shieldHealth);
                    shieldHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getShieldHealth() * 33 + 1, 22);
                    shieldHealth.setFill(Color.web("#00E8FF"));
                    gameRoot.getChildren().add(shieldHealth);
                    shieldHealth.toFront();
                } else {
                    gameRoot.getChildren().remove(actualHealth);
                    actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getHealth() * 20, 22);
                    actualHealth.setFill(Color.web("#00F32C"));
                    gameRoot.getChildren().add(actualHealth);
                    actualHealth.toFront();
                }

                hitTime = timeNow;
            }
        }

        if (!enemy.playerColliding(player)) {
            enemy.move(player, scene.getWidth(), scene.getHeight());
        }
        
        enemy.shoot(player, enemyProj, gameRoot);

        if (enemy.getHealth() == 0) {
            enemy.setAlive(false);
        }
        if (!enemy.isAlive()) {
            enemToRemove.add(enemy);
            gameRoot.getChildren().removeAll(enemy, enemy.actualHealth, enemy.lostHealth, enemy.healthBarOutline);
            level.enemyBeat();
            level.coinUp(enemy);
            level.scoreUp(enemy);
            coinLabel.setText("Coins: " + level.getCoin());
            scoreLabel.setText("Score: " + level.getScore());
        }
    }

    public void shieldUpdate() {
        if (player.hasShield()) {
            if (!shieldAdded) {
                shieldAdded = true;
                shieldHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
                shieldHealth.setFill(Color.web("#00E8FF"));
                shopRoot.getChildren().add(shieldHealth);
                shieldHealth.toFront();
            }
            if (player.getShieldHealth() == 0) {
                player.addShield(false);
                shieldAdded = false;
                gameRoot.getChildren().remove(shieldHealth);
            }
        }
    }

    public void shoppingUpdate(Stage pStage) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - shopTime;
        if (level.isShopping()) {
            for (Upgrades upgrade : shopUpgrades) {
                if (upgrade.isColliding(player) && isPressed(KeyCode.ENTER)) {
                    if (time < 0 || time > 150) {
                        if (level.getCoin() >= upgrade.getPrice()) {
                            upgradesToRemove.add(upgrade);
                            shopBox.getChildren().remove(upgrade);
                            currentUpgrades.add(upgrade);
                            upgrade.setBought(true);
                            level.spend(upgrade.getPrice());
                            coinLabel.setText("Coins: " + level.getCoin());
                            shopTime = timeNow;
                        }
                    }
                }
            }
            for (Upgrades upgrade : currentUpgrades) {
                if (!upgrade.isActive()) {
                    upgrade.activeAbility(player);
                    upgrade.setActive(true);
                    shopRoot.getChildren().remove(actualHealth);
                    actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getHealth() * 20, 22);
                    actualHealth.setFill(Color.web("#00F32C"));
                    shopRoot.getChildren().add(actualHealth);
                    actualHealth.toFront();
                    if (player.hasShield()) {
                        shieldHealth.toFront();
                    }
                }
            }
            if (player.isColliding(toGameStair) && couldGoToMap) {
                shopRoot.getChildren().clear();
                gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth, coinAndScore);
                if (player.hasShield()) {
                    gameRoot.getChildren().addAll(shieldHealth);
                }
                for (Portal port : portals) {
                    gameRoot.getChildren().addAll(port);
                }
                couldGoToShop = true;
                couldGoToMap = false;
                level.increaseLevel();
                level.setShopping(false);
                pStage.getScene().setRoot(gameRoot);
            }

            //need to add here a way to go to next level if player does not want to go to shop!!!
        }

        if (level.getEnemiesLeft() <= 0) {
            if (!level.isShopping()) {
                toShopStair = new Stairs("down", (int) scene.getWidth(), (int) scene.getHeight());
                gameRoot.getChildren().add(toShopStair);
                level.setShopping(true);
            }
            if (player.isColliding(toShopStair)) {
                pStage.getScene().setRoot(shopRoot);
                if (couldGoToShop) {
                    gameRoot.getChildren().clear();
                    shopRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth, 
                                                  coinAndScore, decUpStair, toGameStair);
                    shopRoot.setCenter(shopBox);
                    if (player.hasShield()) {
                        shopRoot.getChildren().addAll(shieldHealth);
                    }
                    couldGoToShop = false;
                    couldGoToMap = true;
                }
                gameRoot.getChildren().remove(toShopStair);
            }
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
        bosses.clear();
        portals.clear();
        portalCount = 0;
        currentUpgrades.clear();
        upgradesToRemove.clear();
        shopUpgrades.clear();
        level.clearScore();
        level.clearCoins();
        level.setShopping(false);
        coinLabel.setText("Coins: " + level.getCoin());
        scoreLabel.setText("Score: " + level.getScore());
        gameRoot.getChildren().clear();
        shopRoot.getChildren().clear();
    }

    public void newGame() {
        level = new Level();
        player = new Character((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
        actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
        actualHealth.setFill(Color.web("#00F32C"));
        gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth, coinAndScore);
        shopBox = addShopButtons();
        shopBox.setAlignment(Pos.CENTER);
        coinAndScore.toFront();
        coinLabel.toFront();
        scoreLabel.toFront();
        health.toFront();
        healthBarOutline.toFront();
        lostHealth.toFront();
        actualHealth.toFront();
        player.addShield(false);
        gameplay = true;
        couldGoToShop = true;
        couldGoToMap = false;
        shieldAdded = false;
        level.fillBoss(bosses);
    }

    //Button Layouts
    public VBox addMenuButtons(Stage pStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);

        Button startBtn = new Button("Start");
        startBtn.setOnAction(e -> {
            pStage.getScene().setRoot(gameRoot);
            clearAll();
            newGame();
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
            if (!level.isShopping()) {
                pStage.getScene().setRoot(gameRoot);
            } else {
                pStage.getScene().setRoot(shopRoot);
            }
            pause = false;
        });

        Button backBtn = new Button("Back to Menu");
        backBtn.setOnAction(e -> {
            pStage.getScene().setRoot(areYouSureRoot);

            yesReturn.setOnAction(eY -> {
                pStage.getScene().setRoot(menuRoot);
                clearAll();
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

    public HBox addShopButtons() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30));
        hbox.setSpacing(70);

        HealthPackUpgrade healthUp = new HealthPackUpgrade();
        PlayerShieldUpgrade shield = new PlayerShieldUpgrade();
        ShootSpeedUpgrade shoot = new ShootSpeedUpgrade();
        PlayerSpeedUpgrade speed = new PlayerSpeedUpgrade();
        shopUpgrades.add(healthUp);
        shopUpgrades.add(shield);
        shopUpgrades.add(shoot);
        shopUpgrades.add(speed);

        hbox.getChildren().addAll(healthUp, shield, shoot, speed);
        return hbox;
    }

    public VBox addGameOverButtons(Stage pStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);

        Button newBtn = new Button("New Game");
        newBtn.setOnAction(e -> {
            pStage.getScene().setRoot(gameRoot);
            clearAll();
            newGame();
        });

        Button backBtn = new Button("Back to Menu");
        backBtn.setOnAction(e -> {
            pStage.getScene().setRoot(areYouSureRoot);

            yesReturn.setOnAction(eY -> {
                pStage.getScene().setRoot(menuRoot);
                clearAll();
                gameplay = false;
                pause = false;
            });
            noReturn.setOnAction(eN -> {
                pStage.getScene().setRoot(gameOverRoot);
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
                pStage.getScene().setRoot(gameOverRoot);
            });
        });

        vbox.getChildren().addAll(newBtn, backBtn, exitBtn);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
