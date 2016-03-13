package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.gen.ExoSolarMapBuilder;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ExoSolarMapLoader {
    /**
     * Takes a JDOM2 document and parses it for
     * solar components
     * @param document The jdom 2 document
     */
    public void from(Document document){
        ExoSolarMapBuilder builder = new ExoSolarMapBuilder(System.currentTimeMillis());

        Element root = document.getRootElement();
        for(Element element : root.getChildren()){
            if(element.getText().toUpperCase().contains("OBJECT")){
                float    mass     = 0;
                Vector2f velocity = new Vector2f();
                Vector2f position = new Vector2f();
                for(Attribute attrib : element.getAttributes()){
                    if(attrib.getName().toUpperCase().contains("MASS")) mass = Float.parseFloat(attrib.getValue());
                    if(attrib.getName().toUpperCase().contains("VELO")){
                        velocity = parseVec(attrib.getValue());
                    }
                    if(attrib.getName().toUpperCase().contains("POSI")) {
                        position = parseVec(attrib.getValue());
                    }
                }
                builder.addObject(new ExoSolarObject(velocity, position, mass));
            }
        }
    }

    public Vector2f parseVec(String str){
        str.replace(",", "");
        str.replace(")", "");
        String[] split = str.split(" ");
        return new Vector2f(Float.parseFloat(split[0]), Float.parseFloat(split[1]));
    }
}
