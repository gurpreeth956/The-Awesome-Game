import java.util.List;
import javafx.geometry.Rectangle2D;
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

    int health = 5;
    boolean alive = true;

    public Character(ImageView iv, int posX, int posY) {
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	this.setTranslateX(posX);
	this.setTranslateY(posY);
	this.x = posX;
	this.y = posY;
	this.getChildren().addAll(iv);
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

    public boolean isAlive() {
	alive = health != 0;
	return alive;
    }
    
    public boolean isColliding(Stairs stair){
	return this.getBoundsInParent().intersects(stair.getBoundsInParent());
    }

    public boolean leftColliding(List<Enemy> enemies) {
	boolean collide = false;
	for (Enemy enemy : enemies) {
	    if(this.getBoundsInParent().intersects(enemy.getBoundsInParent()) && enemy.x < this.x){
		collide = true;
	    }
	}
	return collide;
    }
    
    public boolean rightColliding(List<Enemy> enemies) {
	boolean collide = false;
	for (Enemy enemy : enemies) {
	    if(this.getBoundsInParent().intersects(enemy.getBoundsInParent()) && enemy.x > this.x){
		collide = true;
	    }
	}
	return collide;
    }
    
    public boolean upColliding(List<Enemy> enemies) {
	boolean collide = false;
	for (Enemy enemy : enemies) {
	    if(this.getBoundsInParent().intersects(enemy.getBoundsInParent()) && enemy.y < this.y){
		collide = true;
	    }
	}
	return collide;
    }
    
    public boolean downColliding(List<Enemy> enemies) {
	boolean collide = false;
	for (Enemy enemy : enemies) {
	    if(this.getBoundsInParent().intersects(enemy.getBoundsInParent()) && enemy.y > this.y){
		collide = true;
	    }
	}
	return collide;
    }
}
