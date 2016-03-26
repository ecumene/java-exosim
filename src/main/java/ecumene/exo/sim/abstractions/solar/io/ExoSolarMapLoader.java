package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import ecumene.exo.sim.abstractions.solar.gen.ExoSolarMapBuilder;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joml.Vector2f;

public class ExoSolarMapLoader {
    public ExoSolarMap from(Document document){
        ExoSolarMapBuilder builder = new ExoSolarMapBuilder(System.currentTimeMillis());

        Element root = document.getRootElement();

        ExoSolarDefaults defaults = new ExoSolarDefaults();

        for(Element element : root.getChildren()) {
            if (element.getName().toUpperCase().equals("DEFAULT")) parseDefaultList(builder, defaults, root, element);   // Parse & set defaults
            if (element.getName().toUpperCase().equals("OBJECT"))  builder.addObject(parseObject(defaults, new Vector2f(), root, element)); // Load objects defined at root node
        }

        return builder.build();
    }

    private void parseDefaultList(ExoSolarMapBuilder builder, ExoSolarDefaults defaults, Element rootElement, Element defaultElement){
        // Construct default settings from an XML node
        for (Attribute attrib : defaultElement.getAttributes()) {
            String[] values = attrib.getValue().split(" ");
            if (attrib.getName().toUpperCase().contains("NAME")) defaults.name = attrib.getValue();
            if (attrib.getName().toUpperCase().contains("MASS")) defaults.mass = Float.parseFloat(attrib.getValue());
            if (attrib.getName().toUpperCase().contains("VELOCITY")) defaults.velocity = new Vector2f(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
            if (attrib.getName().toUpperCase().contains("POSITION")) defaults.position = new Vector2f(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
        }
        //TODO: Calculate parent vector... Somehow
        for(Element element : defaultElement.getChildren()) if(element.getName().toUpperCase().equals("OBJECT"))
            builder.addObject(parseObject(defaults, new Vector2f(0, 0), rootElement, element));
        for(Element element : defaultElement.getChildren()) if (element.getName().toUpperCase().equals("DEFAULT"))
            parseDefaultList(builder, defaults, rootElement, element);// Recursively check default lists for defaults
    }

    private ExoSolarObject parseObject(ExoSolarDefaults defaults, Vector2f parent, Element root, Element object){
        return defaults.fill(new XMLSolarObject(object, parent));
    }

}
