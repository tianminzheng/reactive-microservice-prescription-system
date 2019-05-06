package com.tianyalan.medicine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.tianyalan.medicine.model.Medicine;

public class MedicineTest {
	@Test
	public void testLombok() {

		Medicine product = new Medicine("001", "Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F);

		assertThat(product.getId()).isEqualTo("001");
		assertThat(product.getMedicineCode()).isEqualTo("Book001");
		assertThat(product.getPrice()).isEqualTo(100F);
	}
}


