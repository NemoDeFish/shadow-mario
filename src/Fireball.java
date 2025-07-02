import bagel.Image;

import java.util.Properties;

/**
 * Class that contains methods and attributes of an Enemy entity.
 */
public class Fireball extends Entity {
    private final static float ZERO_DAMAGE = 0.0F;
    private final float radius;
    private float damageSize;
    private final int direction;

    /**
     * Constructor.
     * @param centreX the x-coordinate of the fireball
     * @param centreY the y-coordinate of the fireball
     * @param direction the direction of the fireball going +1 for right, -1 for left
     * @param props the game properties to initialize the entity
     */
    public Fireball(int centreX, int centreY, int direction, Properties props) {
        super(centreX, centreY, new Image(props.getProperty("gameObjects.fireball.image")),
              Integer.parseInt(props.getProperty("gameObjects.fireball.speed")));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.fireball.radius"));
        this.damageSize = Float.parseFloat(props.getProperty("gameObjects.fireball.damageSize"));
        this.direction = direction;
    }

    /**
     * Method to update the fireball entity.
     * @param target the target, either the player or the enemmy boss
     */
    public void update(Entity target) {
        /* Moves the fireball until it reaches the end of the screen in the given direction. */
        if (damageSize != 0 && this.getCentreX() > 0) {
            this.setCentreX(this.getCentreX() + this.getSpeed() * direction);
            super.getImage().draw(this.getCentreX(), this.getCentreY());
        }

        /* Subtracts the damage to the corresponding entity depending on which one is collided. */
        if (target instanceof Player && CollisionDetector.isCollided(target, ((Player) target).getRadius(),
                                                                     this.getCentreX(), this.getCentreY(),
                                                                     this.radius)) {
            ((Player) target).takeDamage(damageSize);
            damageSize = ZERO_DAMAGE;
        }

        if (target instanceof EnemyBoss && CollisionDetector.isCollided(target, ((EnemyBoss) target).getRadius(),
                                                                        this.getCentreX(), this.getCentreY(),
                                                                        this.radius)) {
            ((EnemyBoss) target).takeDamage(damageSize);
            damageSize = ZERO_DAMAGE;
        }
    }
}
