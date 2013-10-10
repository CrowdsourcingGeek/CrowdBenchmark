package CrowdBenchmark.tools.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigReader {

	private String currentpath = "";
	private Map<String, String> listConfig = new HashMap<String, String>();

	public Map<String, String> getConfig() {
		return listConfig;
	}

	public void setCurrentPth(String path) {
		currentpath = path;
	}

	public void readfile(String filename) {
		String path = currentpath + filename;

		try {
			BufferedReader dataInput = new BufferedReader(new FileReader(
					new File(path)));
			String line;
			while ((line = dataInput.readLine()) != null) {
				line = line.replaceAll(" ", "");
				line = line.replaceAll("\"", "");
				String[] pair = line.split("=");
				if (pair.length == 2) {
					listConfig.put(pair[0], pair[1]);
				}
			}

			dataInput.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void parseNode(Element root) {
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Node node = list.item(i);

				if (node.getChildNodes().getLength() == 1
						&& node.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE) {
					String key = node.getNodeName();
					String value = node.getTextContent();
					value = value.replaceAll(" ", "");
					value = value.replaceAll("\"", "");
					listConfig.put(key, value);
				} else {
					parseNode((Element) list.item(i));
				}

			}
		}
	}

}
