package com.cowboy76.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.cowboy76.batch.CustomItemProcessor;
import com.cowboy76.batch.mapper.ReportMapper;
import com.cowboy76.batch.model.Report;

public class DbToXmlWriterConfiguration {
	
	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private JobBuilderFactory jobBuilders;
	
	@Autowired
	private DataSourceConfiguration dataSourceConfiguration;

	@Bean
	public ItemReader<Report> reader() {
		
/*		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
	    provider.setSelectClause("SELECT ID, SALES, QTY, STAFF_NAME, DATE");
	    provider.setFromClause("FROM REPORT");
	    
	    Map<String, Order> sortKeys = new LinkedHashMap<String, Order>();
		sortKeys.put("ID", Order.ASCENDING);
		provider.setSortKeys(sortKeys);	  */  
	    
		JdbcCursorItemReader<Report> reader = new JdbcCursorItemReader<Report>();
		reader.setDataSource(dataSourceConfiguration.dataSource());
		reader.setRowMapper(new ReportMapper());
		//reader.setQueryProvider(provider);
		reader.setSql("SELECT ID, SALES, QTY, STAFF_NAME, DATE FROM REPORT ORDER BY ID ASC");
		return reader;
	}
	
 
	@Bean
	public ItemProcessor<Report, Report> processor() {
		return new CustomItemProcessor();
	}

	@Bean
	public ItemWriter<Report> writer() {
		StaxEventItemWriter<Report> writer = new StaxEventItemWriter<Report>();
		writer.setResource(new FileSystemResource("xml/outputs/DbToXmlReport.xml"));
		writer.setRootTagName("report");
		writer.setMarshaller(unmarshaller());
		return writer;
	}

	@Bean
	public Marshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Report.class);
		return marshaller;
	}

	@Bean
	public Job dbToXmlJob() {
		return jobBuilders.get("dbToXmlJob").start(step()).build();
	}

	@Bean
	public Step step() {
		return stepBuilders.get("step").<Report, Report> chunk(1).reader(reader()).processor(processor()).writer(
			writer()).build();
	}

}
