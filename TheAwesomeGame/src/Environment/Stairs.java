package Environment;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Stairs extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 65;
    int height = 47;
    int x; //Portal xPos
    int y; //Portal yPos

    public Stairs(String level, int screenWidth, int screenHeight) {
	if (level.equals("down")) {
	    Image downImage = new Image("file:src/Sprites/Downstairs.png");
	    ImageView downIV = new ImageView(downImage);
	    this.iv = downIV;
	    this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	    this.x = screenWidth / 2 - 65;
	    this.y = screenHeight / 2 - 47;
	    this.setTranslateX(this.x);
	    this.setTranslateY(this.y);
	    this.getChildren().addAll(iv);
	} else if (level.equals("shop")) {
	    Image image = new Image("file:src/Sprites/Downstairs.png");
	    ImageView shopIV = new ImageView(image);
	    this.iv = shopIV;
	    this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	    this.x = screenWidth - 100;
	    this.y = screenHeight - 100;
	    this.setTranslateX(this.x);
	    this.setTranslateY(this.y);
	    this.getChildren().addAll(iv);
	} else if (level.equals("up")) {
	    Image upImage = new Image("file:src/Sprites/Upstairs.png");
	    ImageView upIV = new ImageView(upImage);
	    this.iv = upIV;
	    this.iv.setViewport(new Rectangle2D(offsetX, offsetY, this.width, this.height));
	    this.x = screenWidth / 2 - 65;
	    this.y = screenHeight / 2 - 47;
	    this.setTranslateX(this.x);
	    this.setTranslateY(this.y);
	    this.getChildren().addAll(iv);
	}
    }
}
