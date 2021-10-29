package com.acme.sales.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.acme.sales.vendor.Vendor;

@Table(uniqueConstraints=@UniqueConstraint(name="UIDX_prtNbr", columnNames= {"prtNbr"}))
@Entity(name="products")
public class Product {

	// Primary Key
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=30, nullable=false)
	private String prtNbr;
	
	@Column(length=30, nullable=false)
	private String name;
	
	@Column(columnDefinition="decimal(11,2) DEFAULT 0.0")
	private Double price;
	
	@Column(length=30, nullable=false)
	private String unit;
	
	@Column(length=255, nullable=true)
	private String photoPath;
	
	// Foreign Key
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId")
	public Vendor vendor;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrtNbr() {
		return prtNbr;
	}

	public void setPrtNbr(String prtNbr) {
		this.prtNbr = prtNbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Product() {}
}
