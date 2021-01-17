<?php
	require 'db.php';
	require 'class_account.php';
	$query = "SELECT id,username, email FROM tblaccount WHERE username=? OR email =?";
	$prepare_statement = $connect->prepare($query);
	
	$s1 =$_POST['usernameoremail'];
	$s2 = $s1;
	
	$prepare_statement->bind_param("ss",$s1,$s2) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	while($row = $data->fetch_assoc()){
			$account=new Account($row['id']);
			
			$account->Username = $row['username'];
			$account->Email = $row['email'];
			array_push($array, $account);
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
	
?>