package com.leohart.buildwhisperer.execution;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leohart.buildwhisperer.bridges.BuildStatusBridge;

/**
 * @author Leo Hart
 */
public class BuildStatusBridgeRunner {

	private static final String APP_CONTEXT = "com/leohart/buildwhisperer/execution/defaultApplicationContext.xml";
	private static final String BRIDGE_BEAN_NAME = "buildStatusBridge";
	private static final String REPEAT_INTERVAL_PARAM = "repeatInterval";

	private static Log log = LogFactory.getLog(BuildStatusBridgeRunner.class);

	private static BuildStatusBridge buildStatusBridge;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			log.debug("Arguments passed in were: " + ArrayUtils.toString(args));

			BuildStatusBridgeRunner.initializeContext(args);

			log.info("Bridging build status.");

			Integer repeatInterval = BuildStatusBridgeRunner
					.getRepeatInterval();

			if (repeatInterval == null) {
				buildStatusBridge.bridgeBuildStatus();
			}
			else {
				log.info("Putting runner on repeat.");
				while (true) {
					buildStatusBridge.bridgeBuildStatus();

					log.info(String
							.format("Waiting %s seconds", repeatInterval));
					Thread.sleep(repeatInterval * 1000);
				}
			}
		}
		catch (Exception e) {
			log.error("An unexpected exception occured which running: ", e);
		}
	}

	private static ApplicationContext getContext(final String[] args) {
		if (args.length <= 0) {
			log
					.info("No external config was specified.  Using default on classpath: "
							+ APP_CONTEXT);

			return new ClassPathXmlApplicationContext(APP_CONTEXT);
		}

		log.info("Using external config specified at: " + args[0]);

		return new FileSystemXmlApplicationContext(args[0]);
	}

	private static Integer getRepeatInterval() {
		log.debug("Determining whether repeat interval was specified.");
		String repeatInterval = System.getProperty(REPEAT_INTERVAL_PARAM);

		if (repeatInterval == null) {
			log.debug("No repeat interval specified.");
			return null;
		}
		log.debug(String.format("Repeat interval of %s seconds was specified.",
				repeatInterval));

		return Integer.parseInt(repeatInterval);
	}

	private static void initializeContext(final String[] args) {
		log.info("Loading application context.");
		ApplicationContext context = BuildStatusBridgeRunner.getContext(args);

		log.debug("Getting bridge bean");
		buildStatusBridge = (BuildStatusBridge) context
				.getBean(BRIDGE_BEAN_NAME);
	}

}
