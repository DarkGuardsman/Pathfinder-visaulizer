package com.darkguardsman.visualization.logic;

import com.darkguardsman.visualization.data.DistanceFunction;
import com.darkguardsman.visualization.data.EnumDirections;
import com.darkguardsman.visualization.data.Grid;
import com.darkguardsman.visualization.data.GridPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
    public static final int WAITING_NODE_ID = 6;
    public static final int WALL_NODE_ID = 7;

    public static final Pathfinder breadthFirst = new PathfinderBFS();
    public static final Pathfinder depthFirst = new PathfinderDFS();
    public static final Pathfinder breadthFirstSorted = new PathfinderSortedBFS();

    public static final DistanceFunction distanceFunctionBox = (xx, yy, center, range) ->
    {
        int x = xx - center.x;
        int y = yy - center.y;
        if (x > range || x < -range)
        {
            return false;
        }
        if (y > range || y < -range)
        {
            return false;
        }
        return true;
    };

    public static final DistanceFunction distanceFunctionCircle = (xx, yy, center, range) ->
    {
        int x = xx - center.x;
        int y = yy - center.y;
        double distance = x * x + y * y;
        return Math.floor(distance) < range * range;
    };

    public static final Pathfinder boxShell = new PathfinderShell(distanceFunctionBox);
    public static final Pathfinder circleShell = new PathfinderShell(distanceFunctionCircle);

    public static void doBreadthPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        breadthFirst.pathWithNoTarget(grid, images, startX, startY);
    }

    public static void doDepthPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        depthFirst.pathWithNoTarget(grid, images, startX, startY);
    }

    public static void doBoxShellPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        boxShell.pathWithNoTarget(grid, images, startX, startY);
    }

    public static void doCircleShellPathfinder(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        circleShell.pathWithNoTarget(grid, images, startX, startY);
    }

    public static void doBreadthPathfinderBox(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        grid.setData(startX, startY, CENTER_NODE_ID);

        Queue<GridPoint> queue = new LinkedList();
        for (EnumDirections directions : EnumDirections.values())
        {
            queue.offer(GridPoint.get(startX + directions.xDelta, startY + directions.yDelta));
        }
        queue.offer(GridPoint.get(startX - 1, startY + 1));
        queue.offer(GridPoint.get(startX + 1, startY + 1));
        queue.offer(GridPoint.get(startX + 1, startY - 1));
        queue.offer(GridPoint.get(startX - 1, startY - 1));

        breadthFirst.pathWithNoTarget(grid, queue, images, startX, startY);
    }

    public static void doBreadthPathfinderQuickStart(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        grid.setData(startX, startY, CENTER_NODE_ID);

        Queue<GridPoint> queue = new LinkedList();
        for (EnumDirections directions : EnumDirections.values())
        {
            queue.offer(GridPoint.get(startX + directions.xDelta, startY + directions.yDelta));
        }

        breadthFirst.pathWithNoTarget(grid, queue, images, startX, startY);
    }

    public static void doBreadthPathfinderQuickStartSorted(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        grid.setData(startX, startY, CENTER_NODE_ID);

        Queue<GridPoint> queue = new LinkedList();
        for (EnumDirections directions : EnumDirections.values())
        {
            queue.offer(GridPoint.get(startX + directions.xDelta, startY + directions.yDelta));
        }

        breadthFirstSorted.pathWithNoTarget(grid, queue, images, startX, startY);
    }

    public static void doBreadthPathfinderBoxSorted(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        grid.setData(startX, startY, CENTER_NODE_ID);

        Queue<GridPoint> queue = new LinkedList();
        for (EnumDirections directions : EnumDirections.values())
        {
            queue.offer(GridPoint.get(startX + directions.xDelta, startY + directions.yDelta));
        }
        queue.offer(GridPoint.get(startX - 1, startY + 1));
        queue.offer(GridPoint.get(startX + 1, startY + 1));
        queue.offer(GridPoint.get(startX + 1, startY - 1));
        queue.offer(GridPoint.get(startX - 1, startY - 1));

        breadthFirstSorted.pathWithNoTarget(grid, queue, images, startX, startY);
    }
}
