package ecumene.exo.sim.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.map.real.ExoRMapRenderer;
import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;

public class ExoGalaxyMapRenderer extends ExoRMapRenderer {

	public ExoGalaxyMapRenderer(int id, ExceptionListener exceptionListener, ExoGalaxyMap map, String[] args) {
		super(id, exceptionListener, map, args);
	}
	
	@Override
	protected JRMapRenderer constructRenderer() {
		return new JExoGalaxyRenderer(pMap);
	}

	@Override
	public void onContextChanged(ESContext context) {
		this.pMap = new ExoGalaxyMap(context.getSeed());
		((JExoGalaxyRenderer) renderer).setRMap(pMap);
		((JExoGalaxyRenderer) renderer).onContextChanged(context);
		this.renderer.repaint();
	}

	@Override
	public void onStep(ESContext context, int step) {
		// TODO: Make Galaxy Spin during step
	}
}
