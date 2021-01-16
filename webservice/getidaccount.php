<?php
require 'db.php';
	
	function getIdAccount($username, $connect){
		$query = "SELECT id FROM tblaccount WHERE username = ?";
		$prepare_statement = $connect->prepare($query);
		$prepare_statement->bind_param("s",$username) ;
		$prepare_statement->execute();
		$data = $prepare_statement->get_result();
		while($row = $data->fetch_assoc()){
			return $row["id"];
		}
		$prepare_statement->close();
		
	}
	
	
	
	
?>