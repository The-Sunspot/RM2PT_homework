package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;

public class ReviewUserApplicationServiceImpl implements ReviewUserApplicationService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ReviewUserApplicationServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
			
	/* all get and set functions for temp property*/
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		Test1System test1system_service = (Test1System) ServiceManager.getAllInstancesOf("Test1System").get(0);
	}
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean getUserApplicationsList(String adminname) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (adminname instanceof String && !adminname.equals("")) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
		
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [adminname] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean fillResultAndDesc(boolean results, String comment) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (comment instanceof String && !comment.equals("") && results instanceof boolean) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
		
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [comment] 
	}  
	
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
