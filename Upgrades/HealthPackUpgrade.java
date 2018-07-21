package Upgrades;
import Game.Character;
import javafx.scene.image.Image;

public class HealthPackUpgrade extends Upgrades {
    
    public HealthPackUpgrade() {
	super("file:src/Sprites/HealthPack.png", 2);
        cost = 2;
    }
    
    public void activeAbility(Character player) {
	player.setHealth(player.getFullHealth());
    }
    
    public String getListView() {
        return "Health Pack   -   " + cost;
    }
    
    public String getSummary() {
        return "";
    }
    
    public Image getImage() {
        return new Image("file:src/Sprites/HealthPack.png");
    }
}
