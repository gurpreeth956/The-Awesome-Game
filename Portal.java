import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Portal extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 44;
    int height = 92;
    int x; //Portal xPos
    int y; //Portal yPos
    
    public Portal(int screenWidth, int screenHeight) {
	Image portImage = new Image("file:src/Sprites/Portal.png");
	ImageView portIV = new ImageView(portImage);
	this.iv = portIV;
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
