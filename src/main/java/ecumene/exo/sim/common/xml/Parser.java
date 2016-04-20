package ecumene.exo.sim.common.xml;

import org.jdom2.Element;

public abstract class Parser {
    protected Element rootElement;

    public Parser(Element element){
        this.rootElement = element;
    }

    public Element getRootElement() {
        return rootElement;
    }
}
