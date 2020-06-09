<?php
	require('db_connect.php');
	
	$id = $_GET["id"];

	$q = "SELECT * FROM Textbooks WHERE ID={$id}";
	
	$book_statement = $db->prepare($q);
	$book_statement->execute();
	$row = $book_statement->fetch();
	$book_statement->closeCursor();

	echo json_encode(
		array(
			"title" => $row["title"],
			"image"  => $row["image"],
			"price"  => $row["price"],
			"author"  => $row["author"],
			"edition"  => $row["edition"],
			"type"  => $row["type"],
			"page_count"  => $row["pageCount"],
			"publisher"  => $row["publisher"],
			"language"  => $row["language"],
			"isbn_10"  => $row["ISBN10"],
			"isbn_13"  => $row["ISBN13"],
			"dimensions"  => $row["productDimensions"],
			"weight"  => $row["shippingWeight"],
			"quantity" => $row["quantity"]
		)
	
	);

?>
