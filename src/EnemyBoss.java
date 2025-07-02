import bagel.Image;
import bagel.Input;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

/**
 * Class that contains methods and attributes of an Enemy entity.
 */
public class EnemyBoss extends Entity implements Damageable{
    private final float radius;
    private final int activationRadius;
    private float health;
    private final float initialHealth;
    private final int FIREBALL_COOLDOWN_MAX = 100;
    private int speedY = 0;
    private int frame;
    private Fireball fireball;

    /**
     * Constructor.
     * @param centreX the x-coordinate of the entity.
     * @param centreY the y-coordinate of the entity.
     * @param props the game properties to initialize the game.
     */
    public EnemyBoss(int centreX, int centreY, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.enemyBoss.image")), Integer.parseInt(props.getProperty("gameObjects.enemyBoss.speed")));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.enemyBoss.radius"));
        this.health = Float.parseFloat(props.getProperty("gameObjects.enemyBoss.health"));
        this.activationRadius = Integer.parseInt(props.getProperty("gameObjects.enemyBoss.activationRadius"));
        this.initialHealth = health;
        this.frame = FIREBALL_COOLDOWN_MAX;
    }

    /**
     * Getter.
     * @return the health of the enemy boss
     */
    public float getHealth() {
        return health;
    }

    /**
     * Getter.
     * @return the initial health of the enemy boss
     */
    public float getInitialHealth() {
        return initialHealth;
    }

    /**
     * Getter.
     * @return the radius of the enemy boss.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Setter.
     * @param health the health of the enemy boss.
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * Method to calculate the range of the player and the enemy boss.
     * @param target the target player
     * @return a boolean to determine whether the player and the enemy boss is in range.
     */
    public boolean isRange(Player target) {
        return CollisionDetector.isCollided(target, target.getRadius(), this.getCentreX(), this.getCentreY(), this.activationRadius);
    }

    /**
     * Method to update the enemy boss entity.
     * @param input the user's input
     * @param target the target player
     * @param props the game properties to initialize the fireball
     */
    public void update(Input input, Player target, Properties props) {
        super.getImage().draw(this.getCentreX(), this.getCentreY());
        move(input);

        if (target != null && isRange(target))
            frame--;

        if (fireball != null)
            fireball.update(target);

        // Check if it's time to fire a fireball
        if (frame <= 0) {
            // Generate a random boolean to determine if the enemy can fire
            frame = FIREBALL_COOLDOWN_MAX;
            // Fire a fireball at the player
            boolean canFire = new Random().nextBoolean();
            if (canFire)
                fireAtPlayer(props);

        }

        if (health <= 0) {
            // Enemy boss dies
            // Additional logic can be added here, such as removing the enemy from the game
            speedY = FALLING_SPEED;
        }
    }

    /**
     * Method to create and shoot the fireball.
     * @param props the game properties to initialize the fireball
     */
    private void fireAtPlayer(Properties props) {
        fireball = new Fireball(this.getCentreX(), this.getCentreY(), -1, props);
    }

    /**
     * Method to move the enemy boss entity.
     * @param input the user's input
     */
    public void move(Input input) {
        super.move(input);
        if (this.getCentreY() > 0 - radius)
            this.setCentreY(this.getCentreY() + speedY);
    }

    /**
     * Method that applies damage to the EnemyBoss.
     * @param damageSize the amount of damage to be deducted
     */
    @Override
    public void takeDamage(float damageSize) {
        this.setHealth((new BigDecimal(Float.toString(this.getHealth())).subtract(new BigDecimal(Float.toString(damageSize)))).floatValue());
    }
}
