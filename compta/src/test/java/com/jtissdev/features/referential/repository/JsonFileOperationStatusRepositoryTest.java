package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Test Suite : JsonFileOperationStatusRepository")
@ActiveProfiles("test")
@TestGroup("Core Repository")
@ExtendWith(TestResultLogger.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFileOperationStatusRepositoryTest {

	@Autowired
	private JsonFileOperationStatusRepository repository;

	@Test
	@Order(1)
	@DisplayName("Chargement initial : Doit contenir des données (via le seed)")
	void testFindAll() {
		List<OperationStatus> list = repository.findAll();
		assertNotNull(list);
		assertFalse(list.isEmpty(), "La liste des statuts ne devrait pas être vide après initialisation du seed.");
	}

	@Test
	@Order(2)
	@DisplayName("Sauvegarde : Ajouter un nouveau statut")
	void testSave() {
		OperationStatus newStatus = new OperationStatus()
				                            .setCode("TEST")
				                            .setName("Test Status")
				                            .setColor("#FFFFFF");

		repository.save(newStatus);

		assertTrue(repository.findById("TEST").isPresent());
	}

	@Test
	@Order(3)
	@DisplayName("Suppression : Retirer le statut de test")
	void testDelete() {
		repository.deleteById("TEST");
		assertFalse(repository.findById("TEST").isPresent());
	}
}