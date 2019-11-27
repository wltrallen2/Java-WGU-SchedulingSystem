# Java-WGU-SchedulingSystem
WORK IN PROGRESS - Java application developed as part of the requirements for Western Governor's University C192 - Advanced Java Concepts

**CURRENT STATUS NOTE:** _*All functionality as described below is now complete. Use the username test and password test to login. However, I am still in the process of finishing the javadoc for clarity and any future improvements. There are also opportunities for refractoring that are marked that would help improve organization and/or clarity of intent in the code.*_

For this project, the student is required to implement the following requirements:

- [x] Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

- [x] Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.

- [x] Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.

- [x] Provide the ability to view the calendar by month and by week.

- [x] Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.

- [x] Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
  * COMPLETE - scheduling an appointment outside business hours
  * COMPLETE - scheduling overlapping appointments
  * COMPLETE - entering nonexistent or invalid customer data
  * COMPLETE - entering an incorrect username and password

- [x] Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.

- [x] Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.

- [x] Provide the ability to generate each  of the following reports:
  * COMPLETE - number of appointment types by month
  * COMPLETE - the schedule for each consultant
  * COMPLETE - one additional report of your choice

- [x] Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
