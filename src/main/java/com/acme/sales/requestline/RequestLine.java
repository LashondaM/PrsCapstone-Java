package com.acme.sales.requestline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.acme.sales.product.Product;
import com.acme.sales.request.Request;

@Entity(name="requestlines")
public class RequestLine {
	
	// Primary Key
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	// Foreign Keys
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId")
	public Request request;
	
	//==========
	
	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	public Product product;
	
	private int quantity;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public RequestLine() {}
}
