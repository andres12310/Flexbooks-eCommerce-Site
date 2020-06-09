#URL Of Our Website
- We have no URL because we could not deploy our site

#Database Info
- name: flexbooksdb
- password: server16

#Team Members
- Andres Garcia Roman (19936980)
- Angela Do (86236858)
- Andy Yang (32972683) 
- Ukwa Akkum (68576133)

#General Layout/Design of Website
- The link above takes you to the home page. Here you can see a section on best seller books. We also have another section calles "Flex Your Knowledge" which are books that are recomended for the reader to check out. 
- From here, you have 3 options:
	(1) You can either click on any book in the home page which will take you directly to the item page where you can view product details as well as fill out the purchase form.
	(2) You can go to the Books page where you can view the whole list of books that are available in our website, which is a total of 18 books. You can browse through all the books or sort books by clicking on one of the categories displayed on the left side. 
	(3) You can go to the About page, where you can learn about our business, what we sell, as well as get information about the co-founders of this website. 
- Resources used: w3Schools & lecture notes (mostly popcorn example).


#Where In The Website Are Requirements Satisfied?
- Requirement 1
	- Everything for this requirement is located in the Books page of the website as well as within the subcategories of the Books menu. All information for the textbooks are stored in our "flexbooksdb" database in the "Textbooks" table. Implementation is within file.js (in function setData()) and buy.php. The buy.php file retrieves all the books from the Textbooks table and displays them all. 
- Requirement 2
	- When a user chooses a desired book from the Home or Books page, the website will direct the user to the Item Purchase page. Once the purchase form is filled out properly the user information will be stored in our "flexbooksdb" database in a table called "Orders." Implementation is within file.js (in function sendOrder()) and processOrder.php. The processOrder.php file will return to the client "Suceess" if the insertion into the Orders table was successful. 
- Requirement 3
	- When a user gets back the result from the processOrder.php file and if the result was "Success", then that is an indication that the data of the book order was succcessfully inserted into our Orders table. If this is the case, then we navigate to the confirmation.php file. 
- Requirement 4
	- When the user is on a specific book page with the purchase form, and inputs a quantity more than what is in stock stored in the "flexbooksdb" database in the "Textbooks" table, the user will be alerted. Implementation is within file.js (in functions checkQuantity() and calculateTotal())) and calculateTotal.php. If the qunatity that the user specified is less than what we have in stock, then the user will automatically see the subtotal for the quantity chosen and the price of the book relfected at the bottom of the form. The tax will also be calculated. The total including tax will also be updated so the user knows how much it will be.
	- When the user is on a specific book page with the purchase form, and inputs a zip code that is within the "ZipCodes" table in the "flexbooksdb" database, the website will autofill the city and state. Implementation is within file.js (in function autofillCityState()) and autofillCityState.php.
	- When the user is on a specific book page with the purchase form, and inputs a zip code that is within the "TaxRates" and "ZipCodes" tables in the "flexbooksdb" database, the website will calculate the tax. Implementation is within file.js (in function autofillCityState()) and autofillCityState.php.