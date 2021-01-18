<?php
	require 'db.php';
	
	
	
	function checkAccountExists($username, $email, $connect){
		$query = "SELECT id FROM tblaccount WHERE username = ? and email = ?";
		$prepare_statement = $connect->prepare($query);
		$prepare_statement->bind_param("ss",$username, $email) ;
		$prepare_statement->execute();
		
		$prepare_statement->store_result();
		$prepare_statement->bind_result($id);
		
		$CountRow=$prepare_statement->num_rows;
		if($CountRow>0){
			$prepare_statement->free_result();
			$prepare_statement->close();
			return false;
			
		}else{ 
			$prepare_statement->free_result();
			$prepare_statement->close();
			return true;
			
		}
		
		
		
	}
	
	
	
	
	
	
	
?>