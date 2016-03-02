/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Dec 8, 2015
 * Time: 8:35:10 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: PlayerTest
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Point;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author khainguyen
 */
public class PlayerTest {
    private Player player;
    private Map map;
    private GameManager manager;

    public PlayerTest() {
    }

    @Before
    public void setUp() {
        manager = new GameManager();
        map = new Map((short) 1);
        GameManager.setGameMap(map);
        player = new Player(0, 0, (short) 0);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of plantBomb method, of class Player.
     */
    @Test
    public void testPlantBomb() {
        player.plantBomb();
        System.out.println(map.getFromMap(new Point(0, 0)));
        Assert.assertEquals(map.getFromMap(new Point(0, 0)) instanceof Bomb,
                            true);
    }

    /**
     * Test of reachBorder method, of class Player.
     */
    @Test
    public void testReachBorder() {
        player.setDirection(12);
        Assert.assertEquals(player.reachBorder(), true);
        player.setDirection(9);
        Assert.assertEquals(player.reachBorder(), true);
        player.setDirection(3);
        Assert.assertEquals(player.reachBorder(), false);
        player.setDirection(6);
        Assert.assertEquals(player.reachBorder(), false);
    }

    /**
     * Test of moveable method, of class Player.
     */
    @Test
    public void testMoveable() {
        player.setDirection(12);
        Assert.assertEquals(player.moveable(), false);
        player.setDirection(9);
        Assert.assertEquals(player.moveable(), false);
        player.setDirection(3);
        Assert.assertEquals(player.moveable(), true);
        map.addToMap(new Bomb(1, player), new Point(1, 0));
        Assert.assertEquals(player.moveable(), false);
        player.setDirection(6);
        Assert.assertEquals(player.moveable(), true);
        map.addToMap(new Bomb(1, player), new Point(0, 1));
        Assert.assertEquals(player.moveable(), false);

    }
}
