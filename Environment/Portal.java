package Environment;
import Game.SpriteAnimation;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Portal extends Pane {
    
    ImageView iv;
    SpriteAnimation portalia;
    private final int count = 4;
    private final int columns = 4;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final int width = 52;
    private final int height = 110;
    private final Duration duration = Duration.millis(900);
    private final Animation animation;
    int x; //Portal xPos
    int y; //Portal yPos
    
    public Portal(int screenWidth, int screenHeight) {
	Image portImage = new Image("file:src/Sprites/Portal.png");
	ImageView portIV = new ImageView(portImage);
	this.iv = portIV;
	this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	this.x = (int)(Math.random() * screenWidth);
	this.y = (int)(Math.random() * screenHeight);
	this.setTranslateX(this.x);
        this.setTranslateY(this.y);
        
        portalia = new SpriteAnimation("file:src/Sprites/Portal.png", count, columns, 
            offsetX, offsetY, width, height, duration);
        animation = portalia;
        iv = portalia.getIV();
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().addAll(iv);
        animation.play();
    }
    
    public boolean summon() { //summons enemy from portal
	return Math.random() < 0.01;
    }
    
    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
}
