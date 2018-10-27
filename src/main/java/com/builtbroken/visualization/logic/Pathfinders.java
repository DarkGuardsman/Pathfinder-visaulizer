package com.builtbroken.visualization.logic;

import com.builtbroken.visualization.data.Grid;

import java.util.ArrayList;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
public class Pathfinders
{
    public static final int EMPTY_NODE_ID = 0;
    public static final int CENTER_NODE_ID = 1;
    public static final int READY_NODE_ID = 2;
    public static final int CURRENT_NODE_ID = 3;
    public static final int COMPLETED_NODE_ID = 4;
    public static final int ADDED_NODE_ID = 5;

    public static final Pathfinder breadthFirst = new PathfinderBFS();
    public static final Pathfinder depthFirst = new PathfinderDFS();

    public static void doBreadthPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        breadthFirst.pathWithNoTarget(grid, images, startX, startY);
    }

    public static void doDepthPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        depthFirst.pathWithNoTarget(grid, images, startX, startY);
    }
}
