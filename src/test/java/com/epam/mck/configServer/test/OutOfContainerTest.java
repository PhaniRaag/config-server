package com.epam.mck.configServer.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.mck.configServer.ConfigServerApplication;

/**
 * Out-of-container test for the config server.
 * Verifies that the server serves up configuration when asked.
 * Uses "native" profile to obtain properties from local file system rather than GitHub.
 * 
 * @author Raghavendra_Phani
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigServerApplication.class)
@WebAppConfiguration
@ActiveProfiles("native")	//	"native" means use local classpath location rather than GitHub.
public class OutOfContainerTest {

	@Autowired WebApplicationContext spring;
	MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(spring).build();
	}
	
	/**
	    To test if this config server is working, we will simulate a "testConfig" client
		calling to get properties for its default profile.  
		These configuration files (application.yml and testConfig.properties) are on the 
		classpath as the server is 	running the 'native' profile.
		Check that the test values from the yml and properties file are present
	 * */
	@Test
	public void propertyLoadTest() throws Exception {

		
		MvcResult result =
			mockMvc.perform(get("/testConfig-default.properties"))
			.andExpect(status().isOk())
			.andReturn() 
			;
		
		String returned = result.getResponse().getContentAsString();
		assertTrue(returned.contains("testProperty:"));
		assertTrue(returned.contains("testValue"));
		assertTrue(returned.contains("validation.success:"));
		assertTrue(returned.contains("Password is accepted."));
		assertFalse(returned.contains("some gibberish"));
	}
}
