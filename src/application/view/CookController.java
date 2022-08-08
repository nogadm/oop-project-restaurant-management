package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Cook;
import application.model.Restaurant;
import application.util.Expertise;
import application.util.Gender;
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

public class CookController implements Initializable {

	@FXML
	TextField cookID, firstName, lastName;
	@FXML
	Text nameText, idText, birthDateText, genderText, expertiseText, isChefText ;
	@FXML
	RadioButton yesButton, noButton;
	@FXML
	ComboBox<Expertise> expertiseBox;
	@FXML
	ComboBox<Gender> genderBox;
	@FXML
	ComboBox<Cook> cooksBox;
	@FXML
	DatePicker birthDate;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (expertiseBox != null)
			expertiseBox.getItems().addAll(Expertise.values());
		if (genderBox != null)
			genderBox.getItems().addAll(Gender.values());
		if (cooksBox != null)
			cooksBox.getItems().addAll(Restaurant.getInstance().getCooks().values());
	}
	
	//view cook details
	public void cookByID(ActionEvent e) throws IOException {
		if (!cookID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(cookID.getText());
				if (Restaurant.getInstance().getCooks().keySet().contains(id)) {
					Cook cook = Restaurant.getInstance().getRealCook(id);
					idText.setText(String.valueOf(id));
					nameText.setText(cook.getFirstName() + " " + cook.getLastName());
					birthDateText.setText(cook.getBirthDay().toString());
					genderText.setText(cook.getGender().toString());
					expertiseText.setText(cook.getExpert().toString());
					if (cook.isChef())
						isChefText.setText("Yes");
					else 
					 	isChefText.setText("No");
					cookID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				cookID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				cookID.clear();
			}
		}
	}
	
	//view cook details on update screen
	public void viewCookBeforeUpdate(ActionEvent e) throws IOException {
		if (!cookID.getText().isEmpty()) {
			firstName.clear();
			lastName.clear();
			yesButton.setSelected(false);
			noButton.setSelected(false);
			try {
				int id = Integer.parseInt(cookID.getText());
				if (Restaurant.getInstance().getCooks().keySet().contains(id)) {
					Cook cook = Restaurant.getInstance().getRealCook(id);
					firstName.appendText(cook.getFirstName());
					lastName.appendText(cook.getLastName());
					if (cook.isChef())
						yesButton.setSelected(true);
					else
						noButton.setSelected(true);
					expertiseBox.setPromptText(cook.getExpert().toString());
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				cookID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				cookID.clear();
			}
		}
	}
	
	//update cook details according to user's input
	public void updateCookInfo(ActionEvent e) throws IOException {
		try {
			if (cookID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(cookID.getText());
			Cook cook = Restaurant.getInstance().getRealCook(id);
			if (cook.getFirstName() != firstName.getText())
				cook.setFirstName(firstName.getText());
			if (cook.getLastName() != lastName.getText())
				cook.setLastName(lastName.getText());
			if (!(expertiseBox.getValue() == null)) {
				if (!cook.getExpert().equals(expertiseBox.getValue()))
					cook.setExpert(expertiseBox.getValue());
			}
			if (yesButton.isSelected())
				cook.setChef(true);
			else
				cook.setChef(false);
			Restaurant.getInstance().getCooks().put(id, cook);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Cook details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			cookID.clear();
		}	
	}		
	
	//add new cook to restaurant
	public void addCook(ActionEvent e) throws IOException {
		try {
			if (firstName.getText().isBlank() || lastName.getText().isBlank() || birthDate.getValue() == null ||
					genderBox.getValue() == null || expertiseBox.getValue() == null)
				throw new MissingFieldsException();
			else {	
				Cook newCook = new Cook(firstName.getText(), lastName.getText(), birthDate.getValue(),
						genderBox.getValue(), expertiseBox.getValue(), false);
				if (yesButton.isPressed())
					newCook.setChef(true);
				Restaurant.getInstance().addCook(newCook);
				Alert alert = new Alert(AlertType.INFORMATION, "Added cook (id: " + newCook.getId() + ")",
						ButtonType.OK);
				alert.show();
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
	}
	
	//remove existing cook from restaurant
	public void removeCook(ActionEvent e) throws IOException {
		if (cooksBox.getValue() != null) {
			Restaurant.getInstance().removeCook(cooksBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed cook", ButtonType.OK);
			alert.show();
		}
	}
}