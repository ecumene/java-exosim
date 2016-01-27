package ecumene.exo.view.rmap.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.view.IViewerTag;
import ecumene.exo.view.ViewerRunnable;

public class RMVGalaxyMapTag implements IViewerTag {
	
	public RMVGalaxyMapTag() {}

	@Override
	public String getIdentifier() {
		return "Galaxy simulation";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new RMVGalaxyMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getGalaxy().getMap(), args);
	}
	
}
