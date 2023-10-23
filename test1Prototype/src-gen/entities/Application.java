package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Application implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private LocalDate CreateDate;
	private String State;
	
	/* all references */
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public LocalDate getCreateDate() {
		return CreateDate;
	}	
	
	public void setCreateDate(LocalDate createdate) {
		this.CreateDate = createdate;
	}
	public String getState() {
		return State;
	}	
	
	public void setState(String state) {
		this.State = state;
	}
	
	/* all functions for reference*/
	


}
