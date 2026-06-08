package com.jtissdev.features.referential.service;

import com.jtissdev.features.referential.dto.OperationStatus;
import java.util.List;
import java.util.Optional;

public interface OperationStatusService {
	List<OperationStatus> getAllStatuses();
	Optional<OperationStatus> getStatusById(String code);
	List<OperationStatus> searchByPartialName(String partialName);
	OperationStatus createStatus(OperationStatus status);
	OperationStatus updateStatus(String code, OperationStatus status);
	void deleteStatus(String code);
}