/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 10, 2015
 * Time: 9:27:57 PM
 *
 * Project: csci205_final_project
 * Package: GameUtility
 * File: GameUtilityTest
 * Description:
 *
 * ****************************************
 */
package GameUtility;

import java.awt.Point;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author khainguyen
 */
public class GameUtilityTest {

    public GameUtilityTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of dectectCollision method, of class GameUtility.
     */
    @Test
    public void testDectectCollision() {
        System.out.println("dectectCollision");
        Point Point1 = new Point(100, 100);
        Point Point2 = new Point(170, 170);
        boolean expResult = false;
        boolean result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
        Point2 = new Point(100, 100);
        expResult = true;
        result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
        Point2 = new Point(120, 120);
        expResult = true;
        result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
        Point2 = new Point(0, 0);
        expResult = false;
        result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
        Point2 = new Point(140, 140);
        expResult = true;
        result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
        Point2 = new Point(149, 149);
        expResult = false;
        result = GameUtility.dectectCollision(Point1, Point2);
        assertEquals(expResult, result);
    }

    /**
     * Test of toFrameCordinate method, of class GameUtility.
     */
    @Test
    public void testToFrameCordinate() {
        System.out.println("toFrameCordinate");
        Point gridCor = new Point(1, 1);
        Point expResult = new Point(50, 50);
        Point result = GameUtility.toFrameCordinate(gridCor);
        assertEquals(expResult, result);
        gridCor = new Point(0, 0);
        expResult = new Point(0, 0);
        result = GameUtility.toFrameCordinate(gridCor);
        assertEquals(expResult, result);
        gridCor = new Point(5, 5);
        expResult = new Point(250, 250);
        result = GameUtility.toFrameCordinate(gridCor);
        assertEquals(expResult, result);
    }

    /**
     * Test of toGridCordinate method, of class GameUtility.
     */
    @Test
    public void testToGridCordinate() {
        System.out.println("toGridCordinate");
        Point frameCor = new Point(200, 200);
        Point expResult = new Point(4, 4);
        Point result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);
        frameCor = new Point(0, 0);
        expResult = new Point(0, 0);
        result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);
        frameCor = new Point(10, 0);
        expResult = new Point(0, 0);
        result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);
        frameCor = new Point(100, 120);
        expResult = new Point(2, 2);
        result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);
        frameCor = new Point(120, 120);
        expResult = new Point(2, 2);
        result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);
        frameCor = new Point(90, 60);
        expResult = new Point(2, 1);
        result = GameUtility.toGridCordinate(frameCor);
        assertEquals(expResult, result);

    }

    /**
     * Test of snapToGrid method, of class GameUtility.
     */
    @Test
    public void testSnapToGrid() {
        System.out.println("snapToGrid");
        Point frameCor = new Point(170, 170);
        Point expResult = new Point(150, 150);
        Point result = GameUtility.snapToGrid(frameCor);
        assertEquals(expResult, result);

    }

}
