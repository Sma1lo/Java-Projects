import javax.swing.*;

/**
 *
 *@author Sma1lo
 */

public class RandomWalkLauncher {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int STEP_SIZE = 5;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandomWalkLogic logic = new RandomWalkLogic(WIDTH, HEIGHT, STEP_SIZE);
            Window window = new Window(logic);

            JFrame frame = new JFrame("Random Walk Launcher");
            frame.add(window);
            frame.setSize(WIDTH, HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

