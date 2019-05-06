package com.tianyalan.medicine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Medicine {
	@Id
	private String id;
	
	private String medicineCode;
	private String medicineName;
	private String description;
	private Float price;
}

