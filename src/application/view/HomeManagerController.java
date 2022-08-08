package application.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeManagerController implements Initializable {
	
	@FXML
	Text dateText;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (dateText != null)
			dateText.setText( String.valueOf(LocalDate.now()));
	}
	
	//open all options according to manager authorization
	public void openMenu(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../view/MangOptions.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//log out of manager mode and go back to log in screen
	public void logOut(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//close and exit system
	public void exitSystem(ActionEvent e) throws IOException {
		//save data before 
		Restaurant.getInstance().serialize();
		System.exit(0);
	}

	
}
