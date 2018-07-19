package Upgrades;
import Game.Character;
import javafx.scene.image.Image;

public class PlayerSpeedUpgrade extends Upgrades {

    public PlayerSpeedUpgrade() {
        super("file:src/Sprites/Speed.png", 2);
        cost = 2;
    }
    
    public void activeAbility(Character player) {
        player.setPlayerSpeed(player.getPlayerSpeed() + 1);
    }
    
    public String getListView() {
        return "Player Speed   -   " + cost;
    }
    
    public String getSummary() {
        return "";
    }
    
    public Image getImage() {
        return new Image("file:src/Sprites/Speed.png");
    }
}
