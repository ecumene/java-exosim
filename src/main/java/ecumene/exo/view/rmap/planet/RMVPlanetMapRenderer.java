package ecumene.exo.view.rmap.planet;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.beans.ExceptionListener;

public class RMVPlanetMapRenderer extends RMVRenderer {

    private ExoPlanetMap      planetMap;

    public RMVPlanetMapRenderer(int id, ExceptionListener exceptionListener, ExoPlanetMap planetMap, Vector3f navigation){
        super(id, exceptionListener, new RMap(), navigation);
        this.planetMap = planetMap;
    }

    @Override
    protected JRMViewer constructRenderer() {
        return new JRMVPlanetRenderer(navigation, planetMap);
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
