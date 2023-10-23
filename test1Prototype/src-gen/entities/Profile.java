package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Profile implements Serializable {
	
	/* all primary attributes */
	private String Name;
	private String Phone;
	private String IdCard;
	private SexEnum Sex;
	private String BankCard;
	private String WorkCompany;
	private String Address;
	private LocalDate Birthday;
	
	/* all references */
	
	/* all get and set functions */
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public String getPhone() {
		return Phone;
	}	
	
	public void setPhone(String phone) {
		this.Phone = phone;
	}
	public String getIdCard() {
		return IdCard;
	}	
	
	public void setIdCard(String idcard) {
		this.IdCard = idcard;
	}
	public SexEnum getSex() {
		return Sex;
	}	
	
	public void setSex(SexEnum sex) {
		this.Sex = sex;
	}
	public String getBankCard() {
		return BankCard;
	}	
	
	public void setBankCard(String bankcard) {
		this.BankCard = bankcard;
	}
	public String getWorkCompany() {
		return WorkCompany;
	}	
	
	public void setWorkCompany(String workcompany) {
		this.WorkCompany = workcompany;
	}
	public String getAddress() {
		return Address;
	}	
	
	public void setAddress(String address) {
		this.Address = address;
	}
	public LocalDate getBirthday() {
		return Birthday;
	}	
	
	public void setBirthday(LocalDate birthday) {
		this.Birthday = birthday;
	}
	
	/* all functions for reference*/
	


}
