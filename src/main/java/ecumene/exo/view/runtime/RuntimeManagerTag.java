package ecumene.exo.view.runtime;

import java.beans.ExceptionListener;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.viewer.IViewerTag;
import ecumene.exo.runtime.workspace.ExoWorkspace;
import org.jdom2.Element;

public class RuntimeManagerTag implements IViewerTag {

	@Override
	public void parseWorkspace(ExoWorkspace workspace) throws Throwable {
		if(workspace.getWorkspaceProperties().get("controller") != null)
			for(Element element : workspace.getWorkspaceProperties().get("controller"))
				ExoRuntime.INSTANCE.runViewer(0, new String[]{});
	}

	@Override
	public String getIdentifier() {
		return "Runtime Analyzer";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new RuntimeManager(id, listener);
	}
	
}
