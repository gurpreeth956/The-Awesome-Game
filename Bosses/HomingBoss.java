package Bosses;

import Game.Character;
import Enemies.RangedEnemy;
import Projectiles.*;

import java.util.List;
import javafx.scene.layout.Pane;

public class HomingBoss extends RangedEnemy {

    public HomingBoss(String img, int health, int coin, int width, int height, int shootSpeed) {
        super(img, health, coin, width, height, shootSpeed);
        timeOfLastProjectile = 0;
    }

    public void move(Character player, double width, double height) {
        if (player.getX() > this.getX() && player.getY() == this.getY()) { //right
            this.setCharacterView(0, 61);
            this.moveX(1, width);
        }
        if (player.getX() < this.getX() && player.getY() == this.getY()) { //left
            this.setCharacterView(0, 123);
            this.moveX(-1, width);
        }
        if (player.getX() == this.getX() && player.getY() > this.getY()) { //down
            this.setCharacterView(0, 0);
            this.moveY(1, height);
        }
        if (player.getX() == this.getX() && player.getY() < this.getY()) { //up
            this.setCharacterView(0, 183);
            this.moveY(-1, height);
        }

        if (player.getX() > this.getX() && player.getY() < this.getY()) { //quadrant1
            this.setCharacterView(0, 61);
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (player.getX() < this.getX() && player.getY() < this.getY()) { //quadrant2
            this.setCharacterView(0, 123);
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (player.getX() < this.getX() && player.getY() > this.getY()) { //quadrant3
            this.setCharacterView(0, 123);
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (player.getX() > this.getX() && player.getY() > this.getY()) { //quadrant4
            this.setCharacterView(0, 61);
            this.moveX(1, width);
            this.moveY(1, height);
        }
    }

    //Keep speed params consistent for homing projectile purposes
    //Change homing projectile code later
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        String dist = distance(player);

        if (dist.equals("up")) { //shoot up
            this.setCharacterView(128, 183);
            this.setOffsetY(183);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(3, 3, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("down")) { //shoot down
            this.setCharacterView(128, 0);
            this.setOffsetY(0);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(3, 3, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("left")) { //shoot left
            this.setCharacterView(128, 123);
            this.setOffsetY(123);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(3, 3, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }

        } else if (dist.equals("right")) { //shoot right
            this.setCharacterView(128, 61);
            this.setOffsetY(61);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(3, 3, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }
        }
    }

    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root,
            String img, int width, int height) {
        Projectile proj = new HomingProjectile(img, this.getX() + 28, this.getY() + 16, width, height, 1);
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
}
