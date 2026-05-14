package com.jtissdev_API.controller;

import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.loader.JournalLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.features.PCG.view.AccountingTypeView;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.view.JournalConsoleView;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.view.MainConsoleView;
import com.jtissdev_API.view.ViewUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.Scanner;

/**
 * Represents a AbstractConsoleController DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class AbstractConsoleController {

	protected final Scanner scanner = new Scanner(System.in);
	protected final MainConsoleView mainConsoleView = new MainConsoleView();
	protected final JournalConsoleView journalView = new JournalConsoleView();
	protected final OperationConsolView operationView = new OperationConsolView();
	protected final AccountingTypeView accountingTypeView = new AccountingTypeView();
	protected final PcgDataLoader pcgLoader = new PcgDataLoader();
	protected final TiersDataLoader tiersLoader = new TiersDataLoader();
	protected final DetailsDataLoader detailsLoader = new DetailsDataLoader();
	protected final JournalLoader journalLoader = new JournalLoader(); // TODO À remplacer par JournalService plus tard

	@Value("${app.data.path}")
	protected String dataPath;
	protected JournalDTO currentJournal;
	protected PcgCoreDTO pcgCore;
	protected PcpCoreDTO pcpCore;


	public AbstractConsoleController() {

		this.pcgCore = pcgLoader.loadPcg("data/PCG.json");
		pcpCore = new PcpCoreDTO();
		pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
		pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));
	}

	protected boolean mainMenuChoice(String choice) {
		switch (choice) {
			case "1" -> {
				ViewUtil.displayNotImplemented();
				return false;
			}
			case "2" -> {
				ViewUtil.displayNotImplemented();
				return false;
			}
			case "0" -> {
				ViewUtil.displayMessage("> Fermeture du programme.");
				return true;
			}
			default -> {
				System.out.println(ViewUtil.RED + "Choix invalide." + ViewUtil.RESET);
				return false;
			}
		}

	}

}
