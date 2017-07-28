# Desktop Application for retrieving user data from the Fitbit API

## Ιn a few words
Fitbit App for retreiving User Data from the Fitbit API and sending e-mail for Heart-rate information according to the user selection.

## Implementation
The project is a Spring Boot application partially based on Java Spring framework, Vaadin and Java 8 and built with Maven. The databases used are MongoDB for the stored data and RedisDB for other usefull information needed in auth processes.

## Trying it out
First you have to clone or download the project from github to your workspace "git clone https://github.com/NikosMas/Fitbit_Data_Project.git"

Then you have to start mongo service and redis service required:
on linux ("sudo systemctl start mongod", "sudo systemctl start redis")
on windows just run the mongod and redis services.
   
Then you have to open the project to your IDE and go to "/gradFit/src/main/resources/application.yml" and put your information about mongo, mail and fitbit client properties. 

Finally you run the application as Spring Boot Application from the IDE or by cmd with "./mvnw spring-boot:run" .

## Suggested moves
As you ran the application it will open a browser to "fitbitApp/dashboard" and you have to fill the credentials required taken from the "dev.fitbit.com/apps". After finishing this click to go to the user data retrieving proccess.

You are redirected to "fitbitApp/userData" where you have to chose the start date & end date, the category of data you want to receive. The next step is clicking to redirect at "fitbitApp/heartRateFilter" but this is possible only if you have selected for downloading heart rate data.

At "fitbitApp/heartRateNotification" you have to fill the required fields for e-mail and heart rate details.

As soon as you have finish all steps you can "exit" and go to the finalize tab to restart the process with the same user or not.

The application is now completed.


   
