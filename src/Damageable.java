/**
 * Interface for any class that can take damage and can also die.
 */
public interface Damageable {
    int FALLING_SPEED = 2;
    // Method to apply damage to the object
    void takeDamage(float damageSize);
}
