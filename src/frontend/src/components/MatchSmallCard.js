import { React } from 'react'
import {Link} from 'react-router-dom'
import './scss/MatchSmallCard.scss';

export const MatchSmallCard =  (props)  => {
  if(!props.match) return null;
  const oppositionTeamName = props.teamName === props.match.team1 ? props.match.team2 : props.match.team1;
  const oppositionTeamRoute = `/teams/${oppositionTeamName}`;
  const isTeamWon = props.match.matchWinner === oppositionTeamName ? false : true;
  return (
    <div className= {isTeamWon ? 'MatchSmallCard win-card' : 'MatchSmallCard lost-card'}> 
     <span className="Vs"> vs</span>
     <h1><Link to = {oppositionTeamRoute}> {oppositionTeamName}</Link></h1>
     <p>{props.match.matchWinner} won by {props.match.resultMargin} {props.match.result}</p>
    </div>
  );
}
