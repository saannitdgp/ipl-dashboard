/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react-hooks/exhaustive-deps */
import {React, useEffect, useState} from 'react'
import { TeamTile } from '../components/TeamTile';
import './HomePage.scss';

export const HomePage =  ()  => {
    
    const [teams, setTeams] = useState([]);
    useEffect(
        () => {
           
            const fetchTeams = async () => {
                var fetchURL = `http://localhost:8080/teams/`;
                const response = await fetch(fetchURL);
                const data = await response.json();

                setTeams(data);

            };
            fetchTeams();
        },[] );
  return (
    <div className="HomePage">
    <div className="header-section">
         <h1 className="app-name">IPL DashBoard</h1>
    </div>
    <div className="team-grid">
        {
            teams.map( team => <TeamTile teamName ={team.teamName}/>)
        }
    </div>

    </div>
  );
}
