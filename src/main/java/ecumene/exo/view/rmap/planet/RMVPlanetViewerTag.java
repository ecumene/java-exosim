package ecumene.exo.view.rmap.planet;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.view.IViewerTag;
import ecumene.exo.view.rmap.solar.RMVSolarMapRenderer;

import java.beans.ExceptionListener;

public class RMVPlanetViewerTag implements IViewerTag {
    public RMVPlanetViewerTag(){}

    @Override
    public String getIdentifier(){
        return "Planet simulation";
    }

    @Override
    public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
        return new RMVPlanetMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getPlanet().getMap(), args);
    }
}
