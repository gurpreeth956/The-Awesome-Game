package Enemies;
import Game.Character;
import Game.SpriteAnimation;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

//A.K.A Kaa or Shé

public class MrSnake extends MeleeEnemy {

    SpriteAnimation kaa;
    private final int count = 4;
    private final int columns = 4;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(900);
    private final Animation animation;
    
    public MrSnake(String img, int health, int coin, int width, int height) {
        super(img, health, coin, width, height);
        super.getChildren().remove(iv);
        kaa = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);
        animation = kaa;
        iv = kaa.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().addAll(iv);
    }

    public void hitView(Enemy enemy) {
        kaa.setOffset(0, 0);
    }
    
    public void move(Character player, double width, double height) {
        animation.play();
        //there is a plus 20 for X since snake width is very small
	if (player.getX() + 20 > this.getX() && player.getY() == this.getY()) { //right
            kaa.setOffset(0, 35);
            this.moveX(2, width);
        }
        if (player.getX() + 20 < this.getX() && player.getY() == this.getY()) { //left
            kaa.setOffset(0, 0);
            this.moveX(-2, width);
        }
        if (player.getX() + 20 == this.getX() && player.getY() > this.getY()) { //down
            kaa.setOffset(0, 35);
            this.moveY(2, height);
        }
        if (player.getX() + 20 == this.getX() && player.getY() < this.getY()) { //up
            kaa.setOffset(0, 0);
            this.moveY(-2, height);
        }

        if (player.getX() + 20 > this.getX() && player.getY() < this.getY()) { //quadrant1
            kaa.setOffset(0, 35);
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (player.getX() + 20 < this.getX() && player.getY() < this.getY()) { //quadrant2
            kaa.setOffset(0, 0);
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (player.getX() + 20 < this.getX() && player.getY() > this.getY()) { //quadrant3
            kaa.setOffset(0, 0);
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (player.getX() + 20 > this.getX() && player.getY() > this.getY()) { //quadrant4
            kaa.setOffset(0, 35);
            this.moveX(1, width);
            this.moveY(1, height);
        }
    }
}