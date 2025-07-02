import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

/**
 * Class that contains methods and attributes of an Enemy entity.
 */
public class Enemy extends Entity implements Randomizable {
    private final static float ZERO_DAMAGE = 0.0F;
    private final float RADIUS;
    private float damageSize;
    private int INITIAL_X;
    private final int RANDOM_SPEED, MAX_RANDOM_DISPLACEMENT_X;
    private int direction; // -1 for left, 1 for right

    /**
     * Constructor.
     * @param centreX the x-coordinate of the entity.
     * @param centreY the y-coordinate of the entity.
     * @param props the game properties to initialize the game.
     */
    public Enemy(int centreX, int centreY, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.enemy.image")), Integer.parseInt(props.getProperty("gameObjects.enemy.speed")));
        this.RADIUS = Float.parseFloat(props.getProperty("gameObjects.enemy.radius"));
        this.damageSize = Float.parseFloat(props.getProperty("gameObjects.enemy.damageSize"));
        this.RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.enemy.randomSpeed"));
        this.MAX_RANDOM_DISPLACEMENT_X = Integer.parseInt(props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        this.INITIAL_X = centreX;
        this.direction = new Random().nextBoolean() ? -1 : 1; // Randomly choose initial direction
    }

    /**
     * Method to update the enemy entity.
     * @param input the user's input
     * @param target the target player
     */
    public void update(Input input, Player target) {
        move(input);
        super.getImage().draw(this.getCentreX(), this.getCentreY());

        if (target != null && CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(), this.RADIUS) && damageSize != 0 && !InvinciblePower.isActive()) {
            damageTarget(target);
        }
    }

    /**
     * Method to move the enemy entity.
     * @param input the user's input
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
     * Method that damages the player. If the health of the player is less than or equal to 0,
     * the player will be marked as dead.
     * @param player the target player
     */
    private void damageTarget(Player player) {
        player.setHealth((new BigDecimal(Float.toString(player.getHealth())).subtract(new BigDecimal(Float.toString(damageSize)))).floatValue());
        damageSize = ZERO_DAMAGE;
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
