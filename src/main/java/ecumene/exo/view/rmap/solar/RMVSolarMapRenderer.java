package ecumene.exo.view.rmap.solar;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.view.rmap.JRMViewer;
import ecumene.exo.view.rmap.RMVRenderer;
import org.joml.Vector3f;

public class RMVSolarMapRenderer extends RMVRenderer {
	
	private ExoSolarMap solarMap;

	public RMVSolarMapRenderer(int id, ExceptionListener exceptionListener, ExoSolarMap map, Vector3f navigation){
		super(id, exceptionListener, new RMap(), navigation);
		solarMap = map;
	}
	
	@Override
	protected JRMViewer constructRenderer() {
		return new JRMVSolarRenderer(navigation, solarMap);
	}
	
	@Override
	public void onContextChanged(SimContext context){
		((JRMVSolarRenderer) renderer).setSolarMap(context.getSolarSystem().getSolarMap());
		((JRMVSolarRenderer) renderer).onContextChanged(context);
		frame.repaint();
	}

	@Override
	public void onStep(SimContext context, int step) {
		if(renderer == null){
			try{
				init();
			} catch (Throwable e){
				ExoRuntime.INSTANCE.getExceptionListener().exceptionThrown(new Exception(e));
			}
		}
		((JRMVSolarRenderer) renderer).onStep(context, step);
		frame.repaint();
	}
}
