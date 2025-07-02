import bagel.Image;
import bagel.Input;

import java.util.Properties;

/**
 * Class that contains methods and attributes of an invincible power entity.
 */
public class InvinciblePower extends Entity{
    private final float radius;
    private static int MAX_FRAMES;
    private static int startFrame;
    private final static int VERTICAL_SPEED = -10;
    private int speedY = 0;
    private static boolean isActive = false;

    /**
     * The constructor.
     */
    public InvinciblePower(int centreX, int centreY, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.invinciblePower.image")),
              Integer.parseInt(props.getProperty("gameObjects.invinciblePower.speed")));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.invinciblePower.radius"));
        MAX_FRAMES = Integer.parseInt(props.getProperty("gameObjects.invinciblePower.maxFrames"));
    }

    /**
     * Static method that will allow other classes to determine whether the power is active.
     */
    public static boolean isActive() {
        return isActive;
    }

    /**
     * Method to update the invincible power entity.
     */
    public void update(Input input, Player target, int frame) {
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        move(input);

        if (target != null && CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(), this.radius)) {
            startFrame = frame;
            speedY = VERTICAL_SPEED;
        }
        isActive = frame < startFrame + MAX_FRAMES;
    }

    /**
     * Method to control the movement of the invincible power entity.
     */
    public void move(Input input) {
        super.move(input);
        if (this.getCentreY() > 0 - radius)
            this.setCentreY(this.getCentreY() + speedY);
    }
}
