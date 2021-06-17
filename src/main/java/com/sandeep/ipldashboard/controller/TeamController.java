package com.sandeep.ipldashboard.controller;

import com.sandeep.ipldashboard.model.Team;
import com.sandeep.ipldashboard.repository.MatchRepository;
import com.sandeep.ipldashboard.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        LOGGER.info("Request for Team :{}", teamName);
        Team response = teamRepository.getTeamByTeamName(teamName);
        response.setMatches(matchRepository.getLatestMatchByTeam(teamName, 4));
        LOGGER.info("Response for Team :{}", response);
        return response;
    }
}
