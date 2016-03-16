package ecumene.exo.sim.abstractions.planet.gen;

import ecumene.exo.sim.util.OpenSimplexNoise;
import ecumene.exo.sim.abstractions.planet.ExoPlanet;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMoon;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.util.GenUtils;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a SINGLE planet being built!
public class ExoPlanetMapBuilder {
    private OpenSimplexNoise    noise;
    private IExoSolarObject     planet;
    private List<ExoPlanetMoon> moons;

    public ExoPlanetMapBuilder(long seed){
        noise = new OpenSimplexNoise(seed);
        moons = new ArrayList<ExoPlanetMoon>();
    }

    public ExoPlanetMapBuilder setPlanet(IExoSolarObject object){
        this.planet = object;
        return this;
    }

    // AAA COLLECTIONS ARE SO PRETTY!!!
    public ExoPlanetMapBuilder addMoon(ExoPlanetMoon ... moonArr){
        moons.addAll(Arrays.asList(moonArr));
        return this;
    }

    public ExoPlanetMapBuilder removeMoon(ExoPlanetMap ... moonArr){
        moons.removeAll(Arrays.asList(moonArr));
        return this;
    }

    // Generates and sets planet
    public ExoPlanetMapBuilder genPlanet(Vector2f massRange){
        planet = new ExoSolarObject(GenUtils.getWithin((float) getRand(0), massRange));
        return this;
    }

    public ExoPlanetMapBuilder genMoon(Vector2f massRange,
                                       Vector2f diameterRange,
                                       Vector2f angleRange,
                                       Vector2f velRange){
        moons.add(new ExoPlanetMoon(Math.abs(GenUtils.getWithin((float) getRand(0), massRange)),
                                    Math.abs(GenUtils.getWithin((float) getRand(0), diameterRange)),
                                    Math.abs(GenUtils.getWithin((float) getRand(0), angleRange)),
                                new Vector2f(GenUtils.getWithin((float) getRand(0), velRange),
                                             GenUtils.getWithin((float) getRand(0), velRange))));
        return this;
    }

    public ExoPlanetMapBuilder genMoons(int numMoons,
                                        Vector2f massRange,
                                        Vector2f diameterRange,
                                        Vector2f angleRange,
                                        Vector2f velRange){
        for(int i = 0; i < numMoons; i++)
            genMoon(massRange,diameterRange,angleRange,velRange);
        return this;
    }

    // It's so CLEAN!
    public ExoPlanetMap build(){
        return new ExoPlanetMap(new ExoPlanet(planet, (ExoPlanetMoon[]) moons.toArray(new ExoPlanetMoon[moons.size()])));
    }

    private int randItr=-1; // I hate doing this stuff, YUCK!
    //2D -> 1D noise, gross... I know... ;(
    public double getRand(double index){
        randItr++;
        return noise.eval(index, randItr);
    }

    public OpenSimplexNoise getNoise() {
        return noise;
    }
}
