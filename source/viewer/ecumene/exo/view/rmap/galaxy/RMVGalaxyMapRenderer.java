package ecumene.exo.view.rmap.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;

public class RMVGalaxyMapRenderer extends RMVRenderer {

	public RMVGalaxyMapRenderer(int id, ExceptionListener exceptionListener, ExoGalaxyMap map, String[] args) {
		super(id, exceptionListener, new RMap(), args);
	}
	
	@Override
	protected JRMViewer constructRenderer() {
		return new JRMVGalaxyRenderer(pMap);
	}

	@Override
	public void onContextChanged(SimContext context) { }

	@Override
	public void onStep(SimContext context, int step, float interp) {
		this.pMap = context.getGalaxy().getMap().step();
		((JRMVGalaxyRenderer) renderer).setRMap(pMap);
		((JRMVGalaxyRenderer) renderer).onContextChanged(context);
		this.renderer.repaint();
	}
}
