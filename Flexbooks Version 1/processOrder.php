<?php
	require('db_connect.php');

	$isbn13 = $_POST['isbn13'];
	$quantity = $_POST['quantity'];
	$firstName = $_POST['firstName'];
	$lastName = $_POST['lastName'];
	$email = $_POST['email'];
	$phoneNo = $_POST['phoneNo'];
	$address = $_POST['address'];
	$aptNo = $_POST['aptNo'];
	$city = $_POST['city'];
	$state = $_POST['state'];
	$zipCode = $_POST['zipCode'];
	$shipping = $_POST['shipping'];
	$cardnum = $_POST['cardnum'];
	$expDate = $_POST['expDate'];
	$cvv = $_POST['cvv'];

	$stmt = $db->prepare("INSERT INTO Orders (isbn, quantity, firstName, lastName, email, phoneNumber, address, apt, city, state, zipCode, shippingSpeed, creditCard, cardExpiration, cvv)
						VALUES (:isbn, :quantity, :firstName, :lastName, :email, :phoneNumber, :address, :apt, :city, :state, :zipCode, :shippingSpeed, :creditCard, :cardExpiration, :cvv)");

	$stmt->bindParam(":isbn",$isbn13);
	$stmt->bindParam(":quantity", $quantity);
	$stmt->bindParam(":firstName", $firstName);
	$stmt->bindParam(":lastName",$lastName);
	$stmt->bindParam(":email", $email);
	$stmt->bindParam(":phoneNumber", $phoneNo);
	$stmt->bindParam(":address", $address);
	$stmt->bindParam(":apt", $aptNo);
	$stmt->bindParam(":city", $city);
	$stmt->bindParam(":state", $state);
	$stmt->bindParam(":zipCode",$zipCode);
	$stmt->bindParam(":shippingSpeed", $shipping);
	$stmt->bindParam(":creditCard", $cardnum);
	$stmt->bindParam(":cardExpiration", $expDate);
	$stmt->bindParam(":cvv",$cvv);

	$stmt->execute();
	
	echo "success";
	
	
?>