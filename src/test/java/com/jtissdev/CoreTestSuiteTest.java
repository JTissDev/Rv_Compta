package com.jtissdev;

import com.jtissdev.features.compta.dto.JournalDTOTest;
import com.jtissdev.features.compta.dto.MovementDTOTest;
import com.jtissdev.features.compta.dto.OperationDTOTest;
import com.jtissdev.features.pcg.dto.AccountingTypeDetailsTest;
import com.jtissdev.features.pcg.dto.AccountingTypeTest;
import com.jtissdev.features.pcg.dto.PcgCoreDTOTest;
import com.jtissdev.features.pcg.dto.SubAccountingTypeTest;
import com.jtissdev.features.pcg.mapper.PcgDataMapperTest;
import com.jtissdev.features.pcg.repository.JsonFilePcgRepositoryTest;
import com.jtissdev.features.pcg.service.PcgServiceImplTest;
import com.jtissdev.features.pcp.dto.AnalyticDetailTest;
import com.jtissdev.features.pcp.dto.ContactsTest;
import com.jtissdev.features.pcp.dto.PcpCoreDTOTest;
import com.jtissdev.features.pcp.dto.TiersTest;
import com.jtissdev.features.referential.dto.OperationStatusTest;
import com.jtissdev.features.referential.dto.PaymentMethodTest;
import com.jtissdev.features.referential.dto.ReferentialCoreDTOTest;
import com.jtissdev.features.referential.repository.JsonFileReferentialCoreRepositoryTest;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

//import com.jtissdev.features.compta.repository.JsonFileJournalRepositoryTest;
//import com.jtissdev.features.pcp.repository.JsonFilePcpRepositoryTest;
//import com.jtissdev.features.referential.repository.JsonFileReferentialCoreRepositoryTest;

@Suite
@DisplayName("🚀 JTissDev API : Rv-Compta - Full Test Suite")
@ExtendWith(TestResultLogger.class)
@SelectClasses({
	    /* ====================================================================
	    ===                             REFERENTIAL                         ===
	    ==================================================================== */
		// Referential DTO
		OperationStatusTest.class, PaymentMethodTest.class,
		// Referential Data holder
		ReferentialCoreDTOTest.class,

		// Referential Repository
		JsonFileReferentialCoreRepositoryTest.class,

		/* ====================================================================
	    ===                  pcp - PLAN COMPTABLE PERSONALISE               ===
	    ==================================================================== */

		// pcp DTO
		AnalyticDetailTest.class, ContactsTest.class, TiersTest.class,
		// pcp Data holder
		PcpCoreDTOTest.class,
		// pcp Repository
		//JsonFilePcpRepositoryTest.class,


		/* ====================================================================
	    ===                  pcg - PLAN COMPTABLE GÉNÉRAL                   ===
	    ==================================================================== */
		// pcg DTO
		AccountingTypeDetailsTest.class, SubAccountingTypeTest.class, AccountingTypeTest.class,
		// pcg Data holder
		PcgCoreDTOTest.class,

		// Mapper
		PcgDataMapperTest.class,

		// pcg Repository
		JsonFilePcgRepositoryTest.class,

		// Pcg Service
		PcgServiceImplTest.class,


		/* ====================================================================
	    ===                  COMPTA - JOURNAL & MOUVEMENTS                  ===
	    ==================================================================== */

		// Journal DTO
		MovementDTOTest.class, OperationDTOTest.class, JournalDTOTest.class,

		// Journal Repository
		//JsonFileJournalRepositoryTest.class,


		/* ====================================================================
	    ===                  TESTS GÉNÉRAUX & UTILITAIRES                  ===
	    ==================================================================== */
})
public class CoreTestSuiteTest {
	// Cette classe reste vide.
	// Elle ne sert que de conteneur de configuration.
}
