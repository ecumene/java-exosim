package ecumene.exo.view.rmap.solar;

import java.beans.ExceptionListener;

import org.joml.Vector2f;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.view.IViewerTag;

public class RMVSolarViewerTag implements IViewerTag {

	
	public RMVSolarViewerTag(){}
	
	@Override
	public String getIdentifier(){
		return "Solar simulation";
	}
	
	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new RMVSolarMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getSolarSystem().getSolarMap(), args);
	}
}
