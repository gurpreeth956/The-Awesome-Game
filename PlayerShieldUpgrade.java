public class PlayerShieldUpgrade extends Upgrades {
    
    public PlayerShieldUpgrade() {
	super("file:src/Sprites/Shield.png", 2);
    }
    
    public void activeAbility(Character player) {
	player.addShield(true);
    }
}
