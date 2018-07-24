package Enemies;
import Game.Character;
import Projectiles.Projectile;

//A.K.A Spikey

import java.util.List;
import javafx.scene.layout.Pane;

public class SpikeEnemy extends RangedEnemy {

    public SpikeEnemy(String img, int health, int coin, int width, int height, int shootSpeed) {
        super(img, health, coin, width, height, shootSpeed);
    }
    
    public void move(Character player, double width, double height) {
        //Y - 20 is so it looks like Spikey is aiming for middle of player
	if (player.getX() > this.getX() && player.getY() - 20 == this.getY()) { //right
            this.setCharacterView(3, 194);
            this.moveX(1, width);
        }
        if (player.getX() < this.getX() && player.getY() - 20 == this.getY()) { //left
            this.setCharacterView(3, 65);
            this.moveX(-1, width);
        }
        if (player.getX() == this.getX() && player.getY() - 20 > this.getY()) { //down
            this.setCharacterView(3, 0);
            this.moveY(1, height);
        }
        if (player.getX() == this.getX() && player.getY() - 20 < this.getY()) { //up
            this.setCharacterView(3, 130);
            this.moveY(-1, height);
        }

        if (player.getX() > this.getX() && player.getY() - 20 < this.getY()) { //quadrant1
            this.setCharacterView(74, 130);
            this.moveX(1, width);
            this.moveY(-1, height);
        }
        if (player.getX() < this.getX() && player.getY() - 20 < this.getY()) { //quadrant2
            this.setCharacterView(74, 65);
            this.moveX(-1, width);
            this.moveY(-1, height);
        }
        if (player.getX() < this.getX() && player.getY() - 20 > this.getY()) { //quadrant3
            this.setCharacterView(74, 0);
            this.moveX(-1, width);
            this.moveY(1, height);
        }
        if (player.getX() > this.getX() && player.getY() - 20 > this.getY()) { //quadrant4
            this.setCharacterView(74, 194);
            this.moveX(1, width);
            this.moveY(1, height);
        }
    }
    
    public void shoot(Character player, List<Projectile> projectiles, Pane gameRoot) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;

        if (time < 0 || time > this.getShootSpeed()) {
            Spikes spike = new Spikes(this.x, this.y, gameRoot);
            gameRoot.getChildren().addAll(spike);
            timeOfLastProjectile = timeNow;
        }
    }

    public void hitView(Enemy enemy) {
        this.setCharacterView(3, 0);
    }
}
