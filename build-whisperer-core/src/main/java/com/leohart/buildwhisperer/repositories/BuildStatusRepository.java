package com.leohart.buildwhisperer.repositories;

import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */
public interface BuildStatusRepository<T extends BuildStatus> {

	T getBuildStatus();

}
