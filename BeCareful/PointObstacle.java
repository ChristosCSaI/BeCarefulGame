import greenfoot.*;  
public class PointObstacle extends Obstacle {
    
    private int points;

public PointObstacle() {
        this.points = 10; 
        GreenfootImage image = new GreenfootImage("Fish.jpg");
        setImage(image);
}

public int getPoints() {
        return points;
}

}
