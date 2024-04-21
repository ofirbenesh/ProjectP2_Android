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

![signup](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/6a104f83-49a2-4463-84da-77a1d5970ab3)

**Login page:**
- **Authentication:** Users can log in using their user name and password. The page includes mechanisms to handle authentication securely.
- **sign up:** Users that dont have an account can move to the sign up page to create one.

![login](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/28a0562e-73f8-4f63-9f05-f6bf4bfdb823)

**Feed page:**
- **Post Updates:** Users can post text updates and upload photos. Each post allows interactions such as likes and comments.
- **Real-Time Updates:** Utilizes a swipe-to-refresh functionality to ensure that the feed shows the latest posts without needing to restart the app.
- **Interactive Elements:** Each post includes like buttons and options to comment, similar to the traditional Facebook interface.
- **Popup Menu:** Offers additional user options like 'Delete My Account' and 'Sign Out' through an accessible popup menu.

![post](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/2c0df31b-089c-4779-8c66-dd96e435865f)
![feed](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/af5ba9ae-7f97-4e9d-b374-41ea58e75977)

* **Friends page:**
- **Friend List Management:** Users can see a list of their friends and delete friends.
- **Friend Requests:** Allows sending, receiving, and managing friend requests.
- in feed, the user will be able to send friend requests to users who are not in their friends list.

![friends](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/9b0eb122-4619-408e-b23b-7bf54554e600)
![likes](https://github.com/ofirbenesh/ProjectP2_Android/assets/155812033/4867024a-1e79-4814-acd3-c96ab75662b6)

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
