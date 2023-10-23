package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Admin implements Serializable {
	
	/* all primary attributes */
	private int AdminId;
	private String AdminName;
	
	/* all references */
	private List<QualifiedUser> AdmintoQualifiedUser = new LinkedList<QualifiedUser>(); 
	private List<Application> AdmintoApplication = new LinkedList<Application>(); 
	private List<SealPromotions> AdmintoSealPromotions = new LinkedList<SealPromotions>(); 
	
	/* all get and set functions */
	public int getAdminId() {
		return AdminId;
	}	
	
	public void setAdminId(int adminid) {
		this.AdminId = adminid;
	}
	public String getAdminName() {
		return AdminName;
	}	
	
	public void setAdminName(String adminname) {
		this.AdminName = adminname;
	}
	
	/* all functions for reference*/
	public List<QualifiedUser> getAdmintoQualifiedUser() {
		return AdmintoQualifiedUser;
	}	
	
	public void addAdmintoQualifiedUser(QualifiedUser qualifieduser) {
		this.AdmintoQualifiedUser.add(qualifieduser);
	}
	
	public void deleteAdmintoQualifiedUser(QualifiedUser qualifieduser) {
		this.AdmintoQualifiedUser.remove(qualifieduser);
	}
	public List<Application> getAdmintoApplication() {
		return AdmintoApplication;
	}	
	
	public void addAdmintoApplication(Application application) {
		this.AdmintoApplication.add(application);
	}
	
	public void deleteAdmintoApplication(Application application) {
		this.AdmintoApplication.remove(application);
	}
	public List<SealPromotions> getAdmintoSealPromotions() {
		return AdmintoSealPromotions;
	}	
	
	public void addAdmintoSealPromotions(SealPromotions sealpromotions) {
		this.AdmintoSealPromotions.add(sealpromotions);
	}
	
	public void deleteAdmintoSealPromotions(SealPromotions sealpromotions) {
		this.AdmintoSealPromotions.remove(sealpromotions);
	}
	


}
