package com.jtissdev.features.pcg.service;

import com.jtissdev.features.pcg.dto.AccountingType;
import com.jtissdev.features.pcg.dto.AccountingTypeDetails;
import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import com.jtissdev.features.pcg.dto.SubAccountingType;
import com.jtissdev.features.pcg.repository.PcgRepository;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Suite de tests unitaires pour {@link PcgServiceImpl}.
 * Utilise Mockito pour simuler le comportement du repository et isoler la logique métier.
 */
@ExtendWith({MockitoExtension.class, TestResultLogger.class})
@DisplayName("PcgServiceImpl Test Suite")
@TestGroup("PCG - SERVICE")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PcgServiceImplTest {

	@Mock
	private PcgRepository pcgRepository;

	@InjectMocks
	private PcgServiceImpl pcgService;

	private PcgCoreDTO mockPcgCore;

	@BeforeEach
	void setUp() {
		// Création d'un mini-arbre comptable en mémoire : 1 (Capitaux) -> 10 (Capital) -> 101 (Capital Social)
		AccountingTypeDetails detail = new AccountingTypeDetails()
				                               .setId(3)
				                               .setName("Capital Social")
				                               .setAccountCode(1)
				                               .setParentAccountingCode("10"); // Full code = 101

		SubAccountingType subType = new SubAccountingType()
				                            .setId(2)
				                            .setName("Capital")
				                            .setAccountCode(0)
				                            .setParentAccountingCode("1") // Full code = 10
				                            .addDetails(detail);

		AccountingType root = new AccountingType()
				                      .setId(1)
				                      .setName("Capitaux")
				                      .setAccountCode(1) // Full code = 1
				                      .addSubType(subType);

		mockPcgCore = new PcgCoreDTO();
		mockPcgCore.addAccountingClass(root);
	}

    /* ====================================================================\
    ===                       VUE GLOBALE & NAVIGATION                   ===
    ==================================================================== */

	@Test
	@Order(1)
	@DisplayName("✅ getFullAccountingPlan: Doit appeler le repository et retourner le DTO")
	void testGetFullAccountingPlan() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		PcgCoreDTO result = pcgService.getFullAccountingPlan();

		assertNotNull(result, "Le DTO ne doit pas être nul");
		assertEquals(1, result.getAccountingClasses().size(), "L'arbre doit contenir 1 classe racine");
		verify(pcgRepository, times(1)).load();
	}

	@Test
	@Order(2)
	@DisplayName("✅ getAccountTreePath: Doit générer le chemin textuel correct selon le niveau")
	void testGetAccountTreePath() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		assertAll("Vérification des chemins textuels",
				() -> assertTrue(pcgService.getAccountTreePath(1).contains("Capitaux"), "Chemin Niveau 1"),
				() -> assertTrue(pcgService.getAccountTreePath(10).contains("Capital"), "Chemin Niveau 2"),
				() -> assertTrue(pcgService.getAccountTreePath(101).contains("Capital Social"), "Chemin Niveau 3"),
				() -> assertTrue(pcgService.getAccountTreePath(999).contains("introuvable"), "Chemin inexistant")
		);
	}

    /* ====================================================================\
    ===                   LECTURE PAR NIVEAU (GETTERS)                   ===
    ==================================================================== */

	@Test
	@Order(3)
	@DisplayName("✅ getRootClasses: Doit retourner la liste des classes de niveau 1")
	void testGetRootClasses() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		List<AccountingType> roots = pcgService.getRootClasses();

		assertEquals(1, roots.size());
		assertEquals("1", roots.get(0).getFullCode());
	}

	@Test
	@Order(4)
	@DisplayName("✅ getSubClasses: Doit retourner les sous-classes d'un parent donné")
	void testGetSubClasses() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		List<SubAccountingType> subs = pcgService.getSubClasses(1);
		List<SubAccountingType> emptySubs = pcgService.getSubClasses(99);

		assertAll("Vérification des sous-classes",
				() -> assertEquals(1, subs.size(), "Doit trouver 1 sous-classe pour le parent 1"),
				() -> assertEquals("10", subs.get(0).getFullCode()),
				() -> assertTrue(emptySubs.isEmpty(), "Doit retourner une liste vide pour un parent inexistant")
		);
	}

	@Test
	@Order(5)
	@DisplayName("✅ getAccountDetails: Doit retourner les détails d'une sous-classe donnée")
	void testGetAccountDetails() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		List<AccountingTypeDetails> details = pcgService.getAccountDetails(10);
		List<AccountingTypeDetails> emptyDetails = pcgService.getAccountDetails(99);

		assertAll("Vérification des détails comptables",
				() -> assertEquals(1, details.size(), "Doit trouver 1 détail pour le parent 10"),
				() -> assertEquals("101", details.get(0).getFullCode()),
				() -> assertTrue(emptyDetails.isEmpty(), "Doit retourner une liste vide pour un parent inexistant")
		);
	}

    /* ====================================================================\
    ===                       RECHERCHES CIBLÉES                         ===
    ==================================================================== */

	@Test
	@Order(6)
	@DisplayName("✅ findAccountByCode: Doit trouver n'importe quel élément de l'arbre par son code complet")
	void testFindAccountByCode() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		assertAll("Vérification de la recherche par code complet",
				() -> assertTrue(pcgService.findAccountByCode(1).get() instanceof AccountingType),
				() -> assertTrue(pcgService.findAccountByCode(10).get() instanceof SubAccountingType),
				() -> assertTrue(pcgService.findAccountByCode(101).get() instanceof AccountingTypeDetails),
				() -> assertFalse(pcgService.findAccountByCode(999).isPresent())
		);
	}

	@Test
	@Order(7)
	@DisplayName("✅ findAccountsByName: Doit trouver des éléments en filtrant sur le nom (insensible à la casse)")
	void testFindAccountsByName() {
		when(pcgRepository.load()).thenReturn(Optional.of(mockPcgCore));

		List<Object> searchCapitaux = pcgService.findAccountsByName("capitaux");
		List<Object> searchCapital = pcgService.findAccountsByName("capita"); // Doit matcher "Capitaux", "Capital" et "Capital Social"
		List<Object> searchEmpty = pcgService.findAccountsByName("Licorne");

		assertAll("Vérification de la recherche par nom",
				() -> assertEquals(1, searchCapitaux.size(), "Match exact sur 1 élément"),
				() -> assertEquals(3, searchCapital.size(), "Match partiel sur 3 éléments"),
				() -> assertTrue(searchEmpty.isEmpty(), "Aucun match")
		);
	}

    /* ====================================================================\
    ===            MUTATIONS (PROTECTION PAR EXCEPTION)                  ===
    ==================================================================== */

	@Test
	@Order(8)
	@DisplayName("❌ Mutations: Doivent lever une UnsupportedOperationException")
	void testMutationsThrowExceptions() {
		assertAll("Vérification des verrous de mutation",
				() -> assertThrows(UnsupportedOperationException.class, () -> pcgService.addAccount(1, new Object())),
				() -> assertThrows(UnsupportedOperationException.class, () -> pcgService.updateAccount(1, new Object())),
				() -> assertThrows(UnsupportedOperationException.class, () -> pcgService.removeAccount(1))
		);
	}
}