Feature: Options

    A User enters the Options-Screen to change Settings.

Scenario: User opens Option-Screen

Given Main Screen is shown
When user presses "Options"
Then I should see the Options-Screen

Scenario: User changes Password

Given Options-Screen is shown
When user presses "change Password"
Then I should see the change-password-screen

Scenario: User saves changes with correct password

Given change-password-screen is shown
Given user enters old password
Given user enters new password
When user presses "Confirm"
Then I should see the Message "Passwort erfolgreich geändert!"

Scenario: User saves changes with wrong password

Given change-password-screen is shown
Given user enters old password
Given user enters new password
When user presses "Speichern"
Then I should see the Message "Fehler beim ändern des Passwortes. Versuchenn Sie es nochmal!"