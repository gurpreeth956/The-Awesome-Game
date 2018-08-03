package Bosses;

import Enemies.MeleeEnemy;
import Game.Character;

public class ChargeBoss extends MeleeEnemy {

    long stunTime = 1000;
    long timeIndex = 0;
    boolean charging = true;
    boolean lock = false;
    int targetX;
    int targetY;
    double angle;
    double vx;
    double vy;
    int speed;

    public ChargeBoss(String img, int health, int coin, int width, int height, int speed) {
        super(img, health, coin, width, height);
        this.speed = speed;
    }

    //CrAsHy BoI
    public void move(Character player, double width, double height) {
        long timeNow = System.currentTimeMillis();
        this.setCharacterView(0, 0);
        if ((this.getX() <= 0 || this.getX() + 66>= width) && charging) {
            charging = false;
            timeIndex = System.currentTimeMillis();
            lock = false;
        }
        if ((this.getY() <= 0 || this.getY() + 33>= height) && charging) {
            charging = false;
            timeIndex = System.currentTimeMillis();
            lock = false;
        }
        if (!charging) {
            if (timeNow - stunTime >= timeIndex) {
                charging = true;
            }
        }
        if (!lock) {
            targetX = player.getX() - this.getX();
            targetY = player.getY() - this.getY();
            angle = Math.atan2(targetY, targetX) * 180 / Math.PI;
            this.iv.setRotate(angle);
            lock = true;
        }
        if(charging) {
            vx = speed * (90-Math.abs(angle))/90;
            if(angle<0){
                vy = -speed +Math.abs(vx);
            }else{
                vy = speed - Math.abs(vx);
            }
            this.setTranslateX(this.getTranslateX() + (int)vx);
            this.setTranslateY(this.getTranslateY() + (int)vy);
            this.x += (int)vx;
            this.y += (int)vy;
        }
        //figure out direction to charge in relative to player coordinates
        //charge in direction until collision with pane border
        //use timer for stun duration??
    }
}
