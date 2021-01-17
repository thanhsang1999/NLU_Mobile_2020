<?php
	require 'db.php';
	require 'class_account.php';
	$query1 = "INSERT INTO tblaccount(username, fullname, email, password) values (?,?,?,?)";
	$prepare_statement1 = $connect->prepare($query1);
	
	$s1 =$_POST['username'];
	$s2 =$_POST['fullname'];
	$s3 =$_POST['email'];
	$s4 =$_POST['password'];
	$s11=$s1;
	$s22=$s2;
	$s33=$s3;
	$s44=$s4;
	
	$prepare_statement1->bind_param("ssss",$s1,$s2,$s3,$s4) ;
	
	if($prepare_statement1->execute()){
		
		$prepare_statement1->close();
		//$connect -> next_result();
		$query2 = "SELECT id FROM tblaccount WHERE username =? AND fullname=? AND email =? AND password=?";
		$prepare_statement2 = $connect->prepare($query2);
		
		
		$prepare_statement2->bind_param("ssss",$s11,$s22,$s33,$s44);
		$prepare_statement2-> execute();
		$prepare_statement2->store_result();
		$prepare_statement2->bind_result($id);
		//$data = $prepare_statement1->store_result() or die($connect->error);;
		
			
		$CountRow=$prepare_statement2->num_rows;
		if($CountRow==1){
			if($prepare_statement2->fetch()){
				echo $id . "";		
			}
			
			
		}else{ echo "num_rows" . $CountRow;}
			
		
		$prepare_statement2->close();
		
	}else{
		echo "Error";
	}
	
	
	
	$connect->close();
	
	
?>