package com.darkguardsman.visualization.logic;

import com.darkguardsman.visualization.data.Grid;
import com.darkguardsman.visualization.data.GridPoint;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
public abstract class Pathfinder
{
    /**
     * Called to generate a path
     *
     * @param grid   - grid to use
     * @param images - list to populate copies of the grid each iteration, can be null
     * @param startX - center of the path or starting point
     * @param startY - center of the path or starting point
     */
    public abstract void pathWithNoTarget(@NotNull Grid grid, @Nullable ArrayList<Grid> images, int startX, int startY);

    /**
     * Called to generate a path
     *
     * @param grid       - grid to use
     * @param startNodes - nodes to start the pathfinder with
     * @param images     - list to populate copies of the grid each iteration, can be null
     * @param startX     - center of the path or starting point
     * @param startY     - center of the path or starting point
     */
    public abstract void pathWithNoTarget(@NotNull Grid grid, Collection<GridPoint> startNodes, @Nullable ArrayList<Grid> images, int startX, int startY);
}
