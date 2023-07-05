package com.edu.hutech.major.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
	private int id;

	private int orderId;

	private int productId;

	private int quantity;
}
