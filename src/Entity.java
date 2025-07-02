import bagel.Image;
import bagel.Input;
import bagel.Keys;

/**
 * Class that contains methods and attributes of an abstract parent Entity.
 * This forms a template of shared methods and attributes to concrete subclasses.
 */
public abstract class Entity {
    private int centreX;
    private int centreY;
    private int speed;
    private Image image;

    /**
     * Constructors.
     */
    public Entity(int centreX, int centreY, Image image) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.image = image;
    }

    public Entity(int centreX, int centreY, Image image, int speed) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.image = image;
        this.speed = speed;
    }

    /**
     * Getters and setters.
     */
    public int getCentreX() {
        return centreX;
    }

    public int getCentreY() {
        return centreY;
    }

    public int getSpeed() {
        return speed;
    }

    public Image getImage() {
        return image;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Method to move an entity.
     * @param input the user's input
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT) && Platform.isWithinRightBound()) {
            this.setCentreX(this.getCentreX() - speed);
        } else if (input.isDown(Keys.LEFT) && Platform.isWithinLeftBound()) {
            this.setCentreX(this.getCentreX() + speed);
        }
    }
}
