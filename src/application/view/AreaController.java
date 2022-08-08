package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.Restaurant;
import application.util.Neighberhood;
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

public class AreaController implements Initializable{

	@FXML
	TextField areaID, nameField, dpField, deliveriesField, neighborhoodField, timeField;
	@FXML
	Text idText, nameText, deliveryPersonsText, deliveriesText, neighborhoodText, timeText;
	@FXML
	ComboBox<DeliveryPerson> dpBox;
	@FXML
	ComboBox<Delivery> deliveriesBox;
	@FXML
	ComboBox<Neighberhood> neighborhoodsBox;
	@FXML
	ComboBox<DeliveryArea> oldAreasBox, newAreasBox;
	@FXML
	RadioButton addDelPerButton, removeDelPerButton, addDeliveryButton, removeDeliveryButton, 
	addNeighButton, removeNeighButton, addButton, removeButton;
	
	private HashSet<Neighberhood> hoodsForNewArea;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (dpBox != null)
			dpBox.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
		if (deliveriesBox != null)
			deliveriesBox.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
		if (neighborhoodsBox != null)
			neighborhoodsBox.getItems().addAll(Neighberhood.values());
		if (oldAreasBox != null)
			oldAreasBox.getItems().addAll(Restaurant.getInstance().getAreas().values());
		if (newAreasBox != null)
			newAreasBox.getItems().addAll(Restaurant.getInstance().getAreas().values());
		hoodsForNewArea = new HashSet<>();
	}
	
	//view delivery area details
	public void areaByID(ActionEvent e) throws IOException {
		if (!areaID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(areaID.getText());
				if (Restaurant.getInstance().getAreas().keySet().contains(id)) {
					DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
					idText.setText(String.valueOf(id));
					nameText.setText(da.getAreaName());
					String dp = da.getDelPersons().toString();
					deliveryPersonsText.setText(dp.substring(1, dp.length()-1));
					String del = da.getDelivers().toString();
					deliveriesText.setText(del.substring(1, del.length()-1));
					String neighbor = da.getNeighberhoods().toString().replace('_', ' ');
					neighborhoodText.setText(neighbor.substring(1, neighbor.length()-1));
					timeText.setText(String.valueOf(da.getDeliverTime()));
					areaID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				areaID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				areaID.clear();
			}
		}
	}
	
	//view delivery area details on update screen
	public void viewAreaBeforeUpdate(ActionEvent e) throws IOException {
		if (!areaID.getText().isEmpty()) {
			nameField.clear();
			try {
				int id = Integer.parseInt(areaID.getText());
				if (Restaurant.getInstance().getAreas().keySet().contains(id)) {
					DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
					nameField.setText(da.getAreaName());
					String people = da.getDelPersons().toString();
					dpField.setText(people.substring(1, people.length()-1));
					String deliveries = da.getDelivers().toString();
					deliveriesField.setText(deliveries.substring(1, deliveries.length()-1));
					String neighbor = da.getNeighberhoods().toString();
					neighborhoodField.setText(neighbor.substring(1, neighbor.length()-1));
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				areaID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				areaID.clear();
			}
		}
	}

	//update area details according to user's input
	public void updateAreaInfo(ActionEvent e) throws IOException {
		try {
			if (areaID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(areaID.getText());
			DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
			if (da.getAreaName() != nameField.getText())
				da.setAreaName(nameField.getText());
			Restaurant.getInstance().getAreas().put(id, da);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Area name updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			areaID.clear();
		}	
	}		
	
	//update delivery people in area according to user's input
	public void addAndRemoveDPFromArea(ActionEvent e) throws IOException {
		int id = Integer.parseInt(areaID.getText());
		DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
		if (addDelPerButton.isSelected())
			da.addDelPerson(dpBox.getValue());
		if (removeDelPerButton.isSelected() && da.getDelPersons().contains(dpBox.getValue()))
			da.removeDelPerson(dpBox.getValue());
		String people = da.getDelPersons().toString();
		dpField.setText(people.substring(1, people.length()-1));
		Restaurant.getInstance().getAreas().put(id, da);
		Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Delivery people updated", ButtonType.OK);
		successfulUpdate.show();
	}
	
	//update deliveries in area according to user's input
	public void addAndRemoveDeliveryFromArea(ActionEvent e) throws IOException {
		int id = Integer.parseInt(areaID.getText());
		DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
		if (addDeliveryButton.isSelected())
			da.addDelivery(deliveriesBox.getValue());
		if (removeDeliveryButton.isSelected() && da.getDelivers().contains(deliveriesBox.getValue()))
			da.removeDelivery(deliveriesBox.getValue());
		String deliveries = da.getDelivers().toString();
		deliveriesField.setText(deliveries.substring(1, deliveries.length()-1));
		Restaurant.getInstance().getAreas().put(id, da);
		Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Deliveries updated", ButtonType.OK);
		successfulUpdate.show();
	}
	
	//update neighborhoods in area according to user's input
	public void addAndRemoveNeighborhoodFromArea(ActionEvent e) throws IOException {
		int id = Integer.parseInt(areaID.getText());
		DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(id);
		if (addNeighButton.isSelected())
			da.addNeighberhood(neighborhoodsBox.getValue());
		if (removeNeighButton.isSelected() && da.getNeighberhoods().contains(neighborhoodsBox.getValue()))
			da.removeNeighberhood(neighborhoodsBox.getValue());
		String neighbor = da.getNeighberhoods().toString();
		neighborhoodField.setText(neighbor.substring(1, neighbor.length()-1));
		Restaurant.getInstance().getAreas().put(id, da);
		Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Neighborhoods updated", ButtonType.OK);
		successfulUpdate.show();
	}
	
	//add new area to restaurant
	public void addArea(ActionEvent e) throws IOException {
		try {
			if (nameField.getText().isBlank() || timeField.getText().isBlank() ||
					hoodsForNewArea.isEmpty())
				throw new MissingFieldsException();
			else {	
				DeliveryArea newArea = new DeliveryArea(nameField.getText(), 
						hoodsForNewArea, Integer.valueOf(timeField.getText()));
				Restaurant.getInstance().addDeliveryArea(newArea);
				Alert alert = new Alert(AlertType.INFORMATION, "Added delivery area (id: " + newArea.getId() +
						")", ButtonType.OK);
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
	
	//add neighborhoods to new area
	public void chooseNeighborhoodsForNewArea(ActionEvent e) throws IOException {
		if (addButton.isSelected())
			hoodsForNewArea.add(neighborhoodsBox.getValue());
		if (removeButton.isSelected() && hoodsForNewArea.contains(neighborhoodsBox.getValue()))
			hoodsForNewArea.remove(neighborhoodsBox.getValue());
		String neighborhoods = hoodsForNewArea.toString();
		neighborhoodField.setText(neighborhoods.substring(1, neighborhoods.length()-1));
	}
	
	//remove existing area from restaurant
	public void removeArea(ActionEvent e) throws IOException {
		if (oldAreasBox.getValue() != null && newAreasBox != null) {
			Restaurant.getInstance().removeDeliveryArea(oldAreasBox.getValue(), newAreasBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed delivery area", ButtonType.OK);
			alert.show();
		}
	}
	
	
	
}
