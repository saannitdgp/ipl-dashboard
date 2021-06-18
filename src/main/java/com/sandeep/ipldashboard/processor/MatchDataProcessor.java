package com.sandeep.ipldashboard.processor;

import com.sandeep.ipldashboard.data.MatchInputData;
import com.sandeep.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

/**
 * Match Data processor class for processing data and putting
 * into DB with Match (Pojo class) format.
 */
public class MatchDataProcessor implements ItemProcessor<MatchInputData, Match> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInputData inputData) throws Exception {
        Match match = new Match();
        match.setCity(inputData.getCity());
        match.setId(Long.valueOf(inputData.getId()));
        match.setDate(LocalDate.parse(inputData.getDate()));
        match.setPlayerOfMatch(inputData.getPlayer_of_match());
        match.setVenue(inputData.getVenue());
        if("bat".equals(inputData.getToss_decision())){
            match.setTeam1(inputData.getToss_winner());
            match.setTeam2(inputData.getTeam2().equals(inputData.getToss_winner()) ? inputData.getTeam1() : inputData.getTeam2());
        }
        else {
            match.setTeam1(inputData.getTeam1().equals(inputData.getToss_winner()) ? inputData.getTeam2() : inputData.getTeam1());
            match.setTeam2(inputData.getToss_winner());
        }
        match.setMatchWinner(inputData.getWinner());
        match.setTossWinner(inputData.getToss_winner());
        match.setTossDecision(inputData.getToss_decision());
        match.setResult(inputData.getResult());
        match.setResultMargin(inputData.getResult_margin());
        match.setUmpire1(inputData.getUmpire1());
        match.setUmpire2(inputData.getUmpire2());
        return match;
    }
}