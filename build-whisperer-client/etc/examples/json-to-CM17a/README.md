If you are getting the following error while running:

ERROR [07/07/2015 12:23:07] com.leohart.buildwhisperer.execution.BuildStatusBridgeRunner(54) - An unexpected exception occured which running
:
com.leohart.buildwhisperer.indicators.BuildStatusIndicatorException: Could not send command to BuildStatusIndicator:
        at com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator.indicate(CM17ABuildStatusIndicator.java:53)
        at com.leohart.buildwhisperer.bridges.SimpleBuildStatusBridge.bridgeBuildStatus(SimpleBuildStatusBridge.java:34)
        at com.leohart.buildwhisperer.execution.BuildStatusBridgeRunner.main(BuildStatusBridgeRunner.java:45)
Caused by: javax.comm.NoSuchPortException
        at javax.comm.CommPortIdentifier.getPortIdentifier(CommPortIdentifier.java:105)
        at com.pragauto.X10Device.getPortID(Unknown Source)
        at com.pragauto.X10Device.openPort(Unknown Source)
        at com.pragauto.X10Device.off(Unknown Source)
        at com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator.indicate(CM17ABuildStatusIndicator.java:48)
        ... 2 more

...you probably don't have the CM17A serial port installed.  See the main README for details on installation.