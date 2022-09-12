# NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on application shut down, you can change the spring.database.jdbc-url to point at a file like `jdbc:h2:file:/{your file path here}`

## Starting the Application

Run the `RemoteMonitoringManagementApplication` class

Go to:
* http://localhost:8080/swagger-ui/index.html

We should see the swagger-ui home. The application is working and connected to the H2 database.
When logging in using (username or email and password) we get a security token that we have to provide in every request we make to the other APIs.

## Auth Controller

To use the application you can signup or use a default customer (username: admin, password: admin):
  * ```
    curl -X POST "http://localhost:8080/api/auth/signin" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"admin\", \"usernameOrEmail\": \"admin@admin.com\"}"
    ```
  * ```
    { 
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjYyOTc5OTk4LCJleHAiOjE2NjMwODc5OTh9.w5uYdH03znfjVzTbYelEBavoEc3RWEmrVmdEiaXmjSA",
        "tokenType": "Bearer"
    }
    ```
  * Copy the content of accessToken, go up and click on the button Authorize, paste the token and click autorize;
    -- ![image](https://user-images.githubusercontent.com/6773754/189639423-1e9047ca-08d2-4555-98af-4436073d232d.png)

## Customer Controller

Here you can list, update and delete a customer, add a device, order a service and generate a monthly statement with expendintures in services for the last 30 days.

Let's add a device to our logged customer:
   * ```
     curl -X POST "http://localhost:8080/customers/devices/?systemName=Windows%20OS&type=WORKSTATION" -H "accept: */*" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjYyOTc5OTk4LCJleHAiOjE2NjMwODc5OTh9.w5uYdH03znfjVzTbYelEBavoEc3RWEmrVmdEiaXmjSA" -d ""
     ``` 
   * ```
     {
        "id": 1,
        "documentId": "052.382.456-85",
        "fullName": "admin",
        "nickname": "admin",
        "email": "admin@admin.com",
        "devices": [
            {
            "id": 1,
            "systemName": "Windows OS",
            "type": "WORKSTATION",
            "purchasedServices": null
            }
        ]}
     ``` 

Let's purchased a service for our newly created device:
   * ```
     curl -X POST "http://localhost:8080/customers/services/?cost=4&description=Regular%20monthly%20maintenance&deviceId=1&status=DONE&type=DEVICE_MAINTENANCE" -H "accept: */*" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjYyOTc5OTk4LCJleHAiOjE2NjMwODc5OTh9.w5uYdH03znfjVzTbYelEBavoEc3RWEmrVmdEiaXmjSA" -d ""
     ``` 
   * ```
     {
        "id": 1,
        "documentId": "052.382.456-85",
        "fullName": "admin",
        "nickname": "admin",
        "email": "admin@admin.com",
        "devices": [
            {
            "id": 1,
            "systemName": "Windows OS",
            "type": "WORKSTATION",
            "purchasedServices": [
                {
                "id": 1,
                "type": "DEVICE_MAINTENANCE",
                "description": "Regular monthly maintenance",
                "cost": 4,
                "executionDate": [
                    2022,
                    8,
                    18
                ],
                "status": "DONE",
                "deviceId": 1
                }
            ]
            }
        ]
     } 
    
     ```

## Device Controller

Here a logged customer can list, update and delete their devices, if Admin all devices can be listed, updated or deleted. 

## Service Controller

## H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:

```yml
url: jdbc:h2:mem:localdb
username: sa 
password: password
```

You should be able to see a db console now that has the Sample Repository in it.

Type:

```sql
SELECT * FROM SAMPLE;
````

Click `Run`, you should see two rows, for ids `1` and `2`

### Suggestions

Feel free to remove or repurpose the existing Sample Repository, Entity, Controller, and Service. 
