package ecumene.exo.sim.planet;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.IExoSolarObject;
import org.joml.Vector2f;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExoPlanet implements IExoPlanetObject {

    private IExoSolarObject     solarComponent;
    private List<ExoPlanetMoon> moonList;

    public ExoPlanet(IExoSolarObject solarComponent, ExoPlanetMoon ... moons){ // Collections are so pretty!!!
        this.moonList       = Arrays.asList(moons); // Kinda sketch
        this.solarComponent = solarComponent;
    }

    public static RPoint getPoint(){
        return new RPoint("Planet", new Vector2f(0, 0));
    }

    public float getMass(){
        return solarComponent.getMass();
    }

    @Override
    public Vector2f getPosition() {
        return getPoint().getPosition();
    }

    public void addMoon(ExoPlanetMoon moon){
        moonList.add(moon);
    }

    public boolean removeMoon(ExoPlanetMoon moon){
        return moonList.remove(moon);
    }

    public IExoSolarObject getSolarComponent() {
        return solarComponent;
    }

    public List<ExoPlanetMoon> getMoonList() {
        return moonList;
    }
}
