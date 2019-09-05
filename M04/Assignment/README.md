# Services and Broadcasts
Coding Assignment: Sprint 4, Module 4
Create an Android app that downloads a large image and puts it into an ImageView

### STEP 0 - Create Android Project
A) Using the Android Studio IDE, create a New -> Project

B) Start with an Activity (this can be a Fullscreen Activity or an Empty Activity if you prefer).

C) Define your own package name

D) Other settings:

Minimum API: 22

Language: Kotlin

True: Use androidx.* artifacts

E) Click Finish to create the project

### STEP 1 - SETUP
In AndroidManifest.xml add 

        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        <uses-permission android:name="android.permission.INTERNET"/>

### STEP 2 - Setup Initial UI 

In activity_main.xml add an ImageView and a Button. The ImageView should fill most of the screen, leaving room for the Button.

### STEP 3 - Setup the Utility files
You will need a NetworkAdapter.kt and a BitmapUtils.kt file. These are available in the project at M04/GuidedProject in this repository.

### STEP 4 - Setup the Service class
A) Set up a Service to perform the file download. You'll need to create a new Service and register it in the AndroidManifest.xml file
B) Select an online image for download and record the URL. (You can test on https://i.imgur.com/HaSmgGn.jpg if you cannot find an image you prefer.)
C) In your Service onStartCommand, set up a Thread to use the NetworkAdapter function NetworkAdapter.getBitmapFromUrl to perform the download. Note that the function can take a width and a height, but will return the full sized image if 0 is passed for either the width or height.
D) Stop your Service in the appropriate place.

### STEP 5 - Send a broadcast.
A) When the download is complete, set up an Intent to send a Broadcast. Make sure to create and add an action to the Intent.
B) Attach the Bitmap as a Parcelable extra to the Intent.
C) Send a broadcast using LocalBroadcastManager and the Intent you created. 

### STEP 6 - Start the Service
A) In your activity, set up your Button with a click lisenter.
B) In the click listener, create an Intent to start the Service.
C) (Optional) If you want or need to scale the image to your ImageView, you should pass them to the Service via the Intent. For example, see the code below:

     val serviceIntent = Intent(this, LargeImageDownloadService::class.java)
     serviceIntent.putExtra(LargeImageDownloadService.BITMAP_WIDTH, fullscreen_content.width)
     serviceIntent.putExtra(LargeImageDownloadService.BITMAP_HEIGHT, fullscreen_content.height)

D) Start the Service 

### STEP 7 - Set up a BroadcastReceiver
A) In your Activity, set up a member variable to hold a BroadcastReceiver which will receive the Bitmap.
B) In your BroadcastManager onReceive method, get the Bitmap from the Intent and put it into your ImageView
C) Set up an IntentFilter with the action you set up for the Intent passed to the BroadcastManager (step 5)
D) Register your BroadcastReceiver in an appropriate lifecycle callback in your Activity.
E) Unregister your BroadcastReceiver in the matching lifecycle callback (e.g., register in onResume, unregister in onPause)

### Stretch goals
A) Try adding more ImageViews and downloading multiple images.
B) Try using an IntentService instead of a Service. You can add this as a separate class and call it in your Activity instead of your earlier created Service. What changes?
C) Try using DownloadManager to download the image.
