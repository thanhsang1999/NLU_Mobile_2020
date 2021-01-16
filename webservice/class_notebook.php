	
<?php
class Notebook{
	function Notebook($id, $id_package, $title, $content, $last_edit, $remind){
		$this -> Id= $id;

		$this -> Title= $title;
		$this -> Content= $content;
		$this -> IdPackage= $id_package;
		$this -> LastEdit= $last_edit;
		$this -> Remind= $remind;
	}
	
}

?>