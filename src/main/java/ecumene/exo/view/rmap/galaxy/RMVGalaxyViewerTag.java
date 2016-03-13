package ecumene.exo.view.rmap.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.view.IViewerTag;
import ecumene.exo.view.ViewerRunnable;
import org.joml.Vector3f;

public class RMVGalaxyViewerTag implements IViewerTag {
	
	public RMVGalaxyViewerTag() {}

	@Override
	public String getIdentifier() {
		return "Galaxy simulation";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		Vector3f navigation = new Vector3f();
		for(int i = 0; i < args.length; i++){
			if(args[i].toUpperCase().contains("N")){
				navigation.x = Float.parseFloat(args[i + 1]);
				navigation.y = Float.parseFloat(args[i + 2]);
				navigation.z = Float.parseFloat(args[i + 3]);
				System.out.println("Running galaxy with : " + navigation + " navigation");
			}
		}
		return new RMVGalaxyMapRenderer(id, listener, navigation);
	}
	
}
