# Fitbit Desktop Application
Fitbit App for retrieving User Data from the Fitbit API and sending e-mail for Heart-rate information according to the user selection.


The app starts a local server side application at localhost waiting the user to visit it and complete the steps needed. Then the user continues to login form and Fitbit Server sends to localhost the authorization code to complete the auth process. After completion, the app starts to send the calls to Fitbit Server to get data about the user and it stores them into the database. Finally user selects the minutes and the heart zone to filter out.The app sends an e-mail to the user with the dates and minutes of the selected zone.


The project is a Spring Boot application partially based on Java Spring framework and Java 8. The databases used are MongoDB for the stored data and RedisDB for other usefull information needed in auth process.
   
