<?php
	require 'db.php';
	require 'class_access_noteshared.php';
	
	
	
	$i1 =$_POST['id'];
	//$s1 = 'abc123';
	
	
	
	$query = "SELECT id, id_account,id_noteshared,username, email FROM tblaccessnoteshared WHERE id_account=?";
	$prepare_statement = $connect->prepare($query);
	$prepare_statement->bind_param("i",$i1) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$note=new AccessNoteShared($row["id"]);
		$note -> IdNoteShared= $row["id_noteshared"];
		$note -> IdAccount= $row["id_account"];
		$note -> Username= $row["username"];
		$note -> Email= $row["email"];
		array_push($array, $note);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
?>