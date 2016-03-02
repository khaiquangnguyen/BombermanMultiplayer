/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 9:13:26 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: CollisionGroup
 * Description:
 *
 * ****************************************
 */
package GameObject;

/**
 * An enum class, which specifies all collision groups in the game.
 *
 * @author khainguyen
 */
public enum CollisionGroup {

    PLAYER_COLLISION(
            new GameObjectType[]{GameObjectType.BOMB, GameObjectType.MONSTER, GameObjectType.WALL}),
    WALL_COLLISION(
            new GameObjectType[]{GameObjectType.BOMB, GameObjectType.MONSTER, GameObjectType.PLAYER}),
    BOMB_COLLISION(
            new GameObjectType[]{GameObjectType.PLAYER, GameObjectType.MONSTER, GameObjectType.WALL}),
    MONSTER_COLLISION(
            new GameObjectType[]{GameObjectType.BOMB, GameObjectType.PLAYER, GameObjectType.WALL});
    private final GameObjectType[] collisionGroup;

    //the list of all collision type of the current collion group
    private CollisionGroup(GameObjectType[] collisionGroup) {
        this.collisionGroup = collisionGroup;

    }

}
