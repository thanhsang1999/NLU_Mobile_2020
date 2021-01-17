<?php
	require 'db.php';
	require 'class_account.php';
	$query = "SELECT id,username, fullname, email, password,outside,id_outside,gender,dateofbirth FROM tblaccount WHERE outside=? AND id_outside =?";
	$prepare_statement = $connect->prepare($query);
	$s1 =$_POST['outside'];
	$s2 =$_POST['id_outside'];
	
	/*
	
	
	
	*/
	
	$prepare_statement->bind_param("ss",$s1,$s2) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	while($row = $data->fetch_assoc()){
		$account=new Account($row['id'],$row['username'], $row['fullname'],$row['email'],$row['password']);
		$account->Gender = $row['gender'];
		$account->DateOfBirth = $row['dateofbirth'];
		$account->Outside = $row['outside'];
		$account->IdOutside = $row['id_outside'];
		array_push($array, $account);
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
	
?>