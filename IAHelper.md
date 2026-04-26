# Pour mon projet Compta

## Regles de fonctionnement.
- Tu ne propose rien si je ne te l'ai pas demander.
- tu ne considere comme valid que ce que je t'ai explicitement valider.
- toute classe doit etre commentée avec javadoc detaillée 

### 📝 Règles de Documentation Javadoc (RV_Compta Standard)
 1. Bloc d'Entête de Classe
Chaque classe commence par une description conceptuelle suivie de détails techniques sur sa structure (ex: hiérarchie, comportement des codes).

Tags obligatoires (dans cet ordre) :

``` 
@author jtiss (ou J.Tiss selon la version, à harmoniser).

@since 0.1 (indique la version de création).

@version 1.0.0 (indique la révision actuelle).
```

2. Séparation par Sections (Visual Markers)
   Utilisation de bannières de commentaires pour segmenter la classe de manière lisible :

```Java
// =========================================================
// == FIELDS                                              ==
// =========================================================
```
Sections types : FIELDS, CONSTRUCTORS, GETTERS / SETTERS, METHODS.

3. Documentation des Champs (Fields)
   Chaque champ privé doit avoir sa Javadoc :

Description concise du rôle de la donnée.

Tag @since correspondant à l'ajout du champ.

4. Documentation des Méthodes
   Description : Ce que fait la méthode.

Contrat : Utilisation de {@code ...} pour les valeurs techniques ou {@link ...} pour référencer d'autres classes.

Tags :

@param : Nom et description du paramètre.

@return : Description de la valeur de retour (mentionner "this instance for chaining" pour l'API Fluent).

@since : Version d'introduction.

🏗️ Structure de Code & Design
1. API Fluent (Chaînage)
   Tous les setters doivent retourner l'instance de l'objet pour permettre l'écriture fluide :

```Java
public Type_Comptable setName(String name) {
this.name = name;
return this;
}
```

Constructeurs font appel aux setters.
2. Logique de Mapping JSON (Jakarta)
   Utilisation systématique de JsonObjectBuilder et JsonArrayBuilder pour l'export. Les méthodes toJson() doivent être documentées en précisant qu'elles servent à la représentation JSON.

3. Override Standard
   toString() : Doit être surchargé avec une structure claire (StringBuilder), incluant les indentations pour faciliter le débuggage dans les logs.

## Algorithme en cours de travail.

📑 Algorithme : Importation & Ventilation Interactive (v0.4)
Étape 1 : Initialisation du contexte
Démarrage : Le CommandeWorker reçoit l'ordre d'importation.

Chargement des référentiels :

Chargement du Journal existant via JournalLoader (pour ne pas écraser l'existant).

Chargement du PCG/PCP (pour la navigation dans les types).

Chargement du Référentiel Tiers (pour l'assignation).

Lecture Source : Ouverture du fichier Excel via ExcelService.

Étape 2 : Boucle de lecture "Intelligente"
Pour chaque ligne du fichier Excel :

Vérification du statut : Si la colonne "Statut" contient déjà "VENTILE", passer à la suivante.

Extraction Entête :

dateOperation = Colonne Date.

dateComptable = Colonne Date Valeur.

descriptif = Colonne Libellé.

Création de l'objet : Instanciation d'un OperationDTO avec ces valeurs.

Étape 3 : Génération de la Ligne 1 (Automatique)
Cible : Compte Banque (Code 512, Détail .442).

Logique de montant :

Si Débit Excel > 0 alors Ligne1.creditAmount = Débit Excel.

Si Crédit Excel > 0 alors Ligne1.debitAmount = Crédit Excel.

Ajout : L'opération reçoit son premier MovementDTO.

Étape 4 : Boucle de Ventilation (Interface Utilisateur)
Tant que Somme(Débits) != Somme(Crédits) :

Navigation Hiérarchique :

Affichage des Type_Comptable. L'utilisateur choisit.

Affichage des Sub_Type_Comptable associés. L'utilisateur choisit.

Affichage des Detail (PCP). L'utilisateur choisit le code final.

Enrichissement :

Demande : "Complément de libellé ?" (Optionnel).

Demande : "Sélection du Tiers ?" (Recherche par ID ou Nom).

Moyen de Paiement :

Analyse du libellé (Ex: "VIR" -> Virement).

Si Virement : Question "Interne ou Externe ?".

Calcul du Montant :

Le système affiche le reste à équilibrer.

L'utilisateur valide le montant suggéré OU saisit un montant inférieur (ce qui relance la boucle pour une nouvelle ligne).

Étape 5 : Persistance et Sécurisation
Une fois l'opération équilibrée et validée :

Commit Journal : Ajout de l' OperationDTO finale au JournalDTO.

Sauvegarde JSON : Réécriture immédiate du fichier JSON (sécurité en cas de coupure).

Marquage Excel : ExcelService écrit "VENTILE" sur la ligne traitée dans le fichier .xlsx.

## Structure de dossier.

|arbo.sh
|CHANGELOG.md
|data
|-- |Banque DATA 260414 .xlsx
|-- |compta.json
|-- |comptaCleaner.sh
|-- |dataConvertor
|-- | |compta_converter.sh
|-- | |converter.js
|-- | |sources
|-- | | |~$Banque DATA 260414 .xlsx
|-- |Details.json
|-- |PCG.json
|-- |Tiers.json
|docs
|-- |Bdd.md
|IAHelper.md
|pom.xml
|README.md
|src
|-- |main
|-- | |java
|-- | | |com
|-- | | | |jtissdev_API
|-- | | | | |App.java
|-- | | | | |core
|-- | | | | | |config
|-- | | | | | |exception
|-- | | | | | |util
|-- | | | | | | |ExcelReader.java
|-- | | | | |engine
|-- | | | | | |DbUpdater
|-- | | | | | | |DbUpdater.java
|-- | | | | | | |package-info.java
|-- | | | | | |loader
|-- | | | | | | |ComptaDataLoader.java
|-- | | | | | | |DetailsDataLoader.java
|-- | | | | | | |JournalLoader.java
|-- | | | | | | |package-info.java
|-- | | | | | | |PcgDataLoader.java
|-- | | | | | | |ReferentialDataLoader.java
|-- | | | | | | |TiersDataLoader.java
|-- | | | | |features
|-- | | | | | |compta
|-- | | | | | | |controller
|-- | | | | | | | |package-info.java
|-- | | | | | | |dto
|-- | | | | | | | |JournalDTO.java
|-- | | | | | | | |MovementDTO.java
|-- | | | | | | | |OperationDTO.java
|-- | | | | | | | |package-info.java
|-- | | | | | | |entity
|-- | | | | | | | |package-info.java
|-- | | | | | | |repository
|-- | | | | | | | |package-info.java
|-- | | | | | | |service
|-- | | | | | | | |package-info.java
|-- | | | | | |core
|-- | | | | | | |dto
|-- | | | | | | | |package-info.java
|-- | | | | | | | |PcgCoreDTO.java
|-- | | | | | | | |PcpCoreDTO.java
|-- | | | | | | | |referential
|-- | | | | | | | | |OperationStatus.java
|-- | | | | | | | | |package-info.java
|-- | | | | | | | | |PaymentMethod.java
|-- | | | | | | | |ReferentialCoreDTO.java
|-- | | | | | |PCG
|-- | | | | | | |controller
|-- | | | | | | | |package-info.java
|-- | | | | | | |dto
|-- | | | | | | | |package-info.java
|-- | | | | | | | |Sub_Type_Comptable.java
|-- | | | | | | | |Type_Comptable.java
|-- | | | | | | | |Type_Comptable_Details.java
|-- | | | | | | |entity
|-- | | | | | | | |package-info.java
|-- | | | | | | |repository
|-- | | | | | | | |package-info.java
|-- | | | | | | |service
|-- | | | | | | | |package-info.java
|-- | | | | | |PCP
|-- | | | | | | |controller
|-- | | | | | | | |package-info.java
|-- | | | | | | |dto
|-- | | | | | | | |Contacts.java
|-- | | | | | | | |Details_Comptable.java
|-- | | | | | | | |package-info.java
|-- | | | | | | | |Tiers.java
|-- | | | | | | |entity
|-- | | | | | | | |package-info.java
|-- | | | | | | |repository
|-- | | | | | | | |package-info.java
|-- | | | | | | |service
|-- | | | | | | | |package-info.java
|-- | |resources
|-- | | |data
|-- | | | |OperationStatuts.json
|-- | | | |PaymentMethod.json
|-- | | | |PCG.json
|-- |test
|-- | |java
|-- | | |com
|-- | | | |jtissdev_API
|-- | | | | |AppTest.java
|-- | | | | |core
|-- | | | | | |dto
|-- | | | | |engine
|-- | | | | | |DbUpdater
|-- | | | | | |loader
|-- | | | | | | |DetailsDataLoaderTest.java
|-- | | | | | | |JournalLoaderTest.java
|-- | | | | | | |PcgDataLoaderTest.java
|-- | | | | | | |ReferentialDataLoaderTest.java
|-- | | | | | | |TiersDataLoaderTest.java
|-- | | | | |features
|-- | | | | | |compta
|-- | | | | | | |controller
|-- | | | | | | |dto
|-- | | | | | | | |JournalDTOTest.java
|-- | | | | | | | |MovementDTOTest.java
|-- | | | | | | | |OperationDTOTest.java
|-- | | | | | |core
|-- | | | | | | |dto
|-- | | | | | | | |PcgCoreDTOTest.java
|-- | | | | | | | |PcpCoreDTOTest.java
|-- | | | | | | | |referential
|-- | | | | | | | | |OperationStatusTest.java
|-- | | | | | | | | |PaymentMethodTest.java
|-- | | | | | | | |ReferentialCoreDTOTest.java
|-- | | | | | |PCG
|-- | | | | | | |dto
|-- | | | | | | | |Sub_Type_Comptable_Test.java
|-- | | | | | | | |Type_Comptable_Details_Test.java
|-- | | | | | | | |Type_Comptable_Test.java
|-- | | | | | |PCP
|-- | | | | | | |dto
|-- | | | | | | | |Details_ComptableTest.java
|-- | | | | | | | |TiersTest.java
|TODO.md
------------------------------------

## Structure de package

--- MAPPING DES METHODES : RV_COMPTA ---

📦 PACKAGE : com.jtissdev_API.core.util
================================================
📍 Classe : ExcelReader.java
----------------------------------------------
    -   public List<List<String>> readExcel(File file) throws Exception

📦 PACKAGE : com.jtissdev_API.engine.DbUpdater
================================================
📍 Classe : DbUpdater.java
----------------------------------------------
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.engine.loader
================================================
📍 Classe : DetailsDataLoader.java
----------------------------------------------
    -   public List<Details_Comptable> loadDetailsFromJson(String fileName)
📍 Classe : JournalLoader.java
----------------------------------------------
    -   public JournalDTO loadJournal(InputStream is)
    -   private JournalDTO mapToJournalDTO(JsonObject json)
    -   private OperationDTO mapToOperationDTO(JsonObject json)
    -   private MovementDTO mapToMovementDTO(JsonObject json)
    -   private LocalDate parseDate(String dateStr)
📍 Classe : PcgDataLoader.java
----------------------------------------------
    -   public PcgCoreDTO loadFromJson(String fileName)
    -   private PcgCoreDTO mapToDto(JsonObject json)
    -   private PcgCoreDTO mapToDto(JsonArray json)
📍 Classe : ReferentialDataLoader.java
----------------------------------------------
    -   public void loadOperationStatuses(InputStream inputStream, ReferentialCoreDTO targetDTO) throws Exception
    -   public void loadPaymentMethods(InputStream inputStream, ReferentialCoreDTO targetDTO) throws Exception
📍 Classe : TiersDataLoader.java
----------------------------------------------
    -   public List<Tiers> loadTiersFromJson(String fileName)
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.engine.worker
================================================
📍 Classe : CommandLineWorker.java
----------------------------------------------
    -   public CommandLineWorker(PcgDataLoader pcgLoader,
    -   public void start() throws Exception
    -   private void displayLastOperations(JournalDTO journal, int count)
    -   private void processExcelImport(Scanner scanner, JournalDTO journal)
    -   private void processManualEntry(Scanner scanner, JournalDTO journal)
    -   private File selectExcelFile(Scanner scanner)
    -   private void processSingleExcelRow(List<String> row, Scanner scanner)
    -   private double parseAmount(String value)
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.PCG.dto
================================================
📍 Classe : Sub_Type_Comptable.java
----------------------------------------------
    - public Sub_Type_Comptable()
    - public Sub_Type_Comptable(Long id,
    - public Sub_Type_Comptable(Long id,
    - public Long getId()
    - public String getName()
    - public Integer getCodeComptable()
    - public String getDescription()
    - public String getParentCodeComptable()
    - public String getFullCode()
    - public List<Type_Comptable_Details> getDetailsList()
    - public void setId(Long id)
    - public void setName(String name)
    - public void setCodeComptable(Integer accountCode)
    - public void setDescription(String description)
    - public void setParentCodeComptable(String parentAccountingCode)
    - public void setDetailsList(List<Type_Comptable_Details> detailsList)
    - public void addDetails(Type_Comptable_Details details)
    - public JsonObject toJson()
    - public String toString()
📍 Classe : Type_Comptable.java
----------------------------------------------
    -   public Type_Comptable()
    -   public Type_Comptable(Long id,
    -   public Long getId()
    -   public Type_Comptable setId(Long id)
    -   public String getName()
    -   public Type_Comptable setName(String name)
    -   public Integer getCodeComptable()
    -   public Type_Comptable setCodeComptable(Integer accountCode)
    -   public String getDescription()
    -   public Type_Comptable setDescription(String description)
    -   public List<Sub_Type_Comptable> getSubTypes()
    -   public Type_Comptable setSubTypes(List<Sub_Type_Comptable> subTypes)
    -   public Type_Comptable addSubType(Sub_Type_Comptable subType)
    -   public String getFullCodeComptable()
    -   public JsonObject toJson()
    -   public String toString()
📍 Classe : Type_Comptable_Details.java
----------------------------------------------
    - public Type_Comptable_Details()
    - public Type_Comptable_Details(int id,
    - public Type_Comptable_Details(int id,
    - public Type_Comptable_Details(String name,
    - public long getId()
    - public String getName()
    - public Integer getCodeComptable()
    - public String getDescription()
    - public String getParentCodeComptable()
    - public String getFullCode()
    - public void setId(int id)
    - public void setName(String name)
    - public void setCodeComptable(Integer accountCode)
    - public void setDescription(String description)
    - public JsonObject toJson()
    - public String toString()
    - public void setParentCodeComptable(String fullCode)
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.PCP.dto
================================================
📍 Classe : Contacts.java
----------------------------------------------
    - public Contacts()
    - public Contacts(Long id,
    - public Contacts(Long id,
    - public Long getId()
    - public void setId(Long id)
    - public String getName()
    - public void setName(String name)
    - public String getEmail()
    - public void setEmail(String email)
    - public String getPhone()
    - public void setPhone(String phone)
    - public String getMobile()
    - public void setMobile(String mobile)
    - public String getCompany()
    - public void setCompany(String company)
    - public String getRole()
    - public void setRole(String role)
    - public String getAddress()
    - public void setAddress(String address)
    - public String getZipCode()
    - public void setZipCode(String zipCode)
    - public String getCity()
    - public void setCity(String city)
    - public String getCountry()
    - public void setCountry(String country)
    - public String getNotes()
    - public void setNotes(String notes)
    - public JsonObject toJson()
    - public static Contacts fromJson(JsonObject json)
    - public String toString()
📍 Classe : Details_Comptable.java
----------------------------------------------
    -   public Details_Comptable()
    -   public Details_Comptable(String code, String type, String name)
    -   public Details_Comptable(String code, String type, String name, String description)
    -   public String getCode()
    -   public String getType()
    -   public String getNom()
    -   public String getDescription()
    -   public void setCode(String code)
    -   public void setType(String type)
    -   public void setNom(String name)
    -   public void setDescription(String description)
    -   public JsonObject toJson()
    -   public String toString()
📍 Classe : Tiers.java
----------------------------------------------
    -   public Tiers()
    -   public Tiers(String name, String thirdPartyType)
    -   public Tiers(Long id, String name, String thirdPartyType)
    -   public Long getId()
    -   public void setId(Long id)
    -   public String getName()
    -   public void setName(String name)
    -   public String getThirdPartyType()
    -   public void setThirdPartyType(String thirdPartyType)
    -   public String getDescription()
    -   public void setDescription(String description)
    -   public JsonObject toJson()
    -   public String toString()
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.compta.controller
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.compta.dto
================================================
📍 Classe : JournalDTO.java
----------------------------------------------
    -   public JournalDTO()
    -   public JournalDTO(String name, LocalDate startDate, LocalDate endDate, String journalTypeCode)
    -   public JournalDTO(Long id, String name, LocalDate startDate, LocalDate endDate, String journalTypeCode)
    -   public JournalDTO(Long id, String name, LocalDate startDate, LocalDate endDate,
    -   public Long getId()
    -   public JournalDTO setId(Long id)
    -   public String getNom()
    -   public JournalDTO setNom(String name)
    -   public LocalDate getDateDebut()
    -   public JournalDTO setDateDebut(LocalDate startDate)
    -   public LocalDate getDateFin()
    -   public JournalDTO setDateFin(LocalDate endDate)
    -   public String getTypeJournalCode()
    -   public JournalDTO setTypeJournalCode(String journalTypeCode)
    -   public List<OperationDTO> getOperations()
    -   public JournalDTO setOperations(List<OperationDTO> operations)
    -   public JournalDTO addOperation(OperationDTO operation)
📍 Classe : MovementDTO.java
----------------------------------------------
    -   public MovementDTO()
    -   public MovementDTO(Long tiersId, String paiementCode, String accountDetailCode,
    -   public MovementDTO(Long id, Long tiersId, String paiementCode, String accountDetailCode,
    -   public MovementDTO(Long tiersId, String paiementCode, String accountDetailCode)
    -   public Long getId()
    -   public MovementDTO setId(Long id)
    -   public Long getTiersId()
    -   public MovementDTO setTiersId(Long tiersId)
    -   public String getPaiementCode()
    -   public MovementDTO setPaiementCode(String paiementCode)
    -   public String getCodeDetailsComptable()
    -   public MovementDTO setCodeDetailsComptable(String accountDetailCode)
    -   public BigDecimal getMontantDebit()
    -   public MovementDTO setMontantDebit(BigDecimal debitAmount)
    -   public BigDecimal getMontantCredit()
    -   public MovementDTO setMontantCredit(BigDecimal creditAmount)
    -   public String getDescription()
    -   public MovementDTO setDescription(String description)
📍 Classe : OperationDTO.java
----------------------------------------------
    -   public OperationDTO()
    -   public OperationDTO(LocalDate dateOperation, LocalDate dateComptable, String libelle,
    -   public OperationDTO(Long id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
    -   public OperationDTO(Long id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
    -   public Long getId()
    -   public OperationDTO setId(Long id)
    -   public LocalDate getDateOperation()
    -   public OperationDTO setDateOperation(LocalDate dateOperation)
    -   public LocalDate getDateComptable()
    -   public OperationDTO setDateComptable(LocalDate dateComptable)
    -   public String getLibelle()
    -   public OperationDTO setLibelle(String libelle)
    -   public String getReferenceDocument()
    -   public OperationDTO setReferenceDocument(String referenceDocument)
    -   public String getDescriptif()
    -   public OperationDTO setDescriptif(String descriptif)
    -   public String getStatutCode()
    -   public OperationDTO setStatutCode(String statutCode)
    -   public List<MovementDTO> getMovements()
    -   public OperationDTO setMovements(List<MovementDTO> movements)
    -   public OperationDTO addMovement(MovementDTO movement)
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.compta.entity
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.compta.repository
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.compta.service
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.core.dto.referential
================================================
📍 Classe : OperationStatus.java
----------------------------------------------
    -   public OperationStatus()
    -   public OperationStatus(String code, String name, String color)
    -   public String getCode()
    -   public OperationStatus setCode(String code)
    -   public String getNom()
    -   public OperationStatus setNom(String name)
    -   public String getColor()
    -   public OperationStatus setColor(String color)
    -   public JsonObject toJson()
📍 Classe : PaymentMethod.java
----------------------------------------------
    -   public PaymentMethod()
    -   public PaymentMethod(String code, String name, String description)
    -   public String getCode()
    -   public PaymentMethod setCode(String code)
    -   public String getNom()
    -   public PaymentMethod setNom(String name)
    -   public String getDescription()
    -   public PaymentMethod setDescription(String description)
    -   public JsonObject toJson()
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.core.dto
================================================
📍 Classe : PcgCoreDTO.java
----------------------------------------------
    -   public PcgCoreDTO()
    -   public List<Type_Comptable> getAccountingClasses()
    -   public void setAccountingClasses(List<Type_Comptable> accountingClasses)
    -   public void addAccountingClass(Type_Comptable typeComptable)
    -   public String toString()
📍 Classe : PcpCoreDTO.java
----------------------------------------------
    -   public PcpCoreDTO()
    -   public List<Tiers> getThirdParties()
    -   public List<Details_Comptable> getDetails()
    -   public void setThirdParties(List<Tiers> thirdParties)
    -   public void setDetails(List<Details_Comptable> details)
📍 Classe : ReferentialCoreDTO.java
----------------------------------------------
    -   public ReferentialCoreDTO()
    -   public List<OperationStatus> getOperationStatuses()
    -   public ReferentialCoreDTO setOperationStatuses(List<OperationStatus> operationStatuses)
    -   public List<PaymentMethod> getPaymentMethods()
    -   public ReferentialCoreDTO setPaymentMethods(List<List<PaymentMethod>> paymentMethods)
    -   public ReferentialCoreDTO addOperationStatus(OperationStatus status)
    -   public ReferentialCoreDTO addPaymentMethod(PaymentMethod method)
    -   public JsonObject toJson()
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcg.controller
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcg.entity
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcg.repository
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcg.service
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcp.controller
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcp.entity
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcp.repository
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API.features.pcp.service
================================================
📍 Classe : package-info.java
----------------------------------------------

📦 PACKAGE : com.jtissdev_API
================================================
📍 Classe : App.java
----------------------------------------------
    - public static void main(String[] args)
    - public CommandLineRunner testDeVerite(
    - public CommandLineRunner operationalRunner(CommandLineWorker worker)
----------------------------------------
