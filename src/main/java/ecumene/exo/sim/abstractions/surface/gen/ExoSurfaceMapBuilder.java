package ecumene.exo.sim.abstractions.surface.gen;

import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A builder class for all surface stuffs. Takes components as functions
 * to do to the final map.
 */
public class ExoSurfaceMapBuilder {
    private List<IExoSurfaceMapComponent> components; // List of components

    /**
     * A constructor class for generating and building
     * surface maps with given components. Each component
     * will determine a map's property. Such as weather,
     * temperature, elevation and so on.
     * @param component The components
     */
    public ExoSurfaceMapBuilder(IExoSurfaceMapComponent ... component){
        components = Arrays.asList(component);
    }

    /**
     * Adds a property to the map builder and later, to
     * the map.
     * @param component The component to add
     * @return          The builder (for one-line building)
     */
    public ExoSurfaceMapBuilder addComponent(IExoSurfaceMapComponent component){
        components.add(component);
        return this;
    }

    /** @return The built product including all components*/
    public ExoSurfaceMap build(){
        ExoSurfaceMap out = new ExoSurfaceMap();
        for(IExoSurfaceMapComponent component : components)
            component.construct(this, out);
        return out;
    }
}
