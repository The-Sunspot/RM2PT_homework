package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class EntityManager {

	private static Map<String, List> AllInstance = new HashMap<String, List>();
	
	private static List<Profile> ProfileInstances = new LinkedList<Profile>();
	private static List<User> UserInstances = new LinkedList<User>();
	private static List<QualifiedUser> QualifiedUserInstances = new LinkedList<QualifiedUser>();
	private static List<Admin> AdminInstances = new LinkedList<Admin>();
	private static List<Product> ProductInstances = new LinkedList<Product>();
	private static List<Order> OrderInstances = new LinkedList<Order>();
	private static List<Application> ApplicationInstances = new LinkedList<Application>();
	private static List<SealPromotions> SealPromotionsInstances = new LinkedList<SealPromotions>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("Profile", ProfileInstances);
		AllInstance.put("User", UserInstances);
		AllInstance.put("QualifiedUser", QualifiedUserInstances);
		AllInstance.put("Admin", AdminInstances);
		AllInstance.put("Product", ProductInstances);
		AllInstance.put("Order", OrderInstances);
		AllInstance.put("Application", ApplicationInstances);
		AllInstance.put("SealPromotions", SealPromotionsInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(ProfileInstances);
			stateSave.writeObject(UserInstances);
			stateSave.writeObject(QualifiedUserInstances);
			stateSave.writeObject(AdminInstances);
			stateSave.writeObject(ProductInstances);
			stateSave.writeObject(OrderInstances);
			stateSave.writeObject(ApplicationInstances);
			stateSave.writeObject(SealPromotionsInstances);
			
			stateSave.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Load State */
	public static void load(File file) {
		
		try {
			
			ObjectInputStream stateLoad = new ObjectInputStream(new FileInputStream(file));
			
			try {
				
				ProfileInstances =  (List<Profile>) stateLoad.readObject();
				AllInstance.put("Profile", ProfileInstances);
				UserInstances =  (List<User>) stateLoad.readObject();
				AllInstance.put("User", UserInstances);
				QualifiedUserInstances =  (List<QualifiedUser>) stateLoad.readObject();
				AllInstance.put("QualifiedUser", QualifiedUserInstances);
				AdminInstances =  (List<Admin>) stateLoad.readObject();
				AllInstance.put("Admin", AdminInstances);
				ProductInstances =  (List<Product>) stateLoad.readObject();
				AllInstance.put("Product", ProductInstances);
				OrderInstances =  (List<Order>) stateLoad.readObject();
				AllInstance.put("Order", OrderInstances);
				ApplicationInstances =  (List<Application>) stateLoad.readObject();
				AllInstance.put("Application", ApplicationInstances);
				SealPromotionsInstances =  (List<SealPromotions>) stateLoad.readObject();
				AllInstance.put("SealPromotions", SealPromotionsInstances);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/* create object */  
	public static Object createObject(String Classifer) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method createObjectMethod = c.getDeclaredMethod("create" + Classifer + "Object");
			return createObjectMethod.invoke(c);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* add object */  
	public static Object addObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectMethod = c.getDeclaredMethod("add" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) addObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
	/* add objects */  
	public static Object addObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectsMethod = c.getDeclaredMethod("add" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) addObjectsMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Release object */
	public static boolean deleteObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) deleteObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/* Release objects */
	public static boolean deleteObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) deleteObjectMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}		 	
	
	 /* Get all objects belongs to same class */
	public static List getAllInstancesOf(String ClassName) {
			 return AllInstance.get(ClassName);
	}	

   /* Sub-create object */
	public static Profile createProfileObject() {
		Profile o = new Profile();
		return o;
	}
	
	public static boolean addProfileObject(Profile o) {
		return ProfileInstances.add(o);
	}
	
	public static boolean addProfileObjects(List<Profile> os) {
		return ProfileInstances.addAll(os);
	}
	
	public static boolean deleteProfileObject(Profile o) {
		return ProfileInstances.remove(o);
	}
	
	public static boolean deleteProfileObjects(List<Profile> os) {
		return ProfileInstances.removeAll(os);
	}
	public static User createUserObject() {
		User o = new User();
		return o;
	}
	
	public static boolean addUserObject(User o) {
		return UserInstances.add(o);
	}
	
	public static boolean addUserObjects(List<User> os) {
		return UserInstances.addAll(os);
	}
	
	public static boolean deleteUserObject(User o) {
		return UserInstances.remove(o);
	}
	
	public static boolean deleteUserObjects(List<User> os) {
		return UserInstances.removeAll(os);
	}
	public static QualifiedUser createQualifiedUserObject() {
		QualifiedUser o = new QualifiedUser();
		return o;
	}
	
	public static boolean addQualifiedUserObject(QualifiedUser o) {
		return QualifiedUserInstances.add(o);
	}
	
	public static boolean addQualifiedUserObjects(List<QualifiedUser> os) {
		return QualifiedUserInstances.addAll(os);
	}
	
	public static boolean deleteQualifiedUserObject(QualifiedUser o) {
		return QualifiedUserInstances.remove(o);
	}
	
	public static boolean deleteQualifiedUserObjects(List<QualifiedUser> os) {
		return QualifiedUserInstances.removeAll(os);
	}
	public static Admin createAdminObject() {
		Admin o = new Admin();
		return o;
	}
	
	public static boolean addAdminObject(Admin o) {
		return AdminInstances.add(o);
	}
	
	public static boolean addAdminObjects(List<Admin> os) {
		return AdminInstances.addAll(os);
	}
	
	public static boolean deleteAdminObject(Admin o) {
		return AdminInstances.remove(o);
	}
	
	public static boolean deleteAdminObjects(List<Admin> os) {
		return AdminInstances.removeAll(os);
	}
	public static Product createProductObject() {
		Product o = new Product();
		return o;
	}
	
	public static boolean addProductObject(Product o) {
		return ProductInstances.add(o);
	}
	
	public static boolean addProductObjects(List<Product> os) {
		return ProductInstances.addAll(os);
	}
	
	public static boolean deleteProductObject(Product o) {
		return ProductInstances.remove(o);
	}
	
	public static boolean deleteProductObjects(List<Product> os) {
		return ProductInstances.removeAll(os);
	}
	public static Order createOrderObject() {
		Order o = new Order();
		return o;
	}
	
	public static boolean addOrderObject(Order o) {
		return OrderInstances.add(o);
	}
	
	public static boolean addOrderObjects(List<Order> os) {
		return OrderInstances.addAll(os);
	}
	
	public static boolean deleteOrderObject(Order o) {
		return OrderInstances.remove(o);
	}
	
	public static boolean deleteOrderObjects(List<Order> os) {
		return OrderInstances.removeAll(os);
	}
	public static Application createApplicationObject() {
		Application o = new Application();
		return o;
	}
	
	public static boolean addApplicationObject(Application o) {
		return ApplicationInstances.add(o);
	}
	
	public static boolean addApplicationObjects(List<Application> os) {
		return ApplicationInstances.addAll(os);
	}
	
	public static boolean deleteApplicationObject(Application o) {
		return ApplicationInstances.remove(o);
	}
	
	public static boolean deleteApplicationObjects(List<Application> os) {
		return ApplicationInstances.removeAll(os);
	}
	public static SealPromotions createSealPromotionsObject() {
		SealPromotions o = new SealPromotions();
		return o;
	}
	
	public static boolean addSealPromotionsObject(SealPromotions o) {
		return SealPromotionsInstances.add(o);
	}
	
	public static boolean addSealPromotionsObjects(List<SealPromotions> os) {
		return SealPromotionsInstances.addAll(os);
	}
	
	public static boolean deleteSealPromotionsObject(SealPromotions o) {
		return SealPromotionsInstances.remove(o);
	}
	
	public static boolean deleteSealPromotionsObjects(List<SealPromotions> os) {
		return SealPromotionsInstances.removeAll(os);
	}
  
}

