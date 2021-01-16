<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	
	$s2 =$_POST['username'];
	$s3 =$_POST['title'];
	$s4 =$_POST['color'];
	$s5 =$_POST['last_edit'];
	$i2 = getIdAccount($s2, $connect);
	
	
	$query = "UPDATE tblpackage SET title =?, color =?, last_edit =? WHERE id=? AND id_account=?";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("sssii",$s3,$s4,$s5,$i1, $i2) ;
	$array = array();
	if($prepare_statement->execute()){
		$ret=$connect -> affected_rows;
		if($ret==1)
		echo "OK";else echo "row effect" . $ret;
	}else{
		echo "Error";
	}
	
	$prepare_statement->close();
	$connect->close();
	
	
?>