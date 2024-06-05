import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    
    //Screen dimentions
    int screenHeight;
    int screenWidth;

    //Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    class Bird {
        int width = 34; //birdWidth;
        int height = 24; //birdHeight;
        int xPos;
        int yPos;
        Image img;

        //Constructor
        public Bird(Image img){
            this.img = img;
            this.xPos = screenWidth/8; //birdXPos;
            this.yPos = screenHeight/2; //birdYPos;
        }
    }

    class Pipe {
        int width = 64; //scaled by 1/6
        int height = 512;
        int xPos;
        int yPos;
        Image img;
        boolean passed = false; //To keep track of score

        //Constructor
        public Pipe(Image img){
            this.img = img;
            this.xPos = screenWidth;
            this.yPos = 0;
        }
    }


    //Game Logic
    Bird bird;
    int velocityX = -4; //Move pipes to the left, simulating bird moving right
    int velocityY = 0; //Move bird up/down
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;

    //Constructor
    public FlappyBird(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        //So the JPanel (FlappyBird class) is focusable to receive keyboard events
        setFocusable(true);
        addKeyListener(this);

        //Load Images
        backgroundImage = new ImageIcon(getClass().getResource("./images/flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./images/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./images/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./images/bottompipe.png")).getImage();
    
        //Instantiating the bird and the pipe list
        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>();

        //game timer
        gameLoop = new Timer(1000/60, this); //for 60fps
        gameLoop.start();

        //place pipes timer
        //Every 1.5s placePipes() is called which adds a new set of Pipe to the pipelist
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();
    }


    //Add a new set of pipes to the pipe list
    public void placePipes(){
        Pipe topPipe = new Pipe(topPipeImage);
        //random: (0-1)*pipeHeight/2 -> (0 - 1/2)pipeHeight
        //0 - pipeHeight/4 - (0, 1/2)pipeHeight  ->  -(1/4, 3/4)pipeHeight
        topPipe.yPos = (int) (topPipe.yPos - topPipe.height/4 - Math.random()*(topPipe.height/2));
        pipes.add(topPipe);

        //Opening space for the bird to pass through
        int openingSpace = screenHeight/4;

        //Bottom pipe
        Pipe bottomPipe = new Pipe(bottomPipeImage);
        //Y-position is set to where the topPipe ends + the opening space
        bottomPipe.yPos = topPipe.yPos + topPipe.height + openingSpace;
        pipes.add(bottomPipe);
    }


    @Override
    public void paintComponent(Graphics g){
        //invoke function from JPanel
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g){
        //Background
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        
        //Bird
        g.drawImage(birdImage, bird.xPos, bird.yPos, bird.width, bird.height, null);
    
        //Pipes
        for(int i=0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.xPos, pipe.yPos, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(!gameOver){
            g.drawString("Score: " + String.valueOf((int) score), 10, 35);
        }
        else {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }
    }


    public void move(){
        //bird
        //Gravity will pull the bird down
        velocityY += gravity; 
        //Update the bird's vertical position by adding the current velocity
        bird.yPos += velocityY;
        //Make sure the bird does not go off the screen (the top of the screen was marked as 0)
        bird.yPos = Math.max(bird.yPos, 0);

        //pipes
        for(int i=0; i<pipes.size(); i++){
            //Add a velocity to each pipe, moving them to the left, simulating bird moving right
            Pipe pipe = pipes.get(i);
            pipe.xPos += velocityX;
            
            //If left side of the bird crosses right side of the pipe
            //Add 0.5 for each pipe (0.5 + 0.5 = 1 score for each set of pipes)
            if(!pipe.passed && bird.xPos > pipe.xPos + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }

            //If bird and pipe collides, game is over
            if(collision(bird, pipe)) {
                gameOver = true;
            }
        }

        //If bird falls under the screen, game is over
        if(bird.yPos > screenHeight) {
            gameOver = true;
        }
    }


    //Collision detection with pipes
    public boolean collision(Bird bird, Pipe pipe){
        return bird.xPos < pipe.xPos + pipe.width &&   //bird's top left corner doesn't reach pipe's top right corner
               bird.xPos + bird.width > pipe.xPos &&   //bird's top right corner passes pipe's top left corner
               bird.yPos < pipe.yPos + pipe.height &&  //bird's top left corner doesn't reach pipe's bottom left corner
               bird.yPos + bird.height > pipe.yPos;    //bird's bottom left corner passes pipe's top left corner
    }


    //ActionListener methods
    @Override
    public void actionPerformed(ActionEvent e){
        //Update the bird's position by calling the move method
        move();
        //Repaint the component, which will call the paintComponent method
        //This ensures the screen is updated with the bird's new position
        repaint();

        //If gameOver is true, we stop both timers
        if(gameOver){
            gameLoop.stop();
            placePipesTimer.stop();
        }
    }


    //KeyListener methods
    //For all keys, including non-character keys like space bar
    @Override
    public void keyPressed(KeyEvent e) {
        //If the player presses the space bar, the velocity is updated in the upward direction
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if(gameOver) {
                //restart the game by resetting the conditions
                bird.yPos = screenHeight/2;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    //For keys involving characters
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
