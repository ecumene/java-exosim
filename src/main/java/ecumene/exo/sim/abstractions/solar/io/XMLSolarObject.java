package ecumene.exo.sim.abstractions.solar.io;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.Map;

public class XMLSolarObject {
    public Element  xmlNode;
    public Vector2f parentPosition; // TODO: Change to XMLSolarObject

    public String           name;
    public float            mass;
    public Vector2f         position;
    public Vector2f         velocity;
    public EnumVelocityType velocityType = EnumVelocityType.SELF;

    public XMLSolarObject(Element element, Vector2f parentPosition){
        this.xmlNode = element;
        this.position = parentPosition;

        for(Attribute attrib : xmlNode.getAttributes()){
            String value = attrib.getValue().trim().replaceAll(" +", " ");
            String[] values = value.split(" ");
            if(attrib.getName().toUpperCase().contains("POS")) position = new Vector2f(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
            if(attrib.getName().toUpperCase().contains("VEL")) {
                System.out.println(Arrays.toString(values));
                if(values.length > 2){
                    velocityType = EnumVelocityType.valueOf(values[0].toUpperCase());
                    velocity = new Vector2f(Float.parseFloat(values[1]), Float.parseFloat(values[2]));
                }else velocity = new Vector2f(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
            }
            if(attrib.getName().toUpperCase().contains("MAS")) mass = Float.parseFloat(values[0]);
        }
    }
}
