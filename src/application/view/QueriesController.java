package application.view;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TreeSet;

import application.model.Customer;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.ExpressDelivery;
import application.model.Order;
import application.model.RegularDelivery;
import application.model.Restaurant;
import application.util.Expertise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class QueriesController implements Initializable{

	@FXML
	ComboBox<DeliveryPerson> dpBox;
	@FXML
	ComboBox<Integer> monthBox;
	@FXML
	ComboBox<Order> ordersBox;
	@FXML
	ComboBox<DeliveryArea> areasBox;
	@FXML
	ComboBox<Customer> customersBox;
	@FXML
	ComboBox<Expertise> expertiseBox;
	@FXML
	TextField deliveriesField, expressField, regularField, revenuField, ordersField,
	cooksField;
	@FXML
	Text yearText;
	@FXML
	RadioButton addButton, removeButton;
	@FXML
	TextArea machineDecision, relationField, popCompField, blackListArea, relevantField;
	
	TreeSet<Order> ordersForTheMachine = new TreeSet<>();
	
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (dpBox != null)
			dpBox.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
		if (areasBox != null)
			areasBox.getItems().addAll(Restaurant.getInstance().getAreas().values());
		if (ordersBox != null)
			ordersBox.getItems().addAll(Restaurant.getInstance().getOrders().values());
		if (customersBox != null) {
			//manager mode - add all customers
			if (Restaurant.getInstance().getCurrentUserId() == 0)
				customersBox.getItems().addAll(Restaurant.getInstance().getCustomers().values());
			//user mode - add only current customer
			else 
				customersBox.getItems().addAll(Restaurant.getInstance()
						.getRealCustomer(Restaurant.getInstance().getCurrentUserId()));
		}	
		if (expertiseBox != null)
			expertiseBox.getItems().addAll(Expertise.values());
		if (monthBox != null) {
			Integer[] monthsArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
			monthBox.getItems().addAll(monthsArray);
		}
		if (yearText != null)
			yearText.setText(String.valueOf(LocalDate.now().getYear()));
	}
	
	//getDeliveriesByPerson querie
	public void getDeliveriesByPersonQuerie(ActionEvent e) throws IOException {
		if (dpBox.getValue() != null && monthBox.getValue() != null) {
				DeliveryPerson dp = dpBox.getValue();
				int month = monthBox.getValue();
				deliveriesField.setText(Restaurant.getInstance().getDeliveriesByPerson(dp, month).toString());
		}
	}

	//getNumberOfDeliveriesPerType querie
	public void NumberOfDeliveriesPerTypeQuerie(ActionEvent e) throws IOException {
		int regularNum = Restaurant.getInstance().getNumberOfDeliveriesPerType().get("Regular delivery");
		int expressNum = Restaurant.getInstance().getNumberOfDeliveriesPerType().get("Express delivery");
		expressField.setText(String.valueOf(expressNum));
		regularField.setText(String.valueOf(regularNum));
	}
	
	//revenueFromExpressDeliveries querie
	public void RevenueFromExpressDeliveriesQuerie(ActionEvent e) throws IOException {
		DecimalFormat df2 = new DecimalFormat("#.###");
		double revenu = Restaurant.getInstance().revenueFromExpressDeliveries();
		revenuField.setText(String.valueOf(df2.format(revenu)));
	}
	
	//getPopularComponents querie
	public void popularComponentsQuerie(ActionEvent e) throws IOException {
		String popular = Restaurant.getInstance().getPopularComponents().toString();
		popCompField.setText(popular.substring(1, popular.length()-1));
	}
	
	//getProfitRelation querie
	public void profitRelationQuerie(ActionEvent e) throws IOException {
		String profitRelation = Restaurant.getInstance().getProfitRelation().toString();
		relationField.setText(profitRelation.substring(1, profitRelation.length()-1));
	}
	
	//createAIMacine querie
	public void aiMachineQuerie(ActionEvent e) throws IOException {
		TreeSet<Delivery> decision = new TreeSet<>();
		if (dpBox.getValue() != null && areasBox.getValue() != null && !ordersForTheMachine.isEmpty()) {
			decision = Restaurant.getInstance().createAIMacine(dpBox.getValue(), 
					areasBox.getValue(), ordersForTheMachine);
			String printResult = "";
			//display ai machine's results on screen
			for (Delivery d : decision) {
				if (d instanceof ExpressDelivery)
				printResult += printResult + "Express Delivery -> id: " +d.getId() + ", Delivery Person: " 
				+ d.getDeliveryPerson() + ", Area: " + d.getArea() + ", Order: " + 
						((ExpressDelivery)d).getOrder() + "\n";
				if (d instanceof RegularDelivery)
					printResult += printResult + "Regular Delivery -> id: " +d.getId() + ", Delivery Person: " 
							+ d.getDeliveryPerson() + ", Area: " + d.getArea() + ", Orders: " + 
									((RegularDelivery)d).getOrders() + "\n";
			}
			machineDecision.setText(printResult);
		}
	}
	
	//choose orders for createAIMacine querie
	public void addAndRemoveOrders(ActionEvent e) throws IOException {
		if (ordersBox.getValue() != null) {
			if (addButton.isSelected())
				ordersForTheMachine.add(ordersBox.getValue());
			if (removeButton.isSelected() && ordersForTheMachine.contains(ordersBox.getValue()))
				ordersForTheMachine.remove(ordersBox.getValue());
			String orders = ordersForTheMachine.toString();
			ordersField.setText(orders.substring(1, orders.length()-1));
		}
	}
	
	//getReleventDishList querie
	public void relevantDishListQuerie(ActionEvent e) throws IOException {
		if (customersBox.getValue() != null) {
			Customer customer = customersBox.getValue();
			String relevant = Restaurant.getInstance().getReleventDishList(customer).toString();
			relevantField.setText(relevant.substring(1, relevant.length()-1));
		}
	}
	
	//GetCooksByExpertise querie
	public void cookByExpertQuerie(ActionEvent e) throws IOException {
		if (expertiseBox.getValue() != null) {
			Expertise expertise = expertiseBox.getValue();
			String expertCooks = Restaurant.getInstance().GetCooksByExpertise(expertise).toString();
			cooksField.setText(expertCooks.substring(1, expertCooks.length()-1));
		}
	}
	
	//display all customers in the black list
	public void displayBlackList(ActionEvent e) throws IOException {
		blackListArea.clear();
		if (!Restaurant.getInstance().getBlackList().isEmpty()) {
			for (Customer blackCustomer : Restaurant.getInstance().getBlackList()) 
				blackListArea.appendText(blackCustomer.getFirstName() + " " + blackCustomer.getLastName() +
						" (id: " + blackCustomer.getId() + ")\n");
		}
		else
			blackListArea.appendText("No customers on black list.");
	}
	
	//manager can add customers to the black list
	public void addToBlackList(ActionEvent e) throws IOException {
		if (customersBox.getValue() != null) {
			if (!Restaurant.getInstance().getBlackList().contains(customersBox.getValue())) {
				Restaurant.getInstance().addCustomerToBlackList(customersBox.getValue());
				Alert alert = new Alert(AlertType.INFORMATION, "Added customer (id: " +
				customersBox.getValue().getId() + ") to black list", ButtonType.OK);
				alert.show();
				displayBlackList(e);
			}
			
		}
		
		
		
		
	}
	
	
}
