package clientinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import kioskapp.itemproduct.ItemProduct;

public class TransactionDetail {

	public String Data;
	public int order;
    private DefaultListModel<String> list1;    
    private JList<String> list; 
	
    ItemProduct itemprod = new ItemProduct();
   
	private JFrame frame;
	private JList itemid,item;
	private JLabel label1,label2, label3;
	private JPanel lab,p;
	static String driver;
	static String dbName;
	static String connectionURL;
	static String username;
	static String password;
	
	Connection connection;
	Statement statement;
	ResultSet resultset;
		
	public TransactionDetail() {
		driver = "com.mysql.cj.jdbc.Driver";
		connectionURL ="jdbc:mysql://localhost:3306/";
		dbName = "kioskappdb_dev";
		username = "root";
		password = "";
		
		list1= new DefaultListModel<>();
		list = new JList<>(list1);
		list.setFixedCellHeight(50);
		list.setFixedCellWidth(500);
		list.setBounds(100,100,75,75);
	}
	
	public void TransactionDetailUI() {
		//create a new frame
		frame = new JFrame("Transaction Detail");
				
		label1 = new JLabel();
		label2 = new JLabel("Order Number:");
		label3 = new JLabel("Transaction Receipt  ");
		label1.setFont(new Font("Serif", Font.BOLD, 20));
		label3.setFont(new Font("Serif", Font.BOLD, 20));
		label2.setFont(new Font("Serif", Font.BOLD, 20));

		//NORTH
		lab =new JPanel();
		lab.setPreferredSize(new Dimension(250,100));
		lab.setLayout(new FlowLayout());
		lab.add(label3);
		lab.add(label2);
		lab.add(label1);
		
		//create a panel
		p =new JPanel();
		p.setPreferredSize(new Dimension(250,250));
		p.setLayout(new FlowLayout());
		p.add(list);
		
		frame.add(p, BorderLayout.CENTER);
		frame.add(lab, BorderLayout.NORTH);
		frame.setBackground(Color.BLACK);

		//set the size of frame
		frame.setSize(700,600);		
		frame.setVisible(true);			
	}
	
	public DefaultListModel retrieve() {
			
		DefaultListModel dm = new DefaultListModel();
		
		String sql = "select OrderedItemId, i.Name 'Itemproduct', Quantity , o.OrderReferenceNumber 'orders', o.TotalAmount from orders o, ordereditem t, itemproduct i where t.Orders = o.OrderId and t.ItemProduct = i.ItemProduct";
	
		try {
			Connection connection = DriverManager.getConnection(connectionURL+dbName+"?serverTimezone=UTC",username,password);
			statement = connection.prepareStatement(sql);
			resultset = statement.executeQuery(sql);
			
			order = 0;
			int i = 0;
			float totalOrder = 0;
			list1.addElement("Order Summary: ");
			while (resultset.next())
			{
			  i++;
			  order = resultset.getInt("OrderedItemId");
			  String item  = resultset.getString("ItemProduct");
			  int quantity = resultset.getInt("Quantity");
			  totalOrder = resultset.getFloat("TotalAmount");
			  order = resultset.getInt("Orders");
              itemprod.setName(item);
              
              list1.addElement(String.valueOf(i) + "  " + " ITEM: " + item + " " + "QUANTITY: " + String.valueOf(quantity));
			}
			list1.addElement("Total Amount (RM): " + String.valueOf(totalOrder));
			
			label1.setText(String.valueOf(order));
			return dm;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
