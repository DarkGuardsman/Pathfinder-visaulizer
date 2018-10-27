package com.builtbroken.visualization.logic;

import com.builtbroken.visualization.data.EnumDirections;
import com.builtbroken.visualization.data.Grid;
import com.builtbroken.visualization.data.GridPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
public class PathfinderShell extends Pathfinder
{
    @Override
    public void pathWithNoTarget(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        final Queue<GridPoint> queue = new LinkedList();

        final GridPoint center = GridPoint.get(startX, startY);
        queue.add(center);

        doPath(center, grid, images, queue);
    }

    @Override
    public void pathWithNoTarget(Grid grid, Collection<GridPoint> startNodes, ArrayList<Grid> images, int startX, int startY)
    {
        final Queue<GridPoint> queue = new LinkedList();

        final GridPoint center = GridPoint.get(startX, startY);

        startNodes.forEach(n -> {
            queue.offer(n);
            grid.setData(n.x, n.y, Pathfinders.READY_NODE_ID);
        });

        doPath(center, grid, images, queue);
    }

    protected void doPath(GridPoint center, Grid grid, ArrayList<Grid> images, Queue<GridPoint> queue)
    {
        final ArrayList<GridPoint> tempList = new ArrayList(4);
        final ArrayList<GridPoint> nextSet = new ArrayList();

        if (images != null)
        {
            images.add(grid.copyLayer());
        }

        for (int range = 1; range < grid.size; range++)
        {
            while (!queue.isEmpty())
            {
                //Get next
                final GridPoint node = queue.poll();

                //Mark as current node
                grid.setData(node.x, node.y, Pathfinders.CURRENT_NODE_ID);

                //Path to next tiles
                for (EnumDirections dir : EnumDirections.values())
                {
                    final int x = node.x + dir.xDelta;
                    final int y = node.y + dir.yDelta;

                    //Ensure is inside view
                    if (grid.isValid(x, y))
                    {
                        final GridPoint nextPos = GridPoint.get(x, y);

                        //If have not pathed, add to path list
                        if (grid.getData(x, y) == Pathfinders.EMPTY_NODE_ID)
                        {
                            //Mark as next node
                            grid.setData(x, y, Pathfinders.ADDED_NODE_ID);

                            if (isInRange(x, y, center, range))
                            {
                                //Add to queue
                                queue.offer(nextPos);
                            }
                            else
                            {
                                //Add to queue
                                nextSet.add(nextPos);
                            }

                            tempList.add(nextPos);
                        }
                        else
                        {
                            nextPos.dispose();
                        }
                    }
                }

                //Take picture
                if (images != null)
                {
                    images.add(grid.copyLayer());
                }

                final int r = range;
                //Reset nodes to blue
                tempList.forEach(pos ->
                {
                    if (isInRange(pos.x, pos.y, center, r))
                    {
                        grid.setData(pos.x, pos.y, Pathfinders.READY_NODE_ID);
                    }
                    else
                    {
                        grid.setData(pos.x, pos.y, Pathfinders.WAITING_NODE_ID);
                    }
                });
                tempList.clear();

                //Mark as completed
                if (node == center)
                {
                    grid.setData(node.x, node.y, Pathfinders.CENTER_NODE_ID);
                }
                else
                {
                    grid.setData(node.x, node.y, Pathfinders.COMPLETED_NODE_ID);
                }
            }

            //Move everything from next set into queue
            nextSet.forEach(pos -> {
                grid.setData(pos.x, pos.y, Pathfinders.READY_NODE_ID);
                queue.offer(pos);
            });
            nextSet.clear();
        }
    }

    protected boolean isInRange(int xx, int yy, GridPoint center, int range)
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
    }
}
