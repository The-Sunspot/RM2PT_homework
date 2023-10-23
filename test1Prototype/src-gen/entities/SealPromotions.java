package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class SealPromotions implements Serializable {
	
	/* all primary attributes */
	private float PromotionRate;
	private LocalDate BeginSaleDate;
	private LocalDate CreateDate;
	private LocalDate EndSaleDate;
	
	/* all references */
	private List<Product> SealPromotionstoProduct = new LinkedList<Product>(); 
	
	/* all get and set functions */
	public float getPromotionRate() {
		return PromotionRate;
	}	
	
	public void setPromotionRate(float promotionrate) {
		this.PromotionRate = promotionrate;
	}
	public LocalDate getBeginSaleDate() {
		return BeginSaleDate;
	}	
	
	public void setBeginSaleDate(LocalDate beginsaledate) {
		this.BeginSaleDate = beginsaledate;
	}
	public LocalDate getCreateDate() {
		return CreateDate;
	}	
	
	public void setCreateDate(LocalDate createdate) {
		this.CreateDate = createdate;
	}
	public LocalDate getEndSaleDate() {
		return EndSaleDate;
	}	
	
	public void setEndSaleDate(LocalDate endsaledate) {
		this.EndSaleDate = endsaledate;
	}
	
	/* all functions for reference*/
	public List<Product> getSealPromotionstoProduct() {
		return SealPromotionstoProduct;
	}	
	
	public void addSealPromotionstoProduct(Product product) {
		this.SealPromotionstoProduct.add(product);
	}
	
	public void deleteSealPromotionstoProduct(Product product) {
		this.SealPromotionstoProduct.remove(product);
	}
	


}
