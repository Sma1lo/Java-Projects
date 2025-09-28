import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *@author Sma1lo
 */

public class Window extends JFrame {
    private JTextField particlesField;
    private MonteCarloPanel drawingPanel;
    private JLabel resultLabel;
    private MonteCarloLogic logic;

    public Window() {
        setTitle("MonteCarloBasicLauncher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        logic = new MonteCarloLogic();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());

        JLabel particlesLabel = new JLabel("Number of particles:");
        particlesField = new JTextField("1000", 10);
        JButton simulateButton = new JButton("Start Simulation");
        resultLabel = new JLabel("Pi will be calculated here");

        controlPanel.add(particlesLabel);
        controlPanel.add(particlesField);
        controlPanel.add(simulateButton);
        controlPanel.add(resultLabel);

        drawingPanel = new MonteCarloPanel(logic);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void startSimulation() {
        try {
            int numParticles = Integer.parseInt(particlesField.getText());
            if (numParticles <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number");
                return;
            }

            logic.runSimulation(numParticles);

            drawingPanel.repaint();

            int insideCircle = logic.getPointsInsideCircle();
            double estimatedPi = logic.getEstimatedPi();

            resultLabel.setText(String.format("Points inside: %d/%d, Estimated π: %.6f",
                    insideCircle, numParticles, estimatedPi));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
        }
    }
}