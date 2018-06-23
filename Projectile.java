
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Projectile extends Pane{
    
    ImageView iv;
    int width = 13;
    int height = 13;
    int x;
    int y;
    int offsetX=8;
    int offsetY=8;
	    
    
    public Projectile(ImageView iv, int posX, int posY) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.getChildren().addAll(iv);
    }
    
    public void shootX(int x, double width) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                if(this.x > width - this.width)
                    this.setTranslateX(width - this.width);
                else {
                    this.setTranslateX(this.getTranslateX() + 3);
                    this.x++;
                }
            }
            else  {
                if(this.x < 0)
                    this.setTranslateX(0);
                else {
                    this.setTranslateX(this.getTranslateX() - 3);
                    this.x--;
                }
            }
        }
    }
    
    public void shootY(int y, double height) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                if(this.y > height - this.height)
                    this.setTranslateY(height - this.height);
                else {
                    this.setTranslateY(this.getTranslateY() + 3);
                    this.y++;
                }
            }
            else {
                if(this.y < 0)
                    this.setTranslateY(0);
                else {
                    this.setTranslateY(this.getTranslateY() - 3);
                    this.y--;
                }
            }
        }
    }
    
    
}
