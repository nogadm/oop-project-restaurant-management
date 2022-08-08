package application.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import application.exceptions.IncorrectPasswordException;
import application.exceptions.idDoesntExistException;
import application.model.Customer;
import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

	public class LoginController {
		@FXML
		TextField usernameField, passwordField;
		
		private Stage stage;
		private Scene scene;
		private Parent root;
		 
		//check username and password and open proper authorizations
		public void login(ActionEvent e) throws IOException {
			try {
				File data = new File("Rest.ser");
				if (!data.exists())
					throw new FileNotFoundException();
			}
			catch (FileNotFoundException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Data was not found, cannot load system", ButtonType.OK);
				alert.show();
			}
			try {
				String username = usernameField.getText();
				String password = passwordField.getText();	
				//manager mode
				if (username.equals("manager") && password.equals("manager")) {
					Restaurant.getInstance().setCurrentUserId(0);
					//open manager authorizations
					 root = FXMLLoader.load(getClass().getResource("../view/HomeManager.fxml"));
					 stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					 scene = new Scene(root);
					 stage.setScene(scene);
					 stage.show(); 
				}
				else {
					//customer mode
					int id = Integer.valueOf(username);
					Customer customer = Restaurant.getInstance().getRealCustomer(id);
					//if user name is incorrect
					if (customer == null)
						throw new idDoesntExistException();
					//password has to match user name
					if (customer.getPassword().equals(password)) {
						//save current user
						Restaurant.getInstance().setCurrentUserId(id);
						//open customer authorizations
						root = FXMLLoader.load(getClass().getResource("../view/HomeUser.fxml"));
						stage = (Stage)((Node)e.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show(); 
					}
					else 
						throw new IncorrectPasswordException();
				}
				usernameField.clear();
				passwordField.clear();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				usernameField.clear();
				passwordField.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				usernameField.clear();
				passwordField.clear();
			}
			catch (IncorrectPasswordException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				passwordField.clear();
			}
		}
	}
