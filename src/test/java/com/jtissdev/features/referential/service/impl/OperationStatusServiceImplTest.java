package com.jtissdev.features.referential.service.impl;

import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.repository.OperationStatusRepository;
import com.jtissdev.features.referential.service.OperationStatusService;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(TestResultLogger.class)
@DisplayName("OperationStatus Service Test Suite")
@TestGroup("Referential Service")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperationStatusServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(OperationStatusServiceImplTest.class);

	@Autowired
	private OperationStatusService service;

	@MockBean
	private OperationStatusRepository repository;

	private OperationStatus testStatus;

	@BeforeEach
	void setUp() {
		testStatus = new OperationStatus()
				             .setCode("PLN")
				             .setName("Planifié")
				             .setColor("#CCCCCC");
	}

	@Test
	@Order(1)
	@DisplayName("Global Read: Retrieve full status list")
	void testGetAllStatuses() {
		when(repository.findAll()).thenReturn(List.of(testStatus));

		List<OperationStatus> result = service.getAllStatuses();

		assertNotNull(result, "The returned status list must not be null");
		assertEquals(1, result.size(), "The status list must contain exactly one element");
		assertEquals("PLN", result.get(0).getCode(), "The first element's code must match 'PLN'");

		logger.info("         ✅ SUCCESS: Global status list retrieval verified");
	}

	@Test
	@Order(2)
	@DisplayName("Targeted Read: Extract status by valid ID")
	void testGetStatusById_Found() {
		when(repository.findById("PLN")).thenReturn(Optional.of(testStatus));

		Optional<OperationStatus> result = service.getStatusById("PLN");

		assertTrue(result.isPresent(), "The Optional container must contain the requested status");
		assertEquals("Planifié", result.get().getName(), "The extracted status name must be 'Planifié'");

		logger.info("         ✅ SUCCESS: Unitary status retrieval by identifier verified");
	}

	@Test
	@Order(3)
	@DisplayName("Defensive Check: Handle null or blank ID inputs")
	void testGetStatusById_NullOrBlank() {
		assertTrue(service.getStatusById(null).isEmpty(), "A null identifier must return an empty Optional");
		assertTrue(service.getStatusById("   ").isEmpty(), "A blank identifier must return an empty Optional");

		verify(repository, never()).findById(any());
		logger.info("         ✅ SUCCESS: Defensive protection against invalid identifiers verified");
	}

	@Test
	@Order(4)
	@DisplayName("Persistence: Insert a new valid status")
	void testCreateStatus_Success() {
		when(repository.findById("PLN")).thenReturn(Optional.empty());
		when(repository.save(any(OperationStatus.class))).thenReturn(testStatus);

		OperationStatus result = service.createStatus(testStatus);

		assertNotNull(result, "The created status returned by the service must not be null");
		verify(repository, times(1)).save(testStatus);

		logger.info("         ✅ SUCCESS: Creation and persistence of a new status verified");
	}

	@Test
	@Order(5)
	@DisplayName("Security Check: Throws IllegalStateException on duplicate code")
	void testCreateStatus_DuplicateCode() {
		when(repository.findById("PLN")).thenReturn(Optional.of(testStatus));

		assertThrows(IllegalStateException.class, () -> service.createStatus(testStatus),
				"Attempting to insert a status code that already exists must throw an IllegalStateException");

		verify(repository, never()).save(any());
		logger.info("         ✅ SUCCESS: Duplicate primary key prevention verified");
	}

	@Test
	@Order(6)
	@DisplayName("Persistence: Update status and enforce ID consistency")
	void testUpdateStatus_Success() {
		OperationStatus updateData = new OperationStatus().setName("New Name").setColor("#000000");

		when(repository.findById("PLN")).thenReturn(Optional.of(testStatus));
		when(repository.save(any(OperationStatus.class))).thenAnswer(i -> i.getArguments()[0]);

		OperationStatus result = service.updateStatus("PLN", updateData);

		assertEquals("PLN", result.getCode(), "The service must force the target code into the payload sent to the repository");
		assertEquals("New Name", result.getName(), "The status name must be successfully updated to 'New Name'");

		logger.info("         ✅ SUCCESS: Unitary update and ID integrity verified");
	}

	@Test
	@Order(7)
	@DisplayName("Security Check: Throws NoSuchElementException if entity to update does not exist")
	void testUpdateStatus_NotFound() {
		when(repository.findById("ERR")).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> service.updateStatus("ERR", testStatus),
				"Updating a non-existent status code must throw a NoSuchElementException");

		verify(repository, never()).save(any());
		logger.info("         ✅ SUCCESS: Update restriction on non-existent entity verified");
	}
}