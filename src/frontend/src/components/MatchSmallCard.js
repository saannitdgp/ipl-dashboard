import { React } from 'react'
import {Link} from 'react-router-dom'

export const MatchSmallCard =  (props)  => {
  if(!props.match) return null;
  const oppositionTeamName = props.teamName === props.match.team1 ? props.match.team2 : props.match.team1;
  const oppositionTeamRoute = `/teams/${oppositionTeamName}`;
  return (
    <div className="MatchSmallCard">
     <h3>Vs <Link to = {oppositionTeamRoute}> {oppositionTeamName}</Link></h3>
     <p>{props.match.matchWinner} won by {props.match.resultMargin} {props.match.result}</p>
    </div>
  );
}
