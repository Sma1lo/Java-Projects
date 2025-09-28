import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class MonteCarloLogic {
    private int pointsInsideCircle = 0;
    private int totalPoints = 0;
    private double estimatedPi = 0;
    private List<Point> points;

    public MonteCarloLogic() {
        points = new ArrayList<>();
    }

    public void runSimulation(int numParticles) {
        pointsInsideCircle = 0;
        totalPoints = numParticles;
        points.clear();

        for (int i = 0; i < numParticles; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;

            boolean inside = (x * x + y * y <= 1.0);
            if (inside) {
                pointsInsideCircle++;
            }

            if (i < 10000) {
                double drawX = (x + 1) / 2;
                double drawY = (y + 1) / 2;
                points.add(new Point(drawX, drawY, inside));
            }
        }

        estimatedPi = 4.0 * pointsInsideCircle / numParticles;
    }

    public int getPointsInsideCircle() {
        return pointsInsideCircle;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public double getEstimatedPi() {
        return estimatedPi;
    }

    public List<Point> getPoints() {
        return points;
    }

    public static class Point {
        public final double x;
        public final double y;
        public final boolean insideCircle;

        public Point(double x, double y, boolean insideCircle) {
            this.x = x;
            this.y = y;
            this.insideCircle = insideCircle;
        }
    }
}