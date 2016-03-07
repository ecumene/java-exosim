package ecumene.exo.sim.abstractions.surface.feature.elevation;

import ecumene.exo.sim.abstractions.surface.grid.ExoGridFeature;
import ecumene.exo.sim.abstractions.surface.grid.ExoGridLayer;
import org.joml.Vector2i;

public class ExoSElevation extends ExoGridFeature {
    public float elev;

    public ExoSElevation(float elevation){
        this.elev = elevation;
    }

    @Override
    public ExoGridFeature setParent(ExoGridLayer layer, Vector2i index) {
        return super.setParent(layer, index);
    }

    public float getElevation() {
        return elev;
    }

    @Override
    public String getName() {
        return "e: " + elev;
    }
}
