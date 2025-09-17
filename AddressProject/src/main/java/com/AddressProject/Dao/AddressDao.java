package com.AddressProject.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AddressProject.Entity.Address;

public interface AddressDao  extends JpaRepository<Address, Integer>{
	
	
//	Here By Passing EmployeeId We Are Getting the Address Object By Wrtiting the Query Externally...
//  @Query Annotation :- It is an annotation provided by Spring Data JPA. ----> Used inside a Repository interface to define custom queries.
//  It Supports: 1. JPQL (Java Persistence Query Language → entity-based, portable).  2.Native SQL (actual database SQL).
	
//	Types of Queries
//	1. JPQL (default)
//  it will take Entity Or class name as Table Name and Varible name as Column name It won't consider @Table or @clolumn........
	
//	2. Native SQL (nativeQuery=true)
//	Executes raw SQL directly in the DB.(Means It will consider table name  as Mentioned in @table Annotation and Column name as Mentioned inside @column annotation...
//	Needed if:You want to use DB-specific functions (like TO_CHAR, ILIKE, LIMIT in PostgreSQL). and You want full control of SQL.
	@Query(nativeQuery = true, 
			  value = "SELECT ea.addressid, ea.lane1, ea.lane2, ea.state, ea.zip " +
			          "FROM xaddressbean ea " +
			          "INNER JOIN xemployee e ON e.id = ea.employee_id " +
			          "WHERE e.id = :employee_id")
			Address findAddressByEmployeeId(@Param("employee_id") int employeeId);

}
