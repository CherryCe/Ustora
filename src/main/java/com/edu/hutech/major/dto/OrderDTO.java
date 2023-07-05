package com.edu.hutech.major.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDTO {
	private int id;

	private String serialNumber;

	private Date dateTime;

	private int userId;

	private double total;

	private String status;
}
