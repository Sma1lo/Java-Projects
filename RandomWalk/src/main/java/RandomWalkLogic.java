import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 *@author Sma1lo
 */

public class RandomWalkLogic {
    private int x, y;
    private final int width, height, stepSize;
    private final Random random = new Random();
    private final List<Point> trail = new ArrayList<>();

    public RandomWalkLogic(int width, int height, int stepSize) {
        this.width = width;
        this.height = height;
        this.stepSize = stepSize;
        this.x = width / 2;
        this.y = height / 2;
        trail.add(new Point(x, y));
    }

    public void move() {
        int direction = random.nextInt(4);
        switch (direction) {
            case 0 -> y -= stepSize;
            case 1 -> y += stepSize;
            case 2 -> x -= stepSize;
            case 3 -> x += stepSize;
        }

        if (x < 0) x = 0;
        if (x > width) x = width;
        if (y < 0) y = 0;
        if (y > height) y = height;

        trail.add(new Point(x, y));
        if (trail.size() > 2000) trail.remove(0);
    }

    public List<Point> getTrail() {
        return trail;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

