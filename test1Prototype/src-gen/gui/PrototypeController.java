package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gui.supportclass.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import services.*;
import services.impl.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

import entities.*;

public class PrototypeController implements Initializable {


	DateTimeFormatter dateformatter;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		test1system_service = ServiceManager.createTest1System();
		thirdpartyservices_service = ServiceManager.createThirdPartyServices();
		userloginservice_service = ServiceManager.createUserLoginService();
		signupservice_service = ServiceManager.createSignupService();
		submitqualifyapplicationservice_service = ServiceManager.createSubmitQualifyApplicationService();
		adminloginservice_service = ServiceManager.createAdminLoginService();
		reviewuserapplicationservice_service = ServiceManager.createReviewUserApplicationService();
		addnewproductservice_service = ServiceManager.createAddNewProductService();
		viewselfordersservice_service = ServiceManager.createViewSelfOrdersService();
		promotionsalesystem_service = ServiceManager.createPromotionSaleSystem();
				
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	   	 //prepare data for contract
	   	 prepareData();
	   	 
	   	 //generate invariant panel
	   	 genereateInvairantPanel();
	   	 
		 //Actor Threeview Binding
		 actorTreeViewBinding();
		 
		 //Generate
		 generatOperationPane();
		 genereateOpInvariantPanel();
		 
		 //prilimariry data
		 try {
			DataFitService.fit();
		 } catch (PreconditionException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
		 
		 //generate class statistic
		 classStatisicBingding();
		 
		 //generate object statistic
		 generateObjectTable();
		 
		 //genereate association statistic
		 associationStatisicBingding();

		 //set listener 
		 setListeners();
	}
	
	/**
	 * deepCopyforTreeItem (Actor Generation)
	 */
	TreeItem<String> deepCopyTree(TreeItem<String> item) {
		    TreeItem<String> copy = new TreeItem<String>(item.getValue());
		    for (TreeItem<String> child : item.getChildren()) {
		        copy.getChildren().add(deepCopyTree(child));
		    }
		    return copy;
	}
	
	/**
	 * check all invariant and update invariant panel
	 */
	public void invairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}				
			}
			
			for (Entry<String, Label> inv : service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * check op invariant and update op invariant panel
	 */		
	public void opInvairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : op_entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
			for (Entry<String, Label> inv : op_service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	*	generate op invariant panel 
	*/
	public void genereateOpInvariantPanel() {
		
		opInvariantPanel = new HashMap<String, VBox>();
		op_entity_invariants_label_map = new LinkedHashMap<String, Label>();
		op_service_invariants_label_map = new LinkedHashMap<String, Label>();
		
		VBox v;
		List<String> entities;
		v = new VBox();
		
		//entities invariants
		entities = UserLoginServiceImpl.opINVRelatedEntity.get("fillUsernameAndPassword");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillUsernameAndPassword" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("UserLoginService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillUsernameAndPassword", v);
		
		v = new VBox();
		
		//entities invariants
		entities = UserLoginServiceImpl.opINVRelatedEntity.get("callForLogin");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("callForLogin" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("UserLoginService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("callForLogin", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AdminLoginServiceImpl.opINVRelatedEntity.get("fillAdminnameAndPassword");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillAdminnameAndPassword" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AdminLoginService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillAdminnameAndPassword", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AdminLoginServiceImpl.opINVRelatedEntity.get("callForLogin");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("callForLogin" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AdminLoginService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("callForLogin", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SignupServiceImpl.opINVRelatedEntity.get("fillAccountDetail");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillAccountDetail" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SignupService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillAccountDetail", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SubmitQualifyApplicationServiceImpl.opINVRelatedEntity.get("fillAdvancedProfile");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillAdvancedProfile" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SubmitQualifyApplicationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillAdvancedProfile", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ReviewUserApplicationServiceImpl.opINVRelatedEntity.get("getUserApplicationsList");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getUserApplicationsList" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ReviewUserApplicationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getUserApplicationsList", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ReviewUserApplicationServiceImpl.opINVRelatedEntity.get("fillResultAndDesc");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillResultAndDesc" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ReviewUserApplicationService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillResultAndDesc", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AddNewProductServiceImpl.opINVRelatedEntity.get("callToCreateProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("callToCreateProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AddNewProductService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("callToCreateProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AddNewProductServiceImpl.opINVRelatedEntity.get("addProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("addProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AddNewProductService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("addProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = AddNewProductServiceImpl.opINVRelatedEntity.get("fillProductDetail");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("fillProductDetail" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("AddNewProductService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("fillProductDetail", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ViewSelfOrdersServiceImpl.opINVRelatedEntity.get("getOrderDetail");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("getOrderDetail" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ViewSelfOrdersService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("getOrderDetail", v);
		
		
	}
	
	
	/*
	*  generate invariant panel
	*/
	public void genereateInvairantPanel() {
		
		service_invariants_label_map = new LinkedHashMap<String, Label>();
		entity_invariants_label_map = new LinkedHashMap<String, Label>();
		
		//entity_invariants_map
		VBox v = new VBox();
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			Label l = new Label(inv.getKey());
			l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			service_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		//entity invariants
		for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
			
			String INVname = inv.getKey();
			Label l = new Label(INVname);
			if (INVname.contains("AssociationInvariants")) {
				l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #099b17 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			} else {
				l.setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
			}	
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			entity_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		ScrollPane scrollPane = new ScrollPane(v);
		scrollPane.setFitToWidth(true);
		all_invariant_pane.setMaxHeight(850);
		
		all_invariant_pane.setContent(scrollPane);
	}	
	
	
	
	/* 
	*	mainPane add listener
	*/
	public void setListeners() {
		 mainPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			 
			 	if (newTab.getText().equals("System State")) {
			 		System.out.println("refresh all");
			 		refreshAll();
			 	}
		    
		    });
	}
	
	
	//checking all invariants
	public void checkAllInvariants() {
		
		invairantPanelUpdate();
	
	}	
	
	//refresh all
	public void refreshAll() {
		
		invairantPanelUpdate();
		classStatisticUpdate();
		generateObjectTable();
	}
	
	
	//update association
	public void updateAssociation(String className) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber();
		}
		
	}
	
	public void updateAssociation(String className, int index) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber(index);
		}
		
	}	
	
	public void generateObjectTable() {
		
		allObjectTables = new LinkedHashMap<String, TableView>();
		
		TableView<Map<String, String>> tableProfile = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableProfile_Name = new TableColumn<Map<String, String>, String>("Name");
		tableProfile_Name.setMinWidth("Name".length()*10);
		tableProfile_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_Name);
		TableColumn<Map<String, String>, String> tableProfile_Phone = new TableColumn<Map<String, String>, String>("Phone");
		tableProfile_Phone.setMinWidth("Phone".length()*10);
		tableProfile_Phone.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Phone"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_Phone);
		TableColumn<Map<String, String>, String> tableProfile_IdCard = new TableColumn<Map<String, String>, String>("IdCard");
		tableProfile_IdCard.setMinWidth("IdCard".length()*10);
		tableProfile_IdCard.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IdCard"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_IdCard);
		TableColumn<Map<String, String>, String> tableProfile_Sex = new TableColumn<Map<String, String>, String>("Sex");
		tableProfile_Sex.setMinWidth("Sex".length()*10);
		tableProfile_Sex.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Sex"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_Sex);
		TableColumn<Map<String, String>, String> tableProfile_BankCard = new TableColumn<Map<String, String>, String>("BankCard");
		tableProfile_BankCard.setMinWidth("BankCard".length()*10);
		tableProfile_BankCard.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("BankCard"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_BankCard);
		TableColumn<Map<String, String>, String> tableProfile_WorkCompany = new TableColumn<Map<String, String>, String>("WorkCompany");
		tableProfile_WorkCompany.setMinWidth("WorkCompany".length()*10);
		tableProfile_WorkCompany.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("WorkCompany"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_WorkCompany);
		TableColumn<Map<String, String>, String> tableProfile_Address = new TableColumn<Map<String, String>, String>("Address");
		tableProfile_Address.setMinWidth("Address".length()*10);
		tableProfile_Address.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Address"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_Address);
		TableColumn<Map<String, String>, String> tableProfile_Birthday = new TableColumn<Map<String, String>, String>("Birthday");
		tableProfile_Birthday.setMinWidth("Birthday".length()*10);
		tableProfile_Birthday.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Birthday"));
		    }
		});	
		tableProfile.getColumns().add(tableProfile_Birthday);
		
		//table data
		ObservableList<Map<String, String>> dataProfile = FXCollections.observableArrayList();
		List<Profile> rsProfile = EntityManager.getAllInstancesOf("Profile");
		for (Profile r : rsProfile) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getPhone() != null)
				unit.put("Phone", String.valueOf(r.getPhone()));
			else
				unit.put("Phone", "");
			if (r.getIdCard() != null)
				unit.put("IdCard", String.valueOf(r.getIdCard()));
			else
				unit.put("IdCard", "");
			unit.put("Sex", String.valueOf(r.getSex()));
			if (r.getBankCard() != null)
				unit.put("BankCard", String.valueOf(r.getBankCard()));
			else
				unit.put("BankCard", "");
			if (r.getWorkCompany() != null)
				unit.put("WorkCompany", String.valueOf(r.getWorkCompany()));
			else
				unit.put("WorkCompany", "");
			if (r.getAddress() != null)
				unit.put("Address", String.valueOf(r.getAddress()));
			else
				unit.put("Address", "");
			if (r.getBirthday() != null)
				unit.put("Birthday", r.getBirthday().format(dateformatter));
			else
				unit.put("Birthday", "");

			dataProfile.add(unit);
		}
		
		tableProfile.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableProfile.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Profile", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableProfile.setItems(dataProfile);
		allObjectTables.put("Profile", tableProfile);
		
		TableView<Map<String, String>> tableUser = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableUser_Username = new TableColumn<Map<String, String>, String>("Username");
		tableUser_Username.setMinWidth("Username".length()*10);
		tableUser_Username.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Username"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Username);
		TableColumn<Map<String, String>, String> tableUser_Paasword = new TableColumn<Map<String, String>, String>("Paasword");
		tableUser_Paasword.setMinWidth("Paasword".length()*10);
		tableUser_Paasword.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Paasword"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Paasword);
		TableColumn<Map<String, String>, String> tableUser_IsQualify = new TableColumn<Map<String, String>, String>("IsQualify");
		tableUser_IsQualify.setMinWidth("IsQualify".length()*10);
		tableUser_IsQualify.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsQualify"));
		    }
		});	
		tableUser.getColumns().add(tableUser_IsQualify);
		TableColumn<Map<String, String>, String> tableUser_SignupDate = new TableColumn<Map<String, String>, String>("SignupDate");
		tableUser_SignupDate.setMinWidth("SignupDate".length()*10);
		tableUser_SignupDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SignupDate"));
		    }
		});	
		tableUser.getColumns().add(tableUser_SignupDate);
		TableColumn<Map<String, String>, String> tableUser_UserId = new TableColumn<Map<String, String>, String>("UserId");
		tableUser_UserId.setMinWidth("UserId".length()*10);
		tableUser_UserId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserId"));
		    }
		});	
		tableUser.getColumns().add(tableUser_UserId);
		
		//table data
		ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
		List<User> rsUser = EntityManager.getAllInstancesOf("User");
		for (User r : rsUser) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getUsername() != null)
				unit.put("Username", String.valueOf(r.getUsername()));
			else
				unit.put("Username", "");
			if (r.getPaasword() != null)
				unit.put("Paasword", String.valueOf(r.getPaasword()));
			else
				unit.put("Paasword", "");
			unit.put("IsQualify", String.valueOf(r.getIsQualify()));
			if (r.getSignupDate() != null)
				unit.put("SignupDate", r.getSignupDate().format(dateformatter));
			else
				unit.put("SignupDate", "");
			unit.put("UserId", String.valueOf(r.getUserId()));

			dataUser.add(unit);
		}
		
		tableUser.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableUser.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("User", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableUser.setItems(dataUser);
		allObjectTables.put("User", tableUser);
		
		TableView<Map<String, String>> tableQualifiedUser = new TableView<Map<String, String>>();

		//super entity attribute column
		TableColumn<Map<String, String>, String> tableQualifiedUser_Username = new TableColumn<Map<String, String>, String>("Username");
		tableQualifiedUser_Username.setMinWidth("Username".length()*10);
		tableQualifiedUser_Username.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Username"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_Username);
		TableColumn<Map<String, String>, String> tableQualifiedUser_Paasword = new TableColumn<Map<String, String>, String>("Paasword");
		tableQualifiedUser_Paasword.setMinWidth("Paasword".length()*10);
		tableQualifiedUser_Paasword.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Paasword"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_Paasword);
		TableColumn<Map<String, String>, String> tableQualifiedUser_IsQualify = new TableColumn<Map<String, String>, String>("IsQualify");
		tableQualifiedUser_IsQualify.setMinWidth("IsQualify".length()*10);
		tableQualifiedUser_IsQualify.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsQualify"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_IsQualify);
		TableColumn<Map<String, String>, String> tableQualifiedUser_SignupDate = new TableColumn<Map<String, String>, String>("SignupDate");
		tableQualifiedUser_SignupDate.setMinWidth("SignupDate".length()*10);
		tableQualifiedUser_SignupDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SignupDate"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_SignupDate);
		TableColumn<Map<String, String>, String> tableQualifiedUser_UserId = new TableColumn<Map<String, String>, String>("UserId");
		tableQualifiedUser_UserId.setMinWidth("UserId".length()*10);
		tableQualifiedUser_UserId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserId"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_UserId);
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableQualifiedUser_QualifiedDate = new TableColumn<Map<String, String>, String>("QualifiedDate");
		tableQualifiedUser_QualifiedDate.setMinWidth("QualifiedDate".length()*10);
		tableQualifiedUser_QualifiedDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("QualifiedDate"));
		    }
		});	
		tableQualifiedUser.getColumns().add(tableQualifiedUser_QualifiedDate);
		
		//table data
		ObservableList<Map<String, String>> dataQualifiedUser = FXCollections.observableArrayList();
		List<QualifiedUser> rsQualifiedUser = EntityManager.getAllInstancesOf("QualifiedUser");
		for (QualifiedUser r : rsQualifiedUser) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			if (r.getUsername() != null)
				unit.put("Username", String.valueOf(r.getUsername()));
			else
				unit.put("Username", "");
			if (r.getPaasword() != null)
				unit.put("Paasword", String.valueOf(r.getPaasword()));
			else
				unit.put("Paasword", "");
			unit.put("IsQualify", String.valueOf(r.getIsQualify()));
			if (r.getSignupDate() != null)
				unit.put("SignupDate", r.getSignupDate().format(dateformatter));
			else
				unit.put("SignupDate", "");
			unit.put("UserId", String.valueOf(r.getUserId()));
			
			if (r.getQualifiedDate() != null)
				unit.put("QualifiedDate", r.getQualifiedDate().format(dateformatter));
			else
				unit.put("QualifiedDate", "");

			dataQualifiedUser.add(unit);
		}
		
		tableQualifiedUser.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableQualifiedUser.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("QualifiedUser", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableQualifiedUser.setItems(dataQualifiedUser);
		allObjectTables.put("QualifiedUser", tableQualifiedUser);
		
		TableView<Map<String, String>> tableAdmin = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableAdmin_AdminId = new TableColumn<Map<String, String>, String>("AdminId");
		tableAdmin_AdminId.setMinWidth("AdminId".length()*10);
		tableAdmin_AdminId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("AdminId"));
		    }
		});	
		tableAdmin.getColumns().add(tableAdmin_AdminId);
		TableColumn<Map<String, String>, String> tableAdmin_AdminName = new TableColumn<Map<String, String>, String>("AdminName");
		tableAdmin_AdminName.setMinWidth("AdminName".length()*10);
		tableAdmin_AdminName.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("AdminName"));
		    }
		});	
		tableAdmin.getColumns().add(tableAdmin_AdminName);
		
		//table data
		ObservableList<Map<String, String>> dataAdmin = FXCollections.observableArrayList();
		List<Admin> rsAdmin = EntityManager.getAllInstancesOf("Admin");
		for (Admin r : rsAdmin) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("AdminId", String.valueOf(r.getAdminId()));
			if (r.getAdminName() != null)
				unit.put("AdminName", String.valueOf(r.getAdminName()));
			else
				unit.put("AdminName", "");

			dataAdmin.add(unit);
		}
		
		tableAdmin.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableAdmin.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Admin", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableAdmin.setItems(dataAdmin);
		allObjectTables.put("Admin", tableAdmin);
		
		TableView<Map<String, String>> tableProduct = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableProduct_Id = new TableColumn<Map<String, String>, String>("Id");
		tableProduct_Id.setMinWidth("Id".length()*10);
		tableProduct_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_Id);
		TableColumn<Map<String, String>, String> tableProduct_Name = new TableColumn<Map<String, String>, String>("Name");
		tableProduct_Name.setMinWidth("Name".length()*10);
		tableProduct_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_Name);
		TableColumn<Map<String, String>, String> tableProduct_CreateDate = new TableColumn<Map<String, String>, String>("CreateDate");
		tableProduct_CreateDate.setMinWidth("CreateDate".length()*10);
		tableProduct_CreateDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CreateDate"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_CreateDate);
		TableColumn<Map<String, String>, String> tableProduct_IsOnSale = new TableColumn<Map<String, String>, String>("IsOnSale");
		tableProduct_IsOnSale.setMinWidth("IsOnSale".length()*10);
		tableProduct_IsOnSale.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsOnSale"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_IsOnSale);
		TableColumn<Map<String, String>, String> tableProduct_TotalCount = new TableColumn<Map<String, String>, String>("TotalCount");
		tableProduct_TotalCount.setMinWidth("TotalCount".length()*10);
		tableProduct_TotalCount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TotalCount"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_TotalCount);
		TableColumn<Map<String, String>, String> tableProduct_CurrentCount = new TableColumn<Map<String, String>, String>("CurrentCount");
		tableProduct_CurrentCount.setMinWidth("CurrentCount".length()*10);
		tableProduct_CurrentCount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CurrentCount"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_CurrentCount);
		TableColumn<Map<String, String>, String> tableProduct_OriginPrice = new TableColumn<Map<String, String>, String>("OriginPrice");
		tableProduct_OriginPrice.setMinWidth("OriginPrice".length()*10);
		tableProduct_OriginPrice.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OriginPrice"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_OriginPrice);
		
		//table data
		ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
		List<Product> rsProduct = EntityManager.getAllInstancesOf("Product");
		for (Product r : rsProduct) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getCreateDate() != null)
				unit.put("CreateDate", r.getCreateDate().format(dateformatter));
			else
				unit.put("CreateDate", "");
			unit.put("IsOnSale", String.valueOf(r.getIsOnSale()));
			unit.put("TotalCount", String.valueOf(r.getTotalCount()));
			unit.put("CurrentCount", String.valueOf(r.getCurrentCount()));
			unit.put("OriginPrice", String.valueOf(r.getOriginPrice()));

			dataProduct.add(unit);
		}
		
		tableProduct.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableProduct.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Product", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableProduct.setItems(dataProduct);
		allObjectTables.put("Product", tableProduct);
		
		TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableOrder_Id = new TableColumn<Map<String, String>, String>("Id");
		tableOrder_Id.setMinWidth("Id".length()*10);
		tableOrder_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_Id);
		
		//table data
		ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
		List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
		for (Order r : rsOrder) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));

			dataOrder.add(unit);
		}
		
		tableOrder.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableOrder.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Order", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableOrder.setItems(dataOrder);
		allObjectTables.put("Order", tableOrder);
		
		TableView<Map<String, String>> tableApplication = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableApplication_Id = new TableColumn<Map<String, String>, String>("Id");
		tableApplication_Id.setMinWidth("Id".length()*10);
		tableApplication_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableApplication.getColumns().add(tableApplication_Id);
		TableColumn<Map<String, String>, String> tableApplication_CreateDate = new TableColumn<Map<String, String>, String>("CreateDate");
		tableApplication_CreateDate.setMinWidth("CreateDate".length()*10);
		tableApplication_CreateDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CreateDate"));
		    }
		});	
		tableApplication.getColumns().add(tableApplication_CreateDate);
		TableColumn<Map<String, String>, String> tableApplication_State = new TableColumn<Map<String, String>, String>("State");
		tableApplication_State.setMinWidth("State".length()*10);
		tableApplication_State.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("State"));
		    }
		});	
		tableApplication.getColumns().add(tableApplication_State);
		
		//table data
		ObservableList<Map<String, String>> dataApplication = FXCollections.observableArrayList();
		List<Application> rsApplication = EntityManager.getAllInstancesOf("Application");
		for (Application r : rsApplication) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getCreateDate() != null)
				unit.put("CreateDate", r.getCreateDate().format(dateformatter));
			else
				unit.put("CreateDate", "");
			if (r.getState() != null)
				unit.put("State", String.valueOf(r.getState()));
			else
				unit.put("State", "");

			dataApplication.add(unit);
		}
		
		tableApplication.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableApplication.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Application", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableApplication.setItems(dataApplication);
		allObjectTables.put("Application", tableApplication);
		
		TableView<Map<String, String>> tableSealPromotions = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableSealPromotions_PromotionRate = new TableColumn<Map<String, String>, String>("PromotionRate");
		tableSealPromotions_PromotionRate.setMinWidth("PromotionRate".length()*10);
		tableSealPromotions_PromotionRate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PromotionRate"));
		    }
		});	
		tableSealPromotions.getColumns().add(tableSealPromotions_PromotionRate);
		TableColumn<Map<String, String>, String> tableSealPromotions_BeginSaleDate = new TableColumn<Map<String, String>, String>("BeginSaleDate");
		tableSealPromotions_BeginSaleDate.setMinWidth("BeginSaleDate".length()*10);
		tableSealPromotions_BeginSaleDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("BeginSaleDate"));
		    }
		});	
		tableSealPromotions.getColumns().add(tableSealPromotions_BeginSaleDate);
		TableColumn<Map<String, String>, String> tableSealPromotions_CreateDate = new TableColumn<Map<String, String>, String>("CreateDate");
		tableSealPromotions_CreateDate.setMinWidth("CreateDate".length()*10);
		tableSealPromotions_CreateDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CreateDate"));
		    }
		});	
		tableSealPromotions.getColumns().add(tableSealPromotions_CreateDate);
		TableColumn<Map<String, String>, String> tableSealPromotions_EndSaleDate = new TableColumn<Map<String, String>, String>("EndSaleDate");
		tableSealPromotions_EndSaleDate.setMinWidth("EndSaleDate".length()*10);
		tableSealPromotions_EndSaleDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EndSaleDate"));
		    }
		});	
		tableSealPromotions.getColumns().add(tableSealPromotions_EndSaleDate);
		
		//table data
		ObservableList<Map<String, String>> dataSealPromotions = FXCollections.observableArrayList();
		List<SealPromotions> rsSealPromotions = EntityManager.getAllInstancesOf("SealPromotions");
		for (SealPromotions r : rsSealPromotions) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("PromotionRate", String.valueOf(r.getPromotionRate()));
			if (r.getBeginSaleDate() != null)
				unit.put("BeginSaleDate", r.getBeginSaleDate().format(dateformatter));
			else
				unit.put("BeginSaleDate", "");
			if (r.getCreateDate() != null)
				unit.put("CreateDate", r.getCreateDate().format(dateformatter));
			else
				unit.put("CreateDate", "");
			if (r.getEndSaleDate() != null)
				unit.put("EndSaleDate", r.getEndSaleDate().format(dateformatter));
			else
				unit.put("EndSaleDate", "");

			dataSealPromotions.add(unit);
		}
		
		tableSealPromotions.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableSealPromotions.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("SealPromotions", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableSealPromotions.setItems(dataSealPromotions);
		allObjectTables.put("SealPromotions", tableSealPromotions);
		

		
	}
	
	/* 
	* update all object tables with sub dataset
	*/ 
	public void updateProfileTable(List<Profile> rsProfile) {
			ObservableList<Map<String, String>> dataProfile = FXCollections.observableArrayList();
			for (Profile r : rsProfile) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPhone() != null)
					unit.put("Phone", String.valueOf(r.getPhone()));
				else
					unit.put("Phone", "");
				if (r.getIdCard() != null)
					unit.put("IdCard", String.valueOf(r.getIdCard()));
				else
					unit.put("IdCard", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getBankCard() != null)
					unit.put("BankCard", String.valueOf(r.getBankCard()));
				else
					unit.put("BankCard", "");
				if (r.getWorkCompany() != null)
					unit.put("WorkCompany", String.valueOf(r.getWorkCompany()));
				else
					unit.put("WorkCompany", "");
				if (r.getAddress() != null)
					unit.put("Address", String.valueOf(r.getAddress()));
				else
					unit.put("Address", "");
				if (r.getBirthday() != null)
					unit.put("Birthday", r.getBirthday().format(dateformatter));
				else
					unit.put("Birthday", "");
				dataProfile.add(unit);
			}
			
			allObjectTables.get("Profile").setItems(dataProfile);
	}
	public void updateUserTable(List<User> rsUser) {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getUsername() != null)
					unit.put("Username", String.valueOf(r.getUsername()));
				else
					unit.put("Username", "");
				if (r.getPaasword() != null)
					unit.put("Paasword", String.valueOf(r.getPaasword()));
				else
					unit.put("Paasword", "");
				unit.put("IsQualify", String.valueOf(r.getIsQualify()));
				if (r.getSignupDate() != null)
					unit.put("SignupDate", r.getSignupDate().format(dateformatter));
				else
					unit.put("SignupDate", "");
				unit.put("UserId", String.valueOf(r.getUserId()));
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateQualifiedUserTable(List<QualifiedUser> rsQualifiedUser) {
			ObservableList<Map<String, String>> dataQualifiedUser = FXCollections.observableArrayList();
			for (QualifiedUser r : rsQualifiedUser) {
				Map<String, String> unit = new HashMap<String, String>();
				
				if (r.getUsername() != null)
					unit.put("Username", String.valueOf(r.getUsername()));
				else
					unit.put("Username", "");
				if (r.getPaasword() != null)
					unit.put("Paasword", String.valueOf(r.getPaasword()));
				else
					unit.put("Paasword", "");
				unit.put("IsQualify", String.valueOf(r.getIsQualify()));
				if (r.getSignupDate() != null)
					unit.put("SignupDate", r.getSignupDate().format(dateformatter));
				else
					unit.put("SignupDate", "");
				unit.put("UserId", String.valueOf(r.getUserId()));
				
				if (r.getQualifiedDate() != null)
					unit.put("QualifiedDate", r.getQualifiedDate().format(dateformatter));
				else
					unit.put("QualifiedDate", "");
				dataQualifiedUser.add(unit);
			}
			
			allObjectTables.get("QualifiedUser").setItems(dataQualifiedUser);
	}
	public void updateAdminTable(List<Admin> rsAdmin) {
			ObservableList<Map<String, String>> dataAdmin = FXCollections.observableArrayList();
			for (Admin r : rsAdmin) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("AdminId", String.valueOf(r.getAdminId()));
				if (r.getAdminName() != null)
					unit.put("AdminName", String.valueOf(r.getAdminName()));
				else
					unit.put("AdminName", "");
				dataAdmin.add(unit);
			}
			
			allObjectTables.get("Admin").setItems(dataAdmin);
	}
	public void updateProductTable(List<Product> rsProduct) {
			ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
			for (Product r : rsProduct) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				unit.put("IsOnSale", String.valueOf(r.getIsOnSale()));
				unit.put("TotalCount", String.valueOf(r.getTotalCount()));
				unit.put("CurrentCount", String.valueOf(r.getCurrentCount()));
				unit.put("OriginPrice", String.valueOf(r.getOriginPrice()));
				dataProduct.add(unit);
			}
			
			allObjectTables.get("Product").setItems(dataProduct);
	}
	public void updateOrderTable(List<Order> rsOrder) {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateApplicationTable(List<Application> rsApplication) {
			ObservableList<Map<String, String>> dataApplication = FXCollections.observableArrayList();
			for (Application r : rsApplication) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				if (r.getState() != null)
					unit.put("State", String.valueOf(r.getState()));
				else
					unit.put("State", "");
				dataApplication.add(unit);
			}
			
			allObjectTables.get("Application").setItems(dataApplication);
	}
	public void updateSealPromotionsTable(List<SealPromotions> rsSealPromotions) {
			ObservableList<Map<String, String>> dataSealPromotions = FXCollections.observableArrayList();
			for (SealPromotions r : rsSealPromotions) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("PromotionRate", String.valueOf(r.getPromotionRate()));
				if (r.getBeginSaleDate() != null)
					unit.put("BeginSaleDate", r.getBeginSaleDate().format(dateformatter));
				else
					unit.put("BeginSaleDate", "");
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				if (r.getEndSaleDate() != null)
					unit.put("EndSaleDate", r.getEndSaleDate().format(dateformatter));
				else
					unit.put("EndSaleDate", "");
				dataSealPromotions.add(unit);
			}
			
			allObjectTables.get("SealPromotions").setItems(dataSealPromotions);
	}
	
	/* 
	* update all object tables
	*/ 
	public void updateProfileTable() {
			ObservableList<Map<String, String>> dataProfile = FXCollections.observableArrayList();
			List<Profile> rsProfile = EntityManager.getAllInstancesOf("Profile");
			for (Profile r : rsProfile) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPhone() != null)
					unit.put("Phone", String.valueOf(r.getPhone()));
				else
					unit.put("Phone", "");
				if (r.getIdCard() != null)
					unit.put("IdCard", String.valueOf(r.getIdCard()));
				else
					unit.put("IdCard", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getBankCard() != null)
					unit.put("BankCard", String.valueOf(r.getBankCard()));
				else
					unit.put("BankCard", "");
				if (r.getWorkCompany() != null)
					unit.put("WorkCompany", String.valueOf(r.getWorkCompany()));
				else
					unit.put("WorkCompany", "");
				if (r.getAddress() != null)
					unit.put("Address", String.valueOf(r.getAddress()));
				else
					unit.put("Address", "");
				if (r.getBirthday() != null)
					unit.put("Birthday", r.getBirthday().format(dateformatter));
				else
					unit.put("Birthday", "");
				dataProfile.add(unit);
			}
			
			allObjectTables.get("Profile").setItems(dataProfile);
	}
	public void updateUserTable() {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			List<User> rsUser = EntityManager.getAllInstancesOf("User");
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getUsername() != null)
					unit.put("Username", String.valueOf(r.getUsername()));
				else
					unit.put("Username", "");
				if (r.getPaasword() != null)
					unit.put("Paasword", String.valueOf(r.getPaasword()));
				else
					unit.put("Paasword", "");
				unit.put("IsQualify", String.valueOf(r.getIsQualify()));
				if (r.getSignupDate() != null)
					unit.put("SignupDate", r.getSignupDate().format(dateformatter));
				else
					unit.put("SignupDate", "");
				unit.put("UserId", String.valueOf(r.getUserId()));
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateQualifiedUserTable() {
			ObservableList<Map<String, String>> dataQualifiedUser = FXCollections.observableArrayList();
			List<QualifiedUser> rsQualifiedUser = EntityManager.getAllInstancesOf("QualifiedUser");
			for (QualifiedUser r : rsQualifiedUser) {
				Map<String, String> unit = new HashMap<String, String>();

				if (r.getUsername() != null)
					unit.put("Username", String.valueOf(r.getUsername()));
				else
					unit.put("Username", "");
				if (r.getPaasword() != null)
					unit.put("Paasword", String.valueOf(r.getPaasword()));
				else
					unit.put("Paasword", "");
				unit.put("IsQualify", String.valueOf(r.getIsQualify()));
				if (r.getSignupDate() != null)
					unit.put("SignupDate", r.getSignupDate().format(dateformatter));
				else
					unit.put("SignupDate", "");
				unit.put("UserId", String.valueOf(r.getUserId()));

				if (r.getQualifiedDate() != null)
					unit.put("QualifiedDate", r.getQualifiedDate().format(dateformatter));
				else
					unit.put("QualifiedDate", "");
				dataQualifiedUser.add(unit);
			}
			
			allObjectTables.get("QualifiedUser").setItems(dataQualifiedUser);
	}
	public void updateAdminTable() {
			ObservableList<Map<String, String>> dataAdmin = FXCollections.observableArrayList();
			List<Admin> rsAdmin = EntityManager.getAllInstancesOf("Admin");
			for (Admin r : rsAdmin) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("AdminId", String.valueOf(r.getAdminId()));
				if (r.getAdminName() != null)
					unit.put("AdminName", String.valueOf(r.getAdminName()));
				else
					unit.put("AdminName", "");
				dataAdmin.add(unit);
			}
			
			allObjectTables.get("Admin").setItems(dataAdmin);
	}
	public void updateProductTable() {
			ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
			List<Product> rsProduct = EntityManager.getAllInstancesOf("Product");
			for (Product r : rsProduct) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				unit.put("IsOnSale", String.valueOf(r.getIsOnSale()));
				unit.put("TotalCount", String.valueOf(r.getTotalCount()));
				unit.put("CurrentCount", String.valueOf(r.getCurrentCount()));
				unit.put("OriginPrice", String.valueOf(r.getOriginPrice()));
				dataProduct.add(unit);
			}
			
			allObjectTables.get("Product").setItems(dataProduct);
	}
	public void updateOrderTable() {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateApplicationTable() {
			ObservableList<Map<String, String>> dataApplication = FXCollections.observableArrayList();
			List<Application> rsApplication = EntityManager.getAllInstancesOf("Application");
			for (Application r : rsApplication) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				if (r.getState() != null)
					unit.put("State", String.valueOf(r.getState()));
				else
					unit.put("State", "");
				dataApplication.add(unit);
			}
			
			allObjectTables.get("Application").setItems(dataApplication);
	}
	public void updateSealPromotionsTable() {
			ObservableList<Map<String, String>> dataSealPromotions = FXCollections.observableArrayList();
			List<SealPromotions> rsSealPromotions = EntityManager.getAllInstancesOf("SealPromotions");
			for (SealPromotions r : rsSealPromotions) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("PromotionRate", String.valueOf(r.getPromotionRate()));
				if (r.getBeginSaleDate() != null)
					unit.put("BeginSaleDate", r.getBeginSaleDate().format(dateformatter));
				else
					unit.put("BeginSaleDate", "");
				if (r.getCreateDate() != null)
					unit.put("CreateDate", r.getCreateDate().format(dateformatter));
				else
					unit.put("CreateDate", "");
				if (r.getEndSaleDate() != null)
					unit.put("EndSaleDate", r.getEndSaleDate().format(dateformatter));
				else
					unit.put("EndSaleDate", "");
				dataSealPromotions.add(unit);
			}
			
			allObjectTables.get("SealPromotions").setItems(dataSealPromotions);
	}
	
	public void classStatisicBingding() {
	
		 classInfodata = FXCollections.observableArrayList();
	 	 profile = new ClassInfo("Profile", EntityManager.getAllInstancesOf("Profile").size());
	 	 classInfodata.add(profile);
	 	 user = new ClassInfo("User", EntityManager.getAllInstancesOf("User").size());
	 	 classInfodata.add(user);
	 	 qualifieduser = new ClassInfo("QualifiedUser", EntityManager.getAllInstancesOf("QualifiedUser").size());
	 	 classInfodata.add(qualifieduser);
	 	 admin = new ClassInfo("Admin", EntityManager.getAllInstancesOf("Admin").size());
	 	 classInfodata.add(admin);
	 	 product = new ClassInfo("Product", EntityManager.getAllInstancesOf("Product").size());
	 	 classInfodata.add(product);
	 	 order = new ClassInfo("Order", EntityManager.getAllInstancesOf("Order").size());
	 	 classInfodata.add(order);
	 	 application = new ClassInfo("Application", EntityManager.getAllInstancesOf("Application").size());
	 	 classInfodata.add(application);
	 	 sealpromotions = new ClassInfo("SealPromotions", EntityManager.getAllInstancesOf("SealPromotions").size());
	 	 classInfodata.add(sealpromotions);
	 	 
		 class_statisic.setItems(classInfodata);
		 
		 //Class Statisic Binding
		 class_statisic.getSelectionModel().selectedItemProperty().addListener(
				 (observable, oldValue, newValue) ->  { 
				 										 //no selected object in table
				 										 objectindex = -1;
				 										 
				 										 //get lastest data, reflect updateTableData method
				 										 try {
												 			 Method updateob = this.getClass().getMethod("update" + newValue.getName() + "Table", null);
												 			 updateob.invoke(this);			 
												 		 } catch (Exception e) {
												 		 	 e.printStackTrace();
												 		 }		 										 
				 	
				 										 //show object table
				 			 				 			 TableView obs = allObjectTables.get(newValue.getName());
				 			 				 			 if (obs != null) {
				 			 				 				object_statics.setContent(obs);
				 			 				 				object_statics.setText("All Objects " + newValue.getName() + ":");
				 			 				 			 }
				 			 				 			 
				 			 				 			 //update association information
							 			 				 updateAssociation(newValue.getName());
				 			 				 			 
				 			 				 			 //show association information
				 			 				 			 ObservableList<AssociationInfo> asso = allassociationData.get(newValue.getName());
				 			 				 			 if (asso != null) {
				 			 				 			 	association_statisic.setItems(asso);
				 			 				 			 }
				 			 				 		  });
	}
	
	public void classStatisticUpdate() {
	 	 profile.setNumber(EntityManager.getAllInstancesOf("Profile").size());
	 	 user.setNumber(EntityManager.getAllInstancesOf("User").size());
	 	 qualifieduser.setNumber(EntityManager.getAllInstancesOf("QualifiedUser").size());
	 	 admin.setNumber(EntityManager.getAllInstancesOf("Admin").size());
	 	 product.setNumber(EntityManager.getAllInstancesOf("Product").size());
	 	 order.setNumber(EntityManager.getAllInstancesOf("Order").size());
	 	 application.setNumber(EntityManager.getAllInstancesOf("Application").size());
	 	 sealpromotions.setNumber(EntityManager.getAllInstancesOf("SealPromotions").size());
		
	}
	
	/**
	 * association binding
	 */
	public void associationStatisicBingding() {
		
		allassociationData = new HashMap<String, ObservableList<AssociationInfo>>();
		
		ObservableList<AssociationInfo> Profile_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Profile", Profile_association_data);
		
		ObservableList<AssociationInfo> User_association_data = FXCollections.observableArrayList();
		AssociationInfo User_associatition_UsertoProfile = new AssociationInfo("User", "Profile", "UsertoProfile", true);
		User_association_data.add(User_associatition_UsertoProfile);
		AssociationInfo User_associatition_UsertoOrder = new AssociationInfo("User", "Order", "UsertoOrder", true);
		User_association_data.add(User_associatition_UsertoOrder);
		
		allassociationData.put("User", User_association_data);
		
		ObservableList<AssociationInfo> QualifiedUser_association_data = FXCollections.observableArrayList();
		AssociationInfo QualifiedUser_associatition_QualifiedUsertoApplication = new AssociationInfo("QualifiedUser", "Application", "QualifiedUsertoApplication", true);
		QualifiedUser_association_data.add(QualifiedUser_associatition_QualifiedUsertoApplication);
		
		allassociationData.put("QualifiedUser", QualifiedUser_association_data);
		
		ObservableList<AssociationInfo> Admin_association_data = FXCollections.observableArrayList();
		AssociationInfo Admin_associatition_AdmintoQualifiedUser = new AssociationInfo("Admin", "QualifiedUser", "AdmintoQualifiedUser", true);
		Admin_association_data.add(Admin_associatition_AdmintoQualifiedUser);
		AssociationInfo Admin_associatition_AdmintoApplication = new AssociationInfo("Admin", "Application", "AdmintoApplication", true);
		Admin_association_data.add(Admin_associatition_AdmintoApplication);
		AssociationInfo Admin_associatition_AdmintoSealPromotions = new AssociationInfo("Admin", "SealPromotions", "AdmintoSealPromotions", true);
		Admin_association_data.add(Admin_associatition_AdmintoSealPromotions);
		
		allassociationData.put("Admin", Admin_association_data);
		
		ObservableList<AssociationInfo> Product_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Product", Product_association_data);
		
		ObservableList<AssociationInfo> Order_association_data = FXCollections.observableArrayList();
		AssociationInfo Order_associatition_OrdertoProduct = new AssociationInfo("Order", "Product", "OrdertoProduct", true);
		Order_association_data.add(Order_associatition_OrdertoProduct);
		
		allassociationData.put("Order", Order_association_data);
		
		ObservableList<AssociationInfo> Application_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Application", Application_association_data);
		
		ObservableList<AssociationInfo> SealPromotions_association_data = FXCollections.observableArrayList();
		AssociationInfo SealPromotions_associatition_SealPromotionstoProduct = new AssociationInfo("SealPromotions", "Product", "SealPromotionstoProduct", true);
		SealPromotions_association_data.add(SealPromotions_associatition_SealPromotionstoProduct);
		
		allassociationData.put("SealPromotions", SealPromotions_association_data);
		
		
		association_statisic.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) ->  { 
	
							 		if (newValue != null) {
							 			 try {
							 			 	 if (newValue.getNumber() != 0) {
								 				 //choose object or not
								 				 if (objectindex != -1) {
									 				 Class[] cArg = new Class[1];
									 				 cArg[0] = List.class;
									 				 //reflect updateTableData method
										 			 Method updateob = this.getClass().getMethod("update" + newValue.getTargetClass() + "Table", cArg);
										 			 //find choosen object
										 			 Object selectedob = EntityManager.getAllInstancesOf(newValue.getSourceClass()).get(objectindex);
										 			 //reflect find association method
										 			 Method getAssociatedObject = selectedob.getClass().getMethod("get" + newValue.getAssociationName());
										 			 List r = new LinkedList();
										 			 //one or mulity?
										 			 if (newValue.getIsMultiple() == true) {
											 			 
											 			r = (List) getAssociatedObject.invoke(selectedob);
										 			 }
										 			 else {
										 				r.add(getAssociatedObject.invoke(selectedob));
										 			 }
										 			 //invoke update method
										 			 updateob.invoke(this, r);
										 			  
										 			 
								 				 }
												 //bind updated data to GUI
					 				 			 TableView obs = allObjectTables.get(newValue.getTargetClass());
					 				 			 if (obs != null) {
					 				 				object_statics.setContent(obs);
					 				 				object_statics.setText("Targets Objects " + newValue.getTargetClass() + ":");
					 				 			 }
					 				 		 }
							 			 }
							 			 catch (Exception e) {
							 				 e.printStackTrace();
							 			 }
							 		}
					 		  });
		
	}	
	
	

    //prepare data for contract
	public void prepareData() {
		
		//definition map
		definitions_map = new HashMap<String, String>();
		
		//precondition map
		preconditions_map = new HashMap<String, String>();
		preconditions_map.put("fillUsernameAndPassword", "username.oclIsTypeOf(String) and\npassword.oclIsTypeOf(String) and\npassword <> \"\" and\nusername <> \"\"\n");
		preconditions_map.put("callForLogin", "username.oclIsTypeOf(String) and\nusername <> \"\"\n");
		preconditions_map.put("fillAdminnameAndPassword", "adminname.oclIsTypeOf(String) and\npassword.oclIsTypeOf(String) and\npassword <> \"\" and\nadminname <> \"\"\n");
		preconditions_map.put("callForLogin", "username.oclIsTypeOf(String) and\nusername <> \"\"\n");
		preconditions_map.put("fillAccountDetail", "adminname.oclIsTypeOf(String) and\npassword.oclIsTypeOf(String) and\nphoneNumber.oclIsTypeOf(String) and\nbankCardNumber.oclIsTypeOf(String) and\npassword <> \"\" and\nadminname <> \"\" and\nphoneNumber <> \"\"\n");
		preconditions_map.put("fillAdvancedProfile", "fillAdvancedProfile.oclIsTypeOf(Profile) and\nProfile <> NULL\n");
		preconditions_map.put("getUserApplicationsList", "adminname.oclIsTypeOf(String) and\nadminname <> \"\"\n");
		preconditions_map.put("fillResultAndDesc", "comment.oclIsTypeOf(String) and\ncomment <> \"\" and\nresults.oclIsTypeOf(Boolean)\n");
		preconditions_map.put("callToCreateProduct", "adminname.oclIsTypeOf(String) and\nadminname <> \"\"\n");
		preconditions_map.put("addProduct", "productName.oclIsTypeOf(String) and\nproductName <> \"\"\n");
		preconditions_map.put("fillProductDetail", "productDetail.Id <> 0 and\nproductDetail <> null\n");
		preconditions_map.put("getOrderDetail", "orderId <> 0");
		
		//postcondition map
		postconditions_map = new HashMap<String, String>();
		postconditions_map.put("fillUsernameAndPassword", "true");
		postconditions_map.put("callForLogin", "true");
		postconditions_map.put("fillAdminnameAndPassword", "true");
		postconditions_map.put("callForLogin", "true");
		postconditions_map.put("fillAccountDetail", "true");
		postconditions_map.put("fillAdvancedProfile", "true");
		postconditions_map.put("getUserApplicationsList", "true");
		postconditions_map.put("fillResultAndDesc", "true");
		postconditions_map.put("callToCreateProduct", "true");
		postconditions_map.put("addProduct", "true");
		postconditions_map.put("fillProductDetail", "true");
		postconditions_map.put("getOrderDetail", "true");
		
		//service invariants map
		service_invariants_map = new LinkedHashMap<String, String>();
		
		//entity invariants map
		entity_invariants_map = new LinkedHashMap<String, String>();
		
	}
	
	public void generatOperationPane() {
		
		 operationPanels = new LinkedHashMap<String, GridPane>();
		
		 // ==================== GridPane_fillUsernameAndPassword ====================
		 GridPane fillUsernameAndPassword = new GridPane();
		 fillUsernameAndPassword.setHgap(4);
		 fillUsernameAndPassword.setVgap(6);
		 fillUsernameAndPassword.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillUsernameAndPassword_content = fillUsernameAndPassword.getChildren();
		 Label fillUsernameAndPassword_username_label = new Label("username:");
		 fillUsernameAndPassword_username_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillUsernameAndPassword_content.add(fillUsernameAndPassword_username_label);
		 GridPane.setConstraints(fillUsernameAndPassword_username_label, 0, 0);
		 
		 fillUsernameAndPassword_username_t = new TextField();
		 fillUsernameAndPassword_content.add(fillUsernameAndPassword_username_t);
		 fillUsernameAndPassword_username_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillUsernameAndPassword_username_t, 1, 0);
		 Label fillUsernameAndPassword_password_label = new Label("password:");
		 fillUsernameAndPassword_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillUsernameAndPassword_content.add(fillUsernameAndPassword_password_label);
		 GridPane.setConstraints(fillUsernameAndPassword_password_label, 0, 1);
		 
		 fillUsernameAndPassword_password_t = new TextField();
		 fillUsernameAndPassword_content.add(fillUsernameAndPassword_password_t);
		 fillUsernameAndPassword_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillUsernameAndPassword_password_t, 1, 1);
		 operationPanels.put("fillUsernameAndPassword", fillUsernameAndPassword);
		 
		 // ==================== GridPane_callForLogin ====================
		 GridPane callForLogin = new GridPane();
		 callForLogin.setHgap(4);
		 callForLogin.setVgap(6);
		 callForLogin.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> callForLogin_content = callForLogin.getChildren();
		 Label callForLogin_username_label = new Label("username:");
		 callForLogin_username_label.setMinWidth(Region.USE_PREF_SIZE);
		 callForLogin_content.add(callForLogin_username_label);
		 GridPane.setConstraints(callForLogin_username_label, 0, 0);
		 
		 callForLogin_username_t = new TextField();
		 callForLogin_content.add(callForLogin_username_t);
		 callForLogin_username_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(callForLogin_username_t, 1, 0);
		 operationPanels.put("callForLogin", callForLogin);
		 
		 // ==================== GridPane_fillAdminnameAndPassword ====================
		 GridPane fillAdminnameAndPassword = new GridPane();
		 fillAdminnameAndPassword.setHgap(4);
		 fillAdminnameAndPassword.setVgap(6);
		 fillAdminnameAndPassword.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillAdminnameAndPassword_content = fillAdminnameAndPassword.getChildren();
		 Label fillAdminnameAndPassword_username_label = new Label("username:");
		 fillAdminnameAndPassword_username_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAdminnameAndPassword_content.add(fillAdminnameAndPassword_username_label);
		 GridPane.setConstraints(fillAdminnameAndPassword_username_label, 0, 0);
		 
		 fillAdminnameAndPassword_username_t = new TextField();
		 fillAdminnameAndPassword_content.add(fillAdminnameAndPassword_username_t);
		 fillAdminnameAndPassword_username_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAdminnameAndPassword_username_t, 1, 0);
		 Label fillAdminnameAndPassword_password_label = new Label("password:");
		 fillAdminnameAndPassword_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAdminnameAndPassword_content.add(fillAdminnameAndPassword_password_label);
		 GridPane.setConstraints(fillAdminnameAndPassword_password_label, 0, 1);
		 
		 fillAdminnameAndPassword_password_t = new TextField();
		 fillAdminnameAndPassword_content.add(fillAdminnameAndPassword_password_t);
		 fillAdminnameAndPassword_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAdminnameAndPassword_password_t, 1, 1);
		 operationPanels.put("fillAdminnameAndPassword", fillAdminnameAndPassword);
		 
		 // ==================== GridPane_callForLogin ====================
		 GridPane callForLogin1 = new GridPane();
		 callForLogin1.setHgap(4);
		 callForLogin1.setVgap(6);
		 callForLogin1.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> callForLogin_content1 = callForLogin1.getChildren();
		 Label callForLogin_username_label1 = new Label("username:");
		 callForLogin_username_label1.setMinWidth(Region.USE_PREF_SIZE);
		 callForLogin_content1.add(callForLogin_username_label1);
		 GridPane.setConstraints(callForLogin_username_label1, 0, 0);
		 
		 callForLogin_username_t = new TextField();
		 callForLogin_content1.add(callForLogin_username_t);
		 callForLogin_username_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(callForLogin_username_t, 1, 0);
		 operationPanels.put("callForLogin", callForLogin1);
		 
		 // ==================== GridPane_fillAccountDetail ====================
		 GridPane fillAccountDetail = new GridPane();
		 fillAccountDetail.setHgap(4);
		 fillAccountDetail.setVgap(6);
		 fillAccountDetail.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillAccountDetail_content = fillAccountDetail.getChildren();
		 Label fillAccountDetail_username_label = new Label("username:");
		 fillAccountDetail_username_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAccountDetail_content.add(fillAccountDetail_username_label);
		 GridPane.setConstraints(fillAccountDetail_username_label, 0, 0);
		 
		 fillAccountDetail_username_t = new TextField();
		 fillAccountDetail_content.add(fillAccountDetail_username_t);
		 fillAccountDetail_username_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAccountDetail_username_t, 1, 0);
		 Label fillAccountDetail_password_label = new Label("password:");
		 fillAccountDetail_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAccountDetail_content.add(fillAccountDetail_password_label);
		 GridPane.setConstraints(fillAccountDetail_password_label, 0, 1);
		 
		 fillAccountDetail_password_t = new TextField();
		 fillAccountDetail_content.add(fillAccountDetail_password_t);
		 fillAccountDetail_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAccountDetail_password_t, 1, 1);
		 Label fillAccountDetail_phoneNumber_label = new Label("phoneNumber:");
		 fillAccountDetail_phoneNumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAccountDetail_content.add(fillAccountDetail_phoneNumber_label);
		 GridPane.setConstraints(fillAccountDetail_phoneNumber_label, 0, 2);
		 
		 fillAccountDetail_phoneNumber_t = new TextField();
		 fillAccountDetail_content.add(fillAccountDetail_phoneNumber_t);
		 fillAccountDetail_phoneNumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAccountDetail_phoneNumber_t, 1, 2);
		 Label fillAccountDetail_bankCardNumber_label = new Label("bankCardNumber:");
		 fillAccountDetail_bankCardNumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAccountDetail_content.add(fillAccountDetail_bankCardNumber_label);
		 GridPane.setConstraints(fillAccountDetail_bankCardNumber_label, 0, 3);
		 
		 fillAccountDetail_bankCardNumber_t = new TextField();
		 fillAccountDetail_content.add(fillAccountDetail_bankCardNumber_t);
		 fillAccountDetail_bankCardNumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAccountDetail_bankCardNumber_t, 1, 3);
		 operationPanels.put("fillAccountDetail", fillAccountDetail);
		 
		 // ==================== GridPane_fillAdvancedProfile ====================
		 GridPane fillAdvancedProfile = new GridPane();
		 fillAdvancedProfile.setHgap(4);
		 fillAdvancedProfile.setVgap(6);
		 fillAdvancedProfile.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillAdvancedProfile_content = fillAdvancedProfile.getChildren();
		 Label fillAdvancedProfile_fillAdvancedProfile_label = new Label("fillAdvancedProfile:");
		 fillAdvancedProfile_fillAdvancedProfile_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillAdvancedProfile_content.add(fillAdvancedProfile_fillAdvancedProfile_label);
		 GridPane.setConstraints(fillAdvancedProfile_fillAdvancedProfile_label, 0, 0);
		 
		 fillAdvancedProfile_fillAdvancedProfile_t = new TextField();
		 fillAdvancedProfile_content.add(fillAdvancedProfile_fillAdvancedProfile_t);
		 fillAdvancedProfile_fillAdvancedProfile_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillAdvancedProfile_fillAdvancedProfile_t, 1, 0);
		 operationPanels.put("fillAdvancedProfile", fillAdvancedProfile);
		 
		 // ==================== GridPane_getUserApplicationsList ====================
		 GridPane getUserApplicationsList = new GridPane();
		 getUserApplicationsList.setHgap(4);
		 getUserApplicationsList.setVgap(6);
		 getUserApplicationsList.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getUserApplicationsList_content = getUserApplicationsList.getChildren();
		 Label getUserApplicationsList_adminname_label = new Label("adminname:");
		 getUserApplicationsList_adminname_label.setMinWidth(Region.USE_PREF_SIZE);
		 getUserApplicationsList_content.add(getUserApplicationsList_adminname_label);
		 GridPane.setConstraints(getUserApplicationsList_adminname_label, 0, 0);
		 
		 getUserApplicationsList_adminname_t = new TextField();
		 getUserApplicationsList_content.add(getUserApplicationsList_adminname_t);
		 getUserApplicationsList_adminname_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(getUserApplicationsList_adminname_t, 1, 0);
		 operationPanels.put("getUserApplicationsList", getUserApplicationsList);
		 
		 // ==================== GridPane_fillResultAndDesc ====================
		 GridPane fillResultAndDesc = new GridPane();
		 fillResultAndDesc.setHgap(4);
		 fillResultAndDesc.setVgap(6);
		 fillResultAndDesc.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillResultAndDesc_content = fillResultAndDesc.getChildren();
		 Label fillResultAndDesc_results_label = new Label("results:");
		 fillResultAndDesc_results_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillResultAndDesc_content.add(fillResultAndDesc_results_label);
		 GridPane.setConstraints(fillResultAndDesc_results_label, 0, 0);
		 
		 fillResultAndDesc_results_t = new TextField();
		 fillResultAndDesc_content.add(fillResultAndDesc_results_t);
		 fillResultAndDesc_results_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillResultAndDesc_results_t, 1, 0);
		 Label fillResultAndDesc_comment_label = new Label("comment:");
		 fillResultAndDesc_comment_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillResultAndDesc_content.add(fillResultAndDesc_comment_label);
		 GridPane.setConstraints(fillResultAndDesc_comment_label, 0, 1);
		 
		 fillResultAndDesc_comment_t = new TextField();
		 fillResultAndDesc_content.add(fillResultAndDesc_comment_t);
		 fillResultAndDesc_comment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillResultAndDesc_comment_t, 1, 1);
		 operationPanels.put("fillResultAndDesc", fillResultAndDesc);
		 
		 // ==================== GridPane_callToCreateProduct ====================
		 GridPane callToCreateProduct = new GridPane();
		 callToCreateProduct.setHgap(4);
		 callToCreateProduct.setVgap(6);
		 callToCreateProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> callToCreateProduct_content = callToCreateProduct.getChildren();
		 Label callToCreateProduct_adminname_label = new Label("adminname:");
		 callToCreateProduct_adminname_label.setMinWidth(Region.USE_PREF_SIZE);
		 callToCreateProduct_content.add(callToCreateProduct_adminname_label);
		 GridPane.setConstraints(callToCreateProduct_adminname_label, 0, 0);
		 
		 callToCreateProduct_adminname_t = new TextField();
		 callToCreateProduct_content.add(callToCreateProduct_adminname_t);
		 callToCreateProduct_adminname_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(callToCreateProduct_adminname_t, 1, 0);
		 operationPanels.put("callToCreateProduct", callToCreateProduct);
		 
		 // ==================== GridPane_addProduct ====================
		 GridPane addProduct = new GridPane();
		 addProduct.setHgap(4);
		 addProduct.setVgap(6);
		 addProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> addProduct_content = addProduct.getChildren();
		 Label addProduct_productName_label = new Label("productName:");
		 addProduct_productName_label.setMinWidth(Region.USE_PREF_SIZE);
		 addProduct_content.add(addProduct_productName_label);
		 GridPane.setConstraints(addProduct_productName_label, 0, 0);
		 
		 addProduct_productName_t = new TextField();
		 addProduct_content.add(addProduct_productName_t);
		 addProduct_productName_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addProduct_productName_t, 1, 0);
		 operationPanels.put("addProduct", addProduct);
		 
		 // ==================== GridPane_fillProductDetail ====================
		 GridPane fillProductDetail = new GridPane();
		 fillProductDetail.setHgap(4);
		 fillProductDetail.setVgap(6);
		 fillProductDetail.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> fillProductDetail_content = fillProductDetail.getChildren();
		 Label fillProductDetail_productDetail_label = new Label("productDetail:");
		 fillProductDetail_productDetail_label.setMinWidth(Region.USE_PREF_SIZE);
		 fillProductDetail_content.add(fillProductDetail_productDetail_label);
		 GridPane.setConstraints(fillProductDetail_productDetail_label, 0, 0);
		 
		 fillProductDetail_productDetail_t = new TextField();
		 fillProductDetail_content.add(fillProductDetail_productDetail_t);
		 fillProductDetail_productDetail_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(fillProductDetail_productDetail_t, 1, 0);
		 operationPanels.put("fillProductDetail", fillProductDetail);
		 
		 // ==================== GridPane_getOrderDetail ====================
		 GridPane getOrderDetail = new GridPane();
		 getOrderDetail.setHgap(4);
		 getOrderDetail.setVgap(6);
		 getOrderDetail.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> getOrderDetail_content = getOrderDetail.getChildren();
		 Label getOrderDetail_orderId_label = new Label("orderId:");
		 getOrderDetail_orderId_label.setMinWidth(Region.USE_PREF_SIZE);
		 getOrderDetail_content.add(getOrderDetail_orderId_label);
		 GridPane.setConstraints(getOrderDetail_orderId_label, 0, 0);
		 
		 getOrderDetail_orderId_t = new TextField();
		 getOrderDetail_content.add(getOrderDetail_orderId_t);
		 getOrderDetail_orderId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(getOrderDetail_orderId_t, 1, 0);
		 operationPanels.put("getOrderDetail", getOrderDetail);
		 
	}	

	public void actorTreeViewBinding() {
		
		 

		TreeItem<String> treeRootadministrator = new TreeItem<String>("Root node");
		
		TreeItem<String> subTreeRoot_Profile = new TreeItem<String>("manageProfile");
					 		subTreeRoot_Profile.getChildren().addAll(Arrays.asList(					 		
					 			 		new TreeItem<String>("createProfile"),
					 			 		new TreeItem<String>("queryProfile"),
					 			 		new TreeItem<String>("modifyProfile"),
					 			 		new TreeItem<String>("deleteProfile")					 			 	
					 			 	));							 		
		
					 			
						 		
		treeRootadministrator.getChildren().addAll(Arrays.asList(
		 	subTreeRoot_Profile
				));	
				
	 			treeRootadministrator.setExpanded(true);

		actor_treeview_administrator.setShowRoot(false);
		actor_treeview_administrator.setRoot(treeRootadministrator);
	 		
		actor_treeview_administrator.getSelectionModel().selectedItemProperty().addListener(
		 				 (observable, oldValue, newValue) -> { 
		 				 								
		 				 							 //clear the previous return
		 											 operation_return_pane.setContent(new Label());
		 											 
		 				 							 clickedOp = newValue.getValue();
		 				 							 GridPane op = operationPanels.get(clickedOp);
		 				 							 VBox vb = opInvariantPanel.get(clickedOp);
		 				 							 
		 				 							 //op pannel
		 				 							 if (op != null) {
		 				 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
		 				 								 
		 				 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
		 				 								 choosenOperation = new LinkedList<TextField>();
		 				 								 for (Node n : l) {
		 				 								 	 if (n instanceof TextField) {
		 				 								 	 	choosenOperation.add((TextField)n);
		 				 								 	  }
		 				 								 }
		 				 								 
		 				 								 definition.setText(definitions_map.get(newValue.getValue()));
		 				 								 precondition.setText(preconditions_map.get(newValue.getValue()));
		 				 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
		 				 								 
		 				 						     }
		 				 							 else {
		 				 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
		 				 								 l.setPadding(new Insets(8, 8, 8, 8));
		 				 								 operation_paras.setContent(l);
		 				 							 }	
		 				 							 
		 				 							 //op invariants
		 				 							 if (vb != null) {
		 				 							 	ScrollPane scrollPane = new ScrollPane(vb);
		 				 							 	scrollPane.setFitToWidth(true);
		 				 							 	invariants_panes.setMaxHeight(200); 
		 				 							 	//all_invariant_pane.setContent(scrollPane);	
		 				 							 	
		 				 							 	invariants_panes.setContent(scrollPane);
		 				 							 } else {
		 				 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
		 				 							     l.setPadding(new Insets(8, 8, 8, 8));
		 				 							     invariants_panes.setContent(l);
		 				 							 }
		 				 							 
		 				 							 //reset pre- and post-conditions area color
		 				 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
		 				 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
		 				 							 //reset condition panel title
		 				 							 precondition_pane.setText("Precondition");
		 				 							 postcondition_pane.setText("Postcondition");
		 				 						} 
		 				 				);

		
		
		 
		TreeItem<String> treeRootuser = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_userLogin = new TreeItem<String>("userLogin");
			subTreeRoot_userLogin.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("fillUsernameAndPassword"),
					 	new TreeItem<String>("callForUserLogin")
				 		));	
			TreeItem<String> subTreeRoot_signup = new TreeItem<String>("signup");
			subTreeRoot_signup.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("callForSignup"),
					 	new TreeItem<String>("fillAccountDetail")
				 		));	
			TreeItem<String> subTreeRoot_submitQualifyApplication = new TreeItem<String>("submitQualifyApplication");
			subTreeRoot_submitQualifyApplication.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("fillAdvancedProfile")
				 		));	
		
		treeRootuser.getChildren().addAll(Arrays.asList(
			subTreeRoot_userLogin,
			subTreeRoot_signup,
			subTreeRoot_submitQualifyApplication
					));
		
		treeRootuser.setExpanded(true);

		actor_treeview_user.setShowRoot(false);
		actor_treeview_user.setRoot(treeRootuser);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_user.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootqualifieduser = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_viewSelfOrders = new TreeItem<String>("viewSelfOrders");
			subTreeRoot_viewSelfOrders.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("getOrderDetail")
				 		));	
		
		treeRootqualifieduser.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("viewPromotionalProducts"),
			subTreeRoot_viewSelfOrders
					));
		treeRootqualifieduser.getChildren().addAll(Arrays.asList(
			this.deepCopyTree(subTreeRoot_userLogin),
			this.deepCopyTree(subTreeRoot_signup),
			this.deepCopyTree(subTreeRoot_submitQualifyApplication)
			));
		
		treeRootqualifieduser.setExpanded(true);

		actor_treeview_qualifieduser.setShowRoot(false);
		actor_treeview_qualifieduser.setRoot(treeRootqualifieduser);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_qualifieduser.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootadmin = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_reviewUserApplication = new TreeItem<String>("reviewUserApplication");
			subTreeRoot_reviewUserApplication.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("getUserApplicationsList"),
					 	new TreeItem<String>("fillResultAndDesc")
				 		));	
			TreeItem<String> subTreeRoot_adminLogin = new TreeItem<String>("adminLogin");
			subTreeRoot_adminLogin.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("callForLogin"),
					 	new TreeItem<String>("fillAdminnameAndPassword")
				 		));	
			TreeItem<String> subTreeRoot_addNewProduct = new TreeItem<String>("addNewProduct");
			subTreeRoot_addNewProduct.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("callToCreateProduct"),
					 	new TreeItem<String>("addProduct"),
					 	new TreeItem<String>("fillProductDetail")
				 		));	
		
		treeRootadmin.getChildren().addAll(Arrays.asList(
			subTreeRoot_reviewUserApplication,
			subTreeRoot_adminLogin,
			subTreeRoot_addNewProduct
					));
		
		treeRootadmin.setExpanded(true);

		actor_treeview_admin.setShowRoot(false);
		actor_treeview_admin.setRoot(treeRootadmin);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_admin.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
	}

	/**
	*    Execute Operation
	*/
	@FXML
	public void execute(ActionEvent event) {
		
		switch (clickedOp) {
		case "fillUsernameAndPassword" : fillUsernameAndPassword(); break;
		case "callForLogin" : callForLogin(); break;
		case "fillAdminnameAndPassword" : fillAdminnameAndPassword(); break;
		case "fillAccountDetail" : fillAccountDetail(); break;
		case "fillAdvancedProfile" : fillAdvancedProfile(); break;
		case "getUserApplicationsList" : getUserApplicationsList(); break;
		case "fillResultAndDesc" : fillResultAndDesc(); break;
		case "callToCreateProduct" : callToCreateProduct(); break;
		case "addProduct" : addProduct(); break;
		case "fillProductDetail" : fillProductDetail(); break;
		case "getOrderDetail" : getOrderDetail(); break;
		
		}
		
		System.out.println("execute buttion clicked");
		
		//checking relevant invariants
		opInvairantPanelUpdate();
	}

	/**
	*    Refresh All
	*/		
	@FXML
	public void refresh(ActionEvent event) {
		
		refreshAll();
		System.out.println("refresh all");
	}		
	
	/**
	*    Save All
	*/			
	@FXML
	public void save(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save State to File");
		fileChooser.setInitialFileName("*.state");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			System.out.println("save state to file " + file.getAbsolutePath());				
			EntityManager.save(file);
		}
	}
	
	/**
	*    Load All
	*/			
	@FXML
	public void load(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open State File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			System.out.println("choose file" + file.getAbsolutePath());
			EntityManager.load(file); 
		}
		
		//refresh GUI after load data
		refreshAll();
	}
	
	
	//precondition unsat dialog
	public void preconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Precondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}
	
	//postcondition unsat dialog
	public void postconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Postcondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}

	public void thirdpartyServiceUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("third party service is exception");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}		
	
	
	public void fillUsernameAndPassword() {
		
		System.out.println("execute fillUsernameAndPassword");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillUsernameAndPassword in service: UserLoginService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(userloginservice_service.fillUsernameAndPassword(
			fillUsernameAndPassword_username_t.getText(),
			fillUsernameAndPassword_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
//	public void callForLogin() {
//		
//		System.out.println("execute callForLogin");
//		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
//		log.appendText(time + " -- execute operation: callForLogin in service: UserLoginService ");
//		
//		try {
//			//invoke op with parameters
//			//return value is primitive type, bind result to label.
//			String result = String.valueOf(userloginservice_service.callForLogin(
//			callForLogin_username_t.getText()
//			));	
//			Label l = new Label(result);
//			l.setPadding(new Insets(8, 8, 8, 8));
//			operation_return_pane.setContent(l);
//		    log.appendText(" -- success!\n");
//		    //set pre- and post-conditions text area color
//		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
//		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
//		    //contract evaluation result
//		    precondition_pane.setText("Precondition: True");
//		    postcondition_pane.setText("Postcondition: True");
//		    
//		    
//		} catch (PreconditionException e) {
//			log.appendText(" -- failed!\n");
//			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
//			precondition_pane.setText("Precondition: False");	
//			preconditionUnSat();
//			
//		} catch (PostconditionException e) {
//			log.appendText(" -- failed!\n");
//			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
//			postcondition_pane.setText("Postcondition: False");
//			postconditionUnSat();
//			
//		} catch (NumberFormatException e) {
//			log.appendText(" -- failed!\n");
//			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
//			precondition_pane.setText("Precondition: False");	
//			preconditionUnSat();
//			
//		} catch (Exception e ) {		
//			if (e instanceof ThirdPartyServiceException)
//				thirdpartyServiceUnSat();
//		}
//	}
	public void fillAdminnameAndPassword() {
		
		System.out.println("execute fillAdminnameAndPassword");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillAdminnameAndPassword in service: AdminLoginService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(adminloginservice_service.fillAdminnameAndPassword(
			fillAdminnameAndPassword_username_t.getText(),
			fillAdminnameAndPassword_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void callForLogin() {
		
		System.out.println("execute callForLogin");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: callForLogin in service: AdminLoginService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(adminloginservice_service.callForLogin(
			callForLogin_username_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void fillAccountDetail() {
		
		System.out.println("execute fillAccountDetail");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillAccountDetail in service: SignupService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(signupservice_service.fillAccountDetail(
			fillAccountDetail_username_t.getText(),
			fillAccountDetail_password_t.getText(),
			fillAccountDetail_phoneNumber_t.getText(),
			fillAccountDetail_bankCardNumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void fillAdvancedProfile() {
		
		System.out.println("execute fillAdvancedProfile");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillAdvancedProfile in service: SubmitQualifyApplicationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(submitqualifyapplicationservice_service.fillAdvancedProfile(null
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void getUserApplicationsList() {
		
		System.out.println("execute getUserApplicationsList");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getUserApplicationsList in service: ReviewUserApplicationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(reviewuserapplicationservice_service.getUserApplicationsList(
			getUserApplicationsList_adminname_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void fillResultAndDesc() {
		
		System.out.println("execute fillResultAndDesc");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillResultAndDesc in service: ReviewUserApplicationService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(reviewuserapplicationservice_service.fillResultAndDesc(
			Boolean.valueOf(fillResultAndDesc_results_t.getText()),
			fillResultAndDesc_comment_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void callToCreateProduct() {
		
		System.out.println("execute callToCreateProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: callToCreateProduct in service: AddNewProductService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(addnewproductservice_service.callToCreateProduct(
			callToCreateProduct_adminname_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void addProduct() {
		
		System.out.println("execute addProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: addProduct in service: AddNewProductService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(addnewproductservice_service.addProduct(
			addProduct_productName_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void fillProductDetail() {
		
		System.out.println("execute fillProductDetail");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: fillProductDetail in service: AddNewProductService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(addnewproductservice_service.fillProductDetail(null
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void getOrderDetail() {
		
		System.out.println("execute getOrderDetail");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: getOrderDetail in service: ViewSelfOrdersService ");
		
		try {
			//invoke op with parameters
				Order r = viewselfordersservice_service.getOrderDetail(
				Integer.valueOf(getOrderDetail_orderId_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableOrder_Id = new TableColumn<Map<String, String>, String>("Id");
				tableOrder_Id.setMinWidth("Id".length()*10);
				tableOrder_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_Id);
				
				ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					dataOrder.add(unit);
				
				
				tableOrder.setItems(dataOrder);
				operation_return_pane.setContent(tableOrder);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}




	//select object index
	int objectindex;
	
	@FXML
	TabPane mainPane;

	@FXML
	TextArea log;
	
	@FXML
	TreeView<String> actor_treeview_user;
	@FXML
	TreeView<String> actor_treeview_qualifieduser;
	@FXML
	TreeView<String> actor_treeview_admin;
	
	@FXML
	TreeView<String> actor_treeview_administrator;


	@FXML
	TextArea definition;
	@FXML
	TextArea precondition;
	@FXML
	TextArea postcondition;
	@FXML
	TextArea invariants;
	
	@FXML
	TitledPane precondition_pane;
	@FXML
	TitledPane postcondition_pane;
	
	//chosen operation textfields
	List<TextField> choosenOperation;
	String clickedOp;
		
	@FXML
	TableView<ClassInfo> class_statisic;
	@FXML
	TableView<AssociationInfo> association_statisic;
	
	Map<String, ObservableList<AssociationInfo>> allassociationData;
	ObservableList<ClassInfo> classInfodata;
	
	Test1System test1system_service;
	ThirdPartyServices thirdpartyservices_service;
	UserLoginService userloginservice_service;
	SignupService signupservice_service;
	SubmitQualifyApplicationService submitqualifyapplicationservice_service;
	AdminLoginService adminloginservice_service;
	ReviewUserApplicationService reviewuserapplicationservice_service;
	AddNewProductService addnewproductservice_service;
	ViewSelfOrdersService viewselfordersservice_service;
	PromotionSaleSystem promotionsalesystem_service;
	
	ClassInfo profile;
	ClassInfo user;
	ClassInfo qualifieduser;
	ClassInfo admin;
	ClassInfo product;
	ClassInfo order;
	ClassInfo application;
	ClassInfo sealpromotions;
		
	@FXML
	TitledPane object_statics;
	Map<String, TableView> allObjectTables;
	
	@FXML
	TitledPane operation_paras;
	
	@FXML
	TitledPane operation_return_pane;
	
	@FXML 
	TitledPane all_invariant_pane;
	
	@FXML
	TitledPane invariants_panes;
	
	Map<String, GridPane> operationPanels;
	Map<String, VBox> opInvariantPanel;
	
	//all textfiled or eumntity
	TextField fillUsernameAndPassword_username_t;
	TextField fillUsernameAndPassword_password_t;
	TextField callForLogin_username_t;
	TextField fillAdminnameAndPassword_username_t;
	TextField fillAdminnameAndPassword_password_t;
	TextField fillAccountDetail_username_t;
	TextField fillAccountDetail_password_t;
	TextField fillAccountDetail_phoneNumber_t;
	TextField fillAccountDetail_bankCardNumber_t;
	TextField fillAdvancedProfile_fillAdvancedProfile_t;
	TextField getUserApplicationsList_adminname_t;
	TextField fillResultAndDesc_results_t;
	TextField fillResultAndDesc_comment_t;
	TextField callToCreateProduct_adminname_t;
	TextField addProduct_productName_t;
	TextField fillProductDetail_productDetail_t;
	TextField getOrderDetail_orderId_t;
	
	HashMap<String, String> definitions_map;
	HashMap<String, String> preconditions_map;
	HashMap<String, String> postconditions_map;
	HashMap<String, String> invariants_map;
	LinkedHashMap<String, String> service_invariants_map;
	LinkedHashMap<String, String> entity_invariants_map;
	LinkedHashMap<String, Label> service_invariants_label_map;
	LinkedHashMap<String, Label> entity_invariants_label_map;
	LinkedHashMap<String, Label> op_entity_invariants_label_map;
	LinkedHashMap<String, Label> op_service_invariants_label_map;
	

	
}
