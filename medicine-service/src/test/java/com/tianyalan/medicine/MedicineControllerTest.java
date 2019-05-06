package com.tianyalan.medicine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.tianyalan.medicine.controllers.MedicineController;
import com.tianyalan.medicine.model.Medicine;
import com.tianyalan.medicine.services.MedicineService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = MedicineController.class)
public class MedicineControllerTest {

	@Autowired
	WebTestClient webClient;

	@MockBean
	MedicineService service;

	@Test
	public void testGetByMedicineCode() {
		Medicine mockMedicine = new Medicine("001", "Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F);

		given(service.getMedicineByCode("Book001")).willReturn(Mono.just(mockMedicine));

		EntityExchangeResult<Medicine> result = webClient.get()
				.uri("http://localhost:8081/v1/medicines/{medicineCode}", "Book001").exchange().expectStatus()
				.isOk().expectBody(Medicine.class).returnResult();

		verify(service).getMedicineByCode("Book001");
		verifyNoMoreInteractions(service);
		
		assertThat(result.getResponseBody().getMedicineCode()).isEqualTo("Book001");
	}
	
	
	@Test
	public void testDeleteMedicine() {
		given(service.deleteMedicineById("001")).willReturn(Mono.empty());

		webClient.delete()
				.uri("http://localhost:8081/v1/medicines/{medicineId}", "001").exchange().expectStatus()
				.isOk().expectBody(Void.class).returnResult();

		verify(service).deleteMedicineById("001");
		verifyNoMoreInteractions(service);
	}

}

