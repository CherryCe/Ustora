package com.edu.hutech.major.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orders_id")
	private int id;

	private String serialNumber;

	private Date dateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_order_id", referencedColumnName = "users_id")
	private User user;

	private double total;

	private String status;

}// create table mapping trong db
