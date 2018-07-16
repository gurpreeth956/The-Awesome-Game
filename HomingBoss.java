
import java.util.List;
import javafx.scene.layout.Pane;


public class HomingBoss extends RangedEnemy{
    
    long timeOfLastProjectile = 0;
        
    public HomingBoss(String img, int health, int coin, int width, int height, int shootSpeed){
        super(img, health, coin, width, height, shootSpeed);
    }
    
        //Change characterview and movex/y elements 
    public void move(Character player, double width, double height) {
	if (player.getX() > this.getX() && player.getY() == this.getY()) { //right
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
        }
        if (player.getX() < this.getX() && player.getY() == this.getY()) { //left
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
        }
        if (player.getX() == this.getX() && player.getY() > this.getY()) { //down
            this.setCharacterView(0, 0);
            this.moveY(1, height, 1);
        }
        if (player.getX() == this.getX() && player.getY() < this.getY()) { //up
            this.setCharacterView(0, 183);
            this.moveY(-1, height, 1);
        }

        if (player.getX() > this.getX() && player.getY() < this.getY()) { //quadrant1
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() < this.getY()) { //quadrant2
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() > this.getX() && player.getY() > this.getY()) { //quadrant3
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
            this.moveY(1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() > this.getY()) { //quadrant4
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
            this.moveY(1, height, 1);
        }
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot){
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;
        String dist = distance(player);

        if (dist.equals("up")) {//Shoot up
            this.setCharacterView(128, 0);
            this.setOffsetY(0);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(5, 5, projectiles, gameRoot, "file:src/Sprites/EnemyShot.png", 12, 12);
                timeOfLastProjectile = timeNow;
            }

        }
    }
    
    public void createProjectile(int a, int b, List<Projectile> projectiles, Pane root, String img, int width, int height){
        Projectile proj = new HomingProjectile(img, this.getX() + 28, this.getY() + 16, width, height);
        proj.setVelocityX(a);
        proj.setVelocityY(b);
        root.getChildren().addAll(proj);
        proj.toBack();
        projectiles.add(proj);
    }
    
    public int getShootSpeed(){
        return shootSpeed;
    }
    
}
