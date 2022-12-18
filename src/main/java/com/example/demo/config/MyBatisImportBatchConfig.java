package com.example.demo.config;

import com.example.demo.domaim.model.Employee;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisImportBatchConfig extends BaseConfig {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public MyBatisBatchItemWriter<Employee> myBatisWrite(){
        return new MyBatisBatchItemWriterBuilder<Employee>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("com.example.demo.repository.EmployeeMapper.insertOne")
                .build();
    }

    @Bean
    public Step csvImportMyBatisStep(){
        return this.stepBuilderFactory.get("CsvImportMyBatisStep")
                .<Employee, Employee> chunk(10)
                .reader(csvReader()).listener(this.readListener)
                .processor(compositeProcessor()).listener(this.processListener)
                .writer(myBatisWrite()).listener(this.writeListener)
                .build();
    }

    @Bean("MyBatisJob")
    public Job csvImportMyBatisJob(){
        return this.jobBuilderFactory.get("CsvImportMyBatisJob")
                .incrementer(new RunIdIncrementer())
                .start(csvImportMyBatisStep())
                .build();
    }
}
