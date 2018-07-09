public class ShootSpeedUpgrade extends Upgrades {
    
    public ShootSpeedUpgrade() {
	super("file:src/Sprites/ShootSpeed.png", 2); //made this 2 for testing stuff
    }
    
    public void activeAbility(Character player) {
	player.setShootSpeed(player.getShootSpeed() / 2);
    }
}
