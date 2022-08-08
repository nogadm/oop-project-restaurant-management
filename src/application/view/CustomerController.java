package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.exceptions.IncorrectPasswordException;
import application.exceptions.MissingFieldsException;
import application.exceptions.PasswordsDoNotMatchException;
import application.exceptions.WeakPasswordException;
import application.exceptions.idDoesntExistException;
import application.model.Customer;
import application.model.Restaurant;
import application.util.Gender;
import application.util.Neighberhood;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CustomerController implements Initializable {

	@FXML
	TextField customerID, firstName, lastName;//, oldPasswordField, newPasswordField, newPasswordAgainField;
	@FXML
	Text nameText, idText, birthDateText, genderText, neighborhoodText, sensitivitiesText;
	@FXML
	CheckBox glutenBox, lactoseBox;
	@FXML
	ComboBox<Neighberhood> neighborhoodsBox;
	@FXML
	ComboBox<Gender> genderBox;
	@FXML
	ComboBox<Customer> customersBox;
	@FXML
	DatePicker birthDate;
	@FXML
	PasswordField oldPasswordField, newPasswordField, newPasswordAgainField;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (neighborhoodsBox != null)
			neighborhoodsBox.getItems().addAll(Neighberhood.values());
		if (customerID != null) {
			int id = Restaurant.getInstance().getCurrentUserId();
			if (id != 0) {
				customerID.setEditable(false);
				customerID.setText(String.valueOf(id));
			}
		}
		if (genderBox != null)
			genderBox.getItems().addAll(Gender.values());
		if (customersBox != null)
			customersBox.getItems().addAll(Restaurant.getInstance().getCustomers().values());
	}
	
	//view customer details
	public void customerByID(ActionEvent e) throws IOException {
		if (!customerID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(customerID.getText());
				if (Restaurant.getInstance().getCustomers().keySet().contains(id)) {
					Customer customer = Restaurant.getInstance().getRealCustomer(id);
					idText.setText(String.valueOf(id));
					nameText.setText(customer.getFirstName() + " " + customer.getLastName());
					birthDateText.setText(customer.getBirthDay().toString());
					genderText.setText(customer.getGender().toString());
					neighborhoodText.setText(customer.getNeighberhood().toString().replace('_', ' '));
					if (customer.isSensitiveToGluten()) {
						if (customer.isSensitiveToLactose())
							sensitivitiesText.setText("gluten, lactose");
						else
							sensitivitiesText.setText("gluten");
					}
					else {
						if (customer.isSensitiveToLactose())
							sensitivitiesText.setText("lactose");
					}
					if (!customer.isSensitiveToGluten() && !customer.isSensitiveToLactose())
						sensitivitiesText.setText("none");	
					customerID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				customerID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				customerID.clear();
			}
		}
	}

	//view customer details on update screen
	public void viewCustomerBeforeUpdate(ActionEvent e) throws IOException {
		if (!customerID.getText().isEmpty()) {
			firstName.clear();
			lastName.clear();
			glutenBox.setSelected(false);
			lactoseBox.setSelected(false);
			try {
				int id;
				if (Restaurant.getInstance().getCurrentUserId() == 0)
					id = Integer.parseInt(customerID.getText());
				else 
					id = Restaurant.getInstance().getCurrentUserId();
				if (Restaurant.getInstance().getCustomers().keySet().contains(id)) {
					Customer customer = Restaurant.getInstance().getRealCustomer(id);
					firstName.appendText(customer.getFirstName());
					lastName.appendText(customer.getLastName());
					if (customer.isSensitiveToGluten())
						glutenBox.setSelected(true);
					if (customer.isSensitiveToLactose())
						lactoseBox.setSelected(true);
					neighborhoodsBox.setPromptText(customer.getNeighberhood().toString());
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				customerID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				customerID.clear();
			}
		}
	}
	
	//update customer details according to user's input
	public void updateCustomerInfo(ActionEvent e) throws IOException {
		try {
			if (customerID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(customerID.getText());
			Customer customer = Restaurant.getInstance().getRealCustomer(id);
			if (customer.getFirstName() != firstName.getText())
				customer.setFirstName(firstName.getText());
			if (customer.getLastName() != lastName.getText())
				customer.setLastName(lastName.getText());
			if (!(neighborhoodsBox.getValue() == null)) {
				if (!(customer.getNeighberhood().equals(neighborhoodsBox.getValue())))
					customer.setNeighberhood(neighborhoodsBox.getValue());
			}
			if (glutenBox.isSelected())
				customer.setSensitiveToGluten(true);
			if (lactoseBox.isSelected())
				customer.setSensitiveToLactose(true);
			Restaurant.getInstance().getCustomers().put(id, customer);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Customer details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			customerID.clear();
		}	
	}		
	
	//add new customer to restaurant
	public void addCustomer(ActionEvent e) throws IOException {
		try {
			if (firstName.getText().isBlank() || lastName.getText().isBlank() || birthDate.getValue() == null ||
					genderBox.getValue() == null || neighborhoodsBox.getValue() == null)
				throw new MissingFieldsException();
			else {	
				Customer newCustomer = new Customer(firstName.getText(), lastName.getText(), birthDate.getValue(),
						genderBox.getValue(), neighborhoodsBox.getValue(), false, false, null);
				if (glutenBox.isSelected())
					newCustomer.setSensitiveToGluten(true);
				if (lactoseBox.isSelected())
					newCustomer.setSensitiveToLactose(true);
				//temporary password -> customer's id
				newCustomer.setPassword(String.valueOf(newCustomer.getId()));
				Restaurant.getInstance().addCustomer(newCustomer);
				Alert alert = new Alert(AlertType.INFORMATION, "Added customer (id: " + newCustomer.getId() 
					+ ").\nTemporary password was assigned.", ButtonType.OK);
				alert.show();
			}
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
	}
	
	//remove existing customer from restaurant
	public void removeCustomer(ActionEvent e) throws IOException {
		if (customersBox.getValue() != null) {
			Restaurant.getInstance().removeCustomer(customersBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed customer", ButtonType.OK);
			alert.show();
		}
	}
	
	//customers can change their own password 
	public void changePassword(ActionEvent e) throws IOException {
		try {
			if (oldPasswordField.getText().isBlank() || newPasswordField.getText().isBlank() ||
					newPasswordAgainField.getText().isBlank())
				throw new MissingFieldsException();
			else {
				Customer currentCust = Restaurant.getInstance().
						getRealCustomer(Restaurant.getInstance().getCurrentUserId());
				//user must enter old password
				if (!currentCust.getPassword().equals(oldPasswordField.getText()))
					throw new IncorrectPasswordException();
				else {
					//new password must be 8 or more characters
					if (newPasswordField.getText().length()<8)
						throw new WeakPasswordException();
					else {
						if (newPasswordField.getText().equals(newPasswordAgainField.getText()))
							Restaurant.getInstance().getRealCustomer(Restaurant.getInstance().
									getCurrentUserId()).setPassword(newPasswordField.getText());
						else 
							//user must verify new password twice
							throw new PasswordsDoNotMatchException();
					}
				}
			}
			Alert alert = new Alert(AlertType.INFORMATION, "Password changed successfully", ButtonType.OK);
			alert.show();
		}
		catch (MissingFieldsException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
		}	
		catch (IncorrectPasswordException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			oldPasswordField.clear();
			newPasswordField.clear();
			newPasswordAgainField.clear();
		}
		catch (WeakPasswordException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			newPasswordField.clear();
			newPasswordAgainField.clear();
		}
		catch (PasswordsDoNotMatchException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			newPasswordAgainField.clear();
		}
	}
	
	
}
