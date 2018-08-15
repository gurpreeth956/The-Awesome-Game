package Projectiles;

import Enemies.Enemy;

public class Bomb extends Projectile {

    int timer = 2000;
    long timeIndex;
    boolean explode = false;

    //Create sprite for bomb
    public Bomb(String img, int posX, int posY, int width, int height, int dmg) {
        super(img, posX, posY, width, height, dmg);
        timeIndex = System.currentTimeMillis();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean exploding() {
        return explode;
    }

    public void move(Character player) {
        //called in the super class for some reason
        long timeNow = System.currentTimeMillis();
        if (timeIndex + timer <= timeNow) {
            explode = true;
            this.setAlive(false);
        }
    }

    //deactivate enemy collision for bombs
    public boolean enemyColliding(Enemy enemy) {
        long timeNow = System.currentTimeMillis();
        if (timeIndex + timer <= timeNow) {
            explode = true;
        }
        if (this.exploding()) {
            this.setAlive(false);
            if (Math.abs(enemy.getX() - this.x + 32) <= 80 && Math.abs(enemy.getY()
                    - this.y + 16) <= 80) {
                return true;
            }
        }
        return false;
    }
}
