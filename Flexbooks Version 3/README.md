#URL Of Our Website
- We have no URL because we could not deploy our site

#Database Info
- name: flexbooksdb
- password: 

#Team Members
- Andres Garcia Roman (19936980)
- Angela Do (86236858)
- Andy Yang (32972683) 
- Ukwa Akkum (68576133)

#General Layout/Design of Website
- When you first run the program, you will be encountered with the home page, which displays the list of all the books that we have for our book selling website. It also displays the list of recently viewed books. At the beginning, the user has not viewed any books, so the container that holds the recently viewed books is empty. 
- From here, you have 2 options:
	(1) You can either click on any book in the home page which will take you directly to the item page where you can view product details as well as fill out the purchase form.
	(2) You can go to the About page, where you can learn about our business, what we sell, as well as get information about the co-founders of this website. 
- Resources used: w3Schools & lecture notes (mostly popcorn example).


#Where In The Website Are Requirements Satisfied?
- Requirement 1
	- Everything for this requirement is fulfilled by our home page, which is the first page that you encounter when you run the project. All information for the textbooks are stored in our "flexbooksdb" database in the "Textbooks" table. Implementation is done by including the output of the ProductServlet and the getRecentlyViewed servlets in our home.jsp file. The ProductServlet  is in charge od displaying the entire colelctions of boks from our DB. The getRecentlyViewed servlet is in charge of displaying the information about the recently viewed books. This is done using session tracking. 
- Requirement 2
	- The REST Services are implemented in the Sources folder. The REST Services that we implemented were 2 GET methods and 1 POST method. One of the GET methods is getBookById() and it returns a specific book based on the ID that was generated when the user clicked on a specific book. The other GET method is getAllBooks() which returns a list of all the books we have in our DB. The POST method is addOrder() which inserts a user order into the DB. Those were the only methods we needed for our website. 
- Requirement 3
	- This requirement is fulfilled by all of our servlets that interact with the DB in any way. When we need to retrieve books from the DB or when we need to insert something into the DB, our servlet creates a client and targets a specific URI in order to send over the request to the REST backend application that is running alongside the client application. 

#RESTful Methods Implemented
-getBookById()
	-Method Type: GET
	-Request URL: http://localhost:8080/FlexbooksRestService/v1/api/books/{id}
	-Sample Request: http://localhost:8080/FlexbooksRestService/v1/api/books/0
	-Sample Response: {
					    "author": "Lisa A. Urry ,Michael L. Cain, Steven A. Wasserman, Peter V. Minorsky, Jane B. Reece",
					    "category": "science",
					    "edition": 11,
					    "id": 0,
					    "image": "./images/CampbellBiology.jpg",
					    "isbn10": "0134093410",
					    "isbn13": "978-0134093413",
					    "language": "English",
					    "pageCount": "1488 pages",
					    "price": "$273.32",
					    "productDimensions": "2.1 x 9.3 x 11 inches",
					    "publisher": "Pearson; 11 edition (October 29 2016)",
					    "quantity": 10,
					    "shippingWeight": "7.6 pounds",
					    "title": "Campbell Biology (11th Edition)",
					    "type": "Hardcover"
					  }
-getAllBooks()
	-Method Type: GET
	-Request URL: http://localhost:8080/FlexbooksRestService/v1/api/books/
	-Sample Request: http://localhost:8080/FlexbooksRestService/v1/api/books/
	-Sample Response:[
					    {
					        "author": "Lisa A. Urry ,Michael L. Cain, Steven A. Wasserman, Peter V. Minorsky, Jane B. Reece",
					        "category": "science",
					        "edition": 11,
					        "id": 0,
					        "image": "./images/CampbellBiology.jpg",
					        "isbn10": "0134093410",
					        "isbn13": "978-0134093413",
					        "language": "English",
					        "pageCount": "1488 pages",
					        "price": "$273.32",
					        "productDimensions": "2.1 x 9.3 x 11 inches",
					        "publisher": "Pearson; 11 edition (October 29 2016)",
					        "quantity": 10,
					        "shippingWeight": "7.6 pounds",
					        "title": "Campbell Biology (11th Edition)",
					        "type": "Hardcover"
					    },
					    {
					        "author": "Lee Drickamer, Stephen Vessey, Elizabeth Jakob",
					        "category": "science",
					        "edition": 5,
					        "id": 1,
					        "image": "./images/AnimalBehavior.jpg",
					        "isbn10": "0070121990",
					        "isbn13": "978-0070121997",
					        "language": "English",
					        "pageCount": "432 pages",
					        "price": "$215.33",
					        "productDimensions": "8.8 x 0.8 x 11.1 inches",
					        "publisher": "McGraw-Hill Science/Engineering/Math; 5 edition (July 17, 2001)",
					        "quantity": 9,
					        "shippingWeight": "2,6 pounds",
					        "title": "Animal Behavior: Mechanisms, Ecology, Evolution",
					        "type": "Hardcover"
					    },
					    {
					        "author": "Theodore E. Brown, H. Eugene LeMay, Bruce E. Bursten, Catherine Murphy, Patrick Woodward, Matthew E. Stoltzfus",
					        "category": "science",
					        "edition": 14,
					        "id": 2,
					        "image": "./images/ChemistryTheCentralScience.jpg",
					        "isbn10": "9780134414232",
					        "isbn13": "978-0134414232",
					        "language": "English",
					        "pageCount": "1248 pages",
					        "price": "$313.32",
					        "productDimensions": "1 x 8.7 x 1.7 inches",
					        "publisher": "Pearson; 14 edition (January 14, 2017)",
					        "quantity": 10,
					        "shippingWeight": "5.8 pounds",
					        "title": "Chemistry: The Central Science (14th Edition)",
					        "type": "Hardcover"
					    }
					    ...(MORE)   
                     ]
-addOrder()
	-Method Type: POST
	-Request URL: http://localhost:8080/FlexbooksRestService/v1/api/orders
	-Sample Request: {
						"firstName":"Andres",
						"lastName":"Garcia",
						"email":"abc@gmail.com",
						"phoneNo": "8185704715",
						"addres":"123 Oliver St",
						"aptNo":"",
						"zipCode":"93550",
						"shipping":"Overnight",
						"city":"Palmdale",
						"state":"California",
						"cardnum":"1111111111111111",
						"expDate":1223,
						"cvv":1234
					 }
	-Sample Response:Order Added Successfully
