public class Level {
    
    public static int currentLevel;
    public static int enemiesToBeat;
    public static int enemiesLeft;
    
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
}
