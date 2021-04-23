package clientinterface;

import kioskapp.ordertransaction.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ClientApplication {
	
	static String driver;
	static String dbName;
	static String connectionURL;
	static String username;
	static String password;
	
	Connection connection;
    Statement statement;
	   
	public void SendCreditCardNumber(int cardNumber, int orderID, float totalAmount, String orderMode) {
		
		driver = "com.mysql.cj.jdbc.Driver";
		connectionURL ="jdbc:mysql://localhost:3306/";
		dbName = "kioskappdb_dev";
		username = "root";
		password = "";
		
		OrderTransaction ot = new OrderTransaction();
		
		// The server port to which the client socket is going to connect
		final int SERVERPORT2 = 50002;

		int bufferSize = 1024;

		try {
			// Instantiate client socket
			DatagramSocket clientSocket = new DatagramSocket();

			// Get the IP address of the server
			InetAddress serverAddress = InetAddress.getByName("localhost");
			
			// Create buffer to send data2
			byte sendingDataBuffer2[] = new byte[bufferSize];
			byte sendingDataBuffer3[] = new byte[bufferSize];
			byte sendingDataBuffer4[] = new byte[bufferSize];
			byte sendingDataBuffer5[] = new byte[bufferSize];
			byte sendingDataBuffer6[] = new byte[bufferSize];
			
			// Convert data to bytes and store data in the buffer2			
			// Last4Digits
			String sentence2 = String.valueOf(cardNumber);
			// Transaction Date
			String sentence3 = "now()";
			// Order id
			String sentence4 = String.valueOf(orderID);
			// AmountCharged
			String sentence5 = String.valueOf(totalAmount);
			// OrderMode
			String sentence6 = orderMode;
			
			// Last4Digits
			sendingDataBuffer2 = sentence2.getBytes();
			// Transaction Date
			sendingDataBuffer3 = sentence3.getBytes();
			// Order id
			sendingDataBuffer4 = sentence4.getBytes();
			// AmountCharged
			sendingDataBuffer5 = sentence5.getBytes();
			// OrderMode
			sendingDataBuffer6 = sentence6.getBytes();
			
			// Creating a UDP packet 2
			// Last4Digits
			DatagramPacket sendingPacket2 = new DatagramPacket(sendingDataBuffer2,
					sendingDataBuffer2.length, serverAddress, SERVERPORT2);
			// Transaction Date
			DatagramPacket sendingPacket3 = new DatagramPacket(sendingDataBuffer3,
					sendingDataBuffer3.length, serverAddress, SERVERPORT2);
			// Order id
			DatagramPacket sendingPacket4 = new DatagramPacket(sendingDataBuffer4,
					sendingDataBuffer4.length, serverAddress, SERVERPORT2);
			// AmountCharged
			DatagramPacket sendingPacket5 = new DatagramPacket(sendingDataBuffer5,
					sendingDataBuffer5.length, serverAddress, SERVERPORT2);
			// OrderMode
			DatagramPacket sendingPacket6 = new DatagramPacket(sendingDataBuffer6,
					sendingDataBuffer6.length, serverAddress, SERVERPORT2);
			
			// Sending UDP packet to the server2
			// Last4Digits
			clientSocket.send(sendingPacket2);
			// Transaction Date
			clientSocket.send(sendingPacket3);	
			// Order id
			clientSocket.send(sendingPacket4);	
			// AmountCharged
			clientSocket.send(sendingPacket5);	
			// OrderMode
			clientSocket.send(sendingPacket6);	
			
			// Create buffer to receive data
			byte receivingDataBuffer [] = new byte[bufferSize];
			
			// Receive data packet from server
		    DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,
		    		receivingDataBuffer.length);
		    clientSocket.receive(receivingPacket);
		    
		    // Unpack packet
		    String allCapsData = new String(receivingPacket.getData(), 0, receivingPacket.getLength());
		    System.out.println("Received from server: " + allCapsData);
		    String verified = "verified";
		    
		    if(verified.equals(allCapsData)){
		    	KitchenInterface KI = new KitchenInterface();
		    	KI.retrieve();
		    	
		    	TransactionDetail td = new TransactionDetail();
		    	td.TransactionDetailUI();
		    	td.retrieve();
		    }
		    else {
		    	
		    	Connection connection = DriverManager.getConnection(connectionURL+dbName+"?serverTimezone=UTC",username,password);
		    	String queryDelete = "DELETE FROM ordereditem";
		    	String queryDelete2 = "DELETE FROM ordertransaction";
		    	String queryDelete3 = "DELETE FROM orders";
	    		PreparedStatement preparedStmt = connection.prepareStatement(queryDelete);
	    		PreparedStatement preparedStmt2 = connection.prepareStatement(queryDelete2);
	    		PreparedStatement preparedStmt3 = connection.prepareStatement(queryDelete3);
	    		preparedStmt.executeUpdate();	
	    		preparedStmt2.executeUpdate();	
	    		preparedStmt3.executeUpdate();	
	    		
		    	UserInterface ui = new UserInterface();
		    	UserInterface.mainFrame.dispose();
		    	ui.prepareGUI();
		    	ui.showTableDemo();
		    	JOptionPane.showMessageDialog(null,"Credit Card Number does not valid");
		    }

			// Closing the socket connection with the server
			clientSocket.close();			
			
		} catch (Exception ex) {
			System.out.println("Durian Tunggal... we got problem");
			ex.printStackTrace();
		}

	}

}