package ecumene.exo.sim.map.heightmap.random;

import ecumene.exo.utils.OpenSimplexLoop;
import ecumene.exo.utils.OpenSimplexNoise;
import ecumene.exo.sim.map.heightmap.channel.HeightChannel;
import org.joml.Vector2f;

public class TiledNoiseChannel extends HeightChannel {
    private OpenSimplexNoise noise;
    private Vector2f interp;

    public TiledNoiseChannel(int width, int height, OpenSimplexNoise noise, Vector2f interp){
        super(width, height);
        this.noise  = noise;
        this.interp = interp;
        OpenSimplexLoop loopGen = new OpenSimplexLoop(noise, width, height);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                putPixel(x, y, (float) loopGen.loopedNoise(x, y, interp.x, interp.y));
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
