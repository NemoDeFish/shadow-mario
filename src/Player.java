import bagel.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that contains methods and attributes of a Player entity.
 */
public class Player extends Entity implements Damageable{
    private final static int ZERO_HEALTH = 0, ZERO_SPEED = 0, INITIAL_VERTICAL_SPEED = -20;
    private final float initialHealth;
    private final float radius;
    private final int initialHeight;
    private final Image imageLeft, imageRight;
    private int verticalSpeed;
    private float health;
    private final ArrayList<Fireball> fireballs = new ArrayList<>();

    /**
     * The constructor.
     */
    public Player(int centreX, int centreY, Properties GAME_PROPS) {
        super(centreX, centreY, new Image(GAME_PROPS.getProperty("gameObjects.player.imageRight")));
        this.imageRight = new Image(GAME_PROPS.getProperty("gameObjects.player.imageRight"));
        this.imageLeft = new Image(GAME_PROPS.getProperty("gameObjects.player.imageLeft"));
        this.health = Float.parseFloat(GAME_PROPS.getProperty("gameObjects.player.health"));
        this.radius = Float.parseFloat(GAME_PROPS.getProperty("gameObjects.player.radius"));
        this.initialHeight = centreY;
        this.initialHealth = this.health;
    }

    /**
     * Getters and setters.
     */
    public float getRadius() {
        return radius;
    }

    public float getHealth() {
        return health;
    }

    public float getInitialHealth() {
        return initialHealth;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setVerticalSpeed() {
        this.verticalSpeed = ZERO_SPEED;
    }

    /**
     * Method to update the player entity.
     */
    public void update(Input input, EnemyBoss enemyBoss, Properties GAME_PROPS) {
        if (input.isDown(Keys.RIGHT))
            super.setImage(imageRight);
        else if (input.isDown(Keys.LEFT))
            super.setImage(imageLeft);
        /* Shooting the fireball given that it is in the range. */
        if (input.wasPressed(Keys.S) && enemyBoss.isRange(this))
            fireAtPlayer(GAME_PROPS);
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        jumpUp(input);

        if (!fireballs.isEmpty()) {
            for (Fireball f: fireballs) {
                f.update(enemyBoss);
            }
        }

        if (isDead()) {
            moveOff();
        }
    }

    /**
     * Method to create and shoot the fireball in the given direction.
     */
    private void fireAtPlayer(Properties props) {
        fireballs.add(new Fireball(this.getCentreX(), this.getCentreY(), 1, props));
    }

    /**
     * Method to determine if the player is dead from its health.
     */
    public boolean isDead() {
        return this.health <= ZERO_HEALTH;
    }

    /**
     * Method to implement player's jumping upwards motion.
     */
    public void jumpUp(Input input) {
        // Condition to initialize a new jump
        // Ensures that the player can only jump once using a flag.
        if (input.wasPressed(Keys.UP) && verticalSpeed == 0) {
            verticalSpeed = INITIAL_VERTICAL_SPEED;
        }
        // Player's vertical jumping motion.
        this.setCentreY(this.getCentreY() + verticalSpeed);
        if (this.getCentreY() < initialHeight)
            verticalSpeed++;
        // Once the player reaches the platform again.
        if (this.getCentreY() >= initialHeight && !this.isDead()) {
            // Prevents the player from moving below the platform.
            verticalSpeed = ZERO_SPEED;
            // Naive implementation to reset the player back to the platform, not sure if best solution??
            this.setCentreY(initialHeight);
        }
    }

    /**
     * Method to move the player vertically off the screen.
     */
    public void moveOff() {
        verticalSpeed = FALLING_SPEED;
    }

    /***
     * Method that applies damage to the Player.
     */
    @Override
    public void takeDamage(float damageSize) {
        if (!InvinciblePower.isActive())
            this.setHealth((new BigDecimal(Float.toString(this.getHealth())).subtract(new BigDecimal(Float.toString(damageSize)))).floatValue());
    }
}