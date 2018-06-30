import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Portal extends Pane{
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x; //Portal xPos
    int y; //Portal xPos
    
    public Portal(ImageView iv, int posX, int posY){
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
	this.x = posX;
	this.y = posY;
	this.getChildren().addAll(iv);
    }
    
    public boolean summon(){
	return Math.random() < 0.001; //summons enemy from portal
    }
}
