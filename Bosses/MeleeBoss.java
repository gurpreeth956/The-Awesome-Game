package Bosses;
import Enemies.MeleeEnemy;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

//This is a parent class not a boss
public class MeleeBoss extends MeleeEnemy {
    
    public MeleeBoss(String img, int health, int coin, int width, int height, String bossName) {
	super(img, health, coin, width, height);
        super.getChildren().removeAll(healthBarOutline, lostHealth, actualHealth, nameLabel);
        
        nameLabel = new Label(bossName);
        nameLabel.setFont(new Font("Arial", 25));
        nameLabel.setTextFill(Color.RED);
        nameLabel.setTranslateX(10);
        nameLabel.setTranslateY(700);
        healthBarOutline = new Rectangle(355, 20, 541, 20);
	healthBarOutline.setFill(Color.TRANSPARENT);
	healthBarOutline.setStroke(Color.BLACK);
	lostHealth = new Rectangle(356, 21, 540, 19);
	lostHealth.setFill(Color.RED);
	actualHealth = new Rectangle(356, 21, 540, 19);
	actualHealth.setFill(Color.GREEN);
	actualHealth.toFront();
    }
    
    public Rectangle updateHealth() {
	actualHealth = new Rectangle(356, 21, this.getHealth() * (540 / this.totalHealth), 19);
	actualHealth.setFill(Color.GREEN);
	return actualHealth;
    }
    
    public void healthPos() {
	actualHealth.setX(356);
	actualHealth.setY(21);
	lostHealth.setX(356);
	lostHealth.setY(21);
	healthBarOutline.setX(355);
	healthBarOutline.setY(20);
    }
}
