## Day 1 - Commits on Nov 22, 2023

The search and filter functions are working as expected. I focused on the backend, studying in detail how to construct Spring Boot CRUD REST APIs. I implemented CRUD for products, which will need Get, GetById, and Post. There’s no need for Put and Delete as I’m not implementing Admin.

All tests are to be done, then pushed to GitHub.

I have detailed the process of how the shopping cart should be working. I have some basic ideas on all the four CRUD REST APIs on the cart service.

However, I’m not sure how to retrieve the initial cart info or make it work with the front end, without user login.

I may need to spend some time learning about WebLet? sessions/token, then the cart info can be remembered when the customer is browsing and adding products.

## Day 2 - Nov 23, 2023

Today, I learned about JUnit Mockito tests. The day went as expected, I have successfully implemented POST and GET products tests and controllers, service, and reached ~100% test coverage except for one line. I also implemented PUT AND DELETE REST API and tested it in Postman.

Tests were written after studying JUnit test and Mockito, testing from the getAllProducts controller’s method, then moved to test the service naturally. When I thought it was done and tried to find out any flaws by studying example Spring Boot CRUD API tests, I realized the route mapping part was not tested in my unit tests. Luckily, it’s only one step away - I just need to test when calling the endpoint the controller got called. Things got a lot easier when completing the first GET request tests and controller, service. Hooray!

I solved a tricky problem (I thought it was at first but no longer) by setting drop-down filters only visible on the main page using ngIf logic. So that there’s no need to worry about the route messing up with search and filter functions.

Before I moved on to implement the shopping cart function, I decided to implement the user authentication first, because I want the cart service to be written in the backend Spring Boot, I’ll need the session/user tokens to retrieve/persist the cart items. So I spent the evening learning how Auth0 works and implemented it in my Angular frontend. Now users can log in and log out! I also built a user profile to display the authenticated user’s information.

The next step is to find out how to retrieve the user id. When the user clicks addToCart, the cart service (add/post) will be called, adding the user and item to the cart.

I asked myself not to drink coffee when I put the cup in my mouth today because I deserve a nice sleep after staying awake on the bed till 2 am for three days.

But things are getting crazy - I’m still awake and excited now at 2:08 😭

## Day 3 - Nov 24, 2023

I successfully implemented the shopping cart add and get requests APIs with the user id from Auth0 retrieved and sent by the frontend. But the frontend is complaining because the database cannot find the user due to my database setup, who knows when saving to DB will the user id is actually changed.

Important learning: make things simple. Do not implement extra classes, fields, methods, if I don’t care about that bit of code and don’t need them. For example, the User table, class is not needed, I just need the user id to link to the product in their cart. The Product contains too many fields. I would have used 3-4 important properties only including name, time, isVegan, price.

Another learning is when debugging for a while without success, remember to take a break and restart the IDE/computer, sometimes things just “magically” work after a restart. Today I was testing the addToCart controller sending post requests but it kept complaining or even no error messages after several debugging and rerunning the application. I decided to take a break and shut down the IntelliJ. When I was back, opened the IntelliJ and ran the app, the POST API worked successfully at that time!

I now have a better understanding of mockMvc. I've refactored and structured the product controller and services tests to be more elegant.

## Day 4 - Nov 25, 2023

- Made `addToCart` work in the frontend.
- Got `cart` working with the `cart-info` page and `cart-status`.

`addToCart` is partially working. I need to create a shared service in Angular between `cart-status` and `product-list` (where the `addToCart` button is) to enable them to communicate. When the button is clicked, `cart-status` should be aware of it and refresh the real-time cart information.

If I can describe the problem well, the answer is already there. Think about how a computer (or me as the computer) would approach the problem. For example, let's say I'm adding a product to the basket, and another person is holding a board updating the basket item quantity and total price. They're able to count the basket and calculate. We can't speak to each other. How can they know I'm adding an item to my basket and then they can increase the quantity and price? 
I think we need some kind of signals like lighting colors. I add an item, then I press the red light and the person knows it and starts their job calculating the basket. 

On the `Cart` page, display the products in the way I want with image, quantity, unit price, and total price for each product type in the basket. I need to organize the data from the backend `get cart` API. 

## Day 5 - Nov 26, 2023
 
Successful Implementations: 
- `RemoveFromCart` API
- Checkout with Stripe. Had a workaround when implementing the payment session by creating only one product.
