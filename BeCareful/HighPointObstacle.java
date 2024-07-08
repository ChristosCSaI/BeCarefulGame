import greenfoot.*;  


public class HighPointObstacle extends Obstacle {
    
    private int points;

public HighPointObstacle() {
        this.points = 30; 
        GreenfootImage image = new GreenfootImage("ScubaDiver.jpg");
        setImage(image);
}

public int getPoints() {
        return points;
}

}
