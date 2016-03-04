package ecumene.exo.sim.abstractions;

import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;

public class SimSurfaceContext {

    private SimPlanetContext parentPlanet;
    private ExoSurfaceMap    surfaceMap;

    public SimSurfaceContext(SimPlanetContext planet, ExoSurfaceMap surfaceMap){
        this.parentPlanet = planet;
        this.surfaceMap = surfaceMap;
    }

    public ExoSurfaceMap getSurfaceMap() {
        return surfaceMap;
    }

    public SimPlanetContext getPlanet() {
        return parentPlanet;
    }
}
