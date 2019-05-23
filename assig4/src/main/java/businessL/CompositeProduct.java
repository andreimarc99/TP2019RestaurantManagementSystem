package businessL;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class CompositeProduct extends MenuItem {
	
	private ArrayList<BaseProduct> products = new ArrayList<BaseProduct>();
	
	public CompositeProduct(float price, String name, ArrayList<BaseProduct> products) {
		this.setPrice(price);
		this.setName(name);
		for (BaseProduct product : products) {
			this.addToProductList(product);
		}
	}
	
	public float getPrice() {
		float price = 0;
		for (BaseProduct product : this.products) {
			price += product.getPrice();
		}
		return price;
	}
	
	/**
	 * @return the products
	 */
	public ArrayList<BaseProduct> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<BaseProduct> products) {
		this.products = products;
	}
	
	public void addToProductList(BaseProduct product) {
		products.add(product);
	}
	
	public String toString() {
		return this.getName() + " " + this.getPrice() + " " + products.toString();
		
	}
	
	public String listToString() {
		String s = new String();
		s = products.get(0).getName();
		for (BaseProduct p : products) {
			if (p.equals(products.get(0))) {
				continue;
			} else {
				s = s + " " + p.getName();
			}
		}
		return s;
	}
}
