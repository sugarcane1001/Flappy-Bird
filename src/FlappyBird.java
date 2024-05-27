import java.awt.*;
import java.util.*;
import javax.swing.*;

public class FlappyBird extends JPanel {
    
    int screenHeight;
    int screenWidth;

    //Constructor
    public FlappyBird(int screenHeight, int screenWidth){
        setPreferredSize(new Dimension(screenHeight, screenWidth));
        setBackground(Color.green);
    }
}
