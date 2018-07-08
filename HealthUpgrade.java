
public class HealthUpgrade extends Upgrades{
    
    public HealthUpgrade(){
	super("file:src/HealthUp.png",50);
    }
    
    public void activeAbility(Character player){
	player.setHealth(player.fullHealth+1);
    }
}
