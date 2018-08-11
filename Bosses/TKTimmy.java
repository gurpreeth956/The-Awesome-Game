package Bosses;
import Game.Character;
import Game.SpriteAnimation;

import java.util.List;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TKTimmy extends MeleeBoss {
    
    SpriteAnimation timmy;
    private final int count = 20;
    private final int columns = 5;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final int width;
    private final int height;
    private final Duration duration = Duration.millis(1200);
    private final Animation animation;
    private boolean attacking;
    private boolean centered = false;
    
    private final String leftSpin = "file:src/Sprites/SpinningTimmyLeft.png";
    private final String rightSpin = "file:src/Sprites/SpinningTimmyRight.png";
    private final String basic = "file:src/Sprites/TKTimmyBasic.png";
    
    
    public TKTimmy(String img, int health, int coin, int width, int height) {
        super(img, health, coin, width, height, "SPINING TIMMY");
        super.getChildren().remove(super.getIV());
        timmy = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration);         
        animation = timmy;
        attacking = false;
        super.setIV(timmy.getIV());
        super.getIV().setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        getChildren().addAll(super.getIV());   
        this.width = width;
        this.height = height;
        
        //change below to true if collision rectangles are added
        hasCollisionRects = false;
    }
    
    @Override
   public void move(Character player, double width, double height) {     
       if (!centered)
           centerMove(width, height);
       else {
            if(!attacking) {
                setIV(basic);
                trackingView(player);
            }
       }
    }
   
   public void setIV(String image) {
       getChildren().remove(super.getIV());
       super.getChildren().remove(super.getIV());
       timmy.setIV(image);
       super.setIV(timmy.getIV());
       super.getIV().setViewport(new Rectangle2D(offsetX, offsetY, width, height));
       getChildren().add(super.getIV());
   }
   
   public void centerMove(double width, double height) {
        double timwcenter = this.getX() + 95;
        double timhcenter = this.getY() + 95;
        double wcenter = width / 2;
        double hcenter = height / 2;
        if (wcenter > timwcenter && hcenter == timhcenter) { //right
            this.moveX(1, width);
        }
        if (wcenter < timwcenter && hcenter == timhcenter) { //left
            this.moveX(-1, width);
        }
        if (wcenter == timwcenter && hcenter  > timhcenter) { //down
            this.moveY(1, height);
        }
        if (wcenter == timwcenter && hcenter < timhcenter) { //up
            this.moveY(-1, height);
        }

        if (wcenter > timwcenter && hcenter < timhcenter) { //quadrant1
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (wcenter < timwcenter && hcenter < timhcenter) { //quadrant2
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (wcenter < this.getX() && hcenter > timhcenter) { //quadrant3
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (wcenter > timwcenter && hcenter > timhcenter) { //quadrant4
            this.moveX(1, width);
            this.moveY(1, height);
        }
        if (wcenter == timwcenter && hcenter == timhcenter)
            centered = true;
   }
   
   public void trackingView(Character player) {
        if (player.getY() > this.getY() + height) { //down
            this.setCharacterView(0, 0);
        }
        else if (player.getY() < this.getY()) { //up
            this.setCharacterView(0, 190);
        }     
        else {
            if(player.getX() < this.getX())
                this.setCharacterView(190, 0); //left
            if(player.getX() > this.getX() + width)
                this.setCharacterView(190,190); //right
        }
   }
   
  // public void attack
   
   
   
}