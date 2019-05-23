package businessL;

@SuppressWarnings("serial")
public class BaseProduct extends MenuItem {
	
	public BaseProduct(float price, String name) {
		this.setName(name);
		this.setPrice(price);
	}
	
	public String toString() {
		return this.getName() + " " + this.getPrice();
	}
}
