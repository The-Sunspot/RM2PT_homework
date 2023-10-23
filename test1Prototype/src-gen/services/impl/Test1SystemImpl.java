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

public class Test1SystemImpl implements Test1System, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public Test1SystemImpl() {
		services = new ThirdPartyServicesImpl();
	}

	public void refresh() {
		UserLoginService userloginservice_service = (UserLoginService) ServiceManager
				.getAllInstancesOf("UserLoginService").get(0);
		SignupService signupservice_service = (SignupService) ServiceManager
				.getAllInstancesOf("SignupService").get(0);
		SubmitQualifyApplicationService submitqualifyapplicationservice_service = (SubmitQualifyApplicationService) ServiceManager
				.getAllInstancesOf("SubmitQualifyApplicationService").get(0);
		AdminLoginService adminloginservice_service = (AdminLoginService) ServiceManager
				.getAllInstancesOf("AdminLoginService").get(0);
		ReviewUserApplicationService reviewuserapplicationservice_service = (ReviewUserApplicationService) ServiceManager
				.getAllInstancesOf("ReviewUserApplicationService").get(0);
		AddNewProductService addnewproductservice_service = (AddNewProductService) ServiceManager
				.getAllInstancesOf("AddNewProductService").get(0);
		ViewSelfOrdersService viewselfordersservice_service = (ViewSelfOrdersService) ServiceManager
				.getAllInstancesOf("ViewSelfOrdersService").get(0);
		PromotionSaleSystem promotionsalesystem_service = (PromotionSaleSystem) ServiceManager
				.getAllInstancesOf("PromotionSaleSystem").get(0);
	}			
	
	/* Generate buiness logic according to functional requirement */
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
