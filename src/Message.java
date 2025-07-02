import bagel.*;
import bagel.util.Colour;

/**
 * Class that contains methods and attributes of a Message.
 */
public class Message {
    private static String fontType;
    private final String message;
    private final Font font;
    private final int x, y;

    /**
     * The overloaded constructors.
     * If the x-coordinate is not given, the text should be centered horizontally be default.
     */
    public Message(String message, int size, int y) {
        this.font = new Font(fontType, size);
        this.message = message;
        // Uses the getWidth() method in the Font class to determine the centre of the text.
        this.x = (int)(Window.getWidth()/2.0 - this.font.getWidth(message) / 2.0);
        this.y = y;
    }

    public Message(String message, int size, int x, int y) {
        this.font = new Font(fontType, size);
        this.message = message;
        this.x = x;
        this.y = y;
    }

    /**
     * Method to set the font type for the text.
     * Since the font type for each text is the same.
     * We can set it static and not have to repeat it.
     */
    public static void setFontType(String fontType) {
        Message.fontType = fontType;
    }

    /**
     * Method to draw the text on the screen.
     * Overloaded in cases where an integer such as the score or health is also printed.
     */
    public void drawString() {
        font.drawString(message, x, y);
    }

    public void drawString(int num) {
        font.drawString(message + num, x, y);
    }

    /**
     * Method to draw a red string for the enemy boss.
     */
    public void drawRedString(int num) {
        font.drawString(message + num, x, y, new DrawOptions().setBlendColour(Colour.RED));
    }
}
