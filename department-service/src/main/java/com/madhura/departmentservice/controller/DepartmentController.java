package com.madhura.departmentservice.controller;

import com.madhura.departmentservice.client.EmployeeClient;
import com.madhura.departmentservice.model.Department;
import com.madhura.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department){
        LOGGER.info("Department add: {}",department);
        return repository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("Department Find");
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id){
        LOGGER.info("Department find: id={}",id);
        return repository.findByID(id);
    }

    @GetMapping("with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("Department Find");
        List<Department> departments = repository.findAll();
        departments.forEach(department ->
                department.setEmployees(employeeClient.findByDepartment(department.getId())));
        return departments;
    }

}
