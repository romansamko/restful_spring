package ru.rsamko.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.rsamko.rest.models.Brand;
import ru.rsamko.rest.models.BrandRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BrandRepository brandRepository;

	@Before
	public void setUp() throws Exception {
		brandRepository.deleteAll();
		String[] brands = { "test1", "test2" };
		for (String brand: brands) {
			Brand newBrand = new Brand();
			newBrand.brand = brand;
			this.brandRepository.save(newBrand);
		}
	}

	@Test
	public void brandControllerTest() throws Exception {
		String expectedResult = "[{\"id\":4,\"brand\":\"test1\"},{\"id\":5,\"brand\":\"test2\"}]";
		mockMvc.perform(get("/brand"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResult));
	}
}