package application.view;

import static application.util.Utills.LoadFXML;

import java.io.IOException;

import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserOptionsController {

	@FXML
	BorderPane pannelRoot;
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	//log out of customer mode and go back to log in screen
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

	//customers can edit their own details
	public void updateCustomer(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateCustomer.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//customers can change their own password
	public void changePassword(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ChangePassword.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//queries
	public void queriePopularComponents(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/queriePopularComponents.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void querieRelevantDishList(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieRelevantDishList.fxml"); 
		pannelRoot.setCenter(root);		
	}

	public void querieCookByExpert(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieCookByExpert.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//view restaurant's menu and add dishes to new order
	public void restaurantMenu(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RestaurantMenu.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//customers can view all dishes they chose for new order, and change components
	public void shoppingCart(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ShoppingCart.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//display past orders of customer
	public void orderHistory(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/OrderHistory.fxml"); 
		pannelRoot.setCenter(root);
	}
	
}
	
	
	

