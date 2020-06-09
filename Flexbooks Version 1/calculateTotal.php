<?php
	require("db_connect.php");

	$id = $_GET["id"];

	$q = "SELECT price,quantity FROM Textbooks WHERE ID={$id}";

	$book_statement = $db->prepare($q);
	$book_statement->execute();
	$row = $book_statement->fetch();
	$book_statement->closeCursor();


	echo json_encode(
		array(
			"price" => $row["price"],
			"quantity" => $row["quantity"]
		)
	);
?>