<?php
$conn = new mysqli("localhost","guesswhere","<password here>","datadump");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
