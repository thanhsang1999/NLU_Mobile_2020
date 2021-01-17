<?php
	require 'db.php';
	require 'class_notebook.php';
	require 'getidaccount.php';
	
	
	$i1 =$_POST['id_account'];
	//$s1 = 'abc123';
	
	
	
	$query = "SELECT id,id_package, title, content, last_edit,remind FROM tblnotebook WHERE id_account=?";
	$prepare_statement = $connect->prepare($query);
	$prepare_statement->bind_param("i",$i1) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$p=new Notebook($row["id"], $row["id_package"], $row["title"], $row["content"], $row["last_edit"], $row["remind"]);
		
		array_push($array, $p);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
?>