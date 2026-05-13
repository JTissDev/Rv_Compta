package com.jtissdev_API.controller;

import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.features.PCG.view.AccountingTypeView;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.view.JournalConsoleView;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.view.MainConsoleView;
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

	@Value("${app.data.path}")
	protected String dataPath;

	protected JournalDTO currentJournal;
	protected final Scanner scanner = new Scanner(System.in);

	protected final MainConsoleView mainConsoleView = new MainConsoleView();
	protected final JournalConsoleView journalView = new JournalConsoleView();
	protected final OperationConsolView operationView = new OperationConsolView();

	protected final AccountingTypeView accountingTypeView = new AccountingTypeView();
	protected final PcgDataLoader pcgLoader;
	protected PcgCoreDTO pcgCore;

	public AbstractConsoleController() {
		this.pcgLoader = new PcgDataLoader();
		this.pcgCore = pcgLoader.loadPcg("data/PCG.json");
	}

}
