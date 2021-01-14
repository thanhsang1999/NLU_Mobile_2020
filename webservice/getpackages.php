<?php
	require 'db.php';
	require 'class_package.php';
	require 'getidaccount.php';
	
	
	$s1 =$_POST['username'];
	//$s1 = 'abc123';
	$i1 = getIdAccount($s1, $connect);
	
	
	$query = "SELECT id,id_account, title, color, last_edit FROM tblpackage WHERE id_account=?";
	$prepare_statement = $connect->prepare($query);
	$prepare_statement->bind_param("i",$i1) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$p=new Package($row["id"], $row["id_account"], $row["title"], $row["color"], $row["last_edit"]);
		
		array_push($array, $p);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
?>