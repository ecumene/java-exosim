package ecumene.exo.sim.abstractions.planet.io;

import ecumene.exo.sim.abstractions.planet.ExoPlanet;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMoon;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.abstractions.solar.io.ExoSolarMapObject;
import ecumene.exo.sim.common.xml.Parser;
import ecumene.exo.sim.common.xml.XMLObject;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExoPlanetMapParser extends Parser {
    private List<ExoPlanetMoon> moons;
    private ExoPlanet           planet;
    private ExoPlanetMap        planetMap;

    public ExoPlanetMapParser(Element element){
        super(element);
        moons = new ArrayList<>();
        Stack defaults = new Stack();
        defaults.push(null);
        recursiveParseNode(defaults, element);
        // "Long-line-itis"
        planet = new ExoPlanet(new ExoSolarObject(element.getAttributeValue("name"), XMLObject.getFloat(element.getAttributeValue("mass"))),
                element.getAttributeValue("name"), moons.toArray(new ExoPlanetMoon[moons.size()]));
        planetMap = new ExoPlanetMap(planet);
    }

    private void recursiveParseNode(Stack xmlDefaultsStack, Element node){
        xmlDefaultsStack.push(new ExoPlanetMapObject((ExoPlanetMapObject) xmlDefaultsStack.lastElement(), node));
        for(Element nodeChild : node.getChildren()){
            if(nodeChild.getName().toUpperCase().contains("NODE"))      recursiveParseNode(xmlDefaultsStack, nodeChild);
            if(nodeChild.getName().toUpperCase().contains("COMPONENT")) moons.add(new ExoPlanetMapObject((ExoPlanetMapObject) xmlDefaultsStack.lastElement(), nodeChild).toMoon());
        }
        xmlDefaultsStack.pop();
    }

    public ExoPlanetMap getPlanetMap() {
        return planetMap;
    }

    public List<ExoPlanetMoon> getMoons() {
        return moons;
    }

    public ExoPlanet getPlanet() {
        return planet;
    }
}
