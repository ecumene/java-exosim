package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.common.xml.Parser;
import ecumene.exo.sim.common.xml.XMLObject;
import org.jdom2.Element;
import org.joml.Vector2f;

public class ExoSolarMapObject extends XMLObject {

    private Element element;

    private Float    mass;
    private Vector2f position;
    private Vector2f velocity;

    public ExoSolarMapObject(ExoSolarMapObject parent, Element element){
        super(parent, element);
        this.element = element;
        if(element.hasAttributes() || parent == null) {
            if(element.getAttribute("mass")     != null) mass     = getFloat   (element.getAttributeValue("mass"));
            if(element.getAttribute("position") != null) position = parseVector(element.getAttributeValue("position"));
            if(element.getAttribute("v0")       != null) {
                if((element.getAttributeValue("v0")).toUpperCase().contains("ORBIT-GRAV")) {
                    Vector2f distance = new Vector2f();
                    Vector2f.sub(position, parent.getPosition(), distance);
                    float mass2 = new Float(mass == null ? (parent == null ? parent.getMass() : 1) : mass);
                    float gravityForce = (ExoSolarMap.GRAVITY * mass2 * parent.mass)
                            / (distance.length() * distance.length());
                    velocity = new Vector2f(distance.perpendicular()).normalize().mul((gravityForce) * getFloat(element.getAttributeValue("v0").split("( )|(,)|(:)")[1]));
                } else if((element.getAttributeValue("v0")).toUpperCase().contains("ORBIT-MAG")){
                        Vector2f distance = new Vector2f();
                        Vector2f.sub(position, parent.getPosition(), distance);
                    velocity = new Vector2f(distance.perpendicular()).normalize().mul(getFloat(element.getAttributeValue("v0").split("( )|(,)|(:)")[1]));
                } else {
                    velocity = parseVector(element.getAttributeValue("v0"));
                }
            }
        }
        if(parent == null){
            if (mass == null)     mass     = 1.0F;
            if (position == null) position = new Vector2f();
            if (velocity == null) velocity = new Vector2f();
        } else {
            if (mass == null) mass = parent.getMass();
            if (position == null) position = parent.getPosition();
            if (velocity == null) velocity = parent.getVelocity();
        }
    }

    public IExoSolarObject toObject() {
        return new ExoSolarObject(getName(), getMass(), getPosition(), getVelocity());
    }

    @Override
    public Element getElement() {
        return element;
    }

    public float getMass() {
        return mass;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }
}
