package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DemoApplicationTests {

	@SpringBootApplication
	@Configuration
	public static class Application {

		@Bean
		public ResourceConfig resourceConfig() {
			return new ResourceConfig() {
				@Override
				public ResourceConfig register(Object component) {
					return register(SampleResource.class);
				}
			};
		}
		/* Explicitly disable dispatcherServlet */
		@Bean(name = "dispatcherServlet")
		public String getDispatcherServlet() {
			return "";
		}

		public static void main(String[] args){
			SpringApplication.run(Application.class, args);
		}
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testDispatcherServletPathProvider() {
		ResponseEntity<String> responseEntity = this.restTemplate
				.exchange("/jersey/test", HttpMethod.GET, null, String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
