Feature: Game

    A new game is started

Scenario: User started a game

Given user started a game and
When a picture is loaded
Then I should see the picture

Scenario: User wants to guess

Given picture is shown
When user presses "Guess"
Then I should see the Map

Scenario: User select a location

Given Map is shown
Given Marker is placed on Map
When user presses "Select a location"
Then I Should see a Message "Dein Tipp war" + X + "Kilometer entrfernt!"