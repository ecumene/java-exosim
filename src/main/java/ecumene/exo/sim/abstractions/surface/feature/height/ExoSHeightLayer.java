package ecumene.exo.sim.abstractions.surface.feature.height;

import ecumene.exo.sim.abstractions.surface.grid.ExoGridLayer;
import org.joml.Vector2i;

public class ExoSHeightLayer extends ExoGridLayer {
    private HeightMap heightMap;

    public ExoSHeightLayer(HeightMap heightmap, float renderScale){
        super(heightMapToFeatures(heightmap), renderScale);
        this.heightMap = heightmap;

        for(int x = 0; x < grid.length; x++)
            for(int y = 0; y < grid[0].length; y++)
                getFeatures().add(grid[x][y].setParent(this, new Vector2i(x, y)));
    }

    private static ExoSHeight[][] heightMapToFeatures(HeightMap heightMap){
        ExoSHeight[][] elevations = new ExoSHeight[heightMap.elevation.width][heightMap.elevation.height];
        for(int x = 0; x < heightMap.elevation.width; x++)
            for(int y = 0; y < heightMap.elevation.height; y++)
                elevations[x][y] = new ExoSHeight(); // Just look at the exogridlayer constructor and this'll all make sense.
        return elevations;
    }

    public HeightMap getHeightMap(){
        return heightMap;
    }
}

