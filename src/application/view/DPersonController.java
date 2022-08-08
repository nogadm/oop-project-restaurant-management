package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.Restaurant;
import application.util.Gender;
import application.util.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class DPersonController implements Initializable {

	@FXML
	TextField dpID, firstName, lastName, areaField;
	@FXML
	Text nameText, idText, birthDateText, genderText, vehicleText, areaText;
	@FXML
	ComboBox<Vehicle> vehicleBox;
	@FXML
	ComboBox<Gender> genderBox;
	@FXML
	ComboBox<DeliveryArea> areaBox;
	@FXML
	ComboBox<DeliveryPerson> dpBox;
	@FXML
	DatePicker birthDate;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (vehicleBox != null)
			vehicleBox.getItems().addAll(Vehicle.values());
		if (areaBox != null)
			areaBox.getItems().addAll(Restaurant.getInstance().getAreas().values());
		if (genderBox != null)
			genderBox.getItems().addAll(Gender.values());
		if (dpBox != null)
			dpBox.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
	}
	
	//view delivery person details
	public void dpByID(ActionEvent e) throws IOException {
		if (!dpID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(dpID.getText());
				if (Restaurant.getInstance().getDeliveryPersons().keySet().contains(id)) {
					DeliveryPerson dp = Restaurant.getInstance().getRealDeliveryPerson(id);
					idText.setText(String.valueOf(id));
					nameText.setText(dp.getFirstName() + " " + dp.getLastName());
					birthDateText.setText(dp.getBirthDay().toString());
					genderText.setText(dp.getGender().toString());
					vehicleText.setText(dp.getVehicle().toString());
					areaText.setText(dp.getArea().getAreaName());
					dpID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				dpID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				dpID.clear();
			}
		}
	}
	
	//view delivery person details on update screen
	public void viewDeliveryPersonBeforeUpdate(ActionEvent e) throws IOException {
		if (!dpID.getText().isEmpty()) {
			firstName.clear();
			lastName.clear();
			try {
				int id = Integer.parseInt(dpID.getText());
				if (Restaurant.getInstance().getDeliveryPersons().keySet().contains(id)) {
					DeliveryPerson dp = Restaurant.getInstance().getRealDeliveryPerson(id);
					firstName.appendText(dp.getFirstName());
					lastName.appendText(dp.getLastName());
					vehicleBox.setPromptText(dp.getVehicle().toString());
					areaBox.setPromptText(dp.getArea().toString());
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				dpID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				dpID.clear();
			}
		}
	}
	
	//update delivery person details according to user's input
	public void updateDeliveryPersonInfo(ActionEvent e) throws IOException {
		try {
			if (dpID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(dpID.getText());
			DeliveryPerson dp = Restaurant.getInstance().getRealDeliveryPerson(id);
			if (dp.getFirstName() != firstName.getText())
				dp.setFirstName(firstName.getText());
			if (dp.getLastName() != lastName.getText())
				dp.setLastName(lastName.getText());
			if (!(vehicleBox.getValue() == null)) {
				if (!dp.getVehicle().equals(vehicleBox.getValue()))
						dp.setVehicle(vehicleBox.getValue());
			}
			if (!(areaBox.getValue() == null)) {
				if (!dp.getArea().equals(areaBox.getValue()))
						dp.setArea(areaBox.getValue());
			}
			Restaurant.getInstance().getDeliveryPersons().put(id, dp);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Delivery person details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			dpID.clear();
		}	
	}		
	
	//add new delivery person to restaurant
	public void addDeliveryPerson(ActionEvent e) throws IOException {
		try {
			if (firstName.getText().isBlank() || lastName.getText().isBlank() || birthDate.getValue() == null ||
					genderBox.getValue() == null || vehicleBox.getValue() == null || areaBox.getValue() == null)
				throw new MissingFieldsException();
			else {	
				DeliveryPerson newDeliveryPerson = new DeliveryPerson(firstName.getText(), lastName.getText(), birthDate.getValue(),
						genderBox.getValue(), vehicleBox.getValue(), areaBox.getValue());
				Restaurant.getInstance().addDeliveryPerson(newDeliveryPerson, areaBox.getValue());
				Alert alert = new Alert(AlertType.INFORMATION, "Added delivery person (id: " +
				newDeliveryPerson.getId() + ")", ButtonType.OK);
				alert.show();
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
	}
	
	//remove existing delivery person from restaurant
	public void removeDeliveryPerson(ActionEvent e) throws IOException {
		if (dpBox.getValue() != null) {
			Restaurant.getInstance().removeDeliveryPerson(dpBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed delivery person", ButtonType.OK);
			alert.show();
		}
	}
	
}
