# Ethermine Tracker
[![Known Vulnerabilities](https://snyk.io/test/github/Traptricker/EthermineTracker/badge.svg)]
This project is for those who might have multiple people mining to the same pool, and want to track how much each person is owed. It uses selenium to launch Chrome in headless mode and tracks how much each computer/person is mining on ethermine.org every 10 minutes. It stores the data in a csv file and displays it on the JavaFX gui.

# How to use it
Go to ![image](https://user-images.githubusercontent.com/85963782/147962766-318f4ead-ee04-4162-8e8f-1bb309a403cb.png) and download the latest release's zip file. Extract the folder in the zip to somewhere you will remember, your Desktop is a good place, then open the folder, and run (double-click) `EthermineTracker.jar`. If everything works correctly, this window should appear: 

![image](https://user-images.githubusercontent.com/85963782/147963971-4c25348b-80d6-478a-9a6f-750bbcdce1a4.png)

Now, just enter your miner address (mine is: `0fB3583c11320BB9c7F512e06ce9c3A9218568C9`) into the text box, click track, and leave the program running for as long as you want to track your miner address. If you want to stop tracking, just close the program. The reset button will delete all data collected, so **don't click this unless you want to reset everything.**


# Bugs
If you are getting the error `A JNI error has occurred, please check your installation and try again`, first try installing the newest java jdk, if that doesn't work, try following this stack overflow post I made:
https://stackoverflow.com/questions/70560742/i-made-a-jar-file-and-when-my-friend-tries-to-open-it-they-get-the-error-a-jni

# Frameworks, Tools, and Libraries used
- [JavaFX](https://openjfx.io/)
- [Maven](https://maven.apache.org/)
- [OpenCSV](http://opencsv.sourceforge.net/)
- [Selenium](https://www.selenium.dev/documentation/)

I made this with IntelliJ Idea

# Motivation
Me and a group of my friends all mine to the same pool so that we can sell more often and reduce gas fees. I wrote this program so we could accurately track how much each person deserves.

# Donate
If you find this program useful, you can mine to my ethereum pool at `0fB3583c11320BB9c7F512e06ce9c3A9218568C9`.

MIT © [Traptricker](https://github.com/Traptricker)
