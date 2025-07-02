import bagel.*;

import java.util.Properties;

/**
 * Class that contains methods and attributes of a Flag entity.
 */
public class Flag extends Entity {
    private final float RADIUS;
    private boolean isCollided = false;

    /**
     * The constructor.
     */
    public Flag(int centreX, int centreY, Properties GAME_PROPS) {
        super(centreX, centreY, new Image(GAME_PROPS.getProperty("gameObjects.endFlag.image")),
              Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemy.speed")));
        this.RADIUS = Float.parseFloat(GAME_PROPS.getProperty("gameObjects.endFlag.radius"));
    }

    public boolean isCollided() {
        return isCollided;
    }

    /***
     * Method that updates the end flag movement, draws it, and checks for collisions with the player.
     */
    public void update(Input input, Player target) {
        move(input);
        super.getImage().draw(this.getCentreX(), this.getCentreY());

        if (CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(),
                                         this.RADIUS)) {
            isCollided = true;
        }
    }
}
