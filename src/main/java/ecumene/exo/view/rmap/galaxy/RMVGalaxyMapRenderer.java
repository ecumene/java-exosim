package ecumene.exo.view.rmap.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;
import org.joml.Vector3f;

public class RMVGalaxyMapRenderer extends RMVRenderer {

	public Vector3f navigation;

	public RMVGalaxyMapRenderer(int id, ExceptionListener exceptionListener, Vector3f navigation) {
		super(id, exceptionListener, new RMap(), navigation);
		this.navigation = navigation;
	}
	
	@Override
	protected JRMViewer constructRenderer() {
		return new JRMVGalaxyRenderer(navigation, pMap);
	}

	@Override
	public void onContextChanged(SimContext context) {
		((JRMVGalaxyRenderer) renderer).onContextChanged(context);
	}

	@Override
	public void onStep(SimContext context, int step) {
		this.pMap = context.getGalaxy().getMap().step(context, step);
		((JRMVGalaxyRenderer) renderer).setRMap(pMap);
		this.renderer.repaint();
	}
}
