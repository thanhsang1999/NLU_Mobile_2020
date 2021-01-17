<?php
	require 'db.php';
	
	$query = "INSERT INTO tblaccount(username, fullname, email,outside, id_outside, gender, dateofbirth) values (?,?,?,?,?,?,?)";
	$prepare_statement = $connect->prepare($query);
	
	$s1 =$_POST['username'];
	$s2 =$_POST['fullname'];
	$s3 =$_POST['email'];
	$s4 =$_POST['outside'];
	$s5 =$_POST['id_outside'];
	$s6 =$_POST['gender'];
	$s7 =$_POST['dateofbirth'];
	
	$s11=$s1;
	$s22=$s2;
	$s33=$s3;
	$s44=$s4;
	$s55=$s5;
	$s66=$s6;
	$s77=$s7;
	
	
	$prepare_statement->bind_param("sssssss",$s1,$s2,$s3,$s4,$s5,$s6,$s7) ;
	$array = array();
	if($prepare_statement->execute()){
		$prepare_statement1->close();
		//$connect -> next_result();
		$query2 = "SELECT id FROM tblaccount WHERE username =? AND fullname=? AND email =? AND outside=? AND id_outside=? AND gender=? AND dateofbirth=?";
		$prepare_statement2 = $connect->prepare($query2);
		
		
		$prepare_statement2->bind_param("ssss",$s11,$s22,$s33,$s44,$s55,$s66,$s77);
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
	
	$prepare_statement->close();
	$connect->close();
	
	
?>