package com.edu.hutech.major.global;

import java.util.ArrayList;
import java.util.List;

import com.edu.hutech.major.model.Product;

public class GlobalData {
	// tao bien toan cuc
	public static List<Product> cart;

	public static List<Integer> quantity;

	static {
		cart = new ArrayList<>();
	}

	static {
		quantity = new ArrayList<>();
	}

}
