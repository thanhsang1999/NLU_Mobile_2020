<?php
	require 'db.php';
	require 'class_table.php';
	
	//$query = "update tblaccount set password='hoang'" ;
	$s1 =$_POST['raw'];
	$query=$s1;
	$query= strtolower($query);
	
	
	
	$prepare_statement = $connect->prepare($query);
	
	
	if($prepare_statement->execute()){
		
		echo $prepare_statement->affected_rows;
	}else{
		echo "Error";
	}
	
	
	$prepare_statement->close();
	$connect->close();
	
	
?>