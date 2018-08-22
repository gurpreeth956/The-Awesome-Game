package Enemies;
import Game.Character;
import Projectiles.Projectile;

import java.util.List;
import javafx.scene.layout.Pane;

public class FourWayShooter extends RangedEnemy {

    public FourWayShooter(String img, int health, int coin, int width, int height, int shootSpeed,
            String shotImg) {
        super(img, health, coin, width, height, shootSpeed, shotImg);
        
        //change below to true if collision rectangles are added
        hasCollisionRects = false;
    }
    
    /*public void move(Character player, double width, double height) {
        Will add once new character is desinged
    }*/
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        
        if (time < 0 || time > this.getShootSpeed()) {
            createProjectile(0, -5, projectiles, gameRoot, shotIVFile, 12, 12, 1);
            createProjectile(0, 5, projectiles, gameRoot, shotIVFile, 12, 12, 1);
            createProjectile(-5, 0, projectiles, gameRoot, shotIVFile, 12, 12, 1);
            createProjectile(5, 0, projectiles, gameRoot, shotIVFile, 12, 12, 1);
            timeOfLastProjectile = timeNow;
        }
    }
    
    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root, 
                                 String img, int width, int height, int dmg) {
        Projectile proj = new Projectile(img, this.getX() + 28, this.getY() + 16, width, height, dmg);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
}
