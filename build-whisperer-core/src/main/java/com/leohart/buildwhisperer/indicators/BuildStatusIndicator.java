package com.leohart.buildwhisperer.indicators;

import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */
public interface BuildStatusIndicator {

	void indicate(BuildStatus status);

}
