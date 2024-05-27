package com.puppycrawl.tools.checkstyle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(args[0]);

        System.out.println("Available configs:\n");  // Starting text and a blank line for spacing
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.print("<module");
                if (elem.hasAttributes()) {
                    for (int j = 0; j < elem.getAttributes().getLength(); j++) {
                        Node attr = elem.getAttributes().item(j);
                        System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                    }
                }
                System.out.println(">");
                NodeList childNodes = elem.getChildNodes();
                for (int k = 0; k < childNodes.getLength(); k++) {
                    Node child = childNodes.item(k);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) child;
                        System.out.print("  <property");
                        if (childElement.hasAttributes()) {
                            for (int m = 0; m < childElement.getAttributes().getLength(); m++) {
                                Node attr = childElement.getAttributes().item(m);
                                System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                            }
                        }
                        System.out.println("/>");
                    }
                }
                System.out.println("</module>\n"); // End of module tag with an additional newline for spacing
            }
        }
    }
}
