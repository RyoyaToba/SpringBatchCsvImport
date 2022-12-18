package com.example.demo.config;

import com.example.demo.domaim.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class JpaImportBatchConfig extends BaseConfig{
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaItemWriter<Employee> jpaWriter(){
        JpaItemWriter<Employee> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(this.entityManagerFactory);
        return writer;
    }

    @Bean
    public Step csvImportJpaStep(){
        return this.stepBuilderFactory.get("CsvImportJpaStep")
                .<Employee, Employee>chunk(10)
                .reader(csvReader()).listener(this.readListener)
                .processor(compositeProcessor()).listener(this.processListener)
                .writer(jpaWriter()).listener(this.writeListener)
                .build();
    }

    @Bean("JpaJob")
    public Job csvInportJpaJob(){
        return this.jobBuilderFactory.get("CsvImportJpaJob")
                .incrementer(new RunIdIncrementer())
                .start(csvImportJpaStep())
                .build();
    }
}
