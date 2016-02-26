package ecumene.exo.view.rmap.planet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Map;

import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.planet.ExoPlanetMap;
import ecumene.exo.sim.planet.TrackingParameters;
import org.joml.Vector2f;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.view.rmap.JRMViewer;

public class JRMVPlanetRenderer extends JRMViewer implements ISimContextListener {

    protected boolean showMaterials = false;
    protected boolean showVectors   = false;
    private ExoPlanetMap map;

    public JRMVPlanetRenderer(ExoPlanetMap map) {
        super(null);
        getRendererList().add(new RMVPlanetObjectRenderer(this));
        getRendererList().add(new RMVPlanetMoonObjectRenderer(this));
        getRendererList().remove(getDefaultRPointRenderer());

        this.map = map;

        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e)    {}
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e)  {
                if(e.getKeyCode() == KeyEvent.VK_V) showVectors = !showVectors;
                if(e.getKeyCode() == KeyEvent.VK_M) showMaterials = !showMaterials;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(this.pMap != null){
            Graphics2D graphics = (Graphics2D) g;
            graphics.drawString("Solar Abstraction", 0, 10);
            graphics.drawString("Press M to toggle materials", 0, 20);
            graphics.drawString("Press V to toggle vectors", 0, 30);
            graphics.drawString("Artifact Count: " + pMap.getMap().length, 0, 40);

            Iterator it = map.getPlanet().getTrackedMoons().entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                TrackingParameters moonTrack = (TrackingParameters) pair.getValue();
                for(int i = 0; i < moonTrack.getPreviousPositions().size(); i++) {
                    RPoint point = moonTrack.getPreviousPositions().get(i);
                    if(point != null) {
                        Vector2f navPos = new Vector2f(point.position);
                        Vector2f screenPos;
                        navPos.x *= Math.abs(navigation.z);
                        navPos.y *= -Math.abs(navigation.z);
                        navPos.x += navigation.x;
                        navPos.y += navigation.y;
                        screenPos = new Vector2f(navPos);
                        screenPos.x += (getWidth() / 2);
                        screenPos.y += (getHeight() / 2);

                        drawPoint((Graphics2D) g, -1, point, point.position, navPos, screenPos);
                    }
                }
            }
        }
    }

    public void onStep(){
        if(map != null) pMap = map.step();
    }

    @Override
    public String getNullPMapError() {
        return super.getNullPMapError();
    }

    @Override
    public void onContextChanged(SimContext context) {
        repaint();
    }

    public ExoPlanetMap getMap() {
        return map;
    }

    public void setPlanetMap(ExoPlanetMap planetMap) {
        this.map = planetMap;
    }

    public boolean getShowMaterials(){
        return showMaterials;
    }

    public boolean getShowVectors(){
        return showVectors;
    }

}
