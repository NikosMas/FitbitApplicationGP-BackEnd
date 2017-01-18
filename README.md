# Fitbit_Data_Project
Fitbit App for retrieving User Data and sending e-mail for unexpected high Heart-rate


-> This application should run after Fitbit_Code. It creates and executes the requests required for the Authorization.

-> After the first request for the Authorization-Code is set a delay waiting the user to login and allow the scopes. 
   Then it continues to access-token receiving and data saving to Mongo database.
   
-> Finally it scans the database for dates with high heart-rate and sends these dates to the users mail.   
   
