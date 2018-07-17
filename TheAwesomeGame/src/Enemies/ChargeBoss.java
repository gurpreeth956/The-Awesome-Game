package Enemies;


import Enemies.MeleeEnemy;


public class ChargeBoss extends MeleeEnemy{
    
    int stunTime = 500;
    long lastCharge = 0;
    
    public ChargeBoss(String img, int health, int coin, int width, int height){
        super(img, health, coin, width, height);
    }
    
    public void move(Character player, double width, double height){
        //figure out direction to charge in relative to player coordinates
        //charge in direction until collision with pane border
        //use timer for stun duration??
    }
}
