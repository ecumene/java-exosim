package ecumene.exo.sim.abstractions.surface.feature.height;

import ecumene.exo.sim.abstractions.surface.grid.ExoGridFeature;
import ecumene.exo.sim.abstractions.surface.grid.ExoGridLayer;
import org.joml.Vector2i;

public class ExoSHeight extends ExoGridFeature {
    private HeightMap parent;

    public ExoSHeight(){}

    public float sampleElevation(){
        return this.parent.elevation[getIndex().x][getIndex().y];
    }

    @Override
    public ExoGridFeature setParent(ExoGridLayer layer, Vector2i index) {
        this.parent = ((ExoSHeightLayer) layer).getHeightMap();

        return super.setParent(layer, index);
    }

    @Override
    public String getName() {
        return ""+sampleElevation();
    }
}
