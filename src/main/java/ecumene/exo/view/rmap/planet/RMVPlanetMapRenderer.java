package ecumene.exo.view.rmap.planet;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;

import java.beans.ExceptionListener;

public class RMVPlanetMapRenderer extends RMVRenderer {

    private String[]          args;
    private ExceptionListener listener;
    private ExoPlanetMap      planetMap;

    public RMVPlanetMapRenderer(int id, ExceptionListener exceptionListener, ExoPlanetMap planetMap, String[] args){
        super(id, exceptionListener, new RMap(), args);
        this.planetMap = planetMap;
        this.args = args;
        this.listener = exceptionListener;
    }

    @Override
    protected JRMViewer constructRenderer() {
        return new JRMVPlanetRenderer(planetMap);
    }

    @Override
    public void onContextChanged(SimContext context){
        ((JRMVPlanetRenderer) renderer).setPlanetMap(context.getPlanet().getMap());
        ((JRMVPlanetRenderer) renderer).onContextChanged(context);
        frame.repaint();
    }

    @Override
    public void onStep(SimContext context, int step) {
        ((JRMVPlanetRenderer) renderer).onStep();
        frame.repaint();
    }
}
