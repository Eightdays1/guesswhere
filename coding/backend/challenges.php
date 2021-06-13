<?php

function deleteChallenge()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];


    if ($result = $conn->query("SELECT * FROM users WHERE accesstoken='$accesstoken'")) {
        $row = $result->fetch_assoc();
        $username = $row["username"];
        $deletechallengequery = $conn->query("DELETE FROM challenges WHERE username_sender = '$username' OR username_reciever = '$username'");
        if ($deletechallengequery) {
            $data = ['status' => 'true', 'message' => "challenge deleted"];
            echo json_encode($data);
        } else {
            echo throwerror("Error while deleting the challenge!");
        }
    }


}

function challengeResult()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];

    if ($resultcheck = $conn->query("SELECT * FROM users WHERE accesstoken = '$accesstoken'")) {
        $row1 = $resultcheck->fetch_assoc();
        $username = $row1['username'];
        $result = $conn->query("SELECT * FROM challenges WHERE username_sender = '$username' OR username_reciever = '$username' AND played_by IS NOT NULL");
        $row2 = $result->fetch_assoc();
        if ($result->num_rows == 2) {
            $username_sender = $row2['username_sender'];
            $username_reciever = $row2['username_reciever'];

            $resultsender = $conn->query("SELECT distance FROM challenges WHERE played_by = '$username_sender'");
            $resultdistancesender = $resultsender->fetch_assoc();
            $senderdistance = $resultdistancesender['distance'];

            $resultreciever = $conn->query("SELECT distance FROM challenges WHERE played_by = '$username_reciever'");
            $resultdistancereciever = $resultreciever->fetch_assoc();
            $recieverdistance = $resultdistancereciever['distance'];
            $conn->query("UPDATE challenge_requests SET has_been_challenged=false WHERE username = '$username_reciever'");
            $data = ['status' => 'true', 'username_sender' => $username_sender, 'distance_sender' => $senderdistance, 'username_reciever' => $username_reciever, 'distance_reciever' => $recieverdistance];
            echo json_encode($data);
        } else {
            $data2 = ['status' => 'true', 'result' => "false"];
            echo json_encode($data2);
        }
    }
}

function checkForChallenge()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];

    if ($resultcheck = $conn->query("SELECT * FROM users WHERE accesstoken = '$accesstoken'")) {
        $row1 = $resultcheck->fetch_assoc();
        $username = $row1['username'];
        $result = $conn->query("SELECT * FROM challenge_requests WHERE username = '$username'");
        $row2 = $result->fetch_assoc();
        if ($row2['has_been_challenged'] != '0') {
            $data = ['status' => 'true', 'challenged' => "true"];
            echo json_encode($data);
        } else {
            $data2 = ['status' => 'true', 'challenged' => "false"];
            echo json_encode($data2);
        }
    }
}

function saveChallengeGame()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];
    $usertype = $_POST['usertype'];
    $gameid = $_POST['gameid'];
    $guessedcoor1 = $_POST['guessed_coor1'];
    $guessedcoor2 = $_POST['guessed_coor2'];
    $distancelong = $_POST['distance'];
    $distance = round($distancelong, 2);


    if ($result = $conn->query("SELECT * FROM users WHERE accesstoken='$accesstoken'")) {
        $row = $result->fetch_assoc();
    }
    $username = $row['username'];
    if ($result = $conn->query("SELECT * FROM challenges WHERE username_sender='$username'")) {
        $row2 = $result->fetch_assoc();

    }


    if ($usertype == "sender") {
        $usernamereciever = $row2['username_reciever'];
        $conn->query("UPDATE challenge_requests SET has_been_challenged=true WHERE username = '$usernamereciever'");
        $conn->query("UPDATE challenges SET guessed_coor1='$guessedcoor1',guessed_coor2='$guessedcoor2',distance='$distance', played_by='$username' WHERE gameid = '$gameid'");
    } else {

        $conn->query("UPDATE challenges SET guessed_coor1='$guessedcoor1',guessed_coor2='$guessedcoor2',distance='$distance', played_by='$username' WHERE gameid = '$gameid'");
    }

    $data = ['status' => 'true', 'message' => "game saved"];
    echo json_encode($data);

}

function playChallengedGame()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];

    if ($resultcheck = $conn->query("SELECT * FROM users WHERE accesstoken = '$accesstoken'")) {
        $row1 = $resultcheck->fetch_assoc();
    }

    if ($row1['accesstoken'] == $accesstoken) {

        $username = $row1["username"];

        $getgame = $conn->query("SELECT * FROM challenges WHERE username_reciever = '$username'");
        $game = $getgame->fetch_assoc();
        if ($getgame->num_rows == 1) {
            $usernamesender = $game['username_sender'];
            $coor1 = $game['coor1'];
            $coor2 = $game['coor2'];
            $imagekey = $game['imagekey'];
            $gameid = bin2hex(random_bytes(16));
            $conn->query("INSERT INTO challenges (username_sender, username_reciever, coor1, coor2, imagekey, gameid) VALUES ('$usernamesender', '$username', '$coor1','$coor2', '$imagekey', '$gameid')");
            $data = ['status' => 'true', 'imagekey' => $game['imagekey'], 'coordinate1' => $game['coor1'], 'coordinate2' => $game['coor2'], 'gameid' => $gameid];
            echo json_encode($data);
        } else {
            echo throwerror("No game available for this user");
        }
    } else {
        echo throwerror("Provided no or wrong accesstoken");
    }
}


function requestChallenge()
{
    require 'dbconnection.php';

    $accesstoken = $_POST['accesstoken'];
    $usernamereciever = $_POST['username_reciever'];

    if ($resultcheck = $conn->query("SELECT * FROM users WHERE accesstoken = '$accesstoken'")) {
        $row1 = $resultcheck->fetch_assoc();
    }

    if ($row1['accesstoken'] == $accesstoken) {

        if ($result = $conn->query("SELECT * FROM images ORDER BY RAND() LIMIT 1")) {
            $row = $result->fetch_assoc();
        }
        $userexists = $conn->query("SELECT * FROM users WHERE username = '$usernamereciever'");
        if ($userexists->num_rows == 1) {
            $imagekey = $row["imagekey"];
            $coordinate1 = $row["coordinate1"];
            $coordinate2 = $row["coordinate2"];
            $username = $row1["username"];
            $gameid = bin2hex(random_bytes(16));
            if ($conn->query("INSERT INTO challenges (username_sender, username_reciever, coor1, coor2, imagekey, gameid) VALUES ('$username', '$usernamereciever', '$coordinate1','$coordinate2', '$imagekey', '$gameid')")) {


                $data = ['status' => 'true', 'imagekey' => $imagekey, 'coordinate1' => $coordinate1, 'coordinate2' => $coordinate2, 'gameid' => $gameid];
                echo json_encode($data);
            }
        } else {
            echo throwerror("Username doesnt exists");
        }

    } else {
        echo throwerror("Provided no or wrong accesstoken");
    }
}

?>