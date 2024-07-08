import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Player extends Actor {
    
    private static final int MOVE_SPEED = 3;
    private int health;
    private static final int MAX_HEALTH = 100;
    private int score;
    private MyWorld world;
    private int speed;
    private int lives;
    private static final int SCORE_NEEDED_FOR_NEXT_LEVEL = 100;

public Player() {
        
        health = MAX_HEALTH;
        score = 0;
        speed=1;
        lives=3;
}

public void setWorld(MyWorld world) {
        this.world = world;
}

public void act() {
        handleMovement();
        checkCollision();
        checkLevelTransition();
       
}

private void handleMovement() {
       
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - MOVE_SPEED, getY());
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + MOVE_SPEED, getY());
        }
        if (Greenfoot.isKeyDown("up")) {
            setLocation(getX(), getY() - MOVE_SPEED);
        }
        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + MOVE_SPEED);
        }
}

private void checkLevelTransition() {
        
        if (getX() >= getWorld().getWidth() - 10 && getWorld() instanceof Level1) {
            MyWorld world = (MyWorld) getWorld();
            world.transitionToNextLevel();
        }
}



public void handleDamage(int damage) {
            health -= damage;
            if (health < 0) {
                health = 0;
            }
}

private void checkCollision() {
    Actor obstacle = getOneIntersectingObject(Obstacle.class);
    if (obstacle != null) {
        if (obstacle instanceof HighPointObstacle) {
            // Handle collision with HighPointObstacle
            handleHighPointObstacleCollision((HighPointObstacle) obstacle);
        } else if (obstacle instanceof PointObstacle) {
            // Handle collision with PointObstacle
            handlePointObstacleCollision((PointObstacle) obstacle);
        } else if (obstacle instanceof NegativePointObstacle) {
            // Handle collision with NegativePointObstacle
            handleNegativePointObstacleCollision((NegativePointObstacle) obstacle);
        }
        // Check for game-over condition when hitting any obstacle
        checkGameOver();
    }
}

private void checkGameOver() {
        if (getWorld() instanceof MyWorld) {
            MyWorld world = (MyWorld) getWorld();
            if (world.getLives() <= 0) {
                world.gameOver();
            }
        }
}
    
private void handleHighPointObstacleCollision(HighPointObstacle highPointObstacle) {
            // Implement handling for collision with HighPointObstacle
            addScore(highPointObstacle.getPoints());
            getWorld().removeObject(highPointObstacle);
            ((MyWorld) getWorld()).removeAndRespawnEnemy(); 
}

private void handlePointObstacleCollision(PointObstacle pointObstacle) {
            // Implement handling for collision with PointObstacle
            addScore(pointObstacle.getPoints()); // Add points obtained from PointObstacle
        getWorld().removeObject(pointObstacle); 
        ((MyWorld) getWorld()).removeAndRespawnEnemy(); // Remove the PointObstacle from the world
}

private void handleNegativePointObstacleCollision(NegativePointObstacle negativePointObstacle) {
    
        addScore(negativePointObstacle.getPoints());
        getWorld().removeObject(negativePointObstacle); 
       ((MyWorld) getWorld()).removeAndRespawnEnemy(); 
        world.showStatus();

    
        if (score < 0) {
            loseLife();
        }
}
        
public void addScore(int points) {
            
     score += points;
        if (getWorld() instanceof MyWorld) {
            MyWorld world = (MyWorld) getWorld();
            world.addScore(points);
        if (world.getScore() >= SCORE_NEEDED_FOR_NEXT_LEVEL) {
            world.transitionToNextLevel();
        }
        
    }
}


     
private void handleCollision(Obstacle obstacle) {
    if (obstacle instanceof HighPointObstacle) {
        int points = ((HighPointObstacle) obstacle).getPoints();
        
        world.addScore(points); 
    } else if (obstacle instanceof PointObstacle) {
        int points = ((PointObstacle) obstacle).getPoints();
        
        world.addScore(points); 
    } else if (obstacle instanceof NegativePointObstacle) {
        int points = ((NegativePointObstacle) obstacle).getPoints();
        
        
        getWorld().removeObject(obstacle); 
        world.showStatus();
    } else { 
        setLocation(100, 400); 
        world.loseLife(); 
    }
}

private void loseLife() {
            lives--;
            if (lives <= 0) {
                 MyWorld world = (MyWorld) getWorld();
                world.gameOver();

            } else {
                setLocation(100, 400); 
                health=MAX_HEALTH;
            }
}


}