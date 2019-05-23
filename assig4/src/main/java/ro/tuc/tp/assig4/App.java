package ro.tuc.tp.assig4;

import businessL.BaseProduct;
import businessL.Restaurant;
import dataL.RestaurantSerializator;
import presentationL.AdministratorGraphicalUserInterface;
import presentationL.ChefGraphicalUserInterface;
import presentationL.WaiterGraphicalUserInterface;

public class App {
	public static Restaurant restaurant = new Restaurant();
	
	public static Restaurant getRestaurant() {
		return restaurant;
	}
	
	public static void setRestaurant(Restaurant restaurant) {
		App.restaurant = restaurant;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//App.getRestaurant().addMenuItem(new BaseProduct(5, "Pepperoni"));
		setRestaurant(RestaurantSerializator.readFromFile());
		ChefGraphicalUserInterface chefGui = new ChefGraphicalUserInterface();
		getRestaurant().addObserver(chefGui);
		new AdministratorGraphicalUserInterface().setVisible(true);
		new WaiterGraphicalUserInterface().setVisible(true);
		chefGui.setVisible(true);
	}
}
