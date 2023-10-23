package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface SignupService {

	/* all system operations of the use case*/
	boolean fillAccountDetail(String username, String password, String phoneNumber, String bankCardNumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
