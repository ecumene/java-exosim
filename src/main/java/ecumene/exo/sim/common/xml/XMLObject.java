package ecumene.exo.sim.common.xml;

import org.jdom2.Element;
import org.joml.Vector2f;

public class XMLObject {
    protected XMLObject parent;
    protected String    name;
    protected Element   element;

    public XMLObject(XMLObject parent, Element element){
        this.element = element;
        this.name    = element.getAttributeValue("name");
        this.parent  = parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(XMLObject parent){
        this.parent = parent;
    }

    public Element getElement() {
        return element;
    }

    public static boolean isVector(String attribute){
        return attribute.contains("(,)|( )");
    }

    public static Vector2f parseVector(String attribute) throws IllegalArgumentException {
        String[] attribs = attribute.replace("*", "").split("(,)|( )");
        if(attribs.length != 2) throw new IllegalArgumentException("Illegal number of arguments in string while parsing position " + attribs.length);
        if(attribute.contains("*"))
            return new Vector2f((float) Math.cos(Math.toRadians(getFloat(attribs[1]))) * getFloat(attribs[0]),
                                (float) Math.sin(Math.toRadians(getFloat(attribs[1]))) * getFloat(attribs[0]));
        else return new Vector2f(getFloat(attribs[0]),
                                 getFloat(attribs[1]));
    }

    public static int getInt(String num){
        try{
            return Integer.parseInt(num);
        } catch (Exception e){
            throw e;
        }
    }

    public static float getFloat(String num){
        try{
            return Float.parseFloat(num);
        } catch (Exception e){
            throw e;
        }
    }

}
