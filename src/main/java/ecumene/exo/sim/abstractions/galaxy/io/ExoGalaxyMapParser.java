package ecumene.exo.sim.abstractions.galaxy.io;

import ecumene.exo.sim.abstractions.galaxy.ExoGSingularity;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.gen.ExoGalaxyMapBuilder;
import ecumene.exo.sim.abstractions.solar.io.ExoSolarMapObject;
import ecumene.exo.sim.common.xml.Parser;
import ecumene.exo.sim.common.xml.XMLObject;
import org.jdom2.Element;

import java.util.Arrays;
import java.util.Stack;

public class ExoGalaxyMapParser extends Parser {

    private ExoGalaxyMapBuilder builder; // Honestly it's just easier :/
    private ExoGalaxyMap        galaxyMap;

    public ExoGalaxyMapParser(Element element){
        super(element);
        String[] singularitySplit = element.getAttributeValue("singularity").split("( )|(,)");
        builder = new ExoGalaxyMapBuilder(System.currentTimeMillis());
        builder.setSingularity(new ExoGSingularity(singularitySplit[0],
                                XMLObject.getFloat(singularitySplit[1])));
        Stack defaults = new Stack();
        defaults.push(null);
        recursiveParseNode(defaults, element);
        galaxyMap = builder.build();
    }

    private void recursiveParseNode(Stack xmlDefaultsStack, Element node){
        xmlDefaultsStack.push(new ExoGalaxyMapObject((ExoGalaxyMapObject) xmlDefaultsStack.lastElement(), node));
        for(Element nodeChild : node.getChildren()){
            if(nodeChild.getName().toUpperCase().contains("CLU")) recursiveParseNode(xmlDefaultsStack, nodeChild);
            if(nodeChild.getName().toUpperCase().contains("GEN")) {
                ExoGalaxyMapObject object = new ExoGalaxyMapObject((ExoGalaxyMapObject) xmlDefaultsStack.lastElement(), nodeChild);
                builder.genCluster(object.getName(), object.getOrbiterNum(), object.getRadius(), object.getMassRange(), object.getSpeedRange(), object.getPosition());
                System.out.println(object.getPosition());
            }
        }
        xmlDefaultsStack.pop();
    }

    public ExoGalaxyMap getGalaxyMap() {
        return galaxyMap;
    }
}
