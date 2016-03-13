package ecumene.exo.view.rmap.planet;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.view.rmap.RMVTag;
import org.joml.Vector3f;

import java.beans.ExceptionListener;

public class RMVPlanetViewerTag extends RMVTag {
    public RMVPlanetViewerTag(){}

    @Override
    public String getIdentifier(){
        return "Planet simulation";
    }

    @Override
    public Runnable constructRMV(int id, ExceptionListener listener, String[] args, Vector3f nav) {
        return new RMVPlanetMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getPlanet().getMap(), nav);
    }
}
