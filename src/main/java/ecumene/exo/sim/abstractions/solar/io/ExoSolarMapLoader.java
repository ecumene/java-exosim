package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.gen.ExoSolarMapBuilder;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joml.Vector2f;

import java.util.List;

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
            if(element.getName().toUpperCase().equals("GENGROUP")){
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
