package mif.vu.lt.resathlon.managers;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mif.vu.lt.resathlon.models.Athlete;
import mif.vu.lt.resathlon.models.Params;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlManager {
    
    private static DocumentBuilderFactory docFactory;
    private static DocumentBuilder docBuilder;
    private static Document doc;
    private static Element rootElement;
    
    public XmlManager() throws ParserConfigurationException {
        docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        
        // root elements
        rootElement = doc.createElement("ATHLETES");
        doc.appendChild(rootElement);

    }
    
    /*
     * Create Element in Root Element
     */
    public void createElement(Athlete atl) {
        Element el = doc.createElement("ATHLETE");
        rootElement.appendChild(el);
        
        Element name_surname = doc.createElement("NAME_SURNAME");
        name_surname.appendChild(doc.createTextNode(atl.get_name_surname()));
        el.appendChild(name_surname);
        
        Element total_score = doc.createElement("TOTAL_SCORE");
        total_score.appendChild(doc.createTextNode(String.valueOf(atl.get_total_score())));
        el.appendChild(total_score);
        
        el.appendChild(form_competitions(atl));
        
    }   
    
    /*
     * Create Competitions Elements
     */
    private Element form_competitions(Athlete atl) {          
          Element competitions = doc.createElement("COMPETITIONS");
          for (Params param : Params.values()) {
              
              Element competition = doc.createElement(param.name());
              
              Element points = doc.createElement("POINTS");              
              points.appendChild(doc.createTextNode(String.valueOf(atl.get_points_by_name(param.name()))));
              competition.appendChild(points);
              
              Element score = doc.createElement("SCORE");
              score.appendChild(doc.createTextNode(String.valueOf(atl.get_score_by_name(param.name()))));
              competition.appendChild(score);
              
              Element place = doc.createElement("PLACE");
              Integer[] places = ScoreManager.get_event_place(param.name(), atl.get_score_by_name(param.name()));
              String str = "";
              for (Integer i : places) {
                  str += String.valueOf(i);
              }
              place.appendChild(doc.createTextNode(str));
              competition.appendChild(place);
              // Place
              
              competitions.appendChild(competition);
          }
          return competitions;
    }
    
    public void write_xml() throws TransformerConfigurationException {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/my_raw.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            
            // Pretty reading
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            transformer.transform(source, result);
            
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("File saved!");
    }
}
