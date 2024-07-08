import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Level1 extends MyWorld {
    
    private int levelNumber;
    private static final int MAX_OBSTACLES = 5; 
 
public Level1(int levelNumber) {
    
        this.levelNumber = levelNumber;
        prepare();
}


private void prepare() {
      
        addObject(new Obstacle(), 200, 400);
        addObject(new Obstacle(), 400, 100);
        addObject(new Obstacle(), 600, 400);
        addObject(new HighPointObstacle(), 700, 100);
        addObject(new NegativePointObstacle(), 400, 300);
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
        } else if (obstacleType==1){
            addObject(new HighPointObstacle(), x, y);
            addObject(new PointObstacle(), x, y);

        }else{
            addObject(new NegativePointObstacle(), x, y);
        }
}

    
}
