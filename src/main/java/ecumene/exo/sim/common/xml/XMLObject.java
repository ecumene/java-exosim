package ecumene.exo.sim.common.xml;

import org.jdom2.Element;
import org.joml.Vector2f;

public class XMLObject {
    protected String  name;
    protected Element element;

    public XMLObject(Element element){
        this.element = element;
        this.name = element.getAttributeValue("name");
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
    }

    public static Vector2f parsePosition(String attribute) throws IllegalArgumentException {
        String[] attribs = attribute.replace("*", "").split("(,)|( )");
        if(attribs.length != 2) throw new IllegalArgumentException("Too many arguments in string while parsing position " + attribs.length);
        if(attribute.contains("*"))
            return new Vector2f((float) Math.cos(getFloat(attribs[1])) * getFloat(attribs[0]),
                                (float) Math.sin(getFloat(attribs[1])) * getFloat(attribs[0]));
        else return new Vector2f(getFloat(attribs[0]),
                                 getFloat(attribs[1]));
    }

    private static float getFloat(String num){
        try{
            return Float.parseFloat(num);
        } catch (Exception e){
            throw new IllegalArgumentException("Failed to parse number " + num);
        }
    }

    public static void main(String[] args){
        System.out.println(parsePosition("20 "));
    }
}
