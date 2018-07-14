
import java.util.List;
import javafx.scene.layout.Pane;

public class SpikeEnemy extends RangedEnemy {

    public SpikeEnemy(String img, int health, int coin, int width, int height, int shootSpeed) {
        super(img, health, coin, width, height, shootSpeed);
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;

        this.setCharacterView(128, 0);
        this.setOffsetY(0);
        if (time < 0 || time > this.getShootSpeed()) {
            createProjectile(0, 0, projectiles, gameRoot, "file:src/Sprites/Spikes.png", 28, 28);
            timeOfLastProjectile = timeNow;
        }
    }
    
    //Override for different sprites
    /*public void hitView(Enemy enemy){
        this.setCharacterView(0,0);
    }*/
}
