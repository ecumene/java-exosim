package ecumene.exo.view.rmap.surface;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.view.rmap.RMVTag;
import org.joml.Vector3f;

import java.beans.ExceptionListener;

public class SMVSurfaceViewerTag extends RMVTag {

    public SMVSurfaceRendererConfig config;

    public SMVSurfaceViewerTag(SMVSurfaceRendererConfig config){
        this.config = config;
    }

    @Override
    public String getIdentifier() {
        return "Planet surface simulation";
    }

    @Override
    public Runnable constructRMV(int id, ExceptionListener listener, String[] args, Vector3f nav) {
        return new SMVSurfaceViewer(id, listener, ExoRuntime.INSTANCE.getContext().getSurface().getSurfaceMap(), config, nav);
    }
}
