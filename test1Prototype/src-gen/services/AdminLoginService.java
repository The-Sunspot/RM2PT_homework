package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface AdminLoginService {

	/* all system operations of the use case*/
	boolean callForLogin(String username) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean fillAdminnameAndPassword(String username, String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
