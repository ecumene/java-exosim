package ecumene.exo.view.rmap.solar;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.workspace.ExoWorkspace;
import ecumene.exo.view.rmap.RMVTag;

import ecumene.exo.runtime.ExoRuntime;
import org.jdom2.Element;
import org.joml.Vector3f;

public class RMVSolarViewerTag extends RMVTag {

	@Override
	public String getIdentifier() {
		return "Solar Viewer";
	}

	@Override
	public void parseWorkspace(ExoWorkspace workspace) throws Throwable {
		if(workspace.getWorkspaceProperties().get("solar") != null)
			for(Element element : workspace.getWorkspaceProperties().get("solar"))
				ExoRuntime.INSTANCE.runViewer(2, new String[]{});
	}

	@Override
	public Runnable constructRMV(int id, ExceptionListener listener, String[] args, Vector3f nav) {
		return new RMVSolarMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getSolarSystem().getSolarMap(), nav);
	}
}
	

