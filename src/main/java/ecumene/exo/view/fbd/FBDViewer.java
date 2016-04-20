package ecumene.exo.view.fbd;

import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.Force;
import ecumene.exo.sim.common.physics.instant.InsFBody;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class FBDViewer extends JFrame {
    private InsFBody body;
    private int      largeVal;
    private boolean  normalized = true;

    protected JPanel viewerPane;
    protected JPanel topPanel;

    public FBDViewer(Vector2f north, int width, int height){
        super("Instant FBD Viewer");
        setSize(width, height);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.body = null;

        setVisible(true);
        viewerPane = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponents(graphics);
                if(body != null) paintContentPane(graphics, getWidth(), getHeight());
            }
        };
        viewerPane.setPreferredSize(new Dimension(width, height));
        getContentPane().setLayout(new BorderLayout());
        JRadioButton normalize = new JRadioButton("Normalize Forces");
        topPanel = new JPanel();
        topPanel.add(normalize);
        normalize.addActionListener(actionEvent -> {
            normalized = ((JRadioButton) actionEvent.getSource()).isSelected();
            this.repaint();
        });
        normalize.setSelected(true);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(viewerPane, BorderLayout.CENTER);
        pack();
    }

    private void paintContentPane(Graphics graphics, int width, int height){
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(20, 20, 20, 255));
        g.translate(width / 2, height / 2);

        if(body.getShape().equals(FreeBodyShape.BALL)){
            int x = (int) ((body.getMass() / largeVal) * height / 3);
            g.drawOval(-x / 2, -x / 2, x, x);
        }

        //TODO Render Box
        //if(body.getShape().equals(FreeBodyShape.BOX)) g.drawRect(-(int) (largeVal / body.getMass())/2, -(int) (largeVal / body.getMass())/2,
        //        (int) (largeVal / body.getMass()), (int) (largeVal / body.getMass()));

        Force fnet = new Force("Fnet (Normalized)", new Vector2f());

        for(Force force : body.getForces()){
            Vector2f privateForce = new Vector2f(force.getForce());
            if(normalized) privateForce.normalize().mul(largeVal);
            int x = (int) ((privateForce.x / largeVal) * height / 3);
            int y = (int) ((privateForce.y / largeVal) * height / 3);
            drawArrow(g, 0, 0, x, y*-1);
            fnet.getForce().add(privateForce);
            if(force.getName().contains("<") || force.getName().contains(">")){
                String name = force.getName();
                int rightIndex = force.getName().indexOf(">");
                int leftIndex  = force.getName().indexOf("<");
                name = name.replace(">", "").replace("<", "");
                AttributedString as = new AttributedString(name);
                if(rightIndex != -1) as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB,   rightIndex, name.length());
                if(leftIndex  != -1) as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, leftIndex,  name.length());
                ((Graphics2D) graphics).drawString(as.getIterator(), x, y*-1);
            } else ((Graphics2D) graphics).drawString(force.getName(), x, y*-1);
        }

        if(body.getForces().length != 1) {
            graphics.setColor(new Color(128, 128, 255));
            fnet.getForce().normalize().mul(largeVal);
            int x = (int) ((fnet.getForce().x / largeVal) * height / 3);
            int y = (int) ((fnet.getForce().y / largeVal) * height / 3);
            drawArrow(g, 0, 0, x, y*-1);
            ((Graphics2D) graphics).drawString(fnet.getName(), x, y*-1);
        }
    }

    /**
     * Draws an arrow on the given Graphics2D context
     * @param g The Graphics2D context to draw on
     * @param x The x location of the "tail" of the arrow
     * @param y The y location of the "tail" of the arrow
     * @param xx The x location of the "head" of the arrow
     * @param yy The y location of the "head" of the arrow
     */
    private void drawArrow( Graphics2D g, int x, int y, int xx, int yy )
    {
        float arrowWidth = 5.0f ;
        float theta = 0.423f ;
        int[] xPoints = new int[ 3 ] ;
        int[] yPoints = new int[ 3 ] ;
        float[] vecLine = new float[ 2 ] ;
        float[] vecLeft = new float[ 2 ] ;
        float fLength;
        float th;
        float ta;
        float baseX, baseY ;

        xPoints[ 0 ] = xx ;
        yPoints[ 0 ] = yy ;

        // build the line vector
        vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
        vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

        // build the arrow base vector - normal to the line
        vecLeft[ 0 ] = -vecLine[ 1 ] ;
        vecLeft[ 1 ] = vecLine[ 0 ] ;

        // setup length parameters
        fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
        th = arrowWidth / ( 2.0f * fLength ) ;
        ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

        // find the base of the arrow
        baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
        baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
        yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
        xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
        yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );

        g.drawLine( x, y, (int)baseX, (int)baseY ) ;
        g.fillPolygon( xPoints, yPoints, 3 ) ;
    }

    private static Point midpoint(Point p1, Point p2) {
        return new Point((int)((p1.x + p2.x)/2.0),
                (int)((p1.y + p2.y)/2.0));
    }

    public void setBody(InsFBody body){
        this.body = body;
        largeVal = (int) findScale(body);
        repaint();
    }

    private static float findScale(InsFBody body){
        float scale = 0;
        for(Force force : body.getForces()){
            if(force.getMagnitude() > body.getMass()) scale = force.getMagnitude();
        }
        if(scale < body.getMass()) scale = body.getMass();
        return scale;
    }

    public InsFBody getBody() {
        return body;
    }
}
