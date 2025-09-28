import javax.swing.*;

/**
 *
 *@author Sma1lo
 */

public class MonteCarloBasicLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}