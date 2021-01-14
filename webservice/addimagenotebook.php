<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	
	$s2 =$_POST['username'];
	
	$i3 =$_POST['id_notebook'];
	$s4 =$_POST['image'];
	$s4 = base64_decode($s4);
	
	$s5 =$_POST['last_edit'];
	
	$i2 = getIdAccount($s2, $connect);
	
	$query = "INSERT INTO tblnotebook(id,id_account,id_notebook, image, last_edit) values (?,?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iiiss",$i1,$i2,$i3,$s4,$s5) ;
	$array = array();
	if($prepare_statement->execute()){
		echo "OK";
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>