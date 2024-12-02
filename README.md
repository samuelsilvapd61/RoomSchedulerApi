# RoomSchedulerApi

## History behind the project

A fictitious company has many rooms in its buildings, for different objectives. There are meeting rooms, chatting rooms, and other types of rooms. 
The problem is that many people try to use the same rooms at the same time.

The company needs a solution to prevent these occurrences, some way to provide the employees the possibility to reserve a room for a specific day.
The company wants a software able to manage the schedules, preventing its employees to schedule the same room at the same day. That way, the employees can plan better their meetings or general activities.

Thinking about that, RoomSchedulerApi is a software created to manage schedules of rooms.
The user can log in, list the existing rooms, see their available dates, and make reservations.
Moreover, there will be admin users, that have some extra powers. In addition, when some specific events occur, users receive emails to be informed.

---

## Technically, what is RoomSchedulerApi?

Is a back-end software. A webserver REST API.

It provides services to manage the room scheduling, and a front-end system can connect to this server and show interface to users.

See the functionalities below.

---

## Functionalities

* CRUD of User
* CRUD of Room
* CRUD of Schedule
* Login - Authentication and Authorization
* Automatic email sending on some specific events

---
## Technologies

* Java 21
* Spring Boot 3
  * Spring Validation
  * Spring Data JPA
  * Spring Cache
  * Spring Data Redis
  * Spring Security
  * Spring Doc
  * Spring Mail
  * Spring AMQP
* Lombok
* MySQL
* Flyway
* Redis
* RabbitMQ
* Docker
* Swagger

---

## YouTube presentation

Check out my presentations on YouTube.

Using the system with Postman - https://www.youtube.com/watch?v=Y0hispjK8vE

Showing the code - https://www.youtube.com/watch?v=_jqM1_2DgTM

Slides - [RoomScheduler.pptx](https://github.com/user-attachments/files/17980510/RoomScheduler.pptx)

---

## How to install the system

I will assume you want to see the code and run it in an IDE.

* First, you need to install Java 21, Docker Desktop (or just Docker) and Postman in your PC.
You can use other tools in substitution to Docker and Postman, if you want to.
You can also install manually the tools that will be installed using Docker, but this will probably need a little more effort.
* After that, you need to download this project and open in your IDE.
* Now, you need to locate the docker-compose.yml file and execute in the console the "docker-compose up" command. 
This will install MySQL, Redis and RabbitMQ using the specified configurations in the file.
You can modify this file to set other configurations, if you know what you're doing.
* Finally, you need to locate and open the application.yml file and see what environment variables exist. 
After that, create in your IDE the environment variables indicated in this file, using the respective data informed in the docker-compose.yml file.
These data are the information you need to make the system able to connect to the installed tools.

 
ATTENTION: The EMAIL_PASSWORD variable is not your real email password.
This is a token you have to generate in you google account. You need to go in your Google account management and look for "app password" and create a token.
This is the data you have to set in the EMAIL_PASSWORD variable.
