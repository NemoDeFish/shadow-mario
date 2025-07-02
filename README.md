# Shadow Mario

## Game Overview

**Objective:**
Move the player to jump over enemies and collect coins. To win each level, reach the end flag. The game features three levels, each with unique challenges and features.

**Levels:**

1. **Level 1:**
   - **Controls:** Use arrow keys to move left, right, and jump.
   - **Objective:** Collect coins and avoid enemies.
   - **Scoring:** Each coin collected increases the score by one.
   - **Enemies:** Move within a fixed range. If the player collides with an enemy, they lose health points.
   - **Goal:** Reach the end flag to complete the level. If health points reach zero, the game ends.

2. **Level 2:**
   - **Features:** In addition to Level 1 gameplay, Level 2 includes:
     - **Flying Platforms:** Moveable platforms that the player can jump on to and move with.
     - **Power-Ups:**
       - **Invincibility:** Makes the player invincible to health loss from enemy collisions for a set period.
       - **Double Score:** Doubles the score from each coin collected for a set period.
   - **Goal:** Reach the end flag to complete the level.

3. **Level 3:**
   - **Features:** Combines elements from Level 1 and Level 2 with an additional challenge:
     - **Enemy Boss:** Appears at the end of the level. Both the player and the boss can shoot fireballs at each other when in close proximity, causing health points loss.
   - **Goal:** Defeat the enemy boss and reach the end flag to complete the level and win the game.

**Gameplay Notes:**
- The game does not need to be played progressively. Players can choose any level to play from the start screen or after completing a level.

## Controls

- **Arrow Keys:**
  - **Left Arrow:** Move left
  - **Right Arrow:** Move right
  - **Up Arrow:** Jump

## Power-Ups

- **Invincibility:** Grants temporary invincibility from enemy collisions.
- **Double Score:** Doubles the points from each collected coin for a limited time.

## Tips

- **Avoid Enemies:** Jump over enemies to avoid losing health points.
- **Collect Coins:** Increase your score by collecting coins.
- **Utilize Power-Ups:** Make the most of invincibility and double score power-ups to maximize your chances of success.

## Goal

Complete all levels by reaching the end flag and, in Level 3, defeating the enemy boss. Can you conquer all the challenges and emerge victorious?

Enjoy the game!

## Guide to Sharing Your Game with Others

**Play your game on any computer with Java installed.**

### Pre-requisite:
1. A finished Maven Project (Project 1, 2 etc).
2. Java installed on PC (to verify, open terminal and type `java -version`).

### Steps to Build:

1. Open your project in IntelliJ.
2. From the top menu: Select **File** -> **Project Structure** -> **Artifact** -> Add "Jar with dependencies".
3. Fill in the Main Class field with your own class, say "ShadowMario".
4. Edit Directory of META-INF: change `xxxx\src` -> `xxxx\src\main\resources` (Important!).
5. Apply and press OK.
6. From the top menu: Select **Build** -> **Build Artifact** -> **Build**.
7. A file should be generated in the directory: `out/artifacts/{YourProjectName}/{YourProjectName.jar}`.
8. Copy the "res" folder and `{YourProjectName.jar}` both into a new folder, say "MyGame".
9. Compress "MyGame" -> "MyGame.zip". (Sending a zip file around is a good way to share your game).

### Steps to Run:

1. Make sure you have Java installed.
2. Download the "MyGame.zip" you just created.
3. Extract it to your computer.
4. Open your terminal in the "MyGame" folder directory (just like how you `cd` to your project folder to use git) and type the following command to run:
     ```
     java -jar YourProjectName.jar
     ```
5. Enjoy!

Feel free to share your game with friends and family!
