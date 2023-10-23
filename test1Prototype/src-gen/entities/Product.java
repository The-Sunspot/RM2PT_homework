package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Product implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private String Name;
	private LocalDate CreateDate;
	private boolean IsOnSale;
	private int TotalCount;
	private int CurrentCount;
	private float OriginPrice;
	
	/* all references */
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public LocalDate getCreateDate() {
		return CreateDate;
	}	
	
	public void setCreateDate(LocalDate createdate) {
		this.CreateDate = createdate;
	}
	public boolean getIsOnSale() {
		return IsOnSale;
	}	
	
	public void setIsOnSale(boolean isonsale) {
		this.IsOnSale = isonsale;
	}
	public int getTotalCount() {
		return TotalCount;
	}	
	
	public void setTotalCount(int totalcount) {
		this.TotalCount = totalcount;
	}
	public int getCurrentCount() {
		return CurrentCount;
	}	
	
	public void setCurrentCount(int currentcount) {
		this.CurrentCount = currentcount;
	}
	public float getOriginPrice() {
		return OriginPrice;
	}	
	
	public void setOriginPrice(float originprice) {
		this.OriginPrice = originprice;
	}
	
	/* all functions for reference*/
	


}
