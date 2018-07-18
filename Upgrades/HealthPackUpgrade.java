package Upgrades;
import Game.Character;

public class HealthPackUpgrade extends Upgrades {
    
    public HealthPackUpgrade() {
	super("file:src/Sprites/HealthPack.png", 2);
    }
    
    public void activeAbility(Character player) {
	player.setHealth(player.getFullHealth());
    }
    
    public String getListView() {
        return "Health Pack";
    }
    
    public String getSummary() {
        return "";
    }
}
