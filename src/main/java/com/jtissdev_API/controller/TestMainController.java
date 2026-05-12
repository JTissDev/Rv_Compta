package com.jtissdev_API.controller;

import com.jtissdev_API.App;
import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.features.PCG.view.AccountingTypeView;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.features.core.dto.SelectionContext;
import com.jtissdev_API.view.ViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Represents a TestMainController class for testing purposes.
 *
 * @version 1.0.0
 * @since 0.6
 * @author jtiss
 */
@Component
@Profile("test")
public class TestMainController extends AbstractConsoleController implements MainController {

	private static final Logger logger = LoggerFactory.getLogger(TestMainController.class);

	private final PcgDataLoader pcgLoader;
	private final TiersDataLoader tiersLoader;
	private final DetailsDataLoader detailsLoader;
	private PcgCoreDTO pcgCore;
	private PcpCoreDTO pcpCore;

	public TestMainController() {
		super();
		this.pcgLoader = new PcgDataLoader();
		this.tiersLoader = new TiersDataLoader();
		this.detailsLoader = new DetailsDataLoader();
	}

	public TestMainController(PcgDataLoader pcgLoader, TiersDataLoader tiersLoader, DetailsDataLoader detailsLoader) {
		super();
		this.pcgLoader = pcgLoader;
		this.tiersLoader = tiersLoader;
		this.detailsLoader = detailsLoader;
	}

	@Override
	public void run() {
		logger.info(ViewUtil.CYAN + "=== DÉMARRAGE DU TEST DE VÉRITÉ (AUTOMATIQUE) === \n" + ViewUtil.RESET);

		// --- CHARGEMENT DU PCG ---
		logger.info("> Chargement du Bloc PCG...");
		pcgCore = pcgLoader.loadFromJson("data/PCG.json");
		this.testDisplayPCG();
		this.testDisplayPartialPcg();

		// --- CHARGEMENT DU PCP ---
		logger.info("> Chargement du Bloc PCP...");
		pcpCore = new PcpCoreDTO();
		pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
		pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));
		this.testDisplayPCP();
		// --- AFFICHAGE DE DIAGNOSTIC ---

		logger.info(ViewUtil.CYAN + "\n=== FIN DU TEST DE VÉRITÉ : TOUT EST OK ===" + ViewUtil.RESET);
	}

	private void testDisplayPCG(){
		logger.info("> Test Affichage du Bloc PCG...");

		pcgCore.getAccountingClasses().forEach(accountingTypeView::displayCascadeAccountingType);
	}

	private void testDisplayPartialPcg(){
		logger.info("> Test Affichage du Bloc PCG correspondant a un AccountingType ...");
		SelectionContext context = new SelectionContext(pcgCore.getAccountingClasses().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
		logger.info("> Test Affichage du Bloc PCG correspondant a un SubAccountingType ...");
		context.setSubType(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
		logger.info("> Test Affichage du Bloc PCG correspondant a un AccountingTypeDetails ...");
		context.setDetail(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1).getDetailsList().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);

	}

	private void testDisplayPCP(){
		logger.info("> Test Affichage du Bloc PCP...");
		OperationConsolView operationConsolView = new OperationConsolView();
		ViewUtil.displayNotImplemented();
		logger.debug("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
		logger.debug("[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());
	}
}