package com.jtissdev.features.referential.service;

import com.jtissdev.features.referential.dto.PaymentMethod;
import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
	List<PaymentMethod> getAllMethods();
	Optional<PaymentMethod> getMethodById(String code);
	List<PaymentMethod> searchByPartialName(String partialName);
	PaymentMethod createMethod(PaymentMethod method);
	PaymentMethod updateMethod(String code, PaymentMethod method);
	void deleteMethod(String code);
}