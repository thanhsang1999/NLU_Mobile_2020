	
<?php
class ImageNote{
	function ImageNote($id, $id_account, $id_notebook, $image, $last_edit){
		$this -> Id= $id;
		$this -> IdAccount= $id_account;
		
		$this -> IdNotebook= $id_notebook;
		$this -> Image= $image;
		
		$this -> LastEdit= $last_edit;
		
	}
	
}

?>