package services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import services.*;
	
public class ServiceManager {
	
	private static Map<String, List> AllServiceInstance = new HashMap<String, List>();
	
	private static List<Test1System> Test1SystemInstances = new LinkedList<Test1System>();
	private static List<ThirdPartyServices> ThirdPartyServicesInstances = new LinkedList<ThirdPartyServices>();
	private static List<UserLoginService> UserLoginServiceInstances = new LinkedList<UserLoginService>();
	private static List<SignupService> SignupServiceInstances = new LinkedList<SignupService>();
	private static List<SubmitQualifyApplicationService> SubmitQualifyApplicationServiceInstances = new LinkedList<SubmitQualifyApplicationService>();
	private static List<AdminLoginService> AdminLoginServiceInstances = new LinkedList<AdminLoginService>();
	private static List<ReviewUserApplicationService> ReviewUserApplicationServiceInstances = new LinkedList<ReviewUserApplicationService>();
	private static List<AddNewProductService> AddNewProductServiceInstances = new LinkedList<AddNewProductService>();
	private static List<ViewSelfOrdersService> ViewSelfOrdersServiceInstances = new LinkedList<ViewSelfOrdersService>();
	private static List<PromotionSaleSystem> PromotionSaleSystemInstances = new LinkedList<PromotionSaleSystem>();
	
	static {
		AllServiceInstance.put("Test1System", Test1SystemInstances);
		AllServiceInstance.put("ThirdPartyServices", ThirdPartyServicesInstances);
		AllServiceInstance.put("UserLoginService", UserLoginServiceInstances);
		AllServiceInstance.put("SignupService", SignupServiceInstances);
		AllServiceInstance.put("SubmitQualifyApplicationService", SubmitQualifyApplicationServiceInstances);
		AllServiceInstance.put("AdminLoginService", AdminLoginServiceInstances);
		AllServiceInstance.put("ReviewUserApplicationService", ReviewUserApplicationServiceInstances);
		AllServiceInstance.put("AddNewProductService", AddNewProductServiceInstances);
		AllServiceInstance.put("ViewSelfOrdersService", ViewSelfOrdersServiceInstances);
		AllServiceInstance.put("PromotionSaleSystem", PromotionSaleSystemInstances);
	} 
	
	public static List getAllInstancesOf(String ClassName) {
			 return AllServiceInstance.get(ClassName);
	}	
	
	public static Test1System createTest1System() {
		Test1System s = new Test1SystemImpl();
		Test1SystemInstances.add(s);
		return s;
	}
	public static ThirdPartyServices createThirdPartyServices() {
		ThirdPartyServices s = new ThirdPartyServicesImpl();
		ThirdPartyServicesInstances.add(s);
		return s;
	}
	public static UserLoginService createUserLoginService() {
		UserLoginService s = new UserLoginServiceImpl();
		UserLoginServiceInstances.add(s);
		return s;
	}
	public static SignupService createSignupService() {
		SignupService s = new SignupServiceImpl();
		SignupServiceInstances.add(s);
		return s;
	}
	public static SubmitQualifyApplicationService createSubmitQualifyApplicationService() {
		SubmitQualifyApplicationService s = new SubmitQualifyApplicationServiceImpl();
		SubmitQualifyApplicationServiceInstances.add(s);
		return s;
	}
	public static AdminLoginService createAdminLoginService() {
		AdminLoginService s = new AdminLoginServiceImpl();
		AdminLoginServiceInstances.add(s);
		return s;
	}
	public static ReviewUserApplicationService createReviewUserApplicationService() {
		ReviewUserApplicationService s = new ReviewUserApplicationServiceImpl();
		ReviewUserApplicationServiceInstances.add(s);
		return s;
	}
	public static AddNewProductService createAddNewProductService() {
		AddNewProductService s = new AddNewProductServiceImpl();
		AddNewProductServiceInstances.add(s);
		return s;
	}
	public static ViewSelfOrdersService createViewSelfOrdersService() {
		ViewSelfOrdersService s = new ViewSelfOrdersServiceImpl();
		ViewSelfOrdersServiceInstances.add(s);
		return s;
	}
	public static PromotionSaleSystem createPromotionSaleSystem() {
		PromotionSaleSystem s = new PromotionSaleSystemImpl();
		PromotionSaleSystemInstances.add(s);
		return s;
	}
}	
