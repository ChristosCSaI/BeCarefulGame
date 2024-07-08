import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level2 extends MyWorld {
    
   private int levelNumber;
    
   private static final int MAX_OBSTACLES = 5; 

public Level2(int levelNumber) {
      
        this.levelNumber = levelNumber;
        prepare();
        setBackground(new GreenfootImage("depth.jpg"));
}

private void prepare() {
        

       
        addObject(new Obstacle(), 200, 400);
        addObject(new Obstacle(), 500, 300);
        addObject(new Obstacle(), 600, 200);

        
        
       
}

public void act() {
        super.act();
        checkObstacleGeneration();
}

private void checkObstacleGeneration() {
        
        int numberOfObstacles = getObjects(Obstacle.class).size() + getObjects(HighPointObstacle.class).size();

    
        while (numberOfObstacles < MAX_OBSTACLES) {
            generateObstacle();
            numberOfObstacles++;
        }
}

private void generateObstacle() {
        
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());

        
        int obstacleType = Greenfoot.getRandomNumber(2); 

        
        if (obstacleType == 0) {
            addObject(new Obstacle(), x, y);
        } else {
            addObject(new HighPointObstacle(), x, y);
            addObject(new PointObstacle(), x, y);
        }
}

}
