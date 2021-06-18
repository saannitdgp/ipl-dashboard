package com.sandeep.ipldashboard.controller;

import com.sandeep.ipldashboard.model.Match;
import com.sandeep.ipldashboard.model.Team;
import com.sandeep.ipldashboard.repository.MatchRepository;
import com.sandeep.ipldashboard.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        LOGGER.info("Request for Matches played by team :{}", teamName);
        Team response = teamRepository.getTeamByTeamName(teamName);
        response.setMatches(matchRepository.getLatestMatchByTeam(teamName, 4));
        return response;
    }

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getMatchForTeam(@PathVariable String teamName, @RequestParam int year){
        LOGGER.info("Request for Match played by team : {} in year : {}", teamName, year);
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year,12,31);
        return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
    }
}
