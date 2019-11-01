package org.edison.patientdemoclient.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.edison.patientdemoclient.util.PatientRestApi;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyPatientDataModel  extends LazyDataModel<Patient> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstname;
	private String lastname;
	private String country;
	private String status;
	private String token;
	
	@Override
	public Patient getRowData(String rowKey) {
		return PatientRestApi.getPatient(rowKey, token);
	}
	
	@Override
	public Object getRowKey(Patient patient) {
		return patient.getId();
	}
	
	@Override
	public List<Patient> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		PatientPage page = 
				PatientRestApi.search(firstname, lastname, country, status, first > 0 ? first/pageSize : first, pageSize, token);
		 
		this.setRowCount(page.getTotalElements());
		
		return new ArrayList<Patient>(Arrays.asList(page.getContent()));
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
