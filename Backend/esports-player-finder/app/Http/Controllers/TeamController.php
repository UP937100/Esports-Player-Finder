<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Models\Team;
use App\Models\Game;
use App\Models\UserGameRole;
use App\Models\TeamParticipant;
use Illuminate\Database\Eloquent\ModelNotFoundException;


class TeamController extends Controller
{
     /**
     * Create new team for currently logged in user
     * 
     * @Authenticated
     * 
     * @group Teams
     * 
     * @queryParam name required The name of the team
     * @queryParam description required The description of the team
     * @queryParam game_id required The game the team is for
     * @queryParam discord_channel_id required The discord channel the team uses
     * 
     * @response {
     *     "created": true
     * }
     * 
     * @param Illuminate\Http\Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function create(Request $request) {
        $this->validator($request->all())->validate();
        $user = $request->user();
        $team = Team::create([
            'name' => $request['name'],
            'description' => $request['description'],
            'game_id' => $request['game_id'],
            'discord_channel_id' => $request['discord_channel_id'],
        ]);

        TeamParticipant::create([
            'team_id' => $team->id,
            'user_id' => $user->id,
            'status' => 0, # 0 = owner 1 = member
        ]);

        return response()->json([
            'created' => true,
        ], 201);
    }


    protected function validator(array $data)
    {
         return Validator::make($data, [
          'name' => ['required', 'string', 'max:255', 'unique:teams'],
          'description' => ['required', 'string', 'max:255'],
          'game_id' => ['required', 'int', 'exists:games,id'],
          'discord_channel_id' => ['required', 'string', 'max:255'],
       ]);
    }
    



    /**
     * Get team info from teams id
     * 
     * 
     * 
     * @group Teams
     * 
     * @queryParam id required The Id of the team being queried
     * 
     * @response {
     *      "Team": {
     *           "id": 1,
     *           "name": "testteam",
     *          "created_at": "2021-03-23T21:28:38.000000Z",
     *          "updated_at": "2021-03-23T21:28:39.000000Z",
     *          "description": "testdesc",
     *          "discord_channel_id": "testdis",
     *          "game_id": 1
     *      },
     *      "Game": {
     *          "id": 1,
     *          "name": "testgame",
     *          "created_at": "2021-03-23T19:34:40.000000Z",
     *          "updated_at": "2021-03-23T19:34:40.000000Z"
     *      }
     * }
     * }
     * 
     * @param Illuminate\Http\Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function get(Request $request) {
        try {
            $team_data = Team::findOrFail($request->id)->load('game');
            $response = response()->json([
                "Team" => $team_data,
            ],200);
        }catch(ModelNotFoundException $e) {
            $response = response()->json([
                "Error" => "Invalid Request",
            ], 400);
        }
        return $response;
    }

/**
     * WARN - Endpoint not fully tested. Get team that match the game id & the users current game role id
     * 
     * 
     * 
     * @group Teams
     * 
     * @queryParam id required The Id of the game being queried
     * 
     * @response {
     *"Teams": [
     *   {
     *       "id": 3,
     *       "name": "test2",
     *       "created_at": "2021-03-25T22:52:42.000000Z",
     *       "updated_at": "2021-03-25T22:52:43.000000Z",
     *       "description": "testd",
     *       "discord_channel_id": "test",
     *       "game_id": 1
     *   },
     *   {
     *       "id": 4,
     *       "name": "test3",
     *       "created_at": "2021-03-25T22:52:42.000000Z",
     *       "updated_at": "2021-03-25T22:52:43.000000Z",
     *       "description": "testd",
     *       "discord_channel_id": "test",
     *       "game_id": 1
     *   }
     *]
     *}
     * 
     * @param Illuminate\Http\Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function find(Request $request) {
        $user_role = UserGameRole::where("user_id", "=", $request->user()->id)->firstOrFail();
        try {
            $team_data = Team::select("teams.*")
                             ->join("team_participants","team_participants.team_id", "=", "teams.id")
                             ->join("users", "users.id", "=", "team_participants.user_id")
                             ->join("user_game_roles","user_game_roles.user_id", "=", "users.id")
                             ->join("game_roles","game_roles.id", "=", "user_game_roles.game_role_id")
                             ->where("teams.game_id", $request->game_id)
                             ->where("user_game_roles.game_role_id", "!=", $user_role->game_role_id)->take(10)->get();
            $response = response()->json([
                "Teams" => $team_data,
            ],200);
        }catch(ModelNotFoundException $e) {
            $response = response()->json([
                "Error" => "Invalid Request",
            ], 400);
        }
        return $response;
    }
}