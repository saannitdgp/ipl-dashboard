package com.sandeep.ipldashboard.processor;

import com.sandeep.ipldashboard.data.MatchInputData;
import com.sandeep.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final String [] FILED_NAMES = new String[] {
            "id", "city", "date", "player_of_match","venue","neutral_venue","team1", "team2",
            "toss_winner","toss_decision","winner","result","result_margin","eliminator","method",
            "umpire1","umpire2"
    };
    private final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInputData> reader() {
        return new FlatFileItemReaderBuilder<MatchInputData>()
                .name("personItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FILED_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInputData>() {{
                    setTargetType(MatchInputData.class);
                }})
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }


    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        String sql = "INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner," +
                "toss_decision, match_winner, result," +
                "result_margin, umpire1, umpire2) VALUES (:id, :city, :date, :playerOfMatch, :venue," +
                " :team1, :team2,:tossWinner, :tossDecision, :matchWinner, :result,:resultMargin, :umpire1, :umpire2)";
        LOGGER.info("SQL For saving record:{}", sql);
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(sql)
                .dataSource(dataSource)
                .build();
    }
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInputData, Match> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
