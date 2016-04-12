package ecumene.exo.sim.abstractions.planet;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.DynamicRPoint;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.Gravity;
import org.joml.Vector2f;

import java.util.*;

public class ExoPlanet extends DynamicRPoint implements IExoPlanetObject {

    private ExoPlanetMap parent; // For recording stats...

    private IExoSolarObject                        solarComponent;
    private List<ExoPlanetMoon>                    moonList;
    private Map<ExoPlanetMoon, TrackingParameters> trackedMoons;

    public ExoPlanet(IExoSolarObject solarComponent, String name, ExoPlanetMoon ... moons){ // Collections are so pretty!!!
        super(name, solarComponent.getPosition(), new FBody(FreeBodyShape.BALL, solarComponent.getMass()));
        this.moonList = new LinkedList<>();
        this.solarComponent = solarComponent;
        this.trackedMoons = new HashMap<>();
        nonStationary = false;

        for(ExoPlanetMoon moon : moons){
            moonList.add(moon);
            this.dynamics.forces.add(new Gravity(this, moon));
            moon.setParent(this);
        }
    }

    public ExoPlanet setTracking(int moonID, TrackingParameters trackingParams){
        trackedMoons.put(moonList.get(moonID), trackingParams);
        return this;
    }

    public void onAddedTo(ExoPlanetMap map){this.parent = map;}

    public RPoint step(SimContext context, int steps){
        Iterator it = trackedMoons.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ExoPlanetMoon      moon      = (ExoPlanetMoon)      pair.getKey();
            TrackingParameters moonTrack = (TrackingParameters) pair.getValue();

            boolean inRevThisFrame = moon.hasTriggeredRevolution();
            if(inRevThisFrame && !moonTrack.wasLastFrameInRevolution()) {              // If triggered rev. and wasn't last step, it has gone round'
                moonTrack.itrRevolutions();                                            // Add revolution
                if(moonTrack.doesCleanup()) moonTrack.getPreviousPositions().clear();  // If the moon cleans up every rev, clean the last pos
            }
            moonTrack.setLastFrameInRevolution(inRevThisFrame);                        // Set last revolution
            if(steps % moonTrack.getPositionRecStepInterval()==0)                      // Is this step divisible by the position integral? Save pos.
                if(moon != null) moonTrack.addPosition(new RPoint("refPoint", moon));  // "Damn that's some snazzy code" - Josh
        }

        return this;
    }

    @Override
    public FBody getDynamicBody() {
        return dynamics;
    }

    public float getMass(){
        return solarComponent.getMass();
    }

    public Map<ExoPlanetMoon, TrackingParameters> getTrackedMoons() {
        return trackedMoons;
    }

    public IExoSolarObject getSolarComponent() {
        return solarComponent;
    }

    public List<ExoPlanetMoon> getMoonList() {
        return moonList;
    }
}
