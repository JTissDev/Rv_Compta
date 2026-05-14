package com.jtissdev_API.controller;

import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.features.core.dto.SelectionContext;
import com.jtissdev_API.view.ViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a TestMainController class for testing purposes.
 *
 * @author jtiss
 * @version 1.2.0
 * @since 0.6
 */
@Component
@Profile("test")
public class TestMainController extends AbstractConsoleController implements MainController {

	private static final Logger logger = LoggerFactory.getLogger(TestMainController.class);

	private final Queue<Object> inputQueue = new LinkedList<>();



	private PcpCoreDTO pcpCore;

	public TestMainController() {
		super();


	}


	@Override
	public void run() {
		mainConsoleView.displayHeader();
		logger.info(ViewUtil.CYAN + "=== DÉMARRAGE DES TESTS (AUTOMATIQUE) === \n" + ViewUtil.RESET);

		/* =====================
		 Test Affichage basiques
		===================== */
		//this.testDisplays();

		/* =====================
		Test Scenarios
		 ===================== */




		logger.info(ViewUtil.CYAN + "\n=== FIN DES TESTS : TOUT EST OK ===" + ViewUtil.RESET);
	}

	// =========================================================
	// == DÉFINITION DES SCÉNARIOS                            ==
	// =========================================================

	private void testScenarios() {
		logger.info("> Test de scénario complet...");
		// IL SUFFIT DE COMMENTER/DÉCOMMENTER ICI
		//scenario_ConsultationPCG();
		// scenario_InsertionOperationComplexe();
		// scenario_ErreurSaisie();
		// TODO : Implémenter un scénario de test complet qui couvre les fonctionnalités clés de l'application
		ViewUtil.displayNotImplemented();
		logger.info("--- TOUT LES SCÉNARIOS TERMINÉS ---");
	}

	// =========================================================
	// == Test D'affichage basiques                           ==
	// =========================================================

	private void testDisplays() {
		logger.info("> Test Affichage basiques...");

		logger.info("> Affichage du Bloc PCG...");
		this.testDisplayPCG();
		this.testDisplayPartialPcg();


		logger.info("> Chargement du Bloc PCP...");
		this.testDisplayPCP();
	}

	private void testDisplayPCG() {
		logger.info("> Test Affichage du Bloc PCG...");

		pcgCore.getAccountingClasses().forEach(accountingTypeView::displayCascadeAccountingType);
		ViewUtil.waitForUser();
	}

	private void testDisplayPartialPcg() {
		logger.info("> Test Affichage du Bloc PCG correspondant a un AccountingType ...");
		SelectionContext context = new SelectionContext(pcgCore.getAccountingClasses().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
		ViewUtil.waitForUser();
		logger.info("> Test Affichage du Bloc PCG correspondant a un SubAccountingType ...");
		context.setSubType(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
		ViewUtil.waitForUser();
		logger.info("> Test Affichage du Bloc PCG correspondant a un AccountingTypeDetails ...");
		context.setDetail(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1).getDetailsList().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
		ViewUtil.waitForUser();

	}

	private void testDisplayPCP() {
		logger.info("> Test Affichage du Bloc PCP...");
		OperationConsolView operationConsolView = new OperationConsolView();
		ViewUtil.displayNotImplemented();
		logger.debug("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
		logger.debug("[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());
	}
}