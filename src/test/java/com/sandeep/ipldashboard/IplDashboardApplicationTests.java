package com.sandeep.ipldashboard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

class IplDashboardApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public  void s(){
//		System.out.println(fun("As"));
//		System.out.println(fun(null));
	}

	private String fun(final String map) {
		assert (null != map) : "map can't be null";
		return "As";
	}

}
