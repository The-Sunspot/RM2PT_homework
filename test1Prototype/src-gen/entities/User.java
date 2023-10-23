package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class User implements Serializable {
	
	/* all primary attributes */
	private String Username;
	private String Paasword;
	private boolean IsQualify;
	private LocalDate SignupDate;
	private int UserId;
	
	/* all references */
	private List<Profile> UsertoProfile = new LinkedList<Profile>(); 
	private List<Order> UsertoOrder = new LinkedList<Order>(); 
	
	/* all get and set functions */
	public String getUsername() {
		return Username;
	}	
	
	public void setUsername(String username) {
		this.Username = username;
	}
	public String getPaasword() {
		return Paasword;
	}	
	
	public void setPaasword(String paasword) {
		this.Paasword = paasword;
	}
	public boolean getIsQualify() {
		return IsQualify;
	}	
	
	public void setIsQualify(boolean isqualify) {
		this.IsQualify = isqualify;
	}
	public LocalDate getSignupDate() {
		return SignupDate;
	}	
	
	public void setSignupDate(LocalDate signupdate) {
		this.SignupDate = signupdate;
	}
	public int getUserId() {
		return UserId;
	}	
	
	public void setUserId(int userid) {
		this.UserId = userid;
	}
	
	/* all functions for reference*/
	public List<Profile> getUsertoProfile() {
		return UsertoProfile;
	}	
	
	public void addUsertoProfile(Profile profile) {
		this.UsertoProfile.add(profile);
	}
	
	public void deleteUsertoProfile(Profile profile) {
		this.UsertoProfile.remove(profile);
	}
	public List<Order> getUsertoOrder() {
		return UsertoOrder;
	}	
	
	public void addUsertoOrder(Order order) {
		this.UsertoOrder.add(order);
	}
	
	public void deleteUsertoOrder(Order order) {
		this.UsertoOrder.remove(order);
	}
	


}
