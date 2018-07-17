package Environment;

import Enemies.HomingBoss;
import Enemies.Boss;
import Enemies.RangedEnemy;
import Enemies.SpikeEnemy;
import Enemies.Licker;
import Enemies.Enemy;
import java.util.List;

public class Level {

    public static int currentLevel;
    public static int enemiesToBeat;
    public static int enemiesLeft;
    public static int enemiesSpawned;
    public static int coins;
    public static int highScore;
    static boolean shopping;

    public Level() {
        currentLevel = 1;
        enemiesToBeat = 5;
        enemiesLeft = 5;
        enemiesSpawned = 0;
        shopping = false;
    }

    public void increaseLevel() {
        currentLevel++;
        enemiesSpawned = 0;
        enemiesToBeat *= 2;
        enemiesLeft = enemiesToBeat;
    }

    public int getEnemiesToBeat() {
        return enemiesToBeat;
    }

    public void enemySpawned() {
        enemiesSpawned++;
    }

    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public void enemyBeat() {
        enemiesLeft--;
    }

    public int getEnemiesLeft() {
        return enemiesLeft;
    }

    public int getLevel() {
        return currentLevel;
    }

    public void coinUp(Enemy enemy) {
        coins += enemy.getCoin();
    }

    public void scoreUp(Enemy enemy) {
        highScore += enemy.getScore();
    }

    public int getCoin() {
        return coins;
    }

    public void spend(int i) {
        coins -= i;
    }

    public int getScore() {
        return highScore;
    }

    public void clearScore() {
        highScore = 0;
    }

    public void clearCoins() {
        coins = 0;
    }

    public void setShopping(boolean a) {
        shopping = a;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void fillBoss(List<Enemy> bosses) {
        //testing phase - we will decide bosses order eventually
        bosses.add(new HomingBoss("file:src/Sprites/Redies.png", 30, 20, 66, 33, 1000));
        bosses.add(new Boss("file:src/Sprites/test.png", 30, 20, 200, 200));
    }

    public Enemy generate() {
        int randomNum = 10;
        while(randomNum > 3) {
            randomNum = (int) (Math.random() * this.getLevel() + 1);
        }
        Enemy enemy = null;
        switch (randomNum) {
            case 3:
                enemy = new Licker("file:src/Sprites/CharlesSpriteSheet.png", 3, 1, 80, 80);
                break;
            case 1:
                enemy = new RangedEnemy("file:src/Sprites/Redies.png", 3, 1, 66, 33, 3000);
                break;
            case 2: //for testing reasons
                enemy = new SpikeEnemy("file:src/Sprites/SpikeySpriteSheet.png", 3, 1, 72, 65, 3000);
                break;
        }
        return enemy;
    }
}
