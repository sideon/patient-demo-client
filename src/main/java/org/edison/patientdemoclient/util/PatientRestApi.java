package org.edison.patientdemoclient.util;

import org.apache.logging.log4j.util.Strings;
import org.edison.patientdemoclient.model.Patient;
import org.edison.patientdemoclient.model.PatientPage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class PatientRestApi {
	private static final String PATIENT_ENDPOINT = "http://localhost:9090/patients";
	private final static String TOKEN_HEADER = "X-Firebase-Auth";
	
	public static PatientPage search(String firstname, String lastname, String country, String status, int page, int size, String accessToken) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATIENT_ENDPOINT)
				.queryParam("page", Integer.toString(page))
				.queryParam("size", Integer.toString(size));
		
		if (Strings.isNotEmpty(firstname)) {
			builder.queryParam("firstname", firstname);
		}
		if (Strings.isNotEmpty(lastname)) {
			builder.queryParam("lastname", lastname);
		}
		if (Strings.isNotEmpty(country)) {
			builder.queryParam("country", country);
		}
		if (Strings.isNotEmpty(status)) {
			builder.queryParam("status", status);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(TOKEN_HEADER, accessToken);      
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<PatientPage> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, PatientPage.class);
		
		return response.getBody();
	}
	
	public static Patient getPatient(String id, String accessToken) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATIENT_ENDPOINT).path("/" + id);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(TOKEN_HEADER, accessToken);      
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<Patient> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Patient.class);
		
		return response.getBody();
	}
	
	public static void deletePatient(String id, String accessToken) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATIENT_ENDPOINT).path("/" + id);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(TOKEN_HEADER, accessToken);      
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ResponseEntity.noContent().getClass());
	}
	
	public static void updatePatient(Patient patient, String accessToken) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATIENT_ENDPOINT).path("/" + patient.getId());
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(TOKEN_HEADER, accessToken);      
		
		HttpEntity<Patient> entity = new HttpEntity<Patient>(patient, headers);
		
		restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Patient.class);
		
	}
	
	public static void addPatient(Patient patient, String accessToken) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(TOKEN_HEADER, accessToken);      
		
		HttpEntity<Patient> entity = new HttpEntity<Patient>(patient, headers);
		
		restTemplate.exchange(PATIENT_ENDPOINT, HttpMethod.POST, entity, Patient.class);
		
	}
}
