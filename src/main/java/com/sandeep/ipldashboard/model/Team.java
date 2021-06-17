package com.sandeep.ipldashboard.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id
    /**
     * @link
     * :https://stackoverflow.com/questions/38853317/spring-jpa-a-different-object-with-the-same-identifier-value-was-already-assoc
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String teamName;

    private long totalMatchPlayed;

    private long totalWins;
    /**
     * ava's transient keyword is used to denote that a field is not to be serialized,
     * whereas JPA's @Transient annotation is used to indicate that a field is not to be
     * persisted in the database, i.e. their semantics are different.
     * https://stackoverflow.com/questions/2154622/why-does-jpa-have-a-transient-annotation
     */
    @Transient
    private List<Match> matches;

    public Team(String teamName, long totalMatchPlayed) {
        this.teamName = teamName;
        this.totalMatchPlayed = totalMatchPlayed;
    }
    public Team(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTotalMatchPlayed() {
        return totalMatchPlayed;
    }

    public void setTotalMatchPlayed(long totalMatchPlayed) {
        this.totalMatchPlayed = totalMatchPlayed;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(long totalWins) {
        this.totalWins = totalWins;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
