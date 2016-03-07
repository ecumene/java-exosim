package ecumene.exo.sim.abstractions.surface.feature.elevation;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.sim.abstractions.surface.grid.ExoGridLayer;

public class ExoSElevationLayer extends ExoGridLayer {

    public ExoSElevationLayer(float[][] elevation, float disp){
        this(floatToElevationArr(elevation), disp);
    }

    public ExoSElevationLayer(ExoSElevation[][] elevation, float disp){
        super(elevation, disp);
    }

    public ExoSElevationLayer(int width, int height, float disp){
        super(new ExoSElevation[width][height], disp);
    }

    private static ExoSElevation[][] floatToElevationArr(float[][] elevation){
        ExoSElevation[][] elevations = new ExoSElevation[elevation.length][elevation[0].length];
        for(int x = 0; x < elevation.length; x++)
            for(int y = 0; y < elevation[0].length; y++)
                elevations[x][y] = new ExoSElevation(elevation[x][y]);
        return elevations;
    }

}
