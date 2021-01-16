<?php
	require 'db.php';
	
	$query = "INSERT INTO tblaccount(username, fullname, email,outside, id_outside, gender, dateofbirth) values (?,?,?,?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	$s1 =$_POST['username'];
	$s2 =$_POST['fullname'];
	$s3 =$_POST['email'];
	$s4 =$_POST['outside'];
	$s5 =$_POST['id_outside'];
	$s6 =$_POST['gender'];
	$s7 =$_POST['dateofbirth'];
	
	
	$prepare_statement->bind_param("sssssss",$s1,$s2,$s3,$s4,$s5,$s6,$s7) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>