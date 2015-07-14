package com.leohart.buildwhisperer.indicators.cm17a;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.leohart.buildwhisperer.indicators.BuildStatusIndicatorException;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;
import com.pragauto.X10Device;

/**
 * @author Leo Hart
 */
public class CM17ABuildStatusIndicatorTest {

	private X10Device passDevice;
	private X10Device failDevice;

	private CM17ABuildStatusIndicator indicator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.passDevice = EasyMock.createMock(X10Device.class);
		this.failDevice = EasyMock.createMock(X10Device.class);
		this.indicator = new CM17ABuildStatusIndicator(this.passDevice, this.failDevice);
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)}
	 * .
	 */
	@Test
	public void testIndicateArrayFailure() throws Exception {
		this.passDevice.off();
		org.easymock.EasyMock.expectLastCall().once();
		this.failDevice.on();
		org.easymock.EasyMock.expectLastCall().once();

		EasyMock.replay(this.passDevice);
		EasyMock.replay(this.failDevice);

		BuildStatus[] statuses = { new SimpleBuildStatus(true), new SimpleBuildStatus(false) };

		this.indicator.indicate(statuses);
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)}
	 * .
	 */
	@Test
	public void testIndicateArraySuccess() throws Exception {
		this.passDevice.on();
		org.easymock.EasyMock.expectLastCall().once();
		this.failDevice.off();
		org.easymock.EasyMock.expectLastCall().once();

		EasyMock.replay(this.passDevice);
		EasyMock.replay(this.failDevice);

		BuildStatus[] statuses = { new SimpleBuildStatus(true), new SimpleBuildStatus(true) };

		this.indicator.indicate(statuses);
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#turnOff()}
	 * .
	 */
	@Test(expected = BuildStatusIndicatorException.class)
	public void testIndicateException() throws Exception {
		this.passDevice.on();
		org.easymock.EasyMock.expectLastCall().andThrow(new RuntimeException());

		EasyMock.replay(this.passDevice);

		this.indicator.indicate(new SimpleBuildStatus(true));
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)}
	 * .
	 */
	@Test
	public void testIndicateFailure() throws Exception {
		this.passDevice.off();
		org.easymock.EasyMock.expectLastCall().once();
		this.failDevice.on();
		org.easymock.EasyMock.expectLastCall().once();

		EasyMock.replay(this.passDevice);
		EasyMock.replay(this.failDevice);

		this.indicator.indicate(new SimpleBuildStatus(false));
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)}
	 * .
	 */
	@Test
	public void testIndicateSuccess() throws Exception {
		this.passDevice.on();
		org.easymock.EasyMock.expectLastCall().once();
		this.failDevice.off();
		org.easymock.EasyMock.expectLastCall().once();

		EasyMock.replay(this.passDevice);
		EasyMock.replay(this.failDevice);

		this.indicator.indicate(new SimpleBuildStatus(true));
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#turnOff()}
	 * .
	 */
	@Test
	public void testTurnOff() throws Exception {
		this.passDevice.off();
		org.easymock.EasyMock.expectLastCall().once();
		this.failDevice.off();
		org.easymock.EasyMock.expectLastCall().once();

		EasyMock.replay(this.passDevice);
		EasyMock.replay(this.failDevice);

		this.indicator.turnOff();
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.indicators.cm17a.CM17ABuildStatusIndicator#turnOff()}
	 * .
	 */
	@Test(expected = BuildStatusIndicatorException.class)
	public void testTurnOffException() throws Exception {
		this.passDevice.off();
		org.easymock.EasyMock.expectLastCall().andThrow(new RuntimeException());

		EasyMock.replay(this.passDevice);

		this.indicator.turnOff();
	}
}
