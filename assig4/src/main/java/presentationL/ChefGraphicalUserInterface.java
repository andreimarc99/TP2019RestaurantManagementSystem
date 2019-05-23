package presentationL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessL.BaseProduct;
import businessL.CompositeProduct;
import businessL.MenuItem;
import businessL.Order;
import businessL.Restaurant;
import ro.tuc.tp.assig4.App;

@SuppressWarnings({ "deprecation", "serial" })
public class ChefGraphicalUserInterface extends JFrame implements Observer {
	private static JTable table;
	private static DefaultTableModel tableModel;

	public ChefGraphicalUserInterface() {
		setTitle("Chef UI");
		setBounds(0, 550, 900, 500);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, 828, 338);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Table", "Product", "Ingredients" }));
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		tableModel = (DefaultTableModel) table.getModel();
		for (HashMap.Entry<Order, ArrayList<MenuItem>> entry : App.getRestaurant().getOrders().entrySet()) {
			for (MenuItem i : entry.getValue()) {
				if (i instanceof CompositeProduct) {
					tableModel.addRow(new Object[] { entry.getKey().getOrderId(), entry.getKey().getTable(),
							i.getName(), ((CompositeProduct) i).listToString() });
				}
			}
		}
		scrollPane.setViewportView(table);
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Order) {
			Order order = (Order) arg;
			for (MenuItem item : App.getRestaurant().getOrder(order)) {
				if (item instanceof CompositeProduct) {
					tableModel.addRow(new Object[] { order.getOrderId(), order.getTable(), item.getName(),
							((CompositeProduct) item).listToString() });
				}
			}
		}
	}
}
