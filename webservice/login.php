<?php
	require 'db.php';
	require 'class_account.php';
	$query = "SELECT username, fullname, email, password FROM tblaccount WHERE username=? AND password =?";
	$prepare_statement = $connect->prepare($query);
	$s1 =$_POST['username'];
	$s2 =$_POST['password'];
	
	/*
	
	
	
	*/
	
	$prepare_statement->bind_param("ss",$s1,$s2) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	while($row = $data->fetch_assoc()){
		array_push($array, new Account($row['username'], $row['fullname'],$row['email'],$row['password']));
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
	
?>