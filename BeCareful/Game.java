import greenfoot.*;  


public class Game extends MyWorld {
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private int scoreNeededForNextLevel = 100;
    private GreenfootSound startSound = new GreenfootSound("StartGame.wav");
    private GreenfootSound lostSound = new GreenfootSound("LostGame.wav");

public Game() {
        super();
        prepare();
    }

private void prepare() {
        showText("Instructions:", getWidth() / 2, getHeight() / 2 - 50);
        showText("Use arrow keys to move.", getWidth() / 2, getHeight() / 2);
        showText("Eat your pray to get big but with concern ", getWidth() / 2, getHeight() / 2 + 50);
        showText("Be Careful!There is a whale you can't see right now!", getWidth() / 2, getHeight() / 2 + 100);
        showText("Press 'Space' to Start", getWidth() / 2, getHeight() / 2 + 150);
    }

public void act() {
        super.act();
        if (!gameStarted && Greenfoot.isKeyDown("space")) {
            startGame();
        }
    }

private void startGame() {
        removeObjects(getObjects(null));
        gameStarted = true;
        switchLevel(level);
        startSound.play();
    }

private void switchLevel(int levelNumber) {
    if (levelNumber == 1) {
        Level1 level1 = new Level1(levelNumber);
        Player player = new Player();  
        addObject(player, getWidth() / 2, getHeight() / 2);
        Greenfoot.setWorld(level1);
    } else if (levelNumber == 2) {
        Level2 level2 = new Level2(levelNumber);
        Player player = new Player();  
        addObject(player, getWidth() / 2, getHeight() / 2);
        Greenfoot.setWorld(level2);
    }
    
    }

public void addScore(int points) {
        score += points;
        showText("Score: " + score, 740, 20);
        checkScoreForLevelTransition();
    }
    
private void checkScoreForLevelTransition() {
        if (score >= scoreNeededForNextLevel) {
            transitionToNextLevel();
        }
    }
    
public void transitionToNextLevel() {
        level++;
        switchLevel(level);
        showLevel();
    }

public void loseLife() {
        lives--;
        showText("Lives: " + lives, 60, 20);
        if (lives == 0) {
            gameOver();
        }
    }

public void gameOver() {
        gameOver = true;
        showText("Game Over", getWidth() / 2, getHeight() / 2);
        showText("Final Score: " + score, getWidth() / 2, getHeight() / 2 + 30);
        lostSound.play();
    }
    
public int getScore() {
        return score;
    }
    
private void showLevel() {
        showText("Level: " + level, getWidth() / 2, 30); // Update level display
    }

}
