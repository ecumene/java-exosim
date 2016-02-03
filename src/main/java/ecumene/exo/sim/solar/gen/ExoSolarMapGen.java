package ecumene.exo.sim.solar.gen;

import ecumene.exo.runtime.OpenSimplexNoise;
import ecumene.exo.sim.solar.ExoSolarMap;
import ecumene.exo.sim.solar.ExoSolarObject;
import ecumene.exo.sim.solar.IExoSolarObject;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ExoSolarMapGen {
    private OpenSimplexNoise noise;

    public ExoSolarMapGen(long seed){
        noise = new OpenSimplexNoise(seed);
    }

    public OpenSimplexNoise getNoise() {
        return noise;
    }

    public IExoSolarMapSource genCluster(int size, Vector2f minMaxMass, Vector2f minMaxPos, Vector2f minMaxVelocity){
        List<IExoSolarObject> objectList = new ArrayList<IExoSolarObject>();

        for(int i = 0; i < size; i++){
            objectList.add(new ExoSolarObject(new Vector2f((float) noise.eval(i, 0) * (minMaxPos.y - minMaxPos.x),              //Random pos x (minmax)
                                                           (float) noise.eval(i, 1) * (minMaxPos.y - minMaxPos.x)),             //Random pos y (minmax)
                                              new Vector2f((float) noise.eval(i, 2) * (minMaxVelocity.y - minMaxVelocity.x),    //Random vel x (minmax)
                                                           (float) noise.eval(i, 3) * (minMaxVelocity.y - minMaxVelocity.x)))); //Random vel y (minmax)
            ((ExoSolarObject) objectList.get(i)).mass = (float) noise.eval(i, 4) * (minMaxMass.y - minMaxMass.x) + minMaxMass.y;//Random mass  (minmax)
        }

        ExoSolarMap map = new ExoSolarMap(objectList);

        return new IExoSolarMapSource() {
            @Override
            public ExoSolarMap getSource() {
                return map;
            }
        };
    }

    public IExoSolarMapSource genCentralCluster(int centerMass, int size, Vector2f minMaxMass, Vector2f minMaxPos){
        List<IExoSolarObject> objectList = new ArrayList<IExoSolarObject>();
        ExoSolarObject sun = new ExoSolarObject(new Vector2f(), new Vector2f());
        sun.mass = centerMass;

        for(int i = 0; i < size; i++){
            Vector2f position = new Vector2f((float) noise.eval(i, 0) * (minMaxPos.y - minMaxPos.x),  //Random pos x (minmax)
                    (float) noise.eval(i, 1) * (minMaxPos.y - minMaxPos.x)); //Random pos y (minmax)
            Vector2f velocity = new Vector2f(sun.getPosition()).sub(position).perpendicular().normalize().mul((float)noise.eval(i, 2)
                    * (((float)noise.eval(i, 3) >= 0.5f) ? 1 : -1) * 0.1f);
            objectList.add(new ExoSolarObject(position, velocity));
            ((ExoSolarObject) objectList.get(i)).mass = (float) noise.eval(i, 4) * (minMaxMass.y - minMaxMass.x) + minMaxMass.y;//Random mass  (minmax)
        }

        ExoSolarMap map = new ExoSolarMap(objectList);
        map.getObjects().add(1, sun);

        return new IExoSolarMapSource() {
            @Override
            public ExoSolarMap getSource() {
                return map;
            }
        };
    }

    public IExoSolarMapSource genCentralOrbiters(int centerMass, int size, Vector2f minMaxMass, Vector2f minMaxPos){
        List<IExoSolarObject> objectList = new ArrayList<IExoSolarObject>();
        ExoSolarObject sun = new ExoSolarObject(new Vector2f(), new Vector2f());
        sun.mass = centerMass;

        for(int i = 0; i < size; i++){
            Vector2f position = new Vector2f((float) noise.eval(i, 0) * (minMaxPos.y - minMaxPos.x),  //Random pos x (minmax)
                    (float) noise.eval(i, 1) * (minMaxPos.y - minMaxPos.x)); //Random pos y (minmax)
            Vector2f velocity = new Vector2f(sun.getPosition()).sub(position).normalize().perpendicular().mul(Math.abs((float)noise.eval(i, 2) * .0256f));
            objectList.add(new ExoSolarObject(position, velocity));
            ((ExoSolarObject) objectList.get(i)).mass = (float) noise.eval(i, 4) * (minMaxMass.y - minMaxMass.x) + minMaxMass.y;//Random mass  (minmax)
        }

        ExoSolarMap map = new ExoSolarMap(objectList);
        map.getObjects().add(1, sun);

        return new IExoSolarMapSource() {
            @Override
            public ExoSolarMap getSource() {
                return map;
            }
        };
    }
}
