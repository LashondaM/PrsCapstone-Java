package com.acme.sales.request;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer>{
	List<Request> findByUserIdNot(int userId); // this will show all the orders that doesn't have the user ID you selected
}
