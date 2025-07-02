/**
 * Class that handles the collision detection. Obtained from the sample solution for Project 1.
 */
public class CollisionDetector {
    /**
     * Method that checks for a collision between the player and the given entity's position.
     * @param target the target to check the collision with
     * @param targetRadius the radius of the target
     * @param x the x-coordinate of the colliding entity
     * @param y the y-coordinate of the colliding entity
     * @param radius the radius of the colliding entity
     * @return a boolean to determine whether the two entities have collided
     */
    public static boolean isCollided(Entity target, float targetRadius, int x, int y, double radius) {
        return Math.sqrt(Math.pow(target.getCentreX() - x, 2) +
                                 Math.pow(target.getCentreY() - y, 2)) <= targetRadius + radius;
    }
}