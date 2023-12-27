package com.nhom1.model;

public class PaymentDetails {

	private String paymentMethod;
	
	private String status;
	
	private String paymentId;
	
	private String razorpayPaymentLinkId;
	
	private String razorpayPaymentReferenceId;
	
	private String razorpayPaymentLinkStatus;
	
	private String razorpayPaymentId;

	public PaymentDetails() {
		// TODO Auto-generated constructor stub
	}
	
	public PaymentDetails(String paymentMethod, String status, String paymentId, String razorpayPaymentLinkId,
			String razorpayPaymentReferenceId, String razorpayPaymentLinkStatus, String razorpayPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.paymentId = paymentId;
		this.razorpayPaymentLinkId = razorpayPaymentLinkId;
		this.razorpayPaymentReferenceId = razorpayPaymentReferenceId;
		this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getRazorpayPaymentLinkId() {
		return razorpayPaymentLinkId;
	}

	public void setRazorpayPaymentLinkId(String razorpayPaymentLinkId) {
		this.razorpayPaymentLinkId = razorpayPaymentLinkId;
	}

	public String getRazorpayPaymentReferenceId() {
		return razorpayPaymentReferenceId;
	}

	public void setRazorpayPaymentReferenceId(String razorpayPaymentReferenceId) {
		this.razorpayPaymentReferenceId = razorpayPaymentReferenceId;
	}

	public String getRazorpayPaymentLinkStatus() {
		return razorpayPaymentLinkStatus;
	}

	public void setRazorpayPaymentLinkStatus(String razorpayPaymentLinkStatus) {
		this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	
	
}

