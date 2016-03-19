package ecumene.exo.view.fbd;

import com.sun.imageio.plugins.jpeg.JPEGImageMetadataFormatResources;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;

public class FBDViewer extends JFrame {
    private FreeBody body;
    private float    scale;

    public FBDViewer(FreeBody body, Vector2f north, int width, int height){
        super("FBD Viewer");

        setSize(width, height);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.body = body;
        setBody(body);

        setVisible(true);
        System.out.println(getContentPane().getSize());
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(20, 20, 20, 255));
        if(body.getShape().equals(FreeBodyShape.BALL)){
            g.drawOval(0,0, 100, 100);
        }
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
        if(scale < body.getMass()) scale = body.getMass();
        return scale;
    }

    public FreeBody getBody() {
        return body;
    }
}
