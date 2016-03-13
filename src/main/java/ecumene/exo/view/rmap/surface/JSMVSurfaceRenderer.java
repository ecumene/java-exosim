package ecumene.exo.view.rmap.surface;

import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.surface.ExoSFeature;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.view.rmap.surface.feature.IFeatureFilterRenderer;
import ecumene.exo.view.rmap.surface.feature.IFeatureLayerRenderer;
import ecumene.exo.view.rmap.surface.feature.IFeatureRenderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

// Doesn't extend JRMViewer cause honestly it kinda sucks!
public class JSMVSurfaceRenderer extends JPanel implements ISimContextListener {
    private Vector3f                 navigation;
    private SMVSurfaceRendererConfig renderingConfig;
    private ExoSurfaceMap            map;

    public JSMVSurfaceRenderer(ExoSurfaceMap map, SMVSurfaceRendererConfig config, Vector3f navigation){
        this.navigation = navigation;
        this.map = map;
        this.renderingConfig = config;

        setFocusable(true);

        addMouseWheelListener((MouseWheelEvent e) -> {
                navigation.z += (float) e.getPreciseWheelRotation() * -.2f;
                repaint();
        });

        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { }
            @Override public void keyReleased(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    navigation.y += 4;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    navigation.y -= 4;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    navigation.x += 4;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    navigation.x -= 4;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        graphics.setColor(new Color(0, 0, 0));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(new Color(0, 255, 0));

        if(map != null){
            for(ExoSFeatureLayer layer : map.getLayers()) {                                     // Loop all layers
                for (IFeatureLayerRenderer layerRenderer : renderingConfig.getLayerRenderers()) // Loop all renderers
                    layerRenderer.renderLayer(this, graphics, layer);                           // Render that layer
                // It's  not   a  bug,  it's   a    FEATURE.
                for (ExoSFeature feature : layer.getFeatures()) {
                    for (IFeatureRenderer featureRenderer : renderingConfig.getFeatureRenderers()) {
                        Vector2f screenPosition = new Vector2f(feature.getRPoint().getPosition());
                        screenPosition.x *= +Math.abs(navigation.z);
                        screenPosition.y *= +Math.abs(navigation.z);
                        screenPosition.add(new Vector2f(navigation.x, navigation.y));
                        screenPosition.add(new Vector2f(0, 10));
                        featureRenderer.renderFeature(this, graphics, feature, screenPosition);
                    }
                }
            }
            for(ExoSFeatureFilter filter : map.getFilters())                                      // Loop all filters
                for(IFeatureFilterRenderer filterRenderer : renderingConfig.getFilterRenderers()) // Loop all renderers
                    filterRenderer.renderFilter(this, graphics, filter);                          // Render that filter
        }
    }

    public Vector3f getNavigation() {
        return navigation;
    }

    public void setMap(ExoSurfaceMap map) {
        this.map = map;
    }

    public ExoSurfaceMap getMap() {
        return map;
    }

    @Override
    public void onContextChanged(SimContext context) {
        repaint();
    }
}
