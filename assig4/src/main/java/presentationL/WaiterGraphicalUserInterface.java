package presentationL;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import businessL.BaseProduct;
import businessL.MenuItem;
import businessL.Order;
import businessL.Restaurant;
import businessL.RestaurantProcessing;
import ro.tuc.tp.assig4.App;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

public class WaiterGraphicalUserInterface extends JFrame {
	private JTextField tableTextField;
	private static JList productList;
	private static JList<Order> ordersList;
	private static DefaultListModel<Order> ordersModel;
	private static DefaultListModel<MenuItem> lModel;
	
	public static void updateProductList(MenuItem menuItem) {
		lModel.addElement(menuItem);
	}
	
	public static void removeItemFromProductList(MenuItem menuItem) {
		lModel.removeElement(menuItem);
	}

	public WaiterGraphicalUserInterface() {

		setTitle("Waiter UI");
		setBounds(0, 50, 346, 500);
		getContentPane().setLayout(null);

		JLabel lblNewOrder = new JLabel("NEW   ORDER");
		lblNewOrder.setBounds(10, 11, 115, 14);
		getContentPane().add(lblNewOrder);

		JLabel lblTableNumber = new JLabel("Table number");
		lblTableNumber.setBounds(10, 36, 115, 14);
		getContentPane().add(lblTableNumber);

		tableTextField = new JTextField();
		tableTextField.setBounds(10, 61, 115, 20);
		getContentPane().add(tableTextField);
		tableTextField.setColumns(10);

		JLabel lblProducts = new JLabel("Products");
		lblProducts.setBounds(10, 92, 115, 14);
		getContentPane().add(lblProducts);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 117, 115, 208);
		getContentPane().add(scrollPane);

		productList = new JList();
		lModel = new DefaultListModel<MenuItem>();
		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			lModel.addElement(item);
		}
		productList.setModel(lModel);
		scrollPane.setViewportView(productList);

		JButton btnCreateNewOrder = new JButton("Create order");
		btnCreateNewOrder.setBounds(10, 336, 115, 36);
		btnCreateNewOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!tableTextField.getText().isEmpty() && productList.getSelectedValues().length >= 1) {
					try {
						int table = Integer.parseInt(tableTextField.getText().trim());
						int id = 1;
						int maxId = -1;
						for (HashMap.Entry<Order, ArrayList<MenuItem>> entry : App.getRestaurant().getOrders()
								.entrySet()) {
							if (maxId < entry.getKey().getOrderId()) {
								maxId = entry.getKey().getOrderId();
							}
						}
						if (maxId == -1) {
							id = 1;
						} else {
							id = maxId + 1;
						}
						ArrayList<MenuItem> products = new ArrayList<MenuItem>();
						for (Object o : productList.getSelectedValues()) {
							MenuItem item = (MenuItem) o;
							products.add(item);
						}
						Date date = new Date();
						Order order = new Order(id, date, table);
						App.getRestaurant().createOrder(order, products);
						ordersModel.addElement(order);

						JOptionPane.showMessageDialog(null, "Order created");
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Error parsing the table field.");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Please complete all the necessary fields.");
				}
			}
		});
		getContentPane().add(btnCreateNewOrder);

		JLabel lblComputePriceFor = new JLabel("COMPUTE  PRICE  FOR ");
		lblComputePriceFor.setBounds(165, 11, 151, 14);
		getContentPane().add(lblComputePriceFor);

		JLabel lblAnOrder = new JLabel("          AN  ORDER");
		lblAnOrder.setBounds(165, 24, 115, 14);
		getContentPane().add(lblAnOrder);

		JLabel lblOrders = new JLabel("Orders");
		lblOrders.setBounds(165, 92, 115, 14);
		getContentPane().add(lblOrders);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(165, 117, 115, 208);
		getContentPane().add(scrollPane_1);

		ordersList = new JList();
		ordersModel = new DefaultListModel<Order>();
		for (HashMap.Entry<Order, ArrayList<MenuItem>> o : App.getRestaurant().getOrders().entrySet()) {
			ordersModel.addElement(o.getKey());
		}
		ordersList.setModel(ordersModel);
		scrollPane_1.setViewportView(ordersList);

		JButton btnComputePrice = new JButton("Compute");
		btnComputePrice.setBounds(165, 336, 115, 36);
		btnComputePrice.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Order order = ordersList.getSelectedValue();
				JOptionPane.showMessageDialog(null,
						"Total price for the selected order will be " + App.getRestaurant().computePrice(order));
			}
		});
		getContentPane().add(btnComputePrice);

		JButton btnGenerateBill = new JButton("Generate Bill");
		btnGenerateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (ordersList.getSelectedValue().equals(null) != false) {
					App.getRestaurant().generateBill(ordersList.getSelectedValue());
					JOptionPane.showMessageDialog(null, "Generated bill. Total price will be " + App.getRestaurant().computePrice(ordersList.getSelectedValue()) + "$. "
							+ "For more details, check the .txt file" );
				//}
			}
		});
		btnGenerateBill.setBounds(165, 383, 115, 36);
		getContentPane().add(btnGenerateBill);
	}
}
