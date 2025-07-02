import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;
import java.util.Random;

/**
 * Class that contains methods and attributes of an Enemy entity.
 */
public class FlyingPlatform extends Entity implements Randomizable{
    private int INITIAL_X;
    private final int RANDOM_SPEED, MAX_RANDOM_DISPLACEMENT_X, HALF_LENGTH, HALF_HEIGHT;
    private int direction; // -1 for left, 1 for right

    /**
     * The constructor.
     */
    public FlyingPlatform(int centreX, int centreY, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.flyingPlatform.image")), Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.speed")));
        this.RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        this.MAX_RANDOM_DISPLACEMENT_X = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        this.HALF_LENGTH = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfLength"));
        this.HALF_HEIGHT = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        this.INITIAL_X = centreX;
        this.direction = new Random().nextBoolean() ? -1 : 1; // Randomly choose initial direction
    }

    /**
     * Method to update the flying platform entity.
     */
    public void update(Input input, Player target) {
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        move(input);
        /* Handles the player jumping on to the flying platform. */
        if (Math.abs(target.getCentreX() - this.getCentreX()) < HALF_LENGTH
                && this.getCentreY() - target.getCentreY() <= HALF_HEIGHT
                && this.getCentreY() - target.getCentreY() >= HALF_HEIGHT - 1) {
            target.setVerticalSpeed();
        }
    }

    /**
     * Method to control the movement of the flying platform entity.
     */
    @Override
    public void move(Input input) {
        super.move(input);
        if (input.isDown(Keys.RIGHT) && Platform.isWithinRightBound()){
            INITIAL_X -= 5;
        } else if (input.isDown(Keys.LEFT) && Platform.isWithinLeftBound()){
            INITIAL_X += 5;
        }
        moveRandom();
    }

    /**
     * Method to move the entity randomly.
     */
    @Override
    public void moveRandom() {
        // Move enemy according to chosen direction
        this.setCentreX(this.getCentreX() + RANDOM_SPEED * direction);
        // If maximum displacement reached, reverse direction
        if (Math.abs(this.getCentreX() - INITIAL_X) >= MAX_RANDOM_DISPLACEMENT_X) {
            direction *= -1;
        }
    }
}
