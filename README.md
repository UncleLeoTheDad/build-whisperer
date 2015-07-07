# build-whisperer

The "Build Whisperer" provides a framework for bridging the status (currently just success or failure) of one or more builds to any number or status indicators.

Currently, the whisperer has the following BuildStatusRepository (status reader) types:

JSON-RegExp - works with Jenkins, Hudson, Bamboo and anythng that has a JSON API

and the following Indicator implementations:
	log4j
	CM17a - X10 for Lava Lamps or other electronic devices
	
It is all wired together using spring, allowing you a user to create their own custom BuildStatusRepositories and FeedBackDevices, allowing for simple configuration of new status bridging strategies.

To have gradle compile successfully, you need to go to build-whisperer-core/lib and execute install.bat(sh)

To use the CM17a functionality, you need to go to build-whisperer-adapters/lib and execute install.bat(sh) on the system that will be running build-whisperer.