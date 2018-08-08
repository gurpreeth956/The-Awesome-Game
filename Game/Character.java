package Game;
import Environment.Stairs;
import Friends.*;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Character extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 66;
    int height = 33;
    int x; //Character xPos
    int y; //Character yPos
    int playerSpeed;
    int shootSpeed;

    int health = 100;
    int shieldHealth = 0;
    final int fullHealth = 100;
    final int fullShieldHealth = 3;
    boolean alive = true;
    boolean shield = false;
    boolean stop;

    public Character(int posX, int posY) {
	Image charImage = new Image("file:src/Sprites/Greenies.png");
	ImageView charIV = new ImageView(charImage);
	this.iv = charIV;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	this.setTranslateX(posX);
	this.setTranslateY(posY);
	this.x = posX;
	this.y = posY;
	this.getChildren().addAll(iv);
        this.playerSpeed = 3;
	this.shootSpeed = 500;
    }

    public void setCharacterView(int offsetX, int offsetY) {
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void moveX(int x, double screenWidth) {
	boolean right = x > 0;
	for (int i = 0; i < Math.abs(x); i++) {
	    if (right) {
		if (this.x > screenWidth - this.width) {
		    this.setTranslateX(screenWidth - this.width);
		} else {
		    this.setTranslateX(this.getTranslateX() + 1);
		    this.x++;
		}
	    } else {
		if (this.x < 0) {
		    this.setTranslateX(0);
		} else {
		    this.setTranslateX(this.getTranslateX() - 1);
		    this.x--;
		}
	    }
            
            boolean wall = isWall();
            if (right && wall) {
                this.setTranslateX(this.getTranslateX() - 1);
                this.x--;
            } else if (!right && wall) {
                this.setTranslateX(this.getTranslateX() + 1);
                this.x++;
            }
	}
    }

    public void moveY(int y, double screenHeight) {
	boolean down = y > 0;
	for (int i = 0; i < Math.abs(y); i++) {
	    if (down) {
		if (this.y > screenHeight - this.height) {
		    this.setTranslateY(screenHeight - this.height);
		} else {
		    this.setTranslateY(this.getTranslateY() + 1);
		    this.y++;
		}
	    } else {
		if (this.y < 0) {
		    this.setTranslateY(0);
		} else {
		    this.setTranslateY(this.getTranslateY() - 1);
		    this.y--;
		}
	    }
            
            boolean wall = isWall();
            if (down && wall) {
                this.setTranslateY(this.getTranslateY() - 1);
                this.y--;
            } else if (!down && wall) {
                this.setTranslateY(this.getTranslateY() + 1);
                this.y++;
            }
	}
    }

    public void setOffsetX(int offsetX) {
	this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
	this.offsetY = offsetY;
    }

    public int getOffsetX() {
	return offsetX;
    }

    public int getOffsetY() {
	return offsetY;
    }

    public void setX(int x) {
        this.setTranslateX(x);
	this.x = x;
    }

    public void setY(int y) {
        this.setTranslateX(y);
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public void hit(int dmg) {
        if (shield) {
            shieldHealth-=dmg;
        } else {
            health-=dmg;
        }
    }
    
    public void setHealth(int i) {
	health = i;
    }
    
    public int getHealth() {
	return health;
    }
    
    public int getFullHealth() {
        return fullHealth;
    }

    public boolean isAlive() {
	alive = health != 0;
	return alive;
    }
    
    public boolean isColliding(Stairs stair) {
	return this.getBoundsInParent().intersects(stair.getBoundsInParent());
    }
    
    public boolean isColliding(Friends friend) {
        return this.getBoundsInParent().intersects(friend.getBoundsInParent());
    }
    
    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }
    
    public int getPlayerSpeed() {
        return playerSpeed;
    }
    
    public void setShootSpeed(int shootSpeed) {
	this.shootSpeed = shootSpeed;
    }
    
    public int getShootSpeed() {
	return shootSpeed;
    }
    
    public void addShield(boolean a) {
        shield = a;
        if (a) shieldHealth = fullShieldHealth;
        else shieldHealth = 0;
    }
    
    public boolean hasShield() {
        return shield;
    }
    
    public int getShieldHealth() {
        return shieldHealth;
    }
    
    public int getFullShieldHealth() {
        return fullShieldHealth;
    }
    
    public boolean isWall() {
        stop = false;
        for (Rectangle rect : Main.shopRootWalls) {
            if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && Main.level.isShopping()) {
                stop = true;
            }
        }
        return stop;
    }
}
