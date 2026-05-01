package com.jtissdev_API;

import com.jtissdev_API.features.PCG.dto.AccountingTypeDetailsTest;
import com.jtissdev_API.features.PCG.dto.AccountingTypeTest;
import com.jtissdev_API.features.PCG.dto.SubAccountingTypeTest;
import com.jtissdev_API.features.PCP.dto.AnalyticDetailTest;
import com.jtissdev_API.features.PCP.dto.ContactsTest;
import com.jtissdev_API.features.PCP.dto.TiersTest;
import com.jtissdev_API.features.compta.dto.MovementDTOTest;
import com.jtissdev_API.features.compta.dto.OperationDTOTest;
import com.jtissdev_API.features.core.dto.PcgCoreDTOTest;
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
		// Referential DTO
		OperationStatusTest.class,
		PaymentMethodTest.class,
		// Referential Data holder
		ReferentialCoreDTOTest.class,

		// PCP DTO
		AnalyticDetailTest.class,
		ContactsTest.class,
		TiersTest.class,
		// PCP Data holder
		PcpCoreDTOTest.class,

		// PCG DTO
		AccountingTypeDetailsTest.class,
		SubAccountingTypeTest.class,
		AccountingTypeTest.class,
		// PCG Data holder
		PcgCoreDTOTest.class,

		// Journal DTO
		MovementDTOTest.class,
		OperationDTOTest.class


		// 3. Prochaine étape : tes Loaders viendront ici
		// JournalLoaderTest.class
})
public class CoreTestSuiteTest {
	// Cette classe reste vide.
	// Elle ne sert que de conteneur de configuration.
}
