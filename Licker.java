/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rhuan
 */
//A.K.A Charles
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Licker extends MeleeEnemy{

    SpriteAnimation charles;
    private final int count = 20;
    private final int columns = 10;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(900);
    private final Animation animation;
    
    public Licker(String img, int health, int coin, int width, int height){
            super(img, health, coin, width, height);
            super.getChildren().remove(iv);
            charles = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);         
            animation = charles;
            iv = charles.getIV();
            iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            getChildren().addAll(iv);          
    }
    
    @Override
    public void move(Character player, double width, double height) {
        animation.play();
	if (player.getX() > this.getX() && player.getY() == this.getY()) { //right
            charles.setOffset(0, 320);
            //animation.play();  
            this.moveX(1, width, 1);
        }
        if (player.getX() < this.getX() && player.getY() == this.getY()) { //left
            charles.setOffset(0, 480);
            //animation.play();
            this.moveX(-1, width, 1);
        }
        if (player.getX() == this.getX() && player.getY() > this.getY()) { //down
            charles.setOffset(0, 0);
            //animation.play();
            this.moveY(1, height, 1);
        }
        if (player.getX() == this.getX() && player.getY() < this.getY()) { //up
            charles.setOffset(0, 160);
            //animation.play();
            this.moveY(-1, height, 1);
        }

        if (player.getX() > this.getX() && player.getY() < this.getY()) { //quadrant1
            charles.setOffset(0, 320);
            //animation.play();  
            this.moveX(1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() < this.getY()) { //quadrant2
            charles.setOffset(0, 480);
            //animation.play();
            this.moveX(-1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() > this.getX() && player.getY() > this.getY()) { //quadrant3
            charles.setOffset(0, 320);
            //animation.play();
            this.moveX(1, width, 1);
            this.moveY(1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() > this.getY()) { //quadrant4
            charles.setOffset(0, 480);
            //animation.play();
            this.moveX(-1, width, 1);
            this.moveY(1, height, 1);
        }
    }
}
