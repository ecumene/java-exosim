package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.common.xml.Parser;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExoSolarMapParser extends Parser {
    private List<IExoSolarObject> objects;
    private ExoSolarMap           solarMap;

    public ExoSolarMapParser(Element element){
        super(element);
        objects = new ArrayList<>();
        Stack defaults = new Stack();
        defaults.push(null);
        recursiveParseNode(defaults, element);
        solarMap = new ExoSolarMap(objects);
    }

    private void recursiveParseNode(Stack xmlDefaultsStack, Element node){
        xmlDefaultsStack.push(new ExoSolarMapObject((ExoSolarMapObject) xmlDefaultsStack.lastElement(), node));
        for(Element nodeChild : node.getChildren()){
            if(nodeChild.getName().toUpperCase().contains("NODE"))      recursiveParseNode(xmlDefaultsStack, nodeChild);
            if(nodeChild.getName().toUpperCase().contains("COMPONENT")) objects.add(new ExoSolarMapObject((ExoSolarMapObject) xmlDefaultsStack.lastElement(), nodeChild).toObject());
        }
        xmlDefaultsStack.pop();
    }

    public List<IExoSolarObject> getObjects() {
        return objects;
    }

    public ExoSolarMap getSolarMap() {
        return solarMap;
    }

}
