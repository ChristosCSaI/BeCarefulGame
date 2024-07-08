import greenfoot.*;

public class Enemy extends Actor {
    
    private int damage;
    private int movementSpeed;

public Enemy(int damage, int movementSpeed) {
        this.damage = damage;
        this.movementSpeed=movementSpeed;
        GreenfootImage image = new GreenfootImage("Wale.jpg");
        setImage(image);
}

public void act() {
        moveTowardsPlayer();
        checkCollision();
        
}

private void moveTowardsPlayer() {
        Actor player = getWorld().getObjects(Player.class).get(0);
        if (player != null) {
            turnTowards(player.getX(), player.getY());
            move(movementSpeed);
        }
}

private void checkCollision() {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            player.handleDamage(damage);
            getWorld().removeObject(this);
        }
}

public boolean checkIntersection(Actor otherActor) {
        int myLeft = getX() - getImage().getWidth() / 2;
        int myRight = getX() + getImage().getWidth() / 2;
        int myTop = getY() - getImage().getHeight() / 2;
        int myBottom = getY() + getImage().getHeight() / 2;

        int otherLeft = otherActor.getX() - otherActor.getImage().getWidth() / 2;
        int otherRight = otherActor.getX() + otherActor.getImage().getWidth() / 2;
        int otherTop = otherActor.getY() - otherActor.getImage().getHeight() / 2;
        int otherBottom = otherActor.getY() + otherActor.getImage().getHeight() / 2;

        return myRight >= otherLeft &&
               myLeft <= otherRight &&
               myBottom >= otherTop &&
               myTop <= otherBottom;
}

public int getDamage() {
        return damage;
}

   
}
