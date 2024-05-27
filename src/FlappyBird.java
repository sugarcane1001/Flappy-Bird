import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class FlappyBird extends JPanel {
    
    //Screen dimentions
    int screenHeight;
    int screenWidth;

    //Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

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
    }

    public void paintComponent(Graphics g){
        //invoke function from JPanel
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //background
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
    }
}
