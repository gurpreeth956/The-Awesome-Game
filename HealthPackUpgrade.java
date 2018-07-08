
public class HealthPackUpgrade extends Upgrades{
    
    public HealthPackUpgrade(){
	super("file:src/HealthPack.png",10);
    }
    
    public void activeAbility(Character player){
	player.setHealth(player.fullHealth);
    }
}
