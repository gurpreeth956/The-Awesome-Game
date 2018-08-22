package Projectiles;
import Game.Main;
import Game.Character;
import javafx.geometry.Rectangle2D;

public class HomingProjectile extends Projectile {
    
    double targetX;
    double targetY;
    double vy;
    double vx;
    double rotation;
    int ease = 50;//change this to increase turn radius of missile
    int launchPos;//angle of initial launch
    
    long speedTime=0;
    long timeSpawned;
    boolean spawned = false;

    public HomingProjectile(String img, int posX, int posY, int width, int height, int dmg, int launchPos) {
        super(img, posX, posY, width, height, dmg);
        this.launchPos = launchPos;
        timeSpawned = 0;
    }

    //Link to code for homing missile
    //https://code.tutsplus.com/tutorials/hit-the-target-with-a-deadly-homing-missile--active-8933
    public void move(Character player) {
        long timeNow = System.currentTimeMillis();
        if (!spawned) {
            timeSpawned = timeNow;
            spawned = true;
        }
        
        targetX = player.getX() - this.x;
        targetY = player.getY() - this.y;
        rotation = Math.atan2(targetY, targetX) * 180 / Math.PI;
        this.iv.setRotate(rotation);
        
        //never miss homing missile
        vx = this.getVelocityX() * (90 - Math.abs(rotation)) / 90;
        if (rotation < 0) {
            vy = -this.getVelocityX() + Math.abs(vx);
        } else {
            vy = this.getVelocityX() - Math.abs(vx);
        }
        
        //Code for homing missile with turn delay (bugged)
        /*if (Math.abs(rotation - launchPos) > 180) {
            if (rotation > 0 && launchPos < 0) {
               launchPos -= (360 - rotation + launchPos) / ease;
            }
            else if (launchPos > 0 && rotation < 0){
                launchPos += (360 - rotation + launchPos) / ease;
            }
        } else if (rotation < launchPos) {
            launchPos -= Math.abs(launchPos - rotation) / ease;
        } else {
            launchPos += Math.abs(rotation - launchPos) / ease;
        }
        vx = this.getVelocityX() * (90-Math.abs(launchPos)) / 90;
        if(launchPos<0){
            vy = -this.getVelocityX() + Math.abs(vx);//up
        } else {
            vy = this.getVelocityX() - Math.abs(vx);//down
        }*/
        
        this.setTranslateX(this.getTranslateX()+(int)vx);
        this.setTranslateY(this.getTranslateY()+(int)vy);
        this.x += (int)vx; //X is int and vx is double 
        this.y += (int)vy; //casting to int improves missile accuracy
        
        if (timeNow - timeSpawned >= 10000) {
            if (Math.abs(player.getX() - this.x + 32) <= 80 && Math.abs(player.getY() - 
                    this.y + 16) <= 80) {
                player.hit(this.getDamage());
                Main.playerReceiveHit();
            }
            this.setAlive(false);
        }else if(timeNow-6000>=speedTime){
            this.setVelocityX(this.getVelocityX()+1);
            this.setVelocityY(this.getVelocityY()+1);
            if(speedTime != 0){
                this.iv.setViewport(new Rectangle2D(0, 11, width, height));
            }
            speedTime = timeNow;
        }
    }
}
