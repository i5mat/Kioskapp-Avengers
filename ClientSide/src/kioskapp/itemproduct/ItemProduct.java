package kioskapp.itemproduct;

/**
 * This class represent an item product sells by a restaurant,
 * such as Big Mac, 2 pcs Spicy Fried Chicken, Oreo McFlurry 
 * @author emalianakasmuri
 *
 */

public class ItemProduct {
	
	// Declaration of attributes
	private int itemProduct;
	private String name;
	private float price;
	
	/**
	 * @return the itemProduct
	 */
	public int getItemProduct() {
		return itemProduct;
	}
	
	/**
	 * @param itemProduct the itemProduct to set
	 */
	public void setItemProduct(int itemProduct) {
		this.itemProduct = itemProduct;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	

}
