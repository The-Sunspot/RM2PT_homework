As a Admin, I want to input product information and create new products, so that I can manage _the product catalog
{
	Basic Flow {
		(User) 1. the Admin shall log into _the system.
		(User) 2. the Admin shall navigate to _the product management module.
		(User) 3. the Admin shall click on _the New Product button.
		(System) 4. When button clicked, the system shall prompt _the Admin to input product details.
		(User) 5. the Admin shall enter _the product name description price and other necessary details.
		(System) 6. When admin enter _the message, the system shall validate _the product information and checks for duplicates.
		(System) 7. When product is valid, the system shall create _the new product and adds it to _the product catalog.
	}
	Alternative Flow {
		A. At any time, System fails :
		The system should record what _the administrator has filled in to avoid having to re_enter all _the information about _the commodity.
		1.  Admin restarts System logs in and requests recovery of prior state.
		2.  System reconstructs prior state.
			a2.  System detects anomalies preventing recovery.
				1.  System signals error to Admin records error and enters a clean state then Admin refill _the product info.
	}
}
As a User, I want to register, so that I can sign in and use _the system{
	Basic Flow {  
		(User) 1. the User shall access _the registration page.
		(User) 2. the User shall fill in _the required registration information.
		(System) 4. When  Users input is valid, the System shall create a new account and store _the Users information.
		(System) 5. the System shall generate a confirmation email for _the User.
		(User) 6. the User shall verify their email address by clicking _the confirmation link.
		(System) 7. the System shall confirm _the Users email address and activate their account.
		(User) 8. the User shall sign in to _the system using their registered email address and password.
	}
}
As a User, I want to log in, so that I can synchronize my account information and use _the system{
		Basic Flow {    
		(User) 1. the User shall access _the login page.
		(User) 2. the User shall enter their registered email address and password.
		(System) 3. the System shall validate _the Users input.
		(System) 4. When _the Users input is valid, the System shall check _if _the account is active.
		(System) 5. When _the account is active, the System shall authenticate _the User and grant them access to _the system.
		(System) 6. When _the account is inactive or _the input is invalid, the System shall display an error message and prompt _the User to try again.
	}
}
As a User, I want to submit qualify application, so that I can obtain purchase eligibility and become a qualified user
As a Qualified user, I want to view _the list of discounted goods and product information{
	Basic Flow {  
	(User) 1. the Qualified User shall log in to _the system using their registered email address and password.
	(System) 2. the System shall verify _the Qualified Users identity and grant them access to _the discounted goods and product information page.
	(User) 3. the Qualified User shall navigate to _the discounted goods and product information page.
	(System) 4. the System shall display _the list of discounted goods and their product information including price description and any relevant details.
	(User) 5. the Qualified User shall review _the list of discounted goods and product information.
	}
}
As a Qualified user, I want to view my own order information
As a Qualified user, I want to choose _the goods I like and place an order to purchase them
As a Admin, I want to log in with account and password, so that I can manage _the entire mall
As a Admin, I want to review user applications and provide feedback, so that I can manage user permissions


 
