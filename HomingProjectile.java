
public class HomingProjectile extends Projectile{
    
    public HomingProjectile(String img, int posX, int posY, int width, int height) {
        super(img, posX, posY, width, height);
    }
    
    public void move(Character player){
        String dist = distance(player);
        
        if(dist.equals("up")){
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() - this.getVelocityY());
        }else if(dist.equals("down")){
            this.setTranslateX(this.getTranslateX() + 0);
            this.setTranslateY(this.getTranslateY() + this.getVelocityY());
        }else if(dist.equals("left")){
            this.setTranslateX(this.getTranslateX() - this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
        }else{
            this.setTranslateX(this.getTranslateX() + this.getVelocityX());
            this.setTranslateY(this.getTranslateY() + 0);
        }
    }
    
    public String distance(Character player){
        int vert = player.y-this.y;
        int hori = player.x-this.x;
        
        if(Math.abs(vert)>Math.abs(hori)){
            if(vert<=0){
                return "up";
            }
            return "down";
        }
        else if(hori<=0){
            return "left";
        }
        return "right";
    }
}
