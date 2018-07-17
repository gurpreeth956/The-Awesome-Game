
public class Boss extends MeleeEnemy{
  
    public Boss(String img, int health, int coin, int width, int height){
	super(img, health, coin, width, height);
    }
    
    //Change characterViewPort for each enemy && speed
    public void move(Character player, double width, double height) {
	if (player.getX() > this.getX() && player.getY() == this.getY()) { //right
            this.setCharacterView(240, 222);
            this.moveX(1, width, 1);
        }
        if (player.getX() < this.getX() && player.getY() == this.getY()) { //left
            this.setCharacterView(0, 221);
            this.moveX(-1, width, 1);
        }
        if (player.getX() == this.getX() && player.getY() > this.getY()) { //down
            this.setCharacterView(0, 0);
            this.moveY(1, height, 1);
        }
        if (player.getX() == this.getX() && player.getY() < this.getY()) { //up
            this.setCharacterView(237, 0);
            this.moveY(-1, height, 1);
        }

        if (player.getX() > this.getX() && player.getY() < this.getY()) { //quadrant1
            this.setCharacterView(240, 222);
            this.moveX(1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() < this.getY()) { //quadrant2
            this.setCharacterView(0, 221);
            this.moveX(-1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() > this.getX() && player.getY() > this.getY()) { //quadrant3
            this.setCharacterView(240, 222);
            this.moveX(1, width, 1);
            this.moveY(1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() > this.getY()) { //quadrant4
            this.setCharacterView(0, 221);
            this.moveX(-1, width, 1);
            this.moveY(1, height, 1);
        }
    }
}
