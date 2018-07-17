package Upgrades;
import Game.Character;

public class HealthPackUpgrade extends Upgrades {
    
    public HealthPackUpgrade() {
	super("file:src/Sprites/HealthPack.png", 2);
    }
    
    public void activeAbility(Character player) {
	player.setHealth(player.getFullHealth());
    }
}
