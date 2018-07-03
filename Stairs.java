
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Stairs extends Pane{
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 65;
    int height = 47;
    int x; //Portal xPos
    int y; //Portal yPos
    
    
    
    public Stairs(int screenWidth, int screenHeight) {
	Image image = new Image("file:src/Stairs.png");
	ImageView iv = new ImageView(image);
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	this.x = (int)(Math.random() * screenWidth);
	this.y = (int)(Math.random() * screenHeight);
	this.setTranslateX(this.x);
        this.setTranslateY(this.y);
	this.getChildren().addAll(iv);
    }
    
    
}
