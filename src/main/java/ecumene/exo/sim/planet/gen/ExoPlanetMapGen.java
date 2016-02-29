package ecumene.exo.sim.planet.gen;

import ecumene.exo.runtime.OpenSimplexNoise;
import ecumene.exo.sim.planet.ExoPlanet;
import ecumene.exo.sim.planet.ExoPlanetMap;
import ecumene.exo.sim.planet.ExoPlanetMoon;
import ecumene.exo.sim.solar.IExoSolarObject;
import org.joml.Vector2f;

public class ExoPlanetMapGen {
    private OpenSimplexNoise noise;

    public ExoPlanetMapGen(long seed){
        noise = new OpenSimplexNoise(seed);
    }

    public IExoPlanetMapSource genExoPlanet(IExoSolarObject solarComponent, int moons, Vector2f massRange,     //Mass range (minx, maxy)
                                                                                       Vector2f diameterRange, //Diameter range (minx, maxy)
                                                                                       Vector2f angleRange,    //Angle range (minx, maxy)
                                                                                       Vector2f velRange ){    //Initial velocity range (minx, maxy)
        ExoPlanetMoon[] moonArr = new ExoPlanetMoon[moons];

        for(int i = 0; i < moons; i++){
            moonArr[i] = new ExoPlanetMoon(Math.abs((float)noise.eval(i, 1)) * (massRange.y     - massRange.x)    + massRange.x,
                                           Math.abs((float)noise.eval(i, 2)) * (diameterRange.y - diameterRange.x)+ diameterRange.x,
                                           (float)noise.eval(i, 3)           * (angleRange.y    - angleRange.x)   + angleRange.x,
                              new Vector2f((float)noise.eval(i, 4)           * (velRange.y      - velRange.x)     + velRange.x,
                                           (float)noise.eval(i, 5)           * (velRange.y      - velRange.x)     + velRange.x));
        }

        ExoPlanet planet = new ExoPlanet(solarComponent, moonArr);
        return new IExoPlanetMapSource() {
            @Override
            public ExoPlanetMap getSource() {
                return new ExoPlanetMap(planet);
            }
        };
    }

    public OpenSimplexNoise getNoise() {
        return noise;
    }
}
