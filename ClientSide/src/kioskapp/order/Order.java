package kioskapp.order;

import java.util.List;

import kioskapp.ordereditem.OrderedItem;

/**
 * This class represent order made by a customer
 * 
 * @author emalianakasmuri
 *
 */
public class Order {
	
	// Declaration of attributes
	
	private int orderId;
	
	// Implementation of 1:M
	private List<OrderedItem> orderedItems;
	
	private float totalAmount;
	private int orderReferenceNumber;
	
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * @return the orderedItems
	 */
	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}
	
	/**
	 * @param orderedItems the orderedItems to set
	 */
	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}
	
	/**
	 * @return the totalAmount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
	 * @return the orderReferenceNumber
	 */
	public int getOrderReferenceNumber() {
		return orderReferenceNumber;
	}
	
	/**
	 * @param orderReferenceNumber the orderReferenceNumber to set
	 */
	public void setOrderReferenceNumber(int orderReferenceNumber) {
		this.orderReferenceNumber = orderReferenceNumber;
	}
	
	
	
	
	

}
