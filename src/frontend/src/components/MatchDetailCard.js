import {React} from 'react'
import {Link} from 'react-router-dom'
import './scss/MatchDetailCard.scss'
export const MatchDetailCard =  (props)  => {
  if(!props.match) return <div></div>
  const oppositionTeamName = props.teamName === props.match.team1 ? props.match.team2 : props.match.team1;
  const oppositionTeamRoute = `/teams/${oppositionTeamName}`;
  const isTeamWon = props.match.matchWinner === oppositionTeamName ? false : true;
  return (
    <div className= {isTeamWon ? 'MatchDetailCard win-card' : 'MatchDetailCard lost-card'}> 
    <div>
       <span className="Vs"> vs</span>
       <h3><Link to = {oppositionTeamRoute}> {oppositionTeamName}</Link></h3>
       <h2 className="match-date">{props.match.date}</h2>
       <h3 className ="match-venue">at {props.match.venue}</h3>
       <h3 className="match-result">{props.match.matchWinner} won by {props.match.resultMargin} {props.match.result}</h3>
    </div>
    <div className="additional-detail">
      <h3>First Innings</h3>
      <p>{props.match.team1}</p>
      <h3>Second Innings</h3>
      <p>{props.match.team2}</p>
      <h3>Man of the Match</h3>
      <p>{props.match.playerOfMatch}</p>
      <h3>Umpires</h3>
      <p>{props.match.umpire1}, {props.match.umpire2}</p>
    </div>
  </div>
  );
}
