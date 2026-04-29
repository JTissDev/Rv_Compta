package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PcpCoreDTO} class.
 * <p>
 * This test ensures proper collection management and verifies
 * recursive JSON mapping functionalities for the {@code PcpCoreDTO}.
 * </p>
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("PcpCoreDTO Test Suite")
@TestGroup("PCP DTO")
public class PcpCoreDTOTest {

	/**
	 * Logger instance used for logging messages in the context of `ReferentialCoreDTOTest` tests.
	 * The logger is statically initialized and associated with the `ReferentialCoreDTOTest` class.
	 * Typically used to log test execution details, debugging information, or errors during the test lifecycle.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PcpCoreDTOTest.class);

	private static final JsonObject DATA_JSON = Json.createObjectBuilder()
			                                            // Premier Array : Les Tiers
			                                            .add("thirdParties", Json.createArrayBuilder()
					                                                          .add(Json.createObjectBuilder()
							                                                               .add("id", 1)
							                                                               .add("name", "Jean Dupont")
							                                                               .add("thirdPartyType", "CLIENT"))
					                                                          .add(Json.createObjectBuilder()
							                                                               .add("id", 2)
							                                                               .add("name", "Société Alpha")
							                                                               .add("thirdPartyType", "FOURNISSEUR"))
					                                                          .add(Json.createObjectBuilder()
							                                                               .add("id", 3)
							                                                               .add("name", "Cabinet Comptable")
							                                                               .add("thirdPartyType", "PARTENAIRE"))
			                                            )
			                                            // Second Array : Les Détails
			                                            .add("details", Json.createArrayBuilder()
					                                                            .add(Json.createObjectBuilder()
							                                                                 .add("code", ".442")
							                                                                 .add("type", "Compte")
							                                                                 .add("name", "Compte courant"))
					                                                            .add(Json.createObjectBuilder()
							                                                                 .add("code", ".Vig")
							                                                                 .add("type", "Immobilier")
							                                                                 .add("name", "Maison les Vignes"))
					                                                            .add(Json.createObjectBuilder()
							                                                                 .add("code", ".Ber")
							                                                                 .add("type", "Immobilier")
							                                                                 .add("name", "Terrain les Berthiers"))
			                                            )
			                                            .build();

	private static PcpCoreDTO pcpCoreDTO;

	@BeforeAll
	@DisplayName("Setup Object for Pcp")
	static void setUpTest() {}

	@Test
	@DisplayName("Test Empty COnstructor")
	void emptyConstructorTest() {
		pcpCoreDTO = new PcpCoreDTO();

		assertAll("Test empty constructor, list must be created and empty",
				() -> assertEquals(0, pcpCoreDTO.getDetails().size(),"No details should be inserted."),
				() -> assertEquals(0,pcpCoreDTO.getThirdParties().size(),"No third parties should be inserted.")
				);
	}

	@Test
	@DisplayName("Test Json Constructor")
	void jsonConstructorTest() {
		pcpCoreDTO = new PcpCoreDTO(DATA_JSON);

		assertAll("Test Json constructor, list must be created and filled",
				() -> assertEquals(3, pcpCoreDTO.getDetails().size(),"Three details should be inserted."),
				() -> assertEquals(3,pcpCoreDTO.getThirdParties().size(),"Three third parties should be inserted.")
				);
	}

	@Test
	@DisplayName("Test Json Constructor with empty array")
	void jsonConstructorWithEmptyArrayTest() {
		JsonObject json = Json.createObjectBuilder()
				.add("thirdParties", Json.createArrayBuilder())
				.add("details", Json.createArrayBuilder())
				.build();
		pcpCoreDTO = new PcpCoreDTO(json);
		assertEquals(0, pcpCoreDTO.getDetails().size(),"No details should be inserted.");
		 assertEquals(0,pcpCoreDTO.getThirdParties().size(),"No third parties should be inserted.");

	}

	@Test
	@DisplayName("Fluent API : Setters should return this instance")
	void testFluentSetters() {
		List<Tiers> tiers = new ArrayList<>();
		for (JsonObject tier : DATA_JSON.getJsonArray("thirdParties").getValuesAs(JsonObject.class)) {
			tiers.add(new Tiers(tier));
		}
		List<AnalyticDetail> details = new ArrayList<>();
		for (JsonObject detail : DATA_JSON.getJsonArray("details").getValuesAs(JsonObject.class)) {
			details.add(new AnalyticDetail(detail));
		}

		pcpCoreDTO = new PcpCoreDTO();
		PcpCoreDTO result = pcpCoreDTO
				                    .setDetails(details)
				                    .setThirdParties(tiers);

		assertAll("Test Fluent API state validation",
				() -> assertSame(pcpCoreDTO,result,"setters must return the same instance"),
				() -> assertEquals(3,pcpCoreDTO.getThirdParties().size(),"3 third party should be insert")
				,() -> assertEquals(3,pcpCoreDTO.getDetails().size(),"3 details should be insert")
				);
	}


}