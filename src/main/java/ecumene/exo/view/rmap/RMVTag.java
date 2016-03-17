package ecumene.exo.view.rmap;

import ecumene.exo.runtime.viewer.IViewerTag;
import org.joml.Vector3f;

import java.beans.ExceptionListener;

public abstract class RMVTag implements IViewerTag {
    public RMVTag(){}

    @Override
    public String getIdentifier(){
        return "Point Renderer";
    }

    @Override
    public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
        Vector3f navigation = new Vector3f();
        for(int i = 0; i < args.length; i++){
            if(args[i].toUpperCase().contains("N")){
                navigation.x = Float.parseFloat(args[i + 1]);
                navigation.y = Float.parseFloat(args[i + 2]);
                navigation.z = Float.parseFloat(args[i + 3]);
                System.out.println("Running galaxy with : " + navigation + " navigation");
            }
        }
        return constructRMV(id, listener, args, navigation);
    }

    public abstract Runnable constructRMV(int id, ExceptionListener listener, String[] args, Vector3f nav);
}
