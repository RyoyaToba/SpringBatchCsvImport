package com.example.demo.config;

import com.example.demo.domaim.model.Employee;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

@EnableBatchProcessing
public abstract class BaseConfig {

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("GenderConvertProcessor")
    protected ItemProcessor<Employee, Employee> genderConvertProcessor;

    @Autowired
    protected ItemReadListener<Employee> readListener;

    @Autowired
    protected ItemProcessListener<Employee, Employee> processListener;

    @Autowired
    protected ItemWriteListener<Employee> writeListener;

    @Autowired
    protected SampleProperty property;

    @Bean
    @StepScope
    public FlatFileItemReader<Employee> csvReader(){
        String[] nameArray = new String[]{"id", "name", "age", "genderString"};

        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeCsvReader")
                .resource(new ClassPathResource(property.getCsvPath()))
                .linesToSkip(1)
                .encoding(StandardCharsets.UTF_8.name())
                .delimited()
                .names(nameArray)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
                    {
                        setTargetType(Employee.class);
                    }
                })
                .build();
        }
}
