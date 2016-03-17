package ecumene.exo.view.fbd;

import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;

public class FBDViewer extends JFrame {
    private FreeBody body;
    private float    scale;

    public FBDViewer(FreeBody body, Vector2f north, float width, float height){
        this.body = body;
        setBody(body);
    }

    @Override
    public void paintComponents(Graphics graphics) {
        super.paintComponents(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(20, 20, 20, 255));
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        if(body.getShape().equals(FreeBodyShape.BALL)) g.fillOval(0,0,
                (int) (body.getMass() * scale), (int) (body.getMass() * scale));
        if(body.getShape().equals(FreeBodyShape.BOX)) g.fillRect(-(int) (body.getMass() * scale)/2, -(int) (body.getMass() * scale)/2,
                (int) (body.getMass() * scale)/2, (int) (body.getMass() * scale)/2);
    }

    public void setBody(FreeBody body){
        this.body = body;
        scale = this.findScale(body);
    }

    private static float findScale(FreeBody body){
        float scale = 0;
        for(Force force : body.getForces())
            if(force.getMagnitude() > scale) scale = force.getMagnitude();
        if(scale > body.getMass()) scale = body.getMass();
        return scale;
    }

    public FreeBody getBody() {
        return body;
    }
}
