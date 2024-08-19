    package com.springBatch.POC.Config;

    import com.springBatch.POC.entity.Customer;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.batch.core.Job;
    import org.springframework.batch.core.Step;
    import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
    import org.springframework.batch.core.job.builder.JobBuilder;
    import org.springframework.batch.core.launch.support.RunIdIncrementer;
    import org.springframework.batch.core.repository.JobRepository;
    import org.springframework.batch.core.step.builder.StepBuilder;
    import org.springframework.batch.item.ItemProcessor;
    import org.springframework.batch.item.ItemReader;
    import org.springframework.batch.item.ItemWriter;
    import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
    import org.springframework.batch.item.database.JdbcBatchItemWriter;
    import org.springframework.batch.item.json.JacksonJsonObjectReader;
    import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.transaction.PlatformTransactionManager;

    import javax.sql.DataSource;
    @Configuration
    @Slf4j
    public class BatchConfig {


        @Autowired
        private DataSource dataSource;


        @Bean
        public ItemReader<Customer> jsonItemReader() {
            JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);

            return new JsonItemReaderBuilder<Customer>()
                    .jsonObjectReader(jsonObjectReader)
                    .resource(new ClassPathResource("sample_data.json"))
                    .name("customerJsonItemReader")
                    .build();
        }

        @Bean
        public Step jsonProcessingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
            return new StepBuilder("jsonProcessingStep", jobRepository)
                    .<Customer, Customer>chunk(10, transactionManager) // Specify chunk size here
                    .reader(jsonItemReader())
                    .processor(jsonItemProcessor())
                    .writer(jsonItemWriter(dataSource))
                    .build();
        }

        @Bean
        public Job jsonProcessingJob(JobRepository jobRepository, Step jsonProcessingStep) {
            return new JobBuilder("jsonProcessingJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(jsonProcessingStep)
                    .build();
        }

        @Bean
        public ItemProcessor<Customer, Customer> jsonItemProcessor() {
            return customer -> {
                System.out.println("Processing customer: " + customer.getEmail() + " " + customer.getFirstName() + " " + customer.getLastName());
                return customer;
            };
        }

        @Bean
        public ItemWriter<Customer> jsonItemWriter(DataSource dataSource) {
            JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
            itemWriter.setDataSource(dataSource);
            itemWriter.setSql("INSERT INTO customers_information (firstname, secondname, email) VALUES (:firstName, :lastName, :email)");
            itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
            itemWriter.afterPropertiesSet();

            return itemWriter;
        }


    }
