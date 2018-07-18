package Upgrades;
import Game.Character;

public class PlayerSpeedUpgrade extends Upgrades {

    public PlayerSpeedUpgrade() {
        super("file:src/Sprites/Speed.png", 2);
    }
    
    public void activeAbility(Character player) {
        player.setPlayerSpeed(player.getPlayerSpeed() + 1);
    }
    
    public String getListView() {
        return "Player Speed";
    }
    
    public String getSummary() {
        return "";
    }
}
