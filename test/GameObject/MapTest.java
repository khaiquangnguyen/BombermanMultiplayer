/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Dec 5, 2015
 * Time: 11:07:03 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: MapTest
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Point;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author khainguyen
 */
public class MapTest {
    private GameManager manager;
    private Map map;

    public MapTest() {
    }

    @Before
    public void setUp() {
        manager = new GameManager();
        map = new Map((short) 1);
    }

    @After
    public void tearDown() {
        manager = null;
        map = null;
    }

    /**
     * Test of addToMap method, of class Map.
     */
    @Test
    public void testAddAndGet() {
        Wall wall = new Wall(false, 0, 0);
        map.addToMap(wall, new Point(0, 0));
        Assert.assertEquals(wall, map.getFromMap(new Point(0, 0)));
    }

    /**
     * Test of spawnNewPlayer method, of class Map.
     */
    @Test
    public void testSpawnNewPlayer() {
        Player player = new Player(0, 0, (short) 0);
        map.spawnNewPlayer((short) 0);
        Assert.assertEquals(true, GameManager.getGameObjectList().contains(
                            player));

        //call the spawn new player method
        //chech the GameObjectList from game manager to see if the GameObjectList contain the player.
        //Input the Id from 1 to 4 to see if the player is spawn at the right location
        //1 is top left
        //2 is bottom right
        //3 is top right
        //4 is bottom left
    }

    /**
     * Test of spawnMonster method, of class Map.
     */
    @Test
    public void testSpawnMonster() {
        Monster monster = new Monster(0, 0, 0);
        map.spawnMonster();
        Assert.assertEquals(true, GameManager.getGameObjectList().contains(
                            monster));
        //call the spawn monster method
        //test to see if the gameObjectList from gameManager has the monster

    }

    /**
     * Test of removeObject method, of class Map.
     */
    @Test
    public void testRemoveObject() {
        Wall wall = new Wall(false, 0, 0);
        map.addToMap(wall, new Point(0, 0));
        map.removeObject(wall);
        Assert.assertEquals(null, map.getFromMap(new Point(0, 0)));
    }

    @Test
    public void testEncodeMap() {
        Map mapNew = new Map("000001");
        Assert.assertEquals(Wall.class,
                            mapNew.getFromMap(new Point(0, 0)).getClass());

    }

}
