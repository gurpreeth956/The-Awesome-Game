package Game;
import Enemies.*;
import Environment.*;
import Friends.*;
import Projectiles.*;
import Upgrades.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Main extends Application {

    Scene scene;
    static Pane gameRoot, shopRoot, currentRoot;
    static BorderPane menuRoot, shopBuyingRoot, optionsRoot, gameOptionsRoot, gameOverRoot;
    static VBox areYouSureRoot, exitRoot;

    private final HashMap<KeyCode, Boolean> keys = new HashMap();
    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    Button yesExit = new Button("Yes");
    Button noExit = new Button("No");
    Button yesReturn = new Button("Yes");
    Button noReturn = new Button("No");

    Character player;
    Level level;
    Stairs toShopStair, decUpStair, toGameStair;
    Friends shopKeeper;

    public static List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projToRemove = new ArrayList<>();
    private long timeOfLastProjectile = 0;

    private List<Projectile> enemyProj = new ArrayList<>();
    private List<Projectile> enemyProjToRemove = new ArrayList<>();

    private List<Enemy> enemies = new ArrayList();
    private List<Enemy> enemToRemove = new ArrayList();
    private long hitTime = 0, spikeHitTime = 0;

    private List<Enemy> bosses = new ArrayList();

    private List<Portal> portals = new ArrayList();
    private int portalCount = 0;

    private List<Upgrades> shopUpgrades = new ArrayList();
    private List<Upgrades> upgradesToRemove = new ArrayList();
    private List<Upgrades> currentUpgrades = new ArrayList();
    private ListView<String> shopUpgradesView = new ListView<>();

    Rectangle healthBarOutline, actualHealth, lostHealth, shieldHealth;
    Label coinLabel, scoreLabel, shopBuyingHealthLabel, shopBuyingShieldLabel, shopBuyingCoinLabel, 
        shopBuyingScoreLabel;
    VBox health, coinAndScore;
    
    boolean gameplay = false, pause = false, shieldAdded = false, couldGoToShop = true, 
        couldGoToMap = false, addShopStair = true, inShopBuyingView = false;
    private long pauseTime = 0;

    
    //Main
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        createRoots(primaryStage);
        
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
            
            currentRoot = (Pane)primaryStage.getScene().getRoot();
            if (!currentRoot.equals(exitRoot)) {
                primaryStage.getScene().setRoot(exitRoot);
                
                yesExit.setOnAction(eY -> {
                    Platform.exit();
                    if (gameplay) clearAll();
                    gameplay = false;
                });
                noExit.setOnAction(eN -> {
                    primaryStage.getScene().setRoot(currentRoot);
                    pause = false;
                });
            }
        });
    }
    //Main
    
    
    //Gameplay
    public void update(Stage pStage) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - pauseTime;

        if (gameplay && !pause) {
            if (player.getHealth() == 0) {
                Text gameOver = new Text("Game Over \n Score:  " + level.getScore());
                gameOver.setFont(Font.font("Arial", 50));
                gameOverRoot.setTop(gameOver);
                BorderPane.setAlignment(gameOver, Pos.CENTER);
                BorderPane.setMargin(gameOver, new Insets(100));
                pStage.getScene().setRoot(gameOverRoot);
                gameplay = false;
            }

            //Controls
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
            
            //Updates
            while (portalCount < level.getLevel()) {
                createPortal();
                player.toFront();
                portalCount++;
            }

            for (Portal portal : portals) {
                if (level.getEnemiesSpawned() < level.getEnemiesToBeat() && portal.summon() && 
                    !level.isShopping()) {
                    if (level.getEnemiesLeft() == 1 && bosses.size() >= level.getLevel()) {
                        //bosses.size part is temp so game doesnt crash after we run out of bosses
                        createBoss(portal);
                    } else {
                        if (level.getEnemiesToBeat() - level.getEnemiesSpawned() != 1 || 
                            bosses.size() < level.getLevel()) {
                            createEnemy(portal);
                        }
                    }
                }
            }

            shoppingUpdate(pStage);
            shieldUpdate();

            if (time < 0 || time > 150) {
                if (isPressed(KeyCode.ESCAPE)) {
                    pause = true;
                    currentRoot = (Pane)pStage.getScene().getRoot();
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
            for (Spikes spike : Spikes.spikes) {
                updateSpikes(spike);
            }

            projectiles.removeAll(projToRemove);
            projToRemove.clear();

            enemyProj.removeAll(enemyProjToRemove);
            enemyProjToRemove.clear();

            enemies.removeAll(enemToRemove);
            enemToRemove.clear();
            
            //lists from spikes class
            Spikes.spikes.removeAll(Spikes.spikeToRemove);
            Spikes.spikeToRemove.clear();

            shopUpgrades.removeAll(upgradesToRemove);
            upgradesToRemove.clear();
            
        } else if (pause) {
            if (time < 0 || time > 150) {
                if (isPressed(KeyCode.ESCAPE)) {
                    pStage.getScene().setRoot(currentRoot);
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
        Projectile proj = new Projectile("file:src/Sprites/Shot.png", player.getX() + 28, 
            player.getY() + 16, 12, 12);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        gameRoot.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }

    public void updateProj(Projectile proj) {
        proj.move(player);

        for (Enemy enemy : enemies) {
            if (proj.enemyColliding(enemy)) {
                enemy.hit();
                gameRoot.getChildren().remove(enemy.getActualHealth());
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

    public void updateEnemyProj(Projectile proj) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - hitTime;
        
        if (proj.playerColliding(player)) { //create enemy proj class !note!
            proj.setAlive(false);
            if (time < 0 || time > 1000) {
                player.hit();
                playerReceiveHit();
                hitTime = timeNow;
            }
        }
        
        if (!proj.playerColliding(player)) {
            proj.move(player);
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
    
    public void updateSpikes(Spikes spike) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - spikeHitTime;
        
        if (spike.playerShotColliding(projectiles)) {
            Spikes.spikeToRemove.add(spike);
            gameRoot.getChildren().removeAll(spike);
        }
        
        if (spike.playerColliding(player) && !level.isShopping()) {
            if (time < 0 || time > 500) {
                player.hit();
                playerReceiveHit();
                Spikes.spikeToRemove.add(spike);
                gameRoot.getChildren().removeAll(spike);
                spikeHitTime = timeNow;
            }
        }
    }

    public void createBoss(Portal portal) {
        Enemy enemy = bosses.get(level.getLevel() - 1);
        enemy.summon(portal);
        gameRoot.getChildren().addAll(enemy, enemy.getHealthBarOutline(), enemy.getLostHealth(), 
            enemy.getActualHealth());
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
    
    public void updateBoss(Boss boss) {
        boss.update(gameRoot);
    }

    public void createEnemy(Portal portal) {
        Enemy enemy = level.generate();
        enemy.summon(portal);
        gameRoot.getChildren().addAll(enemy, enemy.getHealthBarOutline(), enemy.getLostHealth(), 
            enemy.getActualHealth());
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
                playerReceiveHit();
                hitTime = timeNow;
            }
        }

        if (!enemy.playerColliding(player)) {
            enemy.move(player, scene.getWidth(), scene.getHeight());
        }

        enemy.shoot(player, enemyProj, gameRoot);
        enemy.update(gameRoot);

        if (enemy.getHealth() <= 0) {
            enemy.update(gameRoot);
            enemy.setAlive(false);
        }
        if (!enemy.isAlive()) {
            enemToRemove.add(enemy);
            gameRoot.getChildren().removeAll(enemy, enemy.getActualHealth(), enemy.getLostHealth(),
                enemy.getHealthBarOutline());
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
    
    public void playerReceiveHit() {
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
    }
    //Gameplay
    
    
    //Shop
    public void shoppingUpdate(Stage pStage) {
        //Shopping
        if (level.isShopping()) {
            if (player.isColliding(shopKeeper) && isPressed(KeyCode.ENTER)) {
                pStage.getScene().setRoot(shopBuyingRoot);
                updateShopBuyingRoot();
                inShopBuyingView = true;
            }
            
            for (Upgrades upgrade : shopUpgrades) {
                if (upgrade.getBought()) {
                    upgradesToRemove.add(upgrade);
                    currentUpgrades.add(upgrade);
                    level.spend(upgrade.getPrice());
                    coinLabel.setText("Coins: " + level.getCoin());
                }
            }
            
            for (Upgrades upgrade : currentUpgrades) {
                if (!upgrade.isActive()) {
                    upgrade.activeAbility(player);
                    upgrade.setActive(true);
                    updateShopBuyingRoot();
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
                gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, 
                    actualHealth, coinAndScore);
                
                if (player.hasShield()) {
                    gameRoot.getChildren().addAll(shieldHealth);
                }
                for (Portal port : portals) {
                    gameRoot.getChildren().addAll(port);
                }
                
                couldGoToShop = true;
                couldGoToMap = false;
                addShopStair = true;
                level.increaseLevel();
                level.setShopping(false);
                pStage.getScene().setRoot(gameRoot);
                currentRoot = gameRoot;
            }
        }
        
        //Round End
        if (level.getEnemiesLeft() <= 0) {
            if (!level.isShopping() && addShopStair) {
                toShopStair = new Stairs("down", (int) scene.getWidth(), (int) scene.getHeight());
                gameRoot.getChildren().add(toShopStair);
                addShopStair = false;
            }
            
            if (player.isColliding(toShopStair)) {
                level.setShopping(true);
                for (Spikes spike : Spikes.spikes) {
                    Spikes.spikeToRemove.add(spike);
                }
                pStage.getScene().setRoot(shopRoot);
                currentRoot = shopRoot;
                
                if (couldGoToShop) {
                    gameRoot.getChildren().clear();
                    shopRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, 
                        actualHealth, coinAndScore, decUpStair, toGameStair, shopKeeper);
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
    
    HealthPackUpgrade healthUp;
    PlayerShieldUpgrade shieldUp;
    ShootSpeedUpgrade shootUp;
    PlayerSpeedUpgrade speedUp;
    
    public void addShopButtons() {
        healthUp = new HealthPackUpgrade();
        shieldUp = new PlayerShieldUpgrade();
        shootUp = new ShootSpeedUpgrade();
        speedUp = new PlayerSpeedUpgrade();
        shopUpgrades.add(healthUp);
        shopUpgrades.add(shieldUp);
        shopUpgrades.add(shootUp);
        shopUpgrades.add(speedUp);

        for (Upgrades upgrade : shopUpgrades) {
            shopUpgradesView.getItems().addAll(upgrade.getListView());
        }
        
        //Add Icons
        shopUpgradesView.setCellFactory(e -> new ListCell<String>() {
            private ImageView iv = new ImageView();
            
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (name.equals("Health Pack   -   " + healthUp.getPrice())) {
                        iv.setImage(healthUp.getImage());
                    } else if (name.equals("Add Shield   -   " + shieldUp.getPrice())) {
                        iv.setImage(shieldUp.getImage());
                    } else if (name.equals("Player Speed   -   " + speedUp.getPrice())) {
                        iv.setImage(speedUp.getImage());
                    } else if (name.equals("Shooting Speed   -   " + shootUp.getPrice())) {
                        iv.setImage(shootUp.getImage());
                    }
                    setText(name);
                    setGraphic(iv);
                }
            }
        });
        
        shopBuyingRoot.setCenter(shopUpgradesView);
        BorderPane.setAlignment(shopUpgradesView, Pos.TOP_CENTER);
        BorderPane.setMargin(shopUpgradesView, new Insets(10));
    }
    
    public void updateShopBuyingRoot() {
        shopBuyingHealthLabel.setText("Health: " + (player.getHealth() * 20) + "%");
        shopBuyingShieldLabel.setText("Shield: " + (player.getShieldHealth() * 33) + "%");
        shopBuyingCoinLabel.setText("Coins: " + level.getCoin());
        shopBuyingScoreLabel.setText("Score: " + level.getScore());
    }
    
    public void upgradeSelected(String upgrade) {
        String[] upgradeNameSplit = upgrade.split("-");
        String upgradeName = upgradeNameSplit[0];
        
        //must keep number of spaces correct (currently 3)
        if (upgradeName.equals("Health Pack   ")) {
            if (level.getCoin() >= healthUp.getPrice()) {
                healthUp.setBought(true);
                removeUpgrade(healthUp);
            }
        } else if (upgradeName.equals("Add Shield   ")) {
            if (level.getCoin() >= shieldUp.getPrice()) {
                shieldUp.setBought(true);
                removeUpgrade(shieldUp);
            }
        } else if (upgradeName.equals("Player Speed   ")) {
            if (level.getCoin() >= speedUp.getPrice()) {
                speedUp.setBought(true);
                removeUpgrade(speedUp);
            }
        } else if (upgradeName.equals("Shooting Speed   ")) {
            if (level.getCoin() >= shootUp.getPrice()) {
                shootUp.setBought(true);
                removeUpgrade(shootUp);
            }
        }
    }
    
    public void removeUpgrade(Upgrades upgrade) {
        shopUpgradesView.getItems().remove(upgrade.getListView());
    }
    
    public VBox getPlayerData() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        
        shopBuyingHealthLabel = new Label();
        shopBuyingHealthLabel.setText("Health: ");
        shopBuyingHealthLabel.setFont(new Font("Arial", 30));
        shopBuyingHealthLabel.setTextFill(Color.BLACK);
        shopBuyingShieldLabel = new Label();
        shopBuyingShieldLabel.setText("Shield: ");
        shopBuyingShieldLabel.setFont(new Font("Arial", 30));
        shopBuyingShieldLabel.setTextFill(Color.BLACK);
        shopBuyingCoinLabel = new Label();
        shopBuyingCoinLabel.setText("Coins: ");
        shopBuyingCoinLabel.setFont(new Font("Arial", 30));
        shopBuyingCoinLabel.setTextFill(Color.BLACK);
        shopBuyingScoreLabel = new Label();
        shopBuyingScoreLabel.setText("Score: ");
        shopBuyingScoreLabel.setFont(new Font("Arial", 30));
        shopBuyingScoreLabel.setTextFill(Color.BLACK);
        
        vbox.getChildren().addAll(shopBuyingHealthLabel, shopBuyingShieldLabel, shopBuyingCoinLabel, 
            shopBuyingScoreLabel);
        return vbox;
    }
    
    public HBox addShopViewButtons(Stage pStage) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(100);
        
        Button buyButton = new Button("BUY");
        Button exitButton = new Button("EXIT");
        
        buyButton.setOnAction(e -> {
            ObservableList<String> upgrade = shopUpgradesView.getSelectionModel().getSelectedItems();
            for (String up : upgrade) {
                upgradeSelected(up);
            }
        });
        exitButton.setOnAction(e-> {
            pStage.getScene().setRoot(shopRoot);
            inShopBuyingView = false;
        });
        hbox.getChildren().addAll(buyButton, exitButton);
        return hbox;
    }
    //Shop
    
    
    //General
    public void newGame() {
        level = new Level();
        player = new Character((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
        actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
        actualHealth.setFill(Color.web("#00F32C"));
        gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth, actualHealth, coinAndScore);
        addShopButtons();
        coinAndScore.toFront();
        coinLabel.toFront();
        scoreLabel.toFront();
        health.toFront();
        healthBarOutline.toFront();
        lostHealth.toFront();
        actualHealth.toFront();
        player.addShield(false);
        gameplay = true;
        pause = false;
        shieldAdded = false;
        couldGoToShop = true;
        couldGoToMap = false;
        addShopStair = true;
        level.fillBoss(bosses);
        currentRoot = gameRoot;
    }
    
    public void clearAll() {
        projectiles.clear();
        projToRemove.clear();
        enemies.clear();
        enemToRemove.clear();
        enemyProj.clear();
        enemyProjToRemove.clear();
        Spikes.spikes.clear();
        Spikes.spikeToRemove.clear();
        bosses.clear();
        portals.clear();
        portalCount = 0;
        currentUpgrades.clear();
        upgradesToRemove.clear();
        shopUpgrades.clear();
        shopUpgradesView.getItems().clear();
        level.clearScore();
        level.clearCoins();
        level.setShopping(false);
        coinLabel.setText("Coins: ");
        scoreLabel.setText("Score: ");
        gameRoot.getChildren().clear();
        shopRoot.getChildren().clear();
    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    //General
    
    
    //Layouts
    public void createRoots(Stage pStage) {
        //Menu Root
        Text title = new Text("THE AWESOME GAME");
        title.setFont(Font.font("Arial", 50));
        VBox vbox = addMenuButtons(pStage);
        vbox.setAlignment(Pos.TOP_CENTER);
        menuRoot = new BorderPane();
        menuRoot.setId("menu");
        menuRoot.setCenter(vbox);
        menuRoot.setTop(title);
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setMargin(title, new Insets(100));

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
        shopRoot = new Pane();
        shopRoot.setId("backgroundshop");
        decUpStair = new Stairs("up", (int) screenSize.getWidth(), (int) screenSize.getHeight());
        toGameStair = new Stairs("shop", (int) screenSize.getWidth() - 100, (int) screenSize.getHeight() - 100);
        shopKeeper = new ShopKeeper("file:src/Sprites/ShopKeeper.png", 65, 40);
        
        //Shop Buying Root
        Text shopTitle = new Text("SHOP");
        shopTitle.setFont(Font.font("Arial", 50));
        HBox shopButtons = addShopViewButtons(pStage);
        Text itemSummary = new Text("SUMMARY");
        itemSummary.setFont(Font.font("Arial", 30));
        VBox playerData = getPlayerData();
        shopBuyingRoot = new BorderPane();
        shopBuyingRoot.setTop(shopTitle);
        shopBuyingRoot.setBottom(shopButtons);
        shopBuyingRoot.setRight(itemSummary);
        shopBuyingRoot.setLeft(playerData);
        playerData.setPrefWidth((int) screenSize.getWidth() / 5);
        BorderPane.setAlignment(shopTitle, Pos.CENTER);
        BorderPane.setMargin(shopTitle, new Insets(50));
        BorderPane.setAlignment(shopButtons, Pos.TOP_CENTER);
        BorderPane.setMargin(shopButtons, new Insets(20, 0, 20, 0));
        BorderPane.setAlignment(itemSummary, Pos.TOP_LEFT);
        BorderPane.setMargin(itemSummary, new Insets(10, 200, 10, 10));
        BorderPane.setAlignment(playerData, Pos.TOP_LEFT);
        BorderPane.setMargin(playerData, new Insets(10, 10, 10, 20));

        //Options Root
        Text opTitle = new Text("GAME OPTIONS");
        opTitle.setFont(Font.font("Arial", 50));
        VBox optionsBox = addOptionButtons(pStage);
        optionsBox.setAlignment(Pos.TOP_CENTER);
        optionsRoot = new BorderPane();
        optionsRoot.setId("menu");
        optionsRoot.setCenter(optionsBox);
        optionsRoot.setTop(opTitle);
        BorderPane.setAlignment(opTitle, Pos.CENTER);
        BorderPane.setMargin(opTitle, new Insets(100));

        //Game Options Root
        Text gameOpTitle = new Text("GAMEOPTIONS");
        gameOpTitle.setFont(Font.font("Arial", 50));
        VBox gameOptionsBox = addGameOptionsButtons(pStage);
        gameOptionsBox.setAlignment(Pos.TOP_CENTER);
        gameOptionsRoot = new BorderPane();
        gameOptionsRoot.setId("menu");
        gameOptionsRoot.setCenter(gameOptionsBox);
        gameOptionsRoot.setTop(gameOpTitle);
        BorderPane.setAlignment(gameOpTitle, Pos.CENTER);
        BorderPane.setMargin(gameOpTitle, new Insets(100));

        //Game Over Root
        VBox gameOverBox = addGameOverButtons(pStage);
        gameOverBox.setAlignment(Pos.TOP_CENTER);
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
    }

    public VBox addMenuButtons(Stage pStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Button startBtn = new Button("START");
        startBtn.setOnAction(e -> {
            pStage.getScene().setRoot(gameRoot);
            newGame();
        });

        Button optionsBtn = new Button("OPTIONS");
        optionsBtn.setOnAction(e -> {
            pStage.getScene().setRoot(optionsRoot);
        });

        Button exitBtn = new Button("EXIT");
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
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        CheckBox musicBox = new CheckBox("MUSIC");
        musicBox.setSelected(false);
        musicBox.setOnAction(e -> {

        });

        Button backBtn = new Button("BACK TO MENU");
        backBtn.setOnAction(e -> {
            pStage.getScene().setRoot(menuRoot);
        });

        vbox.getChildren().addAll(musicBox, backBtn);
        return vbox;
    }

    public VBox addGameOptionsButtons(Stage pStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        CheckBox musicBox = new CheckBox("MUSIC");
        musicBox.setSelected(false);
        musicBox.setOnAction(e -> {

        });

        Button gameBtn = new Button("BACK TO GAME");
        gameBtn.setOnAction(e -> {
            if (!level.isShopping()) {
                pStage.getScene().setRoot(gameRoot);
            } else {
                if (inShopBuyingView) pStage.getScene().setRoot(shopBuyingRoot);
                else pStage.getScene().setRoot(shopRoot);
            }
            pause = false;
        });

        Button backBtn = new Button("BACK TO MENU");
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

        Button exitBtn = new Button("QUIT");
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
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Button newBtn = new Button("NEW GAME");
        newBtn.setOnAction(e -> {
            pStage.getScene().setRoot(gameRoot);
            clearAll();
            newGame();
        });

        Button backBtn = new Button("BACK TO MENU");
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

        Button exitBtn = new Button("QUIT");
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
    //Layouts
}
