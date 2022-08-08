package application.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/* utility class globally and commonly used methods are implemented here*/
public final class Utills {
	
	// private constructor to prevent creation of object
	private Utills() {
		
	}
	
	
	public static <T extends Node> T LoadFXML(Class<?> cls, String path) throws IOException {
		return FXMLLoader.load(cls.getResource(path));
	}
}
