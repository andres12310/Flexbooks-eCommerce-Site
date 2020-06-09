<head>
	<title>Confirmation | Off-Da-Hook Flexbooks</title>
	<link href="root.css" rel="stylesheet" type="text/css">
	<link href="home_aboutUs.css" rel="stylesheet" type="text/css">
	<script src="file.js"></script>
</head>
<body class="background_image">
<div align=center><img style="width:80%; border:none;" src="./images/topBanner.png"></div>
<div class="top_menu">
  <a href="home.html">Home</a>
  <div class="dropdown-books"><a href="buy.php">Books</a>
  		<div class="drop-categories">
				<form action="buy.php" method="post">
					<button type="submit" name="all" class="buy-button">All Books</button><br>
					<button type="submit" name="science" class="buy-button">Science</button><br>
					<button type="submit" name="math" class="buy-button">Math</button><br>
					<button type="submit" name="english" class="buy-button">English</button><br>
					<button type="submit" name="social_science" class="buy-button">Social Science</button><br>
					<button type="submit" name="CS" class="buy-button">Comp. Science</button>
					<button type="submit" name="art_music" class="buy-button">Art & Music</button>
				</form>
  		</div>
  </div>
  <a href="aboutUs.html">About Us</a>
</div>
<div class="container1" style="display:flex; margin-top:10px;">
	<div class="grid">
		<h1 align="center">Your purchase was successful!</h1>
			<table class="description-text" align="center">
				<tr>					
					<td><center>
						<h2 style="font-size: 24px;">Your details are the following:</h2>
						<strong>ISBN:</strong> <?php echo $_GET["isbn13"] ?><br>
						<strong>Quantity:</strong> <?php echo $_GET["quantity"] ?><br>
						<strong>Total:</strong> <?php echo $_GET["total"] ?><br>
						<strong>First Name:</strong> <?php echo $_GET["firstName"] ?><br>
						<strong>Last Name:</strong> <?php echo $_GET["lastName"] ?><br>
						<strong>Email:</strong> <?php echo $_GET["email"] ?><br>
						<strong>Phone Number:</strong> <?php echo $_GET["phoneNo"] ?><br>
						<strong>Address:</strong> <?php echo $_GET["address"] ?><br>
						<strong>Apt No:</strong> <?php echo $_GET["aptNo"] ?><br>
						<strong>City:</strong> <?php echo $_GET["city"] ?><br>
						<strong>State:</strong> <?php echo $_GET["state"] ?><br>
						<strong>Zip Code:</strong> <?php echo $_GET["zipCode"] ?><br>
						<strong>Shipping:</strong> <?php echo $_GET["shipping"] ?><br>
						<strong>Card Number:</strong> <?php echo $_GET["cardnum"] ?><br>
						<strong>Expiration Date:</strong> <?php echo $_GET["expDate"] ?><br>
						<strong>CVV:</strong> <?php echo $_GET["cvv"] ?><br></p>
					</center>
					</td>
				</tr>
			</table>
	</div>
</div>
</body>