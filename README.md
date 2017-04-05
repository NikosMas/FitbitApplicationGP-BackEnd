# Fitbit_Data_Project
Fitbit App for retrieving User Data from the Fitbit API and sending e-mail for unexpected high Heart-rate


The app starts a local server at localhost waiting the user to visit it and trigger it. Then the user continues to login form and Fitbit Server sends to localhost the authorization code to complete the auth process. After completion, the app starts to send the calls to Fitbit Server to get data about the user and it stores them into the database. Finally the app filters the heart data and send an e-mail to the user with the dates and minutes when his heart rate was at its peak.


The project is a Spring Boot application partially based on Java Spring framework and Java 8. The databases used are MongoDB for the stored data and RedisDB for the authorization code needed to auth process.
   
