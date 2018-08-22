package Enemies;
import Game.Character;
import Game.SpriteAnimation;

//A.K.A Cannon

import Projectiles.Projectile;
import java.util.List;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BasicShooter extends RangedEnemy {

    SpriteAnimation cannon;
    private final int count = 4;
    private final int columns = 4;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(shootSpeed);
    private final Animation animation;
    
    int angularDir = 270;
    
    public BasicShooter(String img, int health, int coin, int width, int height, int shootSpeed, 
            String shotImg) {
        super(img, health, coin, width, height, shootSpeed, shotImg);
        super.getChildren().remove(iv);
        cannon = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);
        animation = cannon;
        iv = cannon.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().addAll(iv);
        
        //change below to true if collision rectangles are added
        hasCollisionRects = false;
    }

    public void move(Game.Character player, double width, double height) {
        animation.play();
        
	if (player.getX() + 18 > this.getX() && player.getY() == this.getY()) { //right
            double rotation = 270 - this.getRotate();
            this.setRotate(this.getRotate() + rotation);
            this.moveX(1, width);
        }
        if (player.getX() + 18 < this.getX() && player.getY() == this.getY()) { //left
            double rotation = 90 - this.getRotate();
            this.setRotate(this.getRotate() + rotation);
            this.moveX(-1, width);
        }
        if (player.getX() + 18 == this.getX() && player.getY() > this.getY()) { //down
            double rotation = 360 - this.getRotate();
            this.setRotate(this.getRotate() + rotation);
            this.moveY(1, height);
        }
        if (player.getX() + 18 == this.getX() && player.getY() < this.getY()) { //up
            double rotation = 180 - this.getRotate();
            this.setRotate(this.getRotate() + rotation);
            this.moveY(-1, height);
        }

        if (player.getX() + 18 > this.getX() && player.getY() < this.getY()) { //quadrant1
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (player.getX() + 18 < this.getX() && player.getY() < this.getY()) { //quadrant2
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (player.getX() + 18 < this.getX() && player.getY() > this.getY()) { //quadrant3
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (player.getX() + 18 > this.getX() && player.getY() > this.getY()) { //quadrant4
            this.moveX(1, width);
            this.moveY(1, height);
        }
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        String dist = distance(player);

        if (dist.equals("up")) { //shoot up
            if (time < 0 || time > this.getShootSpeed()) {
                cannon.setOffset(0, 52);
                double rotation = 180 - this.getRotate();
                this.setRotate(this.getRotate() + rotation);
                angularDir = 90;
                createProjectile(0, -5, projectiles, gameRoot, shotIVFile, 20, 20, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("down")) { //shoot down
            if (time < 0 || time > this.getShootSpeed()) {
                cannon.setOffset(0, 52);
                double rotation = 360 - this.getRotate();
                this.setRotate(this.getRotate() + rotation);
                angularDir = 270;
                createProjectile(0, 5, projectiles, gameRoot, shotIVFile, 20, 20, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("left")) { //shoot left
            if (time < 0 || time > this.getShootSpeed()) {
                cannon.setOffset(0, 52);
                double rotation = 90 - this.getRotate();
                this.setRotate(this.getRotate() + rotation);
                angularDir = 180;
                createProjectile(-5, 0, projectiles, gameRoot, shotIVFile, 20, 20, 1);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("right")) { //shoot right
            if (time < 0 || time > this.getShootSpeed()) {
                cannon.setOffset(0, 52);
                double rotation = 270 - this.getRotate();
                this.setRotate(this.getRotate() + rotation);
                angularDir = 360;
                createProjectile(5, 0, projectiles, gameRoot, shotIVFile, 20, 20, 1);
                timeOfLastProjectile = timeNow;
            }
        }
    }
    
    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root, 
                                 String img, int width, int height, int dmg) {
        Projectile proj;
        if (angularDir == 360) {
            proj = new Projectile(img, this.getX() + 22, this.getY() + 18, width, height, dmg);
        } else if (angularDir == 270) {
            proj = new Projectile(img, this.getX() + 4, this.getY() + 20, width, height, dmg); //
        } else if (angularDir == 180) {
            proj = new Projectile(img, this.getX() - 12, this.getY() + 13, width, height, dmg);
        } else {
            proj = new Projectile(img, this.getX() + 4, this.getY() + 22, width, height, dmg);
        }
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
}
