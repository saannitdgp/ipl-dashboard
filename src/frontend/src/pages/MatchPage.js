/* eslint-disable react-hooks/exhaustive-deps */
import {React, useEffect, useState} from 'react'
import { useParams } from 'react-router-dom'

export const MatchPage =  ()  => {
    
    const [team, setTeam] = useState({matches : []});
    const {teamName, year} = useParams();

    useEffect(
        () => {
           
            const fetchMatches = async () => {
                var fetchURL = `http://localhost:8080/teams/${teamName}/matches/${year}`;
                console.log("fetch url for matches");
                const response = await fetch(fetchURL);
                const data = await response.json();

                setTeam(data);

            };
            fetchMatches();
        },[teamName] );
  return (
    <div className="MatchPage">
     <h1>{team.teamName}</h1>
     </div>
  );
}
