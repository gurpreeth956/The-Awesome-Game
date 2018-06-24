import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 66;
    int height = 33;
    int x; //Enemy xPos
    int y; //Enemy yPos
    
    int characterXpos;
    int characterYpos;

    public Enemy(ImageView iv, int posX, int posY) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.getChildren().addAll(iv);
    }
    
    public void playerPos(int playerX, int playerY) {
        characterXpos = playerX;
        characterYpos = playerY;
    }
    
    public void followPlayer() {
        
    }
}