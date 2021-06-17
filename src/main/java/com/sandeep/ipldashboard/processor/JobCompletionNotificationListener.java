package com.sandeep.ipldashboard.processor;

import com.sandeep.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamData = new HashMap<>();

            String sq1 = "select m1.team1, count(*) from Match m1 group by m1.team1"; // total match played by any team by batted first
            String sq2 = "select m2.team2, count(*) from Match m2 group by m2.team2";// total match played by any team by batted second
            /**
             * first calculate the total match played by any team.
             *
             * 1. team1 --> who batted first --> calculate by sql1
             * 2. team2 --> who batted second --> calculate by sql2
             * 3. total match played by any team --> count_from_sql1 + count_from_sql_2
             */
            entityManager.createQuery(sq1, Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team(String.valueOf(e[0]), (long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            entityManager.createQuery(sq2, Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e ->{
                        Team team = teamData.get(String.valueOf(e[0]));
                        // might be some team which never batted first.
                        team.setTotalMatchPlayed(team.getTotalMatchPlayed() + (long) e[1]);
                    });
            entityManager.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner",
                    Object[].class)
                    .getResultList()
                    .stream()
                    // gives any how many times any team won the match.
                    .forEach((e) -> {
                        Team team = teamData.get((String)e[0]);
                        if(team != null) {
                            team.setTotalWins((long) e[1]);
                        }
                    });
           LOGGER.info("Data Saved into DB Successfully!!");
           teamData.values().forEach(team -> entityManager.persist(team));
        }
    }
}