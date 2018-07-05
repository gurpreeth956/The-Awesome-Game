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
	enemiesToBeat = 10;
	enemiesLeft = 10;
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
    
    public int getScore() {
	return highScore;
    }
    
    public void clearScore() {
	highScore = 0;
    }
    
    public void clearCoins() {
	coins = 0;
    }
    
    public void setShopping(boolean i){
	shopping = i;
    }
    
    public boolean shopping(){
	return shopping;
    }
}
