package businessL;

import java.util.ArrayList;

public interface RestaurantProcessing {

	/**
	 * Administrator operations
	 */
	/**
	 * @pre price > 0 && name != null;
	 * @post App.getRestaurant().getMenuItems() != null;
	 * @param price
	 * @param name
	 * @param isComposite
	 * @param products
	 */
	public void createMenuItem(float price, String name, boolean isComposite, ArrayList<BaseProduct> products);
	/**
	 * @pre itemName != null;
	 * @post Restaurant.getMenuItems().get(itemName) == null;
	 * @param itemName
	 */
	public void deleteMenuItem(String itemName);
	
	/**
	 * @pre menuItem != null;
	 * @post menuItem != menuItem@pre
	 * @param menuItem
	 * @param newName
	 * @param newPrice
	 */
	public void editMenuItem(MenuItem menuItem, String newName, float newPrice);
	
	/**
	 * Waiter operations
	 */
	/**
	 * @pre order != null;
	 * @post App.getRestaurant().getOrders().get(order) != null;
	 * @param order
	 * @param products
	 */
	public void createOrder(Order order, ArrayList<MenuItem> products);
	/**
	 * @pre order != null;
	 * @post price > 0;
	 * @param order
	 * @return
	 */
	public float computePrice(Order order);
	/**
	 * @pre order != null;
	 * @post file.exists();
	 * @param order
	 */
	public void generateBill(Order order);
}
