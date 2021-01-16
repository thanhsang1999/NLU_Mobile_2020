<?php
	require 'db.php';
	require 'class_image_notebook.php';
	require 'getidaccount.php';
	
	
	$s1 =$_POST['username'];
	//$s1 = 'abc123';
	$i1 = getIdAccount($s1, $connect);
	
	
	$query = "SELECT id,id_notebook, image, last_edit FROM tblimage WHERE id_account=?";
	$prepare_statement = $connect->prepare($query);
	$prepare_statement->bind_param("i",$i1) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$p=new ImageNote($row["id"], $row["id_notebook"], $row["image"], $row["last_edit"], $row["remind"]);
		
		array_push($array, $p);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
?>