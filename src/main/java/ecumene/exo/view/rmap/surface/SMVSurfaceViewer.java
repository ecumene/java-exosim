package ecumene.exo.view.rmap.surface;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.view.ViewerRunnable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.beans.ExceptionListener;
import java.io.File;

public class SMVSurfaceViewer extends ViewerRunnable {

    private String[]            args;
    private ExceptionListener   listener;
    private ExoSurfaceMap       surfaceMap;
    private JSMVSurfaceRenderer renderer;
    private JFrame              frame;
    private SMVSurfaceRendererConfig config;

    public SMVSurfaceViewer(int id, ExceptionListener listener, ExoSurfaceMap map, SMVSurfaceRendererConfig config, String[]args){
        super(id, listener, args);
        this.surfaceMap = map;
        this.args = args;
        this.listener = listener;
        this.config = config;
    }

    @Override
    public void init() throws Throwable {
        frame = new JFrame("RMap Point Rendering System");
        frame.setVisible(false);
        if(renderer == null) renderer = new JSMVSurfaceRenderer(surfaceMap, config);
        {
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);
            frame.setIconImage(ImageIO.read(new File("./resources/logo.png")));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(renderer);
        }
        frame.setVisible(true);
    }

    @Override
    public void kill(int id) {
        frame.dispose();
    }

    @Override
    public void onStep(SimContext context, int step) {
        if(surfaceMap != null) surfaceMap.onStep(context, step);
        frame.repaint();
    }

    @Override
    public void onContextChanged(SimContext context) {
        renderer.setMap(context.getSurface().getSurfaceMap());
        renderer.onContextChanged(context);
    }

    public ExceptionListener getExceptionListener() {
        return listener;
    }

    public String[] getArgs() {
        return args;
    }
}
