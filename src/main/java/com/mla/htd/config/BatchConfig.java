package com.mla.htd.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mla.htd.entitty.Student;

@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public FlatFileItemReader<Student> studentItemReader() {
		FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/static/read.csv"));

		DefaultLineMapper<Student> customerLineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id", "name", "course", "teacher"});
		tokenizer.setDelimiter(",");
		
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new StudentFieldSetMapper());
		customerLineMapper.afterPropertiesSet();
		reader.setLineMapper(customerLineMapper);
		return reader;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public JdbcBatchItemWriter<Student> studentItemWriter() {
		JdbcBatchItemWriter<Student> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO STUDENT VALUES (:id, :course, :name, :teacher)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Student, Student>chunk(10)
				.reader(studentItemReader())
				.writer(studentItemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}

}
