package Enemies;

public class MeleeEnemy extends Enemy {
    
    public MeleeEnemy(String img, int health, int coin, int width, int height) {
	super(img, health, coin, width, height);
    }
    
    public void hitView(Enemy enemy) {
        this.setCharacterView(0, 0);
    }
}
