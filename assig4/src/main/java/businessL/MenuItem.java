package businessL;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class MenuItem implements Serializable {

	private float price;
	private String name;
	
	public MenuItem() {
	}
	
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		if (o instanceof MenuItem) {
			MenuItem currItem = (MenuItem) o;
			if (this.name.equals(currItem.name) && this.price == currItem.price) {
				return true;
			}
		}
		return false;
	}
}