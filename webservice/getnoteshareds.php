<?php
	require 'db.php';
	require 'class_noteshared.php';
	
	
	
	$i1 =$_POST['id'];
	//$s1 = 'abc123';
	
	
	
	$query = "SELECT tblnoteshared.id, tblnoteshared.title, tblnoteshared.content, tblnoteshared.last_edit,tblnoteshared.remind,tblnoteshared.username,tblnoteshared.id_account FROM tblnoteshared JOIN tblaccessnoteshared ON tblnoteshared.id = tblaccessnoteshared.id_noteshared WHERE tblaccessnoteshared.id_account=?";
	$prepare_statement = $connect->prepare($query);
	$prepare_statement->bind_param("i",$i1) ;
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$note=new Noteshared($row["id"]);
		$note -> Title= $row["title"];
		$note -> Content= $row["content"];
		$note -> Username= $row["username"];
		$note -> LastEdit= $row["last_edit"];
		$note -> Remind= $row["remind"];
		$note -> IdAccount= $row["id_account"];
		array_push($array, $note);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
	
?>