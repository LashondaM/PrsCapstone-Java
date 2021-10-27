package com.acme.sales.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestsController {
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	private ResponseEntity<Iterable<Request>> GetAll(){
		var requests = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK); // await and Async would be used in C#
	}
	
	@GetMapping("{id}")
	private ResponseEntity<Request> GetById(@PathVariable int id){
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	@PostMapping
	private ResponseEntity<Request> Insert(@RequestBody Request request){
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		request.setId(0);
		var newRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	private ResponseEntity Update(@PathVariable int id, @RequestBody Request request) {
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var oldRequest = reqRepo.findById(request.getId());
		if(oldRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity Delete(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<Iterable<Request>> GetRequestNotUser(@PathVariable int userId){
		var requests = reqRepo.findByUserIdNot(userId);
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity SetOrderToZero(@PathVariable int id, @RequestBody Request request) {
		var newTotal = request.getTotal() <= 100 ? 0 : 1000;
		request.setTotal(newTotal);
		return Update(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity setOrderTo5000(@PathVariable int id, @RequestBody Request request) {
		request.setTotal(5000);
		return Update(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity setOrderToNegative5000(@PathVariable int id, @RequestBody Request request) {
		request.setTotal(-5000);
		return Update(id, request);
	}
}
