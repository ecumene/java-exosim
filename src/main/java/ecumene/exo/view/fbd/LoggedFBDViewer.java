package ecumene.exo.view.fbd;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class LoggedFBDViewer extends FBDViewer {
    private List<FreeBody> bodies;

    public LoggedFBDViewer(Vector2f north, int width, int height){
        super(north, width, height);
        bodies = new ArrayList<FreeBody>();
    }

    public List<FreeBody> getHistory(){
        return bodies;
    }

    @Override
    public void setBody(FreeBody body) {
        super.setBody(body);
        bodies.add(body);
    }
}
