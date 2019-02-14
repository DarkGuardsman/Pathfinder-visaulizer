package com.darkguardsman.visualization.instance;

import com.darkguardsman.visualization.data.DistanceFunction;
import com.darkguardsman.visualization.data.EnumDirections;
import com.darkguardsman.visualization.data.Grid;
import com.darkguardsman.visualization.data.GridPoint;

import java.awt.Color;
import java.util.*;

/**
 * Version of {@link com.darkguardsman.visualization.logic.PathfinderShell} that is used
 * to create a in memory state that iterates a few times and then pauses.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/13/2018.
 */
public class DataShell
{

    private static final Random rand = new Random();

    public GridPoint center;
    public int currentShell;
    public int maxShell;

    public ArrayList<Grid> images;

    protected DistanceFunction distanceFunction;

    private Queue<GridPoint> queue;
    private ArrayList<GridPoint> tempList = new ArrayList(4);
    private ArrayList<GridPoint> nextSet = new ArrayList();
    private Set<GridPoint> currentSet = new HashSet();
    private Set<GridPoint> lastSet = new HashSet();

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

        //Set path caches
        lastSet.clear();
        lastSet.addAll(currentSet);
        currentSet.clear();
        currentSet.addAll(queue);

        System.out.println("Expanding[" + currentShell + "]-" + currentSet.size() + "-" + lastSet.size());

        //Process current queue
        while (!queue.isEmpty())
        {
            //Get next
            final GridPoint node = queue.poll();

            System.out.println("Pathing: " + node);

            //Path to next tiles
            for (EnumDirections direction : EnumDirections.values())
            {
                final int x = node.x + direction.xDelta;
                final int y = node.y + direction.yDelta;

                //Ensure is inside view
                if (grid.isValid(x, y) && grid.getData(x, y) == 0)
                {
                    final GridPoint nextPos = GridPoint.get(x, y);

                    //If have not pathed, add to path list
                    if (!currentSet.contains(nextPos) && !lastSet.contains(nextPos))
                    {
                        if (isInRange(x, y, center, currentShell))
                        {
                            //Add to queue
                            queue.offer(nextPos);
                            currentSet.add(nextPos);
                        }
                        else
                        {
                            //Add to queue
                            nextSet.add(nextPos);
                        }
                    }
                    else
                    {
                        nextPos.dispose();
                    }
                }
            }
        }

        //Move everything from next set into queue
        final Color color = getRandomColor();
        nextSet.forEach(pos ->
        {
            grid.setData(pos.x, pos.y, color.getRGB());
            queue.offer(pos);
        });
        nextSet.clear();
    }

    private Color getRandomColor()
    {
        Color color;
        do
        {
            color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        } while (color.getRGB() == 0);
        return color;
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
