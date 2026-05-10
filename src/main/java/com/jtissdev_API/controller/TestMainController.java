package com.jtissdev_API.controller;

import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
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
public class TestMainController implements MainController {

	private final PcgDataLoader pcgLoader;
	private final TiersDataLoader tiersLoader;
	private final DetailsDataLoader detailsLoader;
	private final OperationConsolView diagView = new OperationConsolView();

	public TestMainController(PcgDataLoader pcgLoader, TiersDataLoader tiersLoader, DetailsDataLoader detailsLoader) {
		this.pcgLoader = pcgLoader;
		this.tiersLoader = tiersLoader;
		this.detailsLoader = detailsLoader;
	}

	@Override
	public void run() {
		System.out.println("=== DÉMARRAGE DU TEST DE VÉRITÉ (AUTOMATIQUE) ===\n");

		// --- CHARGEMENT DU PCG ---
		System.out.println("> Chargement du Bloc PCG...");
		PcgCoreDTO pcgCore = pcgLoader.loadFromJson("data/PCG.json");

		// --- CHARGEMENT DU PCP ---
		System.out.println("> Chargement du Bloc PCP...");
		PcpCoreDTO pcpCore = new PcpCoreDTO();
		pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
		pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));

		// --- AFFICHAGE DE DIAGNOSTIC ---
		System.out.println("\n--- CONTENU DU BLOC PCG ---");
		System.out.println("PCG : " + pcgCore.toString());

		System.out.println("\n--- CONTENU DU BLOC PCP ---");
		System.out.println("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
		System.out.println("[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());

		System.out.println("\n=== FIN DU TEST DE VÉRITÉ : TOUT EST OK ===");
	}
}