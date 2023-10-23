package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class QualifiedUser extends User  implements Serializable {
	
	/* all primary attributes */
	private LocalDate QualifiedDate;
	
	/* all references */
	private List<Application> QualifiedUsertoApplication = new LinkedList<Application>(); 
	
	/* all get and set functions */
	public LocalDate getQualifiedDate() {
		return QualifiedDate;
	}	
	
	public void setQualifiedDate(LocalDate qualifieddate) {
		this.QualifiedDate = qualifieddate;
	}
	
	/* all functions for reference*/
	public List<Application> getQualifiedUsertoApplication() {
		return QualifiedUsertoApplication;
	}	
	
	public void addQualifiedUsertoApplication(Application application) {
		this.QualifiedUsertoApplication.add(application);
	}
	
	public void deleteQualifiedUsertoApplication(Application application) {
		this.QualifiedUsertoApplication.remove(application);
	}
	


}
