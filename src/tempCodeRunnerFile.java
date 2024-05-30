import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        //Screen dimentions
        int screenWidth = 360;
        int screenHeight = 640;
        
        JFrame jframe = new JFrame("Flappy Bird");
        jframe.setSize(screenWidth, screenHeight);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instantiate and add the JPanel
        FlappyBird flappyBird =  new FlappyBird(screenWidth, screenHeight);
        jframe.add(flappyBird);
        jframe.pack();
        flappyBird.requestFocus();
        jframe.setVisible(true);

    }
}
