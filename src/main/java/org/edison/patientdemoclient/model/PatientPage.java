package org.edison.patientdemoclient.model;

public class PatientPage {
	private int totalElements;
	private int totalPages;
	private int size;
	private int numberOfElements;
	
	private Patient[] content;

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Patient[] getContent() {
		return content;
	}

	public void setContent(Patient[] content) {
		this.content = content;
	}
}
