# How to Setup Toll Parking project locally
- Install Eclipse IDE for Java Developers version 'Eclipse IDE 2019‑12' from https://www.eclipse.org/downloads/
- Once install , start eclipse -> provide eclipse workspace

 Once eclipse is installed we need to clone toll Parking project to eclipse so that we can use it for development.
 - Copy git repo URI from https://github.com/since-vijaykumar/toll-parking, it will look like` https://github.com/since-vijaykumar/toll-parking.git`
 - Open Eclipse -> File -> Import -> Git -> Project from Git -> Clone URI
 - Paste copied URI and click next-> provide location -> next -> finish

All good to go for development.

</BR></BR>
![](https://github.com/since-vijaykumar/toll-parking/blob/master/toll-parking/gif/Setup.gif)


# How to build libs/jar from Project
- Execute Test cases to make sure our implementation is not breaking behaviour and we are getting expecte results.
- Go to Test -> toll.parking.library.test 
- Right click on AllTests.java or ParkingHandler.java -> Run as -> JUnit Test
- Once all test pass's -> right clikc on toll-parking project folder -> export -> type jar and select jar file
- Only select `src` , `doc` and `build` folder ( `build` folder is contain libs required for project as of now it only junit lib which you can skip )
- Select jar file location and provide name for jar -> next ->next-> finish

Done Toll Parking lib is ready for use.
</BR></BR>
![](https://github.com/since-vijaykumar/toll-parking/blob/master/toll-parking/gif/CreateLibs.gif)


