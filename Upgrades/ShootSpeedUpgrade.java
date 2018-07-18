package Upgrades;
import Game.Character;

public class ShootSpeedUpgrade extends Upgrades {
    
    public ShootSpeedUpgrade() {
	super("file:src/Sprites/ShootSpeed.png", 100);
    }
    
    public void activeAbility(Character player) {
	player.setShootSpeed(player.getShootSpeed() / 2);
    }
    
    public String getListView() {
        return "Shooting Speed";
    }
    
    public String getSummary() {
        return "";
    }
}
