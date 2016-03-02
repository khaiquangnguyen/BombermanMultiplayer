/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Alice Shen
 * Date: Nov 7, 2015
 * Time: 8:41:43 PM *
 * Project: csci205
 * Package: Bomberman
 * File: PlayerPowerup
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Graphics2D;

/**
 * The power up object, which activates and power up the player when picked up
 * by the player.
 *
 * @author alice
 */
public class PowerUp extends GameObject {

    /**
     * The type of power up that this object is associated with.
     */
    private PowerUpType powerUpType;

    /**
     * Time until the power up will disappear from the game.
     */
    private int timeTillVanish = 300;

    /**
     * Initiate the power up object.
     *
     * @param powerUpType - the type of power up that this object is associated
     * with.
     */
    public PowerUp(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
        GameManager.getGameObjectList().add(this);
        this.setType(GameObjectType.POWER_UP);
    }

    /**
     * Power up the player when the player picks up this power up.
     *
     * @param player - the player that this power up will power up.
     */
    public void powerUpPlayer(Player player) {
        if (powerUpType.equals(PowerUpType.SPEED)) {
            //this should be carried on for the player so that after it had the powerup, it have this power up forever
            //Or if necessary, we can set a time limit for this one
            player.setSpeed(player.getSpeed() + 1);
        } else if (powerUpType.equals(PowerUpType.BOMB_RANGE)) {
            //this should be carried on for the player so that after it had the powerup, it have this power up forever
            //Or if necessary, we can set a time limit or a limit of times it can be used
            player.setBombSize(player.getBombSize() + 1);
        } else if (powerUpType.equals(PowerUpType.SHIELD)) {
            //the sheild should be down graded after a certain amount of time or after the player have been hit once
            //this should be performed in Player class or bomberman class so that the time or availability can be checked
            player.setInvulnerable(true);
        } else if (powerUpType.equals(PowerUpType.BOMB_LIMIT)) {
            player.setBombLimit(player.getBombLimit() + 1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (powerUpType.equals(PowerUpType.SPEED)) {
            g.drawImage(GameManager.getResources().getIcons().get("speed"), null,
                        getX(), getY());
        } else if (powerUpType.equals(PowerUpType.BOMB_RANGE)) {
            g.drawImage(GameManager.getResources().getIcons().get("bombRange"),
                        null, getX(), getY());
        } else if (powerUpType.equals(PowerUpType.SHIELD)) {
            g.drawImage(GameManager.getResources().getIcons().get("shield"),
                        null,
                        getX(), getY());
        } else if (powerUpType.equals(PowerUpType.BOMB_LIMIT)) {
            g.drawImage(GameManager.getResources().getIcons().get("bombLimit"),
                        null,
                        getX(), getY());
        }

    }

    @Override
    public void vanish() {
        GameManager.remove(this);
    }

    @Override
    public void update() {
        //check if collide with any player
        for (GameObject gameObject : GameManager.getGameObjectList()) {
            if (gameObject.getType() == GameObjectType.PLAYER) {
                if (GameUtility.GameUtility.dectectCollision(
                        gameObject.getCenterLocation(), this.getCenterLocation())) {
                    powerUpPlayer((Player) gameObject);
                    vanish();
                }
            }
        }
        // if run out of time
        timeTillVanish -= 1;
        if (timeTillVanish == 0) {
            vanish();
        }
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

}
