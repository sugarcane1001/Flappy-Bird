import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        //Screen dimentions
        int screenHeight = 360;
        int screenWidth = 640;

        JFrame jframe = new JFrame("Flappy Bird");
        jframe.setSize(screenHeight, screenWidth);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird =  new FlappyBird(screenHeight, screenWidth);
        jframe.add(flappyBird);
        jframe.pack();
        jframe.setVisible(true);

    }
}
