package ecumene.exo.view.rmap.planet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sun.org.apache.xpath.internal.SourceTree;
import ecumene.exo.sim.planet.ExoPlanetMap;
import org.joml.Vector2f;
import org.joml.Vector3f;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.solar.ExoSolarMap;
import ecumene.exo.sim.solar.IExoSolarObject;
import ecumene.exo.view.rmap.JRMViewer;

public class JRMVPlanetRenderer extends JRMViewer implements ISimContextListener {

    protected boolean showMaterials = false;
    protected boolean showVectors   = false;
    private ExoPlanetMap map;

    public JRMVPlanetRenderer(ExoPlanetMap map) {
        super(null);
        getRendererList().add(new RMVPlanetObjectRenderer(this));
        getRendererList().remove(getDefaultRPointRenderer());

        this.map = map;

        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e)    {}
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e)  {
                if(e.getKeyCode() == KeyEvent.VK_V) showVectors = !showVectors;
                if(e.getKeyCode() == KeyEvent.VK_M) showMaterials = !showMaterials;
                System.out.println(showVectors);
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
