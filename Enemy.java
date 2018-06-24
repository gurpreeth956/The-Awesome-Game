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
    boolean alive;

    public Enemy(ImageView iv, int posX, int posY) {
        this.iv = iv;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.getChildren().addAll(iv);
    }
    
    public void setCharacterView(int offsetX, int offsetY) {
        this.iv.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
    }
    
    public void playerPos(int playerX, int playerY) {
        characterXpos = playerX;
        characterYpos = playerY;
    }
    
    public void followPlayer(double width, double height) {
        if(this.characterXpos<this.x&&this.characterYpos<this.y){
	    this.setCharacterView(0,123);
	    this.moveY(-2,height);//up 
	    this.offsetY=183;
	    this.moveX(-2,width);//left
	    this.offsetY=123;
	}
	else if(this.characterXpos>this.x&&this.characterYpos<this.y){
	    this.setCharacterView(0,61);
	    this.moveY(-2,height);//up 
	    this.offsetY=183;
	    this.moveX(2,width);//right
	    this.offsetY=61;
	}
	else if(this.characterYpos>this.y){
	    this.setCharacterView(0,123);
	    this.moveY(2,height);//down
	    this.offsetY=123;
	    this.moveX(-2,width);//left
	    this.offsetY=123;
	}
	else if(this.characterYpos>this.y){
	    this.setCharacterView(0,61);
	    this.moveY(2, height);//down
	    this.offsetY=61;
	    this.moveX(2,width);//right
	    this.offsetY=61;
	}
	else{
	    this.setCharacterView(0,offsetY);
	}
    }
    
       public void moveX(int x, double width) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                if(this.x > width - this.width)
                    this.setTranslateX(width - this.width);
                else {
                    this.setTranslateX(this.getTranslateX() + 1);
                    this.x++;
                }
            }
            else  {
                if(this.x < 0)
                    this.setTranslateX(0);
                else {
                    this.setTranslateX(this.getTranslateX() - 1);
                    this.x--;
                }
            }
        }
    }
    
    public void moveY(int y, double height) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                if(this.y > height - this.height)
                    this.setTranslateY(height - this.height);
                else {
                    this.setTranslateY(this.getTranslateY() + 1);
                    this.y++;
                }
            }
            else {
                if(this.y < 0)
                    this.setTranslateY(0);
                else {
                    this.setTranslateY(this.getTranslateY() - 1);
                    this.y--;
                }
            }
        }
    }

}