import React from "react";
import { fireEvent, render, screen } from '@testing-library/react';
import CreateTeamPopup from "../components/teams/createTeamPopup";
import { AppProvider } from "../contexts/AppContext"

describe("CreateTeamPopup component test" , () => {

    const games = {
        games: [
            {
            name: "CSGO",
            id: 1,
            }
            ]
        }

    test("Renders without crashing", async() => {

        render(
            <AppProvider>
                <CreateTeamPopup {...games}/>
            </AppProvider>
        )
        
        expect(await screen.findByText(/Team Name:/i)).toBeInTheDocument()
        expect(await screen.findByText(/Game:/i)).toBeInTheDocument()
        expect(await screen.findByText(/Description:/i)).toBeInTheDocument()
        expect(await screen.findByText(/Discord channel ID:/i)).toBeInTheDocument()

    })

    test("create team function runs", async() => {
        const createTeamHandler = jest.fn()
        const createTeam = jest.fn()

        render(
            <AppProvider>
                <CreateTeamPopup {...games} createTeamHandler={createTeamHandler()} createTeam={createTeam()}/>
            </AppProvider>
        )
        
        const createTeamBtn = screen.getByRole('button');
        fireEvent.click(createTeamBtn);
        expect(createTeamHandler).toHaveBeenCalled();
        expect(createTeam).toHaveBeenCalled();

    })

})
