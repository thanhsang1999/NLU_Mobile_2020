<?php
	require 'db.php';
	require 'class_table.php';
	
	//$query = "select id from tblaccount";
	$s1 =$_POST['raw'];
	$query =$s1;
	$query= strtolower($query);
	
	
	
	$string = substr($query, strpos($query, 'select'), strlen($query));
	//echo $string . '</br>';
	$string = substr($string, 6, strpos($string, 'from')-6);
	//echo $string. '</br>';
	//echo "-----------". '</br>';
	$arr = preg_split("/\s{0,},\s{0,}/", $string);
	/*
	foreach ($arr as &$value) {
		echo $value . '</br>';
	}
	*/
	
	$prepare_statement = $connect->prepare($query);
	$array = array();
	$prepare_statement->execute();
	$data = $prepare_statement->get_result();
	
	while($row = $data->fetch_assoc()){
		$object = new MyTable();
		foreach ($arr as &$value) {
			
			//echo $row[trim($value)] . '</br>';
			$object->user_properties[trim($value)]= $row[trim($value)];
			
			
		}	
		array_push($array, $object);
		
	
		
	}
	$prepare_statement->close();
	$connect->close();
	echo json_encode($array);
	
?>