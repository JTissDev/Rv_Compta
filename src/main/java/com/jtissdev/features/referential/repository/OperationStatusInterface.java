package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.OperationStatus;

import java.util.List;
import java.util.Optional;

/**
 *
 *  * @author jtiss
 *  * @version 1.0.0
 *  * @since 0.6
 */
public interface OperationStatusInterface {

	void save(OperationStatus  operationStatus);
	void save(List<OperationStatus> operationStatusList);
	void update(OperationStatus  operationStatus);
	void delete(OperationStatus  operationStatus);
	Optional<OperationStatus> load();

}
