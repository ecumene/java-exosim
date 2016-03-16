package ecumene.exo.sim.util.heightmap.random;

import ecumene.exo.sim.util.OpenSimplexNoise;
import ecumene.exo.sim.util.heightmap.channel.HeightChannel;
import org.joml.Vector2f;

public class NoiseChannel extends HeightChannel {
    private OpenSimplexNoise noise;
    private Vector2f         interp;

    public NoiseChannel(int width, int height, OpenSimplexNoise noise, Vector2f interp){
        super(width, height);
        this.noise  = noise;
        this.interp = interp;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                putPixel(x, y, (float) noise.eval(x / interp.x, y / interp.y));
            }
        }
    }

    public OpenSimplexNoise getNoise() {
        return noise;
    }

    public Vector2f getInterpolation() {
        return interp;
    }
}
