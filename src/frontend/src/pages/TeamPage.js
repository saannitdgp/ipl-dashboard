/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react-hooks/exhaustive-deps */
import {React, useEffect, useState} from 'react'
import './TeamPage.scss';
import { MatchDetailCard } from '../components/MatchDetailCard'
import { MatchSmallCard } from '../components/MatchSmallCard'
import { useParams } from 'react-router-dom'
import {PieChart} from 'react-minimal-pie-chart'

export const TeamPage =  ()  => {
    
    const [team, setTeam] = useState({matches : []});
    const {teamName} = useParams();
    useEffect(
        () => {
           
            const fetchMatches = async () => {
                var fetchURL = `http://localhost:8080/teams/${teamName}`;
                const response = await fetch(fetchURL);
                const data = await response.json();

                setTeam(data);

            };
            fetchMatches();
        },[teamName] );
    if( !team || !team.teamName) return <h1> Team Not Found</h1>    
  return (
    <div className="TeamPage">
     <div className="team-name-section">
         <h1 className="team-name">{team.teamName}</h1>
    </div>
    <div className="win-loss-section">
         Wins/Losses
         <PieChart 
            data={[
             { title: 'Loss', value: team.totalMatchPlayed - team.totalWins, color: '#a34d5d'},
             { title: 'Win', value: team.totalWins, color: '#4da375' }
         ]}
      />
   </div>
    <div className = "match-detail-section">
       <h3> Latest Matches</h3>
       <MatchDetailCard teamName ={team.teamName} match ={team.matches[0]}/>
    </div>
     { 
         team.matches.slice(1).map(match => <MatchSmallCard teamName = {team.teamName} match = {match}/>)
     }
    <div className="more-link"> 
         <a href="#"> More ></a>
    </div>
    </div>
  );
}
