import { React , useState} from 'react';
import { AppContext } from "../../contexts/AppContext";
import TeamDetailPopup from "./teamDetailPopup";

import './css/teamBox.css';

import lolLogo from "../images/LeagueLogo.png"
import csgoLogo from "../images/csgoLogo.png"

import Popup from "../popup/popup.js";

/**
 * @component
 * @description A component for displaying team names and their game 
 * @property {object} teams - An object containing information about a team 
 * @returns A box containing team's name and the game they play
 */
function TeamBox(teams) {

    const [isOpen, setIsOpen] = useState(false);
    
    const togglePopup = () => {
        setIsOpen(!isOpen);
    }

    const team = teams.teams
    let logo = null;
    if(team["game_name"] === "League Of Legends"){
        logo = lolLogo
    }else if(team["game_name"] === "CSGO"){
        logo = csgoLogo
    }

    return(
        <>
        <div id="teamData" className="teamBox" onClick={togglePopup}> 
            <img alt="game Logo" id="gameLogo" src={logo}/>
            <div className="teamBoxName">{team["team_name"]}</div>
            <div className="teamBoxGame">{team["game_name"]}</div>
        </div>
            {isOpen && 
                <Popup content={<TeamDetailPopup handleClose = {togglePopup} teamDetail={team}/>}
                handleClose = {togglePopup}/>}
        </>
    )
}


export default TeamBox;