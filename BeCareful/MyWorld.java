import greenfoot.*;
import java.util.List;
import java.awt.Font;

public class MyWorld extends World {
    private boolean gameOver;
    private int score;
    private int lives;
    private int currentLevel;
    private static final int SCORE_NEEDED_FOR_NEXT_LEVEL = 100;
    private int level;
    private int enemySpawnTimer;
    private static final int MAX_LIVES = 3;
    private GreenfootSound collectSound = new GreenfootSound("Points.wav");
    private GreenfootSound levelUpSound = new GreenfootSound("LevelUp.wav");
    private GreenfootSound enemySpawnSound = new GreenfootSound("EnemySpawn.wav");
  private int respawnTimer = -1;  // Timer for respawning enemies, -1 means no respawn scheduled



public MyWorld() {
        super(800, 600, 1);
        prepare();
        gameOver = false;
        score = 0;
        showStatus(); 
        currentLevel=1;
         level=1;
         showLevel();
         displayInstructions();
         enemySpawnTimer=20;
         lives=3;
         
    }
    
public void removeAndRespawnEnemy() {
    List<Enemy> enemies = getObjects(Enemy.class);
    if (!enemies.isEmpty()) {
        removeObject(enemies.get(0)); // Remove the first found enemy
    }

    respawnTimer = 100; // Set this to however many acts you want to wait before respawning
}
    
private void checkEnemyCollision() {
    Player player = getObjects(Player.class).get(0);
    List<Enemy> enemies = getObjects(Enemy.class);

    for (Enemy enemy : enemies) {
        if (enemy.checkIntersection(player)) {
            player.handleDamage(enemy.getDamage()); 
            removeObject(enemy); 
            loseLife(); 
            break; 
        }
    }
}

    
private void addObstacles() {
    int numberOfRows = 5; 
    int spacing = 100; 

    for (int row = 0; row < numberOfRows; row++) {
        for (int i = 0; i < 4; i++) {
            generateObstacle(row * spacing + 50, i * 150 + 100);  
        }
    }
}

public int getScore(){
        return score;
    }
    
private boolean isClearLocation(int x, int y, int radius) {
        List<Actor> obstacles = getObjectsAt(x, y, null);
        for (Actor obstacle : obstacles) {
            if (obstacle instanceof Obstacle || obstacle instanceof Player) {
                return false; 
            }
        }
        return true; 
    }
    
private void generateObstacle(int x, int y) {
    int obstacleType = Greenfoot.getRandomNumber(4); 
    switch (obstacleType) {
        case 0:
            addObject(new Obstacle(), x, y);
            break;
        case 1:
            addObject(new HighPointObstacle(), x, y);
            
            break;
        case 2:
            addObject(new PointObstacle(), x, y);
            break;
        case 3:
            addObject(new NegativePointObstacle(), x, y);
            break;
        
    }
}

private void displayInstructions() {
        showText("Use arrow keys to move.", getWidth() / 2, getHeight() / 2);
        showText("Eat your pray to get big but not the Jellyfish one", getWidth() / 2, getHeight() / 2 + 50);
        showText("Be Careful!There is a whale you cant see right now!", getWidth() / 2, getHeight() / 2 + 100);
        showText("Reach 100 points at level 1 and get to level 2!", getWidth() / 2, getHeight() / 2 + 100);
        Greenfoot.delay(100); 
        removeInstructions();
}

    
private void removeInstructions() {
    // Remove the displayed instructions
    showText("", getWidth() / 2, getHeight() / 2 - 50);
    showText("", getWidth() / 2, getHeight() / 2);
    showText("", getWidth() / 2, getHeight() / 2 + 50);
    showText("", getWidth() / 2, getHeight() / 2 + 100);
}

private void prepare() {
        Player player= new Player();
        addObject(player, getWidth() / 2, getHeight() / 2);
        player.setWorld(this);
        addObstacles();
        showLevel();
        
}
private void checkGameConditions() {
    if (respawnTimer > 0) {
        respawnTimer--;
        if (respawnTimer == 0) {
            spawnEnemy();
            respawnTimer = -1;
        }
    }
    
    checkGameOver();
    checkLevelTransition();
    checkEnemyCollision();
    updateEnemySpawnTimer();
}

public void act() {
     super.act();
    if (!gameOver) {
        checkGameConditions();
    }

}

private void updateEnemySpawnTimer() {
        enemySpawnTimer++; 
        if (enemySpawnTimer >= 1000) { 
            spawnEnemy(); 
            enemySpawnTimer = 0; 
        }
}

private void spawnEnemy() {
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());
        addObject(new Enemy(10, 1), x, y); 
        enemySpawnSound.play();
}
    
private void checkGameOver() {
    
        if (getObjects(Player.class).isEmpty()) {
            gameOver = true;
            showGameOverMessage();
            Greenfoot.stop();
        }
}
    
public void replaceObstacle(Obstacle obstacleToRemove) {
    
    int x = Greenfoot.getRandomNumber(getWidth());
    int y = Greenfoot.getRandomNumber(getHeight());
    
   
    Obstacle newObstacle;
    if (obstacleToRemove instanceof PointObstacle) {
        newObstacle = new PointObstacle(); 
    } else if (obstacleToRemove instanceof HighPointObstacle) {
        newObstacle = new HighPointObstacle(); 
    }else if(obstacleToRemove instanceof NegativePointObstacle){
        newObstacle = new NegativePointObstacle();
    
    } else {
        
        newObstacle = new Obstacle();
    }
    
 
    addObject(newObstacle, x, y);
}

private void checkLevelTransition() {
     
     if (score >= SCORE_NEEDED_FOR_NEXT_LEVEL) {
        transitionToNextLevel();
    setBackgroundForLevel(level);}
 }
 
private void setBackgroundForLevel(int level) {
    if (level == 2) {
        setBackground(new GreenfootImage("depth.jpg"));  // Ensure this is executed
    }
}

public void transitionToNextLevel() {
    level++;
    if (level == 1 && score >= SCORE_NEEDED_FOR_NEXT_LEVEL) {
        levelUpSound.play();
        Greenfoot.setWorld(new Level2(level));
         
    }
    if (level == 2) {
        if (this instanceof MyWorld) {
            MyWorld myWorld = (MyWorld) this;
           Greenfoot.setWorld(new Level2(level));
            replaceObstacles();
            updateLevelDisplay();
            showLevelTransitionMessage();
        }
    }
}

private void showLevelTransitionMessage() {
    String message = "Congratulations! You have reached Level 2";
    int fontSize = 30;
    
    Color textColor = Color.WHITE;
    int x = getWidth() / 2;
    int y = getHeight() / 2;
    
    showText(message, x, y);
    Greenfoot.delay(200);
    showText("", x, y); 
}

public void loseLife() {
        lives--;
        showStatus(); 
        if (lives <= 0) {
            gameOver();
        }
    }

public int getLives(){
        return lives;
}

private void showGameOverMessage() {
        showText("Game Over", getWidth() / 2, getHeight() / 2);
        showText("Final Score: " + score, getWidth() / 2, getHeight() / 2 + 30);
}

public void showStatus() {
   
    showText("Lives: " + lives, 60, 20);
   showText("Score:" + Math.max(0,score),740,20);
}

public void replaceObstacles() {
    
    if (!getObjects(Obstacle.class).isEmpty()) {
       
       removeObjects(getObjects(Obstacle.class));
    }
    
   
    addObstacles();
}

private void switchLevel(int levelNumber) {
        if (levelNumber == 1) {
            addObject(new Player(), getWidth() / 2, getHeight() / 2);
            addObject(new Obstacle(), 200, 300); 
            addObject(new Obstacle(), 400, 100);
           
        } else if (levelNumber == 2) {
            Greenfoot.setWorld(new Level2(levelNumber));
           
        }
}

public void addScore(int points){
        score+=points;
        showStatus();
        collectSound.play();
}

public void updateLevelDisplay() {
    showText("Level: " + level, getWidth() / 2, 30);
}

public void updateScoreDisplay() {
    
    showText("Score: " + score, 740, 20); 
}

public void gameOver() {
        showGameOverMessage();
        Greenfoot.stop();
 }
 
private void showLevel() {
        showText("Level: " + currentLevel, getWidth() / 2, 30); 
    }
    
}