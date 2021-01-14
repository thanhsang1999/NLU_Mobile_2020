<?php
	require 'db.php';
	require 'class_package.php';
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	$i1 = null;
	$s2 =$_POST['username'];
	$s3 =$_POST['title'];
	$s4 =$_POST['color'];
	$s5 =$_POST['last_edit'];
	$i2 = getIdAccount($s2, $connect);
	
	
	$query = "INSERT INTO tblpackage(id,id_account, title, color, last_edit) values (?,?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iisss",$i1, $i2,$s3,$s4,$s5) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>