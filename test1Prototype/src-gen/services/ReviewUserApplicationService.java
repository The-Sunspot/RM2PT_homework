package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ReviewUserApplicationService {

	/* all system operations of the use case*/
	boolean getUserApplicationsList(String adminname) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean fillResultAndDesc(boolean results, String comment) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
