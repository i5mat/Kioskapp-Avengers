package clientinterface;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class OutputStreamReciept {

	public void generateReceipt(int orderId, String dateTime, ArrayList<String> menuList, float totalOrder) {
		
		// Declaration of target storage
		String targetStorage = String.valueOf(orderId)+"_"+dateTime+".txt";
				
		try (PrintWriter out = new PrintWriter(targetStorage)) {
			
			Iterator itr = menuList.iterator();  
			out.println("Order Id: " + String.valueOf(orderId));
			out.println("Item: ");
			int i = 0;
			while(itr.hasNext()){  
				i++;
				out.println(String.valueOf(i) + ". " + itr.next());
			}  
			out.println("Total: RM" + String.valueOf(totalOrder));
	         
		} catch (Exception e) {

			e.printStackTrace();
		}  
		
		//Indicate end of program - Could be succcessful
		System.out.println("End of program.");
		System.out.println("Right click on project. Select Refresh.");
		System.out.println(targetStorage + " should be there. Check it out!");
	}
}
