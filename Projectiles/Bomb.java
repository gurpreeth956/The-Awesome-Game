/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projectiles;

import Enemies.Enemy;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rhuan
 */
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

    public void explode() {
        long timeNow = System.currentTimeMillis();
        if (timeNow - timer >= timeIndex) {
            explode = true;
            //change sprite to play explosion animation
        }
    }

    //deactivate enemy collision for bombs
    public boolean enemyColliding(Enemy enemy) {
        if (explode) {
            if (!enemy.hasCollisionRects()) {
                return this.getBoundsInParent().intersects(enemy.getBoundsInParent());
            }

            for (Rectangle rect : enemy.getCollisionRects()) {
                //change bounds to support explosion rectangle 
                if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }
}
