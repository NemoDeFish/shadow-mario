import bagel.Image;
import bagel.Input;
import java.util.Properties;

/**
 * Class that contains methods and attributes of a double score power entity.
 */
public class DoubleScore extends Entity{
    private final float radius;
    private static int MAX_FRAMES;
    private final static int MULTIPLIER = 2;
    private static int startFrame;
    private final static int VERTICAL_SPEED = -10;
    private int speedY = 0;

    /**
     * Constructor.
     * @param centreX the x-coordinate of the entity.
     * @param centreY the y-coordinate of the entity.
     * @param props the game properties to initialize the game.
     */
    public DoubleScore(int centreX, int centreY, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.doubleScore.image")), Integer.parseInt(props.getProperty("gameObjects.doubleScore.speed")));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.doubleScore.radius"));
        MAX_FRAMES = Integer.parseInt(props.getProperty("gameObjects.doubleScore.maxFrames"));
    }

    /**
     * Getter to get the multiplier of the power up.
     * @return the multiplier of the power up
     */
    public static int getMULTIPLIER() {
        return MULTIPLIER;
    }

    /**
     * Getter to determine whether the power up is active.
     * @param frame the current frame of the game
     * @return a boolean to determine whether the power up is active
     */
    public static boolean isActive(int frame) {
        return startFrame != 0 && frame < startFrame + MAX_FRAMES;
    }

    /**
     * Method to update the invincible power entity.
     * @param input the user's input
     * @param target the target player
     * @param frame the current frame of the game
     */
    public void update(Input input, Player target, int frame) {
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        move(input);

        if (target != null && CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(), this.radius)) {
            startFrame = frame;
            speedY = VERTICAL_SPEED;
        }
    }

    /**
     * Method to control the movement of the invincible power entity.
     * @param input the user's input
     */
    @Override
    public void move(Input input) {
        super.move(input);
        if (this.getCentreY() > 0 - radius)
            this.setCentreY(this.getCentreY() + speedY);
    }
}
