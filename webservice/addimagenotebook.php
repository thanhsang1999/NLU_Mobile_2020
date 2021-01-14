<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	$i2 =$_POST['id_package'];
	$s3 =$_POST['username'];

	$s4 =$_POST['image'];
	
	$s5 =$_POST['last_edit'];
	
	$i3 = getIdAccount($s2, $connect);
	
	$query = "INSERT INTO tblnotebook(id,id_account,id_package, image, last_edit) values (?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iiisss",$i1,$i2,$i3,$s4,$s5) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>