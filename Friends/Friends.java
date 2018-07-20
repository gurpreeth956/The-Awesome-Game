package Game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Friends extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x; //AI xPos
    int y; //AI yPos
    
    public Friends(String img, int width, int height) {
        Image friendImage = new Image(img);
        ImageView friendIV = new ImageView(friendImage);
        this.iv = friendIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.getChildren().addAll(iv);
    }
    
    public void setX(int x) {
        this.setTranslateX(x);
        this.x = x;
    }
    
    public void setY(int y) {
        this.setTranslateY(y);
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
