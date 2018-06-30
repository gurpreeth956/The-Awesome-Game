import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Portal extends Pane{
    ImageView iv;
    int porX;
    int porY;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x;
    int y;
    
    public Portal(ImageView iv,int width, int height){
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	this.x = (int)(Math.random() * (width));
	this.y = (int)(Math.random() * height);//generates random location for portal
	this.getChildren().addAll(iv);
    }
    
    public boolean summon(){
	return Math.random()<.001;//summons enemy from portal
    }
}
