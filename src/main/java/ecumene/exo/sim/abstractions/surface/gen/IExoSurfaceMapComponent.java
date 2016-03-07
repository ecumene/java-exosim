package ecumene.exo.sim.abstractions.surface.gen;

import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;

/**
 * A single surface component. This represents a specific property
 * of the map, such as a weather system, elevation, tidal property...
 */
public interface IExoSurfaceMapComponent {
    /**
     * Constructs a surface component
     * @param builder The builder (parent)
     * @param map The map to add the component to
     */
    public void construct(ExoSurfaceMapBuilder builder, ExoSurfaceMap map);
}
