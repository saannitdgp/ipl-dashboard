import {React} from 'react'

export const MatchDetailCard =  (props)  => {
  if(!props.match) return <div></div>
  const oppositionTeamName = props.teamName === props.match.team1 ? props.match.team2 : props.match.team1;
  return (
    <div className="MatchDetailCard">

     <h3> Latest Matches</h3>
     <h1>Vs {oppositionTeamName} </h1>
     <h2>{props.match.date}</h2>
     <h3>at {props.match.venue}</h3>
     <h3>Winner {props.match.matchWinner} won by {props.match.resultMargin} {props.match.result}</h3>
    </div>
  );
}
