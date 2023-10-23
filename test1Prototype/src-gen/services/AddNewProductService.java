package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface AddNewProductService {

	/* all system operations of the use case*/
	boolean callToCreateProduct(String adminname) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean addProduct(String productName) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean fillProductDetail(Product productDetail) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
