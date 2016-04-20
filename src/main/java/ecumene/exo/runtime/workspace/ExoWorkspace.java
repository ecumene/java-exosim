package ecumene.exo.runtime.workspace;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExoWorkspace {
    private Map<String, List<Element>> workspaceProperties;
    private Document doc;

    public ExoWorkspace(Document document){
        doc = document;
        workspaceProperties = new HashMap<>();
        for(Element children : document.getRootElement().getChildren()) {
            if (workspaceProperties.get(children.getName()) == null) workspaceProperties.put(children.getName(), new ArrayList<>());
            workspaceProperties.get(children.getName()).add(children);
        }
    }

    public static ExoWorkspace loadWorkspace(File file) throws JDOMException, IOException {
        return new ExoWorkspace(new SAXBuilder().build(file));
    }

    public Document getDocument() {
        return doc;
    }

    public Map<String, List<Element>> getWorkspaceProperties() {
        return workspaceProperties;
    }
}
