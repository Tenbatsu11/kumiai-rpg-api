Hellow and Welcome to the page for the Kumiai GO Learn api for desktop app


----- Quick guide on the use of the api -----

The api is meant to be used with the desktop Kumiai Go Learn app and later on with the Kumiai Go Learn website.
To check the use cases and fonctionnality of the desktop app, please refer to the according repository.

----- Before starting the api -----

Before anything else, you should create the "application.properties" file under the resources folder. 
Note that the application works under maven with spring boot dependencies : as follow 
<img width="720" height="535" alt="image" src="https://github.com/user-attachments/assets/c4ca7dd7-0b42-4370-a4f3-1e52e22e9e83" />

After installing all dependencies, modify the application.properties as follow : 

spring.jpa.hibernate.ddl-auto=update

spring.datasource.url= jdbc:mysql:"your db url"

spring.datasource.username= DB username

spring.datasource.password= DB password

jwt.secret= JWT Token generated for your version of the api
jwt.expiration=86400000 #You can modify this value to change the validity duration of your token

----- Final note -----

Please consider using a mariaDB database as the whole ecosystem revolves around it.

Thank you for your reading and good luck.
