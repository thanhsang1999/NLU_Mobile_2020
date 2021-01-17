<?php
	require 'db.php';
	
	require 'getidaccount.php';
	
	$i1 =$_POST['id'];
	$i2 =$_POST['id_account'];	
	$i3 =$_POST['id_package'];
	$s4 =$_POST['title'];
	$s5 =$_POST['content'];
	$s6 =$_POST['last_edit'];
	
	$hasRemind =$_POST['has_remind'];
	if(strcmp($hasRemind,"true")==0)
		$s7 =$_POST['remind'];
	else 
	$s7=null;
	
	
	
	$query = "UPDATE tblnotebook SET  title=? , content=?, last_edit=?,remind=? WHERE id =? AND id_account=? AND id_package=?";
	$prepare_statement = $connect->prepare($query);
	
	
	
	$prepare_statement->bind_param("ssssiii",$s4,$s5,$s6,$s7,$i1,$i2,$i3) ;
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