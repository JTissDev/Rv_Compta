package com.jtissdev_API;

import com.jtissdev_API.features.PCP.dto.AnalyticDetailTest;
import com.jtissdev_API.features.PCP.dto.ContactsTest;
import com.jtissdev_API.features.PCP.dto.TiersTest;
import com.jtissdev_API.features.core.dto.PcpCoreDTOTest;
import com.jtissdev_API.features.core.dto.ReferentialCoreDTOTest;
import com.jtissdev_API.features.core.dto.referential.OperationStatusTest;
import com.jtissdev_API.features.core.dto.referential.PaymentMethodTest;
import com.jtissdev_API.utils.TestResultLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@DisplayName("🚀 JTissDev API : Rv-Compta - Full Test Suite")
@ExtendWith(TestResultLogger.class)
@SelectClasses({
		// 1. On teste d'abord les petites briques (les "atomes")
		OperationStatusTest.class,
		PaymentMethodTest.class,

		ReferentialCoreDTOTest.class,

		AnalyticDetailTest.class,
		ContactsTest.class,
		TiersTest.class,

		// 2. On teste ensuite les objets qui contiennent ces briques (les "molécules")
		PcpCoreDTOTest.class

		// 3. Prochaine étape : tes Loaders viendront ici
		// JournalLoaderTest.class
})
public class CoreTestSuiteTest {
	// Cette classe reste vide.
	// Elle ne sert que de conteneur de configuration.
}
