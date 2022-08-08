package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Component;
import application.model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ComponentController implements Initializable {

	@FXML
	TextField componentID, nameField, priceField;
	@FXML
	Text nameText, idText, lactoseText, glutenText, priceText;
	@FXML
	CheckBox glutenBox, lactoseBox;
	@FXML
	ComboBox<Component> compBox;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (compBox != null)
			compBox.getItems().addAll(Restaurant.getInstance().getComponenets().values());
	}
	
	//view component details
	public void componentByID(ActionEvent e) throws IOException {
		if (!componentID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(componentID.getText());
				if (Restaurant.getInstance().getComponenets().keySet().contains(id)) {
					Component component = Restaurant.getInstance().getRealComponent(id);
					idText.setText(String.valueOf(id));
					nameText.setText(component.getComponentName());
					priceText.setText(Double.toString(component.getPrice()));  
					if (component.isHasGluten())
						glutenText.setText("Yes");
					else 
						glutenText.setText("No");
					if (component.isHasLactose())
						lactoseText.setText("Yes");
					else 
						lactoseText.setText("No");
					componentID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				componentID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				componentID.clear();
			}
		}
	}
	
	//view component details on update screen
	public void viewComponentBeforeUpdate(ActionEvent e) throws IOException {
		if (!componentID.getText().isEmpty()) {
			nameField.clear();
			priceField.clear();
			try {
				int id = Integer.parseInt(componentID.getText());
				if (Restaurant.getInstance().getComponenets().keySet().contains(id)) {
					Component component = Restaurant.getInstance().getRealComponent(id);
					nameField.appendText(component.getComponentName());
					priceField.appendText(String.valueOf(component.getPrice()));
					if (component.isHasGluten())
						glutenBox.setSelected(true);
					if (component.isHasLactose())
						lactoseBox.setSelected(true);
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				componentID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				componentID.clear();
			}
		}
	}
	
	//update component details according to user's input
	public void updateComponentInfo(ActionEvent e) throws IOException {
		try {
			if (componentID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(componentID.getText());
			Component component = Restaurant.getInstance().getRealComponent(id);
			if (component.getComponentName() != nameField.getText())
				component.setComponentName(nameField.getText());
			if (String.valueOf(component.getPrice()) != priceField.getText())
				component.setPrice(Double.valueOf(priceField.getText()));
			if (glutenBox.isSelected())
				component.setHasGluten(true);
			if (lactoseBox.isSelected())
				component.setHasLactose(true);
			Restaurant.getInstance().getComponenets().put(id, component);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Component details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			componentID.clear();
		}
		catch (NumberFormatException exception) {
			Alert alert = new Alert(AlertType.ERROR, "Enter digits only.", ButtonType.OK);
			alert.show();
		}
	}

	//add new component to restaurant
	public void addComponent(ActionEvent e) throws IOException {
		try {
			if (nameField.getText().isBlank() || priceField.getText().isBlank())
				throw new MissingFieldsException();
			else {	
				Component newComponent = new Component(nameField.getText(), false, false, 
						Double.valueOf(priceField.getText())); 
				if (glutenBox.isSelected()) 
					newComponent.setHasGluten(true);
				if (lactoseBox.isSelected())
					newComponent.setHasLactose(true);
				Restaurant.getInstance().addComponent(newComponent);
				Alert alert = new Alert(AlertType.INFORMATION, "Added component (id: " + newComponent.getId() +
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
	
	//remove existing component from restaurant
	public void removeComponent(ActionEvent e) throws IOException {
		if (compBox.getValue() != null) {
			Restaurant.getInstance().removeComponent(compBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed component", ButtonType.OK);
			alert.show();
		}
	}
	
	
}
