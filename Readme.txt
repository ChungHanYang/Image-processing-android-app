Richard Aguilar
Yang Chung Han

App Objectives:
An app capable of accessing the Android phone camera and processing the frames of the image
that is being displayed by the lens through use of the OpenCV library. When a touch event occurs,
the processing of these frames allows for the outputting of it's coordinates in regards to the 
OpenCV RGBA matrix.The app will then have the ability to display through use of a TextView the 
RGB value of the pixels that surround the touch event as a 2-digit hex value. For Part C, we added the abilities of 
converting the image to greyscale and detecting edges in the image through use of a Canny Filter, as well as a text recognition feature with Google API.   

App Applications: 
Our app is extremely useful in the world of creative. It is obvious that color in of itself is used in almost every
aspect in life as a whole. This app makes it simple to pull a phone out and determine what RGB value of anything that
you are looking at is for any future reference. Color palettes could be created based on these collected colors, as
or utilized directly in specific projects. In addition, obtaining the edges in an image can be useful in many situations. 
For example, in order to prevent trains from derailing from a rail track an analysis can be done along the entirety
of the track in search of any edges that are out of alignment. Edge detection is useful for an individual who is 
seeking to obtain only the true edges of an image and the removal of everything else. The text recognition feature could be used for
computer vision, car license plate recognition and any other function that requires the analysis of text into a computer system. 

App New Features: 
We have modified the app to have the capability of distinguishing objects in the background of the camera's view
through edge detection techniques. The specific edge detector applied is the well known Canny edge detector which 
was available for use through the Open CV library.

We have also added the ability for the app to perform text recognition on any letter
of the alphabet and number. 
  

How to run and use:
FOR PARTS A, B, and Half of Part C (We were unable to combine the entirety of Part A,B,C in one project, so we split them up into two projects.)
1. Open Android Studio, Navigate to AguilarYang_Project2.zip, Open File "EEE508Project__2"
2. Press Play button on top menu to run the code. Choose to run on Emulator (Nexus 4) or Android phone if connected.
3. Output will contain the greyscale image with edge detection as well as our last names on the top right of the screen.

For other half of Part C
4. Navigate back to AguilarYang_Project2.zip folder and open File "EEE508project_partc"
5. Press Play button on top menu to run the code. Choose to run on EMULATOR ONLY (Nexus 4). 
6. Rotate the emulator to be Horizonal using the menu on the right. 
7. Hold a piece of paper with text on it in front of the webcam (preferably on White paper). Wait for about 20 seconds and the app will display the text message. 

Sample Input/Output:
Navigate back to AguilarYang_Project2.zip and Open "Team name and edge detection.mp4" and "Text Recognition.mp4"


Team Member Contributions:

Richard Aguilar: 

Execution of Part A - App outputting text with names
Execution of Part C App - basic color touch functionality
Implementation of Edge Detection (Canny) in an Android Studio Environment
Readme Objectives, Applications, New Features, How to run and use, Contributions

Yang Chung Han:
Execution of Part A - App outputting text with names
Execution of Part C App - basic color touch functionality
Implementation of Text Recognition using Google API
Provided samples of input and output, as well as video proof of all implementation. 