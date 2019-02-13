package com.darkguardsman.visualization.instance;

import com.darkguardsman.visualization.data.DistanceFunction;
import com.darkguardsman.visualization.data.EnumDirections;
import com.darkguardsman.visualization.data.Grid;
import com.darkguardsman.visualization.data.GridPoint;
import com.darkguardsman.visualization.logic.Pathfinder;
import com.darkguardsman.visualization.logic.Pathfinders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Version of {@link com.darkguardsman.visualization.logic.PathfinderShell} that is used
 * to create a in memory state that iterates a few times and then pauses.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/13/2018.
 */
public class DataShell
{

    public GridPoint center;
    public int currentShell;
    public int maxShell;

    public ArrayList<Grid> images;

    protected DistanceFunction distanceFunction;

    private Queue<GridPoint> queue;
    private ArrayList<GridPoint> tempList = new ArrayList(4);
    private ArrayList<GridPoint> nextSet = new ArrayList();

    public DataShell(DistanceFunction distanceFunction)
    {
        this.distanceFunction = distanceFunction;
    }

    /**
     * Called to init the data shell instance
     *
     * @param startX
     * @param startY
     * @param maxShell
     */
    public DataShell init(int startX, int startY, int maxShell, ArrayList<Grid> images)
    {
        //Set data
        this.queue = new LinkedList();
        this.currentShell = 0;
        this.maxShell = maxShell;
        this.images = images;

        //Set center
        center = GridPoint.get(startX, startY);
        queue.add(center);

        return this;
    }

    public DataShell expandAll(Grid grid)
    {
        while (!isCompleted())
        {
            expand(grid);
        }
        return this;
    }

    /**
     * Called to expand a single time
     *
     * @param grid
     */
    public void expand(Grid grid)
    {
        //Move shell out 1
        currentShell++;

        //Save image of current
        if (images != null)
        {
            images.add(grid.copyLayer());
        }

        //Process current queue
        while (!queue.isEmpty())
        {
            //Get next
            final GridPoint node = queue.poll();

            //Mark as current node
            grid.setData(node.x, node.y, Pathfinders.CURRENT_NODE_ID);

            //Path to next tiles
            for (EnumDirections direction : EnumDirections.values())
            {
                final int x = node.x + direction.xDelta;
                final int y = node.y + direction.yDelta;

                //Ensure is inside view
                if (grid.isValid(x, y))
                {
                    final GridPoint nextPos = GridPoint.get(x, y);

                    //If have not pathed, add to path list
                    if (grid.getData(x, y) == Pathfinders.EMPTY_NODE_ID)
                    {
                        //Mark as next node
                        grid.setData(x, y, Pathfinders.ADDED_NODE_ID);

                        if (isInRange(x, y, center, currentShell))
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

            //Reset nodes to blue
            tempList.forEach(pos ->
            {
                if (isInRange(pos.x, pos.y, center, currentShell))
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
        nextSet.forEach(pos ->
        {
            grid.setData(pos.x, pos.y, Pathfinders.READY_NODE_ID);
            queue.offer(pos);
        });
        nextSet.clear();
    }

    public boolean isCompleted()
    {
        return queue.isEmpty() && nextSet.isEmpty();
    }

    protected boolean isInRange(int xx, int yy, GridPoint center, int range)
    {
        return distanceFunction.isInRange(xx, yy, center, range);
    }
}
