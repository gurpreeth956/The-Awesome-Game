import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Projectile extends Pane {
    
    ImageView iv;
    int width = 13;
    int height = 13;
    int offsetX = 8;
    int offsetY = 8;
    int x;
    int y;
    
    int velocityX = 0;
    int velocityY = 0;
    boolean isAlive = true;
    
    public Projectile(ImageView iv, int posX, int posY) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.getChildren().addAll(iv);
    }
    
    public void move() {
        this.setTranslateX(this.getTranslateX() + this.getVelocityX());
        this.setTranslateY(this.getTranslateY() + this.getVelocityY());
    }
    
    public int getVelocityX() {
        return velocityX;
    }
    
    public int getVelocityY() {
        return velocityY;
    }
    
    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }
    
    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setAlive(boolean i) {
        isAlive = i;
    }
}
