package ecumene.exo.sim.planet;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.IExoSolarObject;
import org.joml.Vector2f;

import javax.print.attribute.standard.MediaSize;
import java.util.*;

public class ExoPlanet implements IExoPlanetObject {

    private ExoPlanetMap        parent; // For recording stats...

    private IExoSolarObject     solarComponent;
    private List<ExoPlanetMoon> moonList;

    private List<ExoPlanetMoon>              trackedMoons;
    private Map<ExoPlanetMoon, List<RPoint>> trackedPositions;
    private Map<ExoPlanetMoon, Integer>      trackedMoonsRevolutions;
    private Map<ExoPlanetMoon, Boolean>      lastTrackedMoonsRevolutions;

    public ExoPlanet(IExoSolarObject solarComponent, ExoPlanetMoon ... moons){ // Collections are so pretty!!!
        this.moonList       = Arrays.asList(moons); // Kinda sketch
        this.solarComponent = solarComponent;

        this.trackedMoons                = new ArrayList<ExoPlanetMoon>();
        this.trackedPositions            = new HashMap<ExoPlanetMoon, List<RPoint>>();
        this.trackedMoonsRevolutions     = new HashMap<ExoPlanetMoon, Integer>();
        this.lastTrackedMoonsRevolutions = new HashMap<ExoPlanetMoon, Boolean>();
    }

    public ExoPlanet setTracked(int moonID){
        trackedMoons.add(moonList.get(moonID));
        return this;
    }

    public void onAddedTo(ExoPlanetMap map){this.parent = map;}

    private static RPoint POSITION= new RPoint("Planet", new Vector2f(0, 0));

    public RPoint step(){
        for(int i = 0; i < trackedMoons.size(); i++){
            ExoPlanetMoon trackedMoon = trackedMoons.get(i);
            //if(!trackedPositions       .containsKey(trackedMoon)) trackedPositions       .put(trackedMoon, new ArrayList<RPoint>());
            if(!trackedMoonsRevolutions    .containsKey(trackedMoon)) trackedMoonsRevolutions    .put(trackedMoon, new Integer(0));
            if(!lastTrackedMoonsRevolutions.containsKey(trackedMoon)) lastTrackedMoonsRevolutions.put(trackedMoon, Boolean.TRUE);
            // Check & track for revolutions
            if(trackedMoon.hasTriggeredRevolution() && !lastTrackedMoonsRevolutions.get(trackedMoon)) // If triggered rev. and wasn't last step, it has gone round'
                trackedMoonsRevolutions.put(trackedMoon, trackedMoonsRevolutions.get(trackedMoon).intValue() + 1);
            lastTrackedMoonsRevolutions.put(trackedMoon, trackedMoon.hasTriggeredRevolution());
            // Track for positions
            trackedPositions.get(trackedMoon).add(trackedMoon.getLastPoint());
        }

        return POSITION;
    }

    public float getMass(){
        return solarComponent.getMass();
    }

    @Override
    public Vector2f getPosition() {
        return step().getPosition();
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
