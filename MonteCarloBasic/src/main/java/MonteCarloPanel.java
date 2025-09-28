import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class MonteCarloPanel extends JPanel {
    private MonteCarloLogic logic;

    public MonteCarloPanel(MonteCarloLogic logic) {
        this.logic = logic;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int size = Math.min(getWidth(), getHeight()) - 40;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, size, size);

        g2d.setColor(Color.BLUE);
        g2d.drawOval(x, y, size, size);

        drawPoints(g2d, x, y, size);

        g2d.setColor(Color.RED);
        g2d.drawString(String.format("Estimated π: %.6f", logic.getEstimatedPi()), 10, 20);
        g2d.drawString(String.format("Points: %d/%d", logic.getPointsInsideCircle(), logic.getTotalPoints()), 10, 40);
    }

    private void drawPoints(Graphics2D g2d, int x, int y, int size) {
        List<MonteCarloLogic.Point> points = logic.getPoints();

        for (MonteCarloLogic.Point point : points) {
            int pointX = x + (int) (point.x * size);
            int pointY = y + (int) (point.y * size);

            if (point.insideCircle) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GREEN);
            }

            g2d.fillRect(pointX, pointY, 2, 2);
        }
    }
}