

public class Level {
    
    public static int currentLevel;
    public static int enemiesToBeat;
    public static int enemiesLeft;
    public static int coins;
    public static int highScore;
    static boolean shopping;
    
    public Level() {
	currentLevel = 1;
	enemiesToBeat = 10;
	enemiesLeft = 10;
    }
   
    public static void increaseLevel() {
	currentLevel++;
    }
    
    public void increaseEnemies() {
	enemiesToBeat *= 2;
	enemiesLeft = enemiesToBeat;
    }
    
    public static void enemyBeat() {
	enemiesLeft--;
    }
    
    public static int getEnemiesLeft() {
	return enemiesLeft;
    }
    
    public static int getLevel() {
	return currentLevel;
    }
    
    public static void coinUp(Enemy enemy) {
	coins += enemy.getCoin();
    }
    
    public static void scoreUp(Enemy enemy) {
	highScore += enemy.getScore();
    }
    
    public static String getCoin() {
	return coins + "";
    }
    
    public static String getScore() {
	return highScore + "";
    }
    
    public static void clearScore() {
	highScore = 0;
    }
    
    public static void clearCoins() {
	coins = 0;
    }
    
    public void setShopping(boolean a){
	shopping = a;
    }
    
    public static boolean shopping(){
	return shopping;
    }
}
