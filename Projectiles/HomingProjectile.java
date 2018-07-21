package Projectiles;
import Game.Character;

public class HomingProjectile extends Projectile {
    
    public HomingProjectile(String img, int posX, int posY, int width, int height) {
        super(img, posX, posY, width, height);
    }
    
    public void move(Character player) {
        String dist = distance(player);
        
        if (dist.equals("up")) {
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
        } 
        if (dist.equals("down")) {
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
        }
        if (dist.equals("left")) {
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
        }
        if (dist.equals("right")) {
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
        }
    }
    
    public String distance(Character player) {
        int vert = player.getY() - this.y;
        int hori = player.getX() - this.x;
        
        if(Math.abs(vert) > Math.abs(hori)) {
            if(vert <= 0) {
                return "up";
            }
            return "down";
        }
        else if(hori <= 0) {
            return "left";
        }
        return "right";
    }
}
