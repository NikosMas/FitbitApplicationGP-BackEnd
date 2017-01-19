# Fitbit_Data_Project
Fitbit App for retrieving User Data from the Fitbit API and sending e-mail for unexpected high Heart-rate


-> This application should run after Fitbit_Code. It creates and executes the requests required for the Authorization with the API.

-> After the first request for the Authorization-Code is set a delay waiting the user to login and allow the scopes and the Fitbit API to send the code to the localhost:8080. Then it continues to access-token receiving and data saving to Mongo database.
   
-> Finally it scans the database for dates with high heart-rate and sends these dates to the users mail.   
   
