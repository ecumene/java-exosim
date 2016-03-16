package ecumene.exo.sim.abstractions.solar.io;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.Map;

public class XMLSolarObject {
    public Element             xmlNode;
    public Map<String, String> xmlAttributes;

    public XMLSolarObject(){

        for(Attribute attrib : xmlNode.getAttributes()){
            
        }

    }
}
