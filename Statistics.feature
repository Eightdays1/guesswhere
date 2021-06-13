Feature: Statistics

    User selects "Statistics" tu see the Statistics-Screen.

Scenario: User calls Statistics-Screen

Given Main-Screen is shown
When user presses "Statistics"
Then I should see the Statistics-Screen

Scenario: User refreshes the Statistics-Screen

Given Statistics-Screen is shown
When user presses "Refresh"
Then I should see the Statistics-Screen

Scenario: User returns to Main-Screen

Given Statistics-Screen is shown
When user presses "Return"
Then I should see the Main-Screen