package ecumene.exo.sim.util.xmlio;

import org.jdom2.Element;

import java.util.Arrays;
import java.util.List;

public abstract class ExoXMLObject {
    public ExoXMLObject       parent;
    public List<ExoXMLObject> children;
    public Element            element;

    public ExoXMLObject(ExoXMLObject parent, Element element, ExoXMLObject ... children){
        this.parent = parent;
        this.element = element;
        this.children = Arrays.asList(children);
    }

    public ExoXMLObject getParent() {
        return parent;
    }

    public List<ExoXMLObject> getChildren() {
        return children;
    }

    public Element getElement() {
        return element;
    }
    
}
