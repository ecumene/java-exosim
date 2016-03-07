package ecumene.exo.sim.abstractions.surface.feature.elevation.build;

import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.sim.abstractions.surface.feature.elevation.ExoSElevationLayer;
import ecumene.exo.sim.abstractions.surface.gen.ExoSurfaceMapBuilder;
import ecumene.exo.sim.abstractions.surface.gen.IExoSurfaceMapComponent;

public class ExoSElevationComponent implements IExoSurfaceMapComponent {
    public float[][] elevation;
    public float     displacement;

    public ExoSElevationComponent(float[][] elevation, float displacement){
        this.elevation = elevation;
        this.displacement = displacement;
    }

    @Override
    public void construct(ExoSurfaceMapBuilder builder, ExoSurfaceMap map) {
        ExoSElevationLayer layer = new ExoSElevationLayer(elevation, displacement);
    }

    public float[][] getElevation() {
        return elevation;
    }

    public float getDisplacement() {
        return displacement;
    }
}
