package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ViewSelfOrdersService {

	/* all system operations of the use case*/
	Order getOrderDetail(int orderId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
