# Java-WGU-SchedulingSystem
WORK IN PROGRESS - Java application developed as part of the requirements for Western Governor's University C192 - Advanced Java Concepts

**CURRENT STATUS NOTE:** _*The Login scene is implemented. Use the username test and password test to login. Additionally, the customer information appears in a table on the "View Customer Database" scene and the Add/Modify Customer scene is designed, allowing users to add new customers (including addresses, cities, and countries) to the database. To get to the Add/Modify Customer scene, click on the "View Customer Database" button and then on the "Add/Modify Customer" button. All return, exit, and cancel buttons are also implemented.*_

**NEXT STEPS:** _*Allow the user to delete a record directly from the View Customer Database scene, and allow the customer to choose a customer record to modify.*_

For this project, the student is required to implement the following requirements:

- [x] Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

- [ ] Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.

- [ ] Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.

- [ ] Provide the ability to view the calendar by month and by week.

- [ ] Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.

- [ ] Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
  * scheduling an appointment outside business hours
  * scheduling overlapping appointments
  * entering nonexistent or invalid customer data
  * entering an incorrect username and password

- [ ] Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.

- [ ] Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.

- [ ] Provide the ability to generate each  of the following reports:
  * number of appointment types by month
  * the schedule for each consultant
  * one additional report of your choice

- [ ] Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
