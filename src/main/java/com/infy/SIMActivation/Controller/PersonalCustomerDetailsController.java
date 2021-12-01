package com.infy.SIMActivation.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.SIMActivation.Domain.CustomerIdentity;
import com.infy.SIMActivation.Exceptions.NotValidDetailsException;
import com.infy.SIMActivation.Services.CustomerIdentityServices;
import com.infy.SIMActivation.validation.ValidatePersonalDetails;

@RestController
@RequestMapping("/personaldetails")
@Valid
public class PersonalCustomerDetailsController {
	@Autowired
	private CustomerIdentityServices customerIdentityServices;
	
	@PostMapping(value = "/validate")
	public ResponseEntity<String> validatePersonalDetail(
			@Valid @RequestBody ValidatePersonalDetails validatePersonalDetails) throws NotValidDetailsException
	{
		CustomerIdentity customerIdentity = customerIdentityServices.verificationCustomerIdentity(
				validatePersonalDetails.getFirstName(), validatePersonalDetails.getLastName());
		if(customerIdentity == null)
			return ResponseEntity.ok("No customer found for the provided details");
		else {
			//System.out.println(customerIdentity.getEmailAddress()==validatePersonalDetails.getEmailAddress());
			if(customerIdentity.getEmailAddress().matches(validatePersonalDetails.getEmailAddress()))
			{
				return ResponseEntity.ok("Sucess");
			}
			else {
				return ResponseEntity.ok("Invalid email details!!");
			}
		}	
	}
}
