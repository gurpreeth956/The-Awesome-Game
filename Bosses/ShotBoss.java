package Bosses;
import Enemies.Enemy;
import Game.Character;
import Enemies.RangedEnemy;
import Projectiles.*;

import java.util.List;
import javafx.scene.layout.Pane;

public class ShotBoss extends RangedEnemy {

    public ShotBoss(String img, int health, int coin, int width, int height, int shootSpeed) {
        super(img, health, coin, width, height, shootSpeed);
        timeOfLastProjectile = 0;
    }

    public void move(Character player, double width, double height) {
        //animation.play() need to make better enemy with animation
        this.setCharacterView(0, 0);
        if (player.getX() - 96 > this.getX() && player.getY() - 112 == this.getY()) { //right
            this.moveX(1, width);
        }
        if (player.getX() - 96 < this.getX() && player.getY() - 112 == this.getY()) { //left
            this.moveX(-1, width);
        }
        if (player.getX() - 96 == this.getX() && player.getY() - 112 > this.getY()) { //down
            this.moveY(1, height);
        }
        if (player.getX() - 96 == this.getX() && player.getY() - 112 < this.getY()) { //up
            this.moveY(-1, height);
        }

        if (player.getX() - 96 > this.getX() && player.getY() - 112 < this.getY()) { //quadrant1
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (player.getX() - 96 < this.getX() && player.getY() - 112 < this.getY()) { //quadrant2
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (player.getX() - 96 < this.getX() && player.getY() - 112 > this.getY()) { //quadrant3
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (player.getX() - 96 > this.getX() && player.getY() - 112 > this.getY()) { //quadrant4
            this.moveX(1, width);
            this.moveY(1, height);
        }
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot){
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        
        if (time < 0 || time > this.getShootSpeed()) {
            int randomX = (int) (Math.random() * 13) - 6;
            int randomY = (int) (Math.random() * 13) - 6;
            
            if (Math.abs(randomX) > 4 || Math.abs(randomY) > 4) {
                createProjectile(randomX, randomY, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }
        }
    }
    
    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root, 
                                 String img, int width, int height) {
        Projectile proj = new Projectile(img, this.getX() + 128, this.getY() + 128, width, height);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
    
    public void hitView(Enemy enemy) {
        this.setCharacterView(0, 0);
    }
}
