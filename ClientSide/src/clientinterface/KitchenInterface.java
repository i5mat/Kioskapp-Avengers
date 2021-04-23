package clientinterface;

import java.awt.*;
import javax.swing.*;
import kioskapp.itemproduct.ItemProduct;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KitchenInterface {
	
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
	
	public KitchenInterface() {
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
	
	private void KitchenGUI() {
		//create a new frame
		frame = new JFrame("Kitchen Frame");
		
		//create a object
		KitchenInterface s=new KitchenInterface();
		
		label1 = new JLabel();
		label2 = new JLabel("Order Number:");
		label3 = new JLabel("Kitchen Order Screen");
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
		
		KitchenGUI();		
		DefaultListModel dm = new DefaultListModel();
		
		String sql = "select OrderedItemId, i.Name 'Itemproduct', Quantity , o.OrderReferenceNumber 'orders', o.TotalAmount from orders o, ordereditem t, itemproduct i where t.Orders = o.OrderId and t.ItemProduct = i.ItemProduct";
	
		try {
			Connection connection = DriverManager.getConnection(connectionURL+dbName+"?serverTimezone=UTC",username,password);
			statement = connection.prepareStatement(sql);
			resultset = statement.executeQuery(sql);
			
			order = 0;
			int i = 0, quantity = 0;
			
			//receipt generation
			OutputStreamReciept osr = new OutputStreamReciept();
			ArrayList<String> recieptMenu =new ArrayList<String>();			
			int OrderIdReceipt = 0;
			float totalOrder = 0;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");  
	    	LocalDateTime now = LocalDateTime.now();
	    	//receipt generation
	    	
			while (resultset.next())
			{
			  i++;
			  OrderIdReceipt = resultset.getInt("orders");
			  int id = i;
			  String item  = resultset.getString("ItemProduct");
			  quantity = resultset.getInt("Quantity");
			  totalOrder = resultset.getFloat("TotalAmount");
			  order = resultset.getInt("Orders");
              itemprod.setName(item);
              
              recieptMenu.add(item);
              list1.addElement(String.valueOf(id) +"  "+ " ITEM: " + itemprod.getName()+" "+ "QUANTITY: " + String.valueOf(quantity));
			}
			
			osr.generateReceipt(OrderIdReceipt, dtf.format(now), recieptMenu, totalOrder, quantity);
			
			label1.setText(String.valueOf(order));
			return dm;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
