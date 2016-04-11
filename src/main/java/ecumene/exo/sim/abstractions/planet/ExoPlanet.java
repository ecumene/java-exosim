package ecumene.exo.sim.abstractions.planet;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import org.joml.Vector2f;

import java.util.*;

public class ExoPlanet implements IExoPlanetObject {

    private ExoPlanetMap        parent; // For recording stats...

    private IExoSolarObject                        solarComponent;
    private List<ExoPlanetMoon>                    moonList;
    private Map<ExoPlanetMoon, TrackingParameters> trackedMoons;

    public ExoPlanet(IExoSolarObject solarComponent, ExoPlanetMoon ... moons){ // Collections are so pretty!!!
        this.moonList       = Arrays.asList(moons); // Kinda sketch
        this.solarComponent = solarComponent;

        this.trackedMoons = new HashMap<ExoPlanetMoon, TrackingParameters>();
    }

    public ExoPlanet setTracking(int moonID, TrackingParameters trackingParams){
        trackedMoons.put(moonList.get(moonID), trackingParams);
        return this;
    }

    public void onAddedTo(ExoPlanetMap map){this.parent = map;}

    private static RPoint POSITION = new RPoint("Planet", new Vector2f(0, 0));

    public RPoint step(){
        Iterator it = trackedMoons.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ExoPlanetMoon      moon      = (ExoPlanetMoon)      pair.getKey();
            TrackingParameters moonTrack = (TrackingParameters) pair.getValue();

            boolean inRevThisFrame = moon.hasTriggeredRevolution();
            if(inRevThisFrame && !moonTrack.wasLastFrameInRevolution()) {                                          // If triggered rev. and wasn't last step, it has gone round'
                moonTrack.itrRevolutions();                                                                        // Add revolution
                if(moonTrack.doesCleanup()) moonTrack.getPreviousPositions().clear();                              // If the moon cleans up every rev, clean the last pos
            }
            moonTrack.setLastFrameInRevolution(inRevThisFrame);                                                    // Set last revolution
            if(ExoRuntime.INSTANCE.getContext().getSteps() % moonTrack.getPositionRecStepInterval()==0)            // Is this step divisible by the position integral? Save pos.
                if(moon.getLastPoint() != null) moonTrack.addPosition(new RPoint("refPoint", moon.getLastPoint()));// "Damn that's some snazzy code" - Josh
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

    public Map<ExoPlanetMoon, TrackingParameters> getTrackedMoons() {
        return trackedMoons;
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
