package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLHandler {
	
	public Document parseXMLDocument(String file) throws ParserConfigurationException, SAXException, IOException{
		File xmlFile = new File(file); 
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		return document;
	}
	
	public List<City> extractNodes(Document document){
		List<City> result = new ArrayList<City>();
		NodeList nodeList = document.getElementsByTagName("NODE_COORD_SECTION");
		for(int i = 0; i < nodeList.getLength(); i++){
			City city = new City();
			Node node = nodeList.item(i);
			Node index = node.getFirstChild();
			String str = index.getNodeValue();
			int cityNumber = Integer.parseInt(str);
			city.setIndex(cityNumber);
			NodeList coordinates = ((Element)node).getElementsByTagName("real");
			for(int k = 0; k < coordinates.getLength(); k++){
				Node real = coordinates.item(k);
				str = real.getNodeValue();
				Double num = Double.valueOf(str);
				city.getCoordinates().add(num);
			}
			result.add(city);
		}
		return result;
	}
}
