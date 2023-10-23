package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Order implements Serializable {
	
	/* all primary attributes */
	private int Id;
	
	/* all references */
	private List<Product> OrdertoProduct = new LinkedList<Product>(); 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	
	/* all functions for reference*/
	public List<Product> getOrdertoProduct() {
		return OrdertoProduct;
	}	
	
	public void addOrdertoProduct(Product product) {
		this.OrdertoProduct.add(product);
	}
	
	public void deleteOrdertoProduct(Product product) {
		this.OrdertoProduct.remove(product);
	}
	


}
