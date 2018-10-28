package com.darkguardsman.visualization.data;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/27/2018.
 */
@FunctionalInterface
public interface DistanceFunction
{
    boolean  isInRange(int xx, int yy, GridPoint center, int range);
}
