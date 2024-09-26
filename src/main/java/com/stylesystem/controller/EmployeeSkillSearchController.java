package com.stylesystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeSearchRepository;
import com.stylesystem.repository.EmployeeSkillSearchRepository;

@Controller
public class EmployeeSkillSearchController {
	private final EmployeeSearchRepository employeeSearchRepository;
	private final EmployeeSkillSearchRepository employeeSkillSearchRepository;
	
	public EmployeeSkillSearchController(EmployeeSearchRepository employeeSearchRepository,EmployeeSkillSearchRepository employeeSkillSearchRepository) {
		this.employeeSearchRepository = employeeSearchRepository;
		this.employeeSkillSearchRepository = employeeSkillSearchRepository;
	}
	
	@GetMapping
	 public String showAccountSearchPage(Model model) {
		   List<Employee> allEmployees = employeeSearchRepository.findAll();
	       model.addAttribute("searchResults", allEmployees); // 全社員リストをモデルに追加
     return "accountManage";
 }
}
