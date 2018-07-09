public class UpgradePlayerSpeed extends Upgrades {

    public UpgradePlayerSpeed() {
        super("file:src/Sprites/Speed.png", 2);
    }
    
    public void activeAbility(Character player) {
        player.setPlayerSpeed(player.getPlayerSpeed() + 1);
    }
}
