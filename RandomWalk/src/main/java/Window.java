import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class Window extends JPanel {
    private static final int DELAY = 20;
    private final RandomWalkLogic logic;

    public Window(RandomWalkLogic logic) {
        this.logic = logic;

        Timer timer = new Timer(DELAY, e -> {
            logic.move();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Point> trail = logic.getTrail();

        for (int i = 1; i < trail.size(); i++) {
            Point p1 = trail.get(i - 1);
            Point p2 = trail.get(i);
            float alpha = (float) i / trail.size();
            g2.setColor(new Color(0, 0, 255, (int) (alpha * 255 * 0.4)));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g2.setColor(Color.RED);
        double r = 8;
        g2.fill(new Ellipse2D.Double(logic.getX() - r / 2, logic.getY() - r / 2, r, r));
    }
}

