import greenfoot.*;  

public class NegativePointObstacle extends Obstacle {
    
    private int points;

public NegativePointObstacle() {
        this.points = -20; 
        GreenfootImage image = new GreenfootImage("Jellyfish.jpg");
        setImage(image);
}

public int getPoints() {
        return points;
}

}
