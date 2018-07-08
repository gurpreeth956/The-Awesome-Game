
public class HealthPackUpgrade extends Upgrades{
    
    public HealthPackUpgrade(){
	super("file:src/Sprites/HealthPack.png",10);
    }
    
    public void activeAbility(Character player){
	player.setHealth(player.fullHealth);
    }
}
