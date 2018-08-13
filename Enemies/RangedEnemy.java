package Enemies;
import Game.Character;
import Projectiles.Projectile;

import java.util.List;
import javafx.scene.layout.Pane;

public class RangedEnemy extends Enemy {
    
    public String shotIVFile;
    public long timeOfLastProjectile;
    int shootSpeed;
    
    public RangedEnemy(String img, int health, int coin, int width, int height, int shootSpeed,
            String shotImg) {
        super(img, health, coin, width, height);
        this.shootSpeed = shootSpeed;
        this.shotIVFile = shotImg;
        timeOfLastProjectile = 0;
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        //following code is used for when testing enemy in child class without its own design 
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        String dist = distance(player);

        if (dist.equals("up")) { //shoot up
            this.setCharacterView(128, 183);
            this.setOffsetY(183);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(0, -5, projectiles, gameRoot, shotIVFile, 12, 12, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("down")) { //shoot down
            this.setCharacterView(128, 0);
            this.setOffsetY(0);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(0, 5, projectiles, gameRoot, shotIVFile, 12, 12, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("left")) { //shoot left
            this.setCharacterView(128, 123);
            this.setOffsetY(123);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(-5, 0, projectiles, gameRoot, shotIVFile, 12, 12, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("right")) { //shoot right
            this.setCharacterView(128, 61);
            this.setOffsetY(61);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(5, 0, projectiles, gameRoot, shotIVFile, 12, 12, 1);
                timeOfLastProjectile = timeNow;
            }
        }
    }
    
    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root, 
                                 String img, int width, int height, int dmg) {
        //following code is used for when testing enemy in child class without its own design 
        Projectile proj = new Projectile(img, this.getX() + 28, this.getY() + 16, width, height, dmg);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
    
    public int getShootSpeed() {
        return shootSpeed;
    }
    
    public String distance(Character player) {
        int vert = player.getY() - this.y;
        int hori = player.getX() - this.x;
        
        if(Math.abs(vert) > Math.abs(hori)) {
            if(vert <= 0) {
                return "up";
            }
            return "down";
        }
        else if(hori <= 0) {
            return "left";
        }
        return "right";
    }
    
    public long getTimeofLastProjectile() {
        return timeOfLastProjectile;
    }

}
