package com.darkguardsman.visualization.data;

import java.util.ArrayList;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
@FunctionalInterface
public interface PathFunction
{
    void path(Grid grid, ArrayList<Grid> images, int startX, int startY);
}
