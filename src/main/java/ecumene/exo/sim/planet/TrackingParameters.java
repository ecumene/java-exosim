package ecumene.exo.sim.planet;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import ecumene.exo.sim.map.real.RPoint;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TrackingParameters { // ExoPlanetMoonTrackingParameters -> Wow that's a mouth full!!

    private Color drawColor;
    private int revolutions, positionRecStepInterval; // revolutions: # of times, moon around the planet
                                                      // position rec. step interval: how many steps to wait before recording position
    private boolean lastFrameInRevolution;            // If the moon was in revolution last frame
    private List<RPoint> previousPositions;

    private boolean cleanup;

    public TrackingParameters(Color drawColor, int positionRecStepInterval, boolean cleanup){
        this.drawColor               = drawColor;
        this.positionRecStepInterval = positionRecStepInterval;
        previousPositions            = new ArrayList<RPoint>();
        this.cleanup = cleanup;
    }

    public boolean doesCleanup() {
        return cleanup;
    }

    public void setCleanup(boolean cleanup) {
        this.cleanup = cleanup;
    }

    public void addPosition(RPoint point){
        this.previousPositions.add(point);
    }

    public void setLastFrameInRevolution(boolean val){
        this.lastFrameInRevolution = val;
    }

    public boolean wasLastFrameInRevolution() {
        return lastFrameInRevolution;
    }

    public void itrRevolutions(){
        revolutions ++;
    }

    public int getRevolutions(){
        return revolutions;
    }

    public Color getDrawColor() {
        return drawColor;
    }

    public List<RPoint> getPreviousPositions() {
        return previousPositions;
    }

    public void setPositionRecStepInterval(int positionRecStepInterval) {
        this.positionRecStepInterval = positionRecStepInterval;
    }

    public int getPositionRecStepInterval() {
        return positionRecStepInterval;
    }
}
