package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Customer;
import application.model.Delivery;
import application.model.Dish;
import application.model.Order;
import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OrderController implements Initializable {

	@FXML
	TextField orderID, dishesField;
	@FXML
	Text idText, customerText, dishesText, deliveryNumText;
	@FXML
	ComboBox<Customer> customerBox;
	@FXML
	ComboBox<Delivery> deliveryBox;
	@FXML
	ComboBox<Dish> dishesBox;
	@FXML
	ComboBox<Order> ordersBox;
	@FXML
	RadioButton addButton, removeButton;
	
	private ArrayList<Dish> dishesForNewOrder;
	
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (customerBox != null)
			customerBox.getItems().addAll(Restaurant.getInstance().getCustomers().values());
		if (deliveryBox != null)
			deliveryBox.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
		if (dishesBox != null)
			dishesBox.getItems().addAll(Restaurant.getInstance().getDishes().values());
		if (ordersBox != null)
			ordersBox.getItems().addAll(Restaurant.getInstance().getOrders().values());
		dishesForNewOrder = new ArrayList<>();
	}
	
	//view order details
	public void orderByID(ActionEvent e) throws IOException {
		if (!orderID.getText().isEmpty()) {
			try {
				int id = Integer.valueOf(orderID.getText());
				if (Restaurant.getInstance().getOrders().keySet().contains(id)) {
					Order order = Restaurant.getInstance().getRealOrder(id);
					idText.setText(String.valueOf(id));
					String custName = order.getCustomer().getFirstName()
							+ " " +order.getCustomer().getLastName();
					customerText.setText(custName);
					String dishes = order.getDishes().toString();
					dishesText.setText(dishes.substring(1, dishes.length()-1));
					deliveryNumText.setText(String.valueOf(order.getDelivery().getId()));
					orderID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				orderID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				orderID.clear();
			}
		}
	}
	
	//view order details on update screen
	public void viewOrderBeforeUpdate(ActionEvent e) throws IOException {
		if (!orderID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(orderID.getText());
				if (Restaurant.getInstance().getOrders().keySet().contains(id)) {
					Order order = Restaurant.getInstance().getRealOrder(id);
					customerBox.setPromptText(order.getCustomer().toString());
					deliveryBox.setPromptText(order.getDelivery().toString());
					String dishes = order.getDishes().toString();
					dishesField.setText(dishes.substring(1, dishes.length()-1));
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				orderID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				orderID.clear();
			}
		}
	}
	
	//update order details according to user's input
	public void updateOrderInfo(ActionEvent e) throws IOException {
		try {
			if (orderID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(orderID.getText());
			Order order = Restaurant.getInstance().getRealOrder(id);
			if (customerBox.getValue() != null) {
				if (!order.getCustomer().equals(customerBox.getValue()))
					order.setCustomer(customerBox.getValue());
			}
			if (deliveryBox.getValue() != null) {
				if (!order.getDelivery().equals(deliveryBox.getValue()))
					order.setDelivery(deliveryBox.getValue());
			}
			Restaurant.getInstance().getOrders().put(id, order);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Orders details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			orderID.clear();
		}	
	}		
	
	//update dishes in order according to user's input
	public void addAndRemoveDishFromOrder(ActionEvent e) throws IOException {
		int id = Integer.parseInt(orderID.getText());
		Order order = Restaurant.getInstance().getRealOrder(id);
		if (addButton.isSelected())
			order.addDish(dishesBox.getValue());
		if (removeButton.isSelected() && order.getDishes().contains(dishesBox.getValue()))
			order.removeDish(dishesBox.getValue());
		String dishes = order.getDishes().toString();
		dishesField.setText(dishes.substring(1, dishes.length()-1));
		Restaurant.getInstance().getOrders().put(id, order);
		Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Dishes updated", ButtonType.OK);
		successfulUpdate.show();
	}
	
	//add new order to restaurant
	public void addOrder(ActionEvent e) throws IOException {
		try {
			if (customerBox.getValue() == null || deliveryBox.getValue() == null ||
					dishesForNewOrder.isEmpty())
				throw new MissingFieldsException();
			else {	
				Order newOrder = new Order(customerBox.getValue(), dishesForNewOrder, deliveryBox.getValue());
				Restaurant.getInstance().addOrder(newOrder);
				Alert alert = new Alert(AlertType.INFORMATION, "Added order (id: " + newOrder.getId() +
						")", ButtonType.OK);
				alert.show();
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
	}
	
	//add dishes to new order
	public void chooseDishesForNewOrder(ActionEvent e) throws IOException {
		if (addButton.isSelected())
			dishesForNewOrder.add(dishesBox.getValue());
		if (removeButton.isSelected() && dishesForNewOrder.contains(dishesBox.getValue()))
			dishesForNewOrder.remove(dishesBox.getValue());
		String dishes = dishesForNewOrder.toString();
		dishesField.setText(dishes.substring(1, dishes.length()-1));
	}
	
	//remove existing order from restaurant
	public void removeOrder(ActionEvent e) throws IOException {
		if (ordersBox.getValue() != null) {
			Restaurant.getInstance().removeOrder(ordersBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed order", ButtonType.OK);
			alert.show();
		}
	}
		
	
}
