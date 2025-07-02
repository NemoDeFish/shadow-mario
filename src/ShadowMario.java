import bagel.*;

import java.util.ArrayList;
import java.util.Properties;

public class ShadowMario extends AbstractGame {
    private final Properties GAME_PROPS;
    private static int score;
    private final Message titleMessage, instructionMessage, scoreMessage, healthMessage, winMessage, loseMessage, bossHealthMessage;
    private final Image backgroundImage;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Coin> coins = new ArrayList<>();
    private final ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private final ArrayList<InvinciblePower> invinciblePowers = new ArrayList<>();
    private final ArrayList<DoubleScore> doubleScores = new ArrayList<>();
    private EnemyBoss enemyBoss;
    private Platform platform;
    private Player player;
    private Game state;
    private Flag flag;
    private int frame = 0;

    /**
     * Enumeration for the game state.
     */
    public enum Game {
        NEW,
        START,
        WIN,
        LOSE
    }

    /**
     * Constructor.
     * @param game_props the game props
     * @param message_props the message props
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        backgroundImage = new Image(game_props.getProperty("backgroundImage"));

        // You can initialise other values from the property files here
        Message.setFontType(game_props.getProperty("font"));
        titleMessage = new Message(message_props.getProperty("title"),
                                    Integer.parseInt(game_props.getProperty("title.fontSize")),
                                    Integer.parseInt(game_props.getProperty("title.x")),
                                    Integer.parseInt(game_props.getProperty("title.y")));
        instructionMessage = new Message(message_props.getProperty("instruction"),
                                          Integer.parseInt(game_props.getProperty("instruction.fontSize")),
                                          Integer.parseInt(game_props.getProperty("instruction.y")));
        scoreMessage = new Message(message_props.getProperty("score"),
                                    Integer.parseInt(game_props.getProperty("score.fontSize")),
                                    Integer.parseInt(game_props.getProperty("score.x")),
                                    Integer.parseInt(game_props.getProperty("score.y")));
        healthMessage = new Message(message_props.getProperty("health"),
                                    Integer.parseInt(game_props.getProperty("playerHealth.fontSize")),
                                    Integer.parseInt(game_props.getProperty("playerHealth.x")),
                                    Integer.parseInt(game_props.getProperty("playerHealth.y")));
        winMessage = new Message(message_props.getProperty("gameWon"),
                                  Integer.parseInt(game_props.getProperty("message.fontSize")),
                                  Integer.parseInt(game_props.getProperty("message.y")));
        loseMessage = new Message(message_props.getProperty("gameOver"),
                                  Integer.parseInt(game_props.getProperty("message.fontSize")),
                                  Integer.parseInt(game_props.getProperty("message.y")));
        bossHealthMessage = new Message(message_props.getProperty("health"),
                                        Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize")),
                                        Integer.parseInt(game_props.getProperty("enemyBossHealth.x")),
                                        Integer.parseInt(game_props.getProperty("enemyBossHealth.y")));


        this.GAME_PROPS = game_props;
        state = Game.NEW;
        score = 0;


    }

    /**
     * The entry point for the program.
     * @param args for the command-line arguments if any
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * @param input the user's input
     */
    @Override
    protected void update(Input input) {
        frame++;
        ArrayList<String[]> csvResult;
        // Close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else {
            backgroundImage.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            if (state == Game.NEW) {
                // Render game welcome screen
                titleMessage.drawString();
                instructionMessage.drawString();
                // Handle user input
                if (input.wasPressed(Keys.NUM_1)) {
                    state = Game.START;
                    csvResult = IOUtils.readCsv(this.GAME_PROPS.getProperty("level1File"));
                    createEntities(csvResult);
                }
                else if (input.wasPressed(Keys.NUM_2)) {
                    state = Game.START;
                    csvResult = IOUtils.readCsv(this.GAME_PROPS.getProperty("level2File"));
                    createEntities(csvResult);
                }
                else if (input.wasPressed(Keys.NUM_3)) {
                    state = Game.START;
                    csvResult = IOUtils.readCsv(this.GAME_PROPS.getProperty("level3File"));
                    createEntities(csvResult);
                }
            } else if (state == Game.START) {
                // Check if player is dead, then should stop all possible movements
                if (!player.isDead() || player.getCentreY() < Window.getHeight() + player.getRadius()) {
                    moveEntities(input);
                }
                // Draw strings
                scoreMessage.drawString(score);
                healthMessage.drawString((int)(player.getHealth() * 100 / player.getInitialHealth()));
                if (enemyBoss != null)
                    bossHealthMessage.drawRedString((int)(enemyBoss.getHealth() * 100 / enemyBoss.getInitialHealth()));

                // Check if player is dead and fulfilled lose conditions
                if (player.isDead()) {
                    // Player will move until it reaches the end of the screen
                    if (player.getCentreY() < Window.getHeight() + player.getRadius()) {
                        player.moveOff();
                    } else {
                        state = Game.LOSE;
                    }
                }
            } else {
                if (state == Game.WIN) {
                    // Render winning message screen
                    winMessage.drawString();
                } else if (state == Game.LOSE) {
                    // Render losing message screen
                    loseMessage.drawString();
                }
                // Handle user input for restarting game
                if (input.wasPressed(Keys.SPACE)) {
                    resetGame();
                }
            }
        }
    }

    /**
     * Method that resets all the entities and the game.
     */
    private void resetGame() {
        state = Game.NEW;
        score = 0;
        flag = null;
        platform = null;
        player = null;
        enemyBoss = null;
        enemies.clear();
        coins.clear();
        enemies.clear();
        flyingPlatforms.clear();
        doubleScores.clear();
        invinciblePowers.clear();
    }

    /**
     * Method that updates the game objects each frame, when the game is running.
     * @param input the user's input
     */
    private void moveEntities(Input input) {
        flag.update(input, player);
        platform.update(input);
        player.update(input, enemyBoss, this.GAME_PROPS);
        for (Enemy e: enemies) {
            e.update(input, player);
        }
        for (Coin c: coins) {
            c.update(input, player, frame);
        }
        if (!flyingPlatforms.isEmpty()) {
            for (FlyingPlatform f: flyingPlatforms) {
                f.update(input, player);
            }
        }
        if (!doubleScores.isEmpty()) {
            for (DoubleScore d: doubleScores) {
                d.update(input, player, frame);
            }
        }
        if (!invinciblePowers.isEmpty()) {
            for (InvinciblePower i: invinciblePowers) {
                i.update(input, player, frame);
            }
        }
        if (enemyBoss != null)
            enemyBoss.update(input, player, this.GAME_PROPS);

        // Check if player collided with flag and fulfilled win conditions
        if (flag.isCollided())
            state = Game.WIN;
    }

    /**
     * Method that increases the score.
     * @param increase the amount of score to increase
     */
    public static void increaseScore(int increase) {
        score += increase;
    }

    /**
     * Method that creates Entity objects from the 2D array read from the CSV file.
     * @param csvResult the result of the CSV file after reading.
     */
    private void createEntities(ArrayList<String[]> csvResult) {
        for (String[] strings : csvResult) {
            String entity = strings[0];
            int x = Integer.parseInt(strings[1]);
            int y = Integer.parseInt(strings[2]);

            // Creates a corresponding Entity object depending on its type
            switch (entity) {
                case "PLATFORM":
                    platform = new Platform(x, y, this.GAME_PROPS);
                    break;
                case "PLAYER":
                    player = new Player(x, y, this.GAME_PROPS);
                    break;
                case "END_FLAG":
                    flag = new Flag(x, y, this.GAME_PROPS);
                    break;
                case "ENEMY":
                    enemies.add(new Enemy(x, y, this.GAME_PROPS));
                    break;
                case "COIN":
                    coins.add(new Coin(x, y, this.GAME_PROPS));
                    break;
                case "FLYING_PLATFORM":
                    flyingPlatforms.add(new FlyingPlatform(x, y, this.GAME_PROPS));
                    break;
                case "DOUBLE_SCORE":
                    doubleScores.add(new DoubleScore(x, y, this.GAME_PROPS));
                    break;
                case "INVINCIBLE_POWER":
                    invinciblePowers.add(new InvinciblePower(x, y, this.GAME_PROPS));
                    break;
                case "ENEMY_BOSS":
                    enemyBoss = new EnemyBoss(x, y, this.GAME_PROPS);
                    break;
            }
        }
    }
}
