package com.sandeep.ipldashboard.repository;

import com.sandeep.ipldashboard.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    /**
     * Spring JPA find the method and identifies what should be the implementations. It ignores till By.
     */
    Team getTeamByTeamName(String teamName);
}
