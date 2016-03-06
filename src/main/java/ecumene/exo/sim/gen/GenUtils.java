package ecumene.exo.sim.gen;

import org.joml.Vector2f;

/**Represents generator utilities for builder classes*/
public final class GenUtils {
    // PRIVATE CONSTRUCTORS = AWESOME
    private GenUtils(){}

    /**
     * For example: 1 would be halfway between 0 and 2, so the ratio would be .5 and min = 0 max = 2
     * @param ratio  The ratio between the numbers
     * @param minMax The x = min and y = max between the ratio
     * @return
     */
    public static float getWithin(float ratio, Vector2f minMax){
        return ratio * (minMax.y - minMax.x) + minMax.x;
    }
}
