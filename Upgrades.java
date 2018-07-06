
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Upgrades extends Pane{
    ImageView iv;
    boolean active;
    boolean purchased;
    int cost;
    int width=10;
    int height=10;
    
    public Upgrades(String pic, int price){
	Image downImage = new Image(pic);
	ImageView downIV = new ImageView(downImage);
	this.iv = downIV;
	cost = price;
	active = false;
	purchased = false;
    }
    
    public void setActive(boolean a){
	active = a;
    }
    
    public void setBrought(boolean a){
	purchased = a;
    }
    
    public boolean isActive(){
	return active;
    }
    
}
