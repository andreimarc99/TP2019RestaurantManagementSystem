package businessL;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.JOptionPane;

import presentationL.AdministratorGraphicalUserInterface;
import ro.tuc.tp.assig4.App;

@SuppressWarnings({ "deprecation", "serial" })
public class Restaurant extends Observable implements RestaurantProcessing, Serializable {

	private HashMap<Order, ArrayList<MenuItem>> orders = new HashMap<Order, ArrayList<MenuItem>>();
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	public HashMap<Order, ArrayList<MenuItem>> getOrders() {
		return orders;
	}
	public void setOrders(HashMap<Order, ArrayList<MenuItem>> orders) {
		this.orders = orders;
	}
	public ArrayList<MenuItem> getOrder(Order order) {
		return orders.get(order);
	}
	public void addOrderEntry(Order order, ArrayList<MenuItem> products) {
		orders.put(order, products);
		setChanged();
		notifyObservers(order);
	}
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void addMenuItem(MenuItem menuItem) {
		this.menuItems.add(menuItem);
	}

	public void createOrder(Order order, ArrayList<MenuItem> products) {
		assert order != null && products.size() > 0;
		App.getRestaurant().addOrderEntry(order, products);
		assert !App.getRestaurant().getOrders().get(order).equals(null);
	}

	public float computePrice(Order order) {
		assert !order.equals(null) && App.getRestaurant().getOrders().containsKey(order);
		float price = 0;
		for (MenuItem item : App.getRestaurant().getOrder(order)) {
			price += item.getPrice();
		}
		assert price > 0;
		return price;
	}

	public void generateBill(Order order) {
		assert !order.equals(null);
		File file = new File("Order " + order.getOrderId() + ".txt");

		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				for (int i = 1; i < 100; ++i) {
					file = new File("Order " + order.getOrderId() + "." + i + ".txt");
					if (!file.exists()) {
						file.createNewFile();
						break;
					}
				}
			}

			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println("RESTAURANT INC.\t\tDATE: " + order.getDate() + "\n\n");
			printWriter.println("\n");
			printWriter.println("\nTABLE:" + order.getTable());
			printWriter.println("\nPRODUCTS CONSUMED:");
			for (MenuItem item : App.getRestaurant().getOrder(order)) {
				printWriter.println("\n" + item.getName() + " - " + item.getPrice());
			}
			printWriter.println("\n\n----------------------------------------------");
			printWriter.println("\n\nTOTAL PRICE:\t" + computePrice(order));
			printWriter.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot create file");
		}
		assert file.exists();
	}

	public void createMenuItem(float price, String name, boolean isComposite, ArrayList<BaseProduct> products) {
		assert price > 0 && name != null;
		if (isComposite) {
			ArrayList<BaseProduct> compositeProductList = new ArrayList<BaseProduct>();
			if (products.size() <= 1) {
				JOptionPane.showMessageDialog(null, "Select more than two items.");
			} else {
				float price1 = 0;
				for (BaseProduct product : products) {
					compositeProductList.add(product);
					price1 += product.getPrice();
				}

				App.getRestaurant().addMenuItem(new CompositeProduct(price1, name, compositeProductList));

			}
		} else {
			App.getRestaurant().addMenuItem(new BaseProduct(price, name));
			AdministratorGraphicalUserInterface.refreshModel(price, name);
		}
		assert App.getRestaurant().getMenuItems() != null;
	}

	public void deleteMenuItem(String itemName) {
		assert itemName != null;
		MenuItem itemToBeRemoved = null;
		boolean found = false;
		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			if (item.getName().equals(itemName)) {
				itemToBeRemoved = item;
				found = true;
			}
		}
		if (found) {
			App.getRestaurant().getMenuItems().remove(itemToBeRemoved);
			JOptionPane.showMessageDialog(null, "Deleted.");
		} else {
			JOptionPane.showMessageDialog(null, "Invalid selection.");
		}
		assert App.getRestaurant().getMenuItems().contains(App.getRestaurant().getMenuItemByName(itemName));
	}

	public void editMenuItem(MenuItem menuItem, String newName, float newPrice) {
		assert newName != null && newPrice > 0 && menuItem != null;
		boolean found = false;
		if (App.getRestaurant().getMenuItems().contains(menuItem)) {

			for (MenuItem item : App.getRestaurant().getMenuItems()) {
				if (item.getName().equals(menuItem.getName())) {
					item.setName(newName);
					item.setPrice(newPrice);
					found = true;
				}
			}
		}
		if (!found) {
			JOptionPane.showMessageDialog(null, "Item not found in the menu.");
		}
		assert !menuItem.equals(null);
	}
	
	public String toString() {
		return this.getMenuItems().toString() + " " + this.getOrders().entrySet().toString();
	}
	
	public MenuItem getMenuItemByName(String name) {
		MenuItem searched = null;
		for (MenuItem i : App.getRestaurant().getMenuItems()) {
			if (i.getName().equals(name)) {
				searched = i;
			}
		}
		return searched;
	}
}
