**Game Centre**

This project was created to store a set of users and games in an intuitive way that let users sign up or log in.
The users would then be able to select a game that they wanted to play (currently only SlidingTiles is available)
and afterwards the user would advance to a menu page where they would pick between loading a previous game or starting a new game. Regardless of which choice
the user makes, they would take the user to a page allowing them to choose the difficulty that they would want to start
a new game on or load from, depending on what they choose one screen earlier. The user then gets to play the game. Once
they finish the game, the scoreboard shows up with the top scores between all users and the option to see the top
scores that they've managed to record. They are then given the option to play again or play a different game.

**Code Implementation**

When the app is first launched the GameLaunchActivity is displayed. This page has two textboxes, one for an input for
the users username and one for the their password, along with a button allowing them to log in once they inputted, or
the option to register if they did not have an account yet. The activity also has logic that work with the
GameLaunchCentre to check that the user login matches the username with the password so that someone cannot login to
an account without the correct password. If the user logins successfully then they move on to the gameselectionactivity.
Otherwise a toast appears indicating that the password is incorrect, or one of the fields is missing, or that the user
does not exist. At this point either the user can fill in the missing textbox, use the correct password, or register as
a new user. Then they can create a new username that isn't already taken and create their password. Then after
registering or logging in they would be able to select the game that they wanted to play (In this case only SlidingTiles).

This takes them to the SlidingTilesMenuActivity where the user can pick between starting a new game or loading a previous
one. In either case it takes them to the PreNewGameSlidingTiles where the user is able to select the complexity that they
would like to play at as well as the background image that they would like to use. Afterwards the game starts and
the user can play through the game. This starts a timer which will be used as a way of creating a score for the game,
this also instantiates a container that will hold all of the moves made by the user, thus allowing the undo feature to
work. The user has the option of saving the current gamestate, undoing a move (up to three)
and they can also see how much time has gone by since they started playing while they are within the SlidingTileGameActivity.

Once they finish the time is recorded and if it is better than any times on the scoreboard for either the users personal
best or for the entire app, their time is placed on the scoreboard. They then have the ability to play again or play a
different game.