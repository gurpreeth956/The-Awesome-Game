package Projectiles;

import Game.Character;

public class HomingProjectile extends Projectile {

    public HomingProjectile(String img, int posX, int posY, int width, int height) {
        super(img, posX, posY, width, height);
    }

    public void move(Character player) {
        String dist = this.distance(player);

        if (dist.equals("U")) {
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() - this.getVelocityY());
            this.y -= 3;
        }
        if (dist.equals("D")) {
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
            this.y += 3;
        }
        if (dist.equals("L")) {
            this.setTranslateX(this.getTranslateX() - this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
            this.x -= 3;
        }
        if (dist.equals("R")) {
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
            this.x += 3;
        }
        if (dist.equals("NE")) {
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() - this.getVelocityY());
            this.x += 3;
            this.y -= 3;
        }
        if (dist.equals("NW")) {
            this.setTranslateX(this.getTranslateX() - this.getVelocityX());
            this.setTranslateY(this.getTranslateY() - this.getVelocityY());
            this.x -= 3;
            this.y -= 3;
        }
        if (dist.equals("SE")) {
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
            this.x += 3;
            this.y += 3;
        }
        if (dist.equals("SW")) {
            this.setTranslateX(this.getTranslateX() - this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
            this.x -= 3;
            this.y += 3;
        }
    }

    public String distance(Character player) {
        int vert = player.getY() - this.y;
        int hori = player.getX() - this.x;
        String dir = "";

        if (hori > 0 && vert < 0) {
            dir = "NE";
        }
        if (hori < 0 && vert < 0) {
            dir = "NW";
        }
        if (hori < 0 && vert > 0) {
            dir = "SW";
        }
        if (hori > 0 && vert > 0) {
            dir = "SE";
        }
        if (hori > 0 && vert == 0) {
            dir = "R";
        }
        if (hori < 0 && vert == 0) {
            dir = "L";
        }
        if (hori == 0 && vert > 0) {
            dir = "D";
        }
        if (hori == 0 && vert < 0) {
            dir = "U";
        }
        return dir;
    }
}
