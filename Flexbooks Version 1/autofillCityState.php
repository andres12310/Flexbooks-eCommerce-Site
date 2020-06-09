<?php

	require("db_connect.php");

	/* in case user inputs 9-digit zip code */
	// if (strlen($_GET["zip"]) == 5) {
	// 	$zip = $_GET["zip"];
	// } 
	// else {
	// 	$zip = substr($_GET["zip"], 0, 5);
	// }

	$zip = $_GET["zip"];

	$q = "SELECT city,state,combinedRate FROM ZipCodes NATURAL JOIN TaxRates WHERE zip={$zip}";

	$zip_statement = $db->prepare($q);
	$zip_statement->execute();
	$row = $zip_statement->fetch();
	$zip_statement->closeCursor();

	if (!$row){
		echo json_encode(
			array(
				"city" => "",
				"state"=> "default",
				"combinedRate" => .05
			)
		);
	}else{
		echo json_encode(
			array(
				"city" => $row["city"],
				"state"=> $row["state"],
				"combinedRate" => $row["combinedRate"]
			)
		);
	}
?>