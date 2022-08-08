package application.view;

import static application.util.Utills.LoadFXML;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

import application.exceptions.IllegalCustomerException;
import application.exceptions.SensitiveException;
import application.model.Component;
import application.model.Customer;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.Dish;
import application.model.ExpressDelivery;
import application.model.Order;
import application.model.Restaurant;
import application.util.DishType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController implements Initializable {
	
	@FXML
	Button startersButton, mainsButton, dessertsButton, add1, add2, add3, remove1, remove2, remove3;
	@FXML
	RadioButton addButton, removeButton;
	@FXML
	Text dishName1, dishName2, dishName3, components1, components2, components3, price1, price2, price3;
	@FXML
	Pane menuPane, compsPane, historyPane;
	@FXML
	TextField count1, count2, count3;
	@FXML
	TextArea summaryArea, componentsArea, historyArea;
	@FXML
	ComboBox<Dish> dishToRemoveBox, dishToChangeBox;
	@FXML
	ComboBox<Order> ordersBox;
	@FXML
	ComboBox<Component> compsBox;
	

	private Stage stage;
	private Scene scene;
	private ArrayList<Dish> dishesToDisplay;
	private TreeSet<Order> historyOrders;
	private int starter1 = 0, starter2 = 0, starter3 = 0;
	private int main1 = 0, main2 = 0, main3 = 0; 
	private int dessert1 = 0, dessert2 = 0, dessert3 = 0;
	
	@Override
	//fill all ComboBoxes and data structures 
	public void initialize(URL arg0, ResourceBundle arg1) {
		dishesToDisplay = new ArrayList<>();
		historyOrders = new TreeSet<>();
		if (menuPane != null)
			menuPane.setVisible(false);
		if (compsPane != null)
			compsPane.setVisible(false);
		if (dishToRemoveBox != null)
			dishToRemoveBox.getItems().addAll(Restaurant.getInstance().getUserDishes());
		if (dishToChangeBox != null)
			dishToChangeBox.getItems().addAll(Restaurant.getInstance().getUserDishes());
		if (compsBox != null)
			compsBox.getItems().addAll(Restaurant.getInstance().getComponenets().values());
		if (historyPane !=null)
			historyPane.setVisible(false);
	}
	
	//user chooses type of dishes to display
	public void chooseType(ActionEvent e) throws IOException {
		dishesToDisplay.clear();
		startersButton.setUnderline(false);
		mainsButton.setUnderline(false);
		dessertsButton.setUnderline(false);
		//starters button is pressed
		 if (e.getSource() == startersButton) {
			startersButton.setUnderline(true);
			count1.setText(String.valueOf(starter1));
			count2.setText(String.valueOf(starter2));
			count3.setText(String.valueOf(starter3));
			for (Dish dish : Restaurant.getInstance().getDishes().values()) {
				//add only starters
				if (dish.getType().equals(DishType.Starter))
					dishesToDisplay.add(dish);
			} 
		 }
		//mains button is pressed
		 if (e.getSource() == mainsButton) {
			mainsButton.setUnderline(true);
			count1.setText(String.valueOf(main1));
			count2.setText(String.valueOf(main2));
			count3.setText(String.valueOf(main3));
			for (Dish dish : Restaurant.getInstance().getDishes().values()) {
				//add only mains
				if (dish.getType().equals(DishType.Main))
					dishesToDisplay.add(dish);
			} 
		 }
		//desserts button is pressed
		 if (e.getSource() == dessertsButton) {	
			dessertsButton.setUnderline(true);
			count1.setText(String.valueOf(dessert1));
			count2.setText(String.valueOf(dessert2));
			count3.setText(String.valueOf(dessert3));
			for (Dish dish : Restaurant.getInstance().getDishes().values()) {
				//add only desserts
				if (dish.getType().equals(DishType.Dessert))
					dishesToDisplay.add(dish);
			} 
		 }	 
		displayDishes();
	}
		
	//display on screen dishes according to user's choice	
	public void displayDishes() {
		menuPane.setVisible(true);
		DecimalFormat df2 = new DecimalFormat("#.##");
		//names
		dishName1.setText(dishesToDisplay.get(0).getDishName());
		dishName2.setText(dishesToDisplay.get(1).getDishName());
		dishName3.setText(dishesToDisplay.get(2).getDishName());
		//components
		String comps1 = dishesToDisplay.get(0).getComponenets().toString();
		components1.setText(comps1.substring(1, comps1.length()-1));
		String comps2 = dishesToDisplay.get(1).getComponenets().toString();
		components2.setText(comps2.substring(1, comps2.length()-1));
		String comps3 = dishesToDisplay.get(2).getComponenets().toString();
		components3.setText(comps3.substring(1, comps3.length()-1));
		//prices
		price1.setText(String.valueOf(df2.format(dishesToDisplay.get(0).getPrice())));
		price2.setText(String.valueOf(df2.format(dishesToDisplay.get(1).getPrice())));
		price3.setText(String.valueOf(df2.format(dishesToDisplay.get(2).getPrice())));
	}
	
	//save user's choice of dishes for new order, and display amount of each dish
	public void addAndRemoveDishToOrder(ActionEvent e) throws IOException {
		//add and remove first dish 
		if (e.getSource() == add1) {
			Restaurant.getInstance().getUserDishes().add(dishesToDisplay.get(0)); 
			if (startersButton.isUnderline())
				count1.setText(String.valueOf(++starter1));
			if (mainsButton.isUnderline())
				count1.setText(String.valueOf(++main1));
			if (dessertsButton.isUnderline())
				count1.setText(String.valueOf(++dessert1));
		}
		if (e.getSource() == remove1 && Restaurant.getInstance().getUserDishes().contains(dishesToDisplay.get(0))) { 
			Restaurant.getInstance().getUserDishes().remove(dishesToDisplay.get(0)); 
			if (startersButton.isUnderline())
				count1.setText(String.valueOf(--starter1));
			if (mainsButton.isUnderline())
				count1.setText(String.valueOf(--main1));
			if (dessertsButton.isUnderline())
				count1.setText(String.valueOf(--dessert1));
		}
		//add and remove second dish 
		if (e.getSource() == add2) {
			Restaurant.getInstance().getUserDishes().add(dishesToDisplay.get(1)); 
			if (startersButton.isUnderline())
				count2.setText(String.valueOf(++starter2));
			if (mainsButton.isUnderline())
				count2.setText(String.valueOf(++main2));
			if (dessertsButton.isUnderline())
				count2.setText(String.valueOf(++dessert2));
		}
		if (e.getSource() == remove2 && Restaurant.getInstance().getUserDishes().contains(dishesToDisplay.get(1))) {
			Restaurant.getInstance().getUserDishes().remove(dishesToDisplay.get(1)); 
			if (startersButton.isUnderline())
				count2.setText(String.valueOf(--starter2));
			if (mainsButton.isUnderline())
				count2.setText(String.valueOf(--main2));
			if (dessertsButton.isUnderline())
				count2.setText(String.valueOf(--dessert2));
		}
		//add and remove third dish 
		if (e.getSource() == add3) {
			Restaurant.getInstance().getUserDishes().add(dishesToDisplay.get(2)); 
			if (startersButton.isUnderline())
				count3.setText(String.valueOf(++starter3));
			if (mainsButton.isUnderline())
				count3.setText(String.valueOf(++main3));
			if (dessertsButton.isUnderline())
				count3.setText(String.valueOf(++dessert3));
		}
		if (e.getSource() == remove3 && Restaurant.getInstance().getUserDishes().contains(dishesToDisplay.get(2))) {
			Restaurant.getInstance().getUserDishes().remove(dishesToDisplay.get(2)); 
			if (startersButton.isUnderline())
				count3.setText(String.valueOf(--starter3));
			if (mainsButton.isUnderline())
				count3.setText(String.valueOf(--main3));
			if (dessertsButton.isUnderline())
				count3.setText(String.valueOf(--dessert3));
		}
	}
	
	//go to cart after choosing dishes from menu
	public void goToCart(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../view/UserOptions.fxml"));
		BorderPane bp = (BorderPane)root.getChildrenUnmodifiable().get(0);
		bp.setCenter(LoadFXML(getClass(), "../view/ShoppingCart.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//stop current order and start a new one 
	public void newOrderGoBackToMenu(ActionEvent e) throws IOException {
		//delete all previously chosen dishes
		Restaurant.getInstance().getUserDishes().clear();
		//go back to menu
		Parent root = FXMLLoader.load(getClass().getResource("../view/UserOptions.fxml"));
		BorderPane bp = (BorderPane)root.getChildrenUnmodifiable().get(0);
		bp.setCenter(LoadFXML(getClass(), "../view/RestaurantMenu.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//print dish details on screen 
	public void printSummery() {
		summaryArea.clear();
		DecimalFormat df2 = new DecimalFormat("#.##");
		double totalPrice = 0;
		for (Dish dish : Restaurant.getInstance().getUserDishes()) {
			String comps = dish.getComponenets().toString();
			summaryArea.appendText("*" + dish.getDishName() + "* \n" 
			+ comps.substring(1, comps.length()-1) + "\nPrice: " 
					+ df2.format(dish.getPrice()) + "\n\n");
			totalPrice += dish.getPrice();
		}
		//total order price
		summaryArea.appendText("\nTotal price: " + df2.format(totalPrice) + " NIS\n");
	}
	
	//view all dishes added to new order
	public void initialSummary(ActionEvent e) throws IOException {
		printSummery();
		compsPane.setVisible(true);
	}
	
	//user can remove dishes from current order
	public void removeChosenDish(ActionEvent e) throws IOException {
		if (dishToRemoveBox.getValue() != null) {
			Alert alert = new Alert(AlertType.INFORMATION, "Removed " 
					+ dishToRemoveBox.getValue().getDishName(), ButtonType.OK);
			alert.show();
			Restaurant.getInstance().getUserDishes().remove(dishToRemoveBox.getValue());
			printSummery();
			//update dishes ComboBoxes
			dishToChangeBox.getItems().clear();
			dishToChangeBox.getItems().addAll(Restaurant.getInstance().getUserDishes());
			dishToRemoveBox.getItems().clear();
			dishToRemoveBox.getItems().addAll(Restaurant.getInstance().getUserDishes());
		}
	}
	
	//choose dish to change, and display components
	public void chooseDish(ActionEvent e) throws IOException {
		if (dishToChangeBox.getValue() != null) {
			String oldComps =  dishToChangeBox.getValue().getComponenets().toString();
			componentsArea.setText(oldComps.substring(1, oldComps.length()-1));
		}
	}
	
	//user can add and remove components from dish while ordering
	public void changeComponents(ActionEvent e) throws IOException {
		if (compsBox.getValue() != null && dishToChangeBox != null) {
			Dish newDish = dishToChangeBox.getValue();
			if (newDish != null) {
				//add component
				if (addButton.isSelected()) 
					newDish.addComponent(compsBox.getValue());
				//remove existing component
				if (removeButton.isSelected() && newDish.getComponenets().contains(compsBox.getValue())) 
					newDish.removeComponent(compsBox.getValue());
				//print on screen list of components for user to see
				String updatedComps = newDish.getComponenets().toString();
				componentsArea.setText(updatedComps.substring(1, updatedComps.length()-1));
				//remove old dish object, and create a new dish object containing updated components 
				ArrayList<Component> newComponents = new ArrayList<>();
				newComponents.addAll(newDish.getComponenets());
				Restaurant.getInstance().getUserDishes().remove(newDish);
				Restaurant.getInstance().getUserDishes().add(new Dish(newDish.getDishName(), newDish.getType(),
						newComponents, newDish.getTimeToMake()));
				//update ComboBox of dishes
				dishToChangeBox.getItems().clear();
				dishToChangeBox.getItems().addAll(Restaurant.getInstance().getUserDishes());
				compsBox.setValue(null);
				componentsArea.clear();
				printSummery();
			}
		}
	}
	
	//create new order with chosen dishes
	public void sendOrder(ActionEvent e) throws IOException {
		//current customer
		Customer customer = Restaurant.getInstance().getRealCustomer
				(Restaurant.getInstance().getCurrentUserId());
		try {
			//if customer is on the black list
			if (Restaurant.getInstance().getBlackList().contains(customer))
				throw new IllegalCustomerException();
			//if customer is allergic to one of the dishes they chose
			for(Dish d : Restaurant.getInstance().getUserDishes()){
				for(Component c: d.getComponenets()) {
					if(customer.isSensitiveToGluten() && c.isHasGluten()) {
						throw new SensitiveException(customer.getFirstName() + " " +customer.getLastName(), d.getDishName());
					}
					else if(customer.isSensitiveToLactose() && c.isHasLactose()) {
						throw new SensitiveException(customer.getFirstName() + " " + customer.getLastName(), d.getDishName());
					}
				}
			}
			//find delivery area that match customer's location
			DeliveryArea deliveryArea = null;
			for (DeliveryArea da : Restaurant.getInstance().getAreas().values()) {
				if (da.getNeighberhoods().contains(customer.getNeighberhood())) {
					deliveryArea = da;
					break;
				}
			}
			//find delivery that match area
			Delivery delivery = null;
			if (deliveryArea != null) {
				for (Delivery d : Restaurant.getInstance().getDeliveries().values()) {
					if (d.getArea().equals(deliveryArea)) {
						delivery = d;
						break;
					}
				}
				//if matching delivery was not found, create a new order and a matching new delivery
				if (delivery == null) {
					DeliveryPerson deliveryPerson = null;
					for (DeliveryPerson dp : Restaurant.getInstance().getDeliveryPersons().values()) {
						if (dp.getArea().equals(deliveryArea)) {
							deliveryPerson = dp;
							break;
						}
					}	
					Order unCompleteOrder = new Order(customer, Restaurant.getInstance().getUserDishes(), null);
					delivery = new ExpressDelivery(deliveryPerson, deliveryArea, false, unCompleteOrder,
							LocalDate.now());
					((ExpressDelivery) delivery).getOrder().setDelivery(delivery);
					Restaurant.getInstance().addDelivery(delivery);
					Restaurant.getInstance().addOrder(((ExpressDelivery) delivery).getOrder());
					//order confirmation alert
					Alert alert = new Alert(AlertType.INFORMATION, "Order no. " + unCompleteOrder.getId() + " recieved at"
							+ " restaurant.\nEstimated delivery time: " + deliveryArea.getDeliverTime() 
							+ " minutes\nEnjoy!", ButtonType.OK);
					alert.show();
				}
				//if matching delivery was found, create new order and add to restaurnt's orders
				else {
					Order completeOrder = new Order(customer, Restaurant.getInstance().getUserDishes(), delivery);
					Restaurant.getInstance().addOrder(completeOrder);
					//order confirmation alert
					Alert alert = new Alert(AlertType.INFORMATION, "Order no. " + completeOrder.getId() + " recieved at"
							+ " restaurant.\nEstimated delivery time: " + deliveryArea.getDeliverTime() 
							+ " minutes\nEnjoy!", ButtonType.OK);
					alert.show();
				}
			}
			//clear order
			Restaurant.getInstance().getUserDishes().clear();
			//close shopping cart screen
			Parent root = FXMLLoader.load(getClass().getResource("../view/UserOptions.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show(); 
		}
		catch (IllegalCustomerException exception) {
			Alert alert = new Alert(AlertType.ERROR, "Your name is on the black list.\nCannot send order, sorry.",
					ButtonType.OK);
			alert.show();
			newOrderGoBackToMenu(e);
		} 
		catch (SensitiveException exception) {
			Alert alert = new Alert(AlertType.ERROR, "You are sensitive to one of the components in the order. "
					+ "\nPlease start over.", ButtonType.OK);
			alert.show();
			newOrderGoBackToMenu(e);
		}
	}
	
	//save all of current customer's orders
	public void saveAllOrdersEverMadeByUser() {
		Customer currentCustomer = Restaurant.getInstance().getRealCustomer(Restaurant.getInstance().
				getCurrentUserId());
		for (Order order : Restaurant.getInstance().getOrders().values()) {
			if (order.getCustomer().equals(currentCustomer))
				historyOrders.add(order);
		}
	}
	
	//display all orders ever made by customer
	public void viewPastOrders(ActionEvent e) throws IOException {
		historyArea.clear();
		historyPane.setVisible(true);
		saveAllOrdersEverMadeByUser();
		//display each order's details on screen
		if (!historyOrders.isEmpty()) {
			for (Order o : historyOrders) {
				historyArea.appendText("* Order (id: " + o.getId() + ") *" 
						+ "\nDate: " + o.getDelivery().getDeliveredDate() + "\nDishes: ");
				for (Dish d : o.getDishes()) {
					historyArea.appendText("\n-" + d.getDishName() + "\nComponents: ");
					for (Component c : d.getComponenets())
						historyArea.appendText(c.getComponentName() + ", ");	
				}
				historyArea.appendText("\n\n");
			}
			ordersBox.getItems().addAll(historyOrders);
		}
		else
			historyArea.appendText("No previous orders.");
	}
	
	//remove chosen order from history
	public void removeOrderFromHistory(ActionEvent e) throws IOException {
		if (ordersBox.getValue() != null) {
			historyOrders.remove(ordersBox.getValue());
			Restaurant.getInstance().removeOrder(ordersBox.getValue());
			historyArea.clear();
			if (!historyOrders.isEmpty()) {
				for (Order o : historyOrders) {
					historyArea.appendText("* Order (id: " + o.getId() + ") *" 
							+ "\nDate: " + o.getDelivery().getDeliveredDate() + "\nDishes: ");
					for (Dish d : o.getDishes()) {
						historyArea.appendText("\n-" + d.getDishName() + "\nComponents: ");
						for (Component c : d.getComponenets())
							historyArea.appendText(c.getComponentName() + ", ");	
					}
					historyArea.appendText("\n\n");
				}
				ordersBox.getItems().clear();
				ordersBox.getItems().addAll(historyOrders);
			}
			else
				historyArea.appendText("No previous orders.");
		}
	}
	
}
	
	
	

