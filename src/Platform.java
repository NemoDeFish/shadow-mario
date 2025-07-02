import bagel.*;

import java.util.Properties;

/**
 * Class that contains methods and attributes of a Platform entity.
 */
public class Platform extends Entity {
    private static boolean isWithinLeftBound = true, isWithinRightBound = true;
    private final int PLATFORM_WIDTH;

    /**
     * Constructor.
     */
    public Platform(int centreX, int centreY, Properties GAME_PROPS) {
        super(centreX, centreY, new Image(GAME_PROPS.getProperty("gameObjects.platform.image")), Integer.parseInt(GAME_PROPS.getProperty("gameObjects.platform.speed")));
        this.PLATFORM_WIDTH = centreX;
    }

    /**
     * Getters.
     */
    public static boolean isWithinLeftBound() {
        return isWithinLeftBound;
    }

    public static boolean isWithinRightBound() {
        return isWithinRightBound;
    }

    /**
     * Method to update the platform entity.
     */
    public void update(Input input) {
        super.move(input);
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        isWithinLeftBound = this.getCentreX() < this.PLATFORM_WIDTH;
        isWithinRightBound = this.getCentreX() > -this.PLATFORM_WIDTH + Window.getWidth();
    }
}
