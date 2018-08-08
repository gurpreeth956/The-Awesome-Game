package Bosses;

import Game.Character;
import Projectiles.*;

import java.util.List;
import javafx.scene.layout.Pane;

public class HomingBoss extends RangedBoss {

    public HomingBoss(String img, int health, int coin, int width, int height, int shootSpeed,
            String shotImg) {
        super(img, health, coin, width, height, shootSpeed, shotImg, "Needs a name");
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

        this.setCharacterView(128, 0);
        this.setOffsetY(0);
        if (time < 0 || time > this.getShootSpeed()) {
            createProjectile(5, 5, projectiles, gameRoot, shotIVFile, 20, 9, player);
            timeOfLastProjectile = timeNow;
        }
    }

    public void createProjectile(int x, int y, List<Projectile> projectiles, Pane root,
            String img, int width, int height, Character player) {
        Projectile proj = new HomingProjectile(img, this.getX() + 28, this.getY() + 16, width, height, 1, launchDistance(player));
        proj.setVelocityX(x);
        proj.setVelocityY(y);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }

    public int launchDistance(Character player) {
        int vert = player.getY() - this.y;
        int hori = player.getX() - this.x;
        int dist = 0;
        if (hori > 0 && vert == 0) {//Right
            dist = 0;
        }
        if (hori < 0 && vert == 0) {//Left
            dist = -180;
        }
        if (hori == 0 && vert > 0) {//down
            dist = 90;
        }
        if (hori == 0 && vert < 0) {//up
            dist = -90;
        }
        if (hori > 0 && vert < 0) {
            dist = -45;
        }
        if (hori < 0 && vert < 0) {
            dist = -135;
        }
        if (hori < 0 && vert > 0) {
            dist = 135;
        }
        if (hori > 0 && vert > 0) {
            dist = 45;
        }
        return dist;
    }
}
