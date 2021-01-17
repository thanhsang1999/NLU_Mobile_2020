<?php
	require 'db.php';

	$i1 =$_POST['id'];
	$i2 =$_POST['id_account'];	
	$s3 =$_POST['username'];
	$s4 =$_POST['title'];
	$s5 =$_POST['content'];
	$s6 =$_POST['last_edit'];
	
	$hasRemind =$_POST['has_remind'];
	if(strcmp($hasRemind,"true")==0)
		$s7 =$_POST['remind'];
	else 
	$s7=null;
	
	
	
	$query = "INSERT INTO tblnoteshared(id,id_account,username, title, content, last_edit,remind) values (?,?,?,?,?,?,?);";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iisssss",$i1,$i2,$s3,$s4,$s5,$s6,$s7) ;
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