/* eslint-disable react-hooks/exhaustive-deps */
import {React, useEffect, useState} from 'react'
import { useParams } from 'react-router-dom'
import { MatchDetailCard } from '../components/MatchDetailCard';
import { YearSelector } from '../components/YearSelector';
import './MatchPage.scss';

export const MatchPage =  ()  => {
    
    const [matches, setMatches] = useState([]);
    const {teamName, year} = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                var fetchURL = `${process.env.REACT_APP_API_ROOT_URL}/teams/${teamName}/matches?year=${year}`;
                const response = await fetch(fetchURL);
                const data = await response.json();
                setMatches(data);
            };
        fetchMatches(); 
        },[teamName, year] );
  return (
    <div className="MatchPage">
        <div className="year-selector">
            <h4>Select Years</h4>
            <YearSelector teamName={teamName}/>
        </div>
        <div> 
        <h1 className="page-heading">{teamName} matches in {year}</h1>
        { 
          matches.map(match => <MatchDetailCard  key = {match.id} teamName = {teamName} match = {match}/>)
        }
       </div>
     </div>
  );
}
