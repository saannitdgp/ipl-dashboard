package com.sandeep.ipldashboard.repository;

import com.sandeep.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    /**
     * We can also provide exact query (@Query Annotation) which should be run to JPA. so JPA will not create any query on the basis of
     * method names;
     */
    @Query("select m from Match m where (m.team1 = :teamName or team2 =: teamName) and m.date between :sDate and :eDate")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
                                             @Param("sDate") LocalDate sDate, @Param("eDate") LocalDate eDate);

//    List<Match> getByTeam1OrTeam2AndDateBetweenOrderByDateDesc(@Param("teamName") String teamName, 
//                                                               @Param("sDate") LocalDate sDate, @Param("eDate") LocalDate eDate);
    default List<Match> getLatestMatchByTeam(String teamName, int count){
        Pageable pageableRequest = PageRequest.of(0,count);
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageableRequest);
    }
}
