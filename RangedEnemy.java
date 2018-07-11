
public class RangedEnemy extends Enemy{
    
    long timeOfLastProjectile = 0;
    int shootSpeed;
    
    public RangedEnemy(String img, int health, int coin, int width, int height, int shootSpeed){
        super(img, health, coin, width, height);
        this.shootSpeed = shootSpeed;
    }
    
    //Change characterview and movex/y elements 
    public void move(Character player, double width, double height) {
	if (player.getX() > this.getX() && player.getY() == this.getY()) { //right
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
        }
        if (player.getX() < this.getX() && player.getY() == this.getY()) { //left
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
        }
        if (player.getX() == this.getX() && player.getY() > this.getY()) { //down
            this.setCharacterView(0, 0);
            this.moveY(1, height, 1);
        }
        if (player.getX() == this.getX() && player.getY() < this.getY()) { //up
            this.setCharacterView(0, 183);
            this.moveY(-1, height, 1);
        }

        if (player.getX() > this.getX() && player.getY() < this.getY()) { //quadrant1
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() < this.getY()) { //quadrant2
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
            this.moveY(-1, height, 1);
        }
        if (player.getX() > this.getX() && player.getY() > this.getY()) { //quadrant3
            this.setCharacterView(0, 61);
            this.moveX(1, width, 1);
            this.moveY(1, height, 1);
        }
        if (player.getX() < this.getX() && player.getY() > this.getY()) { //quadrant4
            this.setCharacterView(0, 123);
            this.moveX(-1, width, 1);
            this.moveY(1, height, 1);
        }
    }
    
    public void shoot(){
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastProjectile;

        if () {//Shoot up
            this.setCharacterView(128, 183);
            this.setOffsetY(183);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(0, -9);
                timeOfLastProjectile = timeNow;
            }

        } else if () { //shoot down
            this.setCharacterView(128, 0);
            this.setOffsetY(0);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(0, 9);
                timeOfLastProjectile = timeNow;
            }

        } else if () {//shoot left
            this.setCharacterView(128, 123);
            this.setOffsetY(123);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(-9, 0);
                timeOfLastProjectile = timeNow;
            }

        } else if () {//shoot right
            this.setCharacterView(128, 61);
            this.setOffsetY(61);
            if (time < 0 || time > this.getShootSpeed()) {
                createProjectile(9, 0);
                timeOfLastProjectile = timeNow;
            }
        }
    }
    
    public void createProjectile(int a, int b){
        
    }
    
    public int getShootSpeed(){
        return shootSpeed;
    }
}
