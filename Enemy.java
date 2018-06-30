import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 66;
    int height = 33;
    int x; //Enemy xPos
    int y; //Enemy yPos
    int score;
    
    Rectangle healthBarOutline;
    Rectangle actualHealth;
    Rectangle lostHealth;
    boolean alive = true;
    int health;

    public Enemy(ImageView iv, int posX, int posY, int health, int score) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
	this.health = health;
	this.score = score;
        this.getChildren().addAll(iv);
        
	healthBarOutline = new Rectangle(x - 1, y - 6, 68, 4);
	healthBarOutline.setFill(Color.TRANSPARENT);
	healthBarOutline.setStroke(Color.BLACK);
	lostHealth = new Rectangle(x, y - 5, 66, 3);
	lostHealth.setFill(Color.RED);
	actualHealth = new Rectangle(x, y - 5, 66, 3);
	actualHealth.setFill(Color.GREEN);
	actualHealth.toFront();
    }
    
    public void setCharacterView(int offsetX, int offsetY) {
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
    }
    
    public void moveX(int x, double width) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                if(this.x > width - this.width)
                    this.setTranslateX(width - this.width);
                else {
                    this.setTranslateX(this.getTranslateX() + 1);
                    this.x++;
                }
            }
            else  {
                if(this.x < 0)
                    this.setTranslateX(0);
                else {
                    this.setTranslateX(this.getTranslateX() - 1);
                    this.x--;
                }
            }
	    this.healthPos();
        }
    }
    
    public void moveY(int y, double height) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                if(this.y > height - this.height)
                    this.setTranslateY(height - this.height);
                else {
                    this.setTranslateY(this.getTranslateY() + 1);
                    this.y++;
                }
            }
            else {
                if(this.y < 0)
                    this.setTranslateY(0);
                else {
                    this.setTranslateY(this.getTranslateY() - 1);
                    this.y--;
                }
            }
	    this.healthPos();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isAlive(){
	return alive;
    }
    
    public void setAlive(boolean alive){
	this.alive = alive;
    }
    
    public void hit(){
	health--;
    }
    
    public int getHealth(){
	return health;
    }
    
    public Rectangle updateHealth(){
	actualHealth = new Rectangle(x, y, this.getHealth() * 22, 3);
	actualHealth.setFill(Color.GREEN);
	return actualHealth;
    }
    
    public void healthPos(){
	actualHealth.setX(this.x);
	actualHealth.setY(this.y - 5);
	lostHealth.setX(this.x);
	lostHealth.setY(this.y - 5);
	healthBarOutline.setX(this.x - 1);
	healthBarOutline.setY(this.y - 6);
    }
    
    public boolean playerColliding(Character player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }
    
    public boolean enemyColliding(List<Enemy> enemies){
	boolean colliding = false;
	for(Enemy enemy : enemies){
	    if(this.x == enemy.x && this.y == enemy.y) continue;
	    if(this.getBoundsInParent().intersects(enemy.getBoundsInParent())){
		colliding = true;
	    }
	}
	return colliding;
    }
    
    public int getScore(){
	return score;
    }
}
