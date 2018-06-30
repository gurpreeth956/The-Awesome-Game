public class Level {
    
    public int currentLevel;
    public int enemiesToBeat;
    
    public Level(int level){
	currentLevel = level;
	enemiesToBeat = level * 10; //enemes to beat scales with level 
    }
}
