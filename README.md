ExoSim - Exoplanet Modeling and Generator
=================================
This is a nonexperimental science fair project by Mitchell Hynes and Joshua Murphy for the Canada Wide Science Fair 2016.
We won the IEEE award for demonstrating computer science, although we lost a spot on the trip to CWSF.

Using the project can be broken down into 3 easy steps

Cloning
----------------------------------
`````````bash
git clone https://github.com/ecumene-software/exosim.git # through https
git clone git@github.com:ecumene-software/exosim.git     # through ssh (requires key)
`````````

Building
----------------------------------
`````````bash
gradle --version   # test to see if it's working, you should use gradle 2.11
gradle clean build # cleans previous builds, and builds the application
`````````

Using
----------------------------------
The program is not very user friendly, since it's a command line app. There's a few ways to begin...
This one should get you the splash-screen + the first runnable (#1)
`````````bash
gradle run
`````````
This one allows you to use it how it was intented, it builds it and makes an executable in the same directory.
`````````bash
gradle build distZip && unzip ./build/distributions/exosim.zip
./exosim/bin/exosim
`````````
After it's run you can use whatdo (sort of a glorified help) to find out what it can do.

If you run into any problems email me at mitchell.hynes@ecumene.xyz or ecumene@outlook.com (forwards to the other, I check both)

All product and company names are trademarks™ or registered® trademarks of their respective holders. Use of them does not imply any affiliation with or endorsement by them. 
