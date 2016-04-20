package ecumene.exo.view.rmap.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.viewer.IViewerTag;
import ecumene.exo.runtime.workspace.ExoWorkspace;
import org.jdom2.Element;
import org.joml.Vector3f;

public class RMVGalaxyViewerTag implements IViewerTag {
	
	public RMVGalaxyViewerTag() {}

	@Override
	public String getIdentifier() {
		return "Galaxy Viewer";
	}

	@Override
	public void parseWorkspace(ExoWorkspace workspace) throws Throwable {
		if(workspace.getWorkspaceProperties().get("galaxy") != null)
			for(Element element : workspace.getWorkspaceProperties().get("galaxy"))
				ExoRuntime.INSTANCE.runViewer(1, new String[]{});
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		Vector3f navigation = new Vector3f();
		for(int i = 0; i < args.length; i++){
			if(args[i].toUpperCase().contains("N")){
				navigation.x = Float.parseFloat(args[i + 1]);
				navigation.y = Float.parseFloat(args[i + 2]);
				navigation.z = Float.parseFloat(args[i + 3]);
				System.out.println("Running gen with : " + navigation + " navigation");
			}
		}
		return new RMVGalaxyMapRenderer(id, listener, navigation);
	}
	
}
