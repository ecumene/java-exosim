package ecumene.exo.sim.abstractions.surface.grid;

import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import org.joml.Vector2i;

public class ExoGridLayer extends ExoSFeatureLayer {
    protected float              scale;
    protected ExoGridFeature[][] grid;

    // grid = grid of featurs, scale = distance between points
    public ExoGridLayer(ExoGridFeature[][] grid, float scale){
        this.scale = scale;
        for(int x = 0; x < grid.length; x++)
            for(int y = 0; y < grid[0].length; y++)
                getFeatures().add(grid[x][y].setParent(this, new Vector2i(x, y)));
        this.grid = grid;
    }

    protected ExoGridLayer(float scale){
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public ExoGridFeature[][] getGrid() {
        return grid;
    }
}
