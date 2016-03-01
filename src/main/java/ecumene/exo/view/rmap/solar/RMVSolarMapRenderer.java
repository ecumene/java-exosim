package ecumene.exo.view.rmap.solar;

import java.beans.ExceptionListener;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;

public class RMVSolarMapRenderer extends RMVRenderer {
	
	private String[]          args;
	private ExceptionListener listener;
	private ExoSolarMap       solarMap;
	
	public RMVSolarMapRenderer(int id, ExceptionListener exceptionListener, ExoSolarMap map, String[] args){
		super(id, exceptionListener, new RMap(), args);
		solarMap = map;
		this.args = args;
		this.listener = exceptionListener;
	}
	
	@Override
	protected JRMViewer constructRenderer() {
		return new JRMVSolarRenderer(solarMap);
	}
	
	@Override
	public void onContextChanged(SimContext context){
		((JRMVSolarRenderer) renderer).setSolarMap(context.getSolarSystem().getSolarMap());
		((JRMVSolarRenderer) renderer).onContextChanged(context);
		frame.repaint();
	}

	@Override
	public void onStep(SimContext context, int step) {
		((JRMVSolarRenderer) renderer).onStep();
		frame.repaint();
	}
}
