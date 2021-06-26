import './App.scss';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';
import { HomePage } from './pages/HomePage';
import { TeamPage } from './pages/TeamPage';
import { MatchPage } from './pages/MatchPage';
import {Helmet} from 'react-helmet'

function App() {  
  return (
    <div className="App"> 
     <Helmet>
        <title>IPL Dashboard</title>
      </Helmet>
     <Router>
       <Switch>
         <Route path = "/teams/:teamName/matches/:year">
           <MatchPage/>
          </Route>
          <Route path = "/teams/:teamName">
            <TeamPage/>
          </Route>
          <Route path = "/">
            <HomePage/>
          </Route>
       </Switch>
      </Router>
    </div>
  );
}

export default App;
