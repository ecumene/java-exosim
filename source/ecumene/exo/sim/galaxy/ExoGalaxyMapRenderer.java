package ecumene.exo.sim.galaxy;

import java.beans.ExceptionListener;

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
}
