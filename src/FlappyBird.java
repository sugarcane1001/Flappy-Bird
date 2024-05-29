import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener{
    
    //Screen dimentions
    int screenHeight;
    int screenWidth;

    //Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    //Bird
    /*int birdXPos = screenWidth/8;
    int birdYPos = screenHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;*/

    class Bird {
        int width = 34;//birdWidth;
        int height = 24;//birdHeight;
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

    //Game Logic
    Bird bird;
    int velocityY = -6;

    Timer gameLoop;


    //Constructor
    public FlappyBird(int screenWidth, int screenHeight){
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        setPreferredSize(new Dimension(screenWidth, screenHeight));

        //Load Images
        backgroundImage = new ImageIcon(getClass().getResource("./images/flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./images/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./images/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./images/bottompipe.png")).getImage();
    
        //Instantiating bird object
        bird = new Bird(birdImage);

        //game timer
        gameLoop = new Timer(1000/60, this); //for 60fps
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g){
        //invoke function from JPanel
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //System.out.println("draw");
        //Background
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        
        //Bird
        g.drawImage(birdImage, bird.xPos, bird.yPos, bird.width, bird.height, null);
    }

    public void move(){
        //bird
        bird.yPos += velocityY;
        bird.yPos = Math.max(bird.yPos, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
    }
}


