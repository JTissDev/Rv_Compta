package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import com.jtissdev_API.features.core.dto.referential.OperationStatusTest;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
public class PcpCoreDTOTest {

	/**
	 * Logger instance used for logging messages in the context of `ReferentialCoreDTOTest` tests.
	 * The logger is statically initialized and associated with the `ReferentialCoreDTOTest` class.
	 * Typically used to log test execution details, debugging information, or errors during the test lifecycle.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PcpCoreDTOTest.class);

}