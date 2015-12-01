package ecumene.exo.sim.map.real.example;

import java.beans.ExceptionListener;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.map.real.ExoRMapRenderer;
import ecumene.exo.sim.map.real.RMap;

public class ExoExampleRMapRenderer extends ExoRMapRenderer {

	public ExoExampleRMapRenderer(int id, ExceptionListener exceptionListener, RMap map, String[] args) {
		super(id, exceptionListener, map, args);
	}

	@Override
	public void onContextChanged(ESContext context) {}

	@Override
	public void onStep(ESContext context, int step, float interp) {}
}
