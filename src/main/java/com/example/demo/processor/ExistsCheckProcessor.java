package com.example.demo.processor;

import com.example.demo.domaim.model.Employee;
import com.example.demo.repository.EmployeeJdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ExistsCheckProcessor")
@StepScope
@Slf4j
public class ExistsCheckProcessor implements ItemProcessor<Employee, Employee> {
    @Autowired
    private EmployeeJdbcRepository employeeRepository;

    @Override
    public Employee process(Employee item) throws Exception {

        boolean exists = employeeRepository.exists(item.getId());

        if(exists){
            log.info("Skip because it already exists: {}", item);
            return null;
        }

        return item;
    }
}
