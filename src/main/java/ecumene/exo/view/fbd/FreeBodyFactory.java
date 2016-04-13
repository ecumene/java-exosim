package ecumene.exo.view.fbd;

import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.IExoPlanetObject;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.Force;
import ecumene.exo.sim.common.physics.instant.InsFBody;
import org.joml.Vector2f;

public class FreeBodyFactory {
    public static InsFBody createBody(ExoPlanetMap map, IExoPlanetObject planetObject){
        return new InsFBody(planetObject.getDynamicBody());
    }

    public static InsFBody createBody(ExoSolarMap map, IExoSolarObject solarObject){
        return new InsFBody(solarObject.getDynamicBody());
    }

    public static InsFBody createBody(ExoGalaxyMap map, IExoGalaxyObject galaxyObject) {
        return new InsFBody(galaxyObject.getDynamicBody());
    }
}