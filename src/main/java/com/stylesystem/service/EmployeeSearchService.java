package com.stylesystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeSearchRepository;

@Service
public class EmployeeSearchService {
    private final EmployeeSearchRepository employeeSearchRepository;

    public EmployeeSearchService(EmployeeSearchRepository employeeSearchRepository) {
        this.employeeSearchRepository = employeeSearchRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeSearchRepository.findAll();
    }

    public List<Employee> searchByEmpId(Integer empId, int deleteFlag) {
        if (empId == null) {
            return employeeSearchRepository.findByDeleteFlag(deleteFlag);
        }
        if (deleteFlag == 0) {
            return List.of(employeeSearchRepository.findByEmpIdAndDeleteFlag(empId, deleteFlag));
        } else {
            return List.of(employeeSearchRepository.findByEmpId(empId));
        }
    }

    public List<Employee> searchByEmpName(String empName, int deleteFlag) {
        if (empName == null || empName.isEmpty()) {
            return employeeSearchRepository.findByDeleteFlag(deleteFlag);
        }
        if (deleteFlag == 0) {
            return employeeSearchRepository.findByEmpNameAndDeleteFlag(empName, deleteFlag);
        } else {
            return employeeSearchRepository.findByEmpNameContaining(empName);
        }
    }

    public void deleteEmployee(Integer empId) {
        Employee employee = employeeSearchRepository.findByEmpId(empId);
        if (employee != null) {
            employee.setDeleteFlag(1);
            employeeSearchRepository.save(employee);
        }
    }

    public void restoreEmployee(Integer empId) {
        Employee employee = employeeSearchRepository.findByEmpId(empId);
        if (employee != null) {
            employee.setDeleteFlag(0);
            employeeSearchRepository.save(employee);
        }
    }
}
