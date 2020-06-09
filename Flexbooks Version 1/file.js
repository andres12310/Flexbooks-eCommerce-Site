
function validatePurchaseForm() {
	var isbn13 = document.getElementById("isbn13").value;
	var quantity = document.getElementById("quantity").value;
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var email = document.getElementById("email").value;
	var phoneNo = document.getElementById("phoneNo").value;
	var address = document.getElementById("address").value;
	var aptNo = document.getElementById("aptNo").value;
	var city = document.getElementById("city").value;
	var state = document.getElementById("state").value;
	var zipCode = document.getElementById("zipCode").value;
	var shipping = document.getElementById("shipping").value;
	var cardnum = document.getElementById("cardnum").value;
	var expDate = document.getElementById("expDate").value;
	var cvv = document.getElementById("cvv").value;
	// tells user what they need to correct in the form purposes
	var alertMessage = "The following entries have invalid input:\n";
	// validating isbn13 number

	if (isbn13.length != 14) {
		alertMessage += " - ISBN-13 Number\n";
	}
	// validating quantity number
	if (!quantity.match(/^0*[1-9][\d]*$/g)){
		alertMessage += "- Quantity Amount\n";
	}
	// validating firstName string
	if (!firstName.match(/^[a-zA-Z]+(['-][a-zA-Z]+)*$/g)) {
		alertMessage += "- First Name\n";
	}
	// validating lastName string
	if (!lastName.match(/^[a-zA-Z]+([\s'-][a-zA-Z]+)*$/g)) {
		alertMessage += "- Last Name\n";
	}
	// validating email string
	if (!email.match(/^[a-zA-Z0-9]([a-zA-Z0-9]*([\._-][a-zA-Z0-9]+)?)*@[a-zA-Z0-9]([a-zA-Z0-9]*([-][a-zA-Z0-9])?)*\.[a-zA-Z]{2,3}$/g)) {
		alertMessage += "- Email\n";
	}
	// validating phoneNo number
	if (!phoneNo.match(/^1?[2-9][\d]{9}$/g)) {
		alertMessage += "- Phone Number\n";
	}
	// validating address string
	if (!address.match(/^[1-9][\d]{0,4}(\s([1-9][\d]*)*[a-zA-Z]+.?)+$/g)) {
		alertMessage += "- Street Address\n";
	}
	// validating aptNo string
	if (!aptNo.match(/^(\s*|[a-zA-Z0-9]+(-?[a-zA-Z0-9])*)$/g)) {
		alertMessage += "- Apartment Number\n";
	}
	// validating city string
	if (!city.match(/^[a-zA-Z]{3,}([-\s][a-zA-Z]{3,})*$/g)) {
		alertMessage += "- City\n"
	}
	// validating state option
	if (state.match(/^default$/g)) {
		alertMessage += "- State\n";
	}
	// validating zipCode number
	if (!zipCode.match(/^\d{4,5}(-\d{4})?$/g)){
		alertMessage += "- Zip Code\n";
	}
	// validating shipping string
	if (shipping.match(/^default$/g)){
		alertMessage += "- Shipping Speed\n";
	}
	// validating cardnum number
	if (!cardnum.match(/^[\d]{16}$/g)){
		alertMessage += "- Card Number\n";
	}
	// validating expDate number
	if (!expDate.match(/^((0[5-9]|1[0-2])2[0-4]|(0[1-9]|1[0-2])2[1-4])$/g)){
		alertMessage += "- Credit Card Expiration Date\n";
	}
	// validating cvv number
	if (!cvv.match(/^[\d]{3,4}$/g)){
		alertMessage += "- CVV Number\n";
	}
	if (alertMessage.length > 42) {
		alert(alertMessage);
	}
	else {
		sendOrder(isbn13,quantity,firstName,lastName,email,phoneNo,address,aptNo,city,state,zipCode,shipping,cardnum,expDate,cvv);
	}
}

function sendOrder(isbn13,quantity,firstName,lastName,email,phoneNo,address,aptNo,city,state,zipCode,shipping,cardnum,expDate,cvv){
	if (window.XMLHttpRequest){  
	    var xhr = new XMLHttpRequest();
	}
  	else{  
    	var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
  	}

  	xhr.onreadystatechange = function (){ // 4 means finished, and 200 means okay. 
    	if (xhr.readyState == 4 && xhr.status == 200)
    	{ // Data should look like "Fairfax, Virginia"
    		var result =xhr.responseText;
    		if (result === "success"){
    			var total = document.getElementById("total").innerText;
    			total_str = total.slice(7,);
    			window.location.href = `confirmation.php?total=${total_str}&isbn13=${isbn13}&quantity=${quantity}&firstName=${firstName}&lastName=${lastName}&email=${email}&phoneNo=${phoneNo}&address=${address}&aptNo=${aptNo}&city=${city}&state=${state}&zipCode=${zipCode}&shipping=${shipping}&cardnum=${cardnum}&expDate=${expDate}&cvv=${cvv}`
      		}
     	}
    }


    const data = `isbn13=${isbn13}&quantity=${quantity}&firstName=${firstName}&lastName=${lastName}&email=${email}&phoneNo=${phoneNo}&address=${address}&aptNo=${aptNo}&city=${city}&state=${state}&zipCode=${zipCode}&shipping=${shipping}&cardnum=${cardnum}&expDate=${expDate}&cvv=${cvv}`
    xhr.open ("POST", "processOrder.php?");
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  	xhr.send (data);

}



function setVar(my_id) {
	sessionStorage.setItem('my_id', my_id)
}

function setData(){
	var ID = parseInt(sessionStorage.getItem('my_id'), 10);

	if (window.XMLHttpRequest){  // IE7+, Firefox, Chrome, Opera, Safari
	    var xhr = new XMLHttpRequest();
	}
  	else{  // IE5, IE6
    	var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
  	}


  	xhr.onreadystatechange = function (){ // 4 means finished, and 200 means okay. 
    	if (xhr.readyState == 4 && xhr.status == 200)
    	{ // Data should look like "Fairfax, Virginia"
    		var result = null;
    		try{
    			result = JSON.parse(xhr.responseText);
    		}catch (e){
    			alert("JSON could not beeeee parsed");
    		}
      		if (result){
      			var {title, image, price, author, edition, type, page_count, publisher, language, isbn_10, isbn_13, dimensions, weight, quantity} = result;
      			document.getElementById("title_and_price").innerHTML = "<strong><u>" + title + "</u></strong> - " + price;
				document.getElementById("image").src = image;
				document.getElementById("description").innerHTML = "<h3><strong>Author:</strong>&emsp; " + author + "</h3>" +
																	"<h3><strong>Edition:</strong>&emsp; " + edition + "</h3>" +
																	"<h3><strong>Type:</strong>&emsp; " + type + "</h3>" +
																	"<h3><strong>Page Count:</strong>&emsp; " + page_count + "</h3>" +
																	"<h3><strong>Publisher:</strong>&emsp; " + publisher + "</h3>" +
																	"<h3><strong>Lanaguage:</strong>&emsp; " + language + "</h3>" +
																	"<h3><strong>ISBN-10:</strong>&emsp; " + isbn_10 + "</h3>" +
																	"<h3><strong>ISBN-13:</strong>&emsp; " + isbn_13 + "</h3>" +
																	"<h3><strong>Product Dimensions:</strong>&emsp; " + dimensions + "</h3>" +
																	"<h3><strong>Shipping Weight:</strong>&emsp; " + weight + "</h3>" +
																	"<h3><strong>Quantity:</strong>&emsp; " + quantity + "</h3>";
																	
      		}
    	}
  	}

  	xhr.open ("GET", "getItemPageData.php?id=" + ID);
  	xhr.send (null);	
}


function calculateTotal(quantity){      
	var ID = parseInt(sessionStorage.getItem('my_id'), 10);
	var quantity = parseInt(quantity, 10);

	if (window.XMLHttpRequest){  // IE7+, Firefox, Chrome, Opera, Safari
	    var xhr = new XMLHttpRequest();
	}
  	else{  // IE5, IE6
    	var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
  	}

  	xhr.onreadystatechange = function (){ // 4 means finished, and 200 means okay. 
    	if (xhr.readyState == 4 && xhr.status == 200)
    	{ // Data should look like "Fairfax, Virginia"
    		var result = null;
    		try{
    			result = JSON.parse(xhr.responseText);
    		}catch (e){
    			alert("JSON could not beeeee parsed");
    		}
      		if (result){
      			var price = result.price;
      			var real_quantity = result.quantity;
      			price = parseFloat(price.substring(1)) //we do substring to get rid of $ in the price and then we turn the price string into float
      			real_quantity = parseInt(real_quantity);

      			checkQuantity(real_quantity,quantity,price);

      			var zip = document.getElementById("zipCode").value;
				if (zip == ""){
					zip = "1";
				}
				autofillCityState(zip);
    		}
    	}
  	}

  	xhr.open ("GET", "calculateTotal.php?id=" + ID);
  	xhr.send (null);
}

function checkQuantity(realQuantity, userInputedQuantity, price){
	var quantity = document.getElementById("quantity");
	var total_text = document.getElementById("subtotal");
	if (realQuantity === 0){
		alert("We are out of stock on this book.");
		quantity.value = 0;
		total_text.innerText = "SubTotal: $0";
	}
	else if (realQuantity < userInputedQuantity){
		alert("You inputed a quantity higher than what we have in stock");
		quantity.value = 0;
		total_text.innerText = "SubTotal: $0";
	}
	else {
		price = price * userInputedQuantity;
      	total_text.innerText = "SubTotal: $" + price.toFixed(2); 
	}	
	
}

function autofillCityState (zip)
{
	if (window.XMLHttpRequest)
	{  // IE7+, Firefox, Chrome, Opera, Safari
	 var xhr = new XMLHttpRequest();
	}
	else
	{  // IE5, IE6
	 var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
	}

	// Register the embedded handler function
	// This function will be called when the server returns
	// (the "callback" function)
	
	xhr.onreadystatechange = function ()
	{ // 4 means finished, and 200 means okay.
		if (xhr.readyState == 4 && xhr.status == 200)
		{ // Data should look like "Fairfax, Virginia"

			var result = null;
    		try{
    			result = JSON.parse(xhr.responseText);
    		}catch (e){
    			alert("JSON could not beeeee parsed");
    		}

			
			if (result){
				if (document.getElementById("city").value == "") {
					document.getElementById("city").value = result.city; 
				}
				if (document.getElementById("state").value == "default"){
					document.getElementById("state").value = result.state;
				}
				var tax_rate = parseFloat(result.combinedRate);
				var subtotal_string = document.getElementById("subtotal").innerText;
				subtotal_string = subtotal_string.substring(11);
				subtotal_string = parseFloat(subtotal_string);
				var tax_total = parseFloat((subtotal_string * tax_rate)); //change it to a Float
				tax_total = tax_total.toFixed(2); //this will conver the float to a STRING rounding to 2 decimal places
				tax_total = parseFloat(tax_total) //change it again to float because toFixed(2) made it a string when it truncated it to 2 decimal places
				

				document.getElementById("tax").innerText = "Tax $" + tax_total;
				var total_with_tax = tax_total + subtotal_string;
				document.getElementById("total").innerText = "Total: $" + total_with_tax;
			}		
		}
	}
	// Call the response software component
	
	xhr.open ("GET", "autofillCityState.php?zip=" + zip);
	xhr.send (null);
}
