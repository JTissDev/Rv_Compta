package com.jtissdev_API.controller;

import com.jtissdev_API.features.core.dto.SelectionContext;
import com.jtissdev_API.view.ViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TestMainController - Simulateur d'UI pour tests automatisés.
 * * @author J.Tiss <jtissdev@gmail.com>
 * @version 1.2.0
 * @since 0.6
 */
@Component
@Profile("test")
public class TestMainController extends AbstractConsoleController implements MainController {

	private static final Logger logger = LoggerFactory.getLogger(TestMainController.class);

	/** File d'attente simulant les saisies utilisateur */
	private final Queue<String> inputQueue = new LinkedList<>();

	public TestMainController() {
		super();
	}

	/**
	 * Point d'entrée du mode Test.
	 */
	@Override
	public void run() {
		mainConsoleView.displayHeader();
		logger.info(ViewUtil.CYAN + "=== DÉMARRAGE DES TESTS (AUTOMATIQUE) === \n" + ViewUtil.RESET);

        /* =====================
           Test Affichage basiques
           ===================== */
		// this.testDisplays();

        /* =====================
           Test Scenarios
           ===================== */
		testScenarios();

		logger.info(ViewUtil.CYAN + "\n=== FIN DES TESTS : TOUT EST OK ===" + ViewUtil.RESET);
	}

	/**
	 * Surcharge de la lecture console pour piocher dans la file d'attente.
	 */
	@Override
	protected String readLine() {
		if (inputQueue.isEmpty()) {
			// Sécurité pour éviter les boucles infinies si un scénario est mal fermé
			return "0";
		}
		String cmd = inputQueue.poll();
		logger.info("[AUTO-INPUT] Saisie : {}", cmd);
		return cmd;
	}

	// =========================================================
	// == DÉFINITION DES SCÉNARIOS                            ==
	// =========================================================

	private void testScenarios() {
		logger.info("> Lancement de la séquence de scénarios...");

		// --- Liste des scénarios à jouer ---
		scenario_ConsultationJournal();
		// scenario_InsertionDansJournal();
		// scenario_QuitterAppDepuisSousMenu();

		logger.info("--- TOUS LES SCÉNARIOS TERMINÉS ---");
	}

	/**
	 * Test : Accès au journal et retour au menu principal.
	 */
	private void scenario_ConsultationJournal() {
		logger.info("SCÉNARIO : Entrée dans le journal et retour");

		inputQueue.addAll(List.of(
				"2", // Menu Principal -> Journal
				"B", // Menu Journal -> Back
				"0"  // Menu Principal -> Quit
		));

		runMainLoop();
	}

	/**
	 * Test : Commande d'insertion spécifique.
	 */
	private void scenario_InsertionDansJournal() {
		logger.info("SCÉNARIO : Insertion d'une ligne en position 3");

		inputQueue.addAll(List.of(
				"2",      // Entrée journal
				"INS 3",  // Commande d'insertion (simule parts[0] = INS, parts[1] = 3)
				"B",      // Retour
				"0"       // Quitter
		));

		runMainLoop();
	}

	/**
	 * Déclenche la boucle réelle de l'application.
	 * Consomme l'inputQueue jusqu'à ce que mainMenuChoice renvoie true (fermeture).
	 */
	private void runMainLoop() {
		boolean quit = false;
		while (!quit) {
			String choice = readLine();
			quit = mainMenuChoice(choice);
		}
	}

	// =========================================================
	// == TEST D'AFFICHAGE BASIQUES                           ==
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
		// On ne met pas de waitForUser() ici pour garder l'automation fluide
	}

	private void testDisplayPartialPcg() {
		logger.info("> Test Affichage du Bloc PCG correspondant à un AccountingType ...");
		SelectionContext context = new SelectionContext(pcgCore.getAccountingClasses().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);

		logger.info("> Test Affichage du Bloc PCG correspondant à un SubAccountingType ...");
		context.setSubType(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);

		logger.info("> Test Affichage du Bloc PCG correspondant à un AccountingTypeDetails ...");
		context.setDetail(pcgCore.getAccountingClasses().get(1).getSubTypes().get(1).getDetailsList().get(1));
		accountingTypeView.displayCascadeAccountingType(context.getType(), context);
	}

	private void testDisplayPCP() {
		logger.info("> Test Affichage du Bloc PCP...");
		logger.debug("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
		logger.debug("[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());
	}
}