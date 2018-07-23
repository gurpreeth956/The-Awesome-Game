package Enemies;
import Game.Character;
import Projectiles.Projectile;

import java.util.List;
import javafx.scene.layout.Pane;

public class FourWayShooter extends RangedEnemy {

    public FourWayShooter(String img, int health, int coin, int width, int height, int shootSpeed) {
        super(img, health, coin, width, height, shootSpeed);
    }
    
    /*public void move(Character player, double width, double height) {
        Will add once new character is desinged
    }*/
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        
        if (time < 0 || time > this.getShootSpeed()) {
            createProjectile(0, -5, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
            createProjectile(0, 5, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
            createProjectile(-5, 0, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
            createProjectile(5, 0, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
            timeOfLastProjectile = timeNow;
        }
    }
}