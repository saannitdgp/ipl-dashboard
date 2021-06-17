package com.sandeep.ipldashboard.repository;

import com.sandeep.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    default List<Match> getLatestMatchByTeam(String teamName, int count){
        Pageable pageableRequest = PageRequest.of(0,4);
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageableRequest);
    }
}
