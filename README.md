# Java-WGU-SchedulingSystem
WORK IN PROGRESS - Java application developed as part of the requirements for Western Governor's University C192 - Advanced Java Concepts

**CURRENT STATUS NOTE:** _*The Login, Appointment, and Customer Scenes are implemented, including the ability to view, add, modify, and delete appointments and customers. Use the username test and password test to login. Additionally, the appointment table can be filtered and sorted, and times are adjusted according to the user's local time zone and daylight savings time.*_

**NEXT STEPS:** _*Write exception controls for the remaining three exception cases.*_

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

- [ ] Provide the ability to generate each  of the following reports:
  * number of appointment types by month
  * the schedule for each consultant
  * one additional report of your choice

- [ ] Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
