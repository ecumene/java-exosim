package ecumene.exo.sim.solar;

import java.beans.ExceptionListener;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.map.real.ExoRMapRenderer;

public class ExoSolarMapRenderer extends ExoRMapRenderer {
	
	private String[]          args;
	private ExceptionListener listener;
	
	public ExoSolarMapRenderer(int id, ExceptionListener exceptionListener, ExoSolarMap map, String[] args){
		super(id, exceptionListener, map.getRenderable(), args);
		this.args = args;
		this.listener = exceptionListener;
	}
	
	@Override
	public void onContextChanged(ESContext context){
		this.pMap = new ExoSolarMap(context.getSolarSeed()).getRenderable();
		System.out.println("new solar!");
		System.out.println(context.getSolarSeed());
	}

	@Override
	public void onStep(ESContext context, int step) {
		// TODO: Make solar system step too!
	}
}
