package com.builtbroken.visualization.logic;

import com.builtbroken.visualization.data.EnumDirections;
import com.builtbroken.visualization.data.Grid;
import com.builtbroken.visualization.data.GridPoint;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
public class PathfinderDFS extends Pathfinder
{
    @Override
    public void pathWithNoTarget(Grid grid, ArrayList<Grid> images, int startX, int startY)
    {
        final Stack<GridPoint> queue = new Stack();

        final GridPoint center = GridPoint.get(startX, startY);
        queue.add(center);

        doPath(center, grid, images, queue);
    }

    protected void doPath(GridPoint center, Grid grid, ArrayList<Grid> images, Stack<GridPoint> queue)
    {
        final ArrayList<GridPoint> tempList = new ArrayList(4);

        while (!queue.isEmpty())
        {
            //Get next
            final GridPoint node = queue.pop();

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

                        //Add to queue
                        queue.push(nextPos);

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

            //Reset nodes to blue
            tempList.forEach(pos -> grid.setData(pos.x, pos.y, Pathfinders.READY_NODE_ID));
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
    }
}
