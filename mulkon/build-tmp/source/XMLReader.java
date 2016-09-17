import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {

    public void processDocument(String fileName) throws IOException, ParserConfigurationException, SAXException {
        Document doc = createDocument(fileName);

        System.out.println("Root element: "
                + doc.getDocumentElement().getNodeName());

        printEmployeeInfo(doc);
    }

    /**
     * Traverse an XML document, printing the information on each employee in
     * the list. The document is assumed to contain a list of "employee" nodes,
     * each of which has a "firstname" and a "lastname" element. We extract the
     * values associated with each of those tags, and print them.
     * 
     * @param doc
     *            the XML document containing the employee info
     */
    private void printEmployeeInfo(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("employee");

        System.out.println("Information for each employee");

        for (int i = 0; i < nodeList.getLength(); i++) {
            printEmployee(nodeList.item(i));
        }
    }

    /**
     * Print the employee encapsulated in the given XML node.
     * 
     * @param node
     *            an XML node encapsulating an employee
     */
    private void printEmployee(Node node) {
        /*
         * In this simple example everything is in fact an element, but in a
         * more complex XML document structure that might not necessarily be
         * true.
         */
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element employee = (Element) node;
            printComponent(employee, "firstname");
            printComponent(employee, "lastname");
        }
    }

    /**
     * Print the specified component of the given employee. Note that there's
     * not actually anything here that assumes that the given Element is
     * actually an employee. All that's really needed is that it contains a tag
     * with the name given in component.
     * 
     * @param employee
     *            the XML Element containing an employee
     * @param component
     *            the particular component of Employee to print
     */
    private void printComponent(Element employee, final String component) {
        /*
         * This has to dig into the element to find the appropriate value to
         * print. We get all the elements with the tag name given in
         * "component", and then take the first thing from that list. (In this
         * case there's should only be one sub-element with that tag, so we can
         * just take the first and move on.) We then need to take the value of
         * the first child of that sub-element, which should be the text
         * associated with the specified sub-element.
         */
        NodeList elements = employee.getElementsByTagName(component);
        Element firstElement = (Element) elements.item(0);
        NodeList children = firstElement.getChildNodes();
        String componentText = children.item(0).getNodeValue();
        System.out.println(component + " : " + componentText);
    }

    /**
     * Opens the specified file and creates an XML document object from it which
     * can then be parsed/traversed.
     * 
     * @param fileName
     *            the file to parse
     * @return the XML document corresponding to the specified XML file
     * 
     * @throws FileNotFoundException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document createDocument(String fileName)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {
        InputStream inputStream = new FileInputStream(fileName);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc;
    }
}