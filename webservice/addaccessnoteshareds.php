<?php
	require 'db.php';

	$i1 =$_POST['id'];
	$i2 =$_POST['id_account'];	
	$i3 =$_POST['id_noteshared'];
	$s4 =$_POST['username'];
	$s5 =$_POST['email'];
	
	
	
	
	
	
	$query = "INSERT INTO tblaccessnoteshared(id,id_account,id_noteshared, username, email) values (?,?,?,?,?);";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iiiss",$i1,$i2,$i3,$s4,$s5) ;
	$array = array();
	if($prepare_statement->execute()){
		$ret=$connect -> affected_rows;
		if($ret==1)
		echo "OK";else echo "row effect" . $ret ;
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>