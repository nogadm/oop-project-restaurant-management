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

public class MangOptionsController {

	@FXML
	BorderPane pannelRoot;
	
	private Stage stage;
	private Scene scene;
	private Parent root;

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

	//view details of all objects in the restaurant
	public void viewCook(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewCook.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewCustomer(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewCustomer.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewDeliveryPerson(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewDeliveryPerson.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewComponent(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewComponent.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewDish(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewDish.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewOrder(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewOrder.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewDeliveryArea(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewDeliveryArea.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void viewDelivery(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/ViewDelivery.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//update details of all objects in the restaurant
	public void updateCook(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateCook.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateCustomer(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateCustomer.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateDeliveryPerson(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateDeliveryPerson.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateComponent(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateComponent.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateDish(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateDish.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateOrder(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateOrder.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateDeliveryArea(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateDeliveryArea.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void updateDelivery(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/UpdateDelivery.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//open queries
	public void querieGetDeliveriesByPerson(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieGetDeliveriesByPerson.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void querieNumberOfDeliveriesPerType(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieNumberOfDeliveriesPerType.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void querieRevenueFromExpressDeliveries(ActionEvent e) throws IOException { 
		Pane root = LoadFXML(getClass(), "../view/querieRevenueFromExpressDeliveries.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void queriePopularComponents(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/queriePopularComponents.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void querieProfitRelation(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieProfitRelation.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void querieAIMachine(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/querieAIMachine.fxml"); 
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
	
	//display and add to black list
	public void blackList(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/BlackList.fxml"); 
		pannelRoot.setCenter(root);
	}
	
	//add new objects to restaurant
	public void addCook(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddCook.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addCustomer(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddCustomer.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addDeliveryPerson(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddDeliveryPerson.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addComponent(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddComponent.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addDish(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddDish.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addOrder(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddOrder.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addArea(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddArea.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addRegularDelivery(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddRegularDelivery.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void addExpressDelivery(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/AddExpressDelivery.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	//remove objects from restaurant
	public void removeCook(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveCook.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeCustomer(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveCustomer.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeDeliveryPerson(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveDeliveryPerson.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeComponent(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveComponent.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeDish(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveDish.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeOrder(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveOrder.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeArea(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveArea.fxml"); 
		pannelRoot.setCenter(root);		
	}
	
	public void removeDelivery(ActionEvent e) throws IOException {
		Pane root = LoadFXML(getClass(), "../view/RemoveDelivery.fxml"); 
		pannelRoot.setCenter(root);		
	}
}
	
	
	

