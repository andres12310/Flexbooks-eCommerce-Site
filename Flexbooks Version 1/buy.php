<?php
  require('db_connect.php');
  $query_textbooks = 'SELECT * FROM Textbooks ';
  $category = 'All';

  $textbook_statement = $db->prepare($query_textbooks);
  $textbook_statement->execute();
  $textbooks = $textbook_statement->fetchAll();
  $textbook_statement->closeCursor();

  function update_query($cond) {
    global $query_textbooks, $textbook_statement, $textbooks, $db;
    $textbook_statement = $db->prepare($query_textbooks . $cond);
    $textbook_statement->execute();
    $textbooks = $textbook_statement->fetchAll();
    $textbook_statement->closeCursor();
  };

  if (isset($_POST)){
    if (isset($_POST['all'])){
      update_query("WHERE 1");
      $category = "All";
    }
    elseif (isset($_POST['science'])){
      update_query("WHERE category = 'science'");
      $category = "Science";
    }
    elseif (isset($_POST['math'])){
      update_query("WHERE category = 'math'");
      $category = "Math";
    }
    elseif (isset($_POST['english'])){
      update_query("WHERE category = 'english'");
      $category = "English";
    }
    elseif (isset($_POST['social_science'])){
      update_query("WHERE category = 'social science'");
      $category = "Social Science";
    }
    elseif (isset($_POST['CS'])){
      update_query("WHERE category = 'computer science'");
      $category = "Computer Science";
    }
    elseif (isset($_POST['art_music'])){
      update_query("WHERE category = 'art music'");
      $category = "Art & Music";
    }
  }
?>

<html>
	<head>
		<title>Books | Off-Da-Hook Flexbooks</title>
		<link href="buy_item.css" rel="stylesheet" type="text/css">
		<link href="root.css" rel="stylesheet" type="text/css">
		<script src="file.js"></script>
	</head>
	<body class="background_image">
	<div align=center><img style="width:80%; border:none;" src="./images/topBanner.png"></div>
	<div class="top_menu">
	  <a href="home.html">Home</a>
	  <div class="dropdown-books"><a class="current" href="buy.php">Books</a>
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
			<div class="container">
				<div class="categories_div">
  				<form class="_form" action="buy.php" method="post">
  					<h1 class="categories_title">Categories</h1>
  					<button type="submit" name="all" class="buy-button category-btn">All Books</button><br>
  					<button type="submit" name="science" class="buy-button category-btn">Science</button><br>
  					<button type="submit" name="math" class="buy-button category-btn">Math</button><br>
  					<button type="submit" name="english" class="buy-button category-btn">English</button><br>
  					<button type="submit" name="social_science" class="buy-button category-btn">Social Science</button><br>
  					<button type="submit" name="CS" class="buy-button category-btn" style="padding-left:25px;padding-right:25px;">Comp. Science<br>
            <button type="submit" name="art_music" class="buy-button category-btn">Art & Music</button><br>
  				</form>
				</div>
				<div class="grid">
					<h1 align="left"><?php echo $category ?> Books</h1>
					<?php
            foreach($textbooks as $textbook):
              echo "<div class='cell'>";
                echo "<a id='" . $textbook['ID'] . "' href='./itempage.html' onclick='setVar(" . $textbook['ID'] . ")'>";
                  echo "<img class='zoom' src='" . $textbook['image'] . "' alt='book', height='270' width='270'>";
                echo "</a>";
                echo "<h2>" . $textbook['title'] . "</h2>";
                echo "<h5>".$textbook['author']."</h5>";
                echo "<h5>" . $textbook['price'] . "</h5>";
              echo "</div>";
            endforeach;
					?>
				</div>
			</div>
		</div>
	</body>
</html>
