package com.example.demo.listener;

import com.example.demo.domaim.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessListener implements ItemProcessListener<Employee, Employee> {

    @Override
    public void beforeProcess(Employee item) {
        // Do Nothing
    }

    @Override
    public void afterProcess(Employee item, Employee result) {
        // Do Nothing
    }

    @Override
    public void onProcessError(Employee item, Exception e) {
        log.error("ProcessError: item={}, errorMessage={}",item, e.getMessage(),e);
    }
}
