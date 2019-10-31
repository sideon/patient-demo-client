package org.edison.patientdemoclient.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.edison.patientdemoclient.model.Address;
import org.edison.patientdemoclient.model.FirebaseUser;
import org.edison.patientdemoclient.model.LazyPatientDataModel;
import org.edison.patientdemoclient.model.Patient;
import org.edison.patientdemoclient.util.PatientRestApi;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named
public class PatientBean {
	private LazyPatientDataModel model;
	private Patient selected;
	private Patient added;
	
	private FirebaseUser user;
	
	public LazyDataModel<Patient> getModel() {
		if (model == null) {
			model = new LazyPatientDataModel();
			model.setToken(user.getToken());
		}
		return model;
	}
	
	public FirebaseUser getUser() {
		if (user == null) {
			user = new FirebaseUser();
		}
		return user;
	}

	
	public Patient getAdded() {
		if(added == null) {
			added = new Patient();
		}
		return added;
	}

	public void setAdded(Patient added) {
		this.added = added;
	}

	public void setUser(FirebaseUser user) {
		this.user = user;
	}

	public Patient getSelected() {
		return selected;
	}

	public void setSelected(Patient selected) {
		this.selected = selected;
	}

	public void setModel(LazyPatientDataModel model) {
		this.model = model;
	}
	
	public void clear() {
		model.setCountry(null);
		model.setFirstname(null);
		model.setLastname(null);
		
        addMessage("Cleared");
    }
	
	public void add() {
        added = new Patient();
        added.setStatus("Active");
        
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address());
        addresses.add(new Address());
        
        added.setAddresses(addresses);
    }
	
	public void submitAdd() {
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
		PatientRestApi.addPatient(added, user.getToken());
        addMessage("Added");
    }
	
	public void cancelAdd() {
        addMessage("Cancelled");
    }
	
	public void search() {
        addMessage("Searched");
    }
	
	public void login() {
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
		RequestContext.getCurrentInstance().addCallbackParam("email", user.getEmail());
		RequestContext.getCurrentInstance().addCallbackParam("password", user.getPassword());
    }
	
	public void updateToken() {
		Map<String, String> paramValues = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String token = paramValues.get("token");
		
		FirebaseUser user =  getUser();
		user.setToken(token);
	}

	public void update() {
		PatientRestApi.updatePatient(selected, user.getToken());
		
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
		 
        addMessage("Patient updated");
    }
	
	public void delete() {
		PatientRestApi.deletePatient(Long.toString(selected.getId()), user.getToken());
		
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
        addMessage("Patient deleted");
    }
	
	public void deactivate() {
		selected.setStatus("Inactive");
		
		PatientRestApi.updatePatient(selected, user.getToken());
		
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
        addMessage("Patient deactivated");
    }
	
	public void onRowSelect(SelectEvent event) {
		Patient patient = (Patient) event.getObject();
		List<Address> addresses = patient.getAddresses();
		
		// initialize empty address
		if (addresses.size() == 0) {
			addresses.add(new Address());
			addresses.add(new Address());
		} else if (addresses.size() == 1) {
			addresses.add(new Address());
		}
		selected.setAddresses(addresses);
    }
	
	public void isUserLogin(ComponentSystemEvent event){
		FirebaseUser user = getUser();
		if (Strings.isEmpty(user.getToken())) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
			try {
				response.sendRedirect("login.xhtml");
				FacesContext.getCurrentInstance().responseComplete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
