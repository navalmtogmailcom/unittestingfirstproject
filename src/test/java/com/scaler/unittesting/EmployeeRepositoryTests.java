package com.scaler.unittesting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    //Junit test for save employee
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){
        Employee employee = Employee.builder()
                .firstname("Ramesh")
                .lastname("Kumar")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);
        Assertions.assertThat(employee.getId()).isGreaterThan(0);

    }
    @Test
    @Order(2)
    public void getEmployeeTest(){
        Employee employee = employeeRepository.findById(1L).get();
        Assertions.assertThat(employee.getId()).isEqualTo(1L);

    }
    @Test
    @Order(3)
    public void getListOfEmployeeTest(){
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployee(){
        Employee employee = employeeRepository.findById(1L).get();
        employee.setEmail("ram@gmal.com");
        Employee updatedEmployee = employeeRepository.save(employee);
        Assertions.assertThat(updatedEmployee.getEmail().equalsIgnoreCase("ram@gmail.com"));
    }
       @Test
        @Order(5)
       @Rollback(value = false)
        public void deleteEmployee(){
            Employee employee = employeeRepository.findById(1L).get();
            employeeRepository.delete(employee);
            Employee employee1 = null;
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail("ram@gmail.com");
            if(optionalEmployee.isPresent()){
                employee1 = optionalEmployee.get();
            }
            Assertions.assertThat(employee1).isNull();
        }

}
