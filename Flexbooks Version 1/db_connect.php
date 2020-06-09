<?php
  DEFINE('DB_USER','root');
  DEFINE('DB_PASSWORD', '');
  $dsn = 'mysql:host=localhost;dbname=flexbooksdb';
  try{
    $db = new PDO($dsn, DB_USER, DB_PASSWORD);
  } catch (PDOException $e){
    $err_msg = $e->getMessage();
    include('db_error.php');
    exit();
  }
?>
