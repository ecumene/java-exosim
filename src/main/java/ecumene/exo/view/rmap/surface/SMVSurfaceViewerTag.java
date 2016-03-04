package ecumene.exo.view.rmap.surface;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.view.IViewerTag;
import ecumene.exo.view.rmap.solar.RMVSolarMapRenderer;

import java.beans.ExceptionListener;

public class SMVSurfaceViewerTag implements IViewerTag {

    public SMVSurfaceRendererConfig config;

    public SMVSurfaceViewerTag(SMVSurfaceRendererConfig config){
        this.config = config;
    }

    @Override
    public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
        return new SMVSurfaceViewer(id, listener, ExoRuntime.INSTANCE.getContext().getSurface().getSurfaceMap(), config, args);
    }

    @Override
    public String getIdentifier() {
        return "Planet surface simulation";
    }
}
