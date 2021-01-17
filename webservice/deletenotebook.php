<?php
	require 'db.php';
	
	
	
	$i1 =$_POST['id'];
	$i2 =$_POST['id_account'];	
	
	
	
	
	
	$query = "DELETE FROM tblnotebook  WHERE id =? AND id_account=?";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("ii",$i1,$i2) ;
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