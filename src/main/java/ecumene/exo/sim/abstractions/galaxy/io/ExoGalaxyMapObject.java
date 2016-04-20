package ecumene.exo.sim.abstractions.galaxy.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.common.xml.XMLObject;
import org.jdom2.Element;
import org.joml.Vector2f;

public class ExoGalaxyMapObject extends XMLObject {

    private Integer  orbiterNum;
    private Float    radius;
    private Vector2f massRange;
    private Vector2f speedRange;
    private Vector2f position;

    public ExoGalaxyMapObject(ExoGalaxyMapObject parent, Element element){
        super(parent, element);
        this.element = element;
        if(element.hasAttributes() || parent != null){
            if(element.getAttribute("orbiters")   != null) orbiterNum = getInt(element.getAttributeValue("orbiters"));
            if(element.getAttribute("radius")     != null) radius     = getFloat(element.getAttributeValue("radius"));
            if(element.getAttribute("massRange")  != null) massRange  = parseVector(element.getAttributeValue("massRange"));
            if(element.getAttribute("speedRange") != null) speedRange = parseVector(element.getAttributeValue("speedRange"));
            if(element.getAttribute("position")   != null){
                System.out.println("Found position!");
                position   = parseVector(element.getAttributeValue("position"));
            }
            if(parent == null){
                if(orbiterNum == null) orbiterNum = 10;
                if(radius     == null) radius     = 10.0F;
                if(massRange  == null) massRange  = new Vector2f(1, 10);
                if(speedRange == null) speedRange = new Vector2f(0.0f);
                if(position   == null) position   = new Vector2f(0.0f);
            } else {
                if(orbiterNum == null) orbiterNum = parent.getOrbiterNum();
                if(radius     == null) radius     = parent.getRadius();
                if(massRange  == null) massRange  = parent.getMassRange();
                if(speedRange == null) speedRange = parent.getSpeedRange();
                if(position   == null) position   = parent.getPosition();
            }
        }
    }

    public Integer getOrbiterNum() {
        return orbiterNum;
    }

    public Float getRadius() {
        return radius;
    }

    public Vector2f getMassRange() {
        return massRange;
    }

    public Vector2f getSpeedRange() {
        return speedRange;
    }

    public Vector2f getPosition() {
        return position;
    }
}
