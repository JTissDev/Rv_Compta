package com.jtissdev_API;

import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main Application entry point.
 * Performs the "Test de Vérité" by loading PCG and PCP data.
 *
 * @author J.Tiss
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
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
}