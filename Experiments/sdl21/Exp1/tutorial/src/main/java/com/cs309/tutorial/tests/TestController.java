package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
	
	TestData savedData = new TestData("");

	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hello, %s! You sent a get request with a parameter!", message);
	}

	@GetMapping("/getSaved")
	public String getSaved(){
		return String.format("Hello! You sent a get request to see the saved message: %s", savedData.getMessage());
	}
	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hello, %s! You sent a post request with a parameter!", message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody TestData testData) {
		savedData.setMessage(testData.getMessage());
		return String.format("Hello, %s! You sent a post request with a requestbody!", testData.getMessage());
	}
	
	@DeleteMapping("/deleteTest")
	public String deleteTest() {
		savedData.setMessage("");
		return "Hello! You sent a delete request, so the saved message is now empty!";
	}
	
	@PutMapping("/putTest/{message}")
	public String putTest(@PathVariable String message) {
		savedData.setMessage(message);
		return String.format("You sent a put request, so the saved message has been changed to %s", message);
	}
}
