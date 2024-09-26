package com.stylesystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stylesystem.model.Employee;
import com.stylesystem.service.EmployeeSearchService;

@Controller
public class AccountManageController {
    private final EmployeeSearchService employeeSearchService;

    public AccountManageController(EmployeeSearchService employeeSearchService) {
        this.employeeSearchService = employeeSearchService;
    }

    @GetMapping("/accountManage")
    public String showAccountSearchPage(Model model) {
        List<Employee> allEmployees = employeeSearchService.findAllEmployees();
        model.addAttribute("searchResults", allEmployees);
        return "accountManage";
    }

    @GetMapping("/empSearchBtn")
    public String searchEmployee(@RequestParam String selectType,
                                 @RequestParam(value = "empId", required = false) Integer empId,
                                 @RequestParam(name = "empName", required = false) String empName,
                                 @RequestParam int deleteFlag, Model model) {

        if (selectType.equals("empId")) {
            List<Employee> searchResults = employeeSearchService.searchByEmpId(empId, deleteFlag);
            model.addAttribute("searchResults", searchResults);
        } else if (selectType.equals("empName")) {
            List<Employee> searchResults = employeeSearchService.searchByEmpName(empName, deleteFlag);
            model.addAttribute("searchResults", searchResults);
        }
        return "accountManage";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Integer empId) {
        employeeSearchService.deleteEmployee(empId);
        return "redirect:/accountManage";
    }

    @PostMapping("/restoreEmployee")
    public String restoreEmployee(@RequestParam Integer empId) {
        employeeSearchService.restoreEmployee(empId);
        return "redirect:/accountManage";
    }
}
