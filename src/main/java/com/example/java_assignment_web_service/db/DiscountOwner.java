package com.example.java_assignment_web_service.db;

public class DiscountOwner {
	private int id;
	private int discountId;
	private String discountCode;
	private int usageAllowed;
	private int userid;
	
	
	public DiscountOwner(int id, int discountId, String discountCode, int usageAllowed, int userid) {
		super();
		this.id = id;
		this.discountId = discountId;
		this.discountCode = discountCode;
		this.usageAllowed = usageAllowed;
		this.userid = userid;
	}
	
	
	public DiscountOwner() {
		//super();
		// empty
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDiscountId() {
		return discountId;
	}
	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	public int getUsageAllowed() {
		return usageAllowed;
	}
	public void setUsageAllowed(int usageAllowed) {
		this.usageAllowed = usageAllowed;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
}
