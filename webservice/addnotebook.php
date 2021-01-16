<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	$s2 =$_POST['username'];	
	$i3 =$_POST['id_package'];
	$s4 =$_POST['title'];
	$s5 =$_POST['content'];
	$s6 =$_POST['last_edit'];
	
	$hasRemind =$_POST['has_remind'];
	if(strcmp($hasRemind,"true")==0)
		$s7 =$_POST['remind'];
	else 
	$s7=null;
	$i2 = getIdAccount($s2, $connect);
	
	
	$query = "INSERT INTO tblnotebook(id,id_account,id_package, title, content, last_edit,remind) values (?,?,?,?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("iiissss",$i1,$i2,$i3,$s4,$s5,$s6,$s7) ;
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