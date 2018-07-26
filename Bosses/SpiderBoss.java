package Bosses;
import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Game.Character;
import Game.SpriteAnimation;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class SpiderBoss extends MeleeEnemy {

    SpriteAnimation spidey;
    private final int count = 4;
    private final int columns = 4;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(300);
    private final Animation animation;
    
    long timeUntilNextAttack;
    int currentPauseTime;
    int randomDirection = 0;
    boolean randLocationSet;
    boolean ready;
    boolean rageMode;
    
    public SpiderBoss(String img, int health, int coin, int width, int height) {
        super(img, health, coin, width, height);
        super.getChildren().remove(iv);
        spidey = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);
        animation = spidey;
        iv = spidey.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().addAll(iv);
        
        currentPauseTime = 3000;
        randLocationSet = false;
        ready = false;
        rageMode = false;
    }

    public void move(Character player, double width, double height) {
        animation.play();
        
        if (!ready) {
            if (this.getY() <= -250) {
                ready = true;
            }
            this.moveY(-5, 240);
        }
        else {
            long timeNow = System.currentTimeMillis();
            long time = timeNow - timeUntilNextAttack;

            if (time < 0 || time > currentPauseTime) {
                randomDirection = (int) (Math.random() * 4 + 1);
                if (randomDirection == 1) {
                    int randX = (int) (Math.random() * 1100 + 10);
                    this.setX(randX);
                    this.setY(-250);
                    double rotation = 180 - this.getRotate();
                    this.setRotate(this.getRotate() + rotation);

                } else if (randomDirection == 2) {
                    int randY = (int) (Math.random() * 545 + 10);
                    this.setX(-250);
                    this.setY(randY);
                    double rotation = 90 - this.getRotate();
                    this.setRotate(this.getRotate() + rotation);

                } else if (randomDirection == 3) {
                    int randX = (int) (Math.random() * 1100 + 10);
                    this.setX(randX);
                    this.setY(970);
                    double rotation = 360 - this.getRotate();
                    this.setRotate(this.getRotate() + rotation);

                } else if (randomDirection == 4) {
                    int randY = (int) (Math.random() * 545 + 10);
                    this.setX(1300);
                    this.setY(randY);
                    double rotation = 270 - this.getRotate();
                    this.setRotate(this.getRotate() + rotation);
                }

                timeUntilNextAttack = timeNow;
            }
            
            switch(randomDirection) {
                case 1 : this.moveY(8, height); break;
                case 2 : this.moveX(12, width); break;
                case 3 : this.moveY(-8, height); break;
                case 4 : this.moveX(-12, width); break;
            }
        }
    }
    
    public void hitView(Enemy enemy) {
        
    }
}
