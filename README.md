# facebook app project

## Description
This Facebook app is a social media application designed for Android, 
built using Java in Android Studio with an MVVM architecture.
The app connects to a backend JavaScript server and a MongoDB database, 
allowing users to interact in real-time. It mimics core functionalities of the popular Facebook platform, 
focusing on user interactions such as posting updates, commenting, and managing friendships.

## Features
* **Sign up page:**
- **User Registration:** Allows new users to create an account by entering their personal information, including name, email, and password.
- **Data Validation:** Ensures all user input is valid and alerts the user if any corrections are needed.
- **Privacy Settings:** Users can configure their privacy preferences during registration, deciding what information is visible to others.

**Login page:**
- **Authentication:** Users can log in using their user name and password. The page includes mechanisms to handle authentication securely.
- **sign up:** Users that dont have an account can move to the sign up page to create one.
- 
* **Feed page:**
 **Post Updates:** Users can post text updates and upload photos. Each post allows interactions such as likes and comments.
- **Real-Time Updates:** Utilizes a swipe-to-refresh functionality to ensure that the feed shows the latest posts without needing to restart the app.
- **Interactive Elements:** Each post includes like buttons and options to comment, similar to the traditional Facebook interface.
- **Popup Menu:** Offers additional user options like 'Delete My Account' and 'Sign Out' through an accessible popup menu.

  
* ![feed](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/f1052d7c-9506-400d-83a0-59dc13bf9774)
* ![likes](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/0d11a95a-65f5-4b55-ad14-98d4d0b6b45e)
![post](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/36581391-d20b-4573-9d39-976e65f2ffe5)

* **Friends page:**
- **Friend List Management:** Users can see a list of their friends and delete friends.
- **Friend Requests:** Allows sending, receiving, and managing friend requests.

![friendReq](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/223eb685-6269-42b7-a7b5-68d973bef8a3)


## Installing And Executing
    
To clone and run this application, you'll need [Git](https://git-scm.com) installed on your computer.
  
From your command line:
  
```bash
# Clone this repository.
$ git clone 

# For running the Server - go into the repository.
$ cd 

# start the server (copy the Server folder into a different project and execute it from there).
$ npm start

# For running the Client - go into the repository.
$ cd 

# start the client.
# You will have to use an emulator for an android application.
