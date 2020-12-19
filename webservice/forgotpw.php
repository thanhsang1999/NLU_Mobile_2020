<?php
	require 'db.php';
	require 'class_account.php';
	$query = "UPDATE tblaccount SET password = ? where email = ?";
	$prepare_statement = $connect->prepare($query);

	$s1 =$_POST['newpw'];
	$s2 =$_POST['email'];



	$prepare_statement->bind_param("ss",$s1,$s2) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}

	$prepare_statement->close();
	$connect->close();


?>