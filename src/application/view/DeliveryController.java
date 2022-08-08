package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;

import application.exceptions.ConvertToExpressException;
import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.ExpressDelivery;
import application.model.Order;
import application.model.RegularDelivery;
import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DeliveryController implements Initializable {

	@FXML
	TextField deliveryID, postageField, ordersField;
	@FXML
	Text idText, deliveryPersonText, areaText, isDeliveredText, dateText, ordersText, typeText, postageText;
	@FXML
	ComboBox<DeliveryPerson> dpBox;
	@FXML
	ComboBox<DeliveryArea> areaBox;
	@FXML
	ComboBox<Order> ordersBox;
	@FXML
	ComboBox<Delivery> deliveriesBox;
	@FXML
	RadioButton yesButton, noButton, addButton, removeButton;
	@FXML
	DatePicker datePicker;
	
	private TreeSet<Order> ordersForNewRegDelivery;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (dpBox != null)
			dpBox.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
		if (areaBox != null)
			areaBox.getItems().addAll(Restaurant.getInstance().getAreas().values());
		if (ordersBox != null)
		ordersBox.getItems().addAll(Restaurant.getInstance().getOrders().values());
		if (deliveriesBox != null)
			deliveriesBox.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
		ordersForNewRegDelivery = new TreeSet<>();
	}
	
	//view delivery details
	public void deliveryByID(ActionEvent e) throws IOException {
		if (!deliveryID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(deliveryID.getText());
				if (Restaurant.getInstance().getDeliveries().keySet().contains(id)) {
					Delivery delivery = Restaurant.getInstance().getRealDelivery(id);
					idText.setText(String.valueOf(id));
					String dpName = delivery.getDeliveryPerson().getFirstName() + 
							" " + delivery.getDeliveryPerson().getLastName();
					deliveryPersonText.setText(dpName);
					areaText.setText(delivery.getArea().getAreaName());
					if (delivery.isDelivered())
						isDeliveredText.setText("Yes");
					else 
						isDeliveredText.setText("No");
					dateText.setText(delivery.getDeliveredDate().toString());
					String orders;
					if (delivery instanceof RegularDelivery) {
						orders = ((RegularDelivery) delivery).getOrders().toString();
						ordersText.setText(orders.substring(1, orders.length()-1));
						typeText.setText("Regular");
						postageText.setText("0");
					}
					if (delivery instanceof ExpressDelivery) {
						ordersText.setText(String.valueOf(((ExpressDelivery) delivery).getOrder().getId()));
						typeText.setText("Express");
						postageText.setText(String.valueOf(((ExpressDelivery) delivery).getPostage()));
					}
					deliveryID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				deliveryID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				deliveryID.clear();
			}
		}
	}
	
	//view delivery details on update screen
	public void viewDeliveryBeforeUpdate(ActionEvent e) throws IOException {
		if (!deliveryID.getText().isEmpty()) {
			postageField.clear();
			try {
				int id = Integer.parseInt(deliveryID.getText());
				if (Restaurant.getInstance().getDeliveries().keySet().contains(id)) {
					Delivery delivery = Restaurant.getInstance().getRealDelivery(id);
					dpBox.setPromptText(delivery.getDeliveryPerson().toString());
					areaBox.setPromptText(delivery.getArea().toString());
					if (delivery.isDelivered())
						yesButton.setSelected(true);
					else
						noButton.setSelected(true);
					datePicker.setValue(delivery.getDeliveredDate());
					if (delivery instanceof ExpressDelivery) {
						postageField.appendText(String.valueOf(((ExpressDelivery) delivery).getPostage()));
						ordersField.setText(((ExpressDelivery) delivery).getOrder().toString());
						postageField.setEditable(true);
					}
					if (delivery instanceof RegularDelivery) {
						postageField.appendText("0");
						String orders = ((RegularDelivery) delivery).getOrders().toString();
						ordersField.setText(orders.substring(1, orders.length()-1));
						postageField.setEditable(false);
					}
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				deliveryID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				deliveryID.clear();
			}
		}
	}
	
	//update delivery details according to user's input
	public void updateDeliveryInfo(ActionEvent e) throws IOException {
		try {
			if (deliveryID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(deliveryID.getText());
			Delivery delivery = Restaurant.getInstance().getRealDelivery(id);
			if (dpBox.getValue() != null) {
				if (!delivery.getDeliveryPerson().equals(dpBox.getValue()))
					delivery.setDeliveryPerson(dpBox.getValue());
			}
			if (areaBox.getValue() != null) {
				if (!delivery.getArea().equals(areaBox.getValue()))
					delivery.setArea(areaBox.getValue());
			}	
			if (yesButton.isSelected())
				delivery.setDelivered(true);
			if (noButton.isSelected())
				delivery.setDelivered(false);
			if (!delivery.getDeliveredDate().equals(datePicker.getValue()))
				delivery.setDeliveredDate(datePicker.getValue());
			if (delivery instanceof ExpressDelivery)
				((ExpressDelivery) delivery).setPostage(Double.valueOf(postageField.getText()));
			Restaurant.getInstance().getDeliveries().put(id, delivery);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Delivery details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			deliveryID.clear();
		}	
		catch (NumberFormatException exception) {
			Alert alert = new Alert(AlertType.ERROR, "Enter digits only.", ButtonType.OK);
			alert.show();
		}
	}		
	
	//update orders in delivery according to user's input
	public void addAndRemoveOrderFromDelivery(ActionEvent e) throws IOException {
		int id = Integer.parseInt(deliveryID.getText());
		Delivery delivery = Restaurant.getInstance().getRealDelivery(id);
		if (addButton.isSelected()) {
			if (delivery instanceof ExpressDelivery) {
				TreeSet<Order> orders = new TreeSet<>();
				orders.add(((ExpressDelivery) delivery).getOrder());
				delivery = new RegularDelivery(orders, delivery.getDeliveryPerson(), delivery.getArea(),
						delivery.isDelivered(), delivery.getDeliveredDate());
			}
			((RegularDelivery) delivery).addOrder(ordersBox.getValue());
		}
		if (removeButton.isSelected()) {
			if (delivery instanceof RegularDelivery && 
					((RegularDelivery)delivery).getOrders().contains(ordersBox.getValue())) {
				((RegularDelivery)delivery).removeOrder(ordersBox.getValue());
				try {
					if (((RegularDelivery)delivery).getOrders().size()==1)
						throw new ConvertToExpressException();
				}
				catch(ConvertToExpressException exception) {
					RegularDelivery rd = (RegularDelivery)delivery;
					delivery = new ExpressDelivery(rd.getDeliveryPerson(), rd.getArea(),rd.isDelivered(),
							rd.getOrders().first() ,rd.getDeliveredDate());
				}
			}
		}
		if (delivery instanceof RegularDelivery) {
			String orders = ((RegularDelivery) delivery).getOrders().toString();
			ordersField.setText(orders.substring(1, orders.length()-1));	
		}
		if (delivery instanceof ExpressDelivery)
			ordersField.setText(((ExpressDelivery) delivery).getOrder().toString());
		Restaurant.getInstance().getDeliveries().put(id, delivery);
		Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Orders updated", ButtonType.OK);
		successfulUpdate.show();
	}
	
	//add new regular delivery to restaurant
	public void addRegularDelivery(ActionEvent e) throws IOException {
		try {
			if (dpBox.getValue() == null || areaBox.getValue() == null || datePicker.getValue() == null || 
					ordersForNewRegDelivery.isEmpty())
				throw new MissingFieldsException();
			else {	
				Delivery newRegularDelivery = new RegularDelivery(ordersForNewRegDelivery, dpBox.getValue(),
						areaBox.getValue(), false, datePicker.getValue());
				if (yesButton.isSelected()) 
					newRegularDelivery.setDelivered(true);
				Restaurant.getInstance().addDelivery(newRegularDelivery);
				Alert alert = new Alert(AlertType.INFORMATION, "Added regular delivery (id: " 
				+ newRegularDelivery.getId() + ")", ButtonType.OK);
				alert.show();	
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
	}
	
	//add orders to new area
	public void chooseOrdersForNewDelivery(ActionEvent e) throws IOException {
		if (addButton.isSelected())
			ordersForNewRegDelivery.add(ordersBox.getValue());
		if (removeButton.isSelected() && ordersForNewRegDelivery.contains(ordersBox.getValue()))
			ordersForNewRegDelivery.remove(ordersBox.getValue());
		String orders = ordersForNewRegDelivery.toString();
		ordersField.setText(orders.substring(1, orders.length()-1));
	}
	
	//add new express delivery to restaurant
	public void addExpressDelivery(ActionEvent e) throws IOException {
		try {
			if (dpBox.getValue() == null || areaBox.getValue() == null || datePicker.getValue() == null || 
					ordersBox.getValue() == null || postageField.getText().isBlank())
				throw new MissingFieldsException();
			else {	
				Delivery newExpressDelivery = new ExpressDelivery(dpBox.getValue(), areaBox.getValue(),
						false, ordersBox.getValue(), Double.valueOf(postageField.getText()),
						datePicker.getValue());
				if (yesButton.isSelected()) 
					newExpressDelivery.setDelivered(true);
				Restaurant.getInstance().addDelivery(newExpressDelivery);
				Alert alert = new Alert(AlertType.INFORMATION, "Added express delivery (id: " +
				newExpressDelivery.getId() + ")", ButtonType.OK);
				alert.show();
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
		catch (NumberFormatException exception) {
			Alert alert = new Alert(AlertType.ERROR, "Enter digits only.", ButtonType.OK);
			alert.show();
		}
	}
	
	//remove existing delivery from restaurant
	public void removeDelivery(ActionEvent e) throws IOException {
		if (deliveriesBox.getValue() != null) {
			Restaurant.getInstance().removeDelivery(deliveriesBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed delivery", ButtonType.OK);
			alert.show();
		}
	}
	
	
	
}
