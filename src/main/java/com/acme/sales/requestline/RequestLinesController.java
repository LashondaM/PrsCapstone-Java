package com.acme.sales.requestline;

import java.math.BigDecimal;

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

import com.acme.sales.product.ProductRepository;
import com.acme.sales.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestLinesController {
	
	@Autowired
	private RequestLineRepository rlineRepo;
	@Autowired
	private RequestRepository reqRepo;
	@Autowired
	private ProductRepository proRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<RequestLine>> GetAll(){
		var requestlines = rlineRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(requestlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> GetById(@PathVariable int id){
		var requestline = rlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(requestline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RequestLine> Insert(@RequestBody RequestLine requestline) throws Exception{
		if(requestline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		requestline.setId(0);
		var newRequestline = rlineRepo.save(requestline);
		RecalculateRquestTotal(requestline.getRequest().getId());
		return new ResponseEntity<RequestLine>(newRequestline, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity Update(@PathVariable int id, @RequestBody RequestLine requestline) throws Exception {
		if(requestline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var oldRequestline = rlineRepo.findById(requestline.getId());
		if(oldRequestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		rlineRepo.save(requestline);
		RecalculateRquestTotal(requestline.getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity Delete(@PathVariable int id) throws Exception {
		var requestline = rlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		rlineRepo.deleteById(id);
		RecalculateRquestTotal(requestline.get().getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private void RecalculateRquestTotal(int requestId) throws Exception {
		var optRequest = reqRepo.findById(requestId);
		if(optRequest.isEmpty()) {
			throw new Exception("RecalculateRequestTotal recieved an invalid request id!");
		}
		var request = optRequest.get();
		var requestlines = rlineRepo.findRequestLineByRequestId(requestId);
		var total = BigDecimal.ZERO;
		for(var requestline : requestlines) {
			if(requestline.getProduct().getPrice() == null) {
				var prodId = requestline.getProduct().getId();
				var product = proRepo.findById(prodId).get();
				requestline.setProduct(product);
			}
			var quantity = requestline.getQuantity();
			var price = requestline.getProduct().getPrice();
			total = total.add(BigDecimal.valueOf(quantity*price));
		}
		request.setTotal(total.doubleValue());
		reqRepo.save(request);
	}
	
}
