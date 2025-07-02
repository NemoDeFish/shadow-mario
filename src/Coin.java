import bagel.*;

import java.util.Properties;

/**
 * Class that contains methods and attributes of a Coin entity.
 */
public class Coin extends Entity {
    private final static int VERTICAL_SPEED = -10;
    private final static float ZERO_VALUE = 0.0F;
    private final float RADIUS;
    private int value;

    /**
     * Constructor.
     * @param centreX the x coordinate of the center entity
     * @param centreY the y coordinate of the center entity
     * @param GAME_PROPS the game properties for initializing the entity
     */
    public Coin(int centreX, int centreY, Properties GAME_PROPS) {
        super(centreX, centreY, new Image(GAME_PROPS.getProperty("gameObjects.coin.image")),
              Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemy.speed")));
        this.RADIUS =  Float.parseFloat(GAME_PROPS.getProperty("gameObjects.coin.radius"));
        this.value = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.value"));
    }

    /**
     * Method that updates the coin movement, draws it, and checks for collisions with the player.
     * @param input the input from the user
     * @param target the target player to check the collision
     * @param frame the current frame of the game
     */
    public void update(Input input, Player target, int frame) {
        move(input);
        super.getImage().draw(this.getCentreX(), this.getCentreY());

        /* Check collision of coin with player target. */
        if (CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(),
                                         this.RADIUS)) {
            // Increase score
            if (DoubleScore.isActive(frame)) {
                ShadowMario.increaseScore(value * DoubleScore.getMULTIPLIER());
            } else {
                ShadowMario.increaseScore((value));
            }
            // Set coin's value to 0 so the coin cannot be collided more than once
            value = 0;
        }
    }

    /**
     * Method that moves the coin based on the player's movement.
     * @param input the user's input
     */
    @Override
    public void move(Input input) {
        super.move(input);
        // Move the coin upwards until just off the screen
        if (this.value == ZERO_VALUE && this.getCentreY() > -RADIUS)
            this.setCentreY(this.getCentreY() + VERTICAL_SPEED);
    }
}
