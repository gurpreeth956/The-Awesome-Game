import javafx.scene.image.ImageView;

public class Enemy extends Character {
    
    int characterXpos;
    int characterYpos;

    public Enemy(ImageView iv, int posX, int posY) {
        super(iv, posX, posY);
    }
    
    public void playerPos(int playerX, int playerY) {
        characterXpos = playerX;
        characterYpos = playerY;
    }
    
    public void followPlayer() {
        
    }
}