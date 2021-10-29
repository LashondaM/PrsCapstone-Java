package com.acme.sales.requestline;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLineRepository extends JpaRepository<RequestLine, Integer>{
	List<RequestLine> findRequestLineByRequestId(int requestId);
}
