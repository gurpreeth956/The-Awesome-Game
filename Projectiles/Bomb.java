package Projectiles;

import Enemies.Enemy;
import Game.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Bomb extends Projectile {

    SpriteAnimation boo;
    private final int count = 5;
    private final int columns = 5;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration  = Duration.millis(2000);
    private final Animation animation;
    
    int timer = 2000;
    long timeIndex;
    boolean explode = false;

    //Create sprite for bomb
    public Bomb(String img, int posX, int posY, int width, int height, int dmg) {
        super(img, posX, posY, width, height, dmg);
        super.getChildren().remove(iv);
        timeIndex = System.currentTimeMillis();
        boo = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);
        animation = boo;
        iv = boo.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().add(iv);
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
        animation.play();
        long timeNow = System.currentTimeMillis();
        boo.setOffset(0,0);
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
