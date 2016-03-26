package ecumene.exo.sim.abstractions.solar.gen;

import ecumene.exo.sim.util.OpenSimplexNoise;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.util.GenUtils;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a SINGLE solar system being built!
public class ExoSolarMapBuilder {
    private OpenSimplexNoise noise;
    private List<IExoSolarObject> objects;

    public ExoSolarMapBuilder(long seed){
        noise = new OpenSimplexNoise(seed);
        objects = new ArrayList<IExoSolarObject>();
    }

    public ExoSolarMapBuilder addObject(IExoSolarObject ... object){
        objects.addAll(Arrays.asList(object));
        return this;
    }

    public ExoSolarMapBuilder removeObject(IExoSolarObject ... object){
        objects.removeAll(Arrays.asList(object));
        return this;
    }

    public ExoSolarMapBuilder genObjectsOrbiting(int objects,
                                                 Vector2f orbiting,
                                                 Vector2f massRange,
                                                 Vector2f diameterRange,
                                                 Vector2f angleRange,
                                                 Vector2f velocityRange){
        for(int i = 0; i < objects; i++) {
            float diameter = GenUtils.getWithin((float) getRand(0), diameterRange);
            float angle = GenUtils.getWithin((float) getRand(0), angleRange);
            float velocity = GenUtils.getWithin((float) getRand(0), velocityRange);
            Vector2f objectPosition = new Vector2f(diameter * (float)Math.cos(Math.toRadians(angle)), // Object's position in rectangle relative to orbiter
                                                   diameter * (float)Math.sin(Math.toRadians(angle)));// Object's position in rectangle relative to map
            objectPosition.add(orbiting);
            Vector2f objectVelocity = new Vector2f(orbiting).sub(objectPosition).perpendicular().normalize().mul(velocity); // Velocity
            addObject(new ExoSolarObject("Planet", objectPosition, objectVelocity, GenUtils.getWithin((float) getRand(0), massRange)));
        }

        return this;
    }

    public ExoSolarMapBuilder genObject(Vector2f massRange,
                          Vector2f positionRange,
                          Vector2f velocityRange){
        objects.add(new ExoSolarObject("Object",
                                       new Vector2f(GenUtils.getWithin((float) getRand(0), positionRange),
                                                    GenUtils.getWithin((float) getRand(1), positionRange)),
                                       new Vector2f(GenUtils.getWithin((float) getRand(2), velocityRange),
                                                    GenUtils.getWithin((float) getRand(3), velocityRange)),
                                                    GenUtils.getWithin((float) getRand(4), massRange)));
        return this;
    }

    public ExoSolarMapBuilder genObjects(int numObjects,
                                         Vector2f massRange,
                                         Vector2f positionRange,
                                         Vector2f velocityRange){
        for(int i = 0; i < numObjects; i++)
            genObject(massRange, positionRange, velocityRange);
        return this;
    }

    public ExoSolarMap build(){
        return new ExoSolarMap(objects);
    }

    public void setSeed(long seed){
        this.noise = new OpenSimplexNoise(seed);
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
