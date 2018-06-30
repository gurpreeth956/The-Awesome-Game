import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Portal extends Pane {
    
    ImageView iv;
    int offsetX = 32;
    int offsetY = 20;
    int width = 36;
    int height = 60;
    int x; //Portal xPos
    int y; //Portal yPos
    
    public Portal(ImageView iv, int screenWidth, int screenHeight) {
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	this.x = (int)(Math.random() * screenWidth);
	this.y = (int)(Math.random() * screenHeight);
	this.setTranslateX(this.x);
        this.setTranslateY(this.y);
	this.getChildren().addAll(iv);
    }
    
    public boolean summon() { //summons enemy from portal
	return Math.random() < 0.01;
    }
    
    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
}
