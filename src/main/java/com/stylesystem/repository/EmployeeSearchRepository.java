package com.stylesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stylesystem.model.Employee;


public interface EmployeeSearchRepository extends JpaRepository<Employee,Integer>{
	Employee findByEmpId(int empId);
	List<Employee> findByEmpNameContaining(String empname);
	Employee findByEmpIdAndDeleteFlag(int empId,int deleteFlag);
	List<Employee> findByEmpNameAndDeleteFlag(String empname,int deleteFlag);
	List<Employee> findByDeleteFlag(int deleteFlag);
}
