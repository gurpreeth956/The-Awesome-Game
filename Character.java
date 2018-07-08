
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Character extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 66;
    int height = 33;
    int x; //Character xPos
    int y; //Character yPos
    int shootSpeed;

    int health = 5;
    int fullHealth = 5;
    boolean alive = true;

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
	this.shootSpeed = 500;
    }

    public void setCharacterView(int offsetX, int offsetY) {
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void moveX(int x, double width) {
	boolean right = x > 0;
	for (int i = 0; i < Math.abs(x); i++) {
	    if (right) {
		if (this.x > width - this.width) {
		    this.setTranslateX(width - this.width);
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
	}
    }

    public void moveY(int y, double height) {
	boolean down = y > 0;
	for (int i = 0; i < Math.abs(y); i++) {
	    if (down) {
		if (this.y > height - this.height) {
		    this.setTranslateY(height - this.height);
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
	this.x = x;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public void hit() {
	health--;
    }

    public int getHealth() {
	return health;
    }
    
    public void setHealth(int a){
	health = a;
    }

    public boolean isAlive() {
	alive = health != 0;
	return alive;
    }
    
    public boolean isColliding(Stairs stair){
	return this.getBoundsInParent().intersects(stair.getBoundsInParent());
    }
    
    public void setShootSpeed(int a){
	shootSpeed = a;
    }
    
    public int getShootSpeed(){
	return shootSpeed;
    }

}
