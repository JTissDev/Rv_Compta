package com.jtissdev.controller;

import com.jtissdev.engine.loader.DetailsDataLoader;
import com.jtissdev.engine.loader.JournalLoader;
import com.jtissdev.features.pcg.mapper.PcgDataMapper;
import com.jtissdev.engine.loader.TiersDataLoader;
import com.jtissdev.features.pcg.view.AccountingTypeView;
import com.jtissdev.features.compta.dto.JournalDTO;
import com.jtissdev.features.compta.view.JournalConsoleView;
import com.jtissdev.features.compta.view.OperationConsolView;
import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import com.jtissdev.features.pcp.dto.PcpCoreDTO;
import com.jtissdev.view.MainConsoleView;
import com.jtissdev.view.ViewUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

/**
 * Represents a AbstractConsoleController DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */

public abstract class AbstractConsoleController {

	protected final Scanner scanner = new Scanner(System.in);
	protected final MainConsoleView mainConsoleView = new MainConsoleView();
	protected final JournalConsoleView journalView = new JournalConsoleView();
	protected final OperationConsolView operationView = new OperationConsolView();
	protected final AccountingTypeView accountingTypeView = new AccountingTypeView();
	protected final PcgDataMapper pcgLoader = new PcgDataMapper();
	protected final TiersDataLoader tiersLoader = new TiersDataLoader();
	protected final DetailsDataLoader detailsLoader = new DetailsDataLoader();
	protected final JournalLoader journalLoader = new JournalLoader(); // TODO À remplacer par JournalService plus tard


	protected final String dataPath;
	protected JournalDTO currentJournal;
	protected PcgCoreDTO pcgCore;
	protected PcpCoreDTO pcpCore;


	public AbstractConsoleController( String dataPath) {

		this.dataPath = dataPath;
		this.pcgCore = pcgLoader.loadPcg("data/PCG.json");
		pcpCore = new PcpCoreDTO();
		pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
		pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));
	}

	// Remplace tes scanner.nextLine() par un appel à cette méthode partout
	protected String readLine() {
		return scanner.nextLine();
	}

	protected boolean mainMenuChoice(String choice) {
		switch (choice) {
			case "1" -> {
				ViewUtil.displayNotImplemented();
				return false;
			}
			case "2" -> {
				return handleJournalMenu();
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

	protected boolean handleJournalMenu() {
		ViewUtil.displayNotImplemented();
		return false;
	}

}
