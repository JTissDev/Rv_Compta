package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import com.jtissdev_API.utils.TestDataLoader;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PcgCoreDTO} class.
 * <p>
 * This test ensures proper collection management and verifies
 * recursive JSON mapping functionalities for the {@code PcgCoreDTO}.
 * </p>
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("PcgCoreDTO Test Suite")
@TestGroup("PCG DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PcgCoreDTOTest {

	private static JsonArray ACCOUNTING_JSON ;

	private static final Logger logger = LoggerFactory.getLogger(PcpCoreDTOTest.class);

	private static PcgCoreDTO pcgCoreDTO;

	@BeforeAll @DisplayName("TestDataInitialization")
	static void setUp() {
		ACCOUNTING_JSON = TestDataLoader.loadFromResources("data/PCG.json").getJsonArray("data");
		validerStructure();
	}

	private static void validerStructure() {
		// Ta logique de test commune aux deux sources
		assertNotNull(ACCOUNTING_JSON, "ACCOUNTING_JSON should not be null");
		// ...
	}

	@Test @Order(1) @DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		pcgCoreDTO = new PcgCoreDTO();
		assertNotNull(pcgCoreDTO, "pcgCoreDTO should not be null");
	}

	@Test @Order(2) @DisplayName("Test Fluent API : Setters Test")
	void testFluentSetters(){
		pcgCoreDTO = new PcgCoreDTO();
		PcgCoreDTO result = pcgCoreDTO.setAccountingClasses(ACCOUNTING_JSON);

		assertAll("Pcg List should be initialized",
				() -> assertSame(pcgCoreDTO,result,"setters must return the same instance"),
				() -> assertEquals(ACCOUNTING_JSON.size(),pcgCoreDTO.getAccountingClasses().size(),"Pcg List should be initialized")
				);
	}

	@Test @Order(3) @DisplayName("Test Json Constructor")
	void testJsonConstructor() {
		pcgCoreDTO = new PcgCoreDTO(ACCOUNTING_JSON);
		assertAll("Pcg List should be initialized",
				() -> assertEquals(ACCOUNTING_JSON.size(),pcgCoreDTO.getAccountingClasses().size(),"Pcg List should be initialized")
		);
	}

	@Test @Order(4) @DisplayName("Test Json Constructor with empty array")
	void testJsonConstructorWithEmptyArray() {
		JsonArray json = Json.createArrayBuilder().build();
		pcgCoreDTO = new PcgCoreDTO(json);
		assertEquals(0, pcgCoreDTO.getAccountingClasses().size(),"No accounting classes should be inserted.");
	}

	@Test @Order(4) @DisplayName("Test Adder")
	void testAddAccountingType(){
		pcgCoreDTO = new PcgCoreDTO();

		assertAll("First list must be empty then it should be non empty",
				() -> assertTrue(pcgCoreDTO.getAccountingClasses().isEmpty(),"Non loaded list have to be empty"),
				() -> {
			pcgCoreDTO.addAccountingClass(new AccountingType(ACCOUNTING_JSON.getJsonObject(0)));
			assertEquals(1,pcgCoreDTO.getAccountingClasses().size(),"List should be non empty");
				}
				);

	}

	@Test @Order(5) @DisplayName("Test List Constructor and Getter")
	void testListConstructor() {
		List<AccountingType> list = new ArrayList<>();
		list.add(new AccountingType(ACCOUNTING_JSON.getJsonObject(0)));

		pcgCoreDTO = new PcgCoreDTO(list);
		assertEquals(1, pcgCoreDTO.getAccountingClasses().size(), "La liste devrait contenir 1 élément.");
	}

	@Test @Order(6) @DisplayName("Test Null Safety on Setters")
	void testNullSafety() {
		pcgCoreDTO = new PcgCoreDTO();

		// On teste que passer null ne provoque pas de NullPointerException et initialise une liste vide
		assertAll("Vérification de la gestion du null",
				() -> assertNotNull(pcgCoreDTO.setAccountingClasses((List<AccountingType>) null).getAccountingClasses(), "La liste ne doit pas être nulle après un set(null)"),
				() -> assertNotNull(pcgCoreDTO.setAccountingClasses((JsonArray) null).getAccountingClasses(), "La liste ne doit pas être nulle après un set(JsonArray null)"),
				() -> assertEquals(0, pcgCoreDTO.getAccountingClasses().size(), "La liste doit être vide")
		);
	}

	@Test @Order(7) @DisplayName("Test Append with addAccountingClass(JsonArray)")
	void testAppendJson() {
		pcgCoreDTO = new PcgCoreDTO();
		// On ajoute un premier élément
		pcgCoreDTO.addAccountingClass(new AccountingType(ACCOUNTING_JSON.getJsonObject(0)));
		// On ajoute le tableau complet par-dessus
		pcgCoreDTO.addAccountingClass(ACCOUNTING_JSON);

		// La taille finale doit être 1 + taille du JSON
		assertEquals(ACCOUNTING_JSON.size() + 1, pcgCoreDTO.getAccountingClasses().size(), "L'ajout JSON devrait s'additionner à la liste existante.");
	}

	@Test @Order(8) @DisplayName("Test Serialization toJson")
	void testToJsonSerialization() {
		pcgCoreDTO = new PcgCoreDTO(ACCOUNTING_JSON);
		jakarta.json.JsonObject result = pcgCoreDTO.toJson();

		assertAll("Vérification du format JSON de sortie",
				() -> assertEquals(ACCOUNTING_JSON.size(), result.getInt("size"), "Le champ 'size' du JSON doit être correct"),
				() -> assertTrue(result.containsKey("accountingClasses"), "Le JSON doit contenir la clé 'accountingClasses'")
		);
	}

	@Test @Order(8) @DisplayName("Test toString Format")
	void testToString() {
		pcgCoreDTO = new PcgCoreDTO(ACCOUNTING_JSON);
		String output = pcgCoreDTO.toString();

		assertAll("Vérification du contenu du toString",
				() -> assertTrue(output.contains("PCG Core DTO"), "Le titre doit être présent"),
				() -> assertTrue(output.contains("Accounting Classes"), "La section des classes doit être présente")
		);
	}

}



