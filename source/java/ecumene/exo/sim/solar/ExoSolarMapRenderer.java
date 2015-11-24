package ecumene.exo.sim.solar;

import java.beans.ExceptionListener;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.map.real.ExoRMapRenderer;
import ecumene.exo.sim.map.real.JRMapRenderer;

public class ExoSolarMapRenderer extends ExoRMapRenderer {
	
	private String[]          args;
	private ExceptionListener listener;
	private ExoSolarMap       solarMap;
	
	public ExoSolarMapRenderer(int id, ExceptionListener exceptionListener, ExoSolarMap map, String[] args){
		super(id, exceptionListener, map.step(), args);
		solarMap = map;
		this.args = args;
		this.listener = exceptionListener;
	}
	
	@Override
	protected JRMapRenderer constructRenderer() {
		return new JExoSolarRenderer(solarMap);
	}
	
	@Override
	public void onContextChanged(ESContext context){
		System.out.println("Context changed");
		solarMap = new ExoSolarMap(context.getSolarSeed());
		((JExoSolarRenderer) renderer).setSolarMap(solarMap);
		((JExoSolarRenderer) renderer).onContextChanged(context);
		pMap = solarMap.step();
		frame.repaint();
	}

	@Override
	public void onStep(ESContext context, int step) {
		((JExoSolarRenderer) renderer).onStep(); // Let them take care of it!
		                                         // I'm not getting paid enough for this!
		                                         // I'm not getting paid at all!!
		frame.repaint();
	}
}
