package Bosses;
import Enemies.Enemy;
import Game.Character;
import Game.SpriteAnimation;
import Projectiles.Projectile;

import java.util.List;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class RandShotBoss extends RangedBoss {

    SpriteAnimation bossAnimation;
    private final int count = 2;
    private final int columns = 2;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(900);
    private final Animation animation;
    
    public RandShotBoss(String img, int health, int coin, int width, int height, int shootSpeed,
            String shotImg) {
        super(img, health, coin, width, height, shootSpeed, shotImg, "MR.EVILER");
        super.getChildren().remove(iv);
        bossAnimation = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);
        animation = bossAnimation;
        iv = bossAnimation.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.getChildren().addAll(iv);
        this.setCharacterView(0, 0);
        
        //change below to true if collision rectangles are added
        hasCollisionRects = false;
    }

    public void move(Character player, double width, double height) {
        animation.play();
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
                createProjectile(randomX, randomY, projectiles, gameRoot, shotIVFile, 12, 12);
                timeOfLastProjectile = timeNow;
            }
        }
    }
    
    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root, 
                                 String img, int width, int height) {
        Projectile proj = new Projectile(img, this.getX() + 128, this.getY() + 128, width, height, 1);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
    
    public void hitView(Enemy enemy) {
        animation.play();
    }
}
