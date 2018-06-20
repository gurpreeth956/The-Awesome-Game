//package pkg2dsidescroll; //(Ray's Package)

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Character extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 66;
    int height = 33;
    
    public Character(ImageView iv, int posX, int posY) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        
        this.getChildren().addAll(iv);
    }
    
    public void setCharacterView(int offsetX, int offsetY) {
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
    }
    
    public void moveX(int x) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
        }
    }
    
    public void moveY(int y) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }
}
