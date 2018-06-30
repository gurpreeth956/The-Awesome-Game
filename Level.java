
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Level {
    
    public static int currentLevel;
    public static int enemiesToBeat;
    public static int enemiesLeft;
    public static int highScore;
    
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
    
    public static void scoreUp(Enemy enemy){
	highScore+=enemy.getScore();
    }
    
    public static String getScore(){
	return highScore+"";
    }
    
    public static Label updateScore(){
	Label label = new Label("Score: " + getScore());
	label.setFont(new Font("Arial", 20));
	return label;
    }
    
    public static void clearScore(){
	highScore = 0;
    }
}
