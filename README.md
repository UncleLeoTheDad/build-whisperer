# build-whisperer

The "Build Whisperer" provides a framework for bridging the status (currently just success or failure) of one or more builds to any number or status indicators.

# Status Readers
Currently, the whisperer has the following BuildStatusRepository (status reader) types:

JSON-RegExp - works with Jenkins, Hudson, Bamboo and anythng that has a JSON API

# Status Indicators
The following Indicator implementations are supported:
* log4j 
* BlinkyTape: 
 * Overview of device: http://blinkinlabs.com/blinkytape/
 * Control powered by: https://github.com/leojhartiv/BlinkyTape
* CM17a:
 * X10 for Lava Lamps or other electronic lamp devices
 * Overview of device: http://www.thex10shop.com/product/x10-x-10-firecracker-serial-computer-interface-cm17a * 

On the roadmap:
* [In progress]Speech: http://freetts.sourceforge.net/javadoc/com/sun/speech/freetts/Voice.html

# Usage
It is all wired together using spring, allowing you a user to create their own custom BuildStatusRepositories and FeedBackDevices, allowing for simple configuration of new status bridging strategies.

## Generating an "Uber-JAR"

To have gradle compile successfully, you need to go to build-whisperer-adapters/lib and execute installJarsManuallyToMaven.bat(sh)

You can then execute gradle clean test assemble shadowJar to generate a single jar with all dependencies.

Once this is complete, you can run the examples by following these instructions:
https://github.com/leojhartiv/build-whisperer/tree/master/build-whisperer-client/etc/examples

## CM17a (controlling electrical appliances like lava lamps)
To use the CM17a functionality, you need to go to build-whisperer-adapters/lib and execute installSerialCommSupportForX10.bat(sh) on the system that will be running build-whisperer.  This installs comm.jar and javax.comm.properties into your JRE.
