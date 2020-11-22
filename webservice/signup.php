<?php
	require 'db.php';
	require 'class_account.php';
	$query = "INSERT INTO tblaccount(username, fullname, email, password) values (?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	$s1 =$_POST['username'];
	$s2 =$_POST['fullname'];
	$s3 =$_POST['email'];
	$s4 =$_POST['password'];
	
	
	$prepare_statement->bind_param("ssss",$s1,$s2,$s3,$s4) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>