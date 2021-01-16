<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	
	
	$s1 =$_POST['username'];
	$s2 =$_POST['fullname'];
	$s3 =$_POST['gender'];
	$s4 =$_POST['dateofbirth'];
	$s5 =$_POST['email'];
	$i1 = getIdAccount($s1, $connect);
	
	
	$query = "UPDATE tblaccount SET fullname =?, gender =?, dateofbirth =?, email=? WHERE  id=?";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("ssssi",$s2,$s3,$s4,$s5,$i1) ;
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