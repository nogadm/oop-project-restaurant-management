package application.view;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.exceptions.MissingFieldsException;
import application.exceptions.idDoesntExistException;
import application.model.Component;
import application.model.Dish;
import application.model.Restaurant;
import application.util.DishType;
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

public class DishController implements Initializable {

	@FXML
	TextField dishID;
	@FXML
	Text nameText, idText, typeText, componentsText, priceText, timeText; 
	@FXML
	TextField nameField, priceField, timeField, compField;
	@FXML
	ComboBox<DishType> typeBox;
	@FXML
	ComboBox<Component> compBox;
	@FXML
	ComboBox<Dish> dishesBox;
	@FXML
	RadioButton addButton, removeButton;
	
	private ArrayList<Component> compsForNewDish;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (typeBox != null)
			typeBox.getItems().addAll(DishType.values());
		if (compBox != null)
			compBox.getItems().addAll(Restaurant.getInstance().getComponenets().values());
		if (dishesBox != null)
			dishesBox.getItems().addAll(Restaurant.getInstance().getDishes().values());
		compsForNewDish = new ArrayList<>();
	}
	
	//view dish details
	public void dishByID(ActionEvent e) throws IOException {
		if (!dishID.getText().isEmpty()) {
			try {
				int id = Integer.parseInt(dishID.getText());
				if (Restaurant.getInstance().getDishes().keySet().contains(id)) {
					Dish dish = Restaurant.getInstance().getRealDish(id);
					idText.setText(String.valueOf(id));
					nameText.setText(dish.getDishName());
					typeText.setText(dish.getType().toString());
					String components = dish.getComponenets().toString();
					componentsText.setText(components.substring(1, components.length()-1));
					priceText.setText(Double.toString(dish.getPrice()));
					timeText.setText(Integer.toString(dish.getTimeToMake()));
					dishID.clear();
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				dishID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				dishID.clear();
			}
		}
	}
	
	//view dish details on update screen
	public void viewDishBeforeUpdate(ActionEvent e) throws IOException {
		if (!dishID.getText().isEmpty()) {
			nameField.clear();
			priceField.clear();
			timeField.clear();
			try {
				int id = Integer.valueOf(dishID.getText());
				if (Restaurant.getInstance().getDishes().keySet().contains(id)) {
					Dish dish = Restaurant.getInstance().getRealDish(id);
					nameField.appendText(dish.getDishName());
					priceField.appendText(String.valueOf(dish.getPrice()));
					timeField.appendText(String.valueOf(dish.getTimeToMake()));
					typeBox.setPromptText(dish.getType().toString());
					String components = dish.getComponenets().toString();
					compField.setText(components.substring(1, components.length()-1));
				}
				else 
					throw new idDoesntExistException();
			}
			catch (idDoesntExistException exception) {
				Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
				alert.show();
				dishID.clear();
			}
			catch (RuntimeException exception) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input. Enter digits only.", ButtonType.OK);
				alert.show();
				dishID.clear();
			}
		}
	}

	//update dish details according to user's input
	public void updateDishInfo(ActionEvent e) throws IOException {
		try {
			if (dishID.getText().isEmpty())
				throw new idDoesntExistException();
			int id = Integer.parseInt(dishID.getText());
			Dish dish = Restaurant.getInstance().getRealDish(id);
			if (dish.getDishName() != nameField.getText())
				dish.setDishName(nameField.getText());
			if (dish.getPrice() != Double.valueOf(priceField.getText()))
				dish.setPrice(Double.valueOf(priceField.getText()));
			if (dish.getTimeToMake() != Double.valueOf(timeField.getText()))
				dish.setTimeToMake(Integer.valueOf(timeField.getText()));
			if (typeBox.getValue() != null) {
				if (!dish.getType().equals(typeBox.getValue()))
					dish.setType(typeBox.getValue());
			}
			Restaurant.getInstance().getDishes().put(id, dish);
			Alert successfulUpdate = new Alert(AlertType.INFORMATION, "Dish details updated", ButtonType.OK);
			successfulUpdate.show();
		}
		catch (idDoesntExistException exception) {
			Alert alert = new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK);
			alert.show();
			dishID.clear();
		}	
		catch (NumberFormatException exception) {
			Alert alert = new Alert(AlertType.ERROR, "Enter digits only.", ButtonType.OK);
			alert.show();
		}
	}		
	
	//update components in dish according to user's input
	public void addAndRemoveCompFromDish(ActionEvent e) throws IOException {
		int id = Integer.parseInt(dishID.getText());
		Dish dish = Restaurant.getInstance().getRealDish(id);
		if (addButton.isSelected())
			dish.addComponent(compBox.getValue());
		if (removeButton.isSelected() && dish.getComponenets().contains(compBox.getValue()))
			dish.removeComponent(compBox.getValue());
		String components = dish.getComponenets().toString();
		compField.setText(components.substring(1, components.length()-1));
		Restaurant.getInstance().getDishes().put(id, dish);
	}
	
	//add new dish to restaurant
	public void addDish(ActionEvent e) throws IOException {
		try {
			if (nameField.getText().isBlank() || timeField.getText().isBlank() ||
					typeBox.getValue() == null || compsForNewDish.isEmpty())
				throw new MissingFieldsException();
			else {	
				Dish newDish = new Dish(nameField.getText(), typeBox.getValue(), compsForNewDish, 
						Integer.valueOf(timeField.getText()));
				Restaurant.getInstance().addDish(newDish);
				Alert alert = new Alert(AlertType.INFORMATION, "Added dish (id: " + newDish.getId() +
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
	
	//add components to new area
	public void chooseCompsForNewDish(ActionEvent e) throws IOException {
		if (addButton.isSelected())
			compsForNewDish.add(compBox.getValue());
		if (removeButton.isSelected() && compsForNewDish.contains(compBox.getValue()))
			compsForNewDish.remove(compBox.getValue());
		String components = compsForNewDish.toString();
		compField.setText(components.substring(1, components.length()-1));
	}
	
	//remove existing dish from restaurant
	public void removeDish(ActionEvent e) throws IOException {
		if (dishesBox.getValue() != null) {
			Restaurant.getInstance().removeDish(dishesBox.getValue());
			Alert alert = new Alert(AlertType.INFORMATION, "Removed dish", ButtonType.OK);
			alert.show();
		}
	}
	
	
	
	
	
}
