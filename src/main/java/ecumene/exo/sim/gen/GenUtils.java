package ecumene.exo.sim.gen;

import org.joml.Vector2f;

public final class GenUtils {
    private GenUtils(){}

    public static float getWithin(float ratio, Vector2f minMax){
        return ratio * (minMax.y - minMax.x) + minMax.x;
    }
}
