package com.jtissdev_API;

import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.worker.CommandLineWorker;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Main Application entry point.
 * Performs the "Test de Vérité" by loading PCG and PCP data.
 *
 * @author J.Tiss
 * @since 0.1
 * @version 1.2
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Executes the "Test de Vérité" process for loading and verifying PCG and PCP data.
     * This method loads the General Chart of Accounts (PCG) and Third Parties (PCP) data from JSON files,
     * displays the loaded data in the console, and performs a basic verification.
     *
     * @param pcgLoader The loader responsible for reading the General Chart of Accounts (PCG) from the JSON file.
     * @param tiersLoader The loader responsible for reading Third Parties (Tiers) data from the JSON file.
     * @param detailsLoader The loader responsible for reading Details data from the JSON file.
     * @return A {@code CommandLineRunner} implementation that performs the test when the application runs.
     *
     * @since 0.1
     */
    @Bean
    @Profile( "test")
    public CommandLineRunner testDeVerite(
            PcgDataLoader pcgLoader,
            TiersDataLoader tiersLoader,
            DetailsDataLoader detailsLoader) {

        return args -> {
            System.out.println("=== DÉMARRAGE DU TEST DE VÉRITÉ ===\n");

            // --- CHARGEMENT DU PCG ---
            System.out.println("> Chargement du Bloc PCG...");
            PcgCoreDTO pcgCore = pcgLoader.loadFromJson("data/PCG.json");

            // --- CHARGEMENT DU PCP ---
            System.out.println("> Chargement du Bloc PCP...");
            PcpCoreDTO pcpCore = new PcpCoreDTO();
            pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
            pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));

            // --- AFFICHAGE DU PCG ---
            System.out.println("\n--- CONTENU DU BLOC PCG ---");
            System.out.println("PCG : " + pcgCore.toString());

            // --- AFFICHAGE DU PCP ---
            System.out.println("\n--- CONTENU DU BLOC PCP ---");

            System.out.println("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
            pcpCore.getThirdParties().stream()
                    .limit(5) // On en affiche 5 pour vérifier
                    .forEach(t -> System.out.println("  - " + t.toString()));

            System.out.println("\n[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());
            pcpCore.getDetails().stream()
                    .limit(5)
                    .forEach(d -> System.out.println("  - " + d.toString()));

            System.out.println("\n=== FIN DU TEST DE VÉRITÉ : TOUT EST CHARGÉ EN MÉMOIRE ===");
        };
    }

    /**
     * Creates and returns a CommandLineRunner instance that executes in an operational mode.
     * This runner initializes the application by displaying an operational mode banner
     * and starting the provided worker.
     *
     * @param worker The CommandLineWorker instance responsible for executing the operational tasks.
     * @return A CommandLineRunner which sets the application in operational mode and starts the worker.
     *
     * @since 0.4
     */
    @Bean
    //@Profile({"dev", "prod"})
    public CommandLineRunner operationalRunner(CommandLineWorker worker) {
        return args -> {
            System.out.println("=== MODE OPÉRATIONNEL : ENGINE v0.4 ===");
            worker.start();
        };
    }
}