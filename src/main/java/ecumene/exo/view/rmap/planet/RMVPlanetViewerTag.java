package ecumene.exo.view.rmap.planet;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.workspace.ExoWorkspace;
import ecumene.exo.view.rmap.RMVTag;
import org.jdom2.Element;
import org.joml.Vector3f;

import java.beans.ExceptionListener;

public class RMVPlanetViewerTag extends RMVTag {
    public RMVPlanetViewerTag(){}

    @Override
    public String getIdentifier(){
        return "Planet Viewer";
    }

    @Override
    public void parseWorkspace(ExoWorkspace workspace) throws Throwable {
        if(workspace.getWorkspaceProperties().get("planet") != null)
            for(Element element : workspace.getWorkspaceProperties().get("planet"))
                ExoRuntime.INSTANCE.runViewer(3, new String[]{});
    }

    @Override
    public Runnable constructRMV(int id, ExceptionListener listener, String[] args, Vector3f nav) {
        return new RMVPlanetMapRenderer(id, listener, ExoRuntime.INSTANCE.getContext().getPlanet().getMap(), nav);
    }
}
