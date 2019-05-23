package businessL;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class Order implements Serializable {
	private int orderId;
	private Date date;
	private int table;

	public Order(int id, Date date, int table) {
		this.orderId = id;
		this.date = date;
		this.table = table;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	public String toString() {
		return this.getOrderId() + " - table " + this.getTable();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Order) {
			Order order = (Order) o;
			if (order.orderId == this.orderId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.orderId;
	}

}
