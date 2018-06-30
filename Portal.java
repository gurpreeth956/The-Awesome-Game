import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Portal extends Pane{
    ImageView iv;
    int offsetX = 32;
    int offsetY = 20;
    int width=47;
    int height=60;
    int x;
    int y;
    
    public Portal(ImageView iv,int width, int height){
	this.iv = iv;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	this.x = (int)(Math.random() * width);
	this.y = (int)(Math.random() * height);//generates random location for portal
	this.setTranslateX(this.x);
        this.setTranslateY(this.y);
	this.getChildren().addAll(iv);
    }
    
    public boolean summon(){
	return Math.random()<.01;//summons enemy from portal
    }
    
    public int getX(){
	return x;
    }
    
    public int getY(){
	return y;
    }
}
