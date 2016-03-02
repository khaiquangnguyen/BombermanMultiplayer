/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 10:13:19 AM
 *
 * Project: csci205_final_project
 * Package: GameUtility
 * File: GameUtility
 * Description:
 *
 * ****************************************
 */
package GameUtility;

import java.awt.Point;

/**
 * The utility class. This class is used to store the basic, universal
 * informations and necessary utilities for the game.
 *
 * @author khainguyen
 */
public class GameUtility {

    /**
     * The size of a tile of the grid.
     */
    public static final int TILE_SIZE = 50;
    /**
     * The number of tile of the game.
     */
    public static final int NUMBER_OF_TILE = 17;
    /**
     * The width of the map.
     */
    public static final int MAP_WIDTH = TILE_SIZE * NUMBER_OF_TILE;
    /**
     * The height of the map.
     */
    public static final int MAP_HEIGHT = TILE_SIZE * NUMBER_OF_TILE;

    /**
     * Detect collision between two objects with center at the two points. The
     * collision is handled with the assumption that each object has the size of
     * a grid.
     *
     * @param Point1 - the center of the first object
     * @param Point2 - the center of the second object
     * @return true if two objects can collide, false otherwise
     * @author khainguyen
     */
    public static boolean dectectCollision(Point Point1,
                                           Point Point2) {
        //if both objects can collide
        int difX = (int) Math.abs(Point1.getX() - Point2.getX());
        int difY = (int) Math.abs(Point1.getY() - Point2.getY());
        return ((difX <= TILE_SIZE - 4) && (difY <= TILE_SIZE - 4));
    }

    /**
     * From the grid-based coordinate used in map, calculate the according
     * coordinate in the frame-based coordinate.
     *
     * @param gridCor - the coordinate of the point in the grid
     * @return - a point, the location of the point in the frame
     * @author khainguyen
     */
    public static Point toFrameCordinate(Point gridCor) {
        return new Point(gridCor.x * TILE_SIZE, gridCor.y * TILE_SIZE);

    }

    /**
     * From the frame coordinate, calculate the according coordinate in the
     * grid-based coordinate used in map.
     *
     * @param frameCor - the coordinate of the point in the frame
     * @return - a point, the location of the point in the grid
     * @author khainguyen
     */
    public static Point toGridCordinate(Point frameCor) {
        int corX = Math.round(frameCor.x / (float) TILE_SIZE);
        int corY = Math.round(frameCor.y / (float) TILE_SIZE);
        return new Point(corX, corY);
    }

    /**
     * Convert the frame-based coordinate into the closest possible coordinate
     * which match that of a grid-based one.
     *
     * @param framCor - the original coordinate of the point in the frame
     * @return - the coordinate of the point after snap to the grid
     * @author khainguyen
     */
    public static Point snapToGrid(Point framCor) {
        return new Point(toFrameCordinate(toGridCordinate(framCor)));
    }

    /**
     * Calculate the distance between two points.
     *
     * @param point1 - the first point
     * @param point2 - the second point
     * @return - the distance between two points
     * @author khainguyen
     */
    public static double distance(Point point1, Point point2) {
        int distanceX = point1.x - point2.x;
        int distanceY = point1.y - point2.y;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY,
                                                                      2));
        return distance;
    }
}
