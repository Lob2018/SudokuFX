# Changelog


## Unreleased -


✨ feat release.yml, mainpage.dox, README.md, pom.xml, CHANGELOG.md: Integrate git-changelog-maven-plugin and add changelog links
- release.yml :
  - Add direct CHANGELOG.md link section to the release workflow description body.
- mainpage.dox :
  - Add link to CHANGELOG in the Doxygen main page index.
- README.md :
  - Add Documentation and CHANGELOG shields badges at the top.
  - Insert CHANGELOG.md entry in the main project navigation list.
- pom.xml :
  - Configure se.bjurr.gitchangelog:git-changelog-maven-plugin v2.4.0 with a custom Handlebars template to generate resources during build.
- Tests pass.

([438effb372c4770](https://github.com/Lob2018/SudokuFX/commit/438effb372c47701636a6d5cfd1903d87d91803a))

♻️ refactor SudokuFX__debug_.xml, README.md: Update remote debugging configuration and documentation
- SudokuFX__debug_.xml : Configure Maven runner settings to add JAVA_TOOL_OPTIONS with JDWP agent parameters
- README.md : Document the two-step attach process for reliable startup debugging with suspend enabled
- Tests pass.

([e01fbaab287abd9](https://github.com/Lob2018/SudokuFX/commit/e01fbaab287abd9329a315f051b110e68b1668ae))

♻️ refactor MyRegex, JVMApplicationProperties, GenerateSecret, MenuPlayerViewModel, GridViewModel: encapsulate regex patterns and expose domain-specific validation methods
- MyRegex: Remove public pattern getters to improve encapsulation and introduce domain-specific validation methods (isValidSecret, isValidPlayerName, isValidZeroCommaGrid, isValidVersion, isValidAlphanumeric).
- JVMApplicationProperties: Update configuration property checks to use the new domain-specific validation methods directly.
- GenerateSecret: Retrieve special characters using the new getSpecialChars() method instead of hardcoding them.
- MenuPlayerViewModel: Replace direct pattern matching calls with isValidPlayerName.
- GridViewModel: Replace direct pattern matching calls with isValidZeroCommaGrid.
- JVMApplicationPropertiesUTest: Update unit tests to reflect the refactored validation API.
- GenerateSecretUTest: Update unit tests to use isValidSecret instead of direct pattern matching.
- MyRegexUTest: Update unit tests and error messages to match the refactored validation methods.
- Tests pass

([440cceb4e695701](https://github.com/Lob2018/SudokuFX/commit/440cceb4e69570123541547928e4efc9f388d8fa))

♻️ refactor MyRegex, AbstractDataSourceConfig, ApplicationKeystore, GenerateSecret, IEncryptionService, IKeystore, SecretKeyEncryptionServiceAESGCM, Tests: Refactor sensitive data handling to use char arrays for enhanced memory security
- MyRegex:
	- Update validation methods to accept char[] instead of String for passwords
	- Enhance Javadocs and refine exception handling
- AbstractDataSourceConfig:
	- Manage database credentials as char[] and ensure explicit memory clearing using Arrays.fill
- ApplicationKeystore:
	- Convert username and password fields to char[] to avoid String pool persistence
	- Update keystore password generation and credential encryption flows
- GenerateSecret:
	- Modify passay secret generation to return char[]
- IEncryptionService:
	- Change encryption and decryption signatures to process char[]
- IKeystore:
	- Update credential retrieval methods to return char[]
- SecretKeyEncryptionServiceAESGCM:
	- Implement secure char[] buffer management for AES-GCM cryptographic operations
- Tests:
	- Update unit and integration tests to support char[] changes and assertions
- Tests pass.

([b53cc2e0a8b4986](https://github.com/Lob2018/SudokuFX/commit/b53cc2e0a8b49864dfb13310ec0ca02773a8ce24))

🐛 fix SudoMain: Convert IMainView iMainView as a local variable

([ec9a268b7280b67](https://github.com/Lob2018/SudokuFX/commit/ec9a268b7280b676c5f503d00610bf13358303b3))

👷 ci qodana.yml: Add upload-result to Qodana action
- Enable upload-result parameter in Qodana GitHub Action to publish detailed scan reports.
- Tests pass.

([0e70e60c146dfae](https://github.com/Lob2018/SudokuFX/commit/0e70e60c146dfaeab5b6e6b45fdbcde362c84ff3))

## v1.10.0 - 2026-08-20


🐛 fix pom.xml: Fix CVE on log4j-api transitive dependency by overriding version to 2.25.5
-Add explicit dependency override for log4j-api from 2.25.4 to 2.25.5.
-Tests pass.

([06a561263dae650](https://github.com/Lob2018/SudokuFX/commit/06a561263dae650679d7a3d42ceaa1ff14771518))

📚 docs Doxygen

([85b35cf041332e8](https://github.com/Lob2018/SudokuFX/commit/85b35cf041332e832954ec18ad7339344750457a))

🔧 chore maven-wrapper.properties: Update Maven distribution URL to version 3.9.16
-Upgrade distributionUrl from version 3.9.9 to 3.9.16.
-Tests pass.

([f7851159e6e6907](https://github.com/Lob2018/SudokuFX/commit/f7851159e6e6907c50a7615a23eb272dc10ff6a2))

🔧 chore requirements.txt: Bump the python-dependencies

([f32ca528de6555d](https://github.com/Lob2018/SudokuFX/commit/f32ca528de6555d02c46acf029455fa3c1290d9b))

✨ feat pom.xml: Bump version from 1.9.0 to 1.10.0

([54e3c359678eb71](https://github.com/Lob2018/SudokuFX/commit/54e3c359678eb71cef8f04a34ca8b9ceb0f07463))

🔧 chore flyway-database-hsqldb, checkstyle, jackson-core, jackson-databind: Bump the maven-dependencies

([3447781388ffacd](https://github.com/Lob2018/SudokuFX/commit/3447781388ffacd7617bef20c7eb7bcc8351d869))

🔧 chore harden-runner, codeql-action: Bump the actions-dependencies

([5329d95c623d1f5](https://github.com/Lob2018/SudokuFX/commit/5329d95c623d1f57eb565b0e35707ad4858c6b2d))

📚 docs GenericListViewFactory: Add Javadoc for constructor with grid converter dependency
-Document constructor parameters and requirements.
-Tests pass.

([4ca1af1aeecfdaa](https://github.com/Lob2018/SudokuFX/commit/4ca1af1aeecfdaa4a2673c25847fee05bcb11192))

♻️ refactor GridConverter: Update SUDOKU_RENDER_CAPACITY constant to match exact rendered string length
-Increase SUDOKU_RENDER_CAPACITY from 37 to 38 to prevent StringBuilder internal reallocation.
-Tests pass.

([70360c798e1a63a](https://github.com/Lob2018/SudokuFX/commit/70360c798e1a63a1f4dc3c139c51a46402ce6610))

✨ feat GridConverter, GenericListViewFactory, GridConverterUTest, GenericListViewFactoryUTest: Add 3-line sudoku grid renderer and integrate it into the save list view factory
-IGridConverter, GridConverter: Add renderGridValueFormattedAsThreeLinesOfSudoku method for rendering the first three rows of a saved sudoku grid.
-GenericListViewFactory: Inject IGridConverter to display the formatted sudoku preview alongside game update dates, and declare class as final.
-GridConverterUTest: Add unit tests covering valid grids, invalid grid value lengths, and blank/null inputs.
-GenericListViewFactoryUTest: Update instantiation to supply the GridConverter dependency.
-Tests pass.

([56de720ccb669e9](https://github.com/Lob2018/SudokuFX/commit/56de720ccb669e9ddb870e0df1ee5d994a8d5578))

📚 docs Doxygen

([baeb12eaeda84b8](https://github.com/Lob2018/SudokuFX/commit/baeb12eaeda84b889308893fbbcc5aaf69f4edaf))

📚 docs uc.drawio.png: Update use case diagram for custom grid creation

([2ff9e0cc299118f](https://github.com/Lob2018/SudokuFX/commit/2ff9e0cc299118fb48653a7036c6b855cfd77c80))

📚 README.md docs: Activate subtitles for SudokuFX video tutorial link

([ae67cadd1c64aa5](https://github.com/Lob2018/SudokuFX/commit/ae67cadd1c64aa5a5d6537ff6d17e86fe025aed8))

📚 docs Doxygen

([fd0e57b97e56f3d](https://github.com/Lob2018/SudokuFX/commit/fd0e57b97e56f3db64142a0fd916dc76f376055b))

📚 docs README.md: Update SudokuFX video tutorial link

([80a8dd43f812d40](https://github.com/Lob2018/SudokuFX/commit/80a8dd43f812d4039b0f4255741be49794145ae0))

## v1.9.0 - 2026-08-11


🔧 chore pom.xml: Bump version from 1.8.1 to 1.9.0

([c25bfd464a0fb7c](https://github.com/Lob2018/SudokuFX/commit/c25bfd464a0fb7c680535b9fbc5bc262423e0040))

🔧 chore flyway-database-hsqldb: Bump the maven-dependencies

([dc236691bcd45ee](https://github.com/Lob2018/SudokuFX/commit/dc236691bcd45ee864a783b48932f18f4b2b898e))

🔧 chore harden-runner, codeql-action, osv-scanner-action: Bump the actions-dependencies

([7d8372db09666ec](https://github.com/Lob2018/SudokuFX/commit/7d8372db09666ecc148cdf8bc2052fa32cf3c3bb))

✨ feat release, README, MyRegex, IGridMaster, MainView, MenuSolveViewModel: Add UI functionality and support for using custom grids
- release.yml : Update release notes text to include use capability
- README.md : Update documentation with solve and use features
- MyRegex.java : Add ZERO_COMMA_GRID_PATTERN regex pattern and getter
- IGridMaster.java : Add getNiveauDepuisPourcentage default method
- MainView.java : Bind use grid button and handle action events
- MenuSolveViewModel.java : Add properties and bindings for use this grid feature
- Tests pass.

([b8839d54cbf32b2](https://github.com/Lob2018/SudokuFX/commit/b8839d54cbf32b20dd231a33c60c1977a4ee4be8))

✨ feat MyDateTime, GenericDtoListCell, style.css, MyDateTimeUTest: Add seconds to datetime format and style list item labels
- MyDateTime: Update frenchFormatter and englishFormatter patterns to include seconds.
- GenericDtoListCell: Rename label style class to menuListButtonLabel.
- style.css: Add menuListButtonLabel rule with padding, maximum width, text overrun ellipsis.
- MyDateTimeUTest: Add seconds.
- Tests pass.

([2c16f1979bef39f](https://github.com/Lob2018/SudokuFX/commit/2c16f1979bef39fa55feb42fc372057c714d2ece))

📚 docs README.md: Update SudokuFX video tutorial link

([4cd6058a7721113](https://github.com/Lob2018/SudokuFX/commit/4cd6058a7721113608843723b9aa9de1b1b5c548))

🐛 fix README.md: Wrong path for the picture

([4fbdde4430d43f3](https://github.com/Lob2018/SudokuFX/commit/4fbdde4430d43f32e5616f5bc82939966ab42bfd))

✨ feat README.md: Add a video tutorial

([6e54d661934534a](https://github.com/Lob2018/SudokuFX/commit/6e54d661934534ade33fae5997405ce80287c116))

## v1.8.1 - 2026-08-03


🐛 fix pom.xml: Moved from version 1.8.0.1 to 1.8.1 for macOS jpackage compatibility

([c0669c49ec46352](https://github.com/Lob2018/SudokuFX/commit/c0669c49ec46352deeb3800bb2ed50e943608a85))

✨ feat pom.xml: Bump version from 1.8.0.0 to 1.8.0.1

([c1c9b911b45a3ca](https://github.com/Lob2018/SudokuFX/commit/c1c9b911b45a3ca3d7bcc538fb31e66456bda33e))

🐛 fix pom.xml: Fix CVE and align Jackson versions via dependencyManagement
- Move jackson-core and jackson-databind overrides to dependencyManagement.
- Add explicit comments for transitive dependencies.
- Tests pass.

([b2f9ae620b36c37](https://github.com/Lob2018/SudokuFX/commit/b2f9ae620b36c37bb82056644e50f4d3d6a8e2cb))

🔧 chore requirements.txt: Bump the python-dependencies

([5edcbf12872d568](https://github.com/Lob2018/SudokuFX/commit/5edcbf12872d56800f0f3bcff3c9d4594d704965))

🔧 chore javafx, flyway-database-hsqldb, spotless-maven-plugin, spotbugs-maven-plugin, spotbugs, spotbugs-annotations, checkstyle: Bump the maven-dependencies

([10b327414ad8124](https://github.com/Lob2018/SudokuFX/commit/10b327414ad8124d1223149da0e346d0faf025e2))

🔧 chore harden-runner, checkout, setup-java, codeql-action, scorecard-action, setup-python, qodana-action: Bump the actions-dependencies

([4c943a11836e221](https://github.com/Lob2018/SudokuFX/commit/4c943a11836e221ded6ac2e989d2c968da8b472f))

✨ feat pom.xml: Bump from version 1.8.0 to 1.8.0.0

([1a2baa37ff91d25](https://github.com/Lob2018/SudokuFX/commit/1a2baa37ff91d251dc4891e863ef98de90a53da9))

📚 docs Doxygen

([9f94541702462a8](https://github.com/Lob2018/SudokuFX/commit/9f94541702462a8628ca845118ee677c0658a419))

🐛 fix MenuOptionsViewModel, MainView: handle color persistence and player switch updates
- MainView.java:
  - Remove redundant duplicate color binding listeners.
  - Add log for player switch synchronization.
- MenuOptionsViewModel.java:
  - Add persistColor flag and listener for optionsColorProperty to manage application versus persistence.
  - Update init and applyOptionsToUI to toggle persistColor securely during programmatic color updates.
- Tests pass.

([963c673869a6583](https://github.com/Lob2018/SudokuFX/commit/963c673869a658380f393b6615bd7fc5684f9dda))

✨ feat GenericDtoListCell, style.css: Highlight the first item in lists
-GenericDtoListCell :
  -Add FIRST_IN_LIST PseudoClass to target the first cell
  -Update updateItem to trigger style state based on index 0
-style.css : Define specific background styles for first-in-list and focused state
-Tests pass.

([d6013d7e224d82f](https://github.com/Lob2018/SudokuFX/commit/d6013d7e224d82f61b6f9c04901b9be1a83d2da1))

📚 docs MainView: Javadoc for playerMenuInitialization

([1b941ae4f217cf3](https://github.com/Lob2018/SudokuFX/commit/1b941ae4f217cf3f289cac98347010837809144f))

✨ feat MainView: bind player edit button disable state to selection
-MainView: implement reactive boolean binding for menuPlayerButtonPlayerEdit based on player anonymity status.
-Tests pass.

([fb49108bdf527e2](https://github.com/Lob2018/SudokuFX/commit/fb49108bdf527e278cab476cae4f03ef24e03387))

🐛 fix CrashScreenView, HelpViewModel: standardize year retrieval using system timezone
-CrashScreenView: update year display logic for consistent timezone handling.
-HelpViewModel: refactor year retrieval and conversion for improved robustness.
-Tests pass.

([200cf80dbc4c0f5](https://github.com/Lob2018/SudokuFX/commit/200cf80dbc4c0f5bd3c65754d58d9a90e954837e))

📚 docs Doxygen

([d68ba19e1012455](https://github.com/Lob2018/SudokuFX/commit/d68ba19e1012455515279a8da0c4b30ccca04025))

📚 docs GameService, GridCellViewModel, MenuHiddenViewModel, MenuMaxiViewModel, MenuMiniViewModel, MenuNewViewModel, MenuOptionsViewModel, MenuPlayerViewModel, MenuSaveViewModel, MenuSolveViewModel: Javadoc

([dec064af9041a0c](https://github.com/Lob2018/SudokuFX/commit/dec064af9041a0cff4e6fbc524e543d0d5bc809f))

🔧 chore requirements.txt: Update all .venv packages &amp; hooks

([cf11bbe496b44b3](https://github.com/Lob2018/SudokuFX/commit/cf11bbe496b44b3a629113d09024f9f2161c74b4))

✨ feat: release.yml, pom.xml: Promote to stable release
-pom.xml :
  -Upgrade project version to 1.8.0.
-release.yml:
  -Set prerelease to false in release workflow.
  -Update release body to reflect full feature availability.
-Tests pass.

([1e944da4a8c49ef](https://github.com/Lob2018/SudokuFX/commit/1e944da4a8c49ef109fe11413ad8bfc48f02daaf))

✨ feat: PlayerService, MenuPlayerViewModel, MainView, PlayerSaveMode: Add capability to update player name
-PlayerSaveMode: enum to manage creation and update contexts.
-PlayerService: updateCurrentPlayerName to persist name changes.
-MenuPlayerViewModel: Implement prepareUpdatePlayerNameField and refactor savePlayer.
-MainView: bind the edit button and handle focus transitions.
-Tests pass.

([cef870bc061820c](https://github.com/Lob2018/SudokuFX/commit/cef870bc061820c5607b2f6281b697da4fadaf5c))

🐛 fix MainView: Remove System.out

([423c5bd37bd779e](https://github.com/Lob2018/SudokuFX/commit/423c5bd37bd779e36087fa35535eef6aab630d6c))

✨ feat GridViewModel, MainView: Implement real-time backup list synchronization on grid changes
-GridViewModel:
  -Add gridChanged observable property as a toggle signal
  -Update handleCellTextChange and applyGeneratedGrid to trigger notifications
-MainView:
  -Register listener on gridChangedProperty to auto-refresh backup list when active
  -Refresh games list upon manual backup menu show
  -Update Javadoc for saveMenuInitialization and handleMenuBackupShow
-Tests pass.

([e30afeb0780d93f](https://github.com/Lob2018/SudokuFX/commit/e30afeb0780d93f7d31da4bcfd01334a9fb050e9))

✨ feat GenericDtoListCell, GenericListViewFactory, MenuSaveViewModel: Implement dynamic deletion restrictions
-GenericDtoListCell: add isRemovable predicate to control delete button visibility
-GenericListViewFactory: block deletion of anonymous players and the active game session
-MenuSaveViewModel:  include reusable getCurrentSelectedGameDto and deleteABackup methods
-Tests pass.

([4b5f453917f18dd](https://github.com/Lob2018/SudokuFX/commit/4b5f453917f18ddcc635e0b91fc2ec736850fb5d))

♻️ refactor GameService, MainView, GenericListViewFactory, MenuSaveViewModel, GenericListViewFactoryUTest, MenuSaveViewModelUTest: Implement atomic game restoration and unify naming conventions
-GameService : Add switchAndSelectNewGame for atomic state transitions and extract getGameOrThrow for DRY compliance.
-MainView : Register reactive listener on selectedBackupProperty to trigger game restoration on selection.
-GenericListViewFactory : Update list configuration to use unified getGames nomenclature.
-MenuSaveViewModel : Rename backups collection to games and implement restoreABackup.
-Testing classes : Adjust mocks and assertions to follow new naming standards.
-Tests pass.

([460b821660f0c03](https://github.com/Lob2018/SudokuFX/commit/460b821660f0c03c28b62b5f96b49dee72b64323))

♻️ refactor Project_Default.xml, AbstractDataSourceConfig: Optimize database integrity check and project inspection settings
-Project_Default.xml : Disable SQL data source inspection to avoid false positives.
-AbstractDataSourceConfig : Define database validation query as a private static constant to improve maintainability.
-Tests pass.

([89fdf2bd9517f5d](https://github.com/Lob2018/SudokuFX/commit/89fdf2bd9517f5d6a6a8c92df48b1016f6c6da9b))

🔧 chore flyway-database-hsqldb, spotless-maven-plugin, checkstyle: Bump the maven-dependencies

([cc03dfc0f116659](https://github.com/Lob2018/SudokuFX/commit/cc03dfc0f1166596d9cc302d587ca879e11e6211))

🔧 chore setup-java, codeql-action, setup-python,cache: Bump the actions-dependencies

([8752697f3ab8e90](https://github.com/Lob2018/SudokuFX/commit/8752697f3ab8e9064f721688030cec33c53ff50a))

🔧 requirements.txt chore: Update all .venv packages &amp; hooks

([5dcd5925bbc6dc6](https://github.com/Lob2018/SudokuFX/commit/5dcd5925bbc6dc68fa743610636cc59447c2d0f2))

📚 docs Doxygen

([95cc7e1ff727e48](https://github.com/Lob2018/SudokuFX/commit/95cc7e1ff727e484337a9088e870b7859e772a5e))

📚 docs MainView: Update javadoc for menuSaveViewModel.refreshGames()

([9cbf3c641e24a7e](https://github.com/Lob2018/SudokuFX/commit/9cbf3c641e24a7e0483fb1afcbb57b018033d9c6))

✨ feat main-view.fxml, MainView, GenericListViewFactory, MenuSaveViewModel,  MenuSaveViewModelUTest, GameService, PlayerService, GameRepository, RepositoryTools: Implement game backup creation, list update, and reactive synchronization on player change
-main-view.fxml: Bind backup button to handleGameBackup.
-MainView: Trigger automatic games list refresh upon login and authentication state changes.
-GenericListViewFactory: Update method reference to handle backup deletion.
-MenuSaveViewModel: Implement creation of backups, update of the backup list, and automatic refresh triggered by active player change.
-MenuSaveViewModelUTest: Update unit tests to reflect new persistence-backed logic and observer-based list synchronization.
-GameService: Implement business logic for game creation, deletion, and reactive recovery.
-PlayerService: Refactor to use RepositoryTools.
-GameRepository: Add findAllUnselected method using fetch join.
-RepositoryTools: Add utility for database entity retrieval.
-Tests pass.

([1f8028961129826](https://github.com/Lob2018/SudokuFX/commit/1f80289611298266884f4c2aaf3e53948fc337b1))

♻️ refactor: LevelInteractionHandler, MainView, ToasterService, ToastData, AsyncFileProcessorService, Player: Refactor level synchronization, notification state handling, improve code modularity and null safety.
-LevelInteractionHandler: Extract finalizeLevelState method and rename thread for better diagnostics.
-MainView: Implement check against ToastData.EMPTY instead of null for toast requests.
-ToasterService: Replace null reset with ToastData.EMPTY constant.
-ToastData: Add static final EMPTY constant.
-AsyncFileProcessorService: Remove redundant JSpecify annotation.
-Player: Add Nonnull annotations to getter methods.
-Tests pass.

([40a0ae2e06f9e94](https://github.com/Lob2018/SudokuFX/commit/40a0ae2e06f9e94c1b9929eb6733dd5aa1ba1b16))

👷 ci osv-scanner-scheduled.yml: Restrict scan scope

# Conflicts:
#	.github/workflows/osv-scanner-scheduled.yml

([fd390afa6e0684f](https://github.com/Lob2018/SudokuFX/commit/fd390afa6e0684f05b029fb0dca71e469e1e4608))

♻️ refactor MenuPlayerViewModel: Player update logic for consistency
- Tests pass

([cfc428a305ee28b](https://github.com/Lob2018/SudokuFX/commit/cfc428a305ee28bebabba5ea57748da460e80478))

🐛 fix osv-scanner.toml: Configure security scan to ignore false positives
- Create osv-scanner.toml to document and suppress known security false positives in ignite-spring
- Define ignored vulnerabilities based on CVE IDs
- Document runtime safety verification via mvn dependency:build-classpath confirming version 7.0.8 usage
- Tests pass.

([992866abbc975b1](https://github.com/Lob2018/SudokuFX/commit/992866abbc975b10d1f15b64650e77b64d6a1e78))

📚 docs Doxygen

([748766b4fa07c99](https://github.com/Lob2018/SudokuFX/commit/748766b4fa07c99284464994301e2727af7a407a))

🔧 chore requirements.txt: Update dependencies and hooks

([695d868553571e6](https://github.com/Lob2018/SudokuFX/commit/695d868553571e64c840940f8f691e1970bb366e))

👷 ci osv-scanner-scheduled.yml: Disable strict lockfile scanning in CI and apply default configuration

([663e00763c62757](https://github.com/Lob2018/SudokuFX/commit/663e00763c62757655993c99865c2173b0aab9db))

🐛 fix pom.xml: Exclude vulnerable and conflicting dependencies from ignite-spring
- Exclude cglib and asm to prevent binary conflicts with modern bytecode utilities
- Exclude spring-beans and spring-context to enforce usage of Spring 7.0.8 via dependency mediation
- Verify dependency graph integrity and classpath consistency
- Tests pass.

([3e5b7b2bc0fdbf4](https://github.com/Lob2018/SudokuFX/commit/3e5b7b2bc0fdbf4e337ee0e20331d705bbb8a0b2))

🔧 chore checkout: Bump actions dependencies

([362a55959eb2a4f](https://github.com/Lob2018/SudokuFX/commit/362a55959eb2a4f4878da0cc025b68e8a04818da))

🐛 fix VersionService: Update Jackson methods for compatibility
- Replace deprecated isTextual and asText with isString and asString for robust JSON node processing.
- Tests pass.

([d8871ab948fcb1a](https://github.com/Lob2018/SudokuFX/commit/d8871ab948fcb1a7a4250bcb60daddf278e15f2b))

🐛 fix: package-info.java, MenuPlayerViewModelUTest.java: Update nullability annotations and cleanup test methods
- package-info.java: Replace deprecated org.springframework.lang.NonNullApi with org.jspecify.annotations.NullMarked in business, external, and ui packages.
- MenuPlayerViewModelUTest.java: Remove unnecessary throws Exception declaration.
- Tests pass.

([aded984a3890e9c](https://github.com/Lob2018/SudokuFX/commit/aded984a3890e9c2cbf82c0146f831441c4fe8b2))

♻️ refactor SplashScreenView, SpinnerGridPane, MyDateTimeUTest, OsFolderInitializerUTest, PlayerUTest, AudioServiceUTest, GenericListViewFactoryUTest, MenuPlayerViewModelUTest, AbstractPlayerStateTest: Standardize deterministic time and refactor components
- SplashScreenView: Use system zone for year retrieval.
- SpinnerGridPane: Move animation methods for clarity.
- MyDateTimeUTest: Replace hardcoded integers with Month enum and ensure clock determinism.
- OsFolderInitializerUTest: Use assertDoesNotThrow.
- PlayerUTest: Refactor builders to use deterministic dates and fix test scenarios.
- AudioServiceUTest: Use underscore for unused variables in try-with-resources.
- AbstractPlayerStateTest: Update date initializations to use Month enum.
- MenuPlayerViewModelUTest: Centralize fixed instant for deterministic tests.
- Tests pass

([64659f24def48f4](https://github.com/Lob2018/SudokuFX/commit/64659f24def48f428410a2e07942763befb0b925))

🐛 fix pre_commit_autoupdate.yml, AudioService.java: Add security annotation and sonar exclusion
- Add @Nullable annotation to songPlayer in AudioService to comply with NullMarked contract
- Add sonar:disable comment to pre_commit_autoupdate workflow to ignore dependency locking warning
- Tests pass.

([590151ba06649e7](https://github.com/Lob2018/SudokuFX/commit/590151ba06649e796441a8852c88e09fee53e232))

🔧 chore pom.xml: Bump Maven dependencies

([4d03e414b5dadca](https://github.com/Lob2018/SudokuFX/commit/4d03e414b5dadca47ac154883bfd3ff7f40f4344))

📚 docs Doxygen

([3d7a5dd7c792860](https://github.com/Lob2018/SudokuFX/commit/3d7a5dd7c79286004a399711f18df2f42862428a))

✨ feat: Increment application version from 1.7.0 to 1.7.1

([16ba02248e3420d](https://github.com/Lob2018/SudokuFX/commit/16ba02248e3420d7e595973dea12fb497c24c459))

♻️ refactor dependabot.yml: revert to stable configuration
-Tests pass.

# Conflicts:
#	.github/workflows/pre_commit_autoupdate.yml

([12e6a715a4dbf2c](https://github.com/Lob2018/SudokuFX/commit/12e6a715a4dbf2c7bf82e8d8a6a5f48022199f37))

🐛 fix osv-scanner-scheduled.yml: Revert scan-args to default settings
-Remove forced output-file argument to restore internal artifact passing
-Rely on default workflow behavior for file generation and reporting
-Tests pass.

([0d055279c4b2c21](https://github.com/Lob2018/SudokuFX/commit/0d055279c4b2c213fbe3bd338a14f5613068ed7f))

♻️ refactor pom.xml, unixThirdPartyNotices.sh, windowsThirdPartyNotices.bat, LICENSE.txt: Rename license generation scripts and adjust Maven profiles
- Update scripts to generate THIRD_PARTY_NOTICES.txt instead of LICENSE.txt
- Update Maven exec plugin IDs and script paths in pom.xml to match renamed files
- Restrict LICENSE.txt content to the GPLv3.0 license text only
- Tests pass.

([d03e74f0da7f2e3](https://github.com/Lob2018/SudokuFX/commit/d03e74f0da7f2e3329b2cbad92555cf279d1b025))

🐛 fix PlayerService, BindingConfigurator: Extract repeated error messages into constants
- PlayerService: Create constant PLAYER_NOT_FOUND to centralize player lookup error messages.
- BindingConfigurator: Create constant TEXT_PROPERTY_MUST_NOT_BE_NULL to centralize null check error messages.
- Tests pass.

([bb8e7c9a49ff9fb](https://github.com/Lob2018/SudokuFX/commit/bb8e7c9a49ff9fb5f25bccf12e44f35462116326))

👷 ci: osv-scanner-scheduled.yml: Optimize scan performance and stability
- Add scan-args to configuration
- Disable recursive scanning to reduce API requests and avoid 429 errors
- Skip git history analysis for faster execution
- Exclude requirements.txt to resolve package resolution conflicts
- Tests pass.

([b4b0c1df5441e3e](https://github.com/Lob2018/SudokuFX/commit/b4b0c1df5441e3e2b31650a06b217e717afadd34))

♻️ fix FileSystemManagerUTest, PlayerServiceUTest: Clean up unused code and reorganize test setup
- FileSystemManagerUTest: Reorder lifecycle methods (BeforeEach, AfterEach) for clarity.
- PlayerServiceUTest:
	- Remove unused imports and member variables (GameRepository, GridRepository, IGridMapper).
	- Convert local mocks to local variables within the setup method.
- Tests pass.

([9653cb68ac90fc7](https://github.com/Lob2018/SudokuFX/commit/9653cb68ac90fc7eafa0a9c06a450adc98bd0b27))

📚 docs README.md: Update documentation to include third-party notices
- Add Third-party notices badge to the header
- Add Third-party notices link to the table of contents
- Tests pass.

([8a49426522eee7a](https://github.com/Lob2018/SudokuFX/commit/8a49426522eee7a7ae4eaaba7bd7f8dd5c1bc932))

🐛 fix osv-scanner-scheduled.yml: Update scan-args to modern output-file format
- Replace deprecated output flag with output-file to ensure file generation compatibility
- Fix communication failure between OSV scanner and reporter steps
- Tests pass.

([70cd9b42ff69270](https://github.com/Lob2018/SudokuFX/commit/70cd9b42ff69270123016e2492eb3003eb9ab233))

✨ feat osv-scanner-scheduled.yml, README.md: Add OSV-Scanner automation and badge
- osv-scanner-scheduled.yml: Add scheduled, push, and manual scan workflow pinned to specific commit hash
- README.md: Add OSV-Scanner build status badge
- Tests pass.

([5208082d1313528](https://github.com/Lob2018/SudokuFX/commit/5208082d1313528d2d49aa9c253d4b63883cbe5b))

🔧 chore pre_commit_autoupdate.yml: Secure pre-commit installation
- Add --only-binary :all: flag to pip install to prevent arbitrary code execution during installation
- Tests pass.

([1352f8dea2d06b6](https://github.com/Lob2018/SudokuFX/commit/1352f8dea2d06b66b8030ff5b5676d58b4b5fe8b))

👷 ci openssf_score_card.yml: Restrict workflow permissions to job level
- Remove global permissions
- Set explicit read-only access for contents
- Maintain write access for security-events and id-token
- Tests pass.

([0a3e2164174714e](https://github.com/Lob2018/SudokuFX/commit/0a3e2164174714ea2c789f3f0caca44682bd845c))

🐛 fix coverage_report.yml: Refactor JaCoCo reporting to native shell script
- coverage_report.yml: Remove PavanMudigonda/jacoco-reporter action due to system compatibility issues.
- coverage_report.yml: Implement native XMLLint coverage parsing and report generation.
- coverage_report.yml: Simplify workflow permissions and remove redundant logging.

([febb7d78f2289be](https://github.com/Lob2018/SudokuFX/commit/febb7d78f2289be0bf94fcaedf70aaeab49cf492))

🐛 fix AbstractDataSourceConfig.java, V1_2__fix_gemelevel_values.sql: Prevent data corruption in gamelevel table by ensuring valid level values post-migration
☑ Root cause: Previous migration versions allowed inconsistent level values in gamelevel, leading to potential duplicates or invalid IDs that bypassed application logic.
☑ Fix: Implemented a mandatory integrity check in AbstractDataSourceConfig after Flyway execution to enforce a distinct count of levels; added a new SQL migration script (V1_2__fix_gemelevel_values.sql) to normalize existing level values based on primary key ordering.
☑ Update or add tests: Added runtime integrity validation logic in AbstractDataSourceConfig that triggers a crash-safe exception if the database state is invalid.
- Tests pass.

([d6126ead9198895](https://github.com/Lob2018/SudokuFX/commit/d6126ead91988957f64f700a901900e6353c9801))

🐛 fix MyRegex, MenuPlayerViewModelUTest: Prevent regex catastrophic backtracking and add unit tests
- MyRegex: Update PLAYER_NAME_PATTERN with possessive quantifiers to ensure linear time complexity.
- MenuPlayerViewModelUTest: Add comprehensive validation tests for empty, invalid, unavailable, and valid player name inputs.
- Tests pass.

([aca586d3b12319f](https://github.com/Lob2018/SudokuFX/commit/aca586d3b12319fa9b6801ff488be111b62c6726))

🔧 chore spotless-maven-plugin: Bump maven-dependencies

([ab7a88c1b0873d9](https://github.com/Lob2018/SudokuFX/commit/ab7a88c1b0873d9d87cb428044adb818bbbc2982))

📚 docs Doxygen

([9485d5a67de4e5d](https://github.com/Lob2018/SudokuFX/commit/9485d5a67de4e5d092cabdda41874e278df13b02))

🔧 chore setup-java, jacoco-reporter, qodana-action: Bump the actions-dependencies

([626063da69101f0](https://github.com/Lob2018/SudokuFX/commit/626063da69101f04a22a42a3ff82a90d595e051c))

📚 docs Doxygen

([55f8914345b963e](https://github.com/Lob2018/SudokuFX/commit/55f8914345b963e2c28e2a10b4c7b0115ff96751))

♻️ refactor SudoMain, ExceptionTools, DatabaseIntegrityException: Centralize critical database error handling
- SudoMain: rename logSqlInvalidAuthorization to logCriticalDatabaseException and support DatabaseIntegrityException.
- ExceptionTools: refactor findCriticalDatabaseException to handle both authorization and integrity exceptions.
- LogBackTxt: rename SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION to CRITICAL_DATABASE_EXCEPTION.
- DatabaseIntegrityException: introduce new domain exception for database consistency failures.
- AbstractDataSourceConfig: update database validation to throw DatabaseIntegrityException.
- Tests: update unit tests to support new exception handling logic and polymorphic return types.
- Tests pass.

([15b96cb9e4cd24c](https://github.com/Lob2018/SudokuFX/commit/15b96cb9e4cd24ce4039b3e23348d96884fb4132))

📚 docs Doxygen

([ba0bb4cecf3678c](https://github.com/Lob2018/SudokuFX/commit/ba0bb4cecf3678c58a4a753a4b41620b0563caa7))

🐛 fix MenuPlayerViewModel: prevent recursive state updates during player management
- Add isLockedWhileUpdating flag and runGuarded utility method
- Wrap player selection listener logic in runGuarded block
- Wrap deletePlayer service call and UI refresh in runGuarded block
- Add documentation for runGuarded and refreshUI methods
- Tests pass.

([79e0285c3d1788d](https://github.com/Lob2018/SudokuFX/commit/79e0285c3d1788dc9b1a422f0c21c512de894549))

✨ feat pom.xml: bump version to 1.7.0 and introduce partial profile support
- Update project version to 1.7.0
- Add initial implementation for player profile management (missing update logic)
- Tests pass.

([53c9aa55ea2554f](https://github.com/Lob2018/SudokuFX/commit/53c9aa55ea2554f9c114e6fd6e9d29fe83d35dd9))

♻️ refactor GameLevelService, PlayerService, MenuOptionsViewModel, MenuSaveViewModelUTest: Optimize internal methods and improve documentation

- GameLevelService: Add missing constructor Javadoc.
- PlayerService: Remove redundant validation and persistence steps in updatePlayerSelection, add Javadoc for deletePlayer.
- MenuOptionsViewModel: Update Javadoc for applyOptionsToUI.
- MenuSaveViewModelUTest: Clean up comment.
- Tests pass.

([bd6b93039dc92fb](https://github.com/Lob2018/SudokuFX/commit/bd6b93039dc92fba700a972f9b1d5739f967f34a))

🐛 fix PlayerService, MyAlert, Firework, MenuOptionsViewModel, MenuSaveViewModel, GridViewModel: Improve null safety and resolve potential NullPointerExceptions
- PlayerService: Introduce explicit null checks for game ID and DTO validation
- MyAlert: Add Nullable annotations and enforce final class modifier
- Firework: Suppress FBWarnings for property binding exposure
- MenuOptionsViewModel: Enforce final class modifier
- MenuSaveViewModel: Enforce final class modifier and add task placeholder
- GridViewModel: Use local variable caching for GameDto to ensure null safety and code clarity
- Tests pass.

([74d679f393d78f7](https://github.com/Lob2018/SudokuFX/commit/74d679f393d78f755da29e0bab62a9ee99238a29))

🐛 fix Game, GameLevel, Grid, Menu, Options, Player, PlayerLanguage, GameLevelUTest, GameUTest, GridUTest, MenuUTest, OptionsUTest, PlayerLanguageUTest: Harden JPA entities and fix SpotBugs CT_CONSTRUCTOR_THROW -
- Mark all JPA entity classes as final to prevent finalizer attacks and improve architectural integrity.
- Transition default constructors from protected to private to enforce proper encapsulation while remaining accessible to JPA via reflection.
- Update unit tests to instantiate entities using reflection for default constructor verification, maintaining test coverage while respecting private access.
- Tests pass.

([ed1dfcf135fe02c](https://github.com/Lob2018/SudokuFX/commit/ed1dfcf135fe02c8a4db41c2addc5bbd9cb818a1))

🐛 fix MenuPlayerViewModel: Fix localization update for new player input field
- MenuPlayerViewModel: Add an InvalidationListener to the localeProperty to dynamically update the playerNameInput placeholder when the language changes, preventing stale text.
- Tests pass.

([876cd72ad512f35](https://github.com/Lob2018/SudokuFX/commit/876cd72ad512f35dfc475d14a874e44a1c4ecbf8))

🐛 fix MenuOptionsViewModel.java: Correct synchronization of grid opacity property
- MenuOptionsViewModel: Add missing grid opacity synchronization to reflect player settings accurately.
- Tests pass.

([0b664256bb59dad](https://github.com/Lob2018/SudokuFX/commit/0b664256bb59dad19f5e0a25aff3539ec2bd7c7d))

📚 docs Doxygen

([5f3ea09fe6ba3f5](https://github.com/Lob2018/SudokuFX/commit/5f3ea09fe6ba3f51e8f33292443ced9e2139e50f))

🔧 chore harden-runner, checkout, codeql-action, dependency-review-action: Bump the actions-dependencies

([66136ea3e8316b6](https://github.com/Lob2018/SudokuFX/commit/66136ea3e8316b6a05ca38bfe533b3086277d41a))

📚 docs Doxygen

([ebcb912a0506f7c](https://github.com/Lob2018/SudokuFX/commit/ebcb912a0506f7cbd05532e0c509ee9ac7854880))

🐛 fix ObjectMapperConfig, VersionService, VersionServiceITest: Update imports and configuration for Jackson 3.x migration
- ObjectMapperConfig: Replace com.fasterxml.jackson with tools.jackson imports, switch to JsonMapper builder pattern, update Javadoc to reflect default ISO-8601 behavior.
- VersionService: Update imports to tools.jackson.databind.
- VersionServiceITest: Update imports to tools.jackson.databind.
- Tests pass.

([d9a053ca813c191](https://github.com/Lob2018/SudokuFX/commit/d9a053ca813c1912730eb127993cda753f7f257d))

🔧 chore pom.xml, spring-boot-starter-parent, spotbugs-annotations, flyway-database-hsqldb, spotbugs, checkstyle, datasource-proxy-spring-boot-starter, spotless-maven-plugin, spotbugs-maven-plugin, maven-enforcer-plugin, maven-surefire-plugin, jacoco-maven-plugin: Bump Maven dependencies

([97524bd623d8b94](https://github.com/Lob2018/SudokuFX/commit/97524bd623d8b94958a46bbee836a7fc88271e28))

📚 docs Doxygen

([a2734500013d40b](https://github.com/Lob2018/SudokuFX/commit/a2734500013d40b3f35bf6df5e6f13ce62105a32))

♻️ refactor PlayerNameStatus, LevelInteractionHandler, MenuPlayerViewModel, LevelInteractionHandlerUTest: Add input validation, Javadoc, and fix test exceptions
- PlayerNameStatus: Add Javadoc to all enum constants.
- LevelInteractionHandler: Add input validation for buttonId and opaqueApplier using ExceptionTools and Objects.requireNonNull.
- MenuPlayerViewModel: Add input validation to deletePlayer and null safety check to filterPlayerNameInput.
- LevelInteractionHandlerUTest: Update test expectations to expect IllegalArgumentException instead of NullPointerException following validation refactor.
- Tests pass.

([61df93070a2b178](https://github.com/Lob2018/SudokuFX/commit/61df93070a2b178213b90d26bbd026c20fa389ff))

👷 ci release.yml: Update release message from ❌ to 🚧 for ✨ Create profiles

([090629a074695df](https://github.com/Lob2018/SudokuFX/commit/090629a074695dff9d7507c6321cdb8c50741659))

🐛 fix Incorrect difficulty level selection due to state race conditions
☑ Root cause: The centralized &#x60;handleAction&#x60; method suffered from state inconsistency when tracking the active &#x60;level&#x60; variable. Events were processed sequentially without ensuring the selected level remained bound to the specific triggered interaction, causing the &#x60;level&#x60; state to mismatch the button ID during asynchronous execution.
☑ Fix:
- Decouple state management: Transitioned from a single dispatcher to explicit &#x60;handleStart(String buttonId)&#x60; and &#x60;handleEnd(String buttonId, ...)&#x60; to encapsulate the &#x60;level&#x60; context within the specific interaction instance.
- Refactor &#x60;MainView&#x60;: Switched to a declarative event router that explicitly triggers &#x60;stopCycle()&#x60; upon &#x60;MOUSE_EXITED&#x60;, preventing state contamination from orphaned mouse events.
- Synchronize lifecycle: Ensure the &#x60;level&#x60; is locked at &#x60;handleStart&#x60; and preserved for the duration of the cycle until &#x60;handleEnd&#x60;.
☑ Update or add tests:
- Refactor &#x60;LevelInteractionHandlerUTest&#x60; to simulate specific button ID triggering for each lifecycle phase.
- Added verification for &#x60;stopCycle()&#x60; to ensure state reset upon user abandonment.
- Tests pass.

([85630543e975997](https://github.com/Lob2018/SudokuFX/commit/85630543e975997b24fe3e19a4a47c936d332bbd))

📚 docs Doxygen

([2f62e8d5e79fe69](https://github.com/Lob2018/SudokuFX/commit/2f62e8d5e79fe69de889a28c1fb087951fa136c9))

♻️ refactor PlayerNameStatus, MainView, MenuPlayerViewModel: Relocate PlayerNameStatus domain enum from viewmodel to common package
-MainView : Update import to reflect new package location.
-MenuPlayerViewModel : Update import to reflect new package location.
-Tests pass.

([a4d8cd1d3206f47](https://github.com/Lob2018/SudokuFX/commit/a4d8cd1d3206f47ee8babd092d240cdc04188667))

♻️ refactor MyRegex, MenuOptionsViewModel, MenuPlayerViewModel, PlayerStateHolder: Improve encapsulation and resolve SpotBugs warnings
-MyRegex : Centralize name validation regex in the enum.
-MenuOptionsViewModel : Add missing SpotBugs suppression for optionsColorProperty.
-MenuPlayerViewModel : Migrate to ReadOnlyBooleanWrapper for state properties, centralize regex usage, and apply SuppressFBWarnings for infrastructure services.
-PlayerStateHolder : Restrict constructor visibility to package-private for safer DI and testing.
-Tests pass.

([570c3b2cabb064d](https://github.com/Lob2018/SudokuFX/commit/570c3b2cabb064d3a29522e4baa9ee8ec15cf9ae))

♻️ refactor PlayerConstants, PlayerService, GenericDtoListCell, MenuPlayerViewModel: Centralize anonymous player constant usage
- PlayerConstants: Create new enum for centralizing player-related constants.
- PlayerService: Use PlayerConstants.ANONYMOUS_NAME instead of hardcoded string.
- GenericDtoListCell: Use PlayerConstants.ANONYMOUS_NAME for anonymous player check.
- MenuPlayerViewModel: Remove local constant and use PlayerConstants.ANONYMOUS_NAME.
- Tests pass.

([97267a487716f0e](https://github.com/Lob2018/SudokuFX/commit/97267a487716f0e48fa8e7226273dca750952e7c))

✨ feat Player, PlayerRepository, GameLevelService, PlayerService, GenericDtoListCell, GenericListViewFactory, MenuPlayerViewModel, MenuSaveViewModel, GridViewModel, GridViewModelUTest, PlayerServiceUTest, AbstractPlayerStateTest: Implement complete player deletion flow and refactor level management
- Player: Clean up JPA CascadeType formatting.
- PlayerRepository: Add findByName method to retrieve anonymous player.
- GameLevelService: Create new service to manage GameLevel entities and business logic.
- PlayerService: Inject GameLevelService to handle level lookups, implement deletePlayer with anonymous player protection.
- GenericDtoListCell: Add onRemoveAction, implement anonymous player detection to hide/manage delete button visibility.
- GenericListViewFactory: Integrate deletePlayer and deleteGame actions into list cell creation.
- MenuPlayerViewModel: Implement deletePlayer method with state refresh.
- MenuSaveViewModel: Add placeholder deleteGame method.
- GridViewModel: Inject GameLevelService and IGameLevelMapper, replace manual level DTO construction in persistNewGame.
- GridViewModelUTest: Add mocks for GameLevelMapper, update setup for new dependencies.
- PlayerServiceUTest: Update setup to use GameLevelService instead of GameLevelRepository.
- AbstractPlayerStateTest: Add GameLevelService mock to base test class.
- Tests pass.

([fd9c907b8446325](https://github.com/Lob2018/SudokuFX/commit/fd9c907b84463259b3fc9e98711cc47c9d577b7f))

♻️ refactor Game, Player: Refine JPA cascade operations
- Game: update cascade types for gridid, playerid, and levelid to restrict REMOVE operations.
- Player: update cascade types for playerlanguageid, optionsid, menuid, games to improve lifecycle management and add Javadoc documentation.
- Tests pass.

([848c830d9205572](https://github.com/Lob2018/SudokuFX/commit/848c830d9205572194b21857ff1615061468d9b6))

✨ feat Game, GameLevelRepository, PlayerService, AsyncFileProcessorService, MainView, MenuLevelViewModel, MenuOptionsViewModel, MenuPlayerViewModel, GridViewModel, MenuPlayerViewModelUTest, MenuOptionsViewModelUTest: Implement player creation and selection logic
- Game: Added setLevelid method to facilitate difficulty level updates.
- GameLevelRepository: Added findByLevel method for database lookups by numerical level.
- PlayerService:
    - Updated updatePlayer to synchronize nested game entities using new findByLevel repository method.
    - Replaced unselectPlayer with general updatePlayerSelection for atomic selection status updates.
    - Added switchAndSelectNewPlayer for transactionally switching the active player.
- AsyncFileProcessorService: Updated processFileAsync to return the Task instance for better lifecycle management.
- MainView:
    - Refactored options menu initialization to support reactive bidirectional bindings.
    - Added synchronizeUIAfterPlayerSwitch to update UI state when the active player changes.
    - Updated event handlers to use new ViewModel methods.
- MenuLevelViewModel: Added clearSelectedLevel method to reset selection state.
- MenuOptionsViewModel:
    - Added optionsColorProperty for reactive UI binding.
    - Refactored applyAndPersist methods to allow conditional persistence.
    - Added restoreCurrentOptions to sync visual state without re-triggering database updates.
- MenuPlayerViewModel:
    - Added listener to currentPlayerProperty to automate UI synchronization via playerSwitchedSignal.
    - Renamed commitNewPlayerName to createNewPlayerByName.
- GridViewModel: Added null check for selectedGame to ensure consistency.
- MenuPlayerViewModelUTest: Isolated ViewModel testing using mocked PlayerStateHolder.
- MenuOptionsViewModelUTest: Updated test suite to use refactored ViewModel initialization and conditional persistence methods.
- Tests pass.

([7098a1405ae49f9](https://github.com/Lob2018/SudokuFX/commit/7098a1405ae49f95379b045a764ab3c875265cee))

♻️ refactor GenericListViewFactory, GenericListViewFactoryUTest: Update list selection to explicit confirmation
- GenericListViewFactory:
	- Replace automatic bidirectional binding with explicit event-based confirmation for ENTER/SPACE keys and double-clicks.
	- Prevent premature property updates during list navigation.
- GenericListViewFactoryUTest:
	- Update player and game list selection tests to simulate KEY_PRESSED (ENTER) events.
	- Assert property updates only occur after explicit confirmation, ensuring test alignment with the updated factory logic.
- Tests pass.

([8aeead342206391](https://github.com/Lob2018/SudokuFX/commit/8aeead3422063916ee8fc9373fa5a9cc8f32d534))

📚 docs Doxygen

([8c797132b0cd4a5](https://github.com/Lob2018/SudokuFX/commit/8c797132b0cd4a5138dc014179f63e07c88bea4b))

📚 docs ApplicationKeystore, PlayerLanguageRepository, GridService, OptionsService, PlayerLanguageService, PlayerService, MainView: Add Javadoc to constructors and utility methods &gt; Only the ViewModels remain to be documented.
- ApplicationKeystore : Add constructor Javadoc.
- PlayerLanguageRepository : Add Javadoc for findByIso method.
- GridService : Add constructor Javadoc.
- OptionsService : Add constructor Javadoc.
- PlayerLanguageService : Add constructor Javadoc.
- PlayerService : Add constructor Javadoc.
- MainView : Add constructor Javadoc and Javadoc for status management methods, update comment to English.
- Tests pass.

([898a8d9da4966af](https://github.com/Lob2018/SudokuFX/commit/898a8d9da4966afe5f5c3f4b9ad1e69826663c07))

✨ feat PlayerService, GridService, OptionsService, MainView, BindingConfigurator, PlayerNameStatus, MainView (FXML/CSS): Implement full player creation workflow
- GridService : Add duplicateGrid method for deep cloning of grid entities.
- OptionsService : Add duplicateOptions method for deep cloning of user preferences.
- PlayerService :
	-Add createNewPlayerWithCurrent for managing complete dependency tree (Options, Grid, Game), handling persistence, entity unselection, and timestamp initialization via MyDateTime.
	-Add generic findOrThrow utility for consistent entity resolution.
- PlayerService : Update getPlayers sorting to be case-insensitive.
- MainView :
	-Replace new player label with editable TextField and configure bidirectional bindings to ViewModel.
	-Implement real-time validation feedback using custom CSS pseudo-classes (name-valid, name-invalid, name-unavailable).
	-Register keyboard listeners for ENTER (commit) and ESCAPE (rollback).
- BindingConfigurator : Add utility method for bidirectional TextField binding.
- MenuPlayerViewModel : Implement state machine for name validation, editing lifecycle, and input filtering.
- PlayerNameStatus : Add enum to track validation states.
- Tests pass.

([30f7e2dd9cdba68](https://github.com/Lob2018/SudokuFX/commit/30f7e2dd9cdba68c53ff3972f6aba1dd7bc92692))

📚 docs Merise MPD updated

([9d375be04c205b4](https://github.com/Lob2018/SudokuFX/commit/9d375be04c205b457bafff0bd40f2245b49c423c))

✨ feat PlayerRepository, PlayerService, MenuPlayerViewModel, MenuPlayerViewModelUTest: Implement production player loading query and service logic
-PlayerRepository: Add findAllUnselectedWithSelectedGame query method with sorting support
-PlayerService: Add transactional getPlayers method with mapping and Jakarta bean validation
-MenuPlayerViewModel: Inject PlayerService and replace test data loop with real database query results
-MenuPlayerViewModelUTest: Adjust test constructor call and update initial player list size assertion
-Tests pass.

([1bb3b1beeccb389](https://github.com/Lob2018/SudokuFX/commit/1bb3b1beeccb389488f1e99ba7bc63cd61494c6c))

♻️ refactor DynamicFontSize, DynamicFontSizeChangeE2ETest: Expose font size as read-only property
-DynamicFontSize:
	-Convert currentFontSize primitive to ReadOnlyDoubleWrapper
	-Add currentFontSizeProperty method for reactive bindings
-DynamicFontSizeChangeE2ETest: Update test assertions to use the new property method
-Tests pass.

([f8b7b80cb75d292](https://github.com/Lob2018/SudokuFX/commit/f8b7b80cb75d292eba97a8088ffcd53e57c89aa4))

🔧 chore coverage_report.yml: Update JaCoCo reporter action

([1d89e3344e318a2](https://github.com/Lob2018/SudokuFX/commit/1d89e3344e318a2863b482daaa4fd51b83f5484f))

👷 ci dependabot.yml: Trigger Dependabot rerun

([bd793b06926c40a](https://github.com/Lob2018/SudokuFX/commit/bd793b06926c40abd916434098e27dbd86359ed3))

📚 docs Doxygen

([96210a51e418718](https://github.com/Lob2018/SudokuFX/commit/96210a51e418718a65b4604e3ae77ef3412f11dc))

📚 docs README.md,package-info.java: Document all package structures across the application
- README.md: Synchronize tree architecture descriptions with latest modules
- package-info.java: Add missing package documentation headers for root, benchmark, common, config, dto, model, navigation, repository, view, and viewmodel layers
- Tests pass.

([76883bb1c8363bb](https://github.com/Lob2018/SudokuFX/commit/76883bb1c8363bb8d36f54e8186243ad3d802b67))

## v1.6.2 - 2026-04-29


🔧 chore pom.xml : Bump the maven-dependencies

([55e62edb9877b98](https://github.com/Lob2018/SudokuFX/commit/55e62edb9877b98e4bb167d06ca8d96bbad2bd78))

🔧 chore harden-runner, qodana-action: Bump the actions-dependencies

([cae02b01a6f44be](https://github.com/Lob2018/SudokuFX/commit/cae02b01a6f44be9e031be0939ec85c14981e4e5))

📚 docs Doxygen

([1997f59cc91fc2a](https://github.com/Lob2018/SudokuFX/commit/1997f59cc91fc2adb3c09e635c03364c4fe2f340))

🛠️ build pom.xml: Bump application version to 1.6.2

([15849ac3b15bb6e](https://github.com/Lob2018/SudokuFX/commit/15849ac3b15bb6ed082465dd9f734e1f546fca3d))

🐛 fix spring-boot-starter: Upgrade to 4.0.6 to fix security vulnerabilities
-spring-boot-starter-data-jpa: Bump version from 4.0.5 to 4.0.6.
-spring-boot-starter-validation: Bump version from 4.0.5 to 4.0.6.
-pom.xml: Update application version to 1.6.2.
-spring-boot: Address CWE-377, CWE-59, and CWE-338.
-spring-boot-autoconfigure: Mitigate CWE-297.
-Tests pass.

([e0931e934244c3b](https://github.com/Lob2018/SudokuFX/commit/e0931e934244c3b709edf621e703c7d8a3f5989e))

🐛 fix GridMaster, GridViewModel, resource_en_US.properties, resource_fr_FR.properties, GridMasterUTest: Strengthen grid generation validation and user feedback
-GridMaster: Added strict validation for desired percentage with IllegalArgumentException.
-GridViewModel: Implemented robust Task failure and cancellation handling with localized toasts.
-GridViewModel: Lowercased i18n keys for levelinteractionhandler consistency.
-resource_.properties: Added translations for task failure and cancellation.
-GridMasterUTest: Updated tests to assert IllegalArgumentException for invalid percentages.
-Tests pass.

([5a5235680a18cdb](https://github.com/Lob2018/SudokuFX/commit/5a5235680a18cdb89339637ffc6eddad4e374be3))

🐛 fix linuxRelease.sh: Repair broken Linux deployment and CDS training for No-JRE version
-Change installation check from directory existence to JAR file presence.
-Fix broken CDS cache by moving the JAR instead of deleting it after extraction.
-Fix HSQLDB connection conflicts by adding a 2-second delay for database lock release.
-Add -Xshare:off during dump phase to prevent class sharing conflicts.
-Hide training and runtime logs via /dev/null to prevent duplicate banner display.
-Tests pass.

([3887e900392a0a7](https://github.com/Lob2018/SudokuFX/commit/3887e900392a0a7cba013716583cab0e8ba91536))

🔧 chore pom.xml: Bump flyway-database-hsqldb from 12.3.0 to 12.4.0

([f693d0b1b2a05df](https://github.com/Lob2018/SudokuFX/commit/f693d0b1b2a05dfb870313bc203745e678b61a5d))

🔧 chore harden-runner, codeql-action, upload-artifact, cache: Bump the actions-dependencies

([4a08a9e2157dfb8](https://github.com/Lob2018/SudokuFX/commit/4a08a9e2157dfb849ef23ae42562672a1f8be95f))

## v1.6.1 - 2026-04-08


📚 docs Doxygen

([5ced298f907d907](https://github.com/Lob2018/SudokuFX/commit/5ced298f907d9079cefbc3ae1054f4d5e964735c))

✨ feat pom.xml: Update version from 1.6.0 to 1.6.1 (incl. fix for jackson-core vuln. CWE‑770 CVSS 8.7)

([f0360e90c07823a](https://github.com/Lob2018/SudokuFX/commit/f0360e90c07823a69c58a5b2b5faf6cfc627249f))

♻️ refactor MyRegex, GenerateSecret, GenerateSecretUTest: Implement variable length and enhanced security for secrets
- Update SECRET_PATTERN in MyRegex to support 24-32 characters
- Add lookahead assertions to Regex to enforce password complexity
- Integrate SecureRandomGenerator for dynamic secret length selection
- Refactor GenerateSecret to use Passay 2.0.0 API (constructor-based)
- Update unit tests to validate the new length range and complexity rules
- Clean up unused imports and add missing @Override annotations
- Tests pass

([c880a420c207a5d](https://github.com/Lob2018/SudokuFX/commit/c880a420c207a5d0a7f76ce1f8b6fd29fbd25d9b))

🔧 chore flyway-database-hsqldb, passay: Bump the maven-dependencies

([8646d51e6f36419](https://github.com/Lob2018/SudokuFX/commit/8646d51e6f3641919907285fc176fb7505217972))

🔧 chore harden-runner: Bump the actions-dependencies

([9e7615152d0832b](https://github.com/Lob2018/SudokuFX/commit/9e7615152d0832be502a7a0e5fc54dc7a80e9bc6))

🔧 chore requirements.txt: Update all .venv packages &amp; hooks for pre-commit

([7eed3410e85942e](https://github.com/Lob2018/SudokuFX/commit/7eed3410e85942e2c7ac68ade62ed76943140bb2))

🔧 chore spring-boot-starter-parent, flyway-database-hsqldb, spotbugs-maven-plugin, checkstyle: Update the maven-dependencies

([3c52c1e84f4dd92](https://github.com/Lob2018/SudokuFX/commit/3c52c1e84f4dd9229fadebf9e889e42bd401d5fe))

🔧 chore codeql-action, cache: Update the actions-dependencies

([d504d01bed47440](https://github.com/Lob2018/SudokuFX/commit/d504d01bed4744036264b534f7864f3152cca570))

## v1.6.0 - 2026-03-23


🛠️ build pom.xml: Update project version from 1.5.0 to 1.6.0
- Tests pass

([cbb3dbcc1645211](https://github.com/Lob2018/SudokuFX/commit/cbb3dbcc164521162eb587903e4d8759c50d1101))

🔧 chore spring-boot-starter-parent, javafx.version, spotless-maven-plugin : Bump the maven-dependencies

([70b6b661380266c](https://github.com/Lob2018/SudokuFX/commit/70b6b661380266c44bf30598baf605abebc1ec29))

🔧 chore .pre-commit-config.yaml, requirements.txt: Update all .venv packages &amp; hooks for pre-commit

([8e3946faa3a8340](https://github.com/Lob2018/SudokuFX/commit/8e3946faa3a83403b59cb687db213ac5acc59460))

🔧 chore harden-runner, codeql-action, qodana-action : Bump the actions-dependencies

([c967ed5ccbf5e73](https://github.com/Lob2018/SudokuFX/commit/c967ed5ccbf5e736a245e368d975c8997f65468c))

🔧 chore flyway-database-hsqldb: bump from to 12.1.0 12.1.1

([7d6b4af0fb282b2](https://github.com/Lob2018/SudokuFX/commit/7d6b4af0fb282b2013360374369a33d852f5bb9c))

🔧 chore: Upgrade Flyway and remove Jackson security patches

([cf5cb63ac94cefc](https://github.com/Lob2018/SudokuFX/commit/cf5cb63ac94cefc82447f5f78cc933d2084753e8))

🔧 chore jacoco-reporter, release-action, download-artifact, delete-artifact: Bump the actions-dependencies

([aae37ab5a1e99df](https://github.com/Lob2018/SudokuFX/commit/aae37ab5a1e99df239d15638d3cdd544058f0122))

♻️ refactor PathValidator, GridConverter, GridMaster, SpinnerGridPane, Firework, LevelInteractionHandler, MenuOptionsViewModel, GridViewModel: Standardize flow control with Happy Path and Early Returns
- Replace negative conditional checks (!) with positive Early Returns across utility and view classes.
- Refactor GridConverter loop to use conditional separator appending and eliminate post-loop string manipulation.
- Simplify recursive grid generation in GridMaster by prioritizing the success path.
- Optimize JavaFX event handling and animations by removing nested blocks in lambdas and pattern matching.
- Standardize initialization checks in ViewModels using affirmative guard clauses.
- Tests pass.

([9afaecc1106e388](https://github.com/Lob2018/SudokuFX/commit/9afaecc1106e388a8e9b5f2371651cdd0c063a92))

🔧 chore .pre-commit-config.yaml, requirements.txt: Bump pre_commit packages &amp; hooks

([d6ef25088255722](https://github.com/Lob2018/SudokuFX/commit/d6ef250882557228dd8173ccbddb2ad5c0ea5d8b))

👷 ci release.yml: Update release notes with feature status

([ee83c5dc73788e0](https://github.com/Lob2018/SudokuFX/commit/ee83c5dc73788e0708a936f5dbda26fc2d704fc8))

## v1.5.0 - 2026-03-11


📚 docs: Doxygen

([d6e8df9538f15e6](https://github.com/Lob2018/SudokuFX/commit/d6e8df9538f15e60cac69337e379877d2ef20bbb))

🐛 fix GridMaster: Fix inconsistent grid state on generation fail-safe
- GridMaster:
    - Add explicit Arrays.fill(0) on thread interruption within the generation loop.
    - Ensure grid reset when MAX_ESSAIS_POUR_GENERATION_DE_GRILLE is reached.
    - Extract getPossibilitesGrilleWhileNok to standardize grid masking and entropy calculation.
    - Refactor generation logic to prioritize uniqueness verification before difficulty validation.
    - Fix bug where partially masked grids (e.g., 54-56 zeros) were returned instead of an empty grid upon watchdog timeout.
    - Update Javadoc to reflect Task interruption and watchdog behavior.
- Tests pass.

✅ Fixed bug report
- Scenario: User or test suite attempts to generate a grid with an impossible difficulty percentage (e.g., 500%).
- Platform: Windows/Linux/MacOs (CI GitHub Actions), Java 25.
- Steps to reproduce:
    - 1. Invoke GridMaster.creerLesGrilles with an out-of-bounds or extremely high percentage.
    - 2. Wait for the watchdog (300ms) or maximum trial exhaustion to trigger.
    - 3. Verify the number of empty cells in the returned grid.
- Expected behavior: An entirely empty grid (81 zeros) should be returned following the fail-safe trigger.
- Actual behavior: The returned grid contains a partial state (e.g., 54, 55, or 56 zeros), corresponding to the last iteration interrupted by the watchdog.

([ee0bc2a7247ba67](https://github.com/Lob2018/SudokuFX/commit/ee0bc2a7247ba6717684ec74a9762cffd8302c3e))

📝 style GridMaster.java: Run automatic code formatter

([4292180916a5b90](https://github.com/Lob2018/SudokuFX/commit/4292180916a5b90b4a253c47212dde7faf89f3eb))

♻️ refactor GridMaster.java: Merge entropy and uniqueness checks

([209497ec33ab515](https://github.com/Lob2018/SudokuFX/commit/209497ec33ab515f8afbdae042ff2d41ea3754d9))

🐛 fix GridMaster.java: Force grid reset on generation failure to ensure CI stability

([a1b16217765a3d4](https://github.com/Lob2018/SudokuFX/commit/a1b16217765a3d4cbf2a8e9207df410bf115650e))

📚 docs : Doxygen + style : Run automatic code formatter

([91bd05dc8602c32](https://github.com/Lob2018/SudokuFX/commit/91bd05dc8602c3244bff91d9536b46c957381282))

🛠️ build pom.xml: Bump version from 1.4.4 to 1.5.0 and update Doxygen

([d28f2b93f6291db](https://github.com/Lob2018/SudokuFX/commit/d28f2b93f6291db64ae445959485e057411e9101))

✨ feat GridMaster, GridViewModel, GridCellViewModel, I18n, : Overhaul difficulty entropy and implement manual solve mode
- GridMaster:
	- Implement bit-count based entropy calculation using Integer.bitCount.
	- Add early pruning filter in grid generation to skip uniqueness checks on invalid entropy.
	- Introduce local working arrays to eliminate GC pressure during stochastic search.
	- Refactor difficulty dispatcher to use a unified generation pipeline.
- IGridMaster:
	- Redefine difficulty bounds (150-330) and hidden cells range (30-62).
- GridCellViewModel:
	- Consolidate TextArea key handling into a single EventFilter.
	- Implement silent reset for identical digit re-entry to force property updates.
- GridViewModel:
	- Implement real-time manual solving with cell anchoring and automatic rollback.
	- Inject MenuSolveViewModel to synchronize solve percentage progress.
	- Refactor handleCellTextChange into specialized play and solve flows.
	- Enhance clearGrid to reset state without triggering redundant listeners.
- I18n:
	- Add toast.warning.gridviewmodel.nosolution for both English and French.
- Tests:
	- Update UTests to align with new entropy thresholds and constructor dependencies.
- Tests pass.

([530b886942eed33](https://github.com/Lob2018/SudokuFX/commit/530b886942eed331c24760cb8ac4a25ddbd364a6))

📚 docs Doxygen

([99fcc3bf29d36b4](https://github.com/Lob2018/SudokuFX/commit/99fcc3bf29d36b464c6415b35955becaf070872a))

♻️ refactor AppIcons, SplashScreenView, AppIconsUTest, I18n, pom.xml,JVMApplicationProperties , CrashScreenView, MainView, JVMApplicationPropertiesUTest, GridMasterUTest: centralize window title logic and cleanup
- pom.xml:
    - Add openjfx-monocle 21.0.2 dependency for headless testing support (UI Glass stub).
- AppIcons:
    - Create new enum to centralize SVG path data for application-wide icons.
- I18n:
    - Refactor from static to instance-based members for bundles and properties.
    - Add professional Javadoc documenting JavaFX Toolkit requirements.
    - Rename LOCALE to locale and update accessors to support UI binding.
    - Use Java 21 unnamed variables (_) in locale listener to resolve unused parameter warnings.
- JVMApplicationProperties:
    - Implement getWindowTitle to standardize &quot;Name Version • Organization&quot; pattern.
    - Add package-private setters (setAppNameForTests, etc.) for isolated testing.
- CrashScreenView:
    - Remove unused primaryStage and redundant setMaximized call.
    - Integrate AppIcons.LOGO and simplify createSVG method by removing unused offset parameters.
    - Use centralized getWindowTitle for stage title.
- SplashScreenView:
    - Integrate AppIcons.LOGO for splash screen rendering.
- MainView:
    - Initialize primaryStage title via getWindowTitle.
    - Modernize lambdas and listeners using unnamed variables (_) for unused parameters (toaster, audio, and menu listeners).
- GridMasterUTest:
    - Implement initJfx with Monocle configuration to support headless Spring context loading.
- JVMApplicationPropertiesUTest:
    - Add unit test for window title formatting using internal setters.
- AppIconsUTest:
    - Implement unit tests to validate enum integrity and SVG path data.
- Tests pass.

([36349270e26b045](https://github.com/Lob2018/SudokuFX/commit/36349270e26b045be0be84ba9a7fe7e48afea53b))

🛠️ build runConfigurations/SudokuFX_SpotBugs_GUI_.xml: Rename and enhance SpotBugs run configuration
- Rename configuration file and name from SpotBug to SpotBugs for consistency.
- Add clean and compile goals before launching the GUI.
- Include spotbugs:check goal to ensure analysis is performed.
- Set -Dspotbugs.failOnError&#x3D;false constant to allow GUI launch even with findings.
- Tests pass.

([1290bba8ecd61fb](https://github.com/Lob2018/SudokuFX/commit/1290bba8ecd61fb752235c26fe6bcb3410697711))

## v1.4.4 - 2026-03-09


🐛 fix pom.xml: mitigate Mend.io GHSA-72hv-8253-57qq and upgrade spotless-maven-plugin
- Add jackson-core 2.21.1 to resolve async parser number length bypass (DoS).
- Add jackson-databind 2.21.1 and jackson-annotations 2.21 for version alignment.
- Overwrite transitive vulnerable dependencies from flyway-database-hsqldb.
- Upgrade spotless-maven-plugin from 3.2.1 to 3.3.0.
- Include explicit security warning comments for Jackson modules.
- Tests pass.

([b34c67ae83fc29c](https://github.com/Lob2018/SudokuFX/commit/b34c67ae83fc29c9aaac4c0c2bbaf6bf75dd9eb3))

🔧 chore harden-runner, codeql-action, dependency-review-action: Bump the actions-dependencies

([fbbfbdd13e14651](https://github.com/Lob2018/SudokuFX/commit/fbbfbdd13e14651097d992ddc04e010593daaf84))

🐛 fix:SudokuFX-JVM.desktop: restore menu visibility with absolute paths
- Update Exec to /opt/sudokufx-jvm/bin/SudokuFX-JVM for GNOME validation.
- Update Icon to /opt/sudokufx-jvm/lib/SudokuFX-JVM.png for reliable resource loading.
- Rename Name to SudokuFX-JVM for consistency with jpackage metadata.
- Retain StartupWMClass&#x3D;fr.softsf.sudokufx.SudoMain for Wayland window grouping.
- Ensure Categories&#x3D;Game; for standard application grid indexing.
- Tests pass.

([af019659fc8fd8d](https://github.com/Lob2018/SudokuFX/commit/af019659fc8fd8d3f00c740cff78aae641bea553))

📝 style SudokuFX_SpotBug_GUI_.xml: Run automatic code formatter

([5419e53712af324](https://github.com/Lob2018/SudokuFX/commit/5419e53712af32483a86d9d3008be8183cb7bc36))

🐛 fix linuxRelease.sh, SudokuFX_SpotBug_GUI_.xml: Add SpotBugs GUI run configuration and fix metadata path resolution
- SudokuFX_SpotBug_GUI_.xml:
    - Add IntelliJ IDEA run configuration to launch SpotBugs GUI.
    - Configure spotbugs:gui goal for interactive static analysis review.
- linuxRelease.sh:
    - Move METADATA_DIR resolution before entering target directory.
    - Fix absolute path resolution using PWD to ensure CI/CD compatibility.
    - Ensure resources are correctly located before jpackage execution.
- Tests pass.

([0c9d4453031a9f1](https://github.com/Lob2018/SudokuFX/commit/0c9d4453031a9f1548da48ff0e0c36f02056a9ff))

🐛 fix linuxRelease.sh: Resolve metadata path using PWD for CI compatibility
- Replace SCRIPT_DIR resolution with PWD-based absolute path.
- Fix &quot;No such file or directory&quot; error during Maven/GitHub Actions execution.
- Ensure METADATA_DIR remains valid after directory traversal.
- Tests pass.

([609a26d92fd1b60](https://github.com/Lob2018/SudokuFX/commit/609a26d92fd1b60bb4e679d59993f0eab8276295))

🛠️ build pom.xml: Bump version from 1.4.3 to 1.4.4

([ff6ade06b406df1](https://github.com/Lob2018/SudokuFX/commit/ff6ade06b406df13436a1a36ed791f2c323bdc91))

♻️ refactor GridViewModel.java: Secure victory audio playback
- Extract songPath variable to improve readability.
- Add blank check on songPath to prevent unnecessary processing and path validation errors.
- Clean up audio playback logic within celebrateVictory method.
- Tests pass.

([113eacd61d05269](https://github.com/Lob2018/SudokuFX/commit/113eacd61d052692d95874ba5c9c585ffe3ff69c))

✨ feat SudokuFX-JVM.desktop, linuxRelease.sh: Add custom Linux metadata support
- SudokuFX-JVM.desktop:
	- Create desktop entry with StartupWMClass to fix taskbar window grouping.
	- Set application category to Game.
- linuxRelease.sh:
	- Resolve METADATA_DIR using absolute SCRIPT_DIR path for portability.
	- Integrate resource-dir in jpackage command to override default desktop template.
	- Add descriptive echo logs for metadata path resolution tracking.
- Tests pass.

([8761c47c08d3fe0](https://github.com/Lob2018/SudokuFX/commit/8761c47c08d3fe0dd33a658f6a5fcf300724b671))

## v1.4.3 - 2026-03-05


📚 docs Doxygen

([595ccac09387e11](https://github.com/Lob2018/SudokuFX/commit/595ccac09387e11b0d7464f09f1bd451e71f8cc1))

🔧 chore flyway-database-hsqldb, checkstyle: Bump the maven-dependencies

([d4c05c20c839fc4](https://github.com/Lob2018/SudokuFX/commit/d4c05c20c839fc42d64e8f91a285ae8bc4978470))

🔧 chore harden-runner, upload-artifact, download-artifact: Bump the actions-dependencies

([22b7485eab1a99e](https://github.com/Lob2018/SudokuFX/commit/22b7485eab1a99e036dbf35896e7f5f818286ae9))

Merge branch &#x27;main&#x27; into develop

([f2515027ad69355](https://github.com/Lob2018/SudokuFX/commit/f2515027ad69355d8ead3b98c91bdba33b998f73))

Fix badge link case sensitivity in README.md

([c00d8a2fdf39410](https://github.com/Lob2018/SudokuFX/commit/c00d8a2fdf394104d347a0898f954a814a2111ef))

🔧 chore spring-boot-starter-parent, flyway-database-hsqldb, maven-surefire-plugin: Bump the maven-dependencies

([b840fcdeebe305d](https://github.com/Lob2018/SudokuFX/commit/b840fcdeebe305dab5aacc4ae70e6ee7328a219f))

🔧 chore codeql-action, dependency-review-action: Bump the actions-dependencies

([f69b2d6e1bf0759](https://github.com/Lob2018/SudokuFX/commit/f69b2d6e1bf075928dadc4a20c2227b929e29058))

✨ feat pom.xml: Bump project version from 1.4.2 to 1.4.3

([31003a8839c372b](https://github.com/Lob2018/SudokuFX/commit/31003a8839c372b319d1d26fbefccd832875f9bf))

## v1.4.2 - 2026-02-22


✨ feat pom.xml: Bump project version to 1.4.2

([ac7bac3113e6ae5](https://github.com/Lob2018/SudokuFX/commit/ac7bac3113e6ae5afe805f2acd99fdb1049e6c7e))

⚡ perf Firework: Changes frame rate from 72 to 60 fps

([28ff06cc8b0a3b6](https://github.com/Lob2018/SudokuFX/commit/28ff06cc8b0a3b675cc52e6901d2b765339d73a7))

## v1.4.1 - 2026-02-22


✨ feat pom.xml: Bump project version to 1.4.1

([27f0de8242a05d2](https://github.com/Lob2018/SudokuFX/commit/27f0de8242a05d2e24a0bcc9799d758f714dd5c6))

⚡ perf Firework: Implement frame rate capping at 72 FPS
- Firework:
	- Add NS_PER_FRAME constant for 72 FPS timing.
	- Add lastUpdate field to track frame intervals.
	- Reset lastUpdate in firingProperty invalidation to ensure immediate start.
	- Update AnimationTimer handle method to throttle logic and rendering based on elapsed time.
- Tests pass.

([e5540e7198f1690](https://github.com/Lob2018/SudokuFX/commit/e5540e7198f1690a2db471567a6c0cb7677d2942))

🛠️ build .sdkmanrc, pom.xml: Fix Java executable resolution for JavaFX plugin
- .sdkmanrc:
	- Add java and maven version pinning.
- pom.xml:
	- Add executable property to javafx-maven-plugin configuration using java.home to prevent execution error 2.
- Tests pass.

([865964952c66d04](https://github.com/Lob2018/SudokuFX/commit/865964952c66d047168317be6eaa5b6f163d1942))

## v1.4.0 - 2026-02-21


✨ feat pom.xml: Bump project version to 1.4.0
- Tests pass.

([4360debf142c359](https://github.com/Lob2018/SudokuFX/commit/4360debf142c359e89c658047ae00b1ccde6470a))

🐛 fix HelpViewModelUTest: Ensure OS independence and PathValidator compliance
- HelpViewModelUTest:
    - Integrate JUnit @TempDir to manage a localized temporary file system for tests.
    - Implement physical log file creation using Files.createFile to satisfy PathValidator checks.
    - Apply Mockito.spy on IOsFolder to redirect log path resolution to the temporary directory.
    - Add @BeforeEach setUp method for consistent mock and service initialization.
    - Sanitize log filename resolution to prevent absolute path hijacking during test execution.
- Tests pass.

([3bccaebe3d427b0](https://github.com/Lob2018/SudokuFX/commit/3bccaebe3d427b00fa739b2064434ab9e10a2d91))

📚 docs Doxygen

([9cdfe1906dd0301](https://github.com/Lob2018/SudokuFX/commit/9cdfe1906dd030108416444e1663a63524f17016))

♻️ refactor style.css, SudokuFX_in_action.jpg: Update grid cell background color and demonstration assets
- Tests pass

([3029e6dac7798a8](https://github.com/Lob2018/SudokuFX/commit/3029e6dac7798a81be1e22acb6720b0497514485))

♻️ refactor pom.xml, GridMaster, LevelInteractionHandler, GridViewModel, GridViewModelUTest, LevelInteractionHandlerUTest: Implement asynchronous grid generation and UI synchronization
- pom.xml:
    - Exclude GridViewModel anonymous classes from JaCoCo coverage reports to avoid noise from Task implementations.
- GridMaster:
    - Add interruption check in generation loop to support Task cancellation and clean thread termination.
- LevelInteractionHandler:
    - Replace synchronous grid generation with setCurrentGridTask to offload heavy computation.
    - Implement Task lifecycle management including cancellation of pending tasks and daemon thread execution.
    - Add SLF4J logging for failed level applications.
- GridViewModel:
    - Introduce setCurrentGridTask for asynchronous generation and applyGeneratedGrid for UI-thread safe state updates.
    - Rename synchronous generation to setCurrentGridWithLevelForTests to clarify its role in Unit Testing.
    - Inject SpinnerService to manage loading state during background processing.
    - Add generatingProperty for UI spinner bindings.
    - Define LEVEL_MUSTN_T_BE_NULL constant for standardized validation.
- GridViewModelUTest:
    - Inject spinnerServiceMock and update setup to reflect GridViewModel constructor changes.
    - Rename test cases to match the updated synchronous testing method.
- LevelInteractionHandlerUTest:
    - Stub setCurrentGridTask with a mock Task to prevent NullPointerException.
    - Update verification logic to focus on task triggering instead of direct synchronous execution.
- Tests pass.

([109f90972a1a4fa](https://github.com/Lob2018/SudokuFX/commit/109f90972a1a4faacbea509b6394f02b1361a013))

🐛 fix VersionService: Handle explicit network IOExceptions for version check
- VersionService:
	- Add IOException catch block to distinguish immediate network infrastructure failures from timeouts.
	- Log network error with full stack trace using ex.
	- Update Task message with localized network error feedback.
- Tests pass.

([a421bf77114f9a8](https://github.com/Lob2018/SudokuFX/commit/a421bf77114f9a8b31d8aaf9cc434828ebfcb1b2))

✨ feat BenchGridMaster, GridMaster, GrillesCrees, IGridMaster, LevelInteractionHandler, GridViewModel, resource_en_US.properties, resource_fr_FR.properties, GridMasterUTest, GrillesCreesUTest: Support targeted difficulty percentage in grid generation
  - BenchGridMaster:
    - Update measureCreerLesGrilles call with default parameter.
  - GridMaster:
    - Update genererLaGrilleAResoudre and creerLesGrilles to handle targeted percentage.
    - Implement dynamic IntPredicate logic to validate difficulty ranges.
    - Update Javadoc to document fail-safe behavior and new parameters.
  - GrillesCrees:
    - Update Javadoc to include -1 as a valid failure state for percentage.
    - Change Min annotation constraint from 0 to -1.
  - IGridMaster:
    - Add default method getPossibilitesDepuisPourcentage for difficulty calculation.
    - Update creerLesGrilles method signature and Javadoc.
  - LevelInteractionHandler:
    - Add early return in applyLevel when percentage is -1.
    - Update Javadoc to document process abortion on generation failure.
  - GridViewModel:
    - Pass desiredPossibilities constant to the grid master.
    - Integrate toasterService to display localized failure message on -1 result.
  - resource_en_US.properties:
    - Add toast.msg.levelInteractionHandler.desiredpossibilities.failed constant.
  - resource_fr_FR.properties:
    - Add localized failure message constant.
  - GridMasterUTest:
    - Update test calls to include the new percentage parameter.
  - GrillesCreesUTest:
    - Update givenPourcentageDesPossibilitesBelowMinusOne_whenValidate_thenViolation to test with -2.
  - Tests pass.

([2db8cb0a52ba595](https://github.com/Lob2018/SudokuFX/commit/2db8cb0a52ba5958fc128ad176a71eb255438c21))

♻️ refactor MyRegex, SecureRandomGenerator, JVMApplicationProperties, JVMApplicationPropertiesUTest, GenerateSecretUTest, MyRegexUTest, SecureRandomGeneratorUTest: Relocate utility singletons to util package
- Tests pass.

([257c3b88ff16eb9](https://github.com/Lob2018/SudokuFX/commit/257c3b88ff16eb9c9d09548d8a0aa95f9757d349))

📚 docs Doxygen

([e48671fafe4d929](https://github.com/Lob2018/SudokuFX/commit/e48671fafe4d92944630c6373f20af1cf214a180))

🐛 fix OsFolderInitializer: Fix directory creation by removing premature existence check
- Removed PathValidator and Path imports.
- Updated initializeFolders Javadoc to specify FolderCreationException instead of generic RuntimeException.
- Replaced PathValidator.validateExists with direct File instantiation to allow mkdirs to execute.
- Updated initializeFolders Javadoc.
- Tests pass.

([d77615040eeb8c1](https://github.com/Lob2018/SudokuFX/commit/d77615040eeb8c18a55d78a636fca826ce6f8646))

♻️ refactor LocalUserDataPurger,PathValidator, checkstyle-suppressions.xml, requirements.txt, FileSystemManager, IUserDataPurger, IOsFolder, OsFolderInitializer, FileChooserService, CrashScreenView, SplashScreenView, HelpViewModel, MenuOptionsViewModel, GridViewModel, FileSystemManagerUTest : Centralize path validation and modernize data purge logic
- Created PathValidator enum to centralize fail-fast validation for existence and directory status.
- Renamed IFileSystem to IUserDataPurger and replaced FileSystemManager with LocalUserDataPurger.
- Refactored multiple ViewModels and Services to use PathValidator and java.nio.path instead of manual File existence checks.
- Improved CrashScreenView to validate authorized data scope before recursive deletion.
- Updated HelpViewModel to handle log file button visibility via specific exception catching (IllegalStateException, IllegalArgumentException) with TRACE logging.
- Hardened FileChooserService and OsFolderInitializer by enforcing directory validation through PathValidator.
- Updated requirements.txt dependencies: filelock (3.24.3), platformdirs (4.9.2), virtualenv (20.38.0).
- Adjusted checkstyle-suppressions.xml for MyRegex and updated line mappings for SplashScreen/CrashScreen views.
- Refactored FileSystemManagerUTest into LocalUserDataPurger tests with enhanced log monitoring via logWatcher.
- Tests pass.

([95ec2ce59dd4200](https://github.com/Lob2018/SudokuFX/commit/95ec2ce59dd4200c5ef5c7243d44226e8f789e26))

📚 docs Doxygen

([8e64872d27bef89](https://github.com/Lob2018/SudokuFX/commit/8e64872d27bef89023d3119393e4aabe382aca2c))

📝 style Firework, FireworkParticle: Run automatic code formatter

([6782014406b7d08](https://github.com/Lob2018/SudokuFX/commit/6782014406b7d08af259c300253b91c8076d0c5a))

## v1.3.1 - 2026-02-17


👷 ci release.yml, pom.xml: Bump version to 1.3.1 and enable pre-release status
- pom.xml: Increment project version from 1.3.0 to 1.3.1.
- release.yml: Set prerelease to true in ncipollo/release-action.
- Details: Align version bump with the new &quot;Early Access&quot; distribution strategy.
- Tests pass.

([49007b713a8b83f](https://github.com/Lob2018/SudokuFX/commit/49007b713a8b83f86189d8ca7c078ee04847f174))

📚 docs release.yml, README.md: Update release header and versioning documentation
- release.yml: Add &quot;🧪 Early Access 🚧 Work in Progress&quot; header to GitHub release body.
- README.md: Update placeholder versioning format from v.v.v to v.v.v.v in GPG verification examples.
- README.md: Align execution script examples (.bat, .sh) with the four-digit versioning scheme.
- README.md: Update uninstallation instructions to reflect the new versioning pattern for all platforms.
- Tests pass.

([ed21fecfb2f879b](https://github.com/Lob2018/SudokuFX/commit/ed21fecfb2f879b525b510fd2b51a6b0770ccf90))

🛠️ build Firework, FireworkParticle: Suppress SonarLint pseudorandom warnings
- Firework: Add @SuppressWarnings(&quot;java:S2245&quot;) to constructor and ignite() method.
- FireworkParticle: Add @SuppressWarnings(&quot;java:S2245&quot;) to constructor.
- FireworkParticle: Reorganize imports to follow project standards.
- Details: Acknowledge that ThreadLocalRandom is sufficient for visual effects despite not being cryptographically secure.
- Tests pass

([c6ca41467acae9e](https://github.com/Lob2018/SudokuFX/commit/c6ca41467acae9eed8e101eb59961353a7430915))

⚡ perf Firework, FireworkParticle: Optimize resource consumption and add smooth transition
- Firework: Reduce particle count by half (MINIMUM_PARTICLES to 8, PARTICLE_COUNT_VARIANCE to 10).
- Firework: Increase DEFAULT_COUNTDOWN to 4 to reduce ignition frequency.
- Firework: Adjust REFERENCE_SCREEN_WIDTH to 1000 for scaling metrics.
- Firework: Implement a 1s PauseTransition delay and 1s FadeTransition for smoother appearance.
- Firework: Ensure setVisible(false) and opacity 0.0 during stabilization delay.
- Firework: Add transition recycling by stopping delay/fadeIn in firingProperty.
- FireworkParticle: Halve MIN_DISPLAY_DURATION and VARIANCE to free memory faster.
- FireworkParticle: Reduce BLUR_VARIANCE to 3.0 for better rendering performance.
- Tests pass.

([cc2db9a138a7ac6](https://github.com/Lob2018/SudokuFX/commit/cc2db9a138a7ac60adf22db7c1b70181d2a2dbea))

⚡ perf linuxRelease.sh, macosRelease.sh, windowsRelease.bat, pom.xml: Optimize JVM memory for 4GB RAM devices
- linuxRelease.sh: Reduce heap to 512m/1g and set MaxMetaspaceSize to 192m for DEB package and AppCDS training.
- macosRelease.sh: Apply 512m/1g heap limits and G1GC to macOS scripts (standard and x86_64).
- windowsRelease.bat: Update MSI installer and launcher batch with G1GC and optimized memory boundaries.
- pom.xml: Align visualvm-monitoring profile with the new 512m/1g/192m low-latency memory strategy.
- Project: Enable -XX:+UseG1GC across all release scripts to stabilize UI frame rates.
- Tests pass.

([afa5b8d17389480](https://github.com/Lob2018/SudokuFX/commit/afa5b8d1738948069e64dab1e06e3b2a56e7fc71))

⚡ perf linuxRelease.sh, macosRelease-x86_64.sh, macosRelease.sh, windowsRelease.bat, pom.xml: Standardize high-performance JVM and Prism settings
- linuxRelease.sh: Update jpackage and training java commands with 1g/2g heap and hardware acceleration flags.
- macosRelease.sh: Align macOS scripts (standard and x86_64) with cross-platform Prism order and increased Metaspace.
- windowsRelease.bat: Optimize MSI generation and generated launcher batch with stable memory and VSync.
- pom.xml: Update global jvm.args and visualvm-monitoring profile to ensure consistency during development.
- Tests pass.

([6a4d7bf0b975b6e](https://github.com/Lob2018/SudokuFX/commit/6a4d7bf0b975b6ef3858e366141fdcd090b2c015))

📚 docs: Doxygen

([e8d8595220a547a](https://github.com/Lob2018/SudokuFX/commit/e8d8595220a547aa966f365489dc586ef0e31cb9))

📝 style GridCellViewModel: Run automatic code formatter

([ba09e2e5e36a23b](https://github.com/Lob2018/SudokuFX/commit/ba09e2e5e36a23b00cbb8372619da16df9b85646))

✨ feat Firework, FireworkParticle, MainView, ActiveMenuOrSubmenuViewModel, MenuLevelViewModel, MenuNewViewModel, MenuOptionsViewModel, GridCellViewModel, GridViewModel, Project.xml: Implement victory fireworks and secure ViewModel properties
- Firework: Create reactive Group container for firework animation logic with AnimationTimer.
- FireworkParticle: Implement individual particle entity with physics, gravity, and fade-out logic.
- MainView: Integrate Firework component and bind firingProperty to grid victory state.
- GridViewModel: Add victory ReadOnlyBooleanProperty and trigger celebration logic.
- ActiveMenuOrSubmenuViewModel: Refactor activeMenu to ReadOnlyObjectWrapper.
- MenuLevelViewModel: Refactor selectedLevel to ReadOnlyObjectWrapper.
- MenuNewViewModel: Refactor isOutOfDate and statusMessage to ReadOnlyWrappers.
- MenuOptionsViewModel: Refactor gridOpacity, mute, song, and songIsBlank to ReadOnlyWrappers.
- GridCellViewModel: Refactor id to ReadOnlyIntegerWrapper.
- Project.xml: Update IntelliJ code style to prevent wildcards in package imports.
- Tests pass.

([2b6ac241c8b5874](https://github.com/Lob2018/SudokuFX/commit/2b6ac241c8b5874613f119bba14693d93a77f07e))

🔧 chore misc.xml: Rename project JDK

([0062391864bd5f6](https://github.com/Lob2018/SudokuFX/commit/0062391864bd5f66b3b63987edc314d3e4c5711a))

🔧 chore flyway-database-hsqldb: Bump the maven-dependencies

([ac9050c3adef9ac](https://github.com/Lob2018/SudokuFX/commit/ac9050c3adef9ac90bc8873d32bdeb38227a97f9))

🔧 chore codeql-action : Bump the actions-dependencies

([40f3ff95e0f3773](https://github.com/Lob2018/SudokuFX/commit/40f3ff95e0f37734488d1fd88c4aef19653f9d72))

📚 docs Doxygen

([09f3d90a14b7b75](https://github.com/Lob2018/SudokuFX/commit/09f3d90a14b7b75eb41dab6139568324c7eb3ccb))

✨ feat VersionService, VersionServiceITest: Optimize GitHub version check
- VersionService:
	- Switch from String to InputStream for HttpClient body handling to improve memory efficiency.
	- Implement MAX_TAGS_RESPONSE_SIZE (64KB) and MAX_TAG_NAME_LENGTH (256) safety limits.
	- Replace custom TagDto mapping with direct JsonNode tree parsing to reduce overhead and DTO dependency.
	- Refine logging semantics (differentiate between WARN for empty/invalid data and ERROR for parsing failures).
	- Simplify Javadoc and suppress specialized findbugs warnings for ObjectMapper reference.
- VersionServiceITest:
	- Update all existing tests to use ByteArrayInputStream and adjust mock generic types to HttpResponse&lt;InputStream&gt;.
	- Align log assertions with new production message strings.
	- Add coverage for empty response bodies, excessive tag lengths, and empty normalized names.
	- Add test case for thread interruption handling.
- Tests pass.

([daf508427b58826](https://github.com/Lob2018/SudokuFX/commit/daf508427b5882644512bdba7146a976caa402a3))

👷 ci release.yml: Centralize JVM flags and UTF-8 encoding

([b42dd021c9a0e99](https://github.com/Lob2018/SudokuFX/commit/b42dd021c9a0e9984a906798795712712e8dee06))

♻️ refactor ExceptionTools, MapperUtils, ImageUtils, GridConverter, Options, PlayerLanguage, Coordinator, MenuOptionsViewModel, GridViewModel: Centralize error logging and standardize instantiation
- ExceptionTools :
	- Implemented private logAndInstantiate generic helper to unify &quot;██&quot; prefixed logging.
	- Added logAndInstantiateIllegalArgument with cause support.
	- Added logAndInstantiateIllegalState, logAndInstantiateUnsupportedOperation, and logAndInstantiateUncheckedIO.
	- Refactored findSQLInvalidAuthException for better readability using pattern matching.
- MapperUtils:
	- Updated private constructor to use logAndInstantiateUnsupportedOperation.
- ImageUtils:
	- Migrated manual logging and rethrows to ExceptionTools.
	- Cleaned up redundant Logger instance.
- GridConverter:
	- Refactored listToGridValue with Optional.orElseThrow and ExceptionTools validation.
	- Standardized digit validation exceptions.
- Options, PlayerLanguage, Coordinator, MenuOptionsViewModel, GridViewModel:
	- Replaced manual exception instantiation with standardized ExceptionTools calls.
- Tests pass.

([40292f987b58d77](https://github.com/Lob2018/SudokuFX/commit/40292f987b58d77ad2e671be1102446f2e657ee6))

♻️ refactor requirements.txt, jvm.config, linuxRelease.sh, macosRelease-x86_64.sh, macosRelease.sh, windowsRelease.bat, pom.xml: Centralize JDK 25 JVM configuration and sync release scripts
- requirements.txt: Updated
- jvm.config:
    - Initial creation with global flags for --UTF-8--, --native access--, and --reflective opens-- for java.lang and java.util.
- linuxRelease.sh, macosRelease-x86_64.sh, macosRelease.sh, windowsRelease.bat:
    - Update jpackage command with missing encoding and native access flags.
    - Synchronize --CDS-- training and execution blocks with identical JVM options.
- pom.xml:
    - Define ${jvm.args} property to centralize --JDK 25-- requirements.
    - Inject ${jvm.args} into --maven-surefire-plugin--, --javafx-maven-plugin--, and --spring-boot-maven-plugin--.
- Tests pass.

([db34474346f0887](https://github.com/Lob2018/SudokuFX/commit/db34474346f088765a125c4b67f10f301e2d36ec))

📚 docs: Doxygen

([13ec96b19bb267d](https://github.com/Lob2018/SudokuFX/commit/13ec96b19bb267d8531069de0c31368ce94963cf))

♻️ refactor SudoMain, GrilleResolue, ApplicationKeystore, SecretKeyEncryptionServiceAESGCM, Coordinator, CrashScreenView, MyAlert, MenuOptionsViewModel, MenuPlayerViewModel, GridViewModel, VersionService, CoordinatorUTest: Improve exception safety, remove obsolete suppressions, harden UI rendering, and update tests (SpotBugs)
- SudoMain
  - Add suppression for UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR on stage
  - Replace unsafe cast with instanceof pattern matching
  - Improve SQL authorization logging logic
- GrilleResolue
  - Add suppression for DLS_DEAD_LOCAL_STORE on record pattern matching in equals
- ApplicationKeystore
  - Add justified REC_CATCH_EXCEPTION suppressions for keystore write, load, and credential retrieval
- SecretKeyEncryptionServiceAESGCM
  - Add justified REC_CATCH_EXCEPTION suppressions for AES‑GCM encrypt/decrypt
- Coordinator
  - Remove obsolete suppression on defaultScene
  - Strengthen initialization checks for defaultScene and fxmlLoader
  - Remove dedicated NullPointerException catch
  - Consolidate exception handling into a single safe catch block
  - Update Javadoc to reflect new initialization and error‑handling rules
- CrashScreenView
  - Add suppression for UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR on iSplashScreenView
  - Replace safeDivide with direct division
  - Enforce minimum stroke width for stable SVG rendering
- MyAlert
  - Replace unsafe cast with instanceof pattern matching for window → stage
- MenuOptionsViewModel
  - Harden filename extraction with null/blank safety
- MenuPlayerViewModel
  - Remove obsolete suppression on exposed JavaFX property
- GridViewModel
  - Add justified REC_CATCH_EXCEPTION suppression for UI victory handling
- VersionService
  - Add justified REC_CATCH_EXCEPTION suppression for JSON parsing
- CoordinatorUTest
  - Update test to reflect new exception handling and log message
- Tests pass.

([b2450363b2c33b9](https://github.com/Lob2018/SudokuFX/commit/b2450363b2c33b9561ab817e8b0a2eb920b2448a))

♻️ refactor SudoMain, Game, Player, Coordinator, VersionService, AsyncFileProcessorService, ToasterService, CrashScreenView, SplashScreenView, PossibilityStarsHBox, SpinnerGridPane, GenericDtoListCell, LevelInteractionHandler, MainView, ActiveMenuOrSubmenuViewModel, HelpViewModel, MenuHiddenViewModel, MenuLevelViewModel, MenuMaxiViewModel: Add consistent SpotBugs suppressions across JavaFX, Spring and JPA
- SudoMain
  - Add EI_EXPOSE_REP2 suppression on stage field with JavaFX lifecycle justification.
- Game
  - Add EI_EXPOSE_REP suppressions on JPA getters (getGridid, getPlayerid, getLevelid) to preserve persistence semantics.
- Player
  - Add EI_EXPOSE_REP suppressions on JPA getters (getPlayerlanguageid, getOptionsid, getMenuid, getGames) to avoid breaking ORM behavior.
- Coordinator
  - Add EI_EXPOSE_REP2 suppression on constructor for FXMLLoader.
  - Add EI_EXPOSE_REP suppression on getDefaultScene.
  - Add EI_EXPOSE_REP2 suppression on setDefaultScene for JavaFX Scene reference handling.
- VersionService
  - Add EI_EXPOSE_REP2 suppression on constructor for Spring-managed ObjectMapper.
- AsyncFileProcessorService
  - Add EI_EXPOSE_REP2 suppression on constructor for Spring-managed services.
- ToasterService
  - Add EI_EXPOSE_REP suppressions on JavaFX property getters for binding and listener exposure.
- CrashScreenView
  - Add UPM_UNCALLED_PRIVATE_METHOD suppressions on FXML-invoked private handlers.
- SplashScreenView
  - Add EI_EXPOSE_REP2 suppression on constructor for JavaFX Stage.
  - Add EI_EXPOSE_REP suppression on getSplashScreenScene.
- PossibilityStarsHBox
  - Add EI_EXPOSE_REP suppression on getPercentage for JavaFX property exposure.
- SpinnerGridPane
  - Add EI_EXPOSE_REP suppression on loadingProperty.
- GenericDtoListCell
  - Add EI_EXPOSE_REP2 suppression on constructor for ListView reference.
- LevelInteractionHandler
  - Add EI_EXPOSE_REP2 suppression on constructor for Spring ViewModels.
- MainView
  - Add EI_EXPOSE_REP2 suppression on constructor for Spring-managed singletons.
  - Add UPM_UNCALLED_PRIVATE_METHOD suppressions on FXML lifecycle handlers.
- ActiveMenuOrSubmenuViewModel
  - Add EI_EXPOSE_REP suppression on JavaFX property getter.
- HelpViewModel
  - Add EI_EXPOSE_REP2 suppression on constructor for injected infrastructure services.
- MenuHiddenViewModel
  - Add EI_EXPOSE_REP suppression on accessibility binding getter.
- MenuLevelViewModel
  - Add EI_EXPOSE_REP suppressions on JavaFX property getters.
- MenuMaxiViewModel
  - Add EI_EXPOSE_REP suppressions on JavaFX binding getters.
- Tests pass.

([e55f08a37dd6025](https://github.com/Lob2018/SudokuFX/commit/e55f08a37dd60250662531a14334a5d9cc7812f4))

📚 docs Doxygen

([d46d34e14acced9](https://github.com/Lob2018/SudokuFX/commit/d46d34e14acced9417a8d39057dd60f87a4a3e7e))

♻️ refactor GridMasterUTest, GrillesCreesUTest: Useless comments

([32cce80db517d75](https://github.com/Lob2018/SudokuFX/commit/32cce80db517d75f953aea0e48de325d180a25d3))

♻️ refactor I18n, GrilleResolue, GrillesCrees, ViewModelsUTest: Secure data records and restrict Locale property access (SpotBugs)
- I18n:
	- Change Locale property to ReadOnlyObjectProperty using ReadOnlyObjectWrapper.
	- Update Javadoc for localeProperty method.
- GrilleResolue:
	- Add canonical constructor with fail-fast validation for grid array integrity.
	- Implement defensive cloning in constructor and accessor to prevent representation exposure.
	- Implement manual equals, hashCode, and toString to handle array content.
	- Add CASES_NUMBER constant and Javadoc documentation.
- GrillesCrees:
	- Add canonical constructor with fail-fast validation for multiple grid arrays.
	- Implement defensive cloning in constructor and accessors.
	- Implement manual equals, hashCode, and toString to handle array contents.
	- Add GRID_SIZE constant and Javadoc documentation.
- MenuMiniViewModelUTest, MenuNewViewModelUTest, MenuPlayerViewModelUTest, MenuSaveViewModelUTest, MenuSolveViewModelUTest, MenuMaxiViewModelUTest, MenuOptionsViewModelUTest:
	- Update locale access to use getValue instead of get.
	- Refactor tearDown logic to use setLocaleBundle for state restoration.
- GrilleResolueUTest, GrillesCreesUTest:
	- Update tests to verify IllegalArgumentException throwing during instantiation.
	- Adjust assertion messages to match new validation logic.
- Tests pass.

([b30cdbd1b93e796](https://github.com/Lob2018/SudokuFX/commit/b30cdbd1b93e7966669556d70035e1f9a78cf893))

♻️ refactor MyLogbackConfig, Coordinator, PlayerStateHolder, ApplicationKeystore, SecretKeyEncryptionServiceAESGCM, Options, PlayerLanguage, SpringContextInitializer, PossibilityStarsHBox, GridCellViewModel, pom.xml, GridViewModelUTest, CoordinatorUTest, MenuPlayerViewModelUTest, MenuLevelViewModelUTest, AbstractPlayerStateTest: Fix SpotBugs violations and JEP 482 integration
- MyLogbackConfig, Coordinator, PlayerStateHolder: Move initialization logic to @PostConstruct to resolve CT_CONSTRUCTOR_THROW.
- Coordinator: Use Coordinator.class.getResource() to fix UI_INHERITANCE_UNSAFE_GETRESOURCE and add configuration validation.
- ApplicationKeystore, SecretKeyEncryptionServiceAESGCM: Enforce StandardCharsets.UTF_8 in getBytes() to fix RV_DONT_JUST_CONVERT_STRING_TO_BYTE_ARRAY.
- Options, PlayerLanguage: Implement JEP 482 statements before super() for field validation and update Javadoc.
- SpringContextInitializer, PossibilityStarsHBox, GridCellViewModel: Mark classes and methods as final to prevent inheritance issues.
- pom.xml: Add excludes for model classes to bypass parser crash on JEP 482 syntax.
- GridViewModelUTest, CoordinatorUTest, MenuPlayerViewModelUTest, MenuLevelViewModelUTest, AbstractPlayerStateTest: Update tests to handle manual @PostConstruct invocation and remove stubs.
- Tests pass.

([ed71a1602d280d7](https://github.com/Lob2018/SudokuFX/commit/ed71a1602d280d747879cf2b0e2606a84ffdbe8a))

♻️ refactor AudioService, AudioServiceUTest: Improve MediaPlayer lifecycle and playback initialization
- AudioService
	- Rename player to localEffectPlayer and declare it final for sound effects.
	- Rename songPlayer to localSongPlayer during initialization to ensure thread safety and finality.
	- Use setOnReady to trigger playback for both effects and background music to ensure the media is fully loaded.
	- Update effectsPlayers and songPlayer references to use the improved local variables.
- AudioServiceUTest
	- Add triggerOnReady helper method to extract and run the setOnReady callback from mocks.
	- Update PlayEffectTests and PlaySongTests to verify play() call after triggering the ready state.
	- Fix TooFewActualInvocations in givenSameEffectKeyPlayedTwice_whenPlayEffect_thenPlayerReusedAndRestarted by ensuring the first asynchronous play() is executed before the second synchronous call.
- Tests pass.

([e0b8532dd709006](https://github.com/Lob2018/SudokuFX/commit/e0b8532dd7090068988b549d045e080cd4fe1d8e))

♻️ refactor OsName, AudioUtils, ImageUtils, OsInfoUTest: Use Locale.ROOT for case conversion

([b0f02d1dd585807](https://github.com/Lob2018/SudokuFX/commit/b0f02d1dd58580718c30fdade4731f68e02addf4))

📚 docs Doxygen

([9b3c412db7be45a](https://github.com/Lob2018/SudokuFX/commit/9b3c412db7be45ac2d7d5347de83ef5f9c6a0178))

♻️ refactor ApplicationKeystore: Comment out sensitive debug logs

([9dda349833c0508](https://github.com/Lob2018/SudokuFX/commit/9dda349833c05081bec587a8077e2142dd76d198))

🛠️ build logback.xml: Fix log rotation by removing invalid sub-directory pattern
- Replaced directory-style /app. prefix with flat .archived. suffix in fileNamePattern.
- Fixed rotation failure caused by Logback&#x27;s inability to create or access the archived sub-folder.
- Ensured archives are stored in the same dynamic directory as the active ${logs} file.
- Tests pass.

([f2db03b156d78e9](https://github.com/Lob2018/SudokuFX/commit/f2db03b156d78e93dda20bf9503aca4b4dab20e0))

♻️ refactor VersionService, MyRegex, VersionService, pom.xml: Implement hybrid 4-digit versioning and simplify comparison
- Project: Bumped version to 1.3.0 in pom.xml.
- MyRegex:
	- Updated VERSION_PATTERN to support both X.Y.Z and X.Y.Z.W formats.
	- Added segment length constraints (1-9 digits) to prevent Integer overflow.
	- Optimized with a non-capturing group for the optional fourth segment.
- VersionService:
	- Replaced complex compareVersions logic with a strict String.equals() check for version matching.
	- Added a 1MB payload limit and null check on GitHub response body for memory safety.
	- Refactored CURRENT_VERSION and parseResponse to use replaceFirst(&quot;^v&quot;, &quot;&quot;) for cleaner prefix removal.
	- Improved asynchronous Task lifecycle management and logging.
	- Simplified exception handling and removed unused imports (StringUtils, IOException).
- Documentation: Updated Javadoc for VersionService and MyRegex to reflect 4-digit support.
	- Tests:
	- Added parameterized tests in JVMApplicationPropertiesUTest for valid/invalid version formats.
	- Updated VersionServiceITest to simulate 4-digit version transitions and matching logic.
- Tests pass.

([db4b3520b4dcabe](https://github.com/Lob2018/SudokuFX/commit/db4b3520b4dcabe05b606af9f3d6bb9301dfa096))

♻️ refactor VersionService.java, AsyncFileProcessorService.java, MenuOptionsViewModel.java, SpringContextInitializerUTest.java, MenuOptionsViewModelUTest.java: Standardize Task lifecycle and async testing
- Refactor VersionService and AsyncFileProcessorService to use Task lifecycle overrides (succeeded, failed, done) instead of manual Platform.runLater and try-finally blocks.
- Relocate spinnerService.stopLoading() to done() to ensure robust UI cleanup across all task states.
- Implement CountDownLatch with 2s timeout in unit tests to synchronize with asynchronous Platform.runLater calls.
- Assert latch.await() return values to prevent silent synchronization failures and satisfy static analysis.
- Clean up Javadoc and remove redundant imports.
- Tests pass.

([f79e1747231bf7d](https://github.com/Lob2018/SudokuFX/commit/f79e1747231bf7d02c5ace3f672651deaab5930c))

♻️ refactor ToasterService: Internalize thread-safety for toast notifications

([a8f2d2f5a501153](https://github.com/Lob2018/SudokuFX/commit/a8f2d2f5a501153ff6e7592c4b48a8c56707c87e))

✨ feat VersionService: Integrate SpinnerService for visual feedback
 - Added SpinnerService dependency and updated constructor.
 - Integrated startLoading() and stopLoading() within the Task&#x27;s lifecycle using a try-finally block.
 - Update tests.
 - Tests pass.

([cd6b113c91cdb7a](https://github.com/Lob2018/SudokuFX/commit/cd6b113c91cdb7a1f02d07fce234b45bcc6130af))

♻️ refactor SpinnerService: Ensure thread-safe UI updates

([98174b498e2b5b1](https://github.com/Lob2018/SudokuFX/commit/98174b498e2b5b183c79cafd4033f153b6a460e9))

♻️ refactor MenuNewViewModel: Rename version check task for clarity
- Tests Pass

([e1cef5318a840c8](https://github.com/Lob2018/SudokuFX/commit/e1cef5318a840c8cbf0c2fa007a9216114ac8161))

♻️ refactor AsyncFileProcessorService: Implement robust task orchestration and lifecycle management
- Extract fileProcessingTask factory method to encapsulate Task configuration
- Wrap UI cleanup in try-finally block to ensure deterministic spinner termination
- Add setOnCancelled handler with info logging for task interruptions
- Update Javadoc to detail lifecycle and threading responsibilities
- Rename callback parameter to successCallback
- Add @NonNull annotation
- Tests pass.

([455a4e278423a98](https://github.com/Lob2018/SudokuFX/commit/455a4e278423a98d4c3efb5f68d878ce723e233e))

✨ feat AsyncFileProcessorService, SpinnerService, SpinnerGridPane, MainView, MenuOptionsViewModel: Refactor global spinner management with reactive binding
- Implement SpinnerService with thread-safe AtomicInteger reference counting
- Update SpinnerGridPane to use reactive BooleanProperty with invalidated() override
- Bind MainView spinner visibility to SpinnerService state
- Decouple ViewModels and AsyncFileProcessorService from SpinnerGridPane UI component
- Update unit tests to reflect service injection and correct argument indexing
- Tests pass.

([6acc69a3a41cd53](https://github.com/Lob2018/SudokuFX/commit/6acc69a3a41cd53c457fbe169f5ea6ee06fd6bea))

♻️ refactor resource_fr_FR.properties, resource_en_US.properties: Removes (not guaranteed)for toast.msg.levelInteractionHandler.desiredpossibilities

([74a03a3c527f74c](https://github.com/Lob2018/SudokuFX/commit/74a03a3c527f74c0e966654eff8048edf1d4735f))

♻️ refactor GridMaster, GridViewModel: Relocate fields and reformat documentation
- Tests pass

([d11ab258940a4d6](https://github.com/Lob2018/SudokuFX/commit/d11ab258940a4d6b9c23442d1b6efd30cb5bf672))

✨ feat GridViewModel, GridMasterUTest, GridViewModelUTest: Expose and validate difficulty segment logic
- Add getDesiredPossibilities to GridViewModel to expose the current difficulty percentage.
- Add comprehensive tests in GridMasterUTest for percentage clamping and level bounds resolution.
- Add unit tests in GridViewModelUTest to validate cyclical increments across all difficulty levels (EASY, MEDIUM, DIFFICULT).
- Add test for notifyLevelPossibilityBounds to verify UI notification dispatching.
- Tests pass.

([a99f6d0f934cf38](https://github.com/Lob2018/SudokuFX/commit/a99f6d0f934cf38aadab6854a21b1abc9b06cd6b))

♻️ refactor GridMaster, IGridMaster, GridViewModel, LevelInteractionHandler, LevelPossibilityBounds: Centralize difficulty constants and segment logic
- Migrate all difficulty constants (MIN/MAX possibilities, percentages, and hidden cell counts) from GridMaster to IGridMaster.
- Implement getIntervallePourcentageNiveau to resolve level-specific boundaries.
- Implement calculerValeurSuperieureDuSegment to handle cyclical increment predictions.
- Refactor GridViewModel to delegate difficulty calculations to IGridMaster.
- Simplify LevelInteractionHandler by removing ToasterService dependency and delegating notifications.
- Tests pass.

([5b733fda8989d63](https://github.com/Lob2018/SudokuFX/commit/5b733fda8989d63a05a246ed13c9a01410db7154))

✨ feat LevelInteractionHandler: Ensure previous toast removal before update

([b18ae291fa943eb](https://github.com/Lob2018/SudokuFX/commit/b18ae291fa943ebf222af29d13feb00b767dae9f))

♻️ refactor LevelInteractionHandler: Relocate to main view package to enhance feature cohesion by aligning interaction logic with its specific view.

([0d577b7d88115c5](https://github.com/Lob2018/SudokuFX/commit/0d577b7d88115c5bdae58cb4576e68de4091fb50))

♻️ refactor LevelInteractionHandler, GridViewModel, resource_en_US.properties, resource_fr_FR.properties: Internationalize possibilities toast and clean up logs

([affcb8aedb17a48](https://github.com/Lob2018/SudokuFX/commit/affcb8aedb17a489147a96049a7a2ec5ae9c2a31))

♻️ refactor LevelInteractionHandler: Reduce increment interval from 1s to 500ms

([c1de72e47032b31](https://github.com/Lob2018/SudokuFX/commit/c1de72e47032b31b4e890e823e20f2d605146e6e))

✨ feat MainView, LevelInteractionHandler, GridViewModel, main-view.fxml, LevelInteractionHandler: Start to implement cyclical level generation and refactor level selection logic
- MainView
	- Replace individual handleXxxLevelShow methods with a generic handleLevelAction dispatcher.
	- Add LevelInteractionHandler dependency.
	- Annotate private event handlers with @FXML for consistency.
- LevelInteractionHandler
	- Create new specialized handler for level interactions.
	- Implement iterative selection cycle using a JavaFX Timeline.
	- Add pattern matching for InputEvent (MouseEvent/KeyEvent) to distinguish between cycle start and application.
	- Resolve button IDs to DifficultyLevel.
- GridViewModel
	- Add desiredPossibilities state with increment and reset logic.
	- Introduce DESIRED_POSSIBILITIES_STEP and MAXIMUM_DESIRED_POSSIBILITIES_VALUE constants.
	- Start to update setCurrentGridWithLevel to utilize the desiredPossibilities state during grid generation.
- main-view.fxml
	- Bind level buttons to handleLevelAction for all relevant mouse and keyboard events.
- LevelInteractionHandlerUTest :
	- Add unit tests for the new interaction handler validating cycle start, level application, and exception handling.
- Tests
	- Pass.

([d0b8f4bfaf301d6](https://github.com/Lob2018/SudokuFX/commit/d0b8f4bfaf301d6fceb2ad68124b82afbd350afe))

♻️ refactor main-view.fxml: Update JavaFX namespace version

([9d796c4a9a8f1bd](https://github.com/Lob2018/SudokuFX/commit/9d796c4a9a8f1bd69593dbae95e14d9157c3a6c4))

📝 style SpinnerGridPane: Run automatic code formatter

([6e6205ef3cef3c7](https://github.com/Lob2018/SudokuFX/commit/6e6205ef3cef3c759377a5b3e1d07bd97b54e6e1))

✨ feat SpinnerGridPane: Implement concurrent request tracking and smooth transitions

([20d76a5815e3cd9](https://github.com/Lob2018/SudokuFX/commit/20d76a5815e3cd9dc213cc12f7b9c4ae8e7f9b41))

🔧 chore checkstyle: Bump the maven-dependencies

([8d5f567987760e8](https://github.com/Lob2018/SudokuFX/commit/8d5f567987760e8ab917e9d5fe62a307d08126cb))

🔧 chore harden-runner, codeql-action: Bump the actions-dependencies

([e0090c834e8595f](https://github.com/Lob2018/SudokuFX/commit/e0090c834e8595fb0cf1e607149c60bfac915dbe))

♻️ refactor GridMaster, IGridMaster: Realign difficulty thresholds with sample statistics
- Constants: Updated MAX_POSSIBILITES to 33000, moyenMinPossibilites to 10500, and moyenMaxPossibilites to 12500.
- Documentation: Updated IGridMaster Javadoc to reflect the new masking ranges (34-39, 39-44, 44-48) and their corresponding practical entropy scales.
- Performance: Optimized thresholds ensure a high acceptance rate for the stochastic generator, preventing unnecessary recursion and fail-safe triggers.
- Cleanup: Reorganized internal constant declarations and standardized Javadoc indentation.
- Tests pass.

([c4b7f06e86f01f9](https://github.com/Lob2018/SudokuFX/commit/c4b7f06e86f01f9a4f1415814e44ff9721ff308b))

📚 docs GridMaster: Update documentation to reflect recursive generation and fail-safe logic

([02987f01ce8f059](https://github.com/Lob2018/SudokuFX/commit/02987f01ce8f059b827583c9982df1341de384af))

♻️ refactor GridMaster: Rewrite grid generation logic with watchdog and recursive retry
- Architecture: Refactored logic to support future non-blocking execution within asynchronous tasks.
- Constants: Removed FACILE_MOY_CACHEES, MOYEN_MOY_CACHEES, DIFFICILE_MOY_CACHEES, DUREE_MAXIMALE_POUR_MASQUE_PRECIS, and DUREE_MAXIMALE_POUR_MASQUE_ALEATOIRE.
- Thresholds: Updated hiding ranges and added DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS (300ms) and MAX_ESSAIS_POUR_GENERATION_DE_GRILLE (10).
- Logic: Implemented a recursive approach for genererGrilleAvecCasesCachees using a System.currentTimeMillis() watchdog.
- Safety: Added a fail-safe that clears the grid (Arrays.fill) upon reaching max attempts or critical failure.
- Cleanup: Removed java.time imports and derniereDemande state; updated Javadoc to reflect stochastic search and uniqueness guarantees.
- Tests: Aligned GridMasterUTest with the new stochastic generation and fail-safe behavior.
- Tests pass.

([5c110aac0acb879](https://github.com/Lob2018/SudokuFX/commit/5c110aac0acb879cc618835d722a6ae23023a905))

✨ feat pom.xml: Add SpotBugs

([2e349b429869b23](https://github.com/Lob2018/SudokuFX/commit/2e349b429869b2386bd39e3cfb84704c3cec1172))

♻️ refactor SudokuFX__debug_.xml: Maven options are inherit from settings

([fa1861ab4de86f3](https://github.com/Lob2018/SudokuFX/commit/fa1861ab4de86f3295575a53cbfdd3e6dda8a9aa))

🐛 fix GridMaster: Satisfy SonarQube S135 and refactor neighbor logic

([bcf5de13d4f03fc](https://github.com/Lob2018/SudokuFX/commit/bcf5de13d4f03fcf1ab3f972822f622f53c7b12e))

✨ feat GridMaster: Implement exhaustive uniqueness validation via backtracking
- Added verifierUnicite method implementing backtracking with MRV (Minimum Remaining Values) heuristic and bitwise constraint propagation.
- Integrated uniqueness check into genererGrilleAvecCasesCachees to ensure a single mathematical solution.
- Updated Javadoc to document absolute uniqueness guarantee and adaptive threshold logic.
- Implemented state isolation using array cloning for recursive validation branches.
- Added short-circuit logic capped at 2 solutions for performance optimization.
- Tests pass.

([a933b85bf6662b8](https://github.com/Lob2018/SudokuFX/commit/a933b85bf6662b88b1a0e7fe8e152de65510dde9))

🐛 fix run configurations: JDWP agent conflict and cleanup Maven settings

([68ed1a57d62fe3e](https://github.com/Lob2018/SudokuFX/commit/68ed1a57d62fe3ed665a06017434f851154f2629))

🐛 fix codeql.yml: Correct action call for analysis step

([6e0daaeb689c643](https://github.com/Lob2018/SudokuFX/commit/6e0daaeb689c6433fb05d6d995d6e4c258db051e))

👷 ci: Fix executable permission for mvnw

([b07f45ed3a3a48e](https://github.com/Lob2018/SudokuFX/commit/b07f45ed3a3a48e5160e9c19c87b0ea324650b32))

🐛 fix qodana_code_quality.yml: Correct yaml syntax for checkout step

([6df33d3404175ff](https://github.com/Lob2018/SudokuFX/commit/6df33d3404175ffea8e9baec173dbb1cd376a27c))

👷 ci qodana_code_quality.yml: Refine checkout strategy with conditional ref

([743b4e2505d3a73](https://github.com/Lob2018/SudokuFX/commit/743b4e2505d3a731cb3be5a121d8ed8bccaf9ed9))

🔧 chore pom.xml: Bump the maven-dependencies group

([a1c69dae4557757](https://github.com/Lob2018/SudokuFX/commit/a1c69dae4557757d0e4c676173314fb92097e320))

🐛 fix Launcher, GridMaster: Fix utility class pattern and optimize neighbor logic

([e501a8d4dca3540](https://github.com/Lob2018/SudokuFX/commit/e501a8d4dca354090d9a04b7039fd62c0c57da0d))

🔧 chore CI/CD codeql-action cache : Update

([1f6139d626a7428](https://github.com/Lob2018/SudokuFX/commit/1f6139d626a7428836562d026de661ad36994b3c))

🔧 chore .pre-commit-config.yaml, requirements.txt: Define .venv lifecycle and update dependencies

([9341dac731283de](https://github.com/Lob2018/SudokuFX/commit/9341dac731283de479c0646636778cf27d62b7ec))

📚 docs README.md: Add total download badge

([086075bc69eff4a](https://github.com/Lob2018/SudokuFX/commit/086075bc69eff4a6329f451fed69b10ebedffe5e))

🔧 chore requirements.txt: Update

([7786dee9ad670e9](https://github.com/Lob2018/SudokuFX/commit/7786dee9ad670e9fe960e79945cbb3460d798e07))

👷 ci release.yml: Fix JVM 25 crash on macOS Intel

([4ac73e4ce15ab85](https://github.com/Lob2018/SudokuFX/commit/4ac73e4ce15ab858dca61f40ef6cb6fa4b959af3))

👷 ci release.yml: Implement robust version extraction via Maven

([af8ccbbc0c28a20](https://github.com/Lob2018/SudokuFX/commit/af8ccbbc0c28a20b0f83e546465805f073689fd5))

📚 docs Doxygen and update license links to point to LICENSE.txt

([d10d7802b98705b](https://github.com/Lob2018/SudokuFX/commit/d10d7802b98705b7b204359ef5d0d157ddb0b546))

🔧 chore spring-boot-starter-parent, javafx.version, spotless-maven-plugin: Update maven-dependencies

([656edd1d49db8c1](https://github.com/Lob2018/SudokuFX/commit/656edd1d49db8c1099c89a7734da8ee9f7bc2091))

🔧 chore harden-runner, checkout, setup-java, codeql-action, setup-python: Update the actions-dependencies

([b7e1bba937efc7c](https://github.com/Lob2018/SudokuFX/commit/b7e1bba937efc7c5971e284f9e8265008e0293f4))

🛠️ build pom.xml: Bump version to 1.2.1

([6d768c27679f1d9](https://github.com/Lob2018/SudokuFX/commit/6d768c27679f1d9fc17d2252ede38793e86ccf99))

🐛 fix linuxRelease.sh, macosRelease-x86_64.sh, macosRelease.sh, windowsRelease.bat: Prevent startup crashes and improve JRE installation UX across platforms
-Add &#x60;where java&#x60; check and &#x60;:java_error&#x60; label in Batch generator to prevent silent failures.
-Update shell and batch scripts with clearer JRE installation instructions and simplified URLs.
-Implement explicit &quot;You can now close this window&quot; messages in all error paths.
-Fix command chaining (&#x60;^&amp;&#x60;) and redirection (&#x60;&gt;nul&#x60;) in the Windows generator.
-Tests pass.

📝 style encodings.xml: Run automatic code formatter

([f732d143cb38263](https://github.com/Lob2018/SudokuFX/commit/f732d143cb382637b67e515eb5c2b08976e7409f))

✨ feat encodings.xml: Register encoding for Aider configuration

([3456f0c3361cb2a](https://github.com/Lob2018/SudokuFX/commit/3456f0c3361cb2ae9ce8d581ddd90106b4c921bd))

🛠️ build .gitignore: Exclude aider artifacts from version control

([0b1f0b117316a54](https://github.com/Lob2018/SudokuFX/commit/0b1f0b117316a541b9b13f4d1658c58e9176dfb2))

🛠️ build .gitignore: Extend exclusion rules for Python environments
- .gitignore
- Added .venv/, venv/, ENV/, and env/ to prevent tracking local virtual environments
- Included .env and .python-version to protect secrets and local version configs
- Added pip-log.txt and temporary pip directories to the ignore list
- Functional impact: Prevents leakage of absolute system paths and local usernames
- Tests pass.

([bdf03e86292b522](https://github.com/Lob2018/SudokuFX/commit/bdf03e86292b522853c89b1294357f96d36d616f))

📚 docs CONTRIBUTING: Implement structured &quot;Clean Bug Report&quot; template
-Replace basic bug report list with a detailed, structured template.
-Add specific examples for Scenario, Platform, Steps, Expected/Actual behavior, and Technical Notes.
-Include a Tip block emphasizing the importance of logs and reproducibility.
-Align examples with SudokuFX Backup sub-menu and JavaFX/Spring Boot architecture.
-Functional Impact: Improves quality and reproducibility of community-filed issues.
-Tests pass.

([f20412a54622c5d](https://github.com/Lob2018/SudokuFX/commit/f20412a54622c5d460aa480831de1c1d74704797))

🐛 fix .pre-commit-config.yaml, CONTRIBUTING.md, requirements.txt: Resolve CVE-2024-5569 (zipp) by pinning pre-commit to 4.5.1
- Upgrade runner to eliminate transitive security vulnerability.
- Freeze all dependencies in requirements.txt for Snyk compliance.
- Update docs with mandatory synchronization steps

([708c5e7103ffc72](https://github.com/Lob2018/SudokuFX/commit/708c5e7103ffc72cddbbeb5f20c8ef6db683617b))

🔧 chore CI/CD cache: Update

([255c5e974af9441](https://github.com/Lob2018/SudokuFX/commit/255c5e974af9441fec0a5576e9c0cb72fdc3252f))

🔧 chore CI/CD upload-artifact: Update

([134ec21d7c0b952](https://github.com/Lob2018/SudokuFX/commit/134ec21d7c0b95222c5488aa0984a3a3dbb92889))

🔧 chore CI/CD checkout: Update

([abb30aae7a7143c](https://github.com/Lob2018/SudokuFX/commit/abb30aae7a7143c4fa6a34d84c1ca2f7552e7945))

📚 docs Doxygen

([9efb0399760816b](https://github.com/Lob2018/SudokuFX/commit/9efb0399760816b3a994dbb502da73aad650093e))

♻️ refactor misc.xml, pom.xml, CI/CD, CONTRIBUTING.md, qodana.yaml, README.md: Upgrade to JavaFX 25 with JDK 25, and bump project to 1.2.0
- Update pom.xml and misc.xml to align with OpenJDK 25
- Upgrade JavaFX dependencies to 25.0.1 for stability and audio fixes
- Synchronize CI/CD pipelines and scripts with the new SDK version
- Bump project version to 1.2.0

([b650cd8456a8a2c](https://github.com/Lob2018/SudokuFX/commit/b650cd8456a8a2c07d8130ba1b4a584e1fcdf072))

♻️ refactor dependabot.yml: Optimize Dependabot configuration

([424d99662ed2824](https://github.com/Lob2018/SudokuFX/commit/424d99662ed2824116e2aac158941a9220377849))

♻️ refactor openssf_score_card.yml : Simplify OpenSSF scorecard workflow documentation

([b5ef78495b92baa](https://github.com/Lob2018/SudokuFX/commit/b5ef78495b92baaf369c1670e6bdf8f3e93ba01d))

🐛 fix BindingConfiguratorUTest: Use FxRobot.interact for ColorPicker keyboard events

([96720c0c7e29cf8](https://github.com/Lob2018/SudokuFX/commit/96720c0c7e29cf82c205016941e4a4a333a2e98a))

♻️ refactor logback.xml: Force UTF-8 encoding for Logback output

([0e7226e62a42e2e](https://github.com/Lob2018/SudokuFX/commit/0e7226e62a42e2ed380dcf5c7e742ac2e944295a))

🐛 fix Game, Player: Replace deprecated Hibernate @Cascade with JPA standard

([61cb40278d5bb02](https://github.com/Lob2018/SudokuFX/commit/61cb40278d5bb02534b849a4c4a1e7692fa661ed))

📝 style BenchGridMaster: Run automatic code formatter

([8527a4226bc9e4a](https://github.com/Lob2018/SudokuFX/commit/8527a4226bc9e4aed7beaf996a136b882739b859))

🐛 fix BenchGridMaster: Remove commented-out code in benchmark and document setup steps

([75869f385c9c5f4](https://github.com/Lob2018/SudokuFX/commit/75869f385c9c5f42a89857602ab5f554d276b35b))

🐛 fix I18n: Make setLocale static to match the static bundle field

([6da1a25b990d274](https://github.com/Lob2018/SudokuFX/commit/6da1a25b990d274acbdf4bbfd1c4b0c58c3b4cb3))

🛠️ build: bump version to 1.1.0

([8ac56599d43e083](https://github.com/Lob2018/SudokuFX/commit/8ac56599d43e083820de31b0d4d77db1fa5b2e35))

🔧 chore spring-boot-starter-parent: Update

([89eda3c8ebc0d3c](https://github.com/Lob2018/SudokuFX/commit/89eda3c8ebc0d3ccf5133d55bb56c2932024c416))

📚 docs Doxygen

([e613114f647aba0](https://github.com/Lob2018/SudokuFX/commit/e613114f647aba013f4a595ef3001266bc5a32b1))

🔧 chore exec-maven-plugin, checkstyle, flyway-database-hsqldb, CI/CD codeql-action, CI/CD download-artifact, CI/CD qodana-action, CI/CD harden-runner, CI/CD setup-java, : Update

([5699bff9042c187](https://github.com/Lob2018/SudokuFX/commit/5699bff9042c18732b8f3163841252e7b8b41834))

🔧 chore CI/CD codeql-action/upload-sarif: Update

([bd77c1f63eb4b46](https://github.com/Lob2018/SudokuFX/commit/bd77c1f63eb4b46bd28effd2b2b16dab9fa029f7))

🔧 chore spotless-maven-plugin, commons-lang3, CI/CD Qodana Scan, CI/CD checkout, CI/CD setup-python, CI/CD harden-runner, CI/CD codeql-action: Update

([838acfef7e2793a](https://github.com/Lob2018/SudokuFX/commit/838acfef7e2793afa31a7cd3999f8b9399b72900))

🔧 chore .pre-commit-config.yaml: Update gitleaks

([b006b50c3f9f1be](https://github.com/Lob2018/SudokuFX/commit/b006b50c3f9f1bed07464f7eb0a6b7dfc373e4e6))

📚 docs Doxygen

([972015b8e1bc069](https://github.com/Lob2018/SudokuFX/commit/972015b8e1bc069d9f660b4342177bea56b4b6b6))

🛠️ build misc.xml, maven-wrapper.properties, .gitignore, mvnw.cmd, mvnw : Upgrade maven wrapper to 3.3.4 and switch to GraalVM 21
- Upgrade Maven Wrapper to 3.3.4 (Apache) and Maven to 3.9.9.
- Update project SDK to GraalVM 21 (NIK) in .idea/misc.xml.
- Fix formatting violations in .idea/workspace.xml via spotless:apply.
- Adjust .gitignore to track the Maven Wrapper binary JAR.

([40640941da84af9](https://github.com/Lob2018/SudokuFX/commit/40640941da84af9ed4da250d4e6dc22521c4e8d5))

🐛 fix application-test.properties, PlayerStateHolder: Stabilize in-memory DB access during integration tests
The integration tests were failing due to a race condition where the PlayerStateHolder tried to access the database before Flyway had completed schema migration.
- Added &#x60;@DependsOn(&quot;flyway&quot;)&#x60; to &#x60;PlayerStateHolder&#x60; to explicitly force Spring to wait for Flyway&#x27;s &#x60;migrate()&#x60; method to complete before instantiating the bean.
- Updated &#x60;application-test.properties&#x60; to set &#x60;spring.jpa.hibernate.ddl-auto&#x3D;none&#x60; to prevent Hibernate&#x27;s schema validation from failing on a clean in-memory database.
- Tests pass

([cf88b004b4ec5d3](https://github.com/Lob2018/SudokuFX/commit/cf88b004b4ec5d34cc9df79894c5dda410163ebd))

🔧 chore spring-boot-starter-parent, datasource-proxy-spring-boot-starter, flyway, dependency-review-action, harden-runner, codeql-action, download-artifact, upload-artifact, puppycrawl, exec-maven-plugin : Updated

([6cb58ee38e72542](https://github.com/Lob2018/SudokuFX/commit/6cb58ee38e7254213dc283fd5f10d7d7ff5ae1a9))

🔧 chore .pre-commit-config.yaml: Update gitleaks version

([d091fe911281ec5](https://github.com/Lob2018/SudokuFX/commit/d091fe911281ec5c90bd57659c624ac7bb341277))

📚 docs README.md: Update the license badge

([bf227b1d953dbaf](https://github.com/Lob2018/SudokuFX/commit/bf227b1d953dbaf5fc57fe64a1803ffd85e641c2))

🔧 chore: Change project license from MIT to GPLv3.0
- Tests pass
- Doc Doxygen updated

([7d172aa638b912f](https://github.com/Lob2018/SudokuFX/commit/7d172aa638b912fbf9f57e0d1bb5cc7b04811f6d))

♻️ refactor GridMaster, BenchGridMaster: 20x grid generation performance improvement, and update benchmark for GridMaster resoudreLaGrille and creerLesGrilles methods
- Tests pass

([7a1f40beb19916e](https://github.com/Lob2018/SudokuFX/commit/7a1f40beb19916e0102942a016e2aac3033e3cd1))

📚 docs README: Refine heap object diagnostic example

([d761ba658bb8d72](https://github.com/Lob2018/SudokuFX/commit/d761ba658bb8d72cc522401333d352d1aea8af34))

📚 docs README: Add DEV Debugging with Remote JVM section under IntelliJ development guide

([9747b68a740df5a](https://github.com/Lob2018/SudokuFX/commit/9747b68a740df5a055c3b5742a8cf1607bc764c0))

✨ feat debug runConfigurations: Add SudokuFX [debug] and Debug JavaFX Maven configurations
- SudokuFX [debug]: Launches Maven with JDWP enabled on port 5005
- Debug JavaFX Maven: Attaches IntelliJ debugger to the suspended JVM
Usage:
1. Set breakpoints
2. Run SudokuFX [debug], then immediately start Debug JavaFX Maven to start the app with breakpoints active

([81e6a8b125a1f73](https://github.com/Lob2018/SudokuFX/commit/81e6a8b125a1f73b3d975ed0ec0cbb680d3624fd))

🐛 fix MainView: handleMenuMaxiShow now sets focus on menuMaxiButtonOptions when menuOptionsButtonOptions is clicked

([eb004cfbb8b0fc6](https://github.com/Lob2018/SudokuFX/commit/eb004cfbb8b0fc6e4c27ee5717747b8f9f24c63c))

🐛 fix I18n: Remove unnecessary synchronized keyword

([77369a6e4d48a29](https://github.com/Lob2018/SudokuFX/commit/77369a6e4d48a2946cfc5b43d0ba476021750fed))

📚 docs: DOXYGEN

([39a91558f13643c](https://github.com/Lob2018/SudokuFX/commit/39a91558f13643cd850002c64b59408022f06a3a))

✨ feat linuxRelease.sh, macosRelease.sh, macosRelease-x86_64.sh, windowsRelease.bat: Add JVM memory tuning and diagnostic parameters and improved Java version parsing
Configure explicit memory settings:
- Initial/max heap: 512MB/2GB (reduce resize overhead)
- Metaspace: 128MB initial, 192MB max (control class metadata)
- Heap dump on OOM for debugging

([7cd4a3a5216f28c](https://github.com/Lob2018/SudokuFX/commit/7cd4a3a5216f28c9c15feea810ccb06d3135a974))

♻️ refactor pom.xml : Add MaxMetaspaceSize to VisualVM profile for leak diagnostics

([2f8d33ee2376279](https://github.com/Lob2018/SudokuFX/commit/2f8d33ee2376279980e806e15d49f71d6f2edd30))

📚 docs README.md: constrain Metaspace to expose loader retention

([9f3162d27bfc2b1](https://github.com/Lob2018/SudokuFX/commit/9f3162d27bfc2b1591acd2589584bb2740943361))

📚 docs README: Clarify Metaspace growth indicator to avoid false positives

([92c92fefc0ec78b](https://github.com/Lob2018/SudokuFX/commit/92c92fefc0ec78bf780fd81cbd18187bd6001d4c))

📝 style README: Clean up whitespace in diagnostic table

([6086fb7674850d5](https://github.com/Lob2018/SudokuFX/commit/6086fb7674850d53eb7b4cb6c7149231fc0003f4))

♻️ refactor README: Refactor JVM diagnostic table for clarity and tool alignment

([16bf7f9059c1ee1](https://github.com/Lob2018/SudokuFX/commit/16bf7f9059c1ee11bcaeed819e919c11b84ed66a))

♻️ refactor pom.xml profile: Modernize GC logging for VisualVM monitoring

([26c3f8f3f2a0f04](https://github.com/Lob2018/SudokuFX/commit/26c3f8f3f2a0f049b38f9dc178171355098a8f99))

🔧 chore CI/CD dependency-review-action, codeql-action: Update

([bff3ab2d9e8fb87](https://github.com/Lob2018/SudokuFX/commit/bff3ab2d9e8fb87135ef098a493c5bd066770f38))

🔧 chore pom.xml flyway-database-hsqldb, jacoco-maven-plugin, checkstyle: Update

([e2d633464ff12ee](https://github.com/Lob2018/SudokuFX/commit/e2d633464ff12ee925d1247a07abda27e8edaba3))

📚 docs README: Enhance JVM diagnostics and monitoring documentation
Adds dedicated sections for VisualVM setup and a detailed table of jcmd/jmap commands, focusing on common memory leak patterns (classloaders, Metaspace, heap retention).

([95a493fc60f29a9](https://github.com/Lob2018/SudokuFX/commit/95a493fc60f29a92567d4b9ba665dd199c696830))

🔧 chore pom.xml: Enable Native Memory Tracking for diagnostics
Added &#x60;-XX:NativeMemoryTracking&#x3D;summary&#x60; to the &#x60;visualvm-monitoring&#x60; Maven profile.
This enables diagnostic commands for:
- Native memory analysis (e.g., Metaspace, Code Cache, Thread stacks)
- Classloader statistics (e.g., DelegatingClassLoader instances, Metaspace chunks)
- Object histogram and GC collectability tests
Key commands:
- &#x60;jcmd &lt;pid&gt; VM.native_memory summary&#x60;
- &#x60;jmap -clstats &lt;pid&gt;&#x60;
- &#x60;jmap -histo:live &lt;pid&gt; | head -50&#x60;
- &#x60;jcmd &lt;pid&gt; GC.run &amp;&amp; jmap -clstats &lt;pid&gt;&#x60;
See project documentation for usage examples and thresholds.

([9120e560d887f3d](https://github.com/Lob2018/SudokuFX/commit/9120e560d887f3dfd258bb3b72f8ebf2b97ffaec))

🐛 fix GenericDtoListCell: Eliminate memory leak by cleaning reused state and avoiding redundant allocations
- All tests pass

([278f8eafa834fcb](https://github.com/Lob2018/SudokuFX/commit/278f8eafa834fcb7ab4e6342b3747c9a675952bf))

🐛 fix GenericDtoListCell: Fix MyAlert content truncation by replacing setContentText with custom Label in dialog pane

([ea8c05093ffae95](https://github.com/Lob2018/SudokuFX/commit/ea8c05093ffae95f5a980288705f393642e61bcc))

🐛 fix GenericDtoListCell, GenericListViewFactory.java: Memory leak due to strong references in ListView and reused MyAlert
☑ Root cause
The GenericDtoListCell held strong references to StringBinding objects (buttonAccessibleTextBinding, confirmationTitleBinding, confirmationMessageBinding), the ListView, and a persistent MyAlert instance. Since ListView cells are reused by JavaFX, these strong references prevented the cells, bindings, and the alert dialog from being garbage collected, causing a memory leak.
☑ Fix
Replaced direct references to StringBinding with Suppliers&lt;String&gt; that lazily fetch the current value from the bindings. The MyAlert instance is no longer reused; a new MyAlert is created on demand when needed. This ensures that cells, bindings, and alert dialogs can be garbage collected when no longer in use. Updated the constructor and updateItem method accordingly.
☑ Update or add tests
Manually verified that ListView cell reuse no longer retains old bindings or alert instances, and memory usage remains stable when adding/removing items repeatedly.
- Tests pass

([50eb2c4081baf17](https://github.com/Lob2018/SudokuFX/commit/50eb2c4081baf17f8a36f399fec37e05b19cef94))

📚 docs: DOXYGEN

([86a71a2048aa26a](https://github.com/Lob2018/SudokuFX/commit/86a71a2048aa26a41810ee743aeb4a44ea54ff1c))

🐛 fix PlayerStateHolder: Mask sensitive paths in player logging
- Tests pass

([7e328363e6ebd2b](https://github.com/Lob2018/SudokuFX/commit/7e328363e6ebd2b6e27cd672b8f117ba100f6aea))

✨ feat(AbstractDataSourceConfig, logback.xml, application.properties, pom.xml, README.md): Replace Spring Boot show-sql with DataSource-Proxy, add dev profile, enable SQL logging, update logging and run configurations
- Added a new Maven profile &#x60;dev&#x60; in pom.xml
  - Includes &#x60;datasource-proxy-spring-boot-starter&#x60; dependency
  - Scope set to &#x60;runtime&#x60; to enable SQL logging only during local runs (run... configurations)
  - Ensures the dependency is not included in production builds
- Updated IntelliJ run configurations
  - &#x60;SudokuFX [run]&#x60; and &#x60;SudokuFX [run with details]&#x60; now include &#x60;-Pdev&#x60; profile
- Updated application.properties for dev local runs
  - Replaced &#x60;spring.jpa.show-sql&#x3D;true&#x60; with datasource-proxy logging
  - Enabled &#x60;logging.level.net.ttddyy.dsproxy.listener&#x3D;debug&#x60;
  - Enabled &#x60;decorator.datasource.datasource-proxy.format-sql&#x3D;true&#x60;
  - Prepares SQL logging for local debugging while keeping prod configuration clean
- Updated AbstractDataSourceConfig.flyway()
  - Changed method parameter type from &#x60;HikariDataSource&#x60; to &#x60;DataSource&#x60;
  - Allows Flyway to work with a proxy-wrapped DataSource instance
  - Javadoc updated to indicate support for proxy-wrapped DataSource
- Updated logback.xml
  - Added logger for &#x60;net.ttddyy.dsproxy.listener&#x60; at DEBUG level
  - Ensures SQL logging from datasource-proxy is written to the rolling file appender
- Updated README
  - Added &#x60;datasource-proxy-spring-boot-starter&#x60; under SGBDR &amp; Spring Boot dependencies
  - Notes that it intercepts and logs SQL queries for debugging and performance analysis
- Tests pass

([85f774accb17226](https://github.com/Lob2018/SudokuFX/commit/85f774accb1722685f94dca3c6657d97f564d6e9))

📚 docs SudokuFX_in_action.jpg: Update SudokuFX in action image

([98f7a088fb05233](https://github.com/Lob2018/SudokuFX/commit/98f7a088fb05233dab393ca4f5e37229bb18daf7))

♻️ refactor GridCellViewModel: Use applyBorderStyle in the constructor
- Tests pass

([0e3fbf17ed24d5e](https://github.com/Lob2018/SudokuFX/commit/0e3fbf17ed24d5e7332b774b674db7ef52db2359))

♻️ refactor GridCellViewModel: Centralize border style application
- Replace lastBorderStyle with applyBorderStyle for Label CSS updates
- Apply efficiently based on focus and content, removing redundant state tracking
- Tests pass

([facfaa910a0a6e8](https://github.com/Lob2018/SudokuFX/commit/facfaa910a0a6e8311830b53f8ff712d10f08bc2))

📚 docs: DOXYGEN

([95e3dfbef1a284f](https://github.com/Lob2018/SudokuFX/commit/95e3dfbef1a284f30b07e6e6475737bda518a0c9))

🔧 chore pom.xml maven-enforcer-plugin exec-maven-plugin: Update

([849ec46b913590f](https://github.com/Lob2018/SudokuFX/commit/849ec46b913590fac5426efd4f87c07b9d1bd91a))

📝 style GridConverterUTest: Run automatic code formatter

([b343444eb87d4b1](https://github.com/Lob2018/SudokuFX/commit/b343444eb87d4b143ac063f2c13cf8a63922d0fc))

♻️ refactor IGridConverter, GridConverter: Optimize listToGridValue and improve validation
- Replaced Stream with loop + StringBuilder to reduce allocations and improve performance.
- Added bitmask-based validation for digits 1-9 with no repeats.
- Updated Javadoc to reflect normalization and validation rules.
- Tests pass

([0b8e3f3acc114a1](https://github.com/Lob2018/SudokuFX/commit/0b8e3f3acc114a1cb144387e93101adf6f8fbc14))

♻️ refactor NumberUtils, MY_LICENSE_TO_EDIT.txt, DynamicFontSize, NumberUtilsUTest, CrashScreenView: Replace fixed epsilon comparisons with ULP-based equality, and simplify safeDivide
- Removed areDoublesEqualCenti and simplified areDoublesEqual methods.
- Added areDoublesEqualEpsilon supporting both epsilon and 1-ULP tolerance.
- Internal ULP comparison factored into equalsUlps for code reuse.
- safeDivide now returns Double.NaN for invalid divisions instead of OptionalDouble to reduce object allocation in intensive usage.
- Documentation updated to reflect precise numeric comparison behavior.
- Unit tests updated to cover safeDivide, areDoublesEqual, and areDoublesEqualEpsilon.
- Updated LICENSE.txt to include Apache Commons Numbers – Precision module:
  * Licensed under the Apache License, Version 2.0.
  * Modifications made by Lob2018 – Portions of the logic and documentation adjusted for SudokuFX (2025).
- Tests pass

([a6a59bd05d1781b](https://github.com/Lob2018/SudokuFX/commit/a6a59bd05d1781b68495e96532016cb9c3c10a1c))

🐛 fix GridViewModel: Update Game.updatedAt field in persistGridValue()

([2b41c41e26892be](https://github.com/Lob2018/SudokuFX/commit/2b41c41e26892befe7d2524586fccf4badd3e6dd))

🐛 fix V1_1__insert_initial_values.sql: Ensure UTC timestamps in Flyway initial data script for HSQLDB

([0d4f7495338873a](https://github.com/Lob2018/SudokuFX/commit/0d4f7495338873aa1257e2d2283e5d0404964b35))

📚 docs: DOXYGEN

([f0d8944d95a0b41](https://github.com/Lob2018/SudokuFX/commit/f0d8944d95a0b4193f5bb15b5a14a59b97db198f))

♻️ refactor MyDateTime, MyDateTimeUTest,...: Migrate MyDateTime to use Instant instead of LocalDateTime
- Replaced LocalDateTime with Instant for all current time and timestamp operations
  to avoid ambiguity with time zones and daylight saving time.
- Added Clock field to allow testable and adjustable time.
- Updated getFormattedCurrentTime() and getFormatted() to handle ZonedDateTime
  conversions from Instant.
- Removed direct LocalDateTime usage, centralizing timezone handling via Clock.
- Tests pass

([212e57dac7e1883](https://github.com/Lob2018/SudokuFX/commit/212e57dac7e1883fcbc2ab92b875eb8d635a0c9c))

♻️ refactor NumberUtils, PowerOfTen, DynamicFontSize, CrashScreenView: Unify numeric operations and update CrashScreenView &amp; DynamicFontSize
- Introduce &#x60;NumberUtils&#x60; enum singleton for safe numeric operations (division, approximate equality) with predefined epsilon thresholds via &#x60;PowerOfTen&#x60;.
- Replace hardcoded EPSILON in &#x60;DynamicFontSize&#x60; with &#x60;NumberUtils.INSTANCE.areDoublesEqualCenti&#x60; for consistent approximate comparisons.
- Update &#x60;CrashScreenView&#x60; to use &#x60;NumberUtils.INSTANCE.safeDivide&#x60; for computing SVG stroke width, ensuring safe division and avoiding potential runtime errors.
- Maintain dynamic font resizing logic in &#x60;DynamicFontSize&#x60; and fade-in behavior in &#x60;CrashScreenView&#x60; without impacting existing UI layout or style logic.
- Overall refactor centralizes numeric thresholds, enhances safety, and ensures consistent precision handling across the UI components.
- Created tests
- Tests pass

([2796cf3985a734b](https://github.com/Lob2018/SudokuFX/commit/2796cf3985a734bfcd0ede3a5acdb076d605119a))

🔧 chore codeql-action: Update

([a76e82609d5e90f](https://github.com/Lob2018/SudokuFX/commit/a76e82609d5e90faed5d5d098cc593c356bd935b))

👷 ci openssf_score_card.yml scorecard-action: Update

([43230c844c27f63](https://github.com/Lob2018/SudokuFX/commit/43230c844c27f63bc50d742e7b01bd457fb90e3f))

👷 ci release.yml: Migrate macOS x86_64 build to macos-15-intel runner
- Replace deprecated macos-13 runner with macos-15-intel to ensure
continued x86_64 builds. The macos-13 runner will be retired on
December 4th, 2025.
- Note: GitHub will discontinue x86_64 support on macOS after the
macOS 15 runner retirement in Fall 2027.

([974d236125ccf33](https://github.com/Lob2018/SudokuFX/commit/974d236125ccf335093bb86ffc8c1aaa6a9dd8fd))

📚 docs: DOXYGEN

([25a3608da28b678](https://github.com/Lob2018/SudokuFX/commit/25a3608da28b678a5873a8b690be006f5917e0ac))

♻️ refactor SplashScreenView: Make style.css a static field in SplashScreenView

([4f91bd53c53172e](https://github.com/Lob2018/SudokuFX/commit/4f91bd53c53172ea7b9cd6d7f7477ed45f42e01c))

✨ feat MyAlert, AppPaths, alert.css, HelpViewModel, HelpViewModelUTest: Replace setStyle with dedicated alert style class
- Ensure all alert styles and button cursors are applied correctly
- Tests pass

([7034d41ce96e3e2](https://github.com/Lob2018/SudokuFX/commit/7034d41ce96e3e2b650085be199f956b38275be7))

♻️ refactor SplashScreenView, style.css: Move inline css to style.css, and SudokuFX logo border from .px to.em

([67f42e38c94801b](https://github.com/Lob2018/SudokuFX/commit/67f42e38c94801b39858dbfb00941669a293ca1c))

📝 style IGridMaster: Run automatic code formatter

([1ceb996f13a7589](https://github.com/Lob2018/SudokuFX/commit/1ceb996f13a7589f9569742714a23f202cbb2d5f))

♻️ refactor IGridMaster: Removed non-breaking space (NNBSP)

([4e4214b5a559025](https://github.com/Lob2018/SudokuFX/commit/4e4214b5a55902567b0ab66a98cfec1f17eda8e5))

📚 docs: DOXYGEN

([299b3509be4649d](https://github.com/Lob2018/SudokuFX/commit/299b3509be4649d0c7cb258a76a6a9bebabfbc0d))

🐛 fix MenuPlayerViewModel: Define a constant instead of duplicating a literal

([36693ab1634046b](https://github.com/Lob2018/SudokuFX/commit/36693ab1634046b3331c7d873d59b969fe39b572))

🐛 fix OsFolderInitializer, ExceptionTools, FolderCreationException, OsFolderInitializerUTest: Define and throw a dedicated exception instead of using a generic one.
- Tests pass

([9091bc1fa84500c](https://github.com/Lob2018/SudokuFX/commit/9091bc1fa84500ca293b9be6e16c41174fd3f325))

🐛 fix MyLogbackConfig, ExceptionTools, LogbackConfigurationException: Define and throw a dedicated exception instead of using a generic one.

([d7a8e7207077b96](https://github.com/Lob2018/SudokuFX/commit/d7a8e7207077b960e59f536fa1e3133f372c3474))

🐛 fix SpinnerGridPane: Declare &quot;spinnerAnimation2&quot; on a separate line.

([5ae130c788a6883](https://github.com/Lob2018/SudokuFX/commit/5ae130c788a6883f88f7390ccd2e98ab8c5bdad4))

✨ feat MenuSolveViewModel, GridViewModel, main-view.fxml, GridViewModelUTest, MenuSolveViewModelUTest: Partial development of the clear button in the Solve menu
- Tests pass

([db09b06a942d4ad](https://github.com/Lob2018/SudokuFX/commit/db09b06a942d4ad42bd6184f1147ee19fb2d932b))

📚 docs DynamicFontSize: Update documentation for DynamicFontSize
- Clarify purpose and behavior of dynamic font resizing
- Explain debounce mechanism and stylesheet caching
- Improve readability and conciseness

([7aa536f9189621c](https://github.com/Lob2018/SudokuFX/commit/7aa536f9189621c82b0b12ffc97681678933fad5))

🐛 fix DynamicFontSize: Old Gen memory pressure during scene resize
☑ Root cause
Frequent calls to Node.setStyle() with inline CSS on each resize caused excessive
Old Gen allocations.
☑ Fix
Replaced inline style updates with a dynamic stylesheet approach leveraging
JavaFX&#x27;s stylesheet caching, applying updates only when the font size changes.
☑ Update tests pass.

([c3a2d6b4c7f62fb](https://github.com/Lob2018/SudokuFX/commit/c3a2d6b4c7f62fba973b227b77e54c8a9c5d3cc4))

📝 style DynamicFontSizeChangeE2ETest: Run automatic code formatter

([d5cfb229a09f659](https://github.com/Lob2018/SudokuFX/commit/d5cfb229a09f65902a3b5f5047c8f86a60786709))

✅ test DynamicFontSizeChangeE2ETest: Use WaitForAsyncUtils to handle DEBOUNCE_DELAY

([e8f802341b28777](https://github.com/Lob2018/SudokuFX/commit/e8f802341b2877755d5601703ae23a83d251dc7d))

ChatGPT a dit :

♻️ refactor DynamicFontSize: Reduce DEBOUNCE_DELAY from 500ms to 50ms for smoother UI updates during window resizing while limiting OldGen memory pressure

([02359bdff9601ca](https://github.com/Lob2018/SudokuFX/commit/02359bdff9601ca38e4bd7da05501781a8e3e1ef))

✨ feat DynamicFontSize, README.md (specify VisualVM JMX Connection): Add debounced dynamic font resizing to DynamicFontSize to reduce memory pressure during rapid Scene resizes

([e4c18a87eed6541](https://github.com/Lob2018/SudokuFX/commit/e4c18a87eed6541c15c48fe1dcb17ac37db686eb))

🐛 fix CI/CD updated .yml

([31c997d0a27e82e](https://github.com/Lob2018/SudokuFX/commit/31c997d0a27e82e4ba10caeba9a44002b8ebfd6f))

🔧 chore pom checkstyle spotless-maven-plugin commons-lang3 flyway-database-hsqldb, CI/CD dependency-review-action codeql-action: Update

([97d2ddbaf6b013f](https://github.com/Lob2018/SudokuFX/commit/97d2ddbaf6b013f6bbd7bdbcc51633130a36da22))

🐛 fix MenuOptionsViewModelUTest: Remove requestFocus assertion

([47d51d93a4a0687](https://github.com/Lob2018/SudokuFX/commit/47d51d93a4a0687e59d88b228f8c00f6cbeb867c))

🔧 chore pom profile visualvm-monitoring: Set JVM max heap to 2GB for VisualVM monitoring

([b22c0118e1b04a2](https://github.com/Lob2018/SudokuFX/commit/b22c0118e1b04a2bc2d31de46eb79b677ba1775b))

🐛 fix Coordinator: Use relative path with extension instead of extension local field

([c7183a98a450f1d](https://github.com/Lob2018/SudokuFX/commit/c7183a98a450f1db7da510f701051574d4bfc69f))

✨ feat runConfiguration, pom.xml, README: add VisualVM monitoring profile and IntelliJ run configuration
- Added Maven profile &#x60;visualvm-monitoring&#x60; to enable JMX and GC logging for runtime diagnostics
  - Injects JVM flags via javafx-maven-plugin to target the correct JavaFX process
  - Allows VisualVM sampling and MBeans access on localhost:9010
- Created IntelliJ Run Configuration &#x60;SudokuFX run with VisualVM Monitoring&#x60;
  - Uses &#x60;clean javafx:run -Pvisualvm-monitoring -f pom.xml&#x60;
  - Saved in &#x60;.idea/runConfigurations&#x60;
- Updated README:
  - Documented the profile and its purpose under Windows development instructions
  - Linked to VisualVM usage and plugin setup
- Tests pass

([c1b2b9da8f317e2](https://github.com/Lob2018/SudokuFX/commit/c1b2b9da8f317e2375b8438f2d66df7fae1314c4))

🐛 fix SudoMain: Use of FxmlView instead of local fields

([be6c90275ce594d](https://github.com/Lob2018/SudokuFX/commit/be6c90275ce594d3f775bc2d024277b2540bb367))

📚 docs README: Clarify VisualVM plugin instructions with consistent markdown list formatting

([0ac4f3a14aaaaa7](https://github.com/Lob2018/SudokuFX/commit/0ac4f3a14aaaaa73d267346f1871b54f2b8b3382))

📚 docs README: Document optional VisualVM setup (MBeans, VisualGC) for local monitoring

([cd3f986d4b00130](https://github.com/Lob2018/SudokuFX/commit/cd3f986d4b00130f9add54b531d422f3f2954fe0))

🐛 fix FxmlView, FxmlViewUTest: Append .fxml extension to enum paths and update related tests
- Tests pass

([0f174a072240e48](https://github.com/Lob2018/SudokuFX/commit/0f174a072240e48f465e13cb59c28b051f4fc065))

✨ feat MainView, FxmlView: Introduce FxmlView enum for centralized FXML references
Replaces hardcoded FXML path strings with FxmlView enum to improve type safety and maintainability.
Updated view loading logic to use FxmlView.getPath() where applicable.
- Tests pass

([d0817b2cd79503e](https://github.com/Lob2018/SudokuFX/commit/d0817b2cd79503e4be04ae94d576dd21e3e96795))

🐛 fix ToasterService, ToasterVBox: Improve toast behavior and document WCAG non-compliance
- ToasterService#sendToast now resets &#x60;toastRequest&#x60; to null before publishing
  new ToastData to ensure UI listeners fire even for repeated identical messages.
- Removed &#x60;requestFocus&#x60; parameter from ToastData in sendToast; focus is now
  handled explicitly in the view.
- Updated ToasterVBox#addToast and #addToastWithDuration Javadoc to indicate that
  requesting focus is ⚠ Non WCAG-compliant.
- Minor Javadoc improvements for clarity on accessibility and duration handling.
- Tests pass

([8c27ce5fa5afbde](https://github.com/Lob2018/SudokuFX/commit/8c27ce5fa5afbde18f02de1846c372843ad073aa))

🔧 chore CI/CD codeql-action: Update

([37262861748e1ad](https://github.com/Lob2018/SudokuFX/commit/37262861748e1ad74b99c919bb719a33732794ca))

🐛 fix PlayerServiceUTest: remove unused private fields

([45accb6b7dd6ff7](https://github.com/Lob2018/SudokuFX/commit/45accb6b7dd6ff7e92c14a1718aec03cbc1b96ac))

📚 docs README: Add the WCAG 2.1 AA badge

([4df75672a6f7e93](https://github.com/Lob2018/SudokuFX/commit/4df75672a6f7e9347d1983d9892dd18fc8693c1b))

📚 docs: DOXYGEN

([a4202f6eae39949](https://github.com/Lob2018/SudokuFX/commit/a4202f6eae399491a334731ac686a40b5f30ca57))

✨ feat GridCellViewModel, .properties: Add dynamic accessible text for Sudoku grid cells
- Bind &#x60;Label.accessibleTextProperty&#x60; to display formatted cell content
  along with 1-based row and column indices.
- Supports automatic updates on locale or cell content changes.
- Uses i18n key &#x60;grid.cell.accessibility&#x60; for localization.
- Tests pass

([e4e90f0ba476db8](https://github.com/Lob2018/SudokuFX/commit/e4e90f0ba476db8ef502ef8b9030177f519cde5d))

♻️ refactor MainView, ToasterService, AsyncFileProcessorService, Coordinator, MenuOptionsViewModel, MenuOptionsViewModelUTest, AsyncFileProcessorServiceUTest: Centralize toast handling via ToasterService and remove direct ToasterVBox dependencies
- MainView:
  • Removed all FXML-based arguments from ToasterVBox and switched to constructor injection of ToasterService.
  • toasterInitialization() now listens to removeToastRequestProperty() to handle toast removal.
  • optionsMenuInitialization() no longer passes toaster to MenuOptionsViewModel.init().
- AsyncFileProcessorService:
  • Removed direct ToasterVBox dependency and inject ToasterService instead.
  • processFileAsync() now uses toasterService.showInfo() and toasterService.requestRemoveToast().
  • handleError() now uses toasterService.showError() for error reporting.
- ToasterService:
  • Added removeToastRequest property to allow UI components to react to toast removal requests.
  • Added requestRemoveToast() method to toggle removeToastRequest and notify listeners.
  • Maintains showInfo(), showWarning(), and showError() methods for sending toasts.
  • Centralizes all toast-related logic, decoupling UI components from direct ToasterVBox manipulation.
- MenuOptionsViewModel :
  • Injects ToasterService to replace direct ToasterVBox usage for all info/error toasts.
- Tests pass

([cf587fdd61f5e99](https://github.com/Lob2018/SudokuFX/commit/cf587fdd61f5e99b4947464f64a8ceda3b423043))

♻️ refactor MainView: Use toasterService instead of toaster

([a18a93cecf6f692](https://github.com/Lob2018/SudokuFX/commit/a18a93cecf6f69228f7493b5554354429a9f8ce7))

♻️ refactor Coordinator, MainView, CoordinatorUTest: Inject ToasterService via field instead of passing ToasterVBox to toggleLanguage()
- Tests pass

([cf93fde58afdcf4](https://github.com/Lob2018/SudokuFX/commit/cf93fde58afdcf4d23009dd6f683e0b9f275341c))

🐛 fix AsyncFileProcessorServiceUTest: Remove the declaration of thrown exception
- Tests pass

([81a7542862949c3](https://github.com/Lob2018/SudokuFX/commit/81a7542862949c3a4dc7639842438333d52b8888))

🐛 fix PlayerService: Remove unused gridRepository and gameLevelRepository private fields.
- Tests pass

([f6d3d137ebde851](https://github.com/Lob2018/SudokuFX/commit/f6d3d137ebde851724ce08ae283a438f5a05f669))

🐛 fix ImageUtils: Improve exception handling in getImageMeta method
- Replace generic RuntimeException with specific IllegalArgumentException for unsupported formats
- Use UncheckedIOException instead of RuntimeException for I/O errors
- Remove duplicate logging to avoid log pollution (SonarLint compliance)
- Add generic Exception catch block for unexpected processing errors
- Update Javadoc @throws to reflect actual exception types and scenarios
- Enhance error messages with contextual information from original exceptions
- Tests pass

([8c9c95319f54f5b](https://github.com/Lob2018/SudokuFX/commit/8c9c95319f54f5beb185a994b3f0c323369b32c1))

🐛 fix BindingConfigurator: Use comma-separated labels in switch-case for cleaner control flow

([07997d0c1fcaaaa](https://github.com/Lob2018/SudokuFX/commit/07997d0c1fcaaaacd4344fd546c05414c4a3cacd))

🐛 fix AppPaths: Correct Linux path composition to avoid duplicated home segment
- Removed redundant USER_HOME prefix from LINUX_SUDO_FX_BASE_PATH
- Now appends &quot;/Soft64.fr/SudokuFX/&quot; directly to resolved XDG base path
- Prevents invalid paths like /home/runner/home/runner/.local/share/...
- Ensures consistent POSIX-style formatting across all OS-specific paths
- No impact on Windows/macOS path logic or classpath resources
- Tests pass

([b8cd469bdac8466](https://github.com/Lob2018/SudokuFX/commit/b8cd469bdac846633f94d952c762d98b490c8b94))

📚 docs: DOXYGEN

([36aebd40ea0cb0d](https://github.com/Lob2018/SudokuFX/commit/36aebd40ea0cb0dd8d65fb36259121f24381b12d))

✨ feat AppPaths, README... : rename Paths to AppPaths, add XDG support, update README
- Renamed enum Paths → AppPaths for clarity and consistency
- Added resolveUserHome() for normalized home path resolution
- Added resolveXdgDataHome() for XDG-compliant data paths on Linux
- Ensured POSIX-style formatting for all system paths
- Preserved classpath resource paths for internal loading via getResource()
- Updated README (data removal section) to reflect XDG logic
- All tests passing

([fb689cfcfc0c2d5](https://github.com/Lob2018/SudokuFX/commit/fb689cfcfc0c2d5542a28edbcadd0f0bfac17e63))

🔧 chore CI/CD cache: Update

([4ba867f97d5fb68](https://github.com/Lob2018/SudokuFX/commit/4ba867f97d5fb687f00c54dd3293dd651565c5f9))

📚 docs: DOXYGEN

([b47f7ff68c45c2c](https://github.com/Lob2018/SudokuFX/commit/b47f7ff68c45c2c16a8e65e76839266429d7e50b))

♻️ refactor GridViewModel, GridViewModelUTest, Coordinator, MainView, ToasterService, ToastData: Centralize toast service and enhance Coordinator for Spring-managed controllers
- Introduce &#x60;ToasterService&#x60; to centralize toast notifications from ViewModels.
  - Supports INFO, WARN, and ERROR levels with read-only observable property.
  - Ensures non-null text inputs and optional focus requests.
- Enhance &#x60;Coordinator&#x60; to optionally use Spring &#x60;ApplicationContext&#x60; for FXML controller injection.
  - Maintains existing functionalities: dynamic font resizing, scene switching, language toggle, host services integration.
  - Adds safeguards for null &#x60;applicationContext&#x60; to avoid NullPointerExceptions.
- Minor documentation improvements and code comments for clarity.
- Tests updated
- Tests pass

([07ef9d4172c8b59](https://github.com/Lob2018/SudokuFX/commit/07ef9d4172c8b59b9a074cbcaed82ebd68025d6d))

📚 docs: DOXYGEN

([98a9842beb17e54](https://github.com/Lob2018/SudokuFX/commit/98a9842beb17e546db1f1e9ced79d26239ba8625))

🐛 fix MenuOptionsViewModel: Set &#x27;initialized&#x27; before calling dependent methods
- Move &#x27;initialized &#x3D; true&#x27; to the start of the init(...) method.
- Ensure that methods called during initialization (like applyAndPersistBackgroundImage)
  can safely check the ViewModel state.
- Add null checks for all parameters and update JavaDoc to reflect the change.
- Tests pass

([06d502d5579988c](https://github.com/Lob2018/SudokuFX/commit/06d502d5579988c57d98d96cf4a6b474fa143197))

✨ feat GridViewModel, MainView, main-view, GridViewModelUTest: Solve actions completed without clear button
- Tests pass

([09ab5230f548b44](https://github.com/Lob2018/SudokuFX/commit/09ab5230f548b4484ba74870735c8ea47089276d))

♻️ refactor MenuOptionsViewModel, MenuOptionsViewModelUTest:  Enforce initialization and simplify background image test
MenuOptionsViewModel:
- Introduce &#x60;initialized&#x60; flag to track whether &#x60;init(...)&#x60; has been called.
- Mark ViewModel as initialized at the end of &#x60;init(...)&#x60;.
- Add &#x60;checkInitialized()&#x60; calls to methods that require prior initialization:
  - &#x60;resetSongPath()&#x60;
  - &#x60;saveSong(File file)&#x60;
  - &#x60;toggleMuteAndPersist()&#x60;
  - &#x60;toggleGridOpacityAndPersist()&#x60;
  - &#x60;applyAndPersistOptionsColor(GridPane, Color)&#x60;
  - &#x60;applyAndPersistBackgroundImage(File, SpinnerGridPane, GridPane)&#x60;
- Ensures operations on uninitialized ViewModel throw &#x60;IllegalStateException&#x60;.
- Improves robustness and prevents unintended state modification before initialization.
MenuOptionsViewModelUTest:
- Simplify &#x60;applyAndPersistBackgroundImage&#x60; test by:
  - Removing unused &#x60;processor&#x60; variable.
  - Using &#x60;doAnswer&#x60; to directly simulate async processing and callback.
  - Verifying that the GridPane background is set after applying a valid image.
- Reduces test boilerplate while maintaining coverage of async background processing.
- Tests pass

([22fad0e5deb81a3](https://github.com/Lob2018/SudokuFX/commit/22fad0e5deb81a3e7309b40485d7840793819066))

📚 docs: DOXYGEN

([a629a8f14edc98f](https://github.com/Lob2018/SudokuFX/commit/a629a8f14edc98f3646d979e9c7a366302169dc2))

♻️ refactor GridConverter, GridViewModel, IGridConverter, GridConverterUTest: unify and clean grid conversion API
- Rename interface IConverter → IGridConverter
- Remove redundant intArrayToDefaultGridValueList method
- Enforce null checks with Objects.requireNonNull
- Update javadoc to reflect new API and null-safety
- Simplify GridConverter implementation by removing duplicate methods
- Adapt GridConverterUTest to match updated interface:
  - Remove tests for deleted methods
  - Update int array ↔ list conversion tests to use intArrayToList
  - Preserve coverage for listToGridValue, gridValueToList, defaultGridValueToList, and conversions
- Ensure all conversions normalize empty cells to &quot;0&quot; and validate digits
- Tests pass

([78352d62d0bfb92](https://github.com/Lob2018/SudokuFX/commit/78352d62d0bfb929243d69c78a458f5412110c45))

♻️ refactor GridViewModel: Rename iConverter to iGridConverter for clarity

([6cec8b705412441](https://github.com/Lob2018/SudokuFX/commit/6cec8b705412441013a7201d76f4f3cdc9ef3012))

♻️ refactor GridViewModel, GridConverter, IGridConverter, GridConverterUTest, GridViewModelUTest: Introduce IGridConverter for grid value conversions
- Replaced manual string/int array conversions with IGridConverter
- Simplified persistGridValue, setValues, getCurrentGridFromModel, and setCurrentGridWithLevel
- Improved code readability and maintainability without changing functionality
- Tests added and updated
- Tests pass

([ed7874d4d71c78f](https://github.com/Lob2018/SudokuFX/commit/ed7874d4d71c78f66845b989ec1336659997653b))

♻️ refactor GridMaster: Validate GrilleResolue before returning
- Introduced Jakarta Bean Validation on GrilleResolue to ensure integrity
- Added intermediate variable for validation before return
- No change in business logic, only added robustness
- Tests pass

([4d06480ac3dcc96](https://github.com/Lob2018/SudokuFX/commit/4d06480ac3dcc9661fde28f684146525fd71cf8e))

🔧 chore pom flyway-database-hsqldb, pom maven-compiler-plugin: Update

([0a3eb4bd357a2b1](https://github.com/Lob2018/SudokuFX/commit/0a3eb4bd357a2b1b14988eacd9e9b086de505331))

📚 docs: DOXYGEN

([d34b1f5c15b7ee9](https://github.com/Lob2018/SudokuFX/commit/d34b1f5c15b7ee96660fca7eecfbb932ce78a15e))

✨ feat: Add &quot;Open log file&quot; button in HelpViewModel
- Update Paths enum from LOGS_FILE_NAME(&quot;SudokuFX.log&quot;) to LOGS_FILE_NAME_PATH(&quot;/SudokuFX.log&quot;)
- Added handling and button for opening log file in HelpViewModel
- Updated HelpViewModelUTest and CoordinatorUTest to cover new button
- Updated related .properties entries
- Tests pass

([28e6fd9c5a954e8](https://github.com/Lob2018/SudokuFX/commit/28e6fd9c5a954e8f3f7220c9ff475e01b53aed85))

♻️ refactor .properties: Remove unused key menu.button.help.dialog.information.donation

([8fce321fba524ca](https://github.com/Lob2018/SudokuFX/commit/8fce321fba524ca3537790fb2811e821ecb16dcb))

♻️ refactor: Remove donation buttons in HelpViewModel et README:
- Tests pass

([64e844b91068c80](https://github.com/Lob2018/SudokuFX/commit/64e844b91068c80b1fc7dba8fd943647273fa5dc))

📝 style GenerateSecretUTest: Run automatic code formatter

([9d026562edcd150](https://github.com/Lob2018/SudokuFX/commit/9d026562edcd150792c80d1059ccffef8970fd1d))

🐛 fix GenerateSecretUTest, SecretKeyEncryptionServiceAESGCMUTest : Replace hardcoded secrets and AES key with SecureRandom and repeat-based generation

([b22005722c4da2d](https://github.com/Lob2018/SudokuFX/commit/b22005722c4da2dbede02540642ce74f59696f73))

📚 docs README: Replace outdated Snyk test badge with link to monitored project (authentication required)

([1ea58e69c6fa425](https://github.com/Lob2018/SudokuFX/commit/1ea58e69c6fa425ecad28c2449ed6c4574294266))

📚 docs: update DOXYGEN and README with Ko-fi donation button

([8c53ee91da891e2](https://github.com/Lob2018/SudokuFX/commit/8c53ee91da891e2d2429a2d2e79eb41b09612cdf))

✅ test PlayerLanguageServiceUTest
- Tests pass

([419d27cac08f579](https://github.com/Lob2018/SudokuFX/commit/419d27cac08f579f59041788b40f3e38418a532f))

♻️ refactor HelpViewModel, HelpViewModelUTest, .properties: Replace &quot;Support my apps&quot; label with &quot;Make a voluntary donation&quot; for legal clarity
- Tests pass

([9ae68743eafa91f](https://github.com/Lob2018/SudokuFX/commit/9ae68743eafa91fa747db6c6814f3b06541767b2))

📚 docs: DOXYGEN

([dac65fbe000ac0a](https://github.com/Lob2018/SudokuFX/commit/dac65fbe000ac0aeb24c039e8e63fc466f437823))

🔧 chore pom.xml spring-boot-starter-parent : Update

([2561beba37d7e86](https://github.com/Lob2018/SudokuFX/commit/2561beba37d7e86996b6d2630fb9a93bcf538730))

✨ feat HelpViewModel, Coordinator, Urls, HelpViewModelUTest, CoordinatorUTest, .properties: Add &quot;Support my apps&quot; button in Help menu, opening my Ko-fi URL

([ba08844837c123c](https://github.com/Lob2018/SudokuFX/commit/ba08844837c123c5d4c3b430f40be568958a3a36))

🐛 fix FUNDING.yml: Syntax error

([ffb736f51dc30ad](https://github.com/Lob2018/SudokuFX/commit/ffb736f51dc30ad4b24170cc4f40c1cad6ca7e95))

📚 docs FUNDING.yml: Moved from github Sponsor to Ko-fi

([eecbc350d153c0a](https://github.com/Lob2018/SudokuFX/commit/eecbc350d153c0aaff92db37dad3fae43caf608d))

✅ test GridViewModelUTest: Enrich with completed grid toast verification
- Tests pass

([fdd072a259466f3](https://github.com/Lob2018/SudokuFX/commit/fdd072a259466f3529cf62fed3af8fabf028192b))

✨ feat: GridViewModel, CurrentGrid, PlayerService, MainView, DifficultyLevel, Dto withers, AbstractPlayerStateTest: Read and Update the grid and its associated difficulty level via PlayerDto
- GridViewModel
  - Added persistence for player-modifiable grids via &#x60;PlayerService&#x60;.
  - Added &#x60;suppressCellsListeners&#x60; flag to avoid triggering listeners during bulk updates.
  - Added &#x60;persistGridValue()&#x60; to update &#x60;PlayerDto&#x60; with current grid values.
  - Added &#x60;getCurrentGridFromModel()&#x60; to load the default and current grid state.
  - Updated &#x60;setValues()&#x60; to optionally adjust editability and suppress listeners.
  - Replaced &#x60;applyLevel()&#x60; with &#x60;setCurrentGridWithLevel()&#x60; to persist newly generated grids.
  - Added &#x60;persistNewGame()&#x60; to save grids, difficulty levels, and completion percentages to the player model.
- CurrentGrid
  - New record to encapsulate loaded grid state: difficulty level and completion percentage.
  - Jakarta validation annotations ensure &#x60;level&#x60; is non-null and &#x60;percentage&#x60; is between 0 and 100.
- PlayerService
  - Extended to persist associated &#x60;Game&#x60;, &#x60;GameLevel&#x60;, and &#x60;Grid&#x60; entities when updating a player.
  - Added &#x60;GameRepository&#x60;, &#x60;GridRepository&#x60;, &#x60;GameLevelRepository&#x60; dependencies.
  - Updated &#x60;updatePlayer()&#x60; to handle grid updates, level changes, and maintain Jakarta validation.
- MainView
  - &#x60;gridInitialization()&#x60; now loads the current grid from the model if available.
  - Difficulty handlers (&#x60;handleEasyLevelShow()&#x60;, &#x60;handleMediumLevelShow()&#x60;, &#x60;handleDifficultLevelShow()&#x60;) now use &#x60;setCurrentGridWithLevel()&#x60; for persistence.
  - Calls &#x60;applyOpaqueMode()&#x60; after applying a difficulty level to maintain UI grid opacity.
- DifficultyLevel
  - Added &#x60;fromGridByte(byte)&#x60; method to resolve a difficulty level from its byte representation.
  - Logging added via SLF4J when &#x60;fromGridByte()&#x60; encounters invalid input.
  - Maintains existing &#x60;toGridNumber()&#x60; conversion for enum-to-integer mapping.
- Test
  - Adapt AbstractPlayerStateTest with a GameDto
  - Tests pass

([1509ee8215779f7](https://github.com/Lob2018/SudokuFX/commit/1509ee8215779f7dee2ad30b7874eaddd6461ba5))

✨ feat: Replace Sponsor button by Website button

([c003dad4eecb778](https://github.com/Lob2018/SudokuFX/commit/c003dad4eecb7788dea26b26c94b27e61162cfb1))

♻️ refactor ApplicationKeystore: Add the keystore pass as LOG.info

([ac2932083dbdfcb](https://github.com/Lob2018/SudokuFX/commit/ac2932083dbdfcb268d8655d12914234088739e4))

🐛 fix Coordinator: Add NPE check for iso in pdatePlayerLanguage(String iso)
- Test pass

([b85cc2b2f57ba42](https://github.com/Lob2018/SudokuFX/commit/b85cc2b2f57ba426bbeaa686ad1e24db2b034c40))

📝 style HelpViewModel, HelpViewModelUTest: Run automatic code formatter

([e887bc55b5d829f](https://github.com/Lob2018/SudokuFX/commit/e887bc55b5d829fee62a8b3ec790e60b6ef318ea))

♻️ refactor HelpViewModel, HelpViewModelUTest: Add null checks and update JavaDoc for addSponsorButton() and displayAlert()
- Tests pass

([14282c8fcf0b6d2](https://github.com/Lob2018/SudokuFX/commit/14282c8fcf0b6d2d583d1079aadcaa75d3076152))

✨ feat HelpViewModel, MyAlert: Add hand cursor to alert buttons
- In &#x60;MyAlert&#x60;, add &#x60;applyHandCursorToButton()&#x60; to set the cursor to HAND for all buttons.
- In &#x60;HelpViewModel&#x60;, update &#x60;displayAlert()&#x60; to call &#x60;applyHandCursorToButton()&#x60; before showing the alert.
- Ensures both standard (e.g. OK) and dynamically added buttons (e.g., &quot;Become a sponsor&quot;) display the hand cursor.
- Tests pass

([a9a8843586793d3](https://github.com/Lob2018/SudokuFX/commit/a9a8843586793d3cc9a6d27dc8aaef7f0deda554))

🐛 fix Urls: Add GITHUB_SPONSOR_URL

([a6304f4414ee38d](https://github.com/Lob2018/SudokuFX/commit/a6304f4414ee38d3e93dfd69b03eefd026f042b6))

🐛 fix Coordinator: Add openGitHubSponsorUrl method

([00f3af9cd4e2daa](https://github.com/Lob2018/SudokuFX/commit/00f3af9cd4e2daab1cc3a705cbdda20c29f4ee97))

🐛 fix HelpViewModelUTest: Remove
useless comment

([d0c78db1ad939a2](https://github.com/Lob2018/SudokuFX/commit/d0c78db1ad939a2b973c85ccb52443b3611d9104))

✨ feat HelpViewModel, HelpViewModelUTest, .properties: Add Become a sponsor button to the help info Alert

([74078d08e144e73](https://github.com/Lob2018/SudokuFX/commit/74078d08e144e73c397d8211f1f80a6721cf10f1))

📚 docs README: Change sponsor badge color from pink to gree

([7f0f764178dc712](https://github.com/Lob2018/SudokuFX/commit/7f0f764178dc712d9f2e2323b517ca77edcff751))

Update README.md

([33e21a163ab13be](https://github.com/Lob2018/SudokuFX/commit/33e21a163ab13bee0b119ac49023d1ec412828ca))

🔧 chore FUNDING.yml, README: Add GitHub Sponsors badge to README and create FUNDING.yml

([c622515f6c470f2](https://github.com/Lob2018/SudokuFX/commit/c622515f6c470f29a191817c920f987d0ec7aad4))

🔧 chore pom maven-surefire-plugin: Update

([7d8c1754365f952](https://github.com/Lob2018/SudokuFX/commit/7d8c1754365f952025a0fd199cc52b1c21eb894a))

🔧 chore MY_LICENSE_TO_EDIT.txt: Add Oracle JavaFX Ensemble 8 Fireworks attribution - code adapted for Sudoku completion celebration

([5f2b6ebe7a776c2](https://github.com/Lob2018/SudokuFX/commit/5f2b6ebe7a776c20fc2d80c577d7d75d371ec590))

♻️ refactor GridViewModel: Improve grid verification and victory handling
- GridViewModel:
  * Extracted parseCellValue(String) to centralize conversion from cell text to integer.
  * Renamed implicit &quot;win&quot; logic to celebrateVictory() for clarity and professional naming.
  * verifyGrid() now calls parseCellValue() and triggers celebrateVictory() when grid is fully solved.
  * celebrateVictory() displays a toast message and optionally plays the player&#x27;s configured victory audio.
  * Added i18n key toast.msg.gridviewmodel.completed&#x3D;Félicitations {0} ! Vous avez résolu la grille !
  * Improved Javadoc for verifyGrid(), parseCellValue(), and celebrateVictory().
  * Minor TODOs in init() updated to differentiate test vs production behavior.
- Tests pass

([9efe8f22c94b3cc](https://github.com/Lob2018/SudokuFX/commit/9efe8f22c94b3ccf9b8b2115a5f4d9eae2a84c76))

📚 docs: DOXYGEN

([e82e554f542520e](https://github.com/Lob2018/SudokuFX/commit/e82e554f542520e341ecd54c8a80345b12e86295))

🐛 fix ExceptionTools, OsFolderInitializer, VersionService, AsyncFileProcessorService, FileChooserService, GridViewModel, PlayerStateHolder, MenuOptionsViewModel, VersionServiceITest, PlayerStateHolderUTest: Prefix all LOG.error messages with ██ Exception
- Tests pass

([a472b259b72136a](https://github.com/Lob2018/SudokuFX/commit/a472b259b72136a0d7b008af6748d0c6f61f9643))

✨ feat Coordinator, MainView, i18n, CoordinatorUTest: Handle exceptions and ensure rollback in toggleLanguage
- Coordinator
  * toggleLanguage now requires a non-null ToasterVBox.
  * Exceptions during player language update are caught, logged, and displayed as a toast.
  * Application language and player state remain unchanged if an exception occurs (rollback).
  * Extracted updatePlayerLanguage for clarity and single-responsibility.
- MainView
  * handleToggleLanguage now passes ToasterVBox to Coordinator.toggleLanguage.
- CoordinatorUTest
  * Injects a ToasterVBox for testing.
  * Existing tests updated to call toggleLanguage(toaster).
  * Added test to verify exception handling, toast display, and rollback behavior.
- Added i18n key toast.error.coordinator.toggleLanguage for error messages.
- All tests pass.

([793b392a5a70d7f](https://github.com/Lob2018/SudokuFX/commit/793b392a5a70d7f67f3ecaa12bd68f6a8625a417))

🐛 fix i18n: Useless property

([38ce3091c2b626d](https://github.com/Lob2018/SudokuFX/commit/38ce3091c2b626d02453d9e93be800bb4e540415))

✨ feat PlayerDto, Coordinator, PlayerLanguageRepository, PlayerLanguageService, MainView, CoordinatorUTest: Persist and retrieve player&#x27;s language, enhance DTO immutability, and improve unit tests
- PlayerLanguageRepository:
  * Added custom query method Optional&lt;PlayerLanguage&gt; findByIso(String iso) for ISO-based retrieval.
- PlayerLanguageService:
  * Introduced service to manage PlayerLanguage entities.
  * Provides retrieval by ISO code (&quot;FR&quot; or &quot;EN&quot;).
  * Maps entities to PlayerLanguageDto via IPlayerLanguageMapper.
  * Validates DTOs using JakartaValidator.
  * Throws IllegalArgumentException if the requested ISO code is not found.
  * Enables higher-level components to persist and retrieve the player&#x27;s language.
- PlayerDto:
  * Added &#x60;withXXX&#x60; methods to create modified copies of PlayerDto while keeping it immutable.
  * Methods include withPlayerLanguage, withOptions, withMenu, withSelectedGame, withName, withSelected, withCreatedAt, withUpdatedAt.
  * Supports functional-style updates without mutating existing PlayerDto instances.
- Coordinator:
  * Added &#x60;getCurrentPlayerLanguageIso()&#x60; to retrieve the current player&#x27;s language ISO code.
  * Enhanced &#x60;toggleLanguage()&#x60; to persist the updated language via PlayerService, refresh PlayerStateHolder, and switch I18n locale bundle.
  * Added setter methods for PlayerService, PlayerLanguageService, and PlayerStateHolder for testing purposes.
- MainView:
  * Updated &#x60;initialize()&#x60; method to set the application&#x27;s locale at startup based on the current player&#x27;s language.
  * Preserves all existing initialization logic for audio, menus, active menu manager, and grid.
- CoordinatorUTest:
  * Refactored setup to inject mocks via setter methods instead of using reflection.
  * Added mocks for PlayerService, PlayerLanguageService, and PlayerStateHolder to test language persistence and retrieval.
  * Validates &#x60;toggleLanguage()&#x60; behavior with current player and language updates.
  * Preserves existing tests for FXML loading, scene management, dynamic font size, HostServices, and exception handling.
- Tests pass

([345e92c7c6c4c66](https://github.com/Lob2018/SudokuFX/commit/345e92c7c6c4c6601f738ed382a1c1b5e9d91c2e))

♻️ refactor MenuOptionsViewModel: Avoid unnecessary persistence for unchanged color and image paths
- &#x60;applyAndPersistOptionsColor&#x60; now checks if the new color differs from the current one before updating the database.
- &#x60;applyAndPersistBackgroundImage&#x60; and &#x60;persistImagePath&#x60; avoid persisting image paths if unchanged.
- Improved error handling and logging for background image application.
- Maintains UI updates and shows appropriate info/error toasts.
- Tests pass

([d516ceb02b2fc26](https://github.com/Lob2018/SudokuFX/commit/d516ceb02b2fc26e9cf7ff3cf148d2b9deaef871))

📚 docs: DOXYGEN

([0ca67cccbd7dcda](https://github.com/Lob2018/SudokuFX/commit/0ca67cccbd7dcda83a085dbc5464933b290b5ded))

✨ feat MenuOptionsViewModel: Apply and persist background image asynchronously with i18n
- Implemented background image persistence with proper handling of valid and invalid files.
- Asynchronously load, resize, and convert the image via AsyncFileProcessorService to avoid UI blocking.
- Added &#x60;persistImagePath&#x60; method to encapsulate persistence logic and error handling.
- Shows info toast on successful application; shows error toast and persists empty path on failure.
- Added i18n keys for persistence errors:
  - toast.error.optionsviewmodel.imagepathsavererror
  - toast.error.optionsviewmodel.imagepathclearerror
- Refactored method documentation for conciseness and clarity.
- Tests pass

([1f29ba5a95dee77](https://github.com/Lob2018/SudokuFX/commit/1f29ba5a95dee77bd59cad5efdcca6d3825fcb18))

✨ feat MenuOptionsViewModel: Persist background color with alpha and handle errors
- Updated &#x60;applyAndPersistOptionsColor&#x60; to persist the new background color including alpha (RRGGBBAA) in &#x60;OptionsDto&#x60;.
- Replaced TODO persistence logic with actual call to &#x60;OptionsService#updateOptions&#x60;.
- Added exception handling: logs errors and displays an error toast if persistence fails.
- Ensures the GridPane background is updated on success while keeping the UI responsive.
- Javadoc updated to reflect the hex color format and behavior.
- Added i18n key for color error toast: &#x60;toast.error.optionsviewmodel.colorerror
- Tests pass

([cfeac5f6bb099de](https://github.com/Lob2018/SudokuFX/commit/cfeac5f6bb099de078dc3337809f1e8ce1f75879))

✨ feat MenuOptionsViewModel: Persist grid opacity state with error handling and i18n toasts
- Updated &#x60;toggleGridOpacityAndPersist&#x60; to persist the grid opacity state in &#x60;OptionsDto&#x60;
- Added rollback logic: on failure, the property is restored to its previous value, exceptions are logged, and an error toast is shown.
- On success, the property is updated, and an info toast indicates the new state.
- Added i18n keys for user feedback:
  - toast.msg.optionsviewmodel.opaque.on
  - toast.msg.optionsviewmodel.opaque.off
  - toast.error.optionsviewmodel.opacityerror
- Tests pass

([06e86a0dbd1ed3b](https://github.com/Lob2018/SudokuFX/commit/06e86a0dbd1ed3b8b4704d21f87dceb1b36bc5c9))

✨ feat MenuOptionsViewModel: Persist sound mute state with user-friendly toasts
- &#x60;persistSongPath&#x60; now logs exceptions and shows detailed error toasts on failure.
- &#x60;toggleMuteAndPersist&#x60; persists the sound mute state in &#x60;OptionsDto&#x60;, updates &#x60;AudioService&#x60;, and shows informative toasts on success or failure.
- Added i18n keys for sound state and errors:
  - toast.msg.optionsviewmodel.sound.off
  - toast.msg.optionsviewmodel.sound.on
  - toast.error.optionsviewmodel.sounderror
- Both methods ensure the UI reflects changes immediately while handling errors gracefully.
- Tests pass

([836fdf6ccab605a](https://github.com/Lob2018/SudokuFX/commit/836fdf6ccab605a1698d3f84dc94b07c7c9e7a2b))

✨ feat OptionsDto: Add wither methods
- Tests pass

([261a6f08dba3d0e](https://github.com/Lob2018/SudokuFX/commit/261a6f08dba3d0e9c0c5b40d466540258f98825a))

📚 docs MainView: Update optionsMenuInitialization JavaDoc

([cc6e77e05bb5464](https://github.com/Lob2018/SudokuFX/commit/cc6e77e05bb54645581ed60a23e1398d36a7b246))

🐛 fix MainView, MenuOptionsViewModel: Pseudo-class binding for song button
- Ensure that the REDUCED_SONG_PSEUDO_SELECTED pseudo-class correctly reflects the songIsBlankProperty state, including when using BooleanBinding.not().
- Create an intermediate BooleanBinding to avoid timing/evaluation issues when applying pseudo-class at initialization.

([11c01b776fb867d](https://github.com/Lob2018/SudokuFX/commit/11c01b776fb867d3cde52e3ec395b1bfddf6593d))

♻️ refactor MenuOptionsViewModel: Add NPE checks and update JavaDoc
- Tests pass

([30699f351d378de](https://github.com/Lob2018/SudokuFX/commit/30699f351d378dea6a65a6f294102b174d5a18c8))

📚 docs: DOXYGEN

([18a21aa651b2f30](https://github.com/Lob2018/SudokuFX/commit/18a21aa651b2f308a29533dd39ca6e222397f2ab))

♻️ refactor MCD.drawio.png, MPD.drawio.png, Options, OptionsDto, OptionsService, MenuOptionsViewModel, MenuPlayerViewModel: Remove is.. prefixes and drop unused isimage field in options
- MCD.drawio.png
  - Updated entity diagrams to remove all &#x60;is..&#x60; prefixes.
  - Reflects deletion of &#x60;isimage&#x60; from &#x60;options&#x60;.
- MPD.drawio.png
  - Updated physical model diagrams to match changes in entities.
  - Removed &#x60;isimage&#x60; field in &#x60;options&#x60;.
- Options
  - Removed &#x60;is..&#x60; prefixes from boolean attributes.
  - Deleted &#x60;isimage&#x60; field.
- OptionsDto
  - Updated field names to align with entity changes.
- OptionsService
  - Adapted without &#x60;isimage&#x60; field.
- MenuOptionsViewModel
  - Updated to apply background color when no image path is set.
  - Updated to apply and persist background image when image path is provided.
- MenuPlayerViewModel
  - Adapted without &#x60;isimage&#x60; field.
- Tests updated accordingly.
- Tests pass.

([53cecf0e46d2d95](https://github.com/Lob2018/SudokuFX/commit/53cecf0e46d2d95820068f86c659be65cc50c005))

♻️ refactor MenuOptionsViewModel: TODO

([5e52e51d33bcc43](https://github.com/Lob2018/SudokuFX/commit/5e52e51d33bcc437db46cdcbd26428e89d5cb48f))

🐛 fix Options, Player, Game, OptionsDto, PlayerDto, GameDto, PlayerRepository, MenuOptionsViewModel, MenuSaveViewModel, V1_0__create_base.sql, V1_1__insert_initial_values.sql, GameUTest, PlayerUTest, OptionsUTest, OptionsServiceUTest, PlayerServiceUTest, MenuSaveViewModelUTest: Boolean fields inconsistencies causing incorrect values in DTOs
☑ Root cause
  playeroptions columns &#x60;isopaque&#x60; and &#x60;ismuted&#x60; had default false in DB,
  conflicting with initial SQL inserts that set them to true.
☑ Fix
  - Renamed boolean columns and entity fields for consistency:
    playeroptions: isimage → image, isopaque → opaque, ismuted → muted
    player &amp; game: isselected → selected
  - Updated all getters/setters, builder methods, constructors, and insert scripts
  - Updated PlayerDto, GameDto, OptionsDto, and associated tests
  - Updated PlayerRepository method &#x60;findSelectedPlayerWithSelectedGame&#x60;
  - MapStruct warnings no longer occur due to consistent naming
☑ Update or add tests
  - Tests updated to reflect renamed fields and corrected default values
- Tests pass

([545690d7fc9b43c](https://github.com/Lob2018/SudokuFX/commit/545690d7fc9b43c21054e80590d8d2d6233f3369))

✨ feat MenuOptionsViewModel, MainView, MenuOptionsViewModelUTest: Rename methods, and load playerStateHolder options from database
- Tests pass (AbstractPlayerStateTest updated)

([ac2048cb3b2e40d](https://github.com/Lob2018/SudokuFX/commit/ac2048cb3b2e40d8579aa0b457c5f1164ce1fba6))

📚 docs: DOXYGEN

([da4623e81a15316](https://github.com/Lob2018/SudokuFX/commit/da4623e81a153168611cae7ba5d35634016a41d1))

📚 docs README: Update view package structure with util, list, and toaster inside Package structure chapter

([9740294b1b9ae9e](https://github.com/Lob2018/SudokuFX/commit/9740294b1b9ae9e99bcbf45025f9d61c5a511c78))

🔧 chore CI/CD codeql-action: Update

([738324edd615285](https://github.com/Lob2018/SudokuFX/commit/738324edd6152854c4bdab5653d4d57b01e81202))

✨ feat BindingConfigurator, BindingConfiguratorUTest: add keyboard support to ColorPicker configuration
- Add configureColorPickerKeyboardSupport() method with SPACE/ENTER to open and ESCAPE to close popup
- Integrate keyboard support into configureColorPicker() for automatic configuration
- Update JavaDoc to reflect keyboard interaction support
- Add unit tests to verify keyboard event handler configuration and exception handling
-Improves accessibility by enabling keyboard navigation for ColorPicker components.
-Test pass

([f7befefe779456f](https://github.com/Lob2018/SudokuFX/commit/f7befefe779456fecd423b55d6c0e8fb557971cf))

🐛 fix views: Add missing Javadoc
- Tests pass

([00458f844c3dd23](https://github.com/Lob2018/SudokuFX/commit/00458f844c3dd23df27bfb30a6bbe187e2ec5cbf))

🐛 fix repositories: Add missing Javadoc
- Tests pass

([5570501b806629c](https://github.com/Lob2018/SudokuFX/commit/5570501b806629cb560a9c620c380780dd7036d4))

🐛 fix models: Add missing Javadoc

([af9b78582b148d2](https://github.com/Lob2018/SudokuFX/commit/af9b78582b148d24db8c5829f3406efafc6d5569))

♻️ refactor MainView: Useless import and variable

([2a187273f1898fd](https://github.com/Lob2018/SudokuFX/commit/2a187273f1898fd672fc4dcf0596fd44c4465346))

♻️ refactor MenuOptionsViewModel: Constructor to reduce method length below 100 lines
- Tests pass

([358a6369f384738](https://github.com/Lob2018/SudokuFX/commit/358a6369f3847380c2226ffd6dd761f2d9e3c8d9))

📚 docs: DOXYGEN

([a2d0f593a5ead59](https://github.com/Lob2018/SudokuFX/commit/a2d0f593a5ead59d94a3ca5e2608f4e63a63df6b))

♻️ refactor BindingConfigurator, GenericListViewFactory, MainView, BindingConfiguratorUTest, GenericListViewFactoryUTest, pom: Modularize UI setup and improve coverage
- BindingConfigurator.java
  - Introduced utility class to centralize JavaFX property bindings.
  - Replaces inline binding logic with reusable methods.
- GenericListViewFactory.java
  - Added factory class to encapsulate ListView configuration for players and games.
  - Handles clipping, cell factory setup, and selection synchronization.
  - Introduces new behavior for consistent UI setup.
- MainView.java
  - Refactored to delegate ListView and binding setup to new factory and configurator.
  - Functional change: replaces manual configuration with modular components.
- BindingConfiguratorUTest.java
  - Added unit tests for binding logic.
  - Validates synchronization and property linkage.
- GenericListViewFactoryUTest.java
  - Added unit tests for ListView configuration.
  - Covers valid and null input scenarios for both player and game views.
  - Ensures selection binding and item population behave as expected.
- pom.xml
  - Updated JaCoCo exclusions to refine coverage scope.
  - Replaced broad &#x60;view/**&#x60; exclusion with targeted paths (&#x60;view/component&#x60;, &#x60;view/main&#x60;, &#x60;SplashScreenView&#x60;, &#x60;CrashScreenView&#x60;).
- Tests pass.

([9907c80e0a5f1ae](https://github.com/Lob2018/SudokuFX/commit/9907c80e0a5f1ae48df3acdae6f9e881ee2ffb7a))

🔧 chore CI/CD codeql-action, harden-runner: Update

([92b076f5c4594d5](https://github.com/Lob2018/SudokuFX/commit/92b076f5c4594d5527862fa056cfffcf2b149310))

♻️ refactor README: Consolidate MainView into a single view

([5205ecf53187b1e](https://github.com/Lob2018/SudokuFX/commit/5205ecf53187b1e2d720639cf308ee1ea99aedeb))

♻️ refactor README: Add view.main for MainView and subviews

([82bc31f7fa6c8a7](https://github.com/Lob2018/SudokuFX/commit/82bc31f7fa6c8a76c5d276a6d7b11cfa2b96e2c2))

🔧 chore CI/CD codeql-action, flyway-database-hsqld, com.puppycrawl.tools checkstyle: Update

([27a3179a7a2ced4](https://github.com/Lob2018/SudokuFX/commit/27a3179a7a2ced4c5bb2559df8108942c47c0fbf))

♻️ refactor MainView, SudoMain, main-view.fxml: Move MainView to main package and update FXML paths
-MainView
Moved from fr.softsf.sudokufx.view to fr.softsf.sudokufx.view.main.
Updated Javadoc to reflect the new package location.
-SudoMain
Updated MAIN_VIEW_FXML_NAME from &quot;main-view&quot; to &quot;main/main-view&quot; to match the new FXML path.
-main-view.fxml
Updated fx:controller reference from fr.softsf.sudokufx.view.MainView to fr.softsf.sudokufx.view.main.MainView.
-No functional changes, only package reorganization and FXML path updates.
-Tests pass.

([ea5eaecd37f7ae7](https://github.com/Lob2018/SudokuFX/commit/ea5eaecd37f7ae7a22f3298e50e7ca4266be931d))

♻️ refactor default-view.fxml, crash-screen-view.fxml, DefaultView, SudoMain, Coordinator: Rename views and add constants for consistency
- default-view.fxml
  - Renamed to main-view.fxml.
  - Clarifies its role as the main entry view.
- crash-screen-view.fxml
  - Renamed to crashscreen-view.fxml.
  - Enforces consistent naming by removing unnecessary dash.
- DefaultView
  - Renamed class to MainView.
  - Updated Javadoc:
    - &quot;Default view class...&quot; → &quot;Main view class...&quot;
  - Updated logger declaration to reference MainView.
- SudoMain
  - Added constants for FXML view names:
    - &#x60;MAIN_VIEW_FXML_NAME &#x3D; &quot;main-view&quot;&#x60;
    - &#x60;CRASH_SCREEN_VIEW &#x3D; &quot;crash-screen-view&quot;&#x60;
  - Updated &#x60;createViewTransition&#x60; calls to use the new constants instead of hard-coded strings.
  - Improves maintainability and reduces risk of typos when referencing FXML files.
- Coordinator
  - Added constant &#x60;FXML_EXTENSION &#x3D; &quot;.fxml&quot;&#x60;.
  - Supports consistent handling of FXML files throughout the coordinator.
- No functional changes, only renaming and adding constants for clarity and consistency.

([ea17d3c24264e99](https://github.com/Lob2018/SudokuFX/commit/ea17d3c24264e9934beadbb34e36718bdfddfd74))

🔧 chore AsyncFileProcessorService: Remove useless comment

([80adbb2de5bdbff](https://github.com/Lob2018/SudokuFX/commit/80adbb2de5bdbfff8f8e6009162d0e162603ad34))

♻️ refactor AsyncFileProcessorService: Simplify async task handling and improve documentation
- Removed use of Optional in task result; propagate exceptions directly.
- Extracted error handling into a separate private method &#x60;handleError&#x60;.
- Added concise JavaDoc for class and all public/private methods.
- Ensures JavaFX UI updates (spinner/toast) run on the Application Thread

([a570dfc21d1c9ed](https://github.com/Lob2018/SudokuFX/commit/a570dfc21d1c9ed2065f56813165b762f7bef593))

🔧 chore checkstyle.xml: update LineLength ignorePattern
- Updated Checkstyle LineLength module to ignore records, annotations, and comments (added &#x27;|record|@.*|//.*&#x27; to ignorePattern).
- Ensures compatibility with Google Java Format AOSP while allowing long URLs, imports, package declarations, and certain Java constructs.

([49e81a9185c43a1](https://github.com/Lob2018/SudokuFX/commit/49e81a9185c43a16c05486347a794682e889abd2))

♻️ refactor GridCellViewModel, MenuPlayerViewModel, MenuSaveViewModel: Replace negation in conditions with positive logic

([32258b6fc07628b](https://github.com/Lob2018/SudokuFX/commit/32258b6fc07628b0fad4c57f08d75188341c4b36))

♻️ refactor checkstyle.xml: Refine negation operator check in if/else-if conditions
- Update RegexpSingleline rule to exclude &#x27;!&#x3D;&#x27; comparisons.
- Refine message to suggest positive logic if possible.

([fafb34f2305a4d3](https://github.com/Lob2018/SudokuFX/commit/fafb34f2305a4d3231dcbe91d6827bdb93e5e7c3))

✨ feat checkstyle.xml: Add rule to discourage negation operator in if/else-if
- Added RegexpSingleline check to prohibit &#x27;!&#x27; in if and else-if conditions
- Encourages use of positive logic for better readability and maintainability
- Severity set to warning

([4530f0a4f042b3f](https://github.com/Lob2018/SudokuFX/commit/4530f0a4f042b3f411a72dd4aeab2b363cfe1268))

📚 docs: DOXYGEN

([50eab0647aff0d1](https://github.com/Lob2018/SudokuFX/commit/50eab0647aff0d1800db7c66a7c24fd0daac6202))

📚 docs README: Add System requirements section

([149e633013de2f0](https://github.com/Lob2018/SudokuFX/commit/149e633013de2f0986be989aafd617969f21fb7d))

♻️ refactor: Remove negation operators for improved code readability and file path verifications
- Replaced &#x60;isBlank&#x60; checks with &#x60;isNotEmpty&#x60; for file paths
- Ensured positive logic flow with early returns where applicable
- Preserved original behavior while simplifying conditions

([4128c2978a3c810](https://github.com/Lob2018/SudokuFX/commit/4128c2978a3c810cd32ed669244788bb082f0dc0))

✅ test AbstractPlayerStateTest: Fix non-deterministic timestamps in AbstractPlayerStateTest
-Use fixed LocalDateTime instead of LocalDateTime.now() in createDefaultPlayer() to ensure reproducible test behavior

([b644056185c5ccf](https://github.com/Lob2018/SudokuFX/commit/b644056185c5ccf30a620d8b130d95973b59f395))

♻️ refactor AbstractPlayerStateTest: fix JavaFX properties behavior in AbstractPlayerStateTest
- Replace Mockito spy with real instance + testable subclass to preserve property bindings and listeners functionality during unit tests

([526b9b879b64730](https://github.com/Lob2018/SudokuFX/commit/526b9b879b6473029a584cbba4a8aeba21b8a423))

♻️ refactor MenuOptionsViewModel, GridViewModel, MenuOptionsViewModelUTest: Enforce file existence checks and robust audio handling
- MenuOptionsViewModel
  - loadBackgroundImage(File, SpinnerGridPane, GridPane):
    - Added check for file existence in addition to null and valid image.
    - Updated Javadoc to reflect existence requirement.
  - saveSong(File):
    - Added check for file existence in addition to null and valid audio format.
    - Updated Javadoc to reflect existence requirement.
  - Ensures that missing files are handled gracefully with error logging and toast notifications.
- GridViewModel
  - verifyGrid():
    - Added check for existence of audio file before playing.
    - Throws IllegalStateException if the file is missing or inaccessible.
    - Catches ResourceLoadException and IllegalStateException to log error and show toast.
    - Prevents silent failures when attempting to play a non-existent audio file.
- MenuOptionsViewModelUTest
  - Added tests to verify behavior when background image file exists or not:
    - givenValidFile_whenLoadBackgroundImage_thenAsyncServiceCalled: ensures AsyncFileProcessorService is invoked.
    - givenNonExistentFile_whenLoadBackgroundImage_thenToastErrorAndNoAsyncCall: ensures no async call occurs and an error toast is shown.
- Tests pass.

([2f50d5e360a93b7](https://github.com/Lob2018/SudokuFX/commit/2f50d5e360a93b73fcef8218319eb5c145a4f4b5))

✅ test PlayerStateHolderITest: Moved from unit to integration package

([6385142787c5688](https://github.com/Lob2018/SudokuFX/commit/6385142787c56887df1bc10b3a0dbc0075cc3860))

📚 docs: DOXYGEN

([b1afdcec2593d12](https://github.com/Lob2018/SudokuFX/commit/b1afdcec2593d127746aad3bddb291c27414c1b4))

♻️ refactor GridViewModel, MenuOptionsViewModel, PlayerStateHolder: Enhance testability and reactive bindings
- GridViewModel:
  - Added &#x60;verifyGrid()&#x60; call in GridCellViewModel textProperty listener for test coverage.
  - Refactored grid initialization and value verification logic.
  - Ensured proper audio playback and toast notifications on solved grid.
- MenuOptionsViewModel:
  - Initialized songProperty property from playerStateHolder in &#x60;init()&#x60;.
- PlayerStateHolder: no direct changes, now leveraged in tests via AbstractPlayerStateTest.
- AbstractPlayerStateTest:
  - Provides a mocked PlayerService and PlayerStateHolder spy.
  - Ensures safe setup of default PlayerDto for tests.
  - Base class now extended by GridViewModelUTest, MenuOptionsViewModelUTest, and PlayerStateHolderUTest.
- Tests:
  - GridViewModelUTest, MenuOptionsViewModelUTest, PlayerStateHolderUTest now extend AbstractPlayerStateTest.
  - Shared setup ensures consistent player state across all tests.
- Tests pass

([2b58cf98376a5d2](https://github.com/Lob2018/SudokuFX/commit/2b58cf98376a5d2654155d3c4049c052849b1cf5))

🐛 fix GridViewModel, MenuOptionsViewModel: Comment calls to currentPlayerProperty().get()
- Ensure DB test access is configured for proper execution

([4310fa8ce11f0b1](https://github.com/Lob2018/SudokuFX/commit/4310fa8ce11f0b1d2daf2138f8c8087f497c6113))

✨ feat OptionsService, PlayerService, GridViewModel, PlayerStateHolder, MenuOptionsViewModel, tests: Integrate service layer in MenuOptionsViewModel and enhance persistent options handling
- Add PlayerStateHolder and OptionsService dependencies for data persistence.
- Replace hardcoded TODO comments with actual service calls via updateSongPath().
- Initialize songProperty from current player&#x27;s options on construction.
- Extract song path update logic into reusable updateSongPath() method.
- Add proper error handling with toast notifications for database operations.
- Consolidate clearSong() and saveSong() to use shared updateSongPath() logic.
- Improve separation of concerns between UI state and business logic.
- Complements previous changes: OptionsService, PlayerService update, and PlayerStateHolder.refreshCurrentPlayer().
- Add unit and adapt PlayerStateHolderITest integration tests
- Tests pass

([af30270013c94a9](https://github.com/Lob2018/SudokuFX/commit/af30270013c94a9eb4435d67c757e950a1168b09))

🐛 fix GameLevelRepository, MenuRepository: ID moved from Long to Byte

([86a9d51cdc5e7a9](https://github.com/Lob2018/SudokuFX/commit/86a9d51cdc5e7a95c1bccf620c26686d7ae9cfb7))

📚 docs: DOXYGEN

([2ec3e5fcd6a0aab](https://github.com/Lob2018/SudokuFX/commit/2ec3e5fcd6a0aab27cfa4d2ce0d22482cf3e629b))

✅ test VersionServiceITest, PlayerServiceUTest, AsyncFileProcessorServiceUTest, AudioServiceUTest, FileChooserServiceUTest: Add services unit tests
- Test packages mirror service package structure
- Tests pass

([b85a4c887979384](https://github.com/Lob2018/SudokuFX/commit/b85a4c887979384030423d3f7fc98c67215c6ac6))

🔧 chore CI/CD setup-python: Update

([4a72443d17819bf](https://github.com/Lob2018/SudokuFX/commit/4a72443d17819bfb98789f7bce25c1e59e1eb6f8))

📚 docs: DOXYGEN

([9ee10a5e77ec443](https://github.com/Lob2018/SudokuFX/commit/9ee10a5e77ec443141a407d1c1e89e433c2b4d1c))

✨ Song selection and Song clear implemented
- Functionality works for selecting and clearing songs
- Success song path is not yet saved to the database
- Success song is not played when a Sudoku grid is correctly completed
- Tests still need to be completed

([74c40de8740829c](https://github.com/Lob2018/SudokuFX/commit/74c40de8740829ccbed28cac6276274d0bb957d0))

📚 docs: DOXYGEN

([dbbba417891961f](https://github.com/Lob2018/SudokuFX/commit/dbbba417891961fdb59c5f344fff28ae75e34d84))

✅ test pom.xml: Update JaCoCo plugin exclusions
- Added &#x60;AsyncFileProcessorService$*&#x60; and &#x60;VersionService$*&#x60; to the JaCoCo coverage exclusion list.
- Prevents generated inner classes and service threads from impacting test coverage metrics.
- Tests pass

([c40965ebea981b6](https://github.com/Lob2018/SudokuFX/commit/c40965ebea981b699da243d695646e0818c13c77))

♻️ refactor AudioUtils, ImageUtils, MenuOptionsViewModel,i18n: Add AudioUtils class with audio file validation and update MenuOptionsViewModel
- Introduced &#x60;AudioUtils&#x60; utility class to validate supported audio formats (MP3, WAV, AAC, M4A, AIF, AIFF) with null safety.
- Updated &#x60;MenuOptionsViewModel#saveSong(File)&#x60; to use &#x60;AudioUtils&#x60; for validation before saving song paths and displaying a confirmation toast.
- Added i18n entry &#x60;toast.error.optionsviewmodel.handlefileaudiochooser&#x60; for invalid audio files.
- Updated &#x60;ImageUtilsUTest&#x60; and added &#x60;AudioUtilsUTest&#x60; to cover valid, invalid, and null cases, ensuring consistency and proper exception messages.
- Corrected null-check exception messages in &#x60;ImageUtils&#x60; tests.
- Tests pass

([b2201dd2631dbdb](https://github.com/Lob2018/SudokuFX/commit/b2201dd2631dbdbc5c837cd2b530d5b1fcabb7f4))

✨ feat default-view.fxml, DefaultView, MenuOptionsViewModel, i18n, MenuOptionsViewModelUTest: Partial for save song
- Only Song button to choose the success song
- Tests pass

([e740064f5b6da83](https://github.com/Lob2018/SudokuFX/commit/e740064f5b6da83e6fdeee1f0c6b73baca7c09db))

♻️ refactor: Simplify async file processing and background image loading
- AsyncFileProcessorService.processFileAsync no longer shows a success toast internally; success feedback should be handled externally if needed.
- Replaced explicit if-else with Optional.ifPresentOrElse for cleaner success/failure handling.
- Errors remain handled internally (logged, toast shown, spinner hidden) without requiring an error callback.
- MenuOptionsViewModel.loadBackgroundImage updated to use the new processFileAsync signature.
- Added TODO to persist the loaded background image path externally, removing persistence logic from the UI method.
- Improves readability, maintainability, and separation of concerns for asynchronous file processing and background image handling.
- Tests pass

([89a8ab6740fea9a](https://github.com/Lob2018/SudokuFX/commit/89a8ab6740fea9a0b1f9789f8788b334de749184))

🐛 fix i18n: For toast.msg.optionsviewmodel.saved

([41adfccac65492a](https://github.com/Lob2018/SudokuFX/commit/41adfccac65492a1f0f410e3a056aed39a4fadd0))

♻️ refactor AsyncFileProcessorService, MenuOptionsViewModel: Remove explicit error handling
- Updated loadBackgroundImage to rely on AsyncFileProcessorService for internal error handling and success notifications.
- Removed onError callback and manual success toast from the method.
- JavaDoc updated to reflect that errors and success messages are handled internally.
- Tests pass

([7d9ac74267bceb8](https://github.com/Lob2018/SudokuFX/commit/7d9ac74267bceb828840e26223a2d87e44102594))

🔧 chore CI/CD release-action: Update

([9e178458bf7a9ef](https://github.com/Lob2018/SudokuFX/commit/9e178458bf7a9ef978774727f0d1bdf64686be6d))

♻️ refactor AsyncFileProcessorService: Make onError mandatory and update Javadoc
- onError callback is now required; removed @Nullable and null checks
- Added Objects.requireNonNull(onError, ...) to enforce the contract and prevent NPE
- Ensured onSuccess is also checked with requireNonNull
- Updated Javadoc to reflect that both onSuccess and onError must not be null
- Maintains existing UI behavior with spinner and toaster notifications
- Tests pass

([0ce82d03f5235cc](https://github.com/Lob2018/SudokuFX/commit/0ce82d03f5235cc8ea706f6ed8124a555e708473))

♻️ refactor FileChooserService: Enforce non-null ownerStage in chooseFile
- Updated chooseFile signature to require a non-null Stage instead of allowing null
- Added Objects.requireNonNull(ownerStage, ...) to enforce the contract
- Updated Javadoc: ownerStage is now mandatory, documented @throws NPE for both ownerStage and type
- Clarified return behavior: Optional.empty() is returned only when user cancels or an error occurs
- Tests pass

([e2b133eb284be67](https://github.com/Lob2018/SudokuFX/commit/e2b133eb284be67ba263cef7fc6ebdf57281ee71))

📚 docs: DOXYGEN

([933474b459ee29a](https://github.com/Lob2018/SudokuFX/commit/933474b459ee29a9a2daf9a1fcc874fbbd6217be))

📚 docs README: update project structure section
- Clarified &#x60;service&#x60; package with new subpackages:
  - &#x60;business&#x60; → core business logic
  - &#x60;external&#x60; → external API/system integrations
  - &#x60;ui&#x60; → UI-related services (audio, file processing, etc.)

([2c912d74e35a2e0](https://github.com/Lob2018/SudokuFX/commit/2c912d74e35a2e00de9e2fd6868f82e914754471))

🐛 fix i18n: update toast messages for file and audio errors
- Refined error message for background image file selection
- Added specific toast message for invalid audio files

([9ff205ebc85bda0](https://github.com/Lob2018/SudokuFX/commit/9ff205ebc85bda00cb37e27bafd9dafe7b7f3a75))

✅ test GridViewModelUTest, VersionServiceITest, AudioServiceUTest, PlayerServiceUTest, MenuNewViewModelUTest, MenuPlayerViewModelUTest, PlayerStateHolderUTest, MenuOptionsViewModelUTest: Updated

([7fec1fc49997e37](https://github.com/Lob2018/SudokuFX/commit/7fec1fc49997e37175a59f7aee2a1e22966f2b15))

🔧 chore PlayerStateHolder, MenuNewViewModel, MenuOptionsViewModel: Format and update imports
- Applied automatic code formatting
- Adjusted and optimized import statements

([d1b3a994983c598](https://github.com/Lob2018/SudokuFX/commit/d1b3a994983c598aa4250d210523df0c4a661958))

✨ feat GridViewModel: Integrate AudioService with ResourceLoadException handling
- Added ToasterVBox injection in GridViewModel for toast notifications
- Updated verifyGrid() to handle ResourceLoadException when playing victory song
- Replaced direct AudioService.playSong() calls with try-catch and toaster error reporting
- Added I18n and ToastLevels support for error messages

([3ab21c21f2976be](https://github.com/Lob2018/SudokuFX/commit/3ab21c21f2976be13482677dd0b057557da58294))

🔧 chore DefaultView: format and update imports
- Applied automatic code formatting to DefaultView
- Adjusted and optimized import statements

([dc30bfe640fe952](https://github.com/Lob2018/SudokuFX/commit/dc30bfe640fe95261875006539b25e486e45378e))

♻️ refactor: Reorganize services and update AudioService
- Moved services to sub-packages:
  • business: PlayerService
  • external: VersionService
  • ui: AudioService, AsyncFileProcessorService, FileChooserService
- AudioService:
  • Throws ResourceLoadException instead of RuntimeException on media load/play failure
  • Uses Objects.requireNonNull for file arguments
  • Updated JavaDoc with precise null/exception contracts

([8b66c2954412ece](https://github.com/Lob2018/SudokuFX/commit/8b66c2954412eceafe812a2bc05e681edab6b3c0))

📝 style ExceptionTools, ResourceLoadException: Run automatic code formatter

([7d926ce6983c617](https://github.com/Lob2018/SudokuFX/commit/7d926ce6983c61741a846e5f4396a7ed6d281213))

✨ feat ExceptionTools: Add ResourceLoadException support to ExceptionTools
- Adds &#x60;logAndInstantiateResourceLoad&#x60; to create and log &#x60;ResourceLoadException&#x60; for audio, image, or other resource load failures.
- Safely handles null or blank messages and null cause.
- Provides consistent logging and exception creation for resource-related errors.

([4435567c92d6bc1](https://github.com/Lob2018/SudokuFX/commit/4435567c92d6bc19c86abb7facc833119ebb884d))

✨ feat ResourceLoadException: add ResourceLoadException for checked resource load failures
- Introduces a checked exception &#x60;ResourceLoadException&#x60; for audio, image, or other resource types that fail to load or process.
- Provides constructors with message and optional cause.
- Ensures detailed error reporting while keeping exception handling explicit.

([af140a06538a49d](https://github.com/Lob2018/SudokuFX/commit/af140a06538a49d3bbe41f17cc49acf9ef796e62))

🐛 fix ExceptionTools: handle blank messages in logAndInstantiateIllegalArgument

([ff53aa22c1f0126](https://github.com/Lob2018/SudokuFX/commit/ff53aa22c1f0126683d8b744989569266925b1d3))

🔧 chore CI/CD codeql-action, release-action: Update

([a76ef09f95890e7](https://github.com/Lob2018/SudokuFX/commit/a76ef09f95890e7ca89788db6b541e4db39ca204))

📚 docs: DOXYGEN

([e81ed85f2cc32c5](https://github.com/Lob2018/SudokuFX/commit/e81ed85f2cc32c51bddbc89452bac90ff72ccb52))

🐛 fix DefaultView: Correct MenuOptionsViewModel reference in method Javadoc

([b1bfa246d1e6c35](https://github.com/Lob2018/SudokuFX/commit/b1bfa246d1e6c35984ae75d88cdca5cf4727c5c3))

♻️ refactor AsyncFileProcessorService, MenuOptionsViewModel, MenuOptionsViewModelUTest, i18n: Introduce AsyncFileProcessorService in MenuOptionsViewModel
- Replace direct image loading in MenuOptionsViewModel with AsyncFileProcessorService to handle asynchronous file processing with proper UI spinner and toaster notifications.
- Simplify loadBackgroundImage method to delegate image validation, resizing, and background application to the service.
- Update i18n keys for toast messages to reflect generic file processing instead of only images.
- Add new filechooser extension for audio files.
- Ensure grid opacity and mute toggles remain functional.
- NOTE: unit tests for MenuOptionsViewModelUTest.java need to be completed.

([ba1c9e2209b0eaa](https://github.com/Lob2018/SudokuFX/commit/ba1c9e2209b0eaa6953dbd3a1a6b121163ecbc72))

♻️ refactor FileChooserService, DefaultView, MenuOptionsViewModel, MenuOptionsViewModelUTest: Centralize file chooser logic and simplify background image loading
- Introduce FileChooserService to handle IMAGE and AUDIO file selection with null safety
- Update MenuOptionsView to use FileChooserService instead of ViewModel for file dialogs
- Rename ViewModel method to loadBackgroundImage and remove redundant file chooser code
- Centralize extension filter logic and improve maintainability
- Verified tests pass

([77fd80f4caaa101](https://github.com/Lob2018/SudokuFX/commit/77fd80f4caaa101181a8a6a793a87c7249133644))

📚 docs: DOXYGEN

([dce8f402180416a](https://github.com/Lob2018/SudokuFX/commit/dce8f402180416a7751bf104c0ecf17846413ef5))

✨ feat AudioService, DefaultView, MenuOptionsViewModel, default-view.fxml, i18n, MenuOptionsViewModelUTest: Add audio mute option to MenuOptions with i18n support
- Introduced &#x60;AudioService&#x60; dependency in &#x60;MenuOptionsViewModel&#x60;.
- Added &#x60;muteProperty&#x60; with corresponding StringBindings for accessibility, tooltip, text, and icon.
- Implemented &#x60;toggleMute()&#x60; method to update both &#x60;AudioService&#x60; and the &#x60;muteProperty&#x60;.
- Updated &#x60;init()&#x60; to initialize audio state on startup.
- Modified &#x60;default-view.fxml&#x60; to include &#x60;menuOptionsButtonMute&#x60; button:
  - Button displays mute icon and text.
  - Binds to &#x60;optionsMuteIcon&#x60; and &#x60;optionsMuteText&#x60;.
  - Calls &#x60;#handleMute&#x60; on action.
- Updated menu layout to include the mute button while preserving existing options (color, image, opacity).
- Added i18n support for the mute button.
- Verified tests pass

([95a4bec1e7563e0](https://github.com/Lob2018/SudokuFX/commit/95a4bec1e7563e014f52054d4dc912d7366e2d07))

📚 docs: DOXYGEN

([04f2dd1f2b1fc9b](https://github.com/Lob2018/SudokuFX/commit/04f2dd1f2b1fc9bbc1287fa70f7751f55b73cc21))

♻️ refactor PlayerStateHolder, MenuPlayerViewModel, PlayerStateHolderUTest, PlayerStateHolderITest, README: Rename InMemoryPlayer to PlayerStateHolder and update state holder documentation
- Renamed &#x60;InMemoryPlayer&#x60; → &#x60;PlayerStateHolder&#x60; to reflect its role as a state holder in MVVM.
- Updated Javadoc: emphasizes observable state, initialization via PlayerService, and automatic update propagation.
- Clarified that &#x60;viewmodel.state&#x60; contains state holders and updated README comments accordingly.
- ✅ All tests verified.

([eb873ad740db231](https://github.com/Lob2018/SudokuFX/commit/eb873ad740db231ed61eec987f36ef4c4169b0ca))

🐛 fix style.css: Adjust left/right padding for .menuList .list-cell to prevent unwanted horizontal scrollbar on small screens

([3476c25eaaaf9c5](https://github.com/Lob2018/SudokuFX/commit/3476c25eaaaf9c5af2e55b0d5dc58d25251ea3f7))

🐛  fix style.css: adjust #menuPlayerListView and #menuSaveListView height to display around ten items, also fixes scrollbar rendering on small windows

([abb7fda5d0d0439](https://github.com/Lob2018/SudokuFX/commit/abb7fda5d0d043900fc08e43dc0111b9d35257ab))

♻️ refactor style.css: Remove left menu padding and fix mini menu alignment to center interface elements

([8ca26babb06faea](https://github.com/Lob2018/SudokuFX/commit/8ca26babb06faea16801b5dca963a170f77d9062))

📝 style OptionsUTest: Run automatic code formatter

([2cd0ebbc1fc9cca](https://github.com/Lob2018/SudokuFX/commit/2cd0ebbc1fc9cca05bbad60ef8dbc8ed9f46b48d))

🐛 fix V1_1__insert_initial_values.sql, Options, OptionsDto: Align hexcolor format with database schema
- Updated Options.java to use 8-char ARGB hex format without &#x27;#&#x27; (e.g. FFFFFFFF)
         instead of 6/3-char with &#x27;#&#x27; (e.g. #000000).
- Adjusted validation regex and default value accordingly.
- Modified V1_1__insert_initial_values.sql to insert default playeroptions
         with &#x27;FFFFFFFF&#x27; instead of &#x27;000000ff&#x27; and ensured consistency with entity.
- Fixed initial player creation to reference the correct default options entry.
- Updated OptionsDto.java to enforce 8-character hexcolor format (min&#x3D;8, max&#x3D;8)
         and updated Javadoc to reflect new format (e.g., FFFFFFFF instead of #FFFFFF/#FFF).
- Updated OptionsUTest.java to reflect changes in Options validation and default hexcolor value.

([44041f76ce17184](https://github.com/Lob2018/SudokuFX/commit/44041f76ce17184c25e43b80fe0749f6b51e98e6))

♻️ refactor V1_1__insert_initial_values.sql: Update default hexcolor from black to white

([b21bf4ded1abc13](https://github.com/Lob2018/SudokuFX/commit/b21bf4ded1abc132e2041d5377e36d63173f9b5e))

📚 docs uc.png: Update Gérer les options audiovisuelles
- From Modifier l&#x27;activation du son to Activer ou désactiver le son

([b45ed92eb36d3c0](https://github.com/Lob2018/SudokuFX/commit/b45ed92eb36d3c05ad695cc27868e855f06611f7))

📚 docs uc.png: Update UCP7 Gérer les options audiovisuelles
-UC5 Moved from Modifier la musique du gagnant to Modifier la chanson de réussite

([229cd6a9ed32c97](https://github.com/Lob2018/SudokuFX/commit/229cd6a9ed32c97d66f27ba74225c50f706b4c51))

🐛 fix GridViewModel: Remove incorrect verifyGrid() call

([ab41b5196f8fbc4](https://github.com/Lob2018/SudokuFX/commit/ab41b5196f8fbc42b74236080f504ef546e3e545))

♻️ refactor DefaultView: Apply initial grid opacity state directly after ViewModel init.
Remove unnecessary listener since opacity changes are handled explicitly through handleGridOpacity() method.

([705ff13f8d96ff8](https://github.com/Lob2018/SudokuFX/commit/705ff13f8d96ff81bb4dc3ac19f9f3873eb857f8))

📚 docs SudokuFX_in_action.jpg: Update

([faf786344ba953a](https://github.com/Lob2018/SudokuFX/commit/faf786344ba953a854dfae576770c9d79500d62a))

✅ test MenuNewViewModelUTest: Remove unreliable MenuNewViewModel version check test
- Deleted &#x60;givenVersionService_whenCheckLatestVersion_thenIsUpToDateAndStatusMessageUpdated&#x60; from MenuNewViewModelUTest.
- The test frequently times out due to JavaFX &#x60;Platform.runLater()&#x60; threading issues.
- VersionService logic remains testable independently without JavaFX dependencies.

([841e22186cab7f6](https://github.com/Lob2018/SudokuFX/commit/841e22186cab7f6d6b638567176481f5114a52a5))

📚 docs: DOXYGEN

([bb1563385033799](https://github.com/Lob2018/SudokuFX/commit/bb1563385033799dcaf8561ab327907253c96809))

✅ test MenuNewViewModelUTest: Avoid field shadowing and mock VersionService for checkLatestVersion
- Renamed local &#x27;viewModel&#x27; to &#x27;localViewModel&#x27; to prevent hiding the class-level field.
- Uses a mocked VersionService and Task to reliably simulate &#x27;Up to date&#x27; status.
- Maintains CountDownLatch to verify async statusMessage updates.

([a5a767a2fe60889](https://github.com/Lob2018/SudokuFX/commit/a5a767a2fe60889b942a5e1da3354ca3cca8e85c))

✅ test AudioService, AudioServiceUTest: Add comprehensive unit tests for AudioService
 - Added AudioServiceUTest covering core functionalities:
 * playEffect with null, invalid, and valid files/keys, verifies MediaPlayer creation, playback, and disposal
 * playSong ensures MediaPlayer is created, looped, volume set, and playback started
 * Handles invalid song files by asserting MediaException is thrown
 * muteAll and unmuteAll behavior with correct volume adjustments for song and effects
 * stopAll verifies all MediaPlayers are stopped and disposed
 * setSongVolume and setEffectVolume clamps values between 0.0 and 1.0 and applies to active players
 * Ensures originalEffectVolumes are preserved/restored correctly during mute/unmute
 * Updated VALID_EFFECT_KEYS: added &quot;case_click&quot; alongside existing &quot;button_click&quot;
- Uses Mockito MockedConstruction for Media and MediaPlayer to avoid real file dependencies
- Uses ArgumentCaptor to validate volume values and playback interactions

([d87de46e5d01b42](https://github.com/Lob2018/SudokuFX/commit/d87de46e5d01b422dd5d816f12447beeb19bf5ad))

📚 docs: DOXYGEN

([a853bde8216bbe7](https://github.com/Lob2018/SudokuFX/commit/a853bde8216bbe76c9995680846d0ede1181dda6))

🔧 chore dependency-review-action: Update

([6eae695918f94ae](https://github.com/Lob2018/SudokuFX/commit/6eae695918f94ae391bcdb3073f0422cf4ef829f))

✨ feat pom.xml, AudioService, DefaultView, GridViewModel, GridViewModelUTest: Add AudioService with song/effect playback and stop-on-exit
- Added JavaFX Media dependency in pom.xml for audio support.
- Implemented AudioService to handle songs, short effects, muting, and volume control.
- Ensures MediaPlayer instances are disposed after playback to prevent memory leaks.
- Added stopAll() to stop and dispose all audio resources safely.
- DefaultView now registers a listener to stop all audio when the primary stage is closed.

([c80a544a20106b0](https://github.com/Lob2018/SudokuFX/commit/c80a544a20106b00f985f53745ad42878e9e3cc2))

✨ feat: Add reactive grid opacity icon, dynamic bindings and i18n updates
- FXML: added fx:id &quot;menuOptionsButtonOpacityIcon&quot; to the Text node inside menuOptionsButtonOpacity graphic.
- View: added @FXML field menuOptionsButtonOpacityIcon and bound its textProperty to menuOptionsViewModel.optionsOpacityIconProperty().
- View: replaced static applyOpaqueMode call with listener on menuOptionsViewModel.gridOpacityProperty() for dynamic UI updates.
- ViewModel: replaced boolean isOpaqueMode with BooleanProperty gridOpacityProperty.
- ViewModel: added reactive StringBindings for optionsOpacityIcon, optionsOpacityText, optionsOpacityAccessibleText, and optionsOpacityTooltip that update automatically with locale and opacity changes.
- ViewModel: updated toggleGridOpacity() and init() to use gridOpacityProperty.
- i18n: updated opacity keys to support dynamic state:
  - &quot;menu.options.button.opacity.accessibility&quot; now includes selected state ({0}).
  - Added &quot;menu.options.button.opacity.text.opaque&quot; and &quot;menu.options.button.opacity.text.transparent&quot; for dynamic labeling.

([a7e16ef4d897d36](https://github.com/Lob2018/SudokuFX/commit/a7e16ef4d897d36384c97d61d169f0803843b3ed))

♻️ refactor .properties, MenuLevelViewModel, MenuLevelViewModelUTest: Consolidate accessibility labels and tooltips for difficulty buttons
- Move menu.accessibility.button.easy/medium/difficult from maxi menu section to global accessibility section
- Remove duplicate accessibility definitions from maxi menu buttons
- Share common accessibility labels and tooltips between mini and maxi menu types
- Standardize accessibility label structure across all menu contexts
- Maintain backward compatibility with existing accessibility patterns
- Tests pass

([5eb6c6754e25f6e](https://github.com/Lob2018/SudokuFX/commit/5eb6c6754e25f6ee9666c2131160dd484789f9b3))

📚 docs: DOXYGEN

([304a35b94327f97](https://github.com/Lob2018/SudokuFX/commit/304a35b94327f97980e3d1173a5dee2506016cf1))

✅ test OptionsUTest, PlayerServiceUTest, MenuPlayerViewModelUTest, InMemoryPlayerUTest: Adjusted to match updated Options model

([dfec29b200c9604](https://github.com/Lob2018/SudokuFX/commit/dfec29b200c9604849cd403ee24a2e0eccc8843f))

♻️ refactor MenuPlayerViewModel: Update MenuPlayerViewModel to match OptionsDto model

([1d7e9483cc2eb1a](https://github.com/Lob2018/SudokuFX/commit/1d7e9483cc2eb1a47d4122421b2df09a392cb874))

📝 style Options: Run automatic code formatter

([379a229b2d125db](https://github.com/Lob2018/SudokuFX/commit/379a229b2d125dbe685a4fb0ea679a6a19294f84))

♻️ refactor OptionsDto: Adjust OptionsDto to match Options model
- Added &#x27;songpath&#x27; and &#x27;ismuted&#x27; fields to reflect the current Options entity.
- Updated JavaDoc to describe all fields accurately.
- Applied validation annotations consistent with Options constraints.

([85101bb7365b54a](https://github.com/Lob2018/SudokuFX/commit/85101bb7365b54a57e997a4444fee2d0f2c79771))

✅ test OptionsUTest: Update Options unit tests to match entity changes
- Adjusted tests to include new &#x60;songpath&#x60; and &#x60;ismuted&#x60; fields.
- Updated validations and constructors to reflect MAX_PATH_LENGTH constraint.
- Ensured builder and setter methods are properly tested with new parameters.

([54d745564d9bd2e](https://github.com/Lob2018/SudokuFX/commit/54d745564d9bd2e0bf81e8932bc0482a7f9f18a4))

🐛 fix sql: Reduce column length for imagepath and songpath in playeroptions
- Changed &#x60;imagepath&#x60; and &#x60;songpath&#x60; columns from VARCHAR(1024) to VARCHAR(260) to match application-level MAX_PATH_LENGTH constraint.
- Ensures database schema is consistent with JPA entity validation.

([711cd72c6185934](https://github.com/Lob2018/SudokuFX/commit/711cd72c61859342ff5de6b7833eafcae2e3d832))

♻️ refactor Options: Add songpath and ismuted, unify path validation
- Added &#x60;songpath&#x60; and &#x60;ismuted&#x60; fields with validation and JPA mappings.
- Introduced &#x60;MAX_PATH_LENGTH&#x60; constant to enforce max length for &#x60;imagepath&#x60; and &#x60;songpath&#x60;.
- Updated &#x60;validatePath&#x60; method to check for null and maximum length (replaces old &#x60;validateImagepath&#x60;).
- Updated constructors, getters, setters, and builder to include &#x60;songpath&#x60; and &#x60;ismuted&#x60; with validation.
- Updated &#x60;equals&#x60;, &#x60;hashCode&#x60;, and &#x60;toString&#x60; methods to include &#x60;songpath&#x60; and &#x60;ismuted&#x60;.
- Renamed boolean getters to follow &#x60;isX()&#x60; convention (&#x60;isImage&#x60;, &#x60;isOpaque&#x60;, &#x60;isMuted&#x60;).
- Updated JPA column definitions to use &#x60;MAX_PATH_LENGTH&#x60; for &#x60;imagepath&#x60; and &#x60;songpath&#x60;.
- Ensured default values and validation are consistent with application constraints.

([a073fe0b91af73c](https://github.com/Lob2018/SudokuFX/commit/a073fe0b91af73c0e45a0782de2ae39182fed032))

✨ feat sql: Add songpath and ismuted columns to playeroptions table

([0b0827d5f2f820f](https://github.com/Lob2018/SudokuFX/commit/0b0827d5f2f820f3064a68a82d83fe060f0c67ac))

📚 docs MCD.png, MPD.png: Add audio management properties

([edd5a257c5d9041](https://github.com/Lob2018/SudokuFX/commit/edd5a257c5d90413e24b710b078577c99f81aec7))

♻️ refactor uc.png: Expand audiovisual options from single UC to complete package

([74cd94484c2aa37](https://github.com/Lob2018/SudokuFX/commit/74cd94484c2aa377b158ce87ac1ec6b234f8e135))

📝 style: Run automatic code formatter

([0ce4853e2e104b2](https://github.com/Lob2018/SudokuFX/commit/0ce4853e2e104b229a02adb68b85072a9584e6f9))

📚 docs README: Create Use section with launch instructions and gameplay overview
- Added &quot;Launch and play SudokuFX&quot; instructions for Windows, Linux, and MacOS
- Specified usage with and without Java Runtime Environment (JRE) included
- Included Basic gameplay, Features, and Troubleshooting subsections

([533f5cf372a6e94](https://github.com/Lob2018/SudokuFX/commit/533f5cf372a6e94e8aa3b32a166e00aa75cf64ee))

📚 docs README.md, CONTRIBUTING.md: Migrate Feedback section from README to CONTRIBUTING and enhance issue reporting
- Move Feedback section from README.md to CONTRIBUTING.md
- Expand into comprehensive &quot;Filing an Issue&quot; section
- Add detailed guidelines for bug reports and feature requests
- Document application log paths for all platforms (Windows/Linux/MacOS)
- Update table of contents and internal link references
- Improve formatting and presentation of log file locations

([9a1fff9ea33a312](https://github.com/Lob2018/SudokuFX/commit/9a1fff9ea33a3127458876691c18889740bce96a))

✨ feat ApplicationKeystore: validate alias parameter in setCredentials method
Add proper error handling for invalid/unknown alias values using ExceptionTools validation instead of silently returning empty string.

([1bbe6f6f7a993ee](https://github.com/Lob2018/SudokuFX/commit/1bbe6f6f7a993eedb42a292caf25459b4319e3fb))

🔧 chore pom.xml spring-boot et flyway-database-hsqldb: Update

([214ac93bc3aba32](https://github.com/Lob2018/SudokuFX/commit/214ac93bc3aba32e155804b1f3ec16ea3476fcad))

🐛 fix: Rename options table to playeroptions to avoid SQL reserved keyword
Update all SQL statements (CREATE, INSERT, SELECT) to use new table name

([533158a10f6b264](https://github.com/Lob2018/SudokuFX/commit/533158a10f6b264726951efa837d949cc1b0f64b))

📚 docs: DOXYGEN

([7ed4dfefd89653c](https://github.com/Lob2018/SudokuFX/commit/7ed4dfefd89653ca57a95837d8882b89dc5ac36e))

♻️ refactor SudokuFX_in_action.jpg, MCD.png, MPD.png, uc.png, IGameLevelMapper, IOptionsMapper, IPlayerMapper, OptionsDto, PlayerDto, Options, Player, OptionsRepository, PlayerRepository, DefaultView, ActiveMenuOrSubmenuViewModel, MenuMiniViewModel, MenuOptionsViewModel, MenuPlayerViewModel, V1_0__create_base.sql, V1_1__insert_initial_values.sql, default-view.fxml, resource_en_US.properties, resource_fr_FR.properties, and tests: Rename Background table to Options across application
- Database: rename Background table to Options with Flyway migration
- UI: update menu item, FXML references, views and view-models
- Code: update Model, Repository, DTO, Mapper classes
- Docs: refresh UML, Merise diagrams and README screenshot
- Config: update i18n properties and Jacoco exclusions

([724380d2be0c698](https://github.com/Lob2018/SudokuFX/commit/724380d2be0c698c874653fc620470381123771f))

📚 docs: DOXYGEN

([1997785924a6aa7](https://github.com/Lob2018/SudokuFX/commit/1997785924a6aa7e589e2fe22b1c5302e272947c))

♻️ refactor ImageUtils, ImageUtilsUTest, MenuBackgroundViewModel, ImageMeta : Integrate ImageMeta for efficient background image loading
- Updated MenuBackgroundViewModel to use ImageMeta for asynchronous image scaling.
- Replaced direct Image width/height scaling with getImageMeta(File) to avoid fully loading images in memory.
- LoadImage now runs on a daemon thread, ensuring it doesn&#x27;t block app shutdown.
- Preserved existing bindings, accessibility properties, and error handling.
- Minor cleanup: removed previous tempImage-based scaling approach.
- Added sample.jpg in src/test/resources for unit tests.
- Updated ImageUtilsTest:
  - calculateImageScaleFactor tests now use sample.jpg instead of remote URLs.
  - createBackgroundImage test uses sample.jpg and validates scaled width/height.

([6d81a7c06626d5e](https://github.com/Lob2018/SudokuFX/commit/6d81a7c06626d5e5371973fd2eba162ad82c05e8))

✅ test sample.jpg: Add sample.jpg for ImageUtils unit tests
- Added sample.jpg in src/test/resources to provide a local image for unit tests.
- Enables testing of image scaling and background image creation without relying on external URLs.

([ed8d910d455bec9](https://github.com/Lob2018/SudokuFX/commit/ed8d910d455bec9e1d7975d49694d0f82dccc709))

🔧 chore CI/CD codeql-action: Update

([b3061bf94c0bd23](https://github.com/Lob2018/SudokuFX/commit/b3061bf94c0bd230756f722698bcd0abc13ceb85))

🐛 fix style.css: Remove duplicate rules

([678f723cbd92473](https://github.com/Lob2018/SudokuFX/commit/678f723cbd92473f47adbe6726c3412c839e1996))

♻️ refactor style.css: Update opaque-mode with solid black border and rounded corners

([d6d7daa7ff50d57](https://github.com/Lob2018/SudokuFX/commit/d6d7daa7ff50d57a7a8b34cf6342d2035c566c4c))

📚 docs: DOXYGEN

([de4605e25fe7f32](https://github.com/Lob2018/SudokuFX/commit/de4605e25fe7f320fc649a8e481ff7f90e4284c3))

♻️ refactor IOsFolder, OsFoldersConfig, OsInitializedFolders, CrashScreenView , HelpViewModel : unify OS folder handling
- Renamed IOsFolderFactory → IOsFolders, OsFolderFactoryManager - OsInitializedFolders now implements IOsFolders directly
- OsFolderInitializer returns OsInitializedFolders
- Updated Spring bean to provide IOsFolders based on detected OS
- Removed OS-specific factory classes (LinuxFolderFactory, WindowsFolderFactory, MacosFolderFactory)
- Fixed sealed class usage

([50af8fa92b7c7ef](https://github.com/Lob2018/SudokuFX/commit/50af8fa92b7c7eff91f2f25334d20a0b895670cb))

📚 docs: DOXYGEN

([b5f69aef9c6ef11](https://github.com/Lob2018/SudokuFX/commit/b5f69aef9c6ef1162e0b37660e9dca943e90a26f))

🔧 chore CI/CD setup-java: Update

([9d01f08b254d163](https://github.com/Lob2018/SudokuFX/commit/9d01f08b254d163172c56f7211ff5ee672b242c7))

✨ feat Background: add isopaque property to background entity
- Update SQL schema to include &#x60;isopaque&#x60; column in the &#x60;background&#x60; table with default value TRUE (not null).
- Extend &#x60;BackgroundDto&#x60; to include &#x60;isopaque&#x60; field.
- Update &#x60;Background&#x60; entity:
  - Add &#x60;isopaque&#x60; property with default TRUE.
  - Add getter, setter, builder support, equals/hashCode, and toString updates.
- Ensure consistency between DTO, entity, and schema.

([88faa784e77d20a](https://github.com/Lob2018/SudokuFX/commit/88faa784e77d20a0b8bb072d61d857d086dc7197))

📚 docs MCD &amp; MPD: Add isopaque attribute to background entity

([a67c80d91f5f6d6](https://github.com/Lob2018/SudokuFX/commit/a67c80d91f5f6d63f21e0a6b0643d27b07193077))

✨ feat DefaultView, MenuBackgroundViewModel, default-view.fxml, I18n, style.css: Implement persistent grid opacity toggle
- Add grid opacity state to MenuBackgroundViewModel with possible database persistence
- Initialize and apply grid opacity from database on startup
- Add CSS class manipulation for opaque/transparent modes
- Enable dynamic grid background switching via toggle button
- Update documentation

([276e0d2322654d2](https://github.com/Lob2018/SudokuFX/commit/276e0d2322654d285c044e8bb234bfaeb165e1e0))

♻️ refactor DefaultView, MenuBackgroundViewModel: separate FileChooser concerns between View and ViewModel
- Move file selection logic to MenuBackgroundViewModel
- Keep UI components handling in View layer
- Add proper error handling and documentation
- Use I18n for FileChooser.ExtensionFilter labels

([6e3a468c4986fc1](https://github.com/Lob2018/SudokuFX/commit/6e3a468c4986fc110062cd3db4bd286fe800f9bd))

✨ feat I18n: Add filechooser.extension&#x3D;Image Files

([fdc9e0b8cd2bef6](https://github.com/Lob2018/SudokuFX/commit/fdc9e0b8cd2bef6f383223bdc7616f22558b7b1a))

🐛 fix DefaultView: Prevent intermittent FileChooser crash on Windows
- Add try-catch block to handle FileChooser exceptions
- Set initial directory to user home to avoid Windows path issues
- Add error logging for debugging purposes

Fixes the -805306369 exit code error that occurred randomly when opening file chooser dialog.

([74580efc24a2fb5](https://github.com/Lob2018/SudokuFX/commit/74580efc24a2fb59782a8313a74791295e82c981))

📚 docs SudokuFX_in_action.jpg: Update

([0007d43ecb00050](https://github.com/Lob2018/SudokuFX/commit/0007d43ecb00050d1917fd10c0553b753cc36d35))

♻️ refactor style.css: Mini menu adjustment
-Use translate-x to ensure consistent rendering with 3x3 block separation.

([70c0b9a1c68f8b7](https://github.com/Lob2018/SudokuFX/commit/70c0b9a1c68f8b7113d790a7f7efb54808c5a31b))

♻️ refactor GridCellViewModel, style.css: Transparent borders and resized grid to match mockup
- Updated cell and block separators to use transparent borders instead of black
- Resized and translated the grid so 3×3 blocks align correctly with the design mockup
- Improved border scaling for consistent rendering across different cell sizes
- Refined JavaDoc for getBorderStyle

([99c5c2c919f7c92](https://github.com/Lob2018/SudokuFX/commit/99c5c2c919f7c925426b86ff634361dd2ba8c73c))

📝 style GrillesCrees: Run automatic code formatter

([288550364596998](https://github.com/Lob2018/SudokuFX/commit/2885503645969982d457501b4086a1885142edd5))

🐛 fix GrilleResolue, GrillesCrees: Remove redundant overrides record
- Compiler-generated implementations already handle arrays correctly
- Add @SuppressWarnings(&quot;java:S6218&quot;) for SonarQube warning

([225fc4954bfbc82](https://github.com/Lob2018/SudokuFX/commit/225fc4954bfbc82cfd780a3607c9a4a29a382d47))

📚 docs SudokuFX_in_action.jpg: Update

([a77abc95bc10346](https://github.com/Lob2018/SudokuFX/commit/a77abc95bc10346dd30e006dc5971b961bb8ebcc))

🔧 chore CI/CD codeql-action, dependency-review-action: Update

([bb37f5753c62b07](https://github.com/Lob2018/SudokuFX/commit/bb37f5753c62b076b170320a2e0638895c19e346))

✨ feat SplashScreenView, style.cs, CrashScreenView: Add rounded corners to splash and crash screens, reduce crash screen size
- Add rounded corners styling to splashscreenvbox and crashscreenvbox
- Set stage style to TRANSPARENT for proper rounded corner display
- Apply rounded corners to splash screen and main app sharing the same stage
- Reduce crash screen stage size to 80% of original dimensions
- Update CSS with background-radius and border-radius properties

([e049f61bc32f40f](https://github.com/Lob2018/SudokuFX/commit/e049f61bc32f40faacc3054e48b7e1fa0a5391ae))

🔧 chore pom.xml/flyway-database-hsqldb: Update

([7e66bc690bf29fe](https://github.com/Lob2018/SudokuFX/commit/7e66bc690bf29fec0b89f642a9a9aea1571c01ce))

♻️ refactor MenuPlayerViewModel: Extract constant for player accessibility key

([aacbb517e324c02](https://github.com/Lob2018/SudokuFX/commit/aacbb517e324c02c5fd8f2c5a1739e81a47ad980))

🐛 fix PlayerUTest: fix Player equality test by using fixed timestamp

([ec49413b122f289](https://github.com/Lob2018/SudokuFX/commit/ec49413b122f2893f108fb1352e5bab4e8b8e53c))

✅ test PlayerUTest: Enhance PlayerUTest coverage
- Added builder tests (all fields &amp; minimal fields)
- Extended equals/hashCode tests (self, null, non-Player, copy, same/different IDs)
- Added setter tests (name, isselected, updatedat, games handling)
- Added toString() test ensuring fields are included
- Suppressed warning for equals self-comparison

([a245d3cdad87a07](https://github.com/Lob2018/SudokuFX/commit/a245d3cdad87a0755d5a153c1375592a5783cc77))

🐛 fix BackgroundUTest, GameLevelUTest, GameUTest, GridUTest: Add needed @SuppressWarnings

([414b3cffa1b5b9d](https://github.com/Lob2018/SudokuFX/commit/414b3cffa1b5b9d0393294b8bd68871597b75254))

✨ feat Player, PlayerDto, PlayerUTest: Implement Player entity with builder pattern and validation
-Add Player JPA entity with required fields and relationships
-Implement builder pattern, equals/hashCode/toString
-Add unit tests for creation, validation, and equality
-Handle edge cases and enforce not-null constraints

([914be26fd92e2e5](https://github.com/Lob2018/SudokuFX/commit/914be26fd92e2e5299f7ed6cd4164c683e1ae057))

🐛 fix Menu: Add @Column(name &#x3D; &quot;mode&quot;, nullable &#x3D; false) for consistency

([882a21e5ee23a16](https://github.com/Lob2018/SudokuFX/commit/882a21e5ee23a1620f1eb3013acd963e466610b6))

📝 style GameLevel: Run automatic code formatter

([6e7f89591b087cd](https://github.com/Lob2018/SudokuFX/commit/6e7f89591b087cd8e995f5787ddee20a20db6862))

🐛 fix Grid: Add @Column with nullable and length constraints

([82acaf6917fe3ef](https://github.com/Lob2018/SudokuFX/commit/82acaf6917fe3efe361269bd97abaae4bc50659b))

🐛 fix GameLevel: Add @Column(name &#x3D; &quot;level&quot;, nullable &#x3D; false) on level field

([0f354a390b64429](https://github.com/Lob2018/SudokuFX/commit/0f354a390b644290f39e05cee8a9177c95ff4667))

🐛 fix V1_0__create_base.sql: make Game isselected column NOT NULL in SQL migration

([bfd4b690541c3ec](https://github.com/Lob2018/SudokuFX/commit/bfd4b690541c3ec2e57014fe0c0955a0f4033a93))

🐛 fix Background, Game, GameLevel, Grid, Menu, PlayerLanguage: Add @Column(nullable &#x3D; false) to all @Id fields

([2e9cb75ccdcb9b0](https://github.com/Lob2018/SudokuFX/commit/2e9cb75ccdcb9b081170c564578c358786217da9))

🐛 fix Game: Add @Column(nullable &#x3D; false) to Game boolean and timestamp fields
- isselected, createdat, and updatedat explicitly mapped as NOT NULL

([23cbecf29e303fc](https://github.com/Lob2018/SudokuFX/commit/23cbecf29e303fcbb2855a6f9f5c3f7f325ed32e))

🐛 fix Background: Add @Column(nullable &#x3D; false) to Background.isimage
- Explicitly map isimage field to a NOT NULL column

([87e1cf357752cfe](https://github.com/Lob2018/SudokuFX/commit/87e1cf357752cfe762f713b5282c490032c7bb8c))

🐛 fix Backgroud, V1_0__create_base.sql: table and entity constraints
- Make imagepath NOT NULL in SQL schema
- Add @Column annotations with length and nullable constraints to hexcolor and imagepath in Background entity

([a1f825aad39b6f6](https://github.com/Lob2018/SudokuFX/commit/a1f825aad39b6f6f18e4a5fd1c62d9128161bf62))

✨ feat PlayerLanguage, PlayerLanguageDto, PlayerLanguageUTest: Implement PlayerLanguage entity with builder pattern and validation
- Add PlayerLanguage JPA entity with ISO field
- Implement builder pattern, equals/hashCode/toString
- Add unit tests for creation and validation
- Handle edge cases and enforce ISO constraints (FR/EN)

([47e554674460b7e](https://github.com/Lob2018/SudokuFX/commit/47e554674460b7eb742fe5dc116755610a45e1ea))

✅ test GridUTest: Refactor GridUTest for clarity and maintainability
- Simplified test names and @DisplayName descriptions to be more concise
- Grouped related assertions into single tests (constructor, builder, setters)
- Unified equals/hashCode/toString tests with consistent structure
- Replaced redundant equals checks with clearer assertions (Object instead of String)
- Verified boundary values and null handling in dedicated tests
- Ensured builder null validations are explicitly tested
- Removed unnecessary verbosity for better readability

([5b29d3fd8e7173f](https://github.com/Lob2018/SudokuFX/commit/5b29d3fd8e7173f8f2488e5b700be686ae3039fe))

✨ feat Menu, MenuDto, MenuUTest: Implement Menu entity with builder pattern and tests
- Add Menu JPA entity (Byte menuid, byte mode with @Min/@Max validation)
- Implement builder pattern, equals/hashCode/toString methods
- Add 11 tests
- Handle edge cases: null menuid, boundary values, extreme byte values

([4b596009b37f966](https://github.com/Lob2018/SudokuFX/commit/4b596009b37f9665601891264858433e92b25916))

📝 style: Run automatic code formatter

([aa7b2d17c44fb00](https://github.com/Lob2018/SudokuFX/commit/aa7b2d17c44fb00af89690976ce60c90aa26c294))

🐛 fix GameUTest: Extract builder setup outside assertThrows() to ensure only one invocation can throw runtime exception

([d692ba3c87a1d9b](https://github.com/Lob2018/SudokuFX/commit/d692ba3c87a1d9bcbc9d9ff56f099e3af3053830))

🐛 fix GameUTest: Arguments to &#x27;assertNotEquals()&#x27; in order

([d19864091ae8079](https://github.com/Lob2018/SudokuFX/commit/d19864091ae8079fb1a8f84e6bdd7f4532b4ada8))

📝 style GameUTest: Run automatic code formatter

([727607b93e42e49](https://github.com/Lob2018/SudokuFX/commit/727607b93e42e49fa519c4f407de853da22b12b1))

🐛 fix Game, Player: add safe player detachment to Game entity
- Add Game.detachFromPlayer() method for null assignment without validation
- Remove @Nonnull from getPlayerid() getter to support detached state
- Update Player.removeGame() to use detachFromPlayer()
- Add unit tests for detachment scenarios

Fixes: SonarQube java:S2637 in Player.removeGame()

([60b15a46114e42f](https://github.com/Lob2018/SudokuFX/commit/60b15a46114e42fd0d1324b9089a4c0cfc95e765))

📝 style MenuNewViewModelUTest: run automatic code formatter

([9374b64492dcc19](https://github.com/Lob2018/SudokuFX/commit/9374b64492dcc19057efef22784d97b4b7109538))

♻️ refactor Game: Include all relevant fields
- Includes gameid, isselected, gridid, playerid, and levelid in the string representation.
- Ensures a readable and complete output for logging and debugging.

([635a3ddbd33eee9](https://github.com/Lob2018/SudokuFX/commit/635a3ddbd33eee9a3282d37265cb0c87d5cec8ad))

🐛 fix GameUTest: toString test by stubbing all necessary mock getters
- Added stubs for Grid, Player, and GameLevel mocks to return expected values.
- Ensures Game.toString() does not return null and all relevant fields are included in the test.

([fc2f3c646a93035](https://github.com/Lob2018/SudokuFX/commit/fc2f3c646a930352a67e81f54ad8369b8d911cb5))

📚 docs MenuNewViewModelUTest: Unused comment

([83bae13989e7869](https://github.com/Lob2018/SudokuFX/commit/83bae13989e78696b5d5168265226137f5eb0531))

🐛 fix MenuNewViewModelUTest: Correct a race condition in MenuNewViewModelUTest. The test now works by centralizing mock setup and ViewModel instantiation in @BeforeEach, ensuring that the asynchronous task is synchronized and preventing timeouts.

([6461944ab55c241](https://github.com/Lob2018/SudokuFX/commit/6461944ab55c24150d5e5afb9d913e0415cd0e78))

🐛 fix Background, Game, GameLevel, Grid: all toString() methods using String.format with correct type specifiers
- Background: replaced string concatenation with String.format
- Grid: ensured gridid (Long) uses %s and possibilities (byte) uses %d
- Game: replaced string concatenation with String.format, adjusted Grid, Player, and GameLevel fields to use %s
- GameLevel: replaced string concatenation with String.format, levelid (Byte) uses %s, level (byte) uses %d

This fixes IllegalFormatConversionException and makes all toString() methods type-safe and consistent.

([678fa5386d69bad](https://github.com/Lob2018/SudokuFX/commit/678fa5386d69bad2646477269258b2d384894cfa))

♻️ refactor Background, Game, Grid: toString using String.format

([9365c039e1b94e5](https://github.com/Lob2018/SudokuFX/commit/9365c039e1b94e5fdc02e11f07cad8f6c0eadfa5))

🔧 chore pre-commit-hooks: Update

([c62b60d513d2bb5](https://github.com/Lob2018/SudokuFX/commit/c62b60d513d2bb50cf4f3270f6747280841dc255))

✅ test GridUTest: Verify builder, validation, equals, hashCode, and toString

([be5213d71b74d86](https://github.com/Lob2018/SudokuFX/commit/be5213d71b74d86f8f2db19c83a1aa0380d65756))

♻️ refactor Grid, GridDto: add null checks, use primitives, override equals/hashCode/toString, and enhance builder

([d3b251b3c9f3d36](https://github.com/Lob2018/SudokuFX/commit/d3b251b3c9f3d365a2d465d08f09f10a701e3f9c))

✅ test GameLevelUTest: verify builder, validation, equals, hashCode, and toString

([f5af10995e65885](https://github.com/Lob2018/SudokuFX/commit/f5af10995e658850d8aa962a3890e734264672ff))

♻️ refactor GameLevel, GameLevelDto (from Byte to byte) : simplify GameLevel entity
- Replace nullable Byte &#x27;level&#x27; with primitive byte for simplicity.
- Remove @NotNull annotation.
- Make default constructor protected.
- Add equals, hashCode, and toString methods based on levelid and level.
- Expand builder to support primitive byte type.

([b93bc0c094ec6ce](https://github.com/Lob2018/SudokuFX/commit/b93bc0c094ec6ce845173af1b1910ce7dd0987d0))

📝 style BackgroundUTest: run automatic code formatter

([14821027e632996](https://github.com/Lob2018/SudokuFX/commit/14821027e6329965ced6ca62dc3a4b0b547a40ea))

✅ test GameUTest: add comprehensive tests for Game entity
- Verify constructor, builder, and setters enforce non-null constraints.
- Ensure correct object creation and field assignments.
- Cover all validation methods for Grid, Player, GameLevel, createdAt, and updatedAt.

([edc464eb22a2b3c](https://github.com/Lob2018/SudokuFX/commit/edc464eb22a2b3c5391dcae9c4dbbb5870967e74))

♻️ refactor Game: centralize null checks in Game entity
- Replaced inline &#x60;Objects.requireNonNull&#x60; checks in constructors, setters, and builder with dedicated private validation methods (&#x60;validateGrid&#x60;, &#x60;validatePlayer&#x60;, &#x60;validateLevel&#x60;, &#x60;validateCreatedAt&#x60;, &#x60;validateUpdatedAt&#x60;) for better readability and maintainability.
- Updated GameBuilder methods and setters to use these validation methods.
- Added Javadoc comments for validation methods and GameBuilder class.

([860bc38fbb22a7c](https://github.com/Lob2018/SudokuFX/commit/860bc38fbb22a7cb33a3d91fb8a8b38fd3570f54))

✅ test pom.xml: ensure entity tests cover validation, equals, and hashCode; no longer exclude model package in JaCoCo

([2f5d3f58c62c310](https://github.com/Lob2018/SudokuFX/commit/2f5d3f58c62c31076eeb142b260711809c7b13b1))

✅ test BackgroundUTest: Optimized

([0182d42971ec2c7](https://github.com/Lob2018/SudokuFX/commit/0182d42971ec2c7f4eef934befab19304b425c52))

♻️ refactor MenuNewViewModelUTest: simplify MenuNewViewModelUTest
- Replace individual StringBinding checks with a BindingCheck record and a loop over locales
- Remove unnecessary Task delays and Platform.runLater in versionService mock
- Simplify checkLatestVersion test by directly asserting updated status and isUpToDate
- Clean up imports and remove unused code

([cc9c2cc01a9bef7](https://github.com/Lob2018/SudokuFX/commit/cc9c2cc01a9bef7988b1772a1874562bb8c5ec52))

✅ test: add comprehensive unit tests for Background entity
- Test constructor validation with valid/invalid parameters
- Test hex color format validation (#RGB, #RRGGBB patterns)
- Test image path validation (null checks)
- Test Builder pattern functionality with defaults and custom values
- Test setter methods with validation logic
- Test equals(), hashCode(), and toString() methods
- Test edge cases (null ID, maximum length strings, case sensitivity)
- Use given_when_then naming convention for test methods
- Group tests with @Nested classes for better organization

([565f33c5f494e10](https://github.com/Lob2018/SudokuFX/commit/565f33c5f494e108efad4b01e9204b331f5d0bdb))

📚 docs: DOXYGEN

([b69a192f873853b](https://github.com/Lob2018/SudokuFX/commit/b69a192f873853bdd5dbb988284ff81ba30d8c1e))

🔧 chore CI/CD qodana-action codeql-action checkout, POM.XML flyway-database-hsqldb checkstyle: Update

([17f55cf604b0fe3](https://github.com/Lob2018/SudokuFX/commit/17f55cf604b0fe349576fefa9de4e202c747de32))

♻️ refactor: Spring initialization task to remove circular dependency
- &#x60;createInitializationTask&#x60; now accepts a Class&lt;?&gt; parameter instead of hardcoding SudoMain.class
- &#x60;SudoMain&#x60; passes &#x60;this.getClass()&#x60; to the initializer
- Keeps asynchronous execution with proper JavaFX success/failure callbacks

([cba61e6393bf8d4](https://github.com/Lob2018/SudokuFX/commit/cba61e6393bf8d47c2d6c5281182d18298e9b970))

♻️ refactor Background: Entity code deduplication and modernization
☑ Functionality changes:
- Centralized validation logic in private static methods
- Enhanced hex color validation with regex pattern
- Added comprehensive equals(), hashCode(), and toString() methods
- Improved builder pattern with deferred validation
- Updated to modern Java pattern matching for instanceof
☑ Code quality improvements:
- Reduced code duplication from 25.5% to &lt;5%
- Added concise English JavaDoc documentation
- Consistent error messaging with predefined constants
- Better separation of concerns in validation logic
☑ Verify tests pass:
- All existing functionality preserved
- MapStruct compatibility maintained with isimage field
- No breaking changes to public API
- Constructor and setter validation behavior unchanged

([2253a30c1bd70e9](https://github.com/Lob2018/SudokuFX/commit/2253a30c1bd70e9e7064c3d3229969a25624881a))

📚 docs: DOXYGEN

([665f72f380d8e5b](https://github.com/Lob2018/SudokuFX/commit/665f72f380d8e5b52af834ca6c2dde1cf709f5c2))

🔧 chore release/cache, codeql-action: Update

([45f5805a1529835](https://github.com/Lob2018/SudokuFX/commit/45f5805a15298358c1852f94894fd4c31695ba7d))

🐛 fix: JVM warnings during test execution with Mockito and Class Data Sharing
☑ Root cause:
- Mockito self-attachment warnings due to future JDK restrictions on dynamic agent loading
- Class Data Sharing warnings caused by bootstrap classpath modifications from test agents (JaCoCo, Mockito)
☑ Fix:
- Add -XX:+EnableDynamicAgentLoading to maven-surefire-plugin argLine to allow Mockito dynamic agent attachment
- Add -Xshare:off to disable Class Data Sharing and prevent bootstrap classpath conflict warnings
☑ Update or add tests:
- No test changes required - configuration change only affects test execution environment

([2b4ac4497721cdc](https://github.com/Lob2018/SudokuFX/commit/2b4ac4497721cdcda2121fd69eea292c82635737))

📚 docs .gitmessage: Update

([c22b2ae65c3b2ad](https://github.com/Lob2018/SudokuFX/commit/c22b2ae65c3b2ad2b9efd1f22489beec497b76d6))

🔧 chore flyway-database-hsqldb, spotless, download-artifact, codeql-action, spring-boot: Update

([4bcb0a9ce5e71cf](https://github.com/Lob2018/SudokuFX/commit/4bcb0a9ce5e71cf191cccbb36568d9c809ba5d79))

🐛 fix .properties: deprecated javax.persistence validation mode warning
- Root cause: Using deprecated javax.persistence namespace instead of jakarta.persistence
- Fix: Replace javax.persistence.validation.mode with jakarta.persistence.validation.mode
- Verify application starts without deprecation warnings

([32fdbc3369b906d](https://github.com/Lob2018/SudokuFX/commit/32fdbc3369b906d6273fd7b4400a2e1f73de3f47))

♻️ refactor GridViewModel: grid verification condition logic
- Combine if-else structure into single condition with logical OR
- Eliminate code duplication in grid verification calls
- Verify tests pass

([3b7c86f03c54871](https://github.com/Lob2018/SudokuFX/commit/3b7c86f03c5487121243b9e566612a4272073b65))

✨ feat GridViewModel: Add conditional grid verification
- Implement ActiveMenuOrSubmenuViewModel dependency and conditional grid verification
- Add ActiveMenuOrSubmenuViewModel to constructor
- Implement isCompletelyCompleted() validation method
- Update init() listener to verify grid only in SOLVE mode or when complete
- Add comprehensive documentation for new methods
- Update tests

([8681c933e5e6c7b](https://github.com/Lob2018/SudokuFX/commit/8681c933e5e6c7bc890d113779967a695fe2dea8))

🐛 fix GridViewModel: Field &#x27;iGridMaster&#x27; is &#x27;final&#x27;

([0c3333e13b3f774](https://github.com/Lob2018/SudokuFX/commit/0c3333e13b3f77404af53c0bd1008919e0ed9f6c))

📚 docs .gitmessage: enhance template with PR guidance

([ca5a8651e88a78a](https://github.com/Lob2018/SudokuFX/commit/ca5a8651e88a78adbb5a1275b36768ba5da71105))

🔧 chore MenuNewViewModelUTest: TODO comment to highlight need for code review on potential side effects

([8087e9e4897b511](https://github.com/Lob2018/SudokuFX/commit/8087e9e4897b511ec33f9206ae3c2dba4aa7cbd4))

♻️ refactor Game, GameDto, Background, BackgroundDto: Model entities and DTOs for null-safety, validation, and consistency

([e639a6eda417bc2](https://github.com/Lob2018/SudokuFX/commit/e639a6eda417bc272904e05f102c3551a22e6aed))

♻️ refactor MenuNewViewModelUTest: Inline mocked Task in setUp() to remove temp variable and simplify code

([e4c52ce36feb630](https://github.com/Lob2018/SudokuFX/commit/e4c52ce36feb630c994a411e46b971a2e740d076))

🔧 chore ApplicationKeystore: Switch ObjectUtils.isEmpty from Spring to Apache Commons Lang3 for char[] checks

([ae46200d6347903](https://github.com/Lob2018/SudokuFX/commit/ae46200d6347903af06a36711b1b46979e8fc202))

🔧 chore CI/CD codeql-action: Update

([2ede6e87e8d6205](https://github.com/Lob2018/SudokuFX/commit/2ede6e87e8d62051136dd95bc57f45524df5e03e))

🐛 fix StringUtils.isBlank: replace Micrometer&#x27;s StringUtils with Apache Commons&#x27; null-safe StringUtils.isBlank

([5382cb598430e33](https://github.com/Lob2018/SudokuFX/commit/5382cb598430e338cf89be1e572a156b5417cd82))

✨ feat pom.xml, README.md: add Apache Commons Lang3 dependency
- Added org.apache.commons:commons-lang3 to pom.xml for utility methods (e.g. StringUtils.isBlank)
- Updated README to include commons-lang3 under development dependencies

([5b22d6eee6ad637](https://github.com/Lob2018/SudokuFX/commit/5b22d6eee6ad63750d3b78613a34027ddb344d60))

✅ Add unit tests for GrilleResolue and GrillesCrees validation constraints
  (including null grilleResolue and grilleAResoudre tests for GrillesCreesUTest)
-Cover array length validation (exactly 81 elements required)
-Verify @NotNull constraints on grilleResolue and grilleAResoudre fields
-Test boundary and out-of-range values for pourcentageDesPossibilites (@Min and @Max)
-Add equals, hashCode, and toString tests for consistency and proper representation
-Fix expected error messages in validation assertions

([a7a107678960ab5](https://github.com/Lob2018/SudokuFX/commit/a7a107678960ab584beffa348f41d7a0b7756d25))

📚 docs: DOXYGEN

([407ac1df17a0a68](https://github.com/Lob2018/SudokuFX/commit/407ac1df17a0a680c96ecd9a6684c7bc0237a1af))

♻️ refactor GridMaster, GridViewModel, GridViewModelUTest: add GridViewModel unit tests with package-private GridMaster constructor
-Change constructor visibility to enable test injection in GridMaster
-Add comprehensive tests with stub validator in GridViewModelUTest
-Change to constructor injection for IGridMaster in GridViewModel

([125b5a9ac4a8a09](https://github.com/Lob2018/SudokuFX/commit/125b5a9ac4a8a096bb861b30ecc9777e1be9ecb3))

🐛 fix GridCellViewModel: remove duplicate CSS property -fx-border-color generation in getBorderStyle

([084a60ff80c87b2](https://github.com/Lob2018/SudokuFX/commit/084a60ff80c87b28e9308b1031b4ce170b516087))

✨ feat GridViewModel, GridMaster, GrilleResolue, IGridMaster, GrillesCrees: add record, listener, and update GridMaster for Sudoku handling (✅ tests need to be updated AND checkstyle too)
- Added GrilleResolue record to represent the result of solving a grid
- Added @Nonnull for toString() method in GrilleResolue and GrillesCrees
- Updated IGridMaster with method signatures for creation and solving
- Updated GridMaster class with new features and method signatures
- Added listener to validate user input in the grid

([c7a6f45490fc836](https://github.com/Lob2018/SudokuFX/commit/c7a6f45490fc8362a718c648d0c34765aeac6891))

♻️ refactor style.css:  Formatting for better readability

([86b2b75a3677e64](https://github.com/Lob2018/SudokuFX/commit/86b2b75a3677e6499b3a06c0ab958c9d6b489623))

🐛 fix default-view.fxml: Unused import

([9fff72e1d90d7af](https://github.com/Lob2018/SudokuFX/commit/9fff72e1d90d7af27c6d976e96650692fe872c66))

♻️ refactor pom.xml: Move from version 2.0.0 to 1.0.0

([f6da7982b64ca49](https://github.com/Lob2018/SudokuFX/commit/f6da7982b64ca49bcace046cd41d80b777f6815a))

🐛 fix GridCellViewModel: Extract label font size and border update logic to reduce cyclomatic complexity

([7ba7905bcdb5c28](https://github.com/Lob2018/SudokuFX/commit/7ba7905bcdb5c2830b3314c9bf5283270b30c257))

🐛 fix GridCellViewModel: Remove unnecessary braces

([236e5a5ecf67152](https://github.com/Lob2018/SudokuFX/commit/236e5a5ecf6715235ec27c857f19e427c5c91c34))

✨ feat GridViewModel, GridCellViewModel, style.css: Set cell editability based on value

([d0f53bb0c3075a7](https://github.com/Lob2018/SudokuFX/commit/d0f53bb0c3075a7b1390f799d13122068e45b449))

🐛 fix GridCellViewModel: focused label style applied

([de2106d2652e9c9](https://github.com/Lob2018/SudokuFX/commit/de2106d2652e9c93cd0c2b538ed46f5f6acee45f))

🐛 fix PossibilityStarsHBox: Remove useless curly braces around statement

([ec7c7b524b17fc7](https://github.com/Lob2018/SudokuFX/commit/ec7c7b524b17fc7b625a64713cb2c915530b4c20))

🐛 fix DefaultView: Private field &#x27;GRID_SIZE&#x27; is never used

([f18fdbfa03f8c1a](https://github.com/Lob2018/SudokuFX/commit/f18fdbfa03f8c1a594c594b2c2a9340be952fe55))

🐛 fix GridCellViewModel: Define a constant instead of duplicating this literal

([7c12577eef7086b](https://github.com/Lob2018/SudokuFX/commit/7c12577eef7086b69f80348552cc2b7374ff7cf8))

🐛 fix GridCellViewModel: Avoid using boxed &quot;Boolean&quot; types directly in boolean expressions

([bab7713a49b0297](https://github.com/Lob2018/SudokuFX/commit/bab7713a49b029782eb3dc6780bdd53603b1dbe3))

🐛 fix Game, Player: Boolean literals is not redundant

([d629396fe694ca5](https://github.com/Lob2018/SudokuFX/commit/d629396fe694ca51c2cbf13a224a01dfe3799ebc))

🐛 fix Background: Boolean literals is not redundant

([d4fc7d16d97b3cc](https://github.com/Lob2018/SudokuFX/commit/d4fc7d16d97b3cc76d8348f6948f258bae9c99ab))

📚 docs: DOXYGEN

([a17dabd030deaa6](https://github.com/Lob2018/SudokuFX/commit/a17dabd030deaa62606b1b436659c620ea478cd0))

🔧 chore pom.xml spotless-maven-plugin: Update

([468beda801bb8fd](https://github.com/Lob2018/SudokuFX/commit/468beda801bb8fdd643d8289549fcce358e088b4))

👷 ci pre_commit_autoupdate.yml, .pre-commit-config.yaml: Rename pre-commit workflow to &#x27;Pre-commit autoupdate check&#x27;
- Rename CI job for clarity
- Update all pre-commit hooks to latest versions
- Ensure CI fails if updates are needed

([4ad2ab6ff5733e1](https://github.com/Lob2018/SudokuFX/commit/4ad2ab6ff5733e1a876f34bdc9fc2c9a0bfb81fd))

👷 ci pre_commit_autoupdate.yml: Fail if pre-commit autoupdate needs changes

([ccccffd2661cd4b](https://github.com/Lob2018/SudokuFX/commit/ccccffd2661cd4b07b90f1cee5c319273a4bee87))

👷 ci TESTING .pre-commit-config.yaml: gitleaks from v8.28.0 to v8.27.0

([2011f2396d38811](https://github.com/Lob2018/SudokuFX/commit/2011f2396d388114da14b6dd04799c749b09fdd3))

🐛 fix ci: Add requirements.txt with pre-commit to support Python dependency caching in CI

([9fbca699ba2cf2a](https://github.com/Lob2018/SudokuFX/commit/9fbca699ba2cf2a436405394e527d1c71f5c1064))

👷 ci pre_commit_autoupdate.yml: add GitHub Action for pre-commit autoupdate check

([a80d32f4599454c](https://github.com/Lob2018/SudokuFX/commit/a80d32f4599454c2a28c455abfea5d0e97115deb))

🔧 chore pom.xml checkstyle: Update

([52c4fcd88422131](https://github.com/Lob2018/SudokuFX/commit/52c4fcd884221319921c1a1498be8d5edb9d3c89))

🔧 chore pom.xml maven-enforcer-plugin: Update

([20f3974a3d80367](https://github.com/Lob2018/SudokuFX/commit/20f3974a3d803676d142351c00ce6e00e3b06838))

🔧 chore pom.xml flywaydb: Update

([7f1e5c374ead971](https://github.com/Lob2018/SudokuFX/commit/7f1e5c374ead971765dd79ba677c7aef55c3c300))

🔧 chore codeql-action: Update

([9a7709111303954](https://github.com/Lob2018/SudokuFX/commit/9a7709111303954174164b1cf72749a1fda7ac12))

✨ feat checkstyle-suppressions.xml: Checkstyle suppressions configuration and integrate it with Spotless Maven
- Added &#x60;checkstyle-suppressions.xml&#x60; to define suppressions for specific rules and lines.
- Configured Spotless Maven plugin to optionally use the suppressions file via &#x60;&lt;suppressionsLocation&gt;checkstyle-suppressions.xml&lt;/suppressionsLocation&gt;&#x60;.
- Enables selective disabling of Checkstyle checks without affecting global rules.

📚 docs: DOXYGEN

([665aaa3726bdbc8](https://github.com/Lob2018/SudokuFX/commit/665aaa3726bdbc8e6602bf535848226cf8b4ca3e))

♻️ refactor mainpage.dox: Remove mvn clean as it&#x27;s already in the [Jmh init.] configuration

([6cc2b8515c2e1df](https://github.com/Lob2018/SudokuFX/commit/6cc2b8515c2e1df66a4294277ac3fc3d4712bb9b))

♻️ refactor .properties: Improve wording in database authentication error

([f37a9939919f765](https://github.com/Lob2018/SudokuFX/commit/f37a9939919f76532487310e3d06e5e3829f4a51))

🐛 fix SplashScreenView: SVG errors :
-Soft64 text
-sudo.svg

([ed5f9b6e73d4b9f](https://github.com/Lob2018/SudokuFX/commit/ed5f9b6e73d4b9f4919c9db0abfc189d69bb7bb1))

📚 docs checkstyle.xml: Add TODO : Replace from value&#x3D;&quot;warning&quot; to value&#x3D;&quot;error&quot;

([bfb78a966cb865e](https://github.com/Lob2018/SudokuFX/commit/bfb78a966cb865ec4cae30348baa59e14810f082))

📚 docs CONTRIBUTING.md: Add Git Hooks and Code Quality with pre-commit chapter

([68b4c7c353d5efe](https://github.com/Lob2018/SudokuFX/commit/68b4c7c353d5efea9f447a12cf7a7f8155746de7))

📚 docs: DOXYGEN

([3673b3175a491d2](https://github.com/Lob2018/SudokuFX/commit/3673b3175a491d2a677be91a365aa55e7bab517e))

🔧 chore .pre-commit-config.yaml: Update gitleaks

([ea8b8ed1db62fbe](https://github.com/Lob2018/SudokuFX/commit/ea8b8ed1db62fbe1f3afce3083dfb30feb6c74df))

📚 docs README: Add maven-checkstyle-plugin in build dependencies

([4506b426c8e88a5](https://github.com/Lob2018/SudokuFX/commit/4506b426c8e88a50fd5f8a74ecf91e31f183e973))

📚 docs checkstyle.xml: Add note to ignore IntelliJ &quot;URI is not registered&quot; error for Checkstyle DTD

([25fd89df9e8c5f1](https://github.com/Lob2018/SudokuFX/commit/25fd89df9e8c5f10dc01087cc1c9f98b9dd53bca))

♻️ refactor pom.xml, checkstyle.xml: Add maven-checkstyle-plugin and custom rules
- Configure violationSeverity&#x3D;error to ignore warnings
- Set failsOnError&#x3D;true to fail on Checkstyle errors
- Add checkstyle.xml with naming, complexity, javadoc, and format rules
- Fix Checkstyle errors in affected Java classes

([44f5b4b82c69642](https://github.com/Lob2018/SudokuFX/commit/44f5b4b82c696422f80713731e2880ed94ef734d))

🐛 fix BenchGridMaster: add missing Javadoc comment to BenchGridMaster to satisfy Checkstyle MissingJavadocType rule

([e387a9e9a3514cd](https://github.com/Lob2018/SudokuFX/commit/e387a9e9a3514cddd2d7e14bb1eff5ffdddcc18e))

🐛 fix BenchGridMaster: Use of import.* is prohibited - org.openjdk.jmh.annotations.*. [AvoidStarImport]

([2d51034e3211cc0](https://github.com/Lob2018/SudokuFX/commit/2d51034e3211cc0692a01bbc37f32830d47e7026))

♻️ refactor GridMaster: factor out Sudoku grid generation logic by difficulty level
- Replaces specific methods (easy, medium, hard) with calls to a generic &#x60;genererGrilleAvecCasesCachees&#x60; method
- Adjusts logic for selecting the number of cells to hide based on elapsed time
- Improves readability and maintainability
- Updates documentation to follow proper French typographic rules

([d1db297951f6d60](https://github.com/Lob2018/SudokuFX/commit/d1db297951f6d60c2a839b66343aa37eb81d234a))

📚 docs: Doxygen

([95da6998fdf2ff8](https://github.com/Lob2018/SudokuFX/commit/95da6998fdf2ff83c1e5026a5c71306dc996ba0b))

♻️ refactor PlayerServiceUTest: Moved to unit test package

([3721c8fe449ad9e](https://github.com/Lob2018/SudokuFX/commit/3721c8fe449ad9e61e57f16efa3aa7f014c0df73))

🔧 chore harden-runner: Update

([f821748eb24cd49](https://github.com/Lob2018/SudokuFX/commit/f821748eb24cd49086e13963a7bf8437170d376c))

🐛 fix MenuBackgroundViewModelUTest: Pass null directly instead of nullFile in handleFileImageChooser test

([03d17002454c402](https://github.com/Lob2018/SudokuFX/commit/03d17002454c40253ae3b6ef2afd3ac9bf236085))

🐛 fix MenuBackgroundViewModelUTest: Rename local variables to avoid shadowing fields

([e77da44c7e8a9a1](https://github.com/Lob2018/SudokuFX/commit/e77da44c7e8a9a1951ee729362144281e9b9cdcc))

🐛 fix InMemoryPlayerUTest: Suppress warning by adding explanatory comment on empty exitPlatform()

([068019c8f3ed955](https://github.com/Lob2018/SudokuFX/commit/068019c8f3ed955e33a59f038bcb494263310e22))

🐛 fix GrillesCreesUTest: Suppress DataFlowIssue warning on record instantiation with invalid test data

([e7772a31336e044](https://github.com/Lob2018/SudokuFX/commit/e7772a31336e044ede29f7890fa709b0affbe910))

🐛 fix OsNameUTest:
- Remove &#x27;public&#x27; modifier
- Lambda replaced with method reference

([3c9a2823a9fd2f3](https://github.com/Lob2018/SudokuFX/commit/3c9a2823a9fd2f32ce66944077f83532e9946dc4))

🐛 fix VersionServiceITest: suppress unchecked assignment warnings - specify generic type for HttpResponse.BodyHandler in Mockito any() calls

([51172c7057b7439](https://github.com/Lob2018/SudokuFX/commit/51172c7057b74394b2e5e10797d3fb21f8053c0b))

🐛 fix CoordinatorUTest:
-Close Mockito mocks properly to avoid resource leaks
-Store AutoCloseable from openMocks and close in @AfterEach
-Fix &#x27;AutoCloseable used without try-with-resources&#x27; warning

([d73c787094d0064](https://github.com/Lob2018/SudokuFX/commit/d73c787094d006410d81cd87609531476d05bc29))

🐛 fix AbstractDataSourceConfigUTest:  Add @SuppressWarnings(&quot;resource&quot;) to silence AutoCloseable resource warnings where safe

([e29cd7af38facec](https://github.com/Lob2018/SudokuFX/commit/e29cd7af38facec3749939fa9203deb00d377c8e))

🐛 fix FileSystemManagerUTest: Add @SuppressWarnings(&quot;resource&quot;) to silence AutoCloseable resource warnings where safe

([449bb25132839f0](https://github.com/Lob2018/SudokuFX/commit/449bb25132839f0f9a40c132dcfa9130e3dde281))

♻️ refactor .idea AutoCloseableResource inspection : replace custom METHOD_MATCHER_CONFIG with default settings
-Removed old custom method matcher config to simplify inspection
-Set inspection level to WARNING and enabled it by default
-Ensures better detection of unclosed AutoCloseable resources with standard IntelliJ rules

([3f4c6b0b0aadb1a](https://github.com/Lob2018/SudokuFX/commit/3f4c6b0b0aadb1a3207d1e704d41ebfd17150c1d))

✅ test GridMasterUTest: Remove useless tests

([cd35d3df5c9afe5](https://github.com/Lob2018/SudokuFX/commit/cd35d3df5c9afe55e24be503013363477b9ddb98))

✅ test GrillesCreesUTest:  validate constraints and equality logic

([dda2f1b90394d7c](https://github.com/Lob2018/SudokuFX/commit/dda2f1b90394d7cf7abf1efaef2ecd40774d9694))

♻️ refactor GrillesCrees: Validation and utility methods; update GridMaster and tests
- Add Jakarta Bean Validation annotations (@NotNull, @Size, @Min, @Max) to GrillesCrees fields
- Implement equals, hashCode, and toString in GrillesCrees
- Adjust GridMaster and IGridMaster to align with strengthened data contracts
- Use jakartaValidator.validateOrThrow(grillesCrees) in GridMaster to enforce validation
- Refine GridMasterUTest to remove redundant checks and improve Spring integration

Improves data integrity and code robustness while cleaning up tests.

([a522beeade28f20](https://github.com/Lob2018/SudokuFX/commit/a522beeade28f2036e25fd0e87200b3303405109))

♻️ refactor return type of GridMaster.creerLesGrilles: Use record for grid creation
- Introduce GrillesCrees record for structured grid creation result
- Update IGridMaster and GridMaster to return GrillesCrees
- Update GridViewModel accordingly
- Update GridMasterUTest accordingly

([bdf4eb6b727f429](https://github.com/Lob2018/SudokuFX/commit/bdf4eb6b727f4294a995b6a7a0ac01ebb78dbead))

♻️ refactor config OS (and update tests): Unify folder initialization and improve folder factory implementations
- Replace folder paths array with OsInitializedFolders record for better type safety and clarity in OsFolderInitializer
- Update OsFolderInitializer to return OsInitializedFolders instead of String[]
- Modify LinuxFolderFactory, WindowsFolderFactory, and MacOSFolderFactory constructors to use OsInitializedFolders
- Harmonize and clarify javadoc comments across folder factories and initializer for consistent documentation
- Maintain folder path validation and creation logic with improved error handling and logging

This refactoring improves code readability, reduces error-prone array usage, and ensures consistent folder management across supported OSes.

([58ff4bdc582b3bb](https://github.com/Lob2018/SudokuFX/commit/58ff4bdc582b3bb7a6efc4b64d5fe5755664d7f6))

📝 style: Run automatic code formatter

([40374d03d1ffb37](https://github.com/Lob2018/SudokuFX/commit/40374d03d1ffb37ad681e168c18b3922959d1d36))

♻️ refactor PlayerServiceUTest: Adapt PlayerServiceUTest to JakartaValidator integration

([98dbdcf851601ec](https://github.com/Lob2018/SudokuFX/commit/98dbdcf851601ec07abdd0bdf4a203b9198a5952))

✨ feat PlayerService: Integrate JakartaValidator for DTO validation
- Inject JakartaValidator to validate mapped PlayerDto objects
- Apply validation in getPlayer method
- Update class Javadoc to reflect Jakarta Bean Validation usage

([9f6cc006aeb7e2e](https://github.com/Lob2018/SudokuFX/commit/9f6cc006aeb7e2eace443eb3207e04bd351f8c9b))

♻️ refactor JakartaValidator: Rename ValidationUtils to JakartaValidator and update javadoc
- Avoid conflict with Spring ValidationUtils
- Clarify usage and Spring context requirement in docs

([2716b8182d40623](https://github.com/Lob2018/SudokuFX/commit/2716b8182d406232db10a5007e1590a64305ec48))

✨ feat ValidationUtils: Spring component for centralized Jakarta Bean Validation
-Introduce ValidationUtils as a Spring @Component to centralize validation logic
-Inject Validator automatically from Spring context for ease of use
-Validate any Java object (beans, records, etc.), throwing IllegalArgumentException if null and ConstraintViolationException on constraint violations, and log it
-Add Javadoc with usage example and reminder about Spring context initialization

([ac742a097b7397e](https://github.com/Lob2018/SudokuFX/commit/ac742a097b7397e145a1bc62342bb1bde9722075))

🔧 chore flyway-database-hsqldb, spotless-maven-plugin: Update

([7aa9790a1a85cef](https://github.com/Lob2018/SudokuFX/commit/7aa9790a1a85cef152ba88dded8aee7858a7e44a))

🐛 fix MyLogbackConfig: Add printLogEntryMessage() to the constructor

([6259af4d216c128](https://github.com/Lob2018/SudokuFX/commit/6259af4d216c128caa41abf72f07715b8b7f87f4))

♻️ refactor AbstractDataSourceConfig, DataSourceConfigCds, DataSourceConfigDefault, DataSourceConfigTest : Data source configuration and logging bean dependency
- Remove logbackInitialization bean from AbstractDataSourceConfig
- Update hikariDataSource bean to depend on myLogbackConfig bean directly
- Extract Logback configuration into separate MyLogbackConfig class
- Update all DataSourceConfig... to depend on myLogbackConfig instead of logbackInitialization
- Clean up Javadoc and ensure consistent null checks and exception messages
- Remove obsolete test related to logbackInitialization(null)

([284015163f4ad81](https://github.com/Lob2018/SudokuFX/commit/284015163f4ad812fe727144e7fe0413100d0df3))

🐛 fix pom.xml: remove macOS-x86_64 useless argument &lt;argument&gt;x86_64&lt;/argument&gt;

([0f130a330acaa5d](https://github.com/Lob2018/SudokuFX/commit/0f130a330acaa5dd4d02b20b0a63e4f824b5a4b3))

## v2.0.0 - 2025-07-08


📚 docs DOXYGEN

([df8615cd15f93cd](https://github.com/Lob2018/SudokuFX/commit/df8615cd15f93cd87c8125d102925ec37f1722fa))

📚 docs README: Add OpenSSF Best Practices badge

([3c6ad4a3cc1828b](https://github.com/Lob2018/SudokuFX/commit/3c6ad4a3cc1828b38a02aa6844ba7cea7c764fba))

📝 style:run automatic code formatter

([6cc8cab0e5db33e](https://github.com/Lob2018/SudokuFX/commit/6cc8cab0e5db33e3f75285f2ecd28a0b92a9af70))

♻️ refactor replace all Java assert in tests with corresponding JUnit assertions

([2d87e17359f978c](https://github.com/Lob2018/SudokuFX/commit/2d87e17359f978c72870f61ff625325014b5d940))

🐛 fix CONTRIBUTING.md: Table of content Test Policy for New Functionality link

([74c1ae89d524cc2](https://github.com/Lob2018/SudokuFX/commit/74c1ae89d524cc24de0a782fed99631b839e55d4))

📚 docs CONTRIBUTING.md: document test policy for new functionality

([1478cc091bc7de1](https://github.com/Lob2018/SudokuFX/commit/1478cc091bc7de1af3eb4d4f8813f5eb1e958ea1))

📚 docs README: Reference CONTRIBUTING.md and CODE_OF_CONDUCT.md

([9fa7f8b44b372ce](https://github.com/Lob2018/SudokuFX/commit/9fa7f8b44b372cea89319a6a95c1c7b3e05e5a0a))

📚 docs CONTRIBUTING.md

([37b79d4be7de8c1](https://github.com/Lob2018/SudokuFX/commit/37b79d4be7de8c10cc5adb26661c893d3a2bca1d))

📚 docs CODE_OF_CONDUCT.md

([926c66e02f4fb26](https://github.com/Lob2018/SudokuFX/commit/926c66e02f4fb2652d80ad9d456bc21b4c86ae09))

♻️ refactor DefaultView: create constant for auto-hide delay and manage hideMiniMenuTimeline lifecycle
- Introduced AUTO_HIDE_MINI_MENU_DELAY_MS constant for MINI menu auto-hide delay duration.
- Initialized hideMiniMenuTimeline in initialize() method using hideMiniMenuTimelineInitialization().
- Added usage of hideMiniMenuTimeline.play() and hideMiniMenuTimeline.stop() to control timeline start/stop when showing MINI and MAXI menus.

([a35bec8991438dd](https://github.com/Lob2018/SudokuFX/commit/a35bec8991438ddf60ec6799a291ed5cd459013b))

📝 style GridViewModel: run automatic code formatter

([d582d2453caf488](https://github.com/Lob2018/SudokuFX/commit/d582d2453caf488efe94dce4080e0845ed4cf06f))

♻️ refactor DifficultyLevel, GridViewModel: simplify grid generation using enum method, update Javadoc

([08c6ecd928c4031](https://github.com/Lob2018/SudokuFX/commit/08c6ecd928c4031d24207eff341ae9d2f621e2d2))

♻️ refactor GridCellViewModel:
- extract input formatting logic
- add null/empty checks to prevent NPE
- improve Javadoc clarity

([e93a76c831f6605](https://github.com/Lob2018/SudokuFX/commit/e93a76c831f6605795774834886513d23a31cae2))

♻️ refactor GridCellViewModel: extract EM format string literal into constant

([af3cc4a14e16b6a](https://github.com/Lob2018/SudokuFX/commit/af3cc4a14e16b6a9c6504972da03375560a6d322))

♻️ refactor GridCellViewModel: Complete switch on KeyCode with default branch to comply with SonarQube checks

([2b630d07f618e9e](https://github.com/Lob2018/SudokuFX/commit/2b630d07f618e9e6f26ae6883cf4561803fe5e86))

♻️ refactor GridViewModel: Use Stream.toList() for cleaner and more concise list collection in getAllValues()

([009d6681ab09dec](https://github.com/Lob2018/SudokuFX/commit/009d6681ab09dec043d3c20f202bba77c795c5f0))

🔧 chore flyway-database-hsqldb, maven-enforcer-plugin: Update

([1ac7c7a6d8a6b79](https://github.com/Lob2018/SudokuFX/commit/1ac7c7a6d8a6b794d87e955e646ff889281271d8))

✅ test GridViewModelUTest

([e74916d8f8bb54e](https://github.com/Lob2018/SudokuFX/commit/e74916d8f8bb54eb23402261da42e2a917f65e66))

♻️ refactor MenuBackgroundViewModel, MenuSolveViewModel: Rename binding methods for clarity in shared view context

([6091015565c1c7d](https://github.com/Lob2018/SudokuFX/commit/6091015565c1c7dbfe121c7ee9a2cb6bb128d7d2))

🔧 chore SudokuFX_in_action.jpg updated

([320fefd394619ec](https://github.com/Lob2018/SudokuFX/commit/320fefd394619ec9a72e8b96ac2ea15f3ca4bf66))

📝 style DefaultView, GridViewModel: run automatic code formatter

([3588746f0c0e418](https://github.com/Lob2018/SudokuFX/commit/3588746f0c0e418e4ab3bc56eab3d97be455a2d2))

📝 style MenuLevelViewModelUTest: run automatic code formatter

([d414f69cd8b0af6](https://github.com/Lob2018/SudokuFX/commit/d414f69cd8b0af6f1eb1f0584110a9a929938f69))

🐛 fix: GridViewModel initialization to avoid JavaFX toolkit errors in tests
- Removed @PostConstruct init() to prevent JavaFX toolkit not initialized exceptions
- Moved grid initialization out of Spring bean lifecycle to be called explicitly after JavaFX startup
- Ensured tests pass without JavaFX context issues by deferring UI-related initialization

([961ff7b73b5aca6](https://github.com/Lob2018/SudokuFX/commit/961ff7b73b5aca63a15bb82d0e48f325b26ef24d))

✅ test MenuLevelViewModelUTest modified

([bb5c1b72dba4de4](https://github.com/Lob2018/SudokuFX/commit/bb5c1b72dba4de4bfa6c922a6eaf5545304b1cf4))

📝 style GridCellViewModel: run automatic code formatter

([e3988cf7e478e54](https://github.com/Lob2018/SudokuFX/commit/e3988cf7e478e546c1c0bee40f09d107e8189b4e))

✨ feat img, GridCellViewModel, style.css: Grid rendering
- Basic grid rendering implemented
- Still missing support for non-editable (preset) values
- MenuLevelViewModelUTest needs to be adapted
- README Sudoku grid image to update

([49ba0859e36976a](https://github.com/Lob2018/SudokuFX/commit/49ba0859e36976a35e97c540265bc46a5eb1dc11))

♻️ refactor style.css: Remove useless !important

([912c0e924f47ad1](https://github.com/Lob2018/SudokuFX/commit/912c0e924f47ad1ecb743d8148d613b23bb8f1ec))

✨ feat Grid linked to Levels -&gt; but MenuLevelViewModelUTest needs to be updated

([dbbff984bf416ee](https://github.com/Lob2018/SudokuFX/commit/dbbff984bf416ee4957d29ba2c81baeed6cb6bcd))

🐛 fix DefaultView: comment all the listener for TODO change the size only for one number

([248bd0bdd6abbb6](https://github.com/Lob2018/SudokuFX/commit/248bd0bdd6abbb67b57fc94c05864a53a238fe9c))

✨ feat DefaultView: implement GridPane grid with Label cells and editable TextArea (WIP)
- Set up grid layout using GridPane
- Use Label for displaying cells
- Add TextArea for editing cell content
- Still need to adjust size specifically for single-digit input

([90adadeb954206e](https://github.com/Lob2018/SudokuFX/commit/90adadeb954206e59bd253288c21a2d049e3c319))

🐛 fix style.css: Adjust sudokuFXGridCell width and height to match with the GridPane size

([6a08a0a3f847e9a](https://github.com/Lob2018/SudokuFX/commit/6a08a0a3f847e9a0952307eebcbae25a686575d9))

✨ feat: continue implementing the GridPane for the grid, Label for cells, and TextField to edit them (WIP)

([4d364a0658c5d8c](https://github.com/Lob2018/SudokuFX/commit/4d364a0658c5d8cb0dcdee51a20b7615648f53da))

✨ feat: start implementing the GridPane for the grid and TextArea for cells (WIP)

([6f2080c0c4d5e21](https://github.com/Lob2018/SudokuFX/commit/6f2080c0c4d5e2123139f65f0ec52718d9f54a97))

♻️ refactor default-view.fxml, style:
-Rename from sudokuFXGridContainer to sudokuFXGridPane
-Change TilePane to GridPane

([1e1f0870af36699](https://github.com/Lob2018/SudokuFX/commit/1e1f0870af36699c4810c72aed0e1eb748d329bc))

♻️ refactor DefaultView: Set private visibility for the VBox menus

([539a5ddbc4efe3d](https://github.com/Lob2018/SudokuFX/commit/539a5ddbc4efe3d1c4bb6e74d1a78e7b7e3ebbd9))

♻️ refactor ToastLevels : change CSS class names to camel case

([b1e456d58a40eab](https://github.com/Lob2018/SudokuFX/commit/b1e456d58a40eabc4cb7e017cc2ddb5a027b639f))

📚 docs:
-DOXYGEN
-The names of the properties of the application to run are in bold

([f2fa414a5007086](https://github.com/Lob2018/SudokuFX/commit/f2fa414a500708662053abb42a385750ef933d0b))

📚 docs README: specifies app.version

([e62059aac4b7bad](https://github.com/Lob2018/SudokuFX/commit/e62059aac4b7bad7ae00cd85db2eba30273816c2))

🐛 fix GenericDtoListCell : replace GameDtoListCell and PlayerDtoListCell to eliminate duplicated code

([7ea02020194828c](https://github.com/Lob2018/SudokuFX/commit/7ea02020194828c151122f175db482dbc395d732))

✅ test MenuNewViewModelUTest

([a2d5a8a1dfdb6e9](https://github.com/Lob2018/SudokuFX/commit/a2d5a8a1dfdb6e9b0d1d296149f7cb48c6792365))

✅ test MenuSolveViewModelUTest

([ae1fb430cc7aa34](https://github.com/Lob2018/SudokuFX/commit/ae1fb430cc7aa34374a0b92d0f5ac0b26920e4cb))

✅ test MenuMiniViewModelUTest

([8379f687441cb7d](https://github.com/Lob2018/SudokuFX/commit/8379f687441cb7ddf33f63d64c2d2e906bb71813))

🐛 fix MenuSaveViewModelUTest: remove useless @Start

([4a8ad1978487d2c](https://github.com/Lob2018/SudokuFX/commit/4a8ad1978487d2cceeb7b2bace36c09fa82926e4))

✅ test MenuSaveViewModelUTest

([f947087e14be097](https://github.com/Lob2018/SudokuFX/commit/f947087e14be097634bd4cd7f733c9a8fb320e05))

🐛 fix MenuBackgroundViewModelUTest: await spinner call with latch to ensure verification

([665af0961a3f0a1](https://github.com/Lob2018/SudokuFX/commit/665af0961a3f0a16ecc0187d6153427633475035))

🐛 fix MenuBackgroundViewModelUTest: replacing Thread.sleep with CountDownLatch to synchronize async Task failure and JavaFX addToast call

([87607e44e3ea19e](https://github.com/Lob2018/SudokuFX/commit/87607e44e3ea19e4a6d8335c968673bfe057eb62))

🐛 fix MenuBackgroundViewModelUTest: Replace Thread.sleep with CountDownLatch to reliably await GridPane background update in JavaFX thread, improving test stability.

([043b4e14b6f0253](https://github.com/Lob2018/SudokuFX/commit/043b4e14b6f025382130656b18bc613026429a0a))

📚 docs: DOXYGEN

([9336759f8b45dc3](https://github.com/Lob2018/SudokuFX/commit/9336759f8b45dc3cb8c4fc3191673accc7c2594e))

✅ test MenuPlayerViewModel

([7e77b3eed801d28](https://github.com/Lob2018/SudokuFX/commit/7e77b3eed801d28fd26283b967ec590e212f4b9d))

✅ test MenuBackgroundViewModel

([19f0f4ed4530db8](https://github.com/Lob2018/SudokuFX/commit/19f0f4ed4530db85a69dcb616dc97cfc9c41eb20))

✅ test InMemoryPlayerUTest: Simplified

([f136f5a89e02f68](https://github.com/Lob2018/SudokuFX/commit/f136f5a89e02f6875ec357dd1358e073efb099c5))

📚 docs: DOXYGEN

([00d3d8e77a88615](https://github.com/Lob2018/SudokuFX/commit/00d3d8e77a88615092c23b284ec51efb9a43b16b))

♻️ refactor License script: rename LICENSE_TO_EDIT.txt to MY_LICENSE_TO_EDIT.txt

([5db9092a815f34f](https://github.com/Lob2018/SudokuFX/commit/5db9092a815f34f6f43878066443780857a87d82))

✅ tests: Add @ExtendWith(ApplicationExtension.class) to enable JavaFX thread in tests for stable UI property handling

([b1d3d34eb0b20b9](https://github.com/Lob2018/SudokuFX/commit/b1d3d34eb0b20b9c2fffd62ea5e550670f289a17))

✅ test MenuLevelViewModelUTest

([718e359972f3349](https://github.com/Lob2018/SudokuFX/commit/718e359972f3349a0fdcb9c056a63b7c73bdb325))

📝 style MenuMaxiViewModelUTest: run automatic code formatter

([71906dbc6123f04](https://github.com/Lob2018/SudokuFX/commit/71906dbc6123f04fedc99b12025c808f82434a1b))

✅ test MenuMaxiViewModel

([581d0d1a2589d11](https://github.com/Lob2018/SudokuFX/commit/581d0d1a2589d1131c4e03c9abb9a2c0ad953ce3))

♻️ refactor pom.xml : Excluded MenuBackgroundViewModel$ (task) from JaCoCo coverage

([b4801c984257b6d](https://github.com/Lob2018/SudokuFX/commit/b4801c984257b6d0fd8b997bf8f05a3a26b64a12))

✅ test MenuHiddenViewModelUTest

([479b15d23650809](https://github.com/Lob2018/SudokuFX/commit/479b15d236508098b4ac9ce522f0b8343c0bba08))

✅ test InMemoryPlayerUTest: remove duplicated test givenException_whenInitializingPlayer_thenLogErrorIsProduced

([99569d8d5d10019](https://github.com/Lob2018/SudokuFX/commit/99569d8d5d1001916a15662e9141622cdc32f7b1))

♻️ refactor CI/CD harden-runner: update

([caf82ea854b2862](https://github.com/Lob2018/SudokuFX/commit/caf82ea854b2862c7292b19267f662d7a6b98073))

♻️ refactor CI/CD codeql-action: update

([ea3d4c8889fbe76](https://github.com/Lob2018/SudokuFX/commit/ea3d4c8889fbe7604bf1132a13c6ed6826815d1d))

✅ test InMemoryPlayer: unit and integration test

([7f4d40bf1c00d6e](https://github.com/Lob2018/SudokuFX/commit/7f4d40bf1c00d6ed4222cfe057e5810b6986facf))

🐛 fix completely remove Software related classes

([af6508551f3640d](https://github.com/Lob2018/SudokuFX/commit/af6508551f3640d3e65aa0003f43376419e04f07))

📝 style SudoMain: run automatic code formatter

([3d4d393f478f852](https://github.com/Lob2018/SudokuFX/commit/3d4d393f478f852329e9e1d480ba93d45228120e))

♻️ refactor ExceptionTools: Rename
- createAndLogIllegalArgument to logAndInstantiateIllegalArgument
- getSQLInvalidAuthorizationSpecException to findSQLInvalidAuthException

([d10fe6247f6d832](https://github.com/Lob2018/SudokuFX/commit/d10fe6247f6d8322f643b309f36ae00d2c719fe9))

♻️ refactor SudoMain: Refactor SudoMain to improve exception handling and code clarity
- Rename and enhance SQL authorization error logging with null checks and explicit IllegalArgumentException on null inputs
- Handle NullPointerException alongside other exceptions in Spring context initialization failure
- Improve Javadoc for main methods and exception handling
- Maintain splash screen, async Spring context init, crash screen display, and coordinated view transitions
- Use ExceptionTools for centralized exception management

([848d92906ae0ee7](https://github.com/Lob2018/SudokuFX/commit/848d92906ae0ee776d65f8b436c0b3218f1b5c8d))

♻️ refactor SudoMain: Replace null check with Objects.isNull for sqlInvalidAuthorizationSpecException

([8475731294746d7](https://github.com/Lob2018/SudokuFX/commit/8475731294746d7563165680ea2db0a4a689dfcf))

♻️ refactor SudoMain: Add try-catch to handleSpringContextTaskFailed to catch unexpected exceptions
- Wrap handleSpringContextTaskFailed method body in try-catch to prevent uncaught exceptions during failure handling from crashing the app abruptly
- Log any exception caught and trigger Platform.exit() to ensure graceful shutdown
- Aligns error handling robustness with handleSpringContextTaskSuccess method

([894ec47d1a382f4](https://github.com/Lob2018/SudokuFX/commit/894ec47d1a382f478360263cb4b397c620e91bd5))

✅ test viewmodel:restore viewmodel for cross-platform tests (see 5f1392b)

([e92c9f6c003f7e2](https://github.com/Lob2018/SudokuFX/commit/e92c9f6c003f7e23ce3511c7d199bb09e83d1eb6))

🔧 chore CI/CD release-action: Update

([a14c3be32d65dc1](https://github.com/Lob2018/SudokuFX/commit/a14c3be32d65dc12f00cc88d7012467a524135cd))

🔧 chore CI/CD codeql-action:Update

([c0a5a4e68c5d914](https://github.com/Lob2018/SudokuFX/commit/c0a5a4e68c5d914e4ca0115f1e81f94003a9a2b9))

🔧 chore flyway-database-hsqldb: Update

([aa597c14872abae](https://github.com/Lob2018/SudokuFX/commit/aa597c14872abae6cf851a21e9d7f6cde971a42c))

🐛 fix CI/CD release.yml: extract project version correctly, ignore parent

([f2f87ecd893dd20](https://github.com/Lob2018/SudokuFX/commit/f2f87ecd893dd20efaa35fd4fe9f7f55d5f3136c))

✅ test viewmodel: Temporary exclude viewmodel for cross-platforms tests and package

([5f1392b15ab9425](https://github.com/Lob2018/SudokuFX/commit/5f1392b15ab9425e614d77c53471a5370f3c4649))

🐛 fix MenuSaveViewModel : respects &quot;@NonNull&quot; for playerid in a GameDto

([406a11844800568](https://github.com/Lob2018/SudokuFX/commit/406a118448005684b8eaeed95d9ccfd3a4e3d361))

📚 docs:DOXYGEN

([620c396605b71fe](https://github.com/Lob2018/SudokuFX/commit/620c396605b71fe081acbb8f3d35711c45efb252))

✅ test PlayerServiceUTest: unit test

([ba887d9b5f70909](https://github.com/Lob2018/SudokuFX/commit/ba887d9b5f70909b316e3728eccd735bf654d4dc))

♻️ refactor PlayerService: Improve error message for invalid player name in PlayerService

([5d3549b1d5668d0](https://github.com/Lob2018/SudokuFX/commit/5d3549b1d5668d02bfbae64198103b71288d23c5))

📝 style: run automatic code formatter

([c809f4b1c535d98](https://github.com/Lob2018/SudokuFX/commit/c809f4b1c535d98cd9885a186c508558924ec43a))

♻️ refactor VersionService: Add null/blank input validation and detailed error handling to version check
- Validate JSON and version strings for blank/null inputs with clear exceptions
- Update parseResponse to handle invalid or short tags gracefully
- Ensure compareVersions throws on invalid inputs
- Enhance JavaDoc for all public and private methods
- Improve logging and error messages during GitHub API version check

([8e0bdf1f3991d95](https://github.com/Lob2018/SudokuFX/commit/8e0bdf1f3991d952db368984ff8bf05a8a505ddb))

📝 style:run automatic code formatter

([3a86548bdb313a8](https://github.com/Lob2018/SudokuFX/commit/3a86548bdb313a88436351178ff82c2659e88670))

♻️ refactor HelpViewModelUTest: test for conciseness and clarity
- Simplify argument retrieval in doAnswer lambda
- Remove unnecessary temporary variables
- Streamline assertion by accessing captor value directly

([689db378a82e6c9](https://github.com/Lob2018/SudokuFX/commit/689db378a82e6c9fdf7ea24cceb0f3a1a6adf8ab))

♻️ refactor HelpViewModelUTest: creating and spying HelpViewModel in a single statement

([7694738a6b9cb75](https://github.com/Lob2018/SudokuFX/commit/7694738a6b9cb75996fd6cef3a729d3bebc54328))

✅ test HelpViewModelUTest: Simplify instantiation and clarify spy/doAnswer to improve test readability

([0ccaa9403618238](https://github.com/Lob2018/SudokuFX/commit/0ccaa9403618238aeda3370fddd9925b310e6a8a))

✅ test HelpViewModelUTest: unit test for HelpViewModel.showHelp with alert spy
- Spy displayAlert() to avoid blocking showAndWait()
- Fix JavaFX thread issues with Platform.runLater()
- Verify alert title correctness

([3ba9800136bdf2b](https://github.com/Lob2018/SudokuFX/commit/3ba9800136bdf2b7be8351f111cdb60e8ee75b50))

♻️ refactor HelpViewModel: extract alert display into separate method
- Added displayAlert(Alert alert) to encapsulate alert showing logic
- Replaced direct showAndWait() call with displayAlert() for better testability and separation of concerns
- Ensures UI interactions remain on JavaFX thread and facilitate mocking/spying in tests

([2b680dbb0efe1a0](https://github.com/Lob2018/SudokuFX/commit/2b680dbb0efe1a02725ee59a50a3d92b08058642))

✅ test ActiveMenuOrSubmenuViewModelUTest

([8adfe8abdc550aa](https://github.com/Lob2018/SudokuFX/commit/8adfe8abdc550aa9a9b5d6f398dd13c0e2670ab2))

✅ test VersionServiceITest: add comprehensive integration test comparing current app version with nearby GitHub release tags
- Added a new @Test method &#x60;givenRealCurrentVersion_whenCheckLatestVersion_thenCompareWithNearbyVersions&#x60;  in &#x60;VersionServiceITest&#x60; to verify version checking logic against a range of versions around the current app version.
- This test fetches the current app version from JVMApplicationProperties and compares it to multiple versions (±1 major, minor, patch) to ensure correct latest version detection behavior.
- Kept existing parameterized tests covering empty response, non-200 HTTP status, and invalid version strings.
- Added helper method &#x60;compareVersions&#x60; for semantic version comparison inside the test class.

([e27599e344916cd](https://github.com/Lob2018/SudokuFX/commit/e27599e344916cd046fdd167f7b1a71bf732891f))

♻️ refactor MapperUtils: rename and document MapperUtils methods for clarity and cycle handling
- Renamed methods:
   - &#x60;mapPlayeridDtoToPlayer&#x60; → clearer naming aligned with DTO input
   - &#x60;mapSelectedGameToDto&#x60; → better reflects purpose (selecting first Game)
- Improved JavaDocs:
   - Explained usage context in MapStruct mappings
   - Clarified behavior, especially regarding null handling and cycle avoidance

([964b7b02f9e9519](https://github.com/Lob2018/SudokuFX/commit/964b7b02f9e95193915be8df9e46630879ad458a))

♻️ refactor PlayerDto: add @Nullable to selectedGame in PlayerDto

([fd0322b9a7e65d6](https://github.com/Lob2018/SudokuFX/commit/fd0322b9a7e65d6c939011922cf578b953808e43))

🐛 fix FileSystemManagerUTest: make deleteDataFolderRecursively test platform-independent
- use Paths.get() to build test path with valid segment count
- avoid IllegalArgumentException from invalid subpath indices
- ensure path doesn&#x27;t match DATA_FOLDER to trigger expected failure

([94b22d32dbbae1b](https://github.com/Lob2018/SudokuFX/commit/94b22d32dbbae1bb026ef94aa12be6241bb4461f))

🐛 fix pom.xml : update JaCoCo plugin excludes in pom.xml due to package restructuring

([323c40d30b731e0](https://github.com/Lob2018/SudokuFX/commit/323c40d30b731e04bb35062a27cc1a4d19889999))

✅ test CoordinatorUTest

([c50f8135ebae480](https://github.com/Lob2018/SudokuFX/commit/c50f8135ebae4802ab3fb61fc9f6115254d9ac3e))

♻️ refactor Coordinator: improve setRootByFXMLName error handling and add testable exit method
- Enhanced setRootByFXMLName with detailed Javadoc including error logging and dynamic font update.
- Replaced direct Platform.exit() call by package-private exitPlatform() method.
- exitPlatform() made overridable in tests to prevent actual platform exit and enable exception handling verification.
- Minor logging improvements and code cleanup for better maintainability.

([b9e62e523bbcc10](https://github.com/Lob2018/SudokuFX/commit/b9e62e523bbcc10606dbd458a75569b45dfbf1f2))

🐛 fix Coordinator: add null checks and improve error handling in Coordinator
- Validate constructor and setters for non-null arguments
- Log detailed errors and exit on FXML loading failure
- Warn if hostServices not set before use
- Update documentation for clarity

([bd4668fffbb0498](https://github.com/Lob2018/SudokuFX/commit/bd4668fffbb0498321e67e1554cdaf9bb05e138f))

✅ test MyLogbackConfigUTest: add coverage for missing config resource exception
- Add test to assert IllegalArgumentException thrown when Logback config resource is missing
- Ensure configureLogback() properly handles null InputStream scenario
- Improve test robustness and coverage of error handling

([b3347c450888e01](https://github.com/Lob2018/SudokuFX/commit/b3347c450888e0158e971b27f925eef20a162539))

🐛 fix MyLogbackConfig: add null check for InputStream in configureLogback()
- Throw IllegalArgumentException if config resource is missing
- Prevent NullPointerException during Logback setup
- Update JavaDoc to reflect new behavior

([f45da7205a20603](https://github.com/Lob2018/SudokuFX/commit/f45da7205a20603d3392b2452e47357fb4df566c))

✨ feat HttpClientConfig: switch HttpClient SSLContext from TLSv1.2 to TLSv1.3

([09406b4d4470e9f](https://github.com/Lob2018/SudokuFX/commit/09406b4d4470e9fd62cffd8b6b1574beb0f58d61))

📝 style:run automatic code formatter

([422ef5468ad566d](https://github.com/Lob2018/SudokuFX/commit/422ef5468ad566da50b82f4f78dc993306aaae47))

📚 docs LinuxFolderFactory, MacosFolderFactory, WindowsFolderFactory:clarify constructors
- specify folder path validation and creation are delegated to OsFolderInitializer
- note possible exceptions on invalid paths or folder creation failure

([304b531ef85597e](https://github.com/Lob2018/SudokuFX/commit/304b531ef85597e522e53d9b30f429d99481c60c))

✅ test OsFolderInitializerUTest: refactor it with improved coverage and TempDir
- Added @TempDir to avoid manual temp folder handling and File.delete()
- Added test for null folder argument in createFolder()
- Replaced hardcoded invalid path tests with parameterized test
- Ensured verification of mocked method calls in all tests
- Improved assertions and exception message checks for robustness

([d45be1de67c2874](https://github.com/Lob2018/SudokuFX/commit/d45be1de67c28746b2e951594cd00245fdd59ffc))

♻️ refactor OsFolderInitializer: improve OsFolderInitializer validation and robustness
- Enforced null/blank checks on folder paths via ExceptionTools
- Added explicit null check for folder parameter in createFolder()
- Updated Javadoc for clarity and precision
- Improved exception handling and messaging for better diagnostics

([14f4ef388dacd75](https://github.com/Lob2018/SudokuFX/commit/14f4ef388dacd75fc812f26dc9e9f58a77ba6d98))

📚 docs:DOXYGEN

([2af202179482f0b](https://github.com/Lob2018/SudokuFX/commit/2af202179482f0bd85aa170eceec7ee51e75669b))

✅ test OsInfoUTest: assert OS name contains detected OS instead of strict equality

([349547f6e7abed9](https://github.com/Lob2018/SudokuFX/commit/349547f6e7abed97c4b52e263de2584e84fac444))

✅ test OsNameUTest: verify OS detection and exception handling on invalid OS names

([bf304d0e199a73a](https://github.com/Lob2018/SudokuFX/commit/bf304d0e199a73ad592d67fcb776dd0420a5567c))

✅ test MyRegexUTest: rename MyRegexTest to MyRegexUTest without modifying test logic

([f940803d66436dd](https://github.com/Lob2018/SudokuFX/commit/f940803d66436dd1273e189849f035b2b6c42e20))

✅ test OsFolderFactoryManagerUTest: update OsFolderFactoryManagerUTest to use OsName enum and improve invalid OS tests

([45079ded42f84e0](https://github.com/Lob2018/SudokuFX/commit/45079ded42f84e07543dc6de9605d906fb562a09))

♻️ refactor OsFolderFactoryManager: use OsName enum and switch expression in OsFolderFactoryManager for cleaner OS handling

([c7c2e71ada9ddc0](https://github.com/Lob2018/SudokuFX/commit/c7c2e71ada9ddc00f3d99b7ce881e9feb1cfb7c9))

♻️ refactor OsName: enhance OsName enum with OS detection logic
- Replace single OS_NAME constant with explicit enum values WINDOWS, LINUX, MAC
- Add WRONG_OS_FOR_TESTS and EMPTY_OS_FOR_TESTS constants for test scenarios
- Implement static detect() method to identify current OS from system property
- Throw IllegalArgumentException for null, blank, or unsupported OS names

([ab0e9b5b0ef5653](https://github.com/Lob2018/SudokuFX/commit/ab0e9b5b0ef565315a5ff6ef63e4f4dda01a99ec))

📚 docs:DOXYGEN

([844680fcd8e58c8](https://github.com/Lob2018/SudokuFX/commit/844680fcd8e58c8cd8a49a8178a7f3b2fe8255fe))

♻️ refactor SecretKeyEncryptionServiceAESGCM, SecretKeyEncryptionServiceAESGCMUTest: add null checks, argument validation, and update docs and tests
- Added explicit null check in constructor for SecretKey with IllegalArgumentException.
- Added validation in encrypt() and decrypt() methods to reject null or blank inputs early.
- Updated Javadoc to clearly document constructor behavior and exceptions.
- Refactored unit tests to cover new validation rules, including null and blank inputs and constructor argument checks.
- Removed tests relying on null SecretKey instance, replaced by assertThrows checks.
- Added tests verifying proper error logging and empty string return on encryption/decryption failures.
- Improved overall code robustness, test coverage, and documentation clarity.

([747277214e580cb](https://github.com/Lob2018/SudokuFX/commit/747277214e580cbad6fa0611be46494d09d57661))

♻️ refactor ApplicationKeystore, ApplicationKeystoreITest: add NullPointerException checks and remove test ordering
- Added explicit null checks in ApplicationKeystore constructor and critical methods to prevent NPEs.
- In ApplicationKeystoreITest, introduced new test cases to verify behavior when username and password fields are null.
- Removed enforced test order in ApplicationKeystoreITest to allow independent execution.
- Enhanced existing tests to assert that null credentials do not cause unexpected exceptions.
- Improved overall test coverage and robustness of ApplicationKeystore initialization and credential retrieval.

([a2202aa6a00b739](https://github.com/Lob2018/SudokuFX/commit/a2202aa6a00b7397c34d9bf057db5c316d4714e9))

✅ test AbstractDataSourceConfigUTest

([9b2c4ec8a45428a](https://github.com/Lob2018/SudokuFX/commit/9b2c4ec8a45428a486ef5ad8ad6cee39154cf831))

♻️ refactor AbstractDataSourceConfig: Add null checks and input validations

([1cd4c43e8506b68](https://github.com/Lob2018/SudokuFX/commit/1cd4c43e8506b68de2125d4fcfc1362f31cafe09))

♻️ refactor SpringContextInitializer, SpringContextInitializerUTest:add null checks and unit tests
- add IllegalArgumentException if SpringContext is null in constructor
- add IllegalArgumentException if Task is - prevent NullPointerException by validating inputs early
- test async initialization task calls context.init() correctly
- use CountDownLatch to await task completion in tests

([982448f09c2317f](https://github.com/Lob2018/SudokuFX/commit/982448f09c2317fd1c81578cb598fb0fc33ca363))

♻️ refactor MyRegex:improve input validation and password checking with detailed error handling
- Replace Objects.requireNonNull with ExceptionTools to throw IllegalArgumentException instead of NullPointerException for blank or null inputs
- Add explicit checks rejecting blank text or password values, improving error messages with actual input values
- Update isValidatedByRegex to enforce stricter validation when matching SECRET_PATTERN by delegating to isValidPassword
- Refine isValidPassword to fail fast if password format does not match SECRET_PATTERN, then verify character counts for lowercase, uppercase, digits, and special chars
- Enhance Javadoc to clarify parameter constraints, return values, and exceptions thrown
- Update and optimize unit tests to cover new validation logic, including blank input checks and detailed password criteria verification
- Overall improve robustness and clarity of validation logic and error reporting

([0697b139d20f08f](https://github.com/Lob2018/SudokuFX/commit/0697b139d20f08f25bb7ca8d1aa9c71cfc9df68d))

♻️ refactor I18n: handle empty keys in getValue, improve log message and test it

([897aa6a23c82001](https://github.com/Lob2018/SudokuFX/commit/897aa6a23c82001b15ce456fb36347eda2025434))

✅ test : rename MyEnumsITest to ScreenSizeITest

([04e24880c2d726d](https://github.com/Lob2018/SudokuFX/commit/04e24880c2d726dd0b6341a54b0d37a55d6cdfd6))

📚 docs README: Renaming from Structure to Package Structure

([f62c988122a72ac](https://github.com/Lob2018/SudokuFX/commit/f62c988122a72ac78837a5ba23a174aa720fc52b))

♻️ refactor .gitmessage: correction of the docs comments

([fa3fcb610073bce](https://github.com/Lob2018/SudokuFX/commit/fa3fcb610073bcee8d9a45005dd5f709a97daa56))

📚 docs:DOXYGEN

([c57ecd0a2662cfe](https://github.com/Lob2018/SudokuFX/commit/c57ecd0a2662cfedc585ba5155550e959efa3699))

✅ test: reorganize test packages by test type (unit, integration, e2e)

([8622e16284aaf5d](https://github.com/Lob2018/SudokuFX/commit/8622e16284aaf5db848c96912e35141256017562))

♻️ refactor enums package:move enums package to common package

([d865194ae5109a2](https://github.com/Lob2018/SudokuFX/commit/d865194ae5109a2152f55b6a02bb53f39c0b98c0))

📚 docs README: add Structure section with updated project tree

([6c923283a47fcd1](https://github.com/Lob2018/SudokuFX/commit/6c923283a47fcd155e50ed5af85760e08b55cff3))

♻️ refactor MyDateTime, MyDateTimeUTest, I18nUTest: improve test isolation and reliability
- Added NPE check in MyDateTime#getFormatted
- Updated MyDateTimeUTest to reset I18n locale before and after each test to ensure test independence
- Refactored I18nUTest by removing @TestMethodOrder and @Order annotations; added @BeforeEach/@AfterEach to save and restore original locale/language
- Each I18n test now explicitly sets locale/language to prevent side effects and order dependencies
- Overall improvement in test robustness, reliability, and maintainability

([a43436526ba14b3](https://github.com/Lob2018/SudokuFX/commit/a43436526ba14b3564d23194f673fe24a09aea35))

✅ test ExceptionToolsUTest: add unit tests for ExceptionTools utility enum
- Covered createAndLogIllegalArgument() to ensure proper exception message and logging
- Tested logAndThrowIllegalArgumentIfBlank() with null, blank, and valid inputs
- Verified recursive lookup of SQLInvalidAuthorizationSpecException in exception chains

([a9e3ecec1b33a8f](https://github.com/Lob2018/SudokuFX/commit/a9e3ecec1b33a8f191c4aa2429895615a82a87c2))

✨ feat ImageUtils, ImageUtilsUTest :update ImageUtils documentation and add null checks
- Improved Javadoc for all methods in ImageUtils.
- Added explicit null argument validation with IllegalArgumentException.
- Created dedicated unit tests to verify NPE handling and expected behavior.

([7a19dbea6fff118](https://github.com/Lob2018/SudokuFX/commit/7a19dbea6fff1184b63eb62614b1f3caeff672bc))

✅ test FileSystemManagerUTest:add concise test for deleteFile failure case with IOException
-Mock Files.delete to throw IOException
-Verify deleteFile returns the exception
-Check error log contains expected failure message

([1026f1d68fdc8a2](https://github.com/Lob2018/SudokuFX/commit/1026f1d68fdc8a2bcb400aab1b642b9783b80233))

📝 style FileS
, IFileSystem:run automatic code formatter

([f04375358442d34](https://github.com/Lob2018/SudokuFX/commit/f04375358442d3406761b708e6de90e9a1e6288b))

♻️ refactor FileSystemManager, FileSystemManagerUTest, IFileSystem: add null checks and improve error handling in FileSystemManager and tests
- Validate null parameters with IllegalArgumentException in key methods
- Improve error logging consistency
- Extend tests to cover null argument cases and fix exception expectations

([a90f78d0eb35790](https://github.com/Lob2018/SudokuFX/commit/a90f78d0eb35790a7e6f40d6b1277b3898e3aaec))

♻️ refactor IFileSystem, FileSystemManager et FileSystemManagerUTest: clarify and restrict folder deletion to application data directory
- Renamed method to deleteDataFolderRecursively to reflect specific target (DATA_FOLDER)
- Updated IFileSystem Javadoc to explicitly describe deletion of the application&#x27;s data folder
- Improved logging and exception handling in FileSystemManager
- Adjusted FileSystemManagerUTest to align with renamed method and refined contract

([bd13a4ba0baf6ff](https://github.com/Lob2018/SudokuFX/commit/bd13a4ba0baf6ff062e1b521b16ff5481410eb0d))

📚 docs:DOXYGEN

([11c399e2e028c1e](https://github.com/Lob2018/SudokuFX/commit/11c399e2e028c1e82fcabe36800b6459e247c229))

✅ test DynamicFontSizeChangeE2ETest:add unit test for DynamicFontSize constructor null scene validation
- Verify IllegalArgumentException is thrown and logged when scene parameter is null
- Ensure constructor enforces non-null contract for scene parameter

([803e51cc1b117c9](https://github.com/Lob2018/SudokuFX/commit/803e51cc1b117c94dd062e6dd29d21c872680180))

♻️ refactor DynamicFontSize:add null check with logged exception in DynamicFontSize constructor
- Validate the scene parameter to prevent null usage
- Throw and log IllegalArgumentException via ExceptionTools if scene is null
- Improve robustness and error traceability for DynamicFontSize initialization

([4147463175574e3](https://github.com/Lob2018/SudokuFX/commit/4147463175574e37d5eccb5bc15c59988bab853e))

✅ test GridMasterUTest:add parameterized test for IllegalArgumentException on invalid grid sizes in resoudreLaGrille

([d2267ae33969b31](https://github.com/Lob2018/SudokuFX/commit/d2267ae33969b319c7203c04104a2f1676e8b6a1))

✅ test GridMasterUTest:assert IllegalArgumentException when level is out of [1, 3] in creerLesGrilles

([4ad1c7b7dc8ba09](https://github.com/Lob2018/SudokuFX/commit/4ad1c7b7dc8ba09396f22dc29849a05b656b1cc1))

✨ feat GridMaster, IGridMaster:add parameter validation and improve Javadoc
- Validate input in &#x60;creerLesGrilles(int)&#x60; (level must be 1–3).
- Validate &#x60;grille&#x60; is non-null and has 81 elements in &#x60;resoudreLaGrille(int[])&#x60;.
- Throw &#x60;IllegalArgumentException&#x60; via &#x60;ExceptionTools&#x60; with detailed messages.
- Update Javadoc to reflect validation and clarify behavior.

([e983c7bfa43de65](https://github.com/Lob2018/SudokuFX/commit/e983c7bfa43de65a5851ac8b0ce626ab6d6b1343))

📝 style:run automatic code formatter

([88ba7a12237b191](https://github.com/Lob2018/SudokuFX/commit/88ba7a12237b1914367cc58a9f315d58dee9ce85))

♻️ refactor MapperUtils:simplify and clarify mapping methods with concise null checks and logging
-Use Objects.isNull and CollectionUtils.isEmpty for null/empty checks
-Clarify JavaDoc to be concise and precise
-Maintain logging on null inputs for traceability
-Prevent instantiation with clear exception and log message

([bb744d730f9aca1](https://github.com/Lob2018/SudokuFX/commit/bb744d730f9aca1bac4ebf2f33aff650bed75006))

♻️ refactor PlayerService: getPlayer() split validation into separate filters for selected game and player name
-hasValidName renamed to validatePlayerNameOrThrow
-changed filter to map to explicitly validate player name with exception handling

([fc1983ae34ff02a](https://github.com/Lob2018/SudokuFX/commit/fc1983ae34ff02a2d2e10cd4f92fe3f9586271f2))

♻️ refactor PlayerService: use ExceptionTools for input validation and exception handling, simplify code, and update documentation

([f5e903b49c69072](https://github.com/Lob2018/SudokuFX/commit/f5e903b49c69072e94f97582c41ba234eae90504))

📝 style:run automatic code formatter

([40d24a53d22f412](https://github.com/Lob2018/SudokuFX/commit/40d24a53d22f412a1439be6cc3fd7283337fcc7a))

✅ test ExceptionToolsUTest: adapt tests to reflect createAndLogIllegalArgument behavior

([483d3428fb9a33a](https://github.com/Lob2018/SudokuFX/commit/483d3428fb9a33a8397734163e3a4490db79c59c))

♻️ refactor ExceptionTools: createAndLogIllegalArgument returns exception without throwing

([a491a812dc06990](https://github.com/Lob2018/SudokuFX/commit/a491a812dc0699090765b8fa13dbe3480c390b49))

♻️ refactor ActiveMenuOrSubmenuViewModel:setActiveMenu to use MAXI as default when null is passed

([e0caa2d7e0f6e90](https://github.com/Lob2018/SudokuFX/commit/e0caa2d7e0f6e9082a40282bb114174918994abf))

♻️ refactor package fr.softsf.sudokufx.service:add @NonNullApi to service package

([c7e399a93264fca](https://github.com/Lob2018/SudokuFX/commit/c7e399a93264fca56c7fbc0412f8f4520b95fb4e))

✨ feat PlayerService: validate player name when retrieving selected player
- Refactored getPlayer() to validate that the player&#x27;s name is not null, empty, or blank
- Introduced hasSelectedGameAndValidName(PlayerDto) for clearer validation logic
- Added concise Javadoc for both methods, describing validation and exception behavior
- Logs and throws PlayerNameInvalidException when name is invalid
- Ensures robustness and clear error reporting in player retrieval

([cf2968ba70d0b7b](https://github.com/Lob2018/SudokuFX/commit/cf2968ba70d0b7b023d5307001f8e213fe555752))

♻️ refactor MenuPlayerViewModel:remove selectedPlayer property and use inMemoryPlayer.currentPlayerProperty() directly
- Eliminated the selectedPlayer property from MenuPlayerViewModel.
- Updated references to use inMemoryPlayer.currentPlayerProperty() for better clarity and to avoid duplication.

([b609972757bf3f6](https://github.com/Lob2018/SudokuFX/commit/b609972757bf3f69e6624deedbc442d8c7181218))

🔧 chore MenuPlayerViewModel: remove unnecessary comments from the code

([65d4939b207d939](https://github.com/Lob2018/SudokuFX/commit/65d4939b207d939273a9f92c9b26008589576b43))

♻️ refactor InMemoryPlayer: use Objects.isNull() to check currentPlayer in constructor

([e39b6d96498a83f](https://github.com/Lob2018/SudokuFX/commit/e39b6d96498a83fd58a952e01b979b0e715982a0))

✨ feat PlayerService: add null check on selectedGame and simplify getPlayer JavaDoc

([0feb0ee7618e86a](https://github.com/Lob2018/SudokuFX/commit/0feb0ee7618e86a0e969a61cd67e5d91e9c581ff))

🔧 chore pom.xml flyway-database-hsqldb, spring-boot-starter-parent :Update

([d582d098285fb97](https://github.com/Lob2018/SudokuFX/commit/d582d098285fb97b8d0651459ff8f402b6ae78a2))

♻️ refactor: Rename CachedPlayer to InMemoryPlayer; rename cache package to state; update JavaDoc accordingly

([655f68c3ac66d41](https://github.com/Lob2018/SudokuFX/commit/655f68c3ac66d41d0137496c2acf0d8d4e3ce99c))

📝 style:run automatic code formatter

([c759a597cceffa5](https://github.com/Lob2018/SudokuFX/commit/c759a597cceffa5b3c0e12cb4c65b763de659372))

♻️ refactor:Add custom exception SelectedPlayerWithSelectedGameNotFoundException
-Define SelectedPlayerWithSelectedGameNotFoundException extending RuntimeException
-Use this exception in PlayerService#getPlayer() to signal no selected player with selected game found
-Improve error handling by replacing generic exceptions with a specific, descriptive exception class

([384ee9e8dc4dcde](https://github.com/Lob2018/SudokuFX/commit/384ee9e8dc4dcde4c1521fc019e6b6431ab16f95))

♻️ refactor: getPlayer to throw exception if no player found and update initializePlayer accordingly
- Change getPlayer to return PlayerDto directly and throw NoSuchElementException when no player is found
- Simplify initializePlayer by removing redundant orElseThrow call
- Add try-catch in initializePlayer to log error and exit application cleanly if player is missing
- Improve error handling and application shutdown flow during player initialization

([2526fcaec9b42fc](https://github.com/Lob2018/SudokuFX/commit/2526fcaec9b42fc5c9ce714ef4a236490614edee))

📚 docs:DOXYGEN

([579775edd19da37](https://github.com/Lob2018/SudokuFX/commit/579775edd19da372ae8a4c55090b8ace619121aa))

♻️ refactor(package-naming): use singular package names except for reserved terms

([54a66bba776f0de](https://github.com/Lob2018/SudokuFX/commit/54a66bba776f0de58129d7b9ee0604ae34b44c5e))

📚 docs:DOXYGEN

([9f17d0cf461ed43](https://github.com/Lob2018/SudokuFX/commit/9f17d0cf461ed43c8f83fc0009875a3c51c0c3d3))

♻️ refactor(tests): rename common test package to testing for clearer separation
- Renamed src/test/java/fr/softsf/sufokufx/common to testing
- Added subpackages: unit/, e2e/
- The old common package contained tests relying on package-private visibility
- The new testing package avoids package-private access to improve test isolation
- Prevents confusion with main/common package

([1902d71c4852c70](https://github.com/Lob2018/SudokuFX/commit/1902d71c4852c70fb9595e9214e527d2d065d9d1))

♻️ refactor project-structure: restructure packages to align with Spring conventions
- Moved &#x60;exceptions&#x60; to &#x60;common/exception&#x60;
- Moved &#x60;utils&#x60; to &#x60;common/util&#x60;
- Moved &#x60;annotations&#x60; to &#x60;common/annotations&#x60;
- Moved &#x60;interfaces&#x60; to &#x60;common/interfaces&#x60;
- Renamed &#x60;configuration&#x60; to &#x60;config&#x60;
- Preserved existing structure for feature packages: dto, model, repository, service, view, viewmodel, navigation

([7dcd86b8571c4c6](https://github.com/Lob2018/SudokuFX/commit/7dcd86b8571c4c64fdc05db872f03b7b0525bb7e))

🔧 chore CI/CD harden-runner:Update

([d39a47ac32c3678](https://github.com/Lob2018/SudokuFX/commit/d39a47ac32c36784286805305186c4da7de2d4cc))

🔧 chore CI/CD codeql-action:Update

([bfc66d21610add2](https://github.com/Lob2018/SudokuFX/commit/bfc66d21610add26fde8648e27a71f77d0f9ac9b))

🔧 chore pom.xml flyway-database-hsqldb:Update

([22ac988dcdc18ce](https://github.com/Lob2018/SudokuFX/commit/22ac988dcdc18cefd2dc629bca0cd7310df7bc86))

♻️ refactor pom.xml :maven-enforcer-plugin specifies an error message for requireMavenVersion

([707d2e5c513bacf](https://github.com/Lob2018/SudokuFX/commit/707d2e5c513bacfd49faea3448140d92a0f0d070))

📚 docs Doxygen, CachedPlayer

([c8ba5c53f1943f1](https://github.com/Lob2018/SudokuFX/commit/c8ba5c53f1943f116d6fe2561151bbff40a4c760))

♻️ refactor: Rename view-model component from CurrentPlayerState to CachedPlayer and rename package from shared to cache

([f78bafac6907ed1](https://github.com/Lob2018/SudokuFX/commit/f78bafac6907ed1332c2813c12dd604da8a437c6))

🔧 chore codeql-action:Update

([4d567507c8e9499](https://github.com/Lob2018/SudokuFX/commit/4d567507c8e9499e4f17c8e3e8d631b956620040))

📚 docs V1_0__create_base.sql:Translate from French to English

([94b24789f7ee6fa](https://github.com/Lob2018/SudokuFX/commit/94b24789f7ee6fa981d594311ddfce999e285bb3))

🔧 chore pom.xml,workflows:update
-pom.xml : exec-maven-plugin and spotless-maven-plugin
-scorecard-action

([2e9bad502789808](https://github.com/Lob2018/SudokuFX/commit/2e9bad50278980835b3de8efd029b8fa599f73d4))

♻️ Refactor Flyway initial values – schema cleanup and startup log improved
-Drop obsolete software table
-Add a selected anonymous player
-Add a selected game with empty easy grid linked to player
-Log application name and version at startup

([05b051bacc7adc4](https://github.com/Lob2018/SudokuFX/commit/05b051bacc7adc475184d6cf00a998b441a82f51))

♻️ refactor FullMenuViewModel, SoftwareService:remove useless classes (with VM test, Service test)

([e7ec28f1fd617dc](https://github.com/Lob2018/SudokuFX/commit/e7ec28f1fd617dc6b1a039bae9bc513c8ebd39a8))

♻️ refactor FullMenuView:Remove useless class

([4119393260d32b1](https://github.com/Lob2018/SudokuFX/commit/4119393260d32b11367dee7fbcc3f6386baf0b3c))

♻️ refactor application.properties, TODO:
-Format TODO messages
-Spring JPA show SQL

([509674293a8a0f0](https://github.com/Lob2018/SudokuFX/commit/509674293a8a0f045fa9bfd939318075b1923b8a))

♻️ refactor:Replace simple native query for first selected player with JPQL fetching players where both player and game are selected, eagerly loading related entities
 - Old query: native SQL returning single player with isselected &#x3D; true
- New query: JPQL fetching distinct players with isselected &#x3D; true and their games with isselected &#x3D; true
- Eagerly loads games, grid, level, background, menu, and player language to avoid lazy loading issues and ensure fully initialized data

([22d3b0c47f08058](https://github.com/Lob2018/SudokuFX/commit/22d3b0c47f0805837f637a06e4b5fbd21293f0c8))

✨ feat: return selected Game with PlayerDto and improve mapping
- Add @Transactional(readOnly &#x3D; true) annotation to PlayerService.getPlayer() with doc explaining lazy loading of games
- Add GameDto selectedGame field in PlayerDto
- Update IPlayerMapper to map selectedGame from Player.games using mapSelectedGame method; ignore games when mapping from DTO to entity
- Add MapperUtils.mapSelectedGame method to return first selected Game from Set&lt;Game&gt; with logging when none found or input is null
- Improve documentation for all added/modified methods and classes

([56af1e1d5b21403](https://github.com/Lob2018/SudokuFX/commit/56af1e1d5b21403e1902bbc6a8b8fa238e0262fe))

♻️ refactor MenuPlayerViewModel, unused imports: loadPlayers firstly load the selected player

([1610d7b73e173cd](https://github.com/Lob2018/SudokuFX/commit/1610d7b73e173cd386813098c8870dbc35e0b059))

♻️ refactor:enforce uppercase constant names

([dcf3d0f56f82bf7](https://github.com/Lob2018/SudokuFX/commit/dcf3d0f56f82bf7f86ecec170c2df68bf838215f))

♻️ refactor MapperUtils, log to LOG (enforce uppercase constant names): Prevent instantiation of MapperUtils and improve logging

([b0a1068ae384844](https://github.com/Lob2018/SudokuFX/commit/b0a1068ae384844d71bbcf4fd57cb661ecac0d8d))

♻️ refactor: Removes @Transactional for lazy-loaded &#x27;gamesid&#x27; collection

([c8acf65c7e160c8](https://github.com/Lob2018/SudokuFX/commit/c8acf65c7e160c859f76f61adb31b58e27e59c87))

♻️ refactor: Removes useless PlayerDto gamesid Set

([dc3896ef7a56315](https://github.com/Lob2018/SudokuFX/commit/dc3896ef7a56315cdae721de06f481819fc2cb32))

📚 docs Mappers, PlayerRepository, PlayerService: add concise JavaDoc including lazy loading details

([5f4807f44671445](https://github.com/Lob2018/SudokuFX/commit/5f4807f44671445c09e131150dbbf4426fa60502))

♻️ refactor:all MapStruct mappers and CurrentPlayerState for Spring integration and utility consolidation
- Unified mapping helper methods into MapperUtils with @Named qualifiers
- Updated all @Mapper interfaces (IPlayerMapper, IGameMapper, etc.) to use componentModel &#x3D; &quot;spring&quot;
- Replaced manual INSTANCE usage by Spring-managed beans for mapper dependencies and CurrentPlayerState
- Cleaned up redundant mapping annotations leveraging automatic MapStruct behavior
- Enhanced logging in utility methods for better null safety diagnostics

([f46cb65ac34808a](https://github.com/Lob2018/SudokuFX/commit/f46cb65ac34808a06968e5e7ad46a851fe7a535f))

♻️ refactor MyLogbackConfig:Adds a line break before ▓ Application entry ▓ message

([c9a3e4a91b91a89](https://github.com/Lob2018/SudokuFX/commit/c9a3e4a91b91a89cef384b12f069aff2f3dcaaa8))

🔧 chore pom.xml:Update spring-boot-starter-parent

([d8c250bc46573ab](https://github.com/Lob2018/SudokuFX/commit/d8c250bc46573ab038fbb14c69dd8254c79727fd))

🔧 chore: Log message now includes Platform.exit() indication

([755cdbe48b67a08](https://github.com/Lob2018/SudokuFX/commit/755cdbe48b67a08803edf1415bdcf09cf68772ef))

📚 docs:DOXYGEN

([d35da75f0a7fbb6](https://github.com/Lob2018/SudokuFX/commit/d35da75f0a7fbb64576ff91e1416e494ad1975d7))

✨ feat MenuNewViewModel:Completed (without tests)

([e91d22febeb9914](https://github.com/Lob2018/SudokuFX/commit/e91d22febeb99147fd594b7717552af101084c72))

📝 style: run automatic code formatter

([b4ceb9b0388bf40](https://github.com/Lob2018/SudokuFX/commit/b4ceb9b0388bf409bf9683e9be4377cfc4c202b8))

✨ feat (partially):integrate MenuNewViewModel with VersionService and Coordinator for reactive UI updates
- Added reactive StringBindings for &quot;New&quot; menu button texts and accessible texts with i18n support
- Bound button visibility and tooltips to ViewModel properties reflecting version status from VersionService
- Improved checkVersion task: unbind previous bindings, use daemon thread for background execution
- Added locale change listener to re-trigger version check dynamically on language change
- Updated Coordinator to manage navigation, keeping UI state logic in ViewModel
- Documented UI binding setup method clearly, following MVVM-C principles

([12ac564fd38e6bb](https://github.com/Lob2018/SudokuFX/commit/12ac564fd38e6bbb1424af6d2fcd083793b04fb0))

📚 docs:DOXYGEN

([50e3a44d199213c](https://github.com/Lob2018/SudokuFX/commit/50e3a44d199213c9278df72323d77f36bbe6ad4f))

📝 style: run automatic code formatter

([1e1313ea5cb5478](https://github.com/Lob2018/SudokuFX/commit/1e1313ea5cb547836965c5e975f46d44d972b4c4))

✨ feat MenuNewViewModel:Completed (without tests)

([46fdd452846170c](https://github.com/Lob2018/SudokuFX/commit/46fdd452846170cf0cd242bb321c1a054222ecc1))

📝 style MenuBackgroundViewModel:run automatic code formatter

([b206a89e9d762cd](https://github.com/Lob2018/SudokuFX/commit/b206a89e9d762cd2cb4ca99e264561fc30cae682))

♻️ refactor MenuMiniViewModel, MenuMaxiViewModel:removes new version button management

([5b494ce5a5e6915](https://github.com/Lob2018/SudokuFX/commit/5b494ce5a5e69155b8897aad41f4a151cba9a889))

📚 docs MenuBackgroundViewModel:createStringBinding and createFormattedBinding

([ee7ba8766fdc914](https://github.com/Lob2018/SudokuFX/commit/ee7ba8766fdc914054eae78931debf37b99efd50))

♻️ refactor MenuBackgroundViewModel:factorized

([e89469aeaf9b2fd](https://github.com/Lob2018/SudokuFX/commit/e89469aeaf9b2fddc9bebe8d8543f7bd2be1241b))

🐛 fix SudoMain: remove redundant throw after logging exception, then exit

([e6a8cb3e50e1814](https://github.com/Lob2018/SudokuFX/commit/e6a8cb3e50e1814f5a5e9fdf8b7203c0f3b56ca5))

🐛 fix MenuBackgroundViewModel:Format String inside log.error

([04b4e7e5731a6b4](https://github.com/Lob2018/SudokuFX/commit/04b4e7e5731a6b4ab176195bbaae649c9f93070b))

♻️ refactor sonarlint.xml:only GLOB for fileExclusions

([d3963707216cc69](https://github.com/Lob2018/SudokuFX/commit/d3963707216cc690fb5f977c6447e41bc6fe2193))

🔧 chore DefaultView:remove unnecessary comment

([6a9f677700b2c55](https://github.com/Lob2018/SudokuFX/commit/6a9f677700b2c5517766ac179b738865a4a0d0b1))

🐛 fix sonarlint.xml:use GLOB pattern for excluded paths

([cf1def40683572c](https://github.com/Lob2018/SudokuFX/commit/cf1def40683572ce05cbc9bf1ced7b909b37b1db))

♻️ refactor DefaultView:Extracts new version button

([55db1496ea4d077](https://github.com/Lob2018/SudokuFX/commit/55db1496ea4d077048604c160c5f20163a5cf241))

♻️ refactor DefaultView:Useless constants

([283e56913611bdb](https://github.com/Lob2018/SudokuFX/commit/283e56913611bdb6f3622ff54c23aeae7e2e69b4))

📚 docs:DOXYGEN

([43b160d37ad62c6](https://github.com/Lob2018/SudokuFX/commit/43b160d37ad62c677552d92b059f91b74abe29d0))

✨ feat MenuBackgroundViewModel:Menu background menu completed

([fd8158ac57eb18a](https://github.com/Lob2018/SudokuFX/commit/fd8158ac57eb18a7f7777d2d8a23bb5b8ec0fdd1))

♻️ refactor DefaultView: delegate difficulty level and stars update solely to MenuLevelViewModel

([20e5da93a2cb718](https://github.com/Lob2018/SudokuFX/commit/20e5da93a2cb71801761ca193f8fbe8196687ee1))

📚 docs MenuLevelViewModel:useless details

([238fdc5ba6b1b06](https://github.com/Lob2018/SudokuFX/commit/238fdc5ba6b1b065702817f9f90c19c042acda9e))

📚 docs MenuLevelViewModel:Unused comment

([a0c6c80e3d8c8f2](https://github.com/Lob2018/SudokuFX/commit/a0c6c80e3d8c8f24ad0044cbeebb615ff9dc1114))

♻️ refactor PossibilityStarsHBox: externalize percentage updates and enforce unidirectional binding
- Removed internal percentage.set(...) call to prevent binding conflicts
- Updated documentation to reflect read-only behavior of percentage property
- Switched from bidirectional to unidirectional binding in menu and level initialization methods

([73d3afd1fac3402](https://github.com/Lob2018/SudokuFX/commit/73d3afd1fac34026ddddcf117ef1c4cd184d55d7))

♻️ refactor PossibilityStarsHBox:remove redundant setPercentage setter to enforce property binding
- Removed setPercentage(int) method from component
- Encourages reactive updates via IntegerProperty binding
- Simplifies API by relying on bindBidirectional for percentage updates

([459fbf76bb0bdbb](https://github.com/Lob2018/SudokuFX/commit/459fbf76bb0bdbb562c1dfc7dac682a472f5a27f))

✨ feat MenuSolveViewModel:Solve menu is completed

([18472390e525281](https://github.com/Lob2018/SudokuFX/commit/18472390e52528145e2bf2ea5d64b3d66b050fa2))

♻️ refactor MyAlert,HelpViewModel: MyAlert support optional custom size via overloaded constructor (used by the Help dialog)

([5cc2cdce8425447](https://github.com/Lob2018/SudokuFX/commit/5cc2cdce84254475fd503a6343c087ed3d547008))

📚 docs:DOXYGEN

([b9bb8d09e7bd429](https://github.com/Lob2018/SudokuFX/commit/b9bb8d09e7bd42970475da46f892ba3b8b2816ce))

♻️ refactor DefaultView:maxiMenuInitialization removes unnecessary Tooltips instanciations

([3b5818fbc02c09f](https://github.com/Lob2018/SudokuFX/commit/3b5818fbc02c09f075947b4256286cca45b57a87))

📝 style: run automatic code formatter

([bc82ec38154eefa](https://github.com/Lob2018/SudokuFX/commit/bc82ec38154eefa1858ee2fd9467bc063a43d633))

✨ feat Save menu:completed to align with MVVM-C; add ViewModel bindings and ListView sync

([651e5a3c2a61131](https://github.com/Lob2018/SudokuFX/commit/651e5a3c2a611312de8b0ba6da700140a9cf8521))

♻️ refactor DefaultView:extract individual menu initializations to methods

([282c6283d0522b6](https://github.com/Lob2018/SudokuFX/commit/282c6283d0522b6c14bd65a922accc9efcbcd300))

🔧 chore MenuPlayerViewModel:useless comment

([b59e5e13b9e75da](https://github.com/Lob2018/SudokuFX/commit/b59e5e13b9e75dafa4932de7d6ed596af79eb8b0))

✨ feat MenuPlayer:Completed (without tests)

([7a9725e31d99c25](https://github.com/Lob2018/SudokuFX/commit/7a9725e31d99c25b974278cd7e3d48e7f003a0cd))

📚 docs default-view.fxml:Specifies when submenu are opened

([1e07035ec22759b](https://github.com/Lob2018/SudokuFX/commit/1e07035ec22759b3b63c911aa7420557a3d0010a))

🔧 chore pom.xml flyway-database-hsqldb,CI/CD codeql-action :Updated

([eb0a3382af7dac4](https://github.com/Lob2018/SudokuFX/commit/eb0a3382af7dac439fadc22d7a2eac6032db4885))

♻️ refactor MenuPlayerViewModel: Partial refactoring. Waiting for cell accessibility update with I18n.

([52f2ad46b571a7a](https://github.com/Lob2018/SudokuFX/commit/52f2ad46b571a7a32e9136d7795887cfe5912279))

📚 docs MenuMaxiViewModel: add class and method documentation

([0aaea5f21514722](https://github.com/Lob2018/SudokuFX/commit/0aaea5f215147225f352057222156939df5ca260))

♻️ refactor DefaultView: rename property from menuMaxiButtonBackuptext to menuMaxiButtonBackupText

([6a43f3900831a8b](https://github.com/Lob2018/SudokuFX/commit/6a43f3900831a8b8198c09d4526ab5cd764100d5))

✨ feat DefaultView,MenuMaxiViewModel: add MenuMaxiViewModel with I18n bindings for maxi buttons (excluding submenu buttons)

([8855984a790cf4b](https://github.com/Lob2018/SudokuFX/commit/8855984a790cf4b572516cfb116ee9deb20d211b))

🐛 fix hidden menu button in default-view.fxml to show mini menu instead of maxi menu

([9788d6140b8cc52](https://github.com/Lob2018/SudokuFX/commit/9788d6140b8cc522604555d7dfa5dee4bd794a67))

♻️ refactor MenuHiddenViewModel: use reactive I18n StringBinding
- Replace manual update of accessibility text with a reactive StringBinding
- Remove updateTexts() method and mutable StringProperty
- Bind accessibility text directly to I18n locale changes for automatic updates
- Simplify code and improve reactivity consistent with other ViewModels

([e24e5950c4e49b9](https://github.com/Lob2018/SudokuFX/commit/e24e5950c4e49b9be2d1b70159e2f717daf9af57))

📝 style: run automatic code formatter

([f0acc90e54062df](https://github.com/Lob2018/SudokuFX/commit/f0acc90e54062df6bace32c3c1b42a64241d3b41))

🐛 fix MenuMiniViewModel: ensure :selected pseudo-class is applied on level selection
- Replaced ineffective BooleanBinding-based pseudo-class update with direct listener on selectedLevelProperty()
- Ensures immediate and reactive styling for selected difficulty level in both mini and maxi buttons
- Updated Javadoc of bindLevelSelectedStyling() to reflect implementation change
- Removed obsolete isSelectedBinding() usage which prevented CSS state updates

([4e0ea07fa354a40](https://github.com/Lob2018/SudokuFX/commit/4e0ea07fa354a40ee52a90faaae78dbdcffc892d))

♻️ refactor DefaultView,MenuMiniViewModel:Javadoc and simplify bindings
- Added concise Javadoc for the class and all public methods
- Unified tooltip and accessible text logic using shared keys
- Replaced &#x60;textUpdate&#x60; approach with auto-updating StringBindings
- Simplified tooltip bindings using concatenated accessibility keys
- Ensured all bindings react to locale changes via I18n.localeProperty
- Clarified the role of internal binding creation methods
- Improved method naming consistency and overall readability

([541a03d1e033940](https://github.com/Lob2018/SudokuFX/commit/541a03d1e0339402c8f295cab22f8f958ea9cfe7))

♻️ refactor MenuLevelViewModel:Add doc and @SuppressWarnings to selectedLevelProperty to clarify its use for JavaFX bindings and prevent unused warning

([5013e10c0708531](https://github.com/Lob2018/SudokuFX/commit/5013e10c0708531ea2eee8c303469379366050aa))

♻️ refactor DefaultView: optimize pseudo-class binding in DefaultView using BooleanBinding
- Replace manual listener on selectedLevelProperty with reactive BooleanBinding
- Bind pseudo-class state updates to isSelectedBinding for cleaner and safer code
- Initialize pseudo-class state on binding setup to avoid UI inconsistencies

([8f3b2033b9cb27d](https://github.com/Lob2018/SudokuFX/commit/8f3b2033b9cb27d2e446d3e518593d200a3e1856))

🔧 chore DefaultView:remove outdated comment

([b08d5a4a5b35db8](https://github.com/Lob2018/SudokuFX/commit/b08d5a4a5b35db8ca1908247d941465d6a194b5c))

♻️ refactor MenuLevelViewModel:Unused method

([c8a6743e35afb6f](https://github.com/Lob2018/SudokuFX/commit/c8a6743e35afb6f6740cc31ac93c67ce4134d324))

♻️ refactor DefaultView:Unused property

([787614c395ed312](https://github.com/Lob2018/SudokuFX/commit/787614c395ed31254be00bf6babbb7e340b20beb))

♻️ refactor DefaultView,MenuLevelViewModel: align with MVVM-C, rename methods for clarity and add Javadoc documentation
- Renamed methods for improved readability
- Added Javadoc documentation to all public methods
- Added isSelectedBinding to expose selection state as a BooleanBinding
- Split bindLevel into multiple focused helper methods (bindLevelLabelText, bindLevelStarsVisibility, bindLevelAccessibility, bindLevelTooltipText, bindLevelSelectedStyling) to better isolate responsibilities

([5c5cb975434b2c7](https://github.com/Lob2018/SudokuFX/commit/5c5cb975434b2c76fff0ec538d3b7b5b7c54263d))

🔧 chore CI/CD dependency-review-action:Update

([2eddd942d4524e1](https://github.com/Lob2018/SudokuFX/commit/2eddd942d4524e113a15eb676a948b07200e5162))

🔧 chore MenuLevelViewModel: remove outdated comment

([b80ac4dadf4c84a](https://github.com/Lob2018/SudokuFX/commit/b80ac4dadf4c84a7011c0bfa64ddb5719032342c))

✨ feat complete MenuLevelViewModel and bind UI texts to locale changes via I18n

([3d1b8ca66919a1c](https://github.com/Lob2018/SudokuFX/commit/3d1b8ca66919a1c61cd0df99d45640b6a9b64115))

📚 docs I18n

([949ca231e0d0fc4](https://github.com/Lob2018/SudokuFX/commit/949ca231e0d0fc4161692a292accfaf2841f3740))

♻️ refactor DefaultView, MenuLevelViewModel, I18n: -moved menu level logic to MenuLevelViewModel and made DefaultView responsible for property bindings only
-I18n introduce observable Locale property for future support of automatic updates on language change

([79f0056f87a3adc](https://github.com/Lob2018/SudokuFX/commit/79f0056f87a3adc970a1e496c1f12e14e6b08aad))

✨ feat DefaultView,ActiveMenuOrSubmenuViewModel,MenuLevelViewModel: MenuMiniViewModel ready, and MenuLevelViewModel will mange the level buttons (work in progress)
-DefaultView removes level buttons binding with MenuMiniViewModel (MenuLevelViewModel will manage them)
-ActiveMenuOrSubmenuViewModel as a Spring component

([b0dcff7211533f4](https://github.com/Lob2018/SudokuFX/commit/b0dcff7211533f4cb796d2a47fc0fb1207a076fa))

✨ feat DefaultView,pom.xml:
-Started MenuMini binding for MenuMiniViewModel
-licenseHeader:Updated

([695d3ff713c62e1](https://github.com/Lob2018/SudokuFX/commit/695d3ff713c62e1106e4d9df2d20f2618976fbb1))

♻️ refactor Coordinator and MenuHiddenViewModel for improved MVVM-C architecture
- Refactored &#x60;Coordinator&#x60; to include language switching functionality with &#x60;toggleLanguage()&#x60; method.
- Updated &#x60;handleToggleLanguage()&#x60; to use &#x60;toggleLanguage()&#x60; and refresh ViewModel texts.
- Added dynamic binding for accessibility texts in &#x60;MenuHiddenViewModel&#x60;, allowing UI updates via properties.
- Simplified language toggle logic and integrated with ViewModel for better separation of concerns.
- Refactored &#x60;createViewTransition()&#x60; to streamline view transition process by removing redundant controller retrieval.
- Added &#x60;MenuHiddenViewModel&#x60; for handling business logic related to the &quot;hidden menu&quot; functionality, with methods for text updates and property bindings.

([f8047c56e33a72d](https://github.com/Lob2018/SudokuFX/commit/f8047c56e33a72d69b7502729bb7dfcf3c778670))

📚 docs SudoMain: clarify Coordinator is created only if Spring fails; default scene and dynamic font size are always set

([39ca22afd1a5319](https://github.com/Lob2018/SudokuFX/commit/39ca22afd1a53193d6373b6d12baad76d41da067))

📚 docs SudoMain: clarify Coordinator initialization occurs if Spring fails

([d0cda1c54de22c7](https://github.com/Lob2018/SudokuFX/commit/d0cda1c54de22c702d47c2b9b75c9de036b15316))

✨ feat(DefaultView): Toggle language between French and English and reload default view via language button

([2421c323ea1a2e6](https://github.com/Lob2018/SudokuFX/commit/2421c323ea1a2e698f0deab261c99ef463621619))

📝 style: run automatic code formatter

([59864647dc40ca4](https://github.com/Lob2018/SudokuFX/commit/59864647dc40ca4e16117585397e085d7387f3f2))

📚 docs Coordinator : update Javadoc for setDynamicFontSize method

([d6e11270246b01a](https://github.com/Lob2018/SudokuFX/commit/d6e11270246b01a5b9b4ab2831d06fc48308009d))

♻️ refactor - inject DynamicFontSize into Coordinator using setter
- added call to setDynamicFontSize in SudoMain during Coordinator initialization
- ensured DynamicFontSize is set after default scene
- improved JavaDoc for initializeCoordinator for clarity

([32c586b7417224f](https://github.com/Lob2018/SudokuFX/commit/32c586b7417224faca198bfdddc6cc128d00b004))

📚 docs DOXYGEN

([81c74f4effe29f7](https://github.com/Lob2018/SudokuFX/commit/81c74f4effe29f7964cf30de1799a7a677116e22))

♻️ refactor Coordinator:
- Clarify role of &#x60;getDefaultScene&#x60; and &#x60;setDefaultScene&#x60;.
- Detail dynamic font resizing in &#x60;setDynamicFontSize&#x60;.
- Refine explanation of FXML loading process in &#x60;setRootByFXMLName&#x60;.
- Enhance &#x60;getController&#x60; documentation for UI interaction handling.

([9d7d587120a14c2](https://github.com/Lob2018/SudokuFX/commit/9d7d587120a14c23fb058110533086df892f3d9d))

## v3.0.0 - 2025-05-12


♻️ refactor pom.xml(Jacoco exclude) : Exclude internal classes of VersionService from JaCoCo coverage report

([db3ec59497b786b](https://github.com/Lob2018/SudokuFX/commit/db3ec59497b786bad41adfbeac31ecbc5197b98a))

📚 docs: add missing JavaDoc for Coordinator class fields and methods

([fc6249897b570ca](https://github.com/Lob2018/SudokuFX/commit/fc6249897b570cab08e3901c8afc7d43c5cec4a0))

📚 docs README moved from MVVM to MVVM-C

([7c8223d9da50984](https://github.com/Lob2018/SudokuFX/commit/7c8223d9da509844804deeb30e5820295d7130bb))

✨ feat: add getSplashScreenScene() to ISplashScreenView and use it in CrashScreenView for splash-to-crash transition

([3db4ca9846e33a1](https://github.com/Lob2018/SudokuFX/commit/3db4ca9846e33a106b3fcae83e0b41a5cb9e8f07))

🔧 chore partially removes ISceneProvider (only for default), replaced by coordinator.getScene()

([caa1511485a19f4](https://github.com/Lob2018/SudokuFX/commit/caa1511485a19f4f0f2a62cdc9d12e7b04a2ba10))

🔧 chore Coordinator:will be the Scene provider

([2b6f900e02b04a1](https://github.com/Lob2018/SudokuFX/commit/2b6f900e02b04a15f55cbf0a080500feb1413b66))

🔧 chore: rename FxmlService to Coordinator

([93df38c8f678cd0](https://github.com/Lob2018/SudokuFX/commit/93df38c8f678cd082c42dd743cba60979ed47b36))

📚 docs DOXYGEN

([2eee75ee51c6902](https://github.com/Lob2018/SudokuFX/commit/2eee75ee51c6902888738d41331fb51b4b841a91))

✨ feat: Add spotless-maven-plugin configuration
- Commented out ratchetFrom for initial main commit
- Applied formatting to all relevant files
- Updated licenseHeader content

([9eb80e323fedd5f](https://github.com/Lob2018/SudokuFX/commit/9eb80e323fedd5fb11a78de42e12fee013ea54c0))

📚 docs DOXYGEN

([def803fc7bbd564](https://github.com/Lob2018/SudokuFX/commit/def803fc7bbd564818a908b9f34c4af268903ffd))

📚 docs README:Add Build with spotless-maven-plugin

([5428aca0e326084](https://github.com/Lob2018/SudokuFX/commit/5428aca0e3260846bea22b6252191850b662f611))

✨ feat:Add Spotless plugin for automatic source file formatting
- Format &#x60;.gitignore&#x60;, &#x60;.xml&#x60;, &#x60;.md&#x60;, &#x60;.yaml&#x60;, and other files.
- Apply &#x60;google-java-format&#x60; to Java files with import sorting and unused import removal.
- Format &#x60;pom.xml&#x60; files with element sorting and whitespace management options.
- Format SQL files for Flyway migrations.
- Add two executions: &#x60;spotless-apply&#x60; for formatting and &#x60;spotless-check&#x60; for format verification.

([ad9556fdba90728](https://github.com/Lob2018/SudokuFX/commit/ad9556fdba90728af539cfd57dc044dc3dfea1f2))

🔧 chore flyway-database-hsqldb:Update

([99c8c34119bd625](https://github.com/Lob2018/SudokuFX/commit/99c8c34119bd625a87edbafb04d53896c7877b87))

📚 docs DOXYGEN

([af27e514db521a8](https://github.com/Lob2018/SudokuFX/commit/af27e514db521a8186ad841f0e7b5d6262dce6f4))

🐛 fix .gtiattributes: ensure .bat files use CRLF line endings and UTF-8 encoding without BOM

([88683d5b32d98de](https://github.com/Lob2018/SudokuFX/commit/88683d5b32d98de15f717176565b5f968b7d7cee))

♻️ refactor SudoMain:Rename from SpringInitializer to springInitializer

([516d60f418fba6d](https://github.com/Lob2018/SudokuFX/commit/516d60f418fba6d285faf5abc64783bd3433034f))

♻️ refactor SudoMain:Rename SpringContextInitializer for clarity

([d6fa46e3aba6474](https://github.com/Lob2018/SudokuFX/commit/d6fa46e3aba647401ae0259fa860dbb60e648aff))

🔧 chore .gitmessage : moved from global to local for the template

([b0c72c888add2e9](https://github.com/Lob2018/SudokuFX/commit/b0c72c888add2e972ff388dc8494e6460d8369a9))

🐛 fix SpringContextInitializerUTest: Add CountDownLatch to properly await task completion in test

([553abdc60010e8c](https://github.com/Lob2018/SudokuFX/commit/553abdc60010e8c60948ecc848088c399fa1ce20))

🐛 fix SpringContextInitializerUTest:Replace @SpringBootTest with @ExtendWith(ApplicationExtension.class) for JavaFX context initialization test

([6ac78646de6d319](https://github.com/Lob2018/SudokuFX/commit/6ac78646de6d3194df23832403676af8bca50f24))

🐛 fix SpringContextInitializer: Unused import

([051efea8865e106](https://github.com/Lob2018/SudokuFX/commit/051efea8865e1065592ad37f01d5e742d56e9de3))

♻️ refactor SpringContextInitializer,pom.xml(Jacoco exclude) :
-Handles asynchronous initialization of the Spring application context using a JavaFX Task.
-Exclude internal classes of SpringContextInitializer from JaCoCo coverage report

([42672ea9a3f5282](https://github.com/Lob2018/SudokuFX/commit/42672ea9a3f528214067888e1ba388b2e9fd7d18))

🐛 fix PlayerService : Empty for tests

([da68e0c18ed057c](https://github.com/Lob2018/SudokuFX/commit/da68e0c18ed057c07108630af2dc71002ec0bf16))

🔧 chore CI/CD dependency-review-action : update

([f0e73038f89f873](https://github.com/Lob2018/SudokuFX/commit/f0e73038f89f873262e6a12dc67295983d74cb55))

✨ feat:Adds a Coordinator for MVVM-C

([622cc3548dcbb33](https://github.com/Lob2018/SudokuFX/commit/622cc3548dcbb33c8ed5337dac745e2dab6d220e))

🔧 chore pom.xml,.gitmessage:Moved to version 2.0 due to significant architectural changes
-Optimize line breaks in gitmessage

([6dcc9cf2d85cdd5](https://github.com/Lob2018/SudokuFX/commit/6dcc9cf2d85cdd5f80b6f8182097166f2145a136))

🔧 chore .gitmessage: Details

([4048f6e31f9ef46](https://github.com/Lob2018/SudokuFX/commit/4048f6e31f9ef46ce740dd2d991b20ceb0ec5911))

🔧 chore .gitmessage: Details

([12c0645b25f7c9d](https://github.com/Lob2018/SudokuFX/commit/12c0645b25f7c9d813f00bf7ca07feb8df63ebbc))

🔧 chore: Details

([ec320b8b2828c6d](https://github.com/Lob2018/SudokuFX/commit/ec320b8b2828c6ddb9a345eeed17e36b9f9baef4))

✨ feat .gitmessage: Adds standard commit message template

([c2383c97440abf1](https://github.com/Lob2018/SudokuFX/commit/c2383c97440abf15e880984e829963509345df6c))

feat: introduce CurrentPlayerState component to share PlayerDto across ViewModels
- Created @Component CurrentPlayerState with an ObjectProperty&lt;PlayerDto&gt;
- Automatically initializes the current player from PlayerService if not set
- Exposes getter, setter, and JavaFX property for binding
- Enables shared player state management and reactive UI updates across ViewModels

([b447aada6ad13e6](https://github.com/Lob2018/SudokuFX/commit/b447aada6ad13e69952a564a028806b5ac076692))

fix BackgroundViewModelUTest : removes givenViewModelAndColorPicker_whenInitCalled_thenColorIsSetAndColorPickerUpdated redondant cast

([1ac22b2b575161b](https://github.com/Lob2018/SudokuFX/commit/1ac22b2b575161b5816637474a395cdcf8a89c06))

chore BackgroundViewModelTest, BackgroundViewModelUTest : renamed

([67a846ee6e713f2](https://github.com/Lob2018/SudokuFX/commit/67a846ee6e713f2a412b43b64a51700f5ff759e1))

chore CI/CD qodana-action : update

([8a3d8af62626c8b](https://github.com/Lob2018/SudokuFX/commit/8a3d8af62626c8b76b41375b0373256ba6ea2241))

test DifficultyLevelTest, BackgroundViewModelTest:
- DifficultyLevelTest test methods respect the Given-When-Then naming convention
- BackgroundViewModelTest (partial)

([f8134a50e60ec3b](https://github.com/Lob2018/SudokuFX/commit/f8134a50e60ec3b1e6853adbb6059fd8250bf6fe))

refactor: externalize toasts and errors to I18n, add logging to image handling

([6e9776bce22e7ca](https://github.com/Lob2018/SudokuFX/commit/6e9776bce22e7cabbf16c827945403d93186107b))

chore BackgroundViewModel : Reorganize methods

([7c3284771a5cd72](https://github.com/Lob2018/SudokuFX/commit/7c3284771a5cd7264f43837229ddf8df981baa16))

chore BackgroundViewModel : TODO

([4eb63b186f0e18e](https://github.com/Lob2018/SudokuFX/commit/4eb63b186f0e18ef3bbbf8b4889a545c10cc946d))

chore BackgroundViewModel : Add TODO

([679b791822c7cb7](https://github.com/Lob2018/SudokuFX/commit/679b791822c7cb767e601943012792c574c532f4))

fix DefaultView: Unused imports

([b795cb0c50ed0a4](https://github.com/Lob2018/SudokuFX/commit/b795cb0c50ed0a4cd05d07a78a469a92269298ae))

chore DefaultView, BackgroundViewModel :moved SERVICE TODO

([0f5742a6add9f54](https://github.com/Lob2018/SudokuFX/commit/0f5742a6add9f5452992598a8103dc34033ccb2e))

feat BackgroundViewModel (image and color): handle background color selection/application
- Sets and applies background color from a predefined value.

([8ae2847a605a908](https://github.com/Lob2018/SudokuFX/commit/8ae2847a605a90828b29b7a05f5d938ebf432219))

refactor BackgroundViewModel: Renamed methods to include &#x27;Image&#x27; for clarity and consistency

([be27e282a1330d0](https://github.com/Lob2018/SudokuFX/commit/be27e282a1330d0562233d5760c3070211ee7ea0))

feat BackgroundViewModel (only for image not color): handle background image selection and application
- Validates image file format and loads the image asynchronously.
- Resizes the image to fit the GridPane background.
- Displays notifications using toaster and spinner during the loading process.

([b0a2ea9ceaeb58a](https://github.com/Lob2018/SudokuFX/commit/b0a2ea9ceaeb58ae090d1eeaa60c0ca7cb59176f))

feat LevelViewModel: Add view model for managing difficulty level and stars percentage
- Introduced &#x60;LevelViewModel&#x60; to store and manage selected difficulty level and corresponding stars percentage.
- Added methods to get and set the selected level and stars percentage.

([2666186102468fc](https://github.com/Lob2018/SudokuFX/commit/2666186102468fc50c30d0b7adb24b4c91d2b1b8))

feat HelpViewModel: for displaying the Help menu dialog

([875c052ec3f60fe](https://github.com/Lob2018/SudokuFX/commit/875c052ec3f60fec3a0a7e42929ffb49a794cb4b))

refactor SudoMain: Rename getPauseTransition to createViewTransition

([df4032d486a67a6](https://github.com/Lob2018/SudokuFX/commit/df4032d486a67a614d086efadf13c16be7525151))

chore codeql-action CI/CD : update

([983571c857bdd72](https://github.com/Lob2018/SudokuFX/commit/983571c857bdd725a14bd592dfdfdf7c3ef9ba7d))

test DifficultyLevelTest

([0feba17b4b57faf](https://github.com/Lob2018/SudokuFX/commit/0feba17b4b57faf97cd071c5e30205bda1074516))

refactor MyRegex: improve null handling and simplify validation
- Replace manual null checks with Objects.requireNonNull and clear messages
- Remove try-catch in isValidatedByRegex to let NullPointerException propagate
- Use equals() instead of &#x3D;&#x3D; for pattern comparison
- Simplify isValidPassword using streams and explicit null checks
- Clean imports and enhance Javadoc comments

([017e68764d1fa87](https://github.com/Lob2018/SudokuFX/commit/017e68764d1fa87e024d7e1f7e0652f5bf936ccc))

Doc DOXYGEN

([5792c1978254600](https://github.com/Lob2018/SudokuFX/commit/5792c1978254600bfb5d411f6616c38448ef5856))

fix: drop Lombok annotations from config and service classes and README

([e0a0435a45cf981](https://github.com/Lob2018/SudokuFX/commit/e0a0435a45cf9819fffcee03877d2006de742d4b))

fix: drop Lombok annotations from config and service classes (kept in models only)

([06e295b052243a5](https://github.com/Lob2018/SudokuFX/commit/06e295b052243a51acd4c7fd784f100fdb9750b2))

fix: remove @Autowired constructor annotation (not applicable for tests)

([57f58d85d51644b](https://github.com/Lob2018/SudokuFX/commit/57f58d85d51644b8d9832b597be04d5645c9c419))

refactor PossibilityStarsHBox: Clarify default percentage value as 100%

([36f3468f0d2860d](https://github.com/Lob2018/SudokuFX/commit/36f3468f0d2860d481e1697ff580f0d79a03b444))

chore i18n: add period after version in help dialog message

([411d1b23241f250](https://github.com/Lob2018/SudokuFX/commit/411d1b23241f250d267d9324d68e2a071a9ec28a))

feat(DefaultView):
- no default level selected
- solve menu shows 0% when no level is selected
- solve menu follows difficulty level probabilities

([abd9940c0f74da9](https://github.com/Lob2018/SudokuFX/commit/abd9940c0f74da973d0893b4ad132eb2f385afcd))

Doc DOXYGEN

([e1697d8a9221bd4](https://github.com/Lob2018/SudokuFX/commit/e1697d8a9221bd421aa1a1bafa5bd3c3681a85fb))

chore DefaultView: clarify method parameter names and update Javadoc

([bf78ec6aab5cfc6](https://github.com/Lob2018/SudokuFX/commit/bf78ec6aab5cfc603d92ca96c3940b2b9a48b12d))

Doc DOXYGEN

([18adebb9c093c4c](https://github.com/Lob2018/SudokuFX/commit/18adebb9c093c4ce89b00c964234eb5c8b742b19))

refactor DefaultView,PossibilityStarsHBox: unify tooltip and accessibility text formatting into formattedTextBinding method
- Consolidated text formatting logic into a single method with visibility and role description options.
- Improved naming for clarity and maintainability.
- Updated Javadoc for class and method accordingly.

([f511d7a2095e0c9](https://github.com/Lob2018/SudokuFX/commit/f511d7a2095e0c9ec1e3596a744457fae5a0c8db))

Doc DOXYGEN

([9aed1935bd4f774](https://github.com/Lob2018/SudokuFX/commit/9aed1935bd4f7749e40d96fad99e4cc159129c64))

fix DefaultView: DIFFICULTY_LEVEL_PSEUDO_SELECTED
- Make DIFFICULTY_LEVEL_PSEUDO_SELECTED static final
- Align with Java naming conventions for constants (java:S116)

([91887576528a5f4](https://github.com/Lob2018/SudokuFX/commit/91887576528a5f4a8427ef2088ca35f02233ca2e))

refactor DefaultView handleMenuMiniShow:
- Hide menu after 10 seconds
- Ensure menu is hidden only if &quot;menuMiniButtonShow&quot; has focus
- Add exception handling for potential errors during focus check

([f43985664ca8bd0](https://github.com/Lob2018/SudokuFX/commit/f43985664ca8bd0fcdda48cb3541a8efb939cff5))

refactor default-view: bind mini button role description and extend menu mini auto-hide to 15s

([92a17aef8e0bbe3](https://github.com/Lob2018/SudokuFX/commit/92a17aef8e0bbe33334eb2ec10e1d6fe683c4b7d))

refactor: difficulty button binding to include percentage and improve accessibility integration
- Renamed method to &#x27;initializeDifficultyButton&#x27; for clarity and alignment with behavior.
- Accessibility text binding now reacts to both difficulty level and percentage changes.
- Tooltip and accessible text properties for main and mini buttons are now bound directly.
- Updated documentation to reflect new behavior and responsibilities.
- Replaced style class manipulation with pseudo-class state for cleaner styling logic.

([f5061ea714270f5](https://github.com/Lob2018/SudokuFX/commit/f5061ea714270f53f0e7f3ff01f9ca78f960f06e))

fix difficulty button setup:
-added helper for tooltip binding
-ensured proper unbinding before rebinding properties

([fb1bcbac9fcf3df](https://github.com/Lob2018/SudokuFX/commit/fb1bcbac9fcf3df5e60c0b7340141b792a970b60))

feat DefaultView, StarsForPossibilitiesHBox:
- Implement level toggling
- Add PossibilityStarsHBox component

([05d58ad77cafc45](https://github.com/Lob2018/SudokuFX/commit/05d58ad77cafc45e95fe4687e9f3385429190be6))

fix DefaultView:handleMenuMiniShow() with ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI and not a null value

([426c86d93bac70c](https://github.com/Lob2018/SudokuFX/commit/426c86d93bac70cad0c1dff9489078533a7b4223))

refactor ActiveMenuOrSubmenuViewModel,ExceptionTools : Remove Spring component annotation from ActiveMenuOrSubmenuViewModel
- ActiveMenuOrSubmenuViewModel is no longer a Spring component.
- Modify setActiveMenu to throw IllegalArgumentException if the menu is null.
- ExceptionTools got dedicated logAndThrowIllegalArgument method

([8002ccccfd78c35](https://github.com/Lob2018/SudokuFX/commit/8002ccccfd78c3591862096cd22859aee4550c46))

doc README : Project &gt; Overview

([9aba39572c0ea7a](https://github.com/Lob2018/SudokuFX/commit/9aba39572c0ea7a82654f282b0fe6da3d1631bf9))

chore pom.xml : Update

([f9f9ca9d6665194](https://github.com/Lob2018/SudokuFX/commit/f9f9ca9d666519442a25a1ed92e7957d0178efdf))

doc DOXYGEN

([27b42d29f069dfe](https://github.com/Lob2018/SudokuFX/commit/27b42d29f069dfe73f0811e2ae06edf15655f8ca))

feat pom.xml, DefaultView, OS release scripts : add new JVM application properties used in Help submenu
- app.organization: specifies the organization responsible for the application
- app.license: specifies the license under which the application is distributed

([a4ff45dd5aca4b6](https://github.com/Lob2018/SudokuFX/commit/a4ff45dd5aca4b67a651d61ead7014e96ffd6fac))

chore CI/CD download-artifact qodana-action codeql-action: Update

([30ea56c6e75177e](https://github.com/Lob2018/SudokuFX/commit/30ea56c6e75177e6e9ee435b36990afaa949d93f))

chore rename ActiveMenuViewModel to ActiveMenuOrSubmenuViewModel

([a6d1494a578ddd8](https://github.com/Lob2018/SudokuFX/commit/a6d1494a578ddd83445d231578e75a1847d3c148))

feat DefaultView: add Help menu dialog with game rules and log path

([97f6a2ea49482b5](https://github.com/Lob2018/SudokuFX/commit/97f6a2ea49482b5629e1eabe48dea995a9d979ac))

feat MyAlert : Custom alert dialog with specific styling.

([762f7cccf9d4585](https://github.com/Lob2018/SudokuFX/commit/762f7cccf9d4585332dfa9f480fbddcd72ecd13b))

chore UI style.css : menuMiniButton adjustments

([2917ac865463e0e](https://github.com/Lob2018/SudokuFX/commit/2917ac865463e0e6e860ad0cb5e10305e731b667))

feat DefaultView: Activate MINI menu and hide it after 10s if still active, setting active menu to HIDDEN

([73b5cf8a9107040](https://github.com/Lob2018/SudokuFX/commit/73b5cf8a910704058310a1ecb3e379a9f8dd934e))

feat DefaultView: Activate MINI menu and hide it after 4s if still active, setting active menu to HIDDEN

([c56c369bc4a7397](https://github.com/Lob2018/SudokuFX/commit/c56c369bc4a7397120ca122a35ae26e0ca125d42))

feat DefaultView: handleMenuMaxiShow and submenu focus handling
- handleMenuMaxiShow(ActionEvent event): Activates the MAXI menu and sets focus
  on the corresponding button based on the submenu source button of the event.
- Ensures focus is set on the corresponding button when activating submenus

([28735b91459ecc3](https://github.com/Lob2018/SudokuFX/commit/28735b91459ecc387f94607182f116cd74b86969))

fix UI Menu adjustments : Improve layout consistency and rendering
- Changed alignment from CENTER to TOP_CENTER to unify Player and Backup
- Added padding to menu for better spacing

([7fd186ff129b75b](https://github.com/Lob2018/SudokuFX/commit/7fd186ff129b75bbeb609d51a0a5834204ef2dd8))

fix DefaultView : Unused method ActionEvent should be removed

([1555cb888c14055](https://github.com/Lob2018/SudokuFX/commit/1555cb888c1405511b4adeccf28bb46e83f033d2))

fix DefaultView : Unused method ActionEvent should be removed

([515f0c509c36fe2](https://github.com/Lob2018/SudokuFX/commit/515f0c509c36fe2894ce148de01a67e8793d87a8))

chore style.css : #menuPlayerListView,#menuSaveListView height adjustment

([d49e339746d66fe](https://github.com/Lob2018/SudokuFX/commit/d49e339746d66feca8a2a0bcdec55b1c3cad4b7a))

chore style.css : #menuPlayerListView,#menuSaveListView height adjustment

([5deedc34928eb3e](https://github.com/Lob2018/SudokuFX/commit/5deedc34928eb3e1ce0a3fb2ebc58de8247becd7))

feat ActiveMenuViewModel: added to manage active menu with enum and property binding via getActiveMenu()

([111221f696e7dba](https://github.com/Lob2018/SudokuFX/commit/111221f696e7dba868fbc9b293650ed453b0e1fb))

feat Toaster:
-A boolean to request focus or not when showing
-1.5s delayed removal for current toast via removeToast()

([c6b8079027de7ac](https://github.com/Lob2018/SudokuFX/commit/c6b8079027de7ac090fd9e95294f773af6ec54ea))

feat Toaster : Specifies when to removeToast()

([eb8145a971fb116](https://github.com/Lob2018/SudokuFX/commit/eb8145a971fb116a304acfd19715b42917271e2b))

feat Toaster : add and refine toast removal methods
- add removeToast(): removes the currently displayed toast, if any
- improve removeThisToast(ToasterButton): removes a specified toast, typically from ActionEvent.getSource()

([ad142897d27a0d2](https://github.com/Lob2018/SudokuFX/commit/ad142897d27a0d299d7c2e2eb40a9ad157f29666))

fix DefaultView : removed gif files for background image

([39a5cec375be134](https://github.com/Lob2018/SudokuFX/commit/39a5cec375be134c10072c532b42f3ee3b4faf51))

chore: Opaque blue circle around the opaque white spinner for accessibility contrast

([9995bb3737c6b0c](https://github.com/Lob2018/SudokuFX/commit/9995bb3737c6b0c1c3a0f5d7e777cf792047a64e))

fix DefaultView : String literals are not duplicated

([9dcb296710e12dc](https://github.com/Lob2018/SudokuFX/commit/9dcb296710e12dc5e33e22322e00b9346fe696df))

chore CI/CD step-security/harden-runner : Update

([64aed157efd6d16](https://github.com/Lob2018/SudokuFX/commit/64aed157efd6d16e12ad9fb54ad967f95fa22343))

doc : SpinnerGridPane &amp; DOXYGEN

([2a04f9180582568](https://github.com/Lob2018/SudokuFX/commit/2a04f91805825684c723d8b34b975ab2045df316))

feat fix SpinnerGridPane ToasterVBox :
-create SpinnerGridPane component
-fix : SpinnerGridPane ToasterVBox are not @Component : Spring components should be injected via constructor or field injection, but not combined with @FXML

([d0aeeea47b448f1](https://github.com/Lob2018/SudokuFX/commit/d0aeeea47b448f1c4a36cb12f47a217f6ff95581))

refactor runConfigurations: Specifies the names of Doxygen configuration files

([a8ea0cafbe92558](https://github.com/Lob2018/SudokuFX/commit/a8ea0cafbe92558f6c52692186db6a9dcd738382))

refactor SudoMain: Simplify Spring context init as duplicated in *.properties

([a51cb7cc8403872](https://github.com/Lob2018/SudokuFX/commit/a51cb7cc84038729ea1644b8372550400ee427bd))

refactor SudoMain, *.properties : Disable embedded Tomcat in all configs and enable lazy Spring initialization

([f7d18fbbcc9b5fa](https://github.com/Lob2018/SudokuFX/commit/f7d18fbbcc9b5faaffd481aeaf30b097cdc157a2))

chore pom.xml : Update flyway-database-hsqldb

([b0392c1a4ceeebb](https://github.com/Lob2018/SudokuFX/commit/b0392c1a4ceeebbb8628b54436cda862f580720e))

fix : Debug feature is deactivated.

([e57c4d7ddad6ace](https://github.com/Lob2018/SudokuFX/commit/e57c4d7ddad6aceb5a66cfc3d6be50e560572792))

chore DefaultView : BackgroundImage in a Task (begins)

([5587349d151d105](https://github.com/Lob2018/SudokuFX/commit/5587349d151d1052fa8d9a9d6cf7a2337a16595e))

chore DefaultView : Spinner adjustments

([e9c126c57b00c95](https://github.com/Lob2018/SudokuFX/commit/e9c126c57b00c9532c46dfc12c4c3f8bc1028cf3))

feat DefaultView : Image chooser started

([424dd4f81423cd0](https://github.com/Lob2018/SudokuFX/commit/424dd4f81423cd0d88e55d3da796a19099e1d40b))

feat ToasterVBox : Request focus when adding

([5c1ea45ae2f51e0](https://github.com/Lob2018/SudokuFX/commit/5c1ea45ae2f51e022a7f302318f23ea52c531b16))

feat DefaultView : Spinner

([eb08e056a1b0fe5](https://github.com/Lob2018/SudokuFX/commit/eb08e056a1b0fe55bf9f4c366ebb28a5d513aae7))

chore pom.xml flyway-database-hsqldb : Update

([94651830a662d89](https://github.com/Lob2018/SudokuFX/commit/94651830a662d898ac972c3983453143ffde0dfb))

feat V1_1__insert_initial_values.sql : Background hexcolor is opaque black

([d02ab275edf7f2e](https://github.com/Lob2018/SudokuFX/commit/d02ab275edf7f2ec7098f22e2fc35f11f015e831))

feat style.css : sudokuFXStackPane is black

([837ec61c9b07cc2](https://github.com/Lob2018/SudokuFX/commit/837ec61c9b07cc2e92d74e8ff8015d74173e1dc1))

feat SudokuFX_in_action.jpg : Updated

([5b173d16810f4ef](https://github.com/Lob2018/SudokuFX/commit/5b173d16810f4ef91a6d334453a232b41b165977))

feat runConfigurations : Adds SudokuFX [Doxygen Unix]

([7efdc606eb56eed](https://github.com/Lob2018/SudokuFX/commit/7efdc606eb56eed5430de561b5c0f999ff2d12c6))

fix without .whitesource : Mend Bolt for GitHub error always occurred while running the Security Check - got scan token but nothing to do with

([69ac5e2cc0d270f](https://github.com/Lob2018/SudokuFX/commit/69ac5e2cc0d270f02a8c12c3156119553fb02ac2))

feat ScreenSize enum : Get visual width and visual height

([f1d09a3736fbe76](https://github.com/Lob2018/SudokuFX/commit/f1d09a3736fbe765ad19f0c102aa337007cc4aa8))

doc Doxygen

([f5d54aa0e170480](https://github.com/Lob2018/SudokuFX/commit/f5d54aa0e170480c238602a9ccdea8117989cc41))

chore SudokuFX_in_action : Update

([59c2f715a24a1a4](https://github.com/Lob2018/SudokuFX/commit/59c2f715a24a1a4cef508bbb7a3237df3f4f08be))

fix pom.xml generate-docs: Remove automatic activation for generate-docs-unix and generate-docs-windows profiles

([326ad52d536ea83](https://github.com/Lob2018/SudokuFX/commit/326ad52d536ea839fc0a68336811df121866c0cd))

fix style.css: Removes -fx-graphic-text-gap from color-picker-label for better rendering when resizing

([c801ec65121f989](https://github.com/Lob2018/SudokuFX/commit/c801ec65121f989fb3d7b8d491b0725c2e60880d))

refactor scripts :
- unixLicense.sh : Adds echo &quot;&quot; before and after the action&#x27;s result
- Doc Doxygen

([5b4885321d8ca45](https://github.com/Lob2018/SudokuFX/commit/5b4885321d8ca452044a1f7420f70712e92a4e6c))

chore : Updates LICENCE_TO_EDIT and doc Doxygen

([f340cb6b0c42ed0](https://github.com/Lob2018/SudokuFX/commit/f340cb6b0c42ed0104c54b4bd1e52c056ca07de0))

chore CI/CD setup-java : Update

([57f4009ee125430](https://github.com/Lob2018/SudokuFX/commit/57f4009ee12543087f9ccf076cac07d503593d8d))

feat Maven Run Configuration: Update with NVD vulnerability check
- Update parent, plugins, and dependencies for NVD vulnerability check
- Add NVD API key to .mvn/maven.config
- Ignore .mvn/maven.config in .gitignore
- HTML report generated at target/dependency-check-report.html

([080bd0d3bef24fa](https://github.com/Lob2018/SudokuFX/commit/080bd0d3bef24faef85daeb1af9a9338265994d4))

feat auto-complete LICENSE.txt &amp; Doxygen docs :
- Replace the Doxygen Maven profile with dedicated Windows &amp; Unix profiles that also update LICENSE.txt.
- Generate THIRD-PARTY.txt with third-party dependency licenses via Maven.
- Append LICENSE_TO_EDIT.txt and THIRD-PARTY.txt to LICENSE.txt using dedicated scripts.
- Maintain dependencies exclusively in LICENSE_TO_EDIT.txt.

([c0c4d8e063e40b3](https://github.com/Lob2018/SudokuFX/commit/c0c4d8e063e40b3407204858140f1a31f52f35a5))

Chore CI/CD : Update codeql-action

([1be4226b0951636](https://github.com/Lob2018/SudokuFX/commit/1be4226b095163669d8cbe6ca8586282d8da475a))

Chore pom.xml : Update
-jacoco-maven-plugin
-flyway-database-hsqldb

([34957d44c602447](https://github.com/Lob2018/SudokuFX/commit/34957d44c60244788577f92aebd01024e483a188))

feat ColorPicker : Prepares storage and retrieval of hexadecimal color value

([98596fb75062a30](https://github.com/Lob2018/SudokuFX/commit/98596fb75062a30a085cd75c87b8232470520633))

refactor Background &amp; Models :
-Background hexcolor maximum size is 8 (RRGGBBAA)
-Use wrapper types (Integer, Boolean) instead of primitives

([309cc0aa2ac04f4](https://github.com/Lob2018/SudokuFX/commit/309cc0aa2ac04f46808787219262dbafed2177d8))

feat DefaultView style.css  :
-Implements the ColorPicker

([5174405c56fb344](https://github.com/Lob2018/SudokuFX/commit/5174405c56fb344067be91452eb8b3396b6bec8e))

feat DefaultView style.css  :
-Partially implements the ColorPicker
-Sets the button&#x27;s background color to #DDDDDD of the opened submenu&#x27;s when selected

([6df57ea46e88842](https://github.com/Lob2018/SudokuFX/commit/6df57ea46e888423fc74133b5e37b8201f306371))

chore CI/CD harden-runner  : Update

([13b87a08cde1efb](https://github.com/Lob2018/SudokuFX/commit/13b87a08cde1efbac6df82b0d7072e9ff43ca66d))

chore SudokuFX_in_action.jpg: Without DropShadow for the case multiple numbers

([59b7517d716822e](https://github.com/Lob2018/SudokuFX/commit/59b7517d716822e086874f2d0cec0a4d18e0fffe))

chore SudokuFX_in_action.jpg: Without DropShadow for the grid numbers

([e441a2ca18a6171](https://github.com/Lob2018/SudokuFX/commit/e441a2ca18a61718b6b187041b639060615b3794))

chore SudokuFX_in_action.jpg : Without DropShadow

([6d81a0c5d6c39f6](https://github.com/Lob2018/SudokuFX/commit/6d81a0c5d6c39f629c482d4843ec1f22621be53c))

fix DefaultView : Removes DropShadow because it&#x27;s only in pixels for y position

([a0f30c9e63c748e](https://github.com/Lob2018/SudokuFX/commit/a0f30c9e63c748e7ba3ec40f69a9c1d6eb789cfc))

feat DefaultView : Backup menu only UI

([e78f77b7c8829e9](https://github.com/Lob2018/SudokuFX/commit/e78f77b7c8829e92d02082e4b8ed29119f8457c2))

feat DefaultView : Backup menu

([4759e3c30d0bdc4](https://github.com/Lob2018/SudokuFX/commit/4759e3c30d0bdc42aa0c4cc55e3a2271ea1a1220))

feat DefaultView :
-Colored selected level button
-Colored selected submenu button
-DropShadow for buttons

([55633831f4d7d55](https://github.com/Lob2018/SudokuFX/commit/55633831f4d7d557220cfa1e3cb0b3c24a2a18e4))

fix DefaultView : Remove System.out.println

([1e7bb382091006d](https://github.com/Lob2018/SudokuFX/commit/1e7bb382091006ddb62412280924bb2181d409d2))

feat DefaultView :
- Implement solve menu functionality
- Calculate star ratings based on percentage of possibilities
- Indicate possibilities in maxi menu and solve menu
- Specify role description for selected maxi menu levels
- Enable player menu scrollTo using JavaFX Application Thread

([7cee94d4ba78930](https://github.com/Lob2018/SudokuFX/commit/7cee94d4ba789303dae9e030519ae94401ba4df7))

Doc SudoMain : Updates springContextTask doc

([d2d4ab7974771a0](https://github.com/Lob2018/SudokuFX/commit/d2d4ab7974771a0393d43b568c3ec64be2a62499))

Fix MacOS icon

([f1133aa899523e3](https://github.com/Lob2018/SudokuFX/commit/f1133aa899523e35fc7d71e783d9747413b259b3))

Doc DOXYGEN

([860cb0be26061cd](https://github.com/Lob2018/SudokuFX/commit/860cb0be26061cdc2ec28180f2ebb55551bf9d2c))

Chore pom.xml : Update
-flyway-database-hsqldb
-maven-surefire-plugin

([31bbdb4cae3ef06](https://github.com/Lob2018/SudokuFX/commit/31bbdb4cae3ef06600b0867a46d4bd2e4981d065))

Chore SudokuFX_in_action.jpg : Updated with the true tooltip

([5ebb97ad8a00bd9](https://github.com/Lob2018/SudokuFX/commit/5ebb97ad8a00bd94722e213f64a6f3231f436daf))

Feat DefaultView :
-ListView uses all available height in the menu
-ListView HBox Label uses all available space and moves the button to the right

([8c771a8d452fbd8](https://github.com/Lob2018/SudokuFX/commit/8c771a8d452fbd86ce45a9ab3fea5789e9db2f15))

Feat DefaultView : Tooltips for dropdown buttons shows the accessibility roles

([685316800d53281](https://github.com/Lob2018/SudokuFX/commit/685316800d53281d408cf034d3f92087dd9a4edb))

Chore resource.properties :
-Adds dot at the end of the sentences
-Replaces mini and maxi terms

([a2e4bf0d3f0c019](https://github.com/Lob2018/SudokuFX/commit/a2e4bf0d3f0c01926ab7b7e9bbf7f55bf9e29845))

Doc DefaultView : confirmationAlertStyle method

([c8a2255c8fb337d](https://github.com/Lob2018/SudokuFX/commit/c8a2255c8fb337d5adeb6e16af59dc8d0693cc09))

Feat DefaultView: Enhance UI with tooltips and alert management
- Added tooltips for buttons
- Moved CONFIRMATION_ALERT to DefaultView (removed from ItemListCell)
- Updated tooltips to use the Luciole font family

([c59ab9ad10b23d0](https://github.com/Lob2018/SudokuFX/commit/c59ab9ad10b23d061dc1cb4d6b3819cf24ccbfab))

Chore SudokuFX_in_action.jpg : Updated with the true tooltip

([1a79ab1d79770e4](https://github.com/Lob2018/SudokuFX/commit/1a79ab1d79770e495353bdbf787c215c32ad7c5a))

Chore DataSourceConfigDefault : code formatting

([9497dde24e9ce3b](https://github.com/Lob2018/SudokuFX/commit/9497dde24e9ce3bdc0d65b4c8721a144976dc5d6))

Chore Run configuration for Package

([1800ba4c2391457](https://github.com/Lob2018/SudokuFX/commit/1800ba4c2391457a449c834f79fb3aa81cab4339))

Chore icons :
-Dedicated stage icon
-Icons update
-Icons crafting guide

([69b314b235e12a6](https://github.com/Lob2018/SudokuFX/commit/69b314b235e12a67df7640bc249a259ea7794387))

Chore SudokuFX_in_action.jpg : Update

([4cc40152d2602ac](https://github.com/Lob2018/SudokuFX/commit/4cc40152d2602ac6caeacc909bd55aa8ebfeeb23))

Feat style.css : menuDropdownClose with 90° rotation

([7a0548f8f4c89a4](https://github.com/Lob2018/SudokuFX/commit/7a0548f8f4c89a4ca25f2b87ffcc38f99ffe2d84))

Feat DefaultView UI : menuMini width respected with managed&#x3D;&quot;false&quot; visible&#x3D;&quot;false&quot; for other menus

([29c465a40b249aa](https://github.com/Lob2018/SudokuFX/commit/29c465a40b249aac4dedc3efc4c4c66f57716b3f))

Feat DefaultView UI : All UI containers are ready

([08fd15961b2afdd](https://github.com/Lob2018/SudokuFX/commit/08fd15961b2afdd43c57a7795b09f3218f8b9668))

Feat DefaultView list :
- Customizes the dialog pane to match the application&#x27;s style
- Customizes the scroll bar&#x27;s increment and decrement buttons

([1bf1b3cb238ec46](https://github.com/Lob2018/SudokuFX/commit/1bf1b3cb238ec46fcbf40dbe48d605cacac9f1ea))

Feat DefaultView player-list : customize renderer and add rounded clip
- Implement custom list cell renderer
- Apply rounded clip to ListView for improved UI

([5943ac5c2224bc1](https://github.com/Lob2018/SudokuFX/commit/5943ac5c2224bc1c92bba1b4a283d3b11a42bb13))

Chore // TODO comment when moving to production

([a5629b1fab8434d](https://github.com/Lob2018/SudokuFX/commit/a5629b1fab8434d0db76e1beda33e34c2558980f))

Refactor DefaultView I18n : Specify player name for edit and delete buttons

([b2afd993ee0fdd7](https://github.com/Lob2018/SudokuFX/commit/b2afd993ee0fdd72cb641a06fcf6aa84b464bd21))

Refactor: Rename SelectListCell to ItemListCell and improve null handling

([4dc6f8be52351b4](https://github.com/Lob2018/SudokuFX/commit/4dc6f8be52351b45be9c1f34fef074a4bff59497))

Feat I18n :
-menu player buttons
-Add support for placeholders ({0}) in .properties files for dynamic text replacement

([f7e615c7c566249](https://github.com/Lob2018/SudokuFX/commit/f7e615c7c566249581601554025dfaf50d0905d9))

Chore : // TODO comment when moving to production

([82709470a717e8d](https://github.com/Lob2018/SudokuFX/commit/82709470a717e8d28e99759d51a656fbf2e70fdf))

Chore CI/CD codeql-action : Update

([fcd69667bf22734](https://github.com/Lob2018/SudokuFX/commit/fcd69667bf227347f096aa7301f5f5f761c9ac14))

Chore pom.xml : Update

([f60f17d12581ede](https://github.com/Lob2018/SudokuFX/commit/f60f17d12581ede1aa4fc25baf68f09d8e76e6ee))

Feat default-view.fxml Default-view : Player submenu

([921dc41d3754493](https://github.com/Lob2018/SudokuFX/commit/921dc41d3754493d96737a2fc68ee0333cc6e330))

Feat default-view.fxml Default-view : Partial Player submenu

([7d3c5a76706bbe7](https://github.com/Lob2018/SudokuFX/commit/7d3c5a76706bbe703983229af84804bcc3d09cc1))

Doc DOXYGEN

([daaab64420c9e70](https://github.com/Lob2018/SudokuFX/commit/daaab64420c9e70ec506a284e17fbd19b2eea23f))

Feat default-view.fxml Default-view :
- Initialize values in Default-view
- Moved from some Text to Label to improve formatting
- Accessibility role description :
    # MENU BUTTONS ACCESSIBILITY STATUS
    menu.accessibility.role.description.selected&#x3D;This button is selected.
    menu.accessibility.role.description.opened&#x3D;This submenu is open.
    menu.accessibility.role.description.closed&#x3D;This submenu is closed.
    menu.accessibility.role.description.submenu.option&#x3D;Submenu option.

([bbfa6cb4c8351d8](https://github.com/Lob2018/SudokuFX/commit/bbfa6cb4c8351d8693e216fc1457230d9298077a))

Feat default-view.fxml :
- Add clear button for solving
- Replace non-interactive elements with regions for layout consistency.

([8ffeee425eb3f6c](https://github.com/Lob2018/SudokuFX/commit/8ffeee425eb3f6cceccd02f8075ba135c36e62bd))

Fix MyRegex : Simplifies the regular expression - java:S5843

([80469f55226a86a](https://github.com/Lob2018/SudokuFX/commit/80469f55226a86a50af54247d1341d4cbf2d6b9b))

Fix JVMApplicationProperties : String literal onRefresh is not duplicated

([50d123eef91ae37](https://github.com/Lob2018/SudokuFX/commit/50d123eef91ae37d8ae079ce7104962af15011e2))

Feat default-view.fxml : Changes from MenuButton to Button for the menu

([fe1851d9d5158f1](https://github.com/Lob2018/SudokuFX/commit/fe1851d9d5158f1683f15026d5734282a543c070))

Chore : Renames IMainStageView to IMainView

([9e1672b4f22596e](https://github.com/Lob2018/SudokuFX/commit/9e1672b4f22596e95876071bca356b2af2e005f4))

Feat default-view.fxml : Id&#x27;s in order to use the elements, and the corresponding i18n text and accessibility

([f62bcb49aa962b9](https://github.com/Lob2018/SudokuFX/commit/f62bcb49aa962b9324ac0db98a0df386e48fc41c))

Feat default-view.fxml style.css :
- Hidden menu
- Replaces hidden class by visible&#x3D;&quot;false&quot;

([9461712b5885cdb](https://github.com/Lob2018/SudokuFX/commit/9461712b5885cdbb044ac514eff25326b149fea9))

Fix default-view.fxml : Duplicate id

([60e409b47acb303](https://github.com/Lob2018/SudokuFX/commit/60e409b47acb30360804cd38c7a937b2d0611c7a))

Chore CI/CD cache upload-artifact download-artifact codeql-action : Update

([4c7b9993ca3860a](https://github.com/Lob2018/SudokuFX/commit/4c7b9993ca3860afc1ad72522f31eb730fe3266f))

Feat style.css : Color error for solve

([99eeb5cfd58a017](https://github.com/Lob2018/SudokuFX/commit/99eeb5cfd58a017b5627dfff058f8fe6f5166372))

Doc DOXYGEN

([93b20460cfc204f](https://github.com/Lob2018/SudokuFX/commit/93b20460cfc204f02551cd6cd7af60c2b86c2a64))

Fix runConfigurations : Clean before all run configurations

([2237cbdb97a204d](https://github.com/Lob2018/SudokuFX/commit/2237cbdb97a204d77f5ff6b3a9e99c6b38d64b41))

Chore UML UC : Update use case terminology

([b01143e2d7eff27](https://github.com/Lob2018/SudokuFX/commit/b01143e2d7eff27312bc99c5853ff4acf611e29f))

Feat GridMaster : Adds possibilities percentage to the resolve Sudoku grid
- Change resoudreLaGrille method to return an array containing coherence status and percentage of possibilities.
- Extract getPourcentageDesPossibilites method to calculate the estimated percentage of possibilities based on the total sum.

([36620297ee35b0c](https://github.com/Lob2018/SudokuFX/commit/36620297ee35b0cab153f913fb167dd8cc908c23))

Feat default-view: Integrates the Solve menu button

([ee7bc28c8c51368](https://github.com/Lob2018/SudokuFX/commit/ee7bc28c8c51368be5a6a8e2e47863bbba3b310f))

Feat default-view: integrate the mini menu
-Renames classes

([c1c6b4f9b460b46](https://github.com/Lob2018/SudokuFX/commit/c1c6b4f9b460b4617c91c5731345ea2c1c268da1))

Chore style.css : Renames classes

([4e90e86228ac741](https://github.com/Lob2018/SudokuFX/commit/4e90e86228ac741f506e8c16ad09699d2aa05a28))

Chore style.css : Renames classes

([91e56b00dac2743](https://github.com/Lob2018/SudokuFX/commit/91e56b00dac27436431f659bc5aeff8455fa0587))

Chore style.css : Update .menuButtonArrowDrop position in style.css using Y translation

([f763fd3114e0996](https://github.com/Lob2018/SudokuFX/commit/f763fd3114e0996cbb7ecfe3a7e839c715ba0b13))

Chore codeStyleConfig.xml : add

([8a7fc22b7af0afc](https://github.com/Lob2018/SudokuFX/commit/8a7fc22b7af0afc688c52950b30c61c61b748625))

Feat default-view: integrate partial menu

([6d8ad00f9df54c5](https://github.com/Lob2018/SudokuFX/commit/6d8ad00f9df54c5a9df2c7e74ca5a8ddf27f948d))

Test SoftwareServiceUTest : software with the createdat field set

([1d7d84acdb98c65](https://github.com/Lob2018/SudokuFX/commit/1d7d84acdb98c658dbadc2960fe774a1c2e8a22c))

Chore DataSourceConfig : Renames from DataSourceConfig to AbstractDataSourceConfig

([301e426101e2a5f](https://github.com/Lob2018/SudokuFX/commit/301e426101e2a5ffbe362acde6020ee3a53dbbc0))

Doc README : Moves Qodana

([94fe16615bfe474](https://github.com/Lob2018/SudokuFX/commit/94fe16615bfe474b7866c4d7f712b068164c700f))

Doc CI/CD coverage_report : Renames from Code coverage to JaCoCo line covered ratio (from the pom.xml)

([02779372be58d42](https://github.com/Lob2018/SudokuFX/commit/02779372be58d42f382289a746c28cb94e60c88d))

Doc README :
-JaCoCo line covered ratio (pom.xml)
-Sonarcloud badges

([bd395f38066a930](https://github.com/Lob2018/SudokuFX/commit/bd395f38066a930203d759118917a08645ec607c))

Feat sonarcloud.properties : Updated

([9871cd6c06cca76](https://github.com/Lob2018/SudokuFX/commit/9871cd6c06cca764eea95be54e395b8eb73d9b35))

Fix SudoMain : Unused @EnableAsync because Task is preferred

([3587d298266996b](https://github.com/Lob2018/SudokuFX/commit/3587d298266996b9d7ec3f833d3e0ca4b1ed7557))

Fix Software : Software is created with the flyway&#x27;s script, but createdat will be used

([9646ac47ff9e876](https://github.com/Lob2018/SudokuFX/commit/9646ac47ff9e876783c5b24081181eebe2208fd0))

Chore Software : Software is created with the flyway&#x27;s script, and never change, so the field createdat is removed from the application (entity, dto, etc.)

([1e85cf2b8b94b2b](https://github.com/Lob2018/SudokuFX/commit/1e85cf2b8b94b2b7fa1bdd9d8c734c57e73899de))

Chore pom.xml : Update flyway-database-hsqldb

([fcf2a8b2aab5eae](https://github.com/Lob2018/SudokuFX/commit/fcf2a8b2aab5eae42d7b07f34a3bf1cf63cda480))

Fix ApplicationKeystore : iOsFolderFactory is final

([9db94b9ebc4aba2](https://github.com/Lob2018/SudokuFX/commit/9db94b9ebc4aba21b0fadb22f4e53a633e48e3bd))

Fix pom.xml : update JaCoCo check configuration to enhance coverage validation
- Changed &lt;element&gt; from PACKAGE to BUNDLE to apply coverage rules at the project level.
- Added &lt;phase&gt;package to ensure rules are applied during the packaging phase.

([39ff2992face246](https://github.com/Lob2018/SudokuFX/commit/39ff2992face246bf70d375b9e3e89b5fa7de70a))

Doc CI/CD scripts : Indentation for ████ Please do not close this window ████ after the logo

([77c42def3ec4eca](https://github.com/Lob2018/SudokuFX/commit/77c42def3ec4eca17e3a079a743ec6fa47817a56))

Doc CI/CD scripts :
-First specifies ████ Please do not close this window ████ after the logo
-Specifies the Java Adoptium Temurin JRE link

([e9857db96ca164b](https://github.com/Lob2018/SudokuFX/commit/e9857db96ca164b877d606ad796c29cf8aa7e063))

Doc CI/CD scripts : First specifies ████ Please do not close this window ████

([7332ad0fe89b21c](https://github.com/Lob2018/SudokuFX/commit/7332ad0fe89b21cc829b8257deb4df313278ff52))

Doc CI/CD scripts : First specifies ████ Please do not close this window ████

([2adec2db9b2b03a](https://github.com/Lob2018/SudokuFX/commit/2adec2db9b2b03ad2b40cea9325c46d414f0522c))

Doc DOXYGEN

([4870ab0c3f900d6](https://github.com/Lob2018/SudokuFX/commit/4870ab0c3f900d6807c3d388267c1fee6406577a))

Fix CI/CD release :
-Restrict sensitive data to step scope for better security
-Delete artifacts

([7978bda47240032](https://github.com/Lob2018/SudokuFX/commit/7978bda4724003285166523eb4af0890382e06ad))

Fix CI/CD jacoco-reporter : Recommended to use GitHub auto generated token ${{ secrets.GITHUB_TOKEN }}.

([378dfd9b9db2d2f](https://github.com/Lob2018/SudokuFX/commit/378dfd9b9db2d2fe05601f7cd7d7a3a8d5bed2ea))

Feat CI/CD : prevent accidental exposure of sensitive values using ::add-mask::

- Added the ::add-mask:: command to mask sensitive variables in the logs of GitHub Actions workflows.
- Enhanced log security to avoid accidental leaks.

([6ba25352b7af95f](https://github.com/Lob2018/SudokuFX/commit/6ba25352b7af95f537639838dbb38107925559f3))

Doc CI/CD release : Moves GPG signatures over file fingerprints

([c0c5e60fcff3966](https://github.com/Lob2018/SudokuFX/commit/c0c5e60fcff396605d4b0d03ec843ada8a3c65ef))

Doc DOXYGEN

([5249bfc9dbc18b2](https://github.com/Lob2018/SudokuFX/commit/5249bfc9dbc18b2f30b14c2d2da6028bacebdb8a))

Doc scripts : Specifies to the user not to close script windows

([125b6f1bb185e1a](https://github.com/Lob2018/SudokuFX/commit/125b6f1bb185e1a34f2a1bf52ff46a486842115c))

Doc README, CI/CD : update README structure and release details
- Reorganize the order of sections in the README.
- Add detailed text for release information.
- Include the Adoptium Temurin JRE download link for clarity in both README and CI/CD.

([876c8dfd12ea5cd](https://github.com/Lob2018/SudokuFX/commit/876c8dfd12ea5cd3a996e2211d82d40adc627e17))

Doc README

([580c928a3df3fb4](https://github.com/Lob2018/SudokuFX/commit/580c928a3df3fb41f5e4442c53827312f4ce950e))

Doc README : Specifies Verifying downloaded assets

([12a06875a9ad377](https://github.com/Lob2018/SudokuFX/commit/12a06875a9ad377a43a7a8036b648e37c52dbdd3))

Fix CI/CD : Export public key after sign and verify files

([28d9191976e5de6](https://github.com/Lob2018/SudokuFX/commit/28d9191976e5de682b965e6d813de64c5bd903ec))

Feat CI/CD with GPG

([ef630bf0b5ee170](https://github.com/Lob2018/SudokuFX/commit/ef630bf0b5ee1700eb86531d6c9597382f861632))

Feat CI/CD with GPG

([cd6dd9142e08d3d](https://github.com/Lob2018/SudokuFX/commit/cd6dd9142e08d3dfcdccf50e21f567037c3eea22))

Feat CI/CD with GPG

([b235267c50a7e63](https://github.com/Lob2018/SudokuFX/commit/b235267c50a7e63e57142214971db44f389e3a0c))

Feat CI/CD with GPG

([49a74a73d681bbe](https://github.com/Lob2018/SudokuFX/commit/49a74a73d681bbe477a50d9ba461a559ccd7ad4d))

Feat CI/CD with GPG

([4fad2aa4565d045](https://github.com/Lob2018/SudokuFX/commit/4fad2aa4565d045e891ecf6c9aae04ad1e822b3f))

Feat CI/CD with GPG

([73140a504e975c2](https://github.com/Lob2018/SudokuFX/commit/73140a504e975c21d0aaafc746f0428476965d8c))

Feat CI/CD with GPG

([7e2925887cb0ce3](https://github.com/Lob2018/SudokuFX/commit/7e2925887cb0ce3e52f912911db272d37cd0af81))

Feat CI/CD with GPG

([dc3c8535117930e](https://github.com/Lob2018/SudokuFX/commit/dc3c8535117930eddc91c7de17c68ada2e7fbf9b))

Feat CI/CD with GPG

([5a02e54398355f6](https://github.com/Lob2018/SudokuFX/commit/5a02e54398355f61e4984c7b9fc289b5700302f0))

Fix CI/CD .sh : Exit in case cd fails

([6149290e0db0f11](https://github.com/Lob2018/SudokuFX/commit/6149290e0db0f11ad2e943ce5473c65d82676d9e))

Feat dependabot org.openjfx : # Blocks versions higher than JavaFX 23

([db44527b87e16bd](https://github.com/Lob2018/SudokuFX/commit/db44527b87e16bd221207ef3f29798f619a2c2de))

Doc DOXYGEN and README : Specifies actions for benchmark

([a0762482df809c0](https://github.com/Lob2018/SudokuFX/commit/a0762482df809c09089824c48fc3fd911029dc72))

Chore CI/CD codeql-action : Updated

([080b2b524457e5c](https://github.com/Lob2018/SudokuFX/commit/080b2b524457e5c7b5413197004f5f9be526ef44))

Doc README : Specifies Temporary performance evaluation with Java Microbenchmark Harness (JMH)

([ddc625484bb1a85](https://github.com/Lob2018/SudokuFX/commit/ddc625484bb1a854d482722c639f40581f9e6810))

Doc CI/CD release : from (Launch with ... file) to (launch with ... file)

([8cefee66fd5dbd6](https://github.com/Lob2018/SudokuFX/commit/8cefee66fd5dbd6ddb3e7694647377cf2efe2826))

Doc DOXYGEN

([365ea70ce08e56b](https://github.com/Lob2018/SudokuFX/commit/365ea70ce08e56bbbcdcc9bb901ba68087d0f152))

Doc README : Adds jmh to the Build with section

([d5b70c590ea2b13](https://github.com/Lob2018/SudokuFX/commit/d5b70c590ea2b132a2f12622135ee077b2a980f9))

Doc DOXYGEN

([75cd64bf33ca87a](https://github.com/Lob2018/SudokuFX/commit/75cd64bf33ca87a773539116bd644e5b128fbc0e))

refactor(profile): Clean up JMH profile
- Remove redundant &#x27;activeByDefault&#x27; property
- Eliminate unused dependencies

([b096445b6c3b122](https://github.com/Lob2018/SudokuFX/commit/b096445b6c3b122baa58dba838f5e17f3f872a8a))

Chore benchmark : Increase forks from 1 to 2 for improved reliability

([ff530f4af1e5dcd](https://github.com/Lob2018/SudokuFX/commit/ff530f4af1e5dcdf891f017b23cf0f0a4a56eecb))

Refactor GridMaster : Optimizes cacherLesCases

([3a308ca7a55b8e5](https://github.com/Lob2018/SudokuFX/commit/3a308ca7a55b8e57a54afaad0eeed9f109a00c3d))

Feat JMH: Introduces Java Microbenchmark Harness (JMH), excluded from the final package and utilized solely for benchmarking purposes when required.

([8d08a68262b80ad](https://github.com/Lob2018/SudokuFX/commit/8d08a68262b80ad5de50fd3aa90def39285d2880))

Chore GridMaster : Removes unused import

([6e8cd176644f9c5](https://github.com/Lob2018/SudokuFX/commit/6e8cd176644f9c597ca44e59e8b913586678f5a5))

Doc DOXYGEN

([8334ebf5ac98107](https://github.com/Lob2018/SudokuFX/commit/8334ebf5ac9810742f11ce7a41d2449e78b238c1))

Refactor GridMaster : Optimizes cacherLesCases method by replacing the Set with an indexed array shuffle approach, eliminating hash collisions and improving performance.

([a4e860fa27ae94d](https://github.com/Lob2018/SudokuFX/commit/a4e860fa27ae94dee2e92baed67ac1b439cbb2b9))

Refactor SplashScreenView CrashScreenView : Use Year API for current year retrieval.

([c5665562089485d](https://github.com/Lob2018/SudokuFX/commit/c5665562089485d376eea27f65067f80de8c294e))

Refactor SudoMain : Spring context initialization to use a background task, ensuring JavaFX thread remains unblocked.

([43a5394d1855ed3](https://github.com/Lob2018/SudokuFX/commit/43a5394d1855ed32da33fa32df375b0fe16a7f66))

Doc DOXYGEN

([4e9029119cf3450](https://github.com/Lob2018/SudokuFX/commit/4e9029119cf345069e82d5058f5b95c6e3198055))

Refactor ObjectMapper: Use Spring @Configuration to define a singleton instance
- Added a Spring-managed @Configuration class for Jackson&#x27;s ObjectMapper.
- Ensures a single instance managed by Spring&#x27;s dependency injection.
- Improves configurability and centralizes ObjectMapper settings.

([1e0697b77c45f9f](https://github.com/Lob2018/SudokuFX/commit/1e0697b77c45f9f8dda9b8f7e710839212ca49ad))

Refactor I18n : enum for improved internationalization handling

- Changed &#x60;bundle&#x60; from static to instance-based to allow for more flexible locale management.
- Added support for dynamic locale switching between French and English.
- Implemented resource bundles for both French and English languages.
- Enhanced &#x60;getValue&#x60; method to handle null, missing, and invalid keys with proper error messages.
- Introduced methods to retrieve current language and host environment language code.
- Added functionality to set locale based on the host environment&#x27;s language.

([67c04a51eb12e9f](https://github.com/Lob2018/SudokuFX/commit/67c04a51eb12e9f6641eaecd5da3409a43a04dda))

Fix DefaultView : Removes duplicated lines from FullMenuView

([75a0e3e15365078](https://github.com/Lob2018/SudokuFX/commit/75a0e3e153650780e817ec9ca76b7a25c01526fd))

Chore flyway-database-hsqldb : Updated

([9c42dc9aa626dc9](https://github.com/Lob2018/SudokuFX/commit/9c42dc9aa626dc933aa17192d1c40809da5357ee))

Doc DOXYGEN

([0458f3617438545](https://github.com/Lob2018/SudokuFX/commit/0458f36174385455384febc701867466642c0940))

Refactor enums : MyEnums to specifics enums

([388aa2aecf60926](https://github.com/Lob2018/SudokuFX/commit/388aa2aecf6092673b1700eb742fca110583aca8))

Refactor MyRegex : Migrate from legacy singleton to enum-based singleton

([3d5e3ac29cc1adf](https://github.com/Lob2018/SudokuFX/commit/3d5e3ac29cc1adf245aaf41deb9f98b245c0c994))

Refactor MyDateTime : Migrate from legacy singleton to Spring Component

([918b04886cf627a](https://github.com/Lob2018/SudokuFX/commit/918b04886cf627a7aa24463ba513f29a5b1812be))

Refactor : Reorganization of packages for enums

([e00fb1667ab5bd2](https://github.com/Lob2018/SudokuFX/commit/e00fb1667ab5bd2c76d0a462319d43491822ea1b))

Refactor I18n: Migrate from legacy singleton to enum-based singleton
- Allows method chaining by returning the I18n instance in setLocaleBundle.

([45be12cd012ebf0](https://github.com/Lob2018/SudokuFX/commit/45be12cd012ebf0f14f95a58ff7c57c5d54a08f6))

Refactor I18n: Migrate from legacy singleton to enum-based singleton
- Allows method chaining by returning the I18n instance in setLocaleBundle.

([bb65c4fab06e5f6](https://github.com/Lob2018/SudokuFX/commit/bb65c4fab06e5f616c2a750741fd30113d34bcb0))

Refactor OsFolderInitializer : from legacy singleton to enum-based singleton

([21d814c5c483134](https://github.com/Lob2018/SudokuFX/commit/21d814c5c483134af5af797d4c4b83d86580065b))

Refactor SecureRandomGenerator : from legacy singleton to enum-based singleton

- Removes unnecessary static final field, as INSTANCE is already defined in  enum-based singletons.

([ac584bfa0d5bac1](https://github.com/Lob2018/SudokuFX/commit/ac584bfa0d5bac10f80bddb582c712c8a4de51b2))

Refactor JVMApplicationProperties : from legacy singleton to enum-based singleton

([0412ff4b464f016](https://github.com/Lob2018/SudokuFX/commit/0412ff4b464f016890599eb35ee1145b7cc62181))

Doc ExceptionTools

([4744a50c11a0292](https://github.com/Lob2018/SudokuFX/commit/4744a50c11a0292622a4273ea78b9ca5066be14d))

refactor ExceptionTools : from legacy singleton to enum-based singleton

- Converted ExceptionTools from a legacy singleton class to an enum-based singleton implementation.
- Added a generic method &#x60;getException&#x60; to search for any specific exception type in the exception chain.
- Retained the existing method &#x60;getSQLInvalidAuthorizationSpecException&#x60; for convenience.

([191c2d9108f5ccd](https://github.com/Lob2018/SudokuFX/commit/191c2d9108f5ccd5734437bf3c91dd7551dac0b0))

Chore DefaultView : Removes comment

([67781ad750963b1](https://github.com/Lob2018/SudokuFX/commit/67781ad750963b19aedfcf13109013736ea93919))

Feat style.css focus: Add WCAG AA accessibility gradient for blue background colors

([36b23b5df31a418](https://github.com/Lob2018/SudokuFX/commit/36b23b5df31a418cf734d323253d80e72748d0cc))

Chore CI/CD cache : update

([dbabe38d85959f8](https://github.com/Lob2018/SudokuFX/commit/dbabe38d85959f8fcb4cd69ef1a536df4e85e6ff))

Test SecureRandomGenerator : Add IllegalArgumentExceptions

([49154788bb2281d](https://github.com/Lob2018/SudokuFX/commit/49154788bb2281da06a5da894333f9e200ad355c))

Doc DOXYGEN

([fdbd92b299faa2e](https://github.com/Lob2018/SudokuFX/commit/fdbd92b299faa2ef20bb9c2853dbd3ad1d766734))

Refactor SecureRandomGenerator : Refactor SecureRandomGenerator to a static utility class
- Removed dependency injection
- Made class final
- Added private constructor to prevent instantiation

([c809ed0c0a1312e](https://github.com/Lob2018/SudokuFX/commit/c809ed0c0a1312e7ae8f8480a152c79b97f61ab1))

Test : Removes unused @ExcludedFromCoverageReportGenerated

([7eec1b288ba8b1e](https://github.com/Lob2018/SudokuFX/commit/7eec1b288ba8b1edc7f4e8e27cf23b1212f2a694))

Refactor MyDateTime : Refactor MyDateTime to a static utility class
- Removed dependency injection
- Made class final
- Added private constructor to prevent instantiation

([dc386d703ef8e0a](https://github.com/Lob2018/SudokuFX/commit/dc386d703ef8e0a67b64c848d2313791e94fc68b))

Fix FileSystemManagerUTest : JUnit5 test classes and methods have default package visibility

([37d61ce1c078cf4](https://github.com/Lob2018/SudokuFX/commit/37d61ce1c078cf4fdda075ec9c135feabedd3f38))

Doc DOXYGEN

([da667f6785ec541](https://github.com/Lob2018/SudokuFX/commit/da667f6785ec5415bc5fcb4086c3c868ab341ffd))

Refactor: Removes unnecessary test and mock for IOsFolderFactory

([4a145ca38ef8a86](https://github.com/Lob2018/SudokuFX/commit/4a145ca38ef8a86f5e453aa8f4cd404e4caaafef))

Test : Respects test case naming convention

([14697d3c68ff65d](https://github.com/Lob2018/SudokuFX/commit/14697d3c68ff65d00a7c2a05e938c5d11f868e8f))

Doc Doxygen

([a4fa116e56a2f34](https://github.com/Lob2018/SudokuFX/commit/a4fa116e56a2f341dcf5da38f22d216ceb716fe2))

Doc Doxygen

([a3d7b24f0977c87](https://github.com/Lob2018/SudokuFX/commit/a3d7b24f0977c876ba726fa20407af923511990b))

refactor(DataSourceConfig): Remove @Configuration from abstract base class and adjust visibility
-DataSourceConfig is now strictly a base class and no longer annotated with @Configuration, preventing unintended instantiation by Spring.
-Beans defined in DataSourceConfig remain inherited by subclasses (DataSourceConfigDefault, DataSourceConfigCds, DataSourceConfigTest).
-Adjusted visibility: DataSourceConfig, DataSourceConfigDefault, and DataSourceConfigCds are now package-private, while DataSourceConfigTest remains public.

([389b04f101cdc06](https://github.com/Lob2018/SudokuFX/commit/389b04f101cdc065de9e97b97faeb06cce28d4ed))

Feat IKeystore : package-private (removes keystore package)

([5a0d93be42eab86](https://github.com/Lob2018/SudokuFX/commit/5a0d93be42eab86c66cf019adf591f53b7dde764))

Doc DOXYGEN

([d75b94a1082628d](https://github.com/Lob2018/SudokuFX/commit/d75b94a1082628df9b8d9a341c7e89f8203d5569))

Feat IEncryptionService GenerateSecret : package-private

([9d4a96a12e116e1](https://github.com/Lob2018/SudokuFX/commit/9d4a96a12e116e1016ab00a8e12f26036f72afd5))

Feat IGridMaster : Added sealed interfaces to enhance clarity and security of the class hierarchy, and GridMaster package-private

([6fe7a56b54b7b99](https://github.com/Lob2018/SudokuFX/commit/6fe7a56b54b7b99205a6318ba462da625a2e78ab))

Feat IFileSystem : Added sealed interfaces to enhance clarity and security of the class hierarchy

([08b62406e5c3a7d](https://github.com/Lob2018/SudokuFX/commit/08b62406e5c3a7d33bf2f00e9fba64439caf4397))

Refactor FolderFactory*3 : Make MacosFolderFactory LinuxFolderFactory WindowsFolderFactory package-private to restrict visibility

([9e7ba157adfc4ba](https://github.com/Lob2018/SudokuFX/commit/9e7ba157adfc4babfc3dec00d0984d3d8ea8ed4b))

Refactor: Make OsFolderInitializer class final and package-private to prevent extension and restrict visibility

([194be9a7d3a1466](https://github.com/Lob2018/SudokuFX/commit/194be9a7d3a146694bfba89c978aeb5b6f3a460a))

Feat IOsFolderFactory : Added sealed interfaces (and IMockIOsFolderFactory for tests) to enhance clarity and security of the class hierarchy

([c62baec8d2d8d59](https://github.com/Lob2018/SudokuFX/commit/c62baec8d2d8d594f7588531388f099ec5f46701))

Doc DOXYGEN

([3438dd08f815dee](https://github.com/Lob2018/SudokuFX/commit/3438dd08f815deec833f89f21b776a61fb91bd07))

Doc DOXYGEN

([6807ab63757e217](https://github.com/Lob2018/SudokuFX/commit/6807ab63757e2176b9ebc410e96b0f63248b835d))

Feat IEncryptionService IKeystore  : Added sealed interfaces to enhance clarity and security of the class hierarchy

([2b5b25435efbd16](https://github.com/Lob2018/SudokuFX/commit/2b5b25435efbd16d4adf1d663bbd3e08a7a21763))

Chore CI/CD download-artifact : Update

([4529051be2cc8db](https://github.com/Lob2018/SudokuFX/commit/4529051be2cc8db06d02ad0430e92b4fc28b284b))

Chore VersionService MyEnums :
- Renames GITHUB_API_URL_REPO_TAGS to GITHUB_API_REPOSITORY_TAGS_URL
- Test that GITHUB_REPOSITORY_RELEASES_URL and GITHUB_API_REPOSITORY_TAGS_URL aren&#x27;t null

([6557c4104400864](https://github.com/Lob2018/SudokuFX/commit/6557c4104400864b5b324f3e1153311677ed6c50))

Chore VersionService MyEnums : Moved version service URLs to MyEnums

([79ee347ed50f6fd](https://github.com/Lob2018/SudokuFX/commit/79ee347ed50f6fddd4521e9431f4d0132320c093))

Doc LICENSE.txt : Add licensing information for EPL-1.0, and LGPL-2.1

([27e352114176521](https://github.com/Lob2018/SudokuFX/commit/27e352114176521bbda423e05648278b778f99ac))

Chore CI/CD release-action : Updated

([1f882430c2976eb](https://github.com/Lob2018/SudokuFX/commit/1f882430c2976ebadf1ea5e8d3ebf52467b9aee3))

Chore CI/CD codeql-action : Updated

([d0220cae0ce21ac](https://github.com/Lob2018/SudokuFX/commit/d0220cae0ce21ac4a6c85cae5163680615657ae1))

Chore CI/CD scorecard-action : Updated

([bd6e3e1f3e5be85](https://github.com/Lob2018/SudokuFX/commit/bd6e3e1f3e5be8589377dd186849fc2f0501009c))

Chore(pom.xml): update to enforce minimum Maven version 3.9.9
- Added maven-enforcer-plugin to enforce Maven version 3.9.9 for the project build.
- Updated dependencies and plugin versions for compatibility with Maven 3.9.9.
- Applied version updates to relevant plugins and properties to use the latest versions.
- Modified the update configuration to refresh project dependencies and plugins.

([9da0dd3fc2e0ae5](https://github.com/Lob2018/SudokuFX/commit/9da0dd3fc2e0ae58bc7178a2a95b252934c141e5))

Chore CI/CD release.yml : Update upload-artifact

([496002f0b4afe5f](https://github.com/Lob2018/SudokuFX/commit/496002f0b4afe5ff8c6fc1d2b2617c475ae36b39))

Doc release.yml : indents release links

([2b26cd13f52339b](https://github.com/Lob2018/SudokuFX/commit/2b26cd13f52339b9fbeb65eb58edf99d61ec307f))

Test GridMasterUTest : Tests the creation of two grids with a 600 ms pause in between for all levels.

([9d63bf9df7e540e](https://github.com/Lob2018/SudokuFX/commit/9d63bf9df7e540e0103a8e0b296e5bc340836bc5))

Test SudoMain : Excluded from coverage

([e60903ad24b72fd](https://github.com/Lob2018/SudokuFX/commit/e60903ad24b72fd6341d19a8253d9a358faed6fc))

Chore pom.xml : Update JavaFX version to 23 (needs at least JDK 21)

([03bc786ab4ac7b8](https://github.com/Lob2018/SudokuFX/commit/03bc786ab4ac7b8123a2c17a5436c75855bead11))

Chore CI/CD release.yml cache : Update

([4df56043f2d2030](https://github.com/Lob2018/SudokuFX/commit/4df56043f2d2030d0f6ea5d64ecdee68977b6569))

Fix flyway-database-hsqldb : artifactId

([d6993e8acc9d6e7](https://github.com/Lob2018/SudokuFX/commit/d6993e8acc9d6e7a703165413cce97fef29ed86b))

Chore flyway-database-hsqldb : Update

([095a81c6486dbdf](https://github.com/Lob2018/SudokuFX/commit/095a81c6486dbdff01df3cd1aa258764dd5cda10))

Chore pom.xml : Rename arch to aarch64

([9dac4a2b1442577](https://github.com/Lob2018/SudokuFX/commit/9dac4a2b1442577cd04ba1a537114863b6d5b51c))

Chore pom.xml exec-maven-plugin : Rename the identifier of this execution for labelling the goals during the build

([7cb16fc770d3c0c](https://github.com/Lob2018/SudokuFX/commit/7cb16fc770d3c0c55ce2a7f5f570da4cabd0fc77))

Doc release : Complete the release message

([3a3ade161079468](https://github.com/Lob2018/SudokuFX/commit/3a3ade161079468a9d7c0dd3ec9c7cc3acba4d82))

Doc DOXYGEN

([d1b7662a4d97206](https://github.com/Lob2018/SudokuFX/commit/d1b7662a4d9720676bd2f1d66213cc0fc1af2845))

Doc release : Complete the release message

([9d48eb84b113582](https://github.com/Lob2018/SudokuFX/commit/9d48eb84b11358292ac512147dd8df0f1387cd74))

Doc release : Complete the release message

([ebfbf1d2fc5eb2f](https://github.com/Lob2018/SudokuFX/commit/ebfbf1d2fc5eb2f2ad39c0923ecbd52180bfc780))

Doc release : Complete the release message

([b72a89843bf9f48](https://github.com/Lob2018/SudokuFX/commit/b72a89843bf9f48545fe814eeb0674f85c2ed9e9))

Doc release : Update the release message

([7e60f544e7cd53e](https://github.com/Lob2018/SudokuFX/commit/7e60f544e7cd53e14030d5af4b08fcb9092d2b31))

Doc README : Specify the description

([442133b7d9941ab](https://github.com/Lob2018/SudokuFX/commit/442133b7d9941abe9805f39fcf60ac0afd6b7061))

Chore Maven wrapper : Update to latest versions

([17af8596ed8aa0b](https://github.com/Lob2018/SudokuFX/commit/17af8596ed8aa0b00b308f1d89fecbb46b988a9b))

Chore Maven configurations : Used Maven wrapper

([ea9da1560ab5a6a](https://github.com/Lob2018/SudokuFX/commit/ea9da1560ab5a6a4cd7145fac9869677d508420a))

Merge branch &#x27;main&#x27; into develop

([a933c87be32b877](https://github.com/Lob2018/SudokuFX/commit/a933c87be32b87746fcda7593e4c33467defcadb))

Fix Maven configuration : run with details

([c7528ade05e9a01](https://github.com/Lob2018/SudokuFX/commit/c7528ade05e9a014974da252431c7fb8a5e01dcb))

Chore pom.xml : Specifies default Mac profile with &lt;arch&gt;arch64&lt;/arch&gt;

([f8cf0be34be95c5](https://github.com/Lob2018/SudokuFX/commit/f8cf0be34be95c56a764692934b27a65b0542b56))

Chore CI/CD harden-runner : Update

([996a743ddf684a9](https://github.com/Lob2018/SudokuFX/commit/996a743ddf684a92dd8993243a43ac863c5c320b))

Feat VersionService MyDateTime :
- Bind VersionService status message with a Label
- Create a dedicated MyDateTime class for time formatting
- Inject MyDateTime into VersionService to timestamp status messages

([d1feab89a5a4f8a](https://github.com/Lob2018/SudokuFX/commit/d1feab89a5a4f8a5f8f4820b7bdce5c21153f916))

Fix VersionService : Removed useless setOnFailed task event handler

([e1f9409bc39cccd](https://github.com/Lob2018/SudokuFX/commit/e1f9409bc39cccdb626ae451a8c159ea5f3b8377))

Doc DOXYGEN

([7e11b15ee8c1737](https://github.com/Lob2018/SudokuFX/commit/7e11b15ee8c1737accaac6eb38b910c182163ff2))

Refactor VersionService : Replaced CompletableFuture with JavaFX Task&lt;Boolean&gt; to ensure UI thread safety.
- Updated HTTP request handling to use synchronous HttpClient.send().
- Modified tests accordingly to execute and retrieve Task results properly.
- Change test extension from @ExtendWith(MockitoExtension.class) to @ExtendWith(ApplicationExtension.class) to initialize JavaFX Toolkit for proper UI interaction in tests.

([a87a877668c6970](https://github.com/Lob2018/SudokuFX/commit/a87a877668c697021b2fffea4d2f661e02345515))

Fix VersionServiceTest : Remove useless field

([abf9158874bc98f](https://github.com/Lob2018/SudokuFX/commit/abf9158874bc98fa392146bb43dc9d81a642f715))

Doc DOXYGEN

([d961610346e3200](https://github.com/Lob2018/SudokuFX/commit/d961610346e3200b246f67dc25f3cd0177f8a13f))

Chore flyway-database-hsqldb : Updated

([bb8e00cc530fdd8](https://github.com/Lob2018/SudokuFX/commit/bb8e00cc530fdd83aa7c92bb520133ce040cf7f9))

feat system-stubs-jupiter :
- Pass JVM application properties to tests to avoid side effects
- Remove JVM properties redefinition in tests to avoid side effects
- Remove system-stubs-jupiter plugin

([b12b638cae20019](https://github.com/Lob2018/SudokuFX/commit/b12b638cae20019cf7d9f03162cbc7dec158765c))

Feat: Integrate JaCoCo code coverage and configure Surefire plugin

This commit integrates JaCoCo for code coverage reporting and modifies the Surefire plugin configuration.

Changes:
- Added JaCoCo Maven plugin for code coverage reporting, excluding specific classes and packages.
- Configured JaCoCo to prepare the agent and generate reports during the test phase.
- Modified the Surefire plugin to include JaCoCo&#x27;s agent in the argLine for code coverage during tests.

([bba3c064637312f](https://github.com/Lob2018/SudokuFX/commit/bba3c064637312fea090331fce8e7e3208e937b7))

refactor UML activity invalid.authorization : update UML diagram to clarify process termination with flow final node

([6b4622cf241861b](https://github.com/Lob2018/SudokuFX/commit/6b4622cf241861b02c376130b7e226a472bea74a))

Doc DOXYGEN

([652808be2872873](https://github.com/Lob2018/SudokuFX/commit/652808be2872873c497dbb73fbf5ccfeb0b00200))

Refactor VersionService:
- Add a connection timeout
- Remove unnecessary try catch
- Replace if-else with a switch expression for better readability
- Add explicit null case to prevent NullPointerException

([02305180cfde8d2](https://github.com/Lob2018/SudokuFX/commit/02305180cfde8d274b0d4c1db5e7e82a69797674))

Doc DOXYGEN

([176a24ec48be308](https://github.com/Lob2018/SudokuFX/commit/176a24ec48be308f2e84711245f6576476629cbd))

Fix FullMenuViewModel : Initialize the SimpleBooleanProperty version value

([940d2e45ce50272](https://github.com/Lob2018/SudokuFX/commit/940d2e45ce50272e7341324931614165dff0a73d))

Refactor VersionService to follow MVVM architecture
- Moved UI-related properties to VersionViewModel
- Decoupled VersionService from JavaFX (removed JavaFX Service&lt;Boolean&gt; inheritance)
- VersionService now returns CompletableFuture&lt;Boolean&gt; instead of modifying UI state
- VersionViewModel manages the versionUpToDate property and updates the UI
- Improved separation of concerns for better maintainability and testability

([b2e37855d307c5e](https://github.com/Lob2018/SudokuFX/commit/b2e37855d307c5e87b4d3ae30e18f3fc0a2b2303))

Fix GameLevel : Removed unused imports

([d5175efe3274cfc](https://github.com/Lob2018/SudokuFX/commit/d5175efe3274cfc25b42acf53a2d7c97233d7982))

Doc DOXYGEN

([298dec6ce90f28c](https://github.com/Lob2018/SudokuFX/commit/298dec6ce90f28c0767759f751787376842b652c))

Fix ToasterVBox : Uses the visibleText to calculate the duration of the toast, and not the detailed text.

([f276ba4f54637ce](https://github.com/Lob2018/SudokuFX/commit/f276ba4f54637ce54f0cad2a110aa5771340c754))

Feat VersionService : Used a read-only property that indicates whether the software version is up to date.

([f33cf01d8678173](https://github.com/Lob2018/SudokuFX/commit/f33cf01d8678173f0005d224194198bd534da7b3))

Fix VersionServiceTest : Removes the declaration of thrown exception &#x27;java.lang.Exception&#x27;.

([45f7995c465d782](https://github.com/Lob2018/SudokuFX/commit/45f7995c465d782b9f97a89b66703dc53735492a))

Fix VersionServiceTest : Default package visibility

([40db90c74f43f94](https://github.com/Lob2018/SudokuFX/commit/40db90c74f43f94e06fe21f93c3d72b4032c6d79))

Doc DOXYGEN

([d4a23d384f95907](https://github.com/Lob2018/SudokuFX/commit/d4a23d384f95907ff51996f73ccd45a7f0b49a7f))

Feat VersionService: Becomes an asynchronous service

([78a840941c9237f](https://github.com/Lob2018/SudokuFX/commit/78a840941c9237fb489d8aeaaa1a8d241687c22c))

Feat default-view menu-button: add a default view and create a menu-button

🚧 This commit is partial, it remains to be implemented

([72d78c7b03f3ea4](https://github.com/Lob2018/SudokuFX/commit/72d78c7b03f3ea4973cc41ee21036cc29eae1fa3))

Feat dto : add GitHub version DTOs and integrate into VersionService

([9d7654e0146ade2](https://github.com/Lob2018/SudokuFX/commit/9d7654e0146ade2bac372851e421557bcbdd4852))

Doc removes DOXYGEN

([59d3fcd870b4d0a](https://github.com/Lob2018/SudokuFX/commit/59d3fcd870b4d0a06a670e9a6b573f0a9c131e78))

Feat: create exception package for centralized error handling

([9af63b442463634](https://github.com/Lob2018/SudokuFX/commit/9af63b442463634d51706ac4c63d4b6999f4cab1))

Feat UML activity diagram keystore.invalid.authorization :
Improve clarity and structure of UML diagram.
- Shortened long annotation texts for better readability.
- Reformulated decision labels for more explicit wording.

([8e9f4bb712f2fb2](https://github.com/Lob2018/SudokuFX/commit/8e9f4bb712f2fb2147293d6239df49cd139c8660))

Chore UML activity schema for invalid authorization : Update the folder name and the messages

([0b6fe568d528912](https://github.com/Lob2018/SudokuFX/commit/0b6fe568d528912ef444e5a0bbc789100421b4b0))

Fix VersionServiceTest JVMApplicationPropertiesUTest : For all tests the application version property is set by default to v1.1.1

([b5b190e4438cdf8](https://github.com/Lob2018/SudokuFX/commit/b5b190e4438cdf8cc86624b108c3d59063350e1e))

Test VersionServiceTest: Add tests for all version numbers and invalid versions

([cfe3b5091e2677f](https://github.com/Lob2018/SudokuFX/commit/cfe3b5091e2677f31e31183e0abf645b9a5b186a))

Chore CI/CD codeql-action : Update

([f1fca4fbaf14baf](https://github.com/Lob2018/SudokuFX/commit/f1fca4fbaf14baf339432a2f88898e25a0a9ffc9))

Refactor switch statement : Use modern switch expression for better readability, safety, and maintainability.

([f5a8c8f4e37a94b](https://github.com/Lob2018/SudokuFX/commit/f5a8c8f4e37a94b2fa7e729750783d4b76f5e692))

Refactor VersionService:
- Use a single ObjectMapper instance
- Ensure tagName is not null and has a valid length before processing
- Improve compareVersions logic for MAJOR.MINOR.PATCH format
- Enhance logging and error handling

([09a17987835cce6](https://github.com/Lob2018/SudokuFX/commit/09a17987835cce671d2a4337dd0017a2b714b33d))

Fix VersionService: Validate last version in getFormattedVersionNumberOnly using MyRegex.isValidatedByRegex(lastVersion, MyRegex.getVERSION())

([a1b0409230e8b0a](https://github.com/Lob2018/SudokuFX/commit/a1b0409230e8b0a7d4b7abfef7c5b44cdeb319aa))

Fix VersionService:
- Update getFormattedVersionNumberOnly to avoid regex vulnerability to polynomial runtime due to backtracking (DoS prevention). By replacing \d+ with \d++, the regex engine consumes the digits without the possibility of backtracking.

([916b48b7000d49e](https://github.com/Lob2018/SudokuFX/commit/916b48b7000d49ec7a5e888d0662d9605a5ced8f))

Fix VersionService:
- Add catch (InterruptedException e) and re-interrupt the thread.
- Update getFormattedVersionNumberOnly to avoid regex vulnerability to polynomial runtime due to backtracking (DoS prevention).

([e35a25be7ca6fb9](https://github.com/Lob2018/SudokuFX/commit/e35a25be7ca6fb9e63fc25e1f018f7cc9d65a83a))

Doc DOXYGEN

([8e0e03f146e4d63](https://github.com/Lob2018/SudokuFX/commit/8e0e03f146e4d63ef0d2a4c001d392c48bb8064e))

Feat VersionService VersionServiceTest : Service for managing version information from a GitHub repository.

([3dcbd3c0b3d0e2d](https://github.com/Lob2018/SudokuFX/commit/3dcbd3c0b3d0e2d304edbe5f2721baed5930dc20))

Refactor : Sets application.properties for production

([6cebc7f7f6557dd](https://github.com/Lob2018/SudokuFX/commit/6cebc7f7f6557dd6ce1e32c2a1d7e9e016fcc78e))

Refactor: Add a configuration package and rename the classes with @Configuration with the suffix Config

([fe261ca9c8cd7f0](https://github.com/Lob2018/SudokuFX/commit/fe261ca9c8cd7f052ee28e3971ab06232344465f))

CI/CD codeql-action : Update

([bc530c9a4906a26](https://github.com/Lob2018/SudokuFX/commit/bc530c9a4906a26d808dc5609af312ad198c8648))

Doc DOXYGEN

([e59cb3b14bd8592](https://github.com/Lob2018/SudokuFX/commit/e59cb3b14bd859250910d51985f50dabb89ee788))

Refactor GameLevel Menu :
- SQL : Moves ID from BIGINT to TINYINT, for menuid and levelid primary keys
- Model : Moves ID from Long to Byte, for menuid and levelid primary keys

([2a66d9a5afd845e](https://github.com/Lob2018/SudokuFX/commit/2a66d9a5afd845ef1adc8aca647b9299f8444b7c))

Refactor GameLevel Menu : Specifies fields :
GameLevel : renames levelname to level and moves type from VARCHAR to TINYINT
Menu : moves mode type from INTEGER to TINYINT

([935b567e7ac6a71](https://github.com/Lob2018/SudokuFX/commit/935b567e7ac6a712fdc36dcac077d2ec98debf08))

Doc without DOXYGEN

([531c676f4bb0e62](https://github.com/Lob2018/SudokuFX/commit/531c676f4bb0e627a8521e5c0c1288d5b58424ca))

Refactor GridMaster : Specifies possibilities and not difficulty

([b7b01eb9aeafa65](https://github.com/Lob2018/SudokuFX/commit/b7b01eb9aeafa65ed1ebdae52bb24c37132ea10f))

Refactor grid model : Renames grid difficulty to possibilities

([f1da625c12ff07d](https://github.com/Lob2018/SudokuFX/commit/f1da625c12ff07dd6f15c7407287789ecd686398))

Refactor MyLogback : Respects DIP with use of IOsFolderFactory instead of OsFolderFactoryManager

([02edbdac4f2d7d1](https://github.com/Lob2018/SudokuFX/commit/02edbdac4f2d7d147b77177936205e233e6a0c38))

Refactor DataSource : Respects DIP with use of IOsFolderFactory instead of OsFolderFactoryManager

([e0fdeaf8932a870](https://github.com/Lob2018/SudokuFX/commit/e0fdeaf8932a87053e67afd37c7111be2b9b6507))

Doc DOXYGEN

([dc8ec545523d5fe](https://github.com/Lob2018/SudokuFX/commit/dc8ec545523d5fef46ae9164902dccefe9189c67))

Feat (UX/UI) ToasterVBox : Uses ToasterButton with display text and separate full detail text that will be copied to the system clipboard on user click.

([6eb2739f1c72b6d](https://github.com/Lob2018/SudokuFX/commit/6eb2739f1c72b6de7de867266c5c1c09fe959f32))

Fix GridMasterUTest : Switch statement have default clause

([fb3363df8eacbe1](https://github.com/Lob2018/SudokuFX/commit/fb3363df8eacbe185112424386822aa06b7c156d))

Doc DOXYGEN

([24e7d32682ad089](https://github.com/Lob2018/SudokuFX/commit/24e7d32682ad08916075f43b0e94185745a017c5))

Test GridMaster : Tests that loops generating sudoku grids by level can terminate after one second if they fail

([16e24cefc7e5458](https://github.com/Lob2018/SudokuFX/commit/16e24cefc7e5458546ddd30a9945d4d69a6b9de4))

Fix GridMaster : Loops generating sudoku grids by level never checked their duration so that they could end after one second if they failed

([79f3b9d0839547c](https://github.com/Lob2018/SudokuFX/commit/79f3b9d0839547c36213582b158f36d903e5e85a))

Doc DOXYGEN

([059067a1a097983](https://github.com/Lob2018/SudokuFX/commit/059067a1a097983c892076e6f9d09ac1df4d44e3))

Feat I18n : Sets default language based on the host environment

([92870f67bc928f8](https://github.com/Lob2018/SudokuFX/commit/92870f67bc928f8dbb9381c2e5e208022df234f0))

Refactor DynamicFontSize

([30dff6273e69317](https://github.com/Lob2018/SudokuFX/commit/30dff6273e69317abe32a18b8ef39acd4a5bd7d0))

Chore pom.xml flyway-database-hsqldb : Updated

([3919bc3e71fc591](https://github.com/Lob2018/SudokuFX/commit/3919bc3e71fc591546a04dca376adb23969bd9c4))

Doc : DOXYGEN

([45b38cf1dda4bdc](https://github.com/Lob2018/SudokuFX/commit/45b38cf1dda4bdc65342d18ab199fcb9957e256a))

Fix Main : 1 scene for the application, so the scene is static

([ab403ab6d85b857](https://github.com/Lob2018/SudokuFX/commit/ab403ab6d85b8574ff37ca5a6f918197eab146f8))

Refactor with interfaces : Uses interfaces outside packages

([04773f5b43d8fd9](https://github.com/Lob2018/SudokuFX/commit/04773f5b43d8fd96c623c7dcae32ae0be641b582))

Doc DOXYGEN

([aa689339a5e7cd1](https://github.com/Lob2018/SudokuFX/commit/aa689339a5e7cd1c696b7de04999b5288dcfb012))

Feat Refactor GridMaster :
- New grid asks in less than 500ms, generate a grid with a fix number of hidden cases defined by the level
- 1s maximum to generate the grid to be solved according to the level possibilities

([54d39a2fbb9172c](https://github.com/Lob2018/SudokuFX/commit/54d39a2fbb9172c23f7e9001f54b8bd2ab46dc05))

Chore resource_EN resource_FR : Specifies toastlevel messages

([760855ceb13f04e](https://github.com/Lob2018/SudokuFX/commit/760855ceb13f04e79a5a2207970e04cc1695ffd1))

Chore resource_EN resource_FR : Specifies crashscreen.messages

([bd09e5aeb9bca4a](https://github.com/Lob2018/SudokuFX/commit/bd09e5aeb9bca4ad315aecb2d451a6fce34ca265))

Chore SudoMain : Unused import

([cf65c77c681c59e](https://github.com/Lob2018/SudokuFX/commit/cf65c77c681c59e933afdc80be6f93520706c08b))

Fix SudoMain DynamicFontSize : Resolved circular dependencies between them with fxmlService.setDynamicFontSize() method.

([2da7a300faa7893](https://github.com/Lob2018/SudokuFX/commit/2da7a300faa789393370ea82829b3f9dcfee568e))

Doc DOXYGEN

([6ab1a047fd2632c](https://github.com/Lob2018/SudokuFX/commit/6ab1a047fd2632c7054383944fefcadb5e2f9229))

Fix ISceneProvider : Resolved circular dependencies between classes across packages. ISceneProvider provides standardized access to the main scene, without directly depending on the main application class.

([74f1742149d40d3](https://github.com/Lob2018/SudokuFX/commit/74f1742149d40d3a94cb56c382f154e09df9865d))

Refactor crashscreen-view.fxml : Unused imports

([6f9f6bce14a5050](https://github.com/Lob2018/SudokuFX/commit/6f9f6bce14a5050fb615f1e52be5d043b02600a2))

Doc DOXYGEN

([4b12639bba8044b](https://github.com/Lob2018/SudokuFX/commit/4b12639bba8044bdd8440d4ccc3ae7b7c6455ed9))

Doc DOXYGEN

([780f722315d3abf](https://github.com/Lob2018/SudokuFX/commit/780f722315d3abf1b61da23035d99d0614daecde))

Perf DataSourceTest DataSourceCds : setJdbcUrl with DB_CLOSE_DELAY&#x3D;-1 so the JPA implementation, which is Hibernate, won’t close the database while the application is running.

([90f15b2b25c9083](https://github.com/Lob2018/SudokuFX/commit/90f15b2b25c9083f4c8c9da9b1d720ec8132830f))

Refactor DataSource :
- Creates an abstract DataSource class to centralize common database configuration logic.
- Removed duplicate code across different data source implementations.
- Ensured that Logback initialization and Flyway migration setup are handled uniformly.
- Improved maintainability and readability of the data source configuration.

([4fd72dc69ef75e2](https://github.com/Lob2018/SudokuFX/commit/4fd72dc69ef75e20a747ee9258076ca4f67a4ebb))

Refactor DataSource :
- Creates an abstract DataSource class to centralize common database configuration logic.
- Removed duplicate code across different data source implementations.
- Ensured that Logback initialization and Flyway migration setup are handled uniformly.
- Improved maintainability and readability of the data source configuration.

([d5e57260e8ac94e](https://github.com/Lob2018/SudokuFX/commit/d5e57260e8ac94e228618185f866f6ded3b51c4a))

Fix GridMaster : Constant names should comply with the naming convention

([e2f5a377e247f50](https://github.com/Lob2018/SudokuFX/commit/e2f5a377e247f50c1981373cc262ad4b95fad729))

Doc Doxygen

([2f5501cd737e565](https://github.com/Lob2018/SudokuFX/commit/2f5501cd737e5658591fc15f7c3b9948a3c08cc9))

Fix GridMaster : Method names should comply with the naming convention

([fe46b67da28b4d3](https://github.com/Lob2018/SudokuFX/commit/fe46b67da28b4d336b8b9b69afb20f98291c1196))

Fix ToasterVBox : Handles if this.getScene() is null when the view is changed

([b527b041f6fabdf](https://github.com/Lob2018/SudokuFX/commit/b527b041f6fabdfee0e9c9bdd187b33047d91a0c))

Fix FullMenuView : Sets class properties as private

([45304ea786c22e8](https://github.com/Lob2018/SudokuFX/commit/45304ea786c22e8992d646f332bec30b4438ec54))

Fix SoftwareServiceUTest : Removes the &#x27;public&#x27; modifier.

([3fc754464cb3e23](https://github.com/Lob2018/SudokuFX/commit/3fc754464cb3e23578330391ee4d2f58c6da5ce1))

Fix MyLogbackUTest : Removes the &#x27;public&#x27; modifier.

([f9b9bc2eae9d6ae](https://github.com/Lob2018/SudokuFX/commit/f9b9bc2eae9d6aee98ab0c5baf5f03f047c83f44))

Fix MyLogbackUTest : Replace 3 tests with a single Parameterized one.

([1130e4c29b9778e](https://github.com/Lob2018/SudokuFX/commit/1130e4c29b9778e0d3d247ccb00bb6301fe5d671))

Fix MyLogbackUTest : Removes empty statement.

([10bf945c196e3b6](https://github.com/Lob2018/SudokuFX/commit/10bf945c196e3b6dc3df2896338063f003b245e9))

Fix DataSourceConfigForTests : Moved to configuration files and specifies excluded from coverage report

([408b4a94422df4e](https://github.com/Lob2018/SudokuFX/commit/408b4a94422df4e5151c08a8099d94d7cb6f46d4))

Fix OsFolderFactoryManagerUTest : Refactors the code of the lambda to have only one invocation possibly throwing a runtime exception.

([ec77edb20e62f98](https://github.com/Lob2018/SudokuFX/commit/ec77edb20e62f9872bc47717943a7f236410af1b))

Doc : Doxygen

([eab3b139cf66df1](https://github.com/Lob2018/SudokuFX/commit/eab3b139cf66df112587cb2f6af29606594857ce))

Feat: ApplicationKeystore and GenerateSecret as Spring beans with constructor injection (ensured interaction with other Spring components for better integration).

([9a6aed9eeee8d12](https://github.com/Lob2018/SudokuFX/commit/9a6aed9eeee8d12e743a5cd51566b81fdabfc637))

CI/CD upload-artifact : Update

([e863cfc05aaf93a](https://github.com/Lob2018/SudokuFX/commit/e863cfc05aaf93a63167577157f6bba3cd08c2bf))

Chore GridMaster and IGridMaster : Level values adjustment

([b7d387cfe5b1477](https://github.com/Lob2018/SudokuFX/commit/b7d387cfe5b14777a1deebd1fef7e6d35ed3ec79))

Fix windowsRelease.bat : Gets the year from the Maven Build timestamp

([cf39614d5ee73cf](https://github.com/Lob2018/SudokuFX/commit/cf39614d5ee73cfb5268b4942e84e7957f9548d0))

Doc README

([932f10879a92fe2](https://github.com/Lob2018/SudokuFX/commit/932f10879a92fe2086eb9360500175521d21afed))

Doc README : How to run with IntelliJ IDEA specifies Maven execution configurations files exists

([d33851398af31f0](https://github.com/Lob2018/SudokuFX/commit/d33851398af31f015080b230fe0d20537ded0990))

Feat RunConfigurations : Save Maven execution configurations as project files
-Run
-Run with details
-Doxygen
-Tests
-Update dependencies

([4f1a1427201f2c7](https://github.com/Lob2018/SudokuFX/commit/4f1a1427201f2c747161586e8cef0c79fd7b91b4))

Doc Doxygen

([3a5489b0aa14860](https://github.com/Lob2018/SudokuFX/commit/3a5489b0aa1486094393ceac507b27d09cdd678f))

Fix FullMenuViewModelITest : Specify SudoMain as the configuration class in FullMenuViewModelITest

([33b2deeb3ea3e72](https://github.com/Lob2018/SudokuFX/commit/33b2deeb3ea3e72c95369eab23575087fe471ff1))

Chore SoftwareService : Specifies @Autowired for the injection constructor

([d980c4f0d9a2716](https://github.com/Lob2018/SudokuFX/commit/d980c4f0d9a27169ddb6fd54af0dd272ab264be7))

Fix FullMenuViewModelITest: Removes @Slf4j

([cfec4d4c68aaf81](https://github.com/Lob2018/SudokuFX/commit/cfec4d4c68aaf81a9db220d4aa60e91273a60b8c))

Fix jdbc:hsqldb:mem : Uses a simple name without the path

([5c3eb9baf972877](https://github.com/Lob2018/SudokuFX/commit/5c3eb9baf972877bf7222e3a10123774d15f251c))

Fix Grid GridDto : Updated to match with create.base.sql :
defaultgridvalue @Size(max &#x3D; 81)
gridvalue @Size(max &#x3D; 810)

([a634cf777049191](https://github.com/Lob2018/SudokuFX/commit/a634cf7770491911729d3e34fe0d1238c2b1df7f))

Feat : Put multiple values in the temporary boxes, when the value entry is greater than 1 digit

([35dd498d5d6910c](https://github.com/Lob2018/SudokuFX/commit/35dd498d5d6910c1b65f02dbedea04ec2ef1596e))

Doc Doxygen

([6c954da22ab0fa7](https://github.com/Lob2018/SudokuFX/commit/6c954da22ab0fa737e422f137b3203e9f80c3b76))

feat GridMaster : Add SecureRandomGenerator for secure random number generation

- Creates SecureRandomGenerator class to provide secure random number generation methods.
- Replaces instances of java.util.Random in GridMaster with SecureRandomGenerator.

([3f4bf528c590cd0](https://github.com/Lob2018/SudokuFX/commit/3f4bf528c590cd0df2f4c685082071dbb4723490))

Test GridMaster

([01090d3271704a7](https://github.com/Lob2018/SudokuFX/commit/01090d3271704a7e24e7f8096d894b7ecc363dcb))

Feat Grid : Adds the difficulty field :
-Model Grid Byte difficulty (min 0, max 100)
-Flyway migration (create base)
-GridDto
-MCD + MPD

([2ee8cce10f6f838](https://github.com/Lob2018/SudokuFX/commit/2ee8cce10f6f838bd328591d913d857ca6249ce4))

Feat FullMenuView : onHelloButtonClick :
-generates Sudoku puzzles.
-randomly displays a different toaster message

([6a13b5a79ef32bd](https://github.com/Lob2018/SudokuFX/commit/6a13b5a79ef32bdb08256ac9842cc122011a9ea6))

Feat GridMaster :
-Provides essential functionalities for generating and solving Sudoku puzzles.

([8606dac26a0e9c7](https://github.com/Lob2018/SudokuFX/commit/8606dac26a0e9c7e501eb32cbb1046dae1dca8bb))

Chore flyway-database-hsqldb : Updated

([f6fe6bc96767980](https://github.com/Lob2018/SudokuFX/commit/f6fe6bc96767980a9b984850b3ffbd00457b4592))

Fix CI/CD openssf_score_card.yml : As specifies in the documentation : https://github.com/ossf/scorecard/blob/main/.github/workflows/scorecard-analysis.yml

([a292a3bfe3f5ab9](https://github.com/Lob2018/SudokuFX/commit/a292a3bfe3f5ab9ac924fbd9dcb397b3eda149a1))

Fix CI/CD openssf_score_card.yml : Rename the artifact without spaces

([fdb9f7b54acbfb2](https://github.com/Lob2018/SudokuFX/commit/fdb9f7b54acbfb2f2514c2647fa7f59c41ea9723))

Chore CI/CD : Updates codeql-action

([3f646d70ea74d78](https://github.com/Lob2018/SudokuFX/commit/3f646d70ea74d78a9b3e6c12930a3fa931a04b20))

Chore interfaces : Method documentation is now with method signatures in interfaces

([001a54bd0191ccc](https://github.com/Lob2018/SudokuFX/commit/001a54bd0191ccc9e93f6f6c538c3d36216bd4be))

CI/CD setup-java : Added check-latest parameter to ensure it always uses the most up-to-date version of Java

([e44f8ad5d899a1c](https://github.com/Lob2018/SudokuFX/commit/e44f8ad5d899a1c0081550866cd4ed1419512664))

Feat style.css: Better integration of the focus border

([2321e83ee451e5c](https://github.com/Lob2018/SudokuFX/commit/2321e83ee451e5c463a93b0548141763c469ca33))

Feat CdsDataSourceConfig : default configuration

([192b74f606e681e](https://github.com/Lob2018/SudokuFX/commit/192b74f606e681ee631d674152da4ab03ce226e7))

Feat CdsDataSourceConfig : minimum configuration

([c8a9d804ce6c380](https://github.com/Lob2018/SudokuFX/commit/c8a9d804ce6c380787f7f6af8efad77e5444d2aa))

Chore CI/CD setup-java : Update

([104ef7259159186](https://github.com/Lob2018/SudokuFX/commit/104ef7259159186ea2027156299207a94caf4859))

Feat CDS: Add CDS profile to prevent interactions with remote services during startup.
- Create CdsDataSourceConfig class
- Add application-cds.properties file
- Update script commands to include -Dspring.profiles.active&#x3D;cds to activate the CDS profile during application training.

([e99d679a868b980](https://github.com/Lob2018/SudokuFX/commit/e99d679a868b980d4fd976a56ef218d5b1608e0f))

## v1.0.0 - 2025-01-28


Chore codeql-action : Updated

([1e20cae1419048c](https://github.com/Lob2018/SudokuFX/commit/1e20cae1419048cc5a6d702451eff81872c1dee8))

Feat style.css :
-Moves focused border outside the elements
-Changes the color of the focus

([da7f3e68f02ba40](https://github.com/Lob2018/SudokuFX/commit/da7f3e68f02ba4090f12561874aedc2119a1db7e))

Chore SudokuFX_in_action.jpg : Updated

([ae8fc86d02dd8b4](https://github.com/Lob2018/SudokuFX/commit/ae8fc86d02dd8b482ce4f4199bb6fe6c254507cf))

Chore SudokuFX_in_action.jpg : Updated

([46aabf01bb4c412](https://github.com/Lob2018/SudokuFX/commit/46aabf01bb4c412a996dd576c329349c253ec1ef))

Chore pom.xml : Updates spring-boot-starter-parent

([899ef164cf2ca99](https://github.com/Lob2018/SudokuFX/commit/899ef164cf2ca99c713d7fb09efd34db7acedc79))

Doc Doxygen

([224bb1e9d597aff](https://github.com/Lob2018/SudokuFX/commit/224bb1e9d597aff8df0c3f829150030a237308b6))

Feat MyRegex: Handling Null Values in isValidatedByRegex
-Returns false if text or regex is null.
-Logs an error message.

([4665228d63c4a86](https://github.com/Lob2018/SudokuFX/commit/4665228d63c4a86c084b80f8153dc3e8b0063c8a))

Chore MyEnums : Adds LOGS_FILE_NAME to enum

([c40cf2bc45ab0d8](https://github.com/Lob2018/SudokuFX/commit/c40cf2bc45ab0d837d8fc118e82e10def4e6ffd5))

Chore ApplicationKeystore :
-Replaces the keystore file path by a constant
-Specifies the class and method documentation

([959d81a14a02352](https://github.com/Lob2018/SudokuFX/commit/959d81a14a023525418484f67984cd333bc1f1eb))

Chore DataSourceConfig and DataSourceConfigForTests : Replaces the path to Flyway migration scripts by an enum

([02ecc4e26f002f4](https://github.com/Lob2018/SudokuFX/commit/02ecc4e26f002f4d369cf0b21c1102646c4c93ef))

Chore FxmlService : Replaces word viewmodel by service

([353b6fd8adf08d7](https://github.com/Lob2018/SudokuFX/commit/353b6fd8adf08d7a8ca08efc8d9777987978580d))

Chore CI/CD openssf_score_card.yml : Replaces word viewmodel by service

([26bf769f37ba005](https://github.com/Lob2018/SudokuFX/commit/26bf769f37ba00567d59e7b329ecbb77cfffc1fa))

chore Datasource for tests : Renames DataSourceConfigOverload to DataSourceConfigForTests and changes profile to &quot;test&quot;

This change renames the DataSource configuration for tests to improve clarity and updates the profile from &quot;overload&quot; to &quot;test&quot; for better alignment with testing practices.

([94d63a64fc3c8ee](https://github.com/Lob2018/SudokuFX/commit/94d63a64fc3c8ee8e9bed126bd683ad7cfa11b90))

Chore JVMApplicationPropertiesUTest : Removes System.out

([825b55f98532ef3](https://github.com/Lob2018/SudokuFX/commit/825b55f98532ef3329a4d447a218f404264c9333))

Chore codeql-action : Update

([ea52f81178836a1](https://github.com/Lob2018/SudokuFX/commit/ea52f81178836a1f56f42c0a8320ec8ef2bf8c14))

Fix (integration-tests) : replace existing DataSource configuration with DataSourceConfigOverload

This change enhances connection management by using an in-memory database and facilitates integration testing.

([50757bda4f5883c](https://github.com/Lob2018/SudokuFX/commit/50757bda4f5883c3f8095bc9e041278f3e671d2e))

Fix FullMenuViewModel : Sets software update with default values

([589b0f26ba06476](https://github.com/Lob2018/SudokuFX/commit/589b0f26ba064762bb07a6864a4f5896613ee6e9))

Fix FullMenuViewModel : Update the software without the v for versions

([c3c07cbdf6207af](https://github.com/Lob2018/SudokuFX/commit/c3c07cbdf6207affd86634d1b38b5b992d65ad9c))

Doc Doxygen

([7a200e5a53311d8](https://github.com/Lob2018/SudokuFX/commit/7a200e5a53311d88d484eb8b40ae5699734866a3))

Fix JVMApplicationPropertiesUTest : Sets default application properties

([e8eba80ac6115c3](https://github.com/Lob2018/SudokuFX/commit/e8eba80ac6115c32630888b93a9cec526be7d673))

Chore CI/CD codeql-action : Update

([00d393fcc2f7cbe](https://github.com/Lob2018/SudokuFX/commit/00d393fcc2f7cbe9cc5360cdd3c86ab0b3e9fb76))

Test JVMApplicationPropertiesUTest : Implements System Stubs for isolating tests that redefine system properties

([ea785a6deec81e0](https://github.com/Lob2018/SudokuFX/commit/ea785a6deec81e07bb6e8f9b5c314368cfeefa7e))

Test FullMenuViewModelITest with application-test.properties :
-Integration test example for VM
-Implements System Stubs for isolating tests that redefine system properties

([126ce6a1dc96441](https://github.com/Lob2018/SudokuFX/commit/126ce6a1dc96441b5f9491aec6f0d7d61723d9b0))

Test MyLogbackUTest : Adds testLogEntryMessageWithInitSpringContextExitForTests

([42a26051ef9a9a2](https://github.com/Lob2018/SudokuFX/commit/42a26051ef9a9a22942b5b42daf3385556b627cd))

Chore : Add system-stubs-jupiter for test isolation.

([e21ab382262a7b8](https://github.com/Lob2018/SudokuFX/commit/e21ab382262a7b8ec6d5241eeacdcc9b38e294a8))

Test : Moved from integration tests to unit tests

([5604d1dda66bf99](https://github.com/Lob2018/SudokuFX/commit/5604d1dda66bf99504c4940492cc586722868ac6))

Docs Doxygen (partial)

([cb29be7de3bd3fd](https://github.com/Lob2018/SudokuFX/commit/cb29be7de3bd3fd12df41c79f52c4cc660301012))

Chore CI/CD : Updates codeql-action

([d0fee890793bd1e](https://github.com/Lob2018/SudokuFX/commit/d0fee890793bd1ee61689067e546231feb107771))

Chore MyLogbackITest : Removes unused import

([6e6f3d461f4d4d3](https://github.com/Lob2018/SudokuFX/commit/6e6f3d461f4d4d3c79a0d7bfb5901a170f5592a7))

Refactor I18n : Replaces &#x27;switch&#x27; expression with an &#x27;if&#x27; statement

([2f95fdc899b300c](https://github.com/Lob2018/SudokuFX/commit/2f95fdc899b300c340f8d4ee0413c0ad6334bd31))

Refactor ApplicationKeystore : Unused import

([baa5b531ea2bacf](https://github.com/Lob2018/SudokuFX/commit/baa5b531ea2bacf921418746a933964645343184))

Chore CI/CD : Update codeql-action

([958e253987bc591](https://github.com/Lob2018/SudokuFX/commit/958e253987bc59111d07a554ec13736c5c2d1184))

Fix FullMenuViewModel :
-Uses constructor injection for softwareService
-Removes _ for instance variables

([b61ef483dd594b3](https://github.com/Lob2018/SudokuFX/commit/b61ef483dd594b3d07f4c4a36ab6f069ff79b4c8))

Refactor SoftwareService

([f0743c7ace9071c](https://github.com/Lob2018/SudokuFX/commit/f0743c7ace9071c68dea21515c831b43de40c49d))

Refactor @SpringBootTest :
-ApplicationKeystoreITest
-MyLogbackITest
-OsFolderFactoryManagerITest

([e79a4008332102b](https://github.com/Lob2018/SudokuFX/commit/e79a4008332102bfad4c83671fab2ffb511368d8))

Feat SoftwareServiceITest : refactor and complete with :
-testGetSoftware_NoSoftwareFound
-testGetSoftware_ExceptionThrown
-testUpdateSoftware_ExceptionThrown

([8bea65ce44100ac](https://github.com/Lob2018/SudokuFX/commit/8bea65ce44100ac28c23dc4d08aa78f5f069e866))

Fix SoftwareServiceITest : JUnit5 test classes and methods have default package visibility

([8dbafd6e746b8df](https://github.com/Lob2018/SudokuFX/commit/8dbafd6e746b8dfff33413b1a76e153b97a9bdd4))

Doc : Doxygen

([a9d039744734e0a](https://github.com/Lob2018/SudokuFX/commit/a9d039744734e0a1d2be0c626364745dc1a4e222))

Test SoftwareServiceITest : partial service test

([f3827d2ac37107f](https://github.com/Lob2018/SudokuFX/commit/f3827d2ac37107f45d0d02dde440b05995c72374))

Chore FullMenuView : simple test onHelloButtonClick

([5fafe75c8459a6f](https://github.com/Lob2018/SudokuFX/commit/5fafe75c8459a6f004dd09a14a3fe3c14abe85a2))

Fix ExceptionToolsUTest : that&#x27;s a unit test

([534834a9a76d875](https://github.com/Lob2018/SudokuFX/commit/534834a9a76d875cddba349f5c1ce8c898154551))

Fix ApplicationKeystoreITest : useless @BeforeAll

([deb80e74efa5700](https://github.com/Lob2018/SudokuFX/commit/deb80e74efa570016437eb3d255a21d755f4b942))

Chore views classes as final : Views aren&#x27;t tested because there&#x27;s no logic

([d357ef021e556bc](https://github.com/Lob2018/SudokuFX/commit/d357ef021e556bc01dda28578d5d55ea27296295))

Fix ApplicationKeystoreITest : Unnecessary import is removed

([64695dc71f40523](https://github.com/Lob2018/SudokuFX/commit/64695dc71f4052328dae60732905962ad2726cc9))

Fix DynamicFontSizeChangeE2ETest : Unnecessary import is removed

([09301ad16cb61c3](https://github.com/Lob2018/SudokuFX/commit/09301ad16cb61c35fbc06a3a64ff734e45d717f1))

Doc README : Specifies Runs for Maven configurations

([2b2cc21ae7432fc](https://github.com/Lob2018/SudokuFX/commit/2b2cc21ae7432fcbba3eb2e1af00218ffeea0858))

Fix SoftwareService : Public constants and fields initialized at declaration are &quot;static final&quot; rather than merely &quot;final&quot;

([68d1d81f8f076cd](https://github.com/Lob2018/SudokuFX/commit/68d1d81f8f076cdba125009c634fb0703d2f42b8))

Fix IEncryptionService : Circular dependencies between classes in the same package are resolved

([e4bfe540d0fdf0b](https://github.com/Lob2018/SudokuFX/commit/e4bfe540d0fdf0b18594763668307a14adda69e4))

Fix IOsFolderFactory : Circular dependencies between classes in the same package are resolved

([c56f10e730f1893](https://github.com/Lob2018/SudokuFX/commit/c56f10e730f189366353ece18f6bc860eaaf7b47))

Fix MacosFolderFactory : Field names comply with the naming convention

([a02a46af27aa607](https://github.com/Lob2018/SudokuFX/commit/a02a46af27aa6070cff38669503ca51558fb913d))

Fix LinuxFolderFactory : Field names comply with the naming convention

([0c258bc20409136](https://github.com/Lob2018/SudokuFX/commit/0c258bc2040913662eaf67f3674e68c042355ea2))

Fix WindowsFolderFactory : Field names comply with the naming convention

([89d5a676b6ea1ff](https://github.com/Lob2018/SudokuFX/commit/89d5a676b6ea1ff037047f426dc5b9a4f9fb9cf2))

Fix style.css : Specifies pseudo-class selectors

([e82cf1f91d0e191](https://github.com/Lob2018/SudokuFX/commit/e82cf1f91d0e191f722422d6c9358742eda1fb52))

Fix DynamicFontSizeChangeE2ETest : Exceptions in &quot;throws&quot; clauses aren&#x27;t superfluous

([174560b66967003](https://github.com/Lob2018/SudokuFX/commit/174560b6696700340382dbbe98ec364e076599b9))

Fix SecretKeyEncryptionServiceAESGCMITest : Unused &quot;private&quot; fields is removed

([233d03efeed2cdf](https://github.com/Lob2018/SudokuFX/commit/233d03efeed2cdf6c007c67f98e649dd47329180))

Fix FileSystemManagerUTest : Exceptions in &quot;throws&quot; clauses are not superfluous

([3e3948e00c663c3](https://github.com/Lob2018/SudokuFX/commit/3e3948e00c663c3e310551bf31eaed5a82fe5ecd))

Fix I18nUTest : Local variable and method parameter names comply with the naming convention

([b5de39a39e0b586](https://github.com/Lob2018/SudokuFX/commit/b5de39a39e0b586e6e57d96e43e735b40ae1595e))

Fix I18nUTest : Methods don&#x27;t have identical implementations

([6be54855d50d06b](https://github.com/Lob2018/SudokuFX/commit/6be54855d50d06bdda5402e74352174b8f6dbe27))

Fix SplashScreenView : Circular dependencies between classes across packages

([5d489cbda3a4cd3](https://github.com/Lob2018/SudokuFX/commit/5d489cbda3a4cd3247218ba6d46a4b15a91bff4f))

Fix SplashScreenView : Circular dependencies between classes across packages

([59f9d8ce6219b0b](https://github.com/Lob2018/SudokuFX/commit/59f9d8ce6219b0bd6ac9db2ce146d36fdff166d6))

Chore CI/CD harden-runner : Update

([1cb8710e296bc70](https://github.com/Lob2018/SudokuFX/commit/1cb8710e296bc709ffb0cd2269dd7fd25a4da795))

Chore flyway-database-hsqldb : Update

([be376749f19f5f1](https://github.com/Lob2018/SudokuFX/commit/be376749f19f5f1d8815db376ca655f860eb7cd8))

Doc README : Doxygen

([e0b4f7659d91e81](https://github.com/Lob2018/SudokuFX/commit/e0b4f7659d91e8154930172a8664ab3e8ff24cea))

Fix ISplashScreenView : Circular dependencies between classes across packages should be resolved

([83695e5a8ca734f](https://github.com/Lob2018/SudokuFX/commit/83695e5a8ca734fddc882f29f1565117267f1699))

Fix FileSystemManagerUTest : testFilesWalkHandleNullPointerException_fail test should include assertion.

([29921cefb528b77](https://github.com/Lob2018/SudokuFX/commit/29921cefb528b775e8768f8510013149b3472deb))

Chore Run_with_Maven_for_JavaFX.jpg : Updated

([2ac93e03ca72a5a](https://github.com/Lob2018/SudokuFX/commit/2ac93e03ca72a5a7756f64597eb77f90fa94983e))

Fix JVMApplicationProperties : Static non-final field names comply with the naming convention

([0f4333d30c1ee13](https://github.com/Lob2018/SudokuFX/commit/0f4333d30c1ee130f9e3f21165172e2ead85464d))

Refactor : Update JVMApplicationProperties class

- Refactored isSpringContextExitOnRefresh method for improved clarity and logic.
- Enhanced documentation for isSpringContextExitOnRefresh to clearly explain its purpose and behavior.
- Renamed setSpringContextExitInRefresh to better reflect its functionality.
- Added new method setInitSpringContextExitForTests to initialize springContextExit for testing purposes.
- Updated comments for better readability and understanding.

([08801286bdf9405](https://github.com/Lob2018/SudokuFX/commit/08801286bdf9405ea122537e7d05eb5083208caa))

Fix isSpringContextExitOnRefresh :
-If springContextExit is empty, the value is retrieved from the system property and recursively re-evaluated.
-Initializes springContextExit inside testGetAppProperties_Success

([c987ece2ba0df27](https://github.com/Lob2018/SudokuFX/commit/c987ece2ba0df27a6ede8a7fce854ccc23d32528))

Feat : If the Spring context is set to exit on refresh, it logs an optimizing startup message.

([c68a0dddd052690](https://github.com/Lob2018/SudokuFX/commit/c68a0dddd052690286362e4ff1ad49ebfec02261))

Fix release batch commands: Improves Java process management in the execution script

- Replace the &#x27;start&#x27; command with &#x27;cmd&#x27; to ensure that the second command only executes after the first one succeeds.
- This change enhances process synchronization, reducing the risk of initialization errors.

([6d32cb8e631a08c](https://github.com/Lob2018/SudokuFX/commit/6d32cb8e631a08cc11c672f44f0245c21ef41a7b))

Fix release shells : Improves Java process management in the execution script

- Replace the &#x27;&amp;&#x27; operator with &#x27;&amp;&amp;&#x27; to ensure the second command only executes after the first one succeeds.
- Add a line break for better readability of the script.

This change ensures better process synchronization and reduces the risk of initialization errors.

([7f3df241a007dc2](https://github.com/Lob2018/SudokuFX/commit/7f3df241a007dc27078c3c2394ab6bc8ad5c8598))

Fix release.yml : Removes debug mode for sonarsource scanner maven

([92f2b25cb4a58e3](https://github.com/Lob2018/SudokuFX/commit/92f2b25cb4a58e364457e64ff9ea40313d855fca))

Fix .sonarcloud.properties : The code coverage exclusions is set online

([07f5897ea7500c7](https://github.com/Lob2018/SudokuFX/commit/07f5897ea7500c740d87fbf071712943c5da4faa))

Fix release.xml : Debug mode

([50ca2ab8964695a](https://github.com/Lob2018/SudokuFX/commit/50ca2ab8964695af86c85b28e79d5e0fe7040473))

Feat release.xml : Specifies jacoco report&#x27;s path

([94f98514c5fc82a](https://github.com/Lob2018/SudokuFX/commit/94f98514c5fc82ad674e63290cde978a94a56091))

Fix .sonarcloud.properties : Defines the scope of code coverage exclusions

([1fac80f4462718a](https://github.com/Lob2018/SudokuFX/commit/1fac80f4462718a2466a3b8f6273a8d82d21db85))

Fix release.yml : Set fetch-depth to 0 in actions/checkout to avoid shallow clone issues

- Updated the GitHub Actions workflow to include &#x60;fetch-depth: 0&#x60; in the checkout step.
- This change ensures that the full repository history is fetched, resolving warnings related to missing SCM information during SonarCloud analysis.

([57c47e3075334ff](https://github.com/Lob2018/SudokuFX/commit/57c47e3075334ff17056d1e613474dc2f598f794))

Fix .sonarcloud.properties : The test exclusions and the tests path

([54dc43dfb5e9848](https://github.com/Lob2018/SudokuFX/commit/54dc43dfb5e98486c42e5f2a9c5f02c9d5d6706a))

Feat : renames repository to SudokuFX

([0f0971fb6a2b2a0](https://github.com/Lob2018/SudokuFX/commit/0f0971fb6a2b2a08497a38b17389908b95c75cdf))

Doc DOXYGEN

([e5871832eb69318](https://github.com/Lob2018/SudokuFX/commit/e5871832eb69318bce1a20721f51a49725e948f7))

Chore DataSourceConfig :
-rename the class from DynamicDataSourceConfiguration to DataSourceConfig
-sets a pool name

([aa570762dc35d94](https://github.com/Lob2018/SudokuFX/commit/aa570762dc35d94d1ea7cb3864d9debf32c55698))

Chore application.properties : Separates the development configuration

([162480daa1fc37b](https://github.com/Lob2018/SudokuFX/commit/162480daa1fc37b83ba3faef74f9e8e29dcf5e67))

Chore CI/CD : Upgrades ncipollo/release-action

([448c3391606ae8b](https://github.com/Lob2018/SudokuFX/commit/448c3391606ae8b66b3f2ba332d3ee18193d374a))

Chore CI/CD : Upgrades github/codeql-action

([29924ac31d80fd2](https://github.com/Lob2018/SudokuFX/commit/29924ac31d80fd242ca37bace1298d8ac1af0ff7))

Doc README : Adds links to the latest release

([2e8e5e577cd6d52](https://github.com/Lob2018/SudokuFX/commit/2e8e5e577cd6d523978c59c175a596c5be37b23b))

Chore CI/CD release : the job package_for_macos_x86_64 specifies Upload output folder (macOS-x86_64)

([bc0775dbe9a2d27](https://github.com/Lob2018/SudokuFX/commit/bc0775dbe9a2d2741e970da7b1b11f88aaaf7df3))

Doc README : Adds MacOS x86_64 details

([6bb812056cc12f6](https://github.com/Lob2018/SudokuFX/commit/6bb812056cc12f67f565c04ce46b367316068db9))

Feat CI/CD : Package for Macos x86_64 with macos13 runner

([c03fc0080cb3de1](https://github.com/Lob2018/SudokuFX/commit/c03fc0080cb3de1421ce964af0823c362fa5f8d0))

Fix CI/CD release : adds hash_macos-x86_64.txt

([4bcc7286b459f69](https://github.com/Lob2018/SudokuFX/commit/4bcc7286b459f69a9b37f2e74a19954aeaff701e))

Feat CI/CD : Removes the Sonar cache located at ~/.sonar/cache because we consistently use the latest SonarQube plugin directly via the command line.

([ca5f6b8624b8e6d](https://github.com/Lob2018/SudokuFX/commit/ca5f6b8624b8e6d9376c87d8ca6c4acaa917153d))

Chore CI/CD : upgrades

([628a398a9a9f06d](https://github.com/Lob2018/SudokuFX/commit/628a398a9a9f06d0a02090937cd6b435771974d2))

Feat CI/CD coverage_report : The action should fail if the coverage is below 80%

([f95daedc2190dc2](https://github.com/Lob2018/SudokuFX/commit/f95daedc2190dc2b2d44fbd172ea1ac4e2326a41))

Feat pom.xml : Set line covered ration to minimum 80%

([98bf87b804c59e4](https://github.com/Lob2018/SudokuFX/commit/98bf87b804c59e405e5694ef751c987d02d20d20))

Doc README: Replaces the documentation content with the Doxygen documentation (GitHub page)

([8cbbc1becd69758](https://github.com/Lob2018/SudokuFX/commit/8cbbc1becd69758bf48e0cf97ab5cf3e6aa71d9f))

Doc README: Replaces license and security content with repository tab links

([9493508d917e6a6](https://github.com/Lob2018/SudokuFX/commit/9493508d917e6a6a15d443c02b5723e801785da9))

Chore CI/CD : github/codeql-action/upload-sarif from 3.27.1 to 3.28.0

([1dcb766a9e27fad](https://github.com/Lob2018/SudokuFX/commit/1dcb766a9e27fad80e985684fa6fd55476389950))

CI/CD Feat:
-Push only because it packages the app
-Linux package and analysis with SonarCloud is necessarily executed before other tasks

([ab772805af30680](https://github.com/Lob2018/SudokuFX/commit/ab772805af306804890aa5981e595ab990922b94))

Doc README: Reorganization of badges and associated links

([5821cba00d4df44](https://github.com/Lob2018/SudokuFX/commit/5821cba00d4df44d74ff3770a437f2c0e797412e))

Chore CI/CD : upgrades

([8e1aab5b0f03e1f](https://github.com/Lob2018/SudokuFX/commit/8e1aab5b0f03e1f9741755e6a970baa507670894))

Chore pom.xml : flyway-database-hsqldb from 11.1.0 to 11.1.1

([530899a3050e4cf](https://github.com/Lob2018/SudokuFX/commit/530899a3050e4cf7a7d025166e18bd691d8d17a4))

Chore gitignore : ignore pom.xml.versionsBackup, generated by mvn versions:use-latest-releases

([0f0b03f91e95181](https://github.com/Lob2018/SudokuFX/commit/0f0b03f91e951816694a80e5f5365a7de0349061))

Chore JetBrains/qodana-action latest version

([f6c82cc0e02da66](https://github.com/Lob2018/SudokuFX/commit/f6c82cc0e02da66f90a958c123ddfc4343f2b7e3))

Chore spring-boot version

([6f7aee9791cb693](https://github.com/Lob2018/SudokuFX/commit/6f7aee9791cb69339c8c158ae549140fc96613fb))

Fix CI/CD release : Pin actions/cache to specific SHA for stability and security

([319805b54e94c27](https://github.com/Lob2018/SudokuFX/commit/319805b54e94c27db8c13fcf0b6aabe5c4b2cadf))

Feat CI/CD : Failing the workflow when the SonarQube Cloud Quality Gate fails

([ef9f78e6c9b1e86](https://github.com/Lob2018/SudokuFX/commit/ef9f78e6c9b1e86b27e291146af453b84dc48cb5))

Fix CI/CD : Sonarcloud environment without -X debugging mode

([30cf00b3a4dbbba](https://github.com/Lob2018/SudokuFX/commit/30cf00b3a4dbbbac0487e4e31344c89d3b3af952))

Fix CI/CD : Sonarcloud environment

([8fff12901d5dc2e](https://github.com/Lob2018/SudokuFX/commit/8fff12901d5dc2ea628de471d1d137cd0c2b6dbb))

Fix CI/CD : Sonarcloud detailed

([e2d80fd31bea9a7](https://github.com/Lob2018/SudokuFX/commit/e2d80fd31bea9a799c6cd839743ad4036c7047ae))

Fix CI/CD : Sonarcloud moved to Analysis with SonarCloud and package for Windows, Linux, MacOS

([01d517569b5447a](https://github.com/Lob2018/SudokuFX/commit/01d517569b5447ab8854e5c25aae5c2d79ac18ed))

Fix CI/CD : Sonarcloud moved to Analysis with SonarCloud and package for Windows, Linux, MacOS

([d388e458902a959](https://github.com/Lob2018/SudokuFX/commit/d388e458902a959041d4f3f6e750bd86f61f3629))

Fix CI/CD : Sonarcloud

([ab049a7e287525d](https://github.com/Lob2018/SudokuFX/commit/ab049a7e287525d4f709d2e8a64675dbf57648e5))

Feat CI/CD : Sonarcloud

([4e2cbce807f3a41](https://github.com/Lob2018/SudokuFX/commit/4e2cbce807f3a419b9201d7e32bc92381e2236d1))

Feat CI/CD : Sonarcloud

([2cc1523c37809c1](https://github.com/Lob2018/SudokuFX/commit/2cc1523c37809c10ba1d6b5c643e9e4cc6f9983a))

Chore @Autowired : Constructor Injection

([16d6f0c7672ae10](https://github.com/Lob2018/SudokuFX/commit/16d6f0c7672ae10ac2a91b142c470f3d215ef299))

Doc Doxygene

([1ca62dbb9990843](https://github.com/Lob2018/SudokuFX/commit/1ca62dbb9990843147bf024a81ac8e839db4e67d))

Doc README

([69c42be3422fc83](https://github.com/Lob2018/SudokuFX/commit/69c42be3422fc833888a32fc9809f495850a308c))

Chore CD release.yml : release text moved from 🔹 to ▪

([12c9660a4af4276](https://github.com/Lob2018/SudokuFX/commit/12c9660a4af42769003cbe17b536b540c00e73e0))

Chore pom.xml : flyway-database-hsqldb upgrade

([9014e4c6531fac3](https://github.com/Lob2018/SudokuFX/commit/9014e4c6531fac3c51584be496e9f18aa4df0f6a))

Feat SPlashScreenView : Animates the SVG flower

([c7ecf568ad96700](https://github.com/Lob2018/SudokuFX/commit/c7ecf568ad96700c9326461d7379f4162c6ad74d))

Doc Doxygen

([e9b32317339cf4a](https://github.com/Lob2018/SudokuFX/commit/e9b32317339cf4ae47806aa2ffb21ebeb23e574d))

Feat Spring : Specifies
- application.properties for spring.jpa
- DynamicDataSourceConfiguration HikariConfig with prepared statements cache

([acf781c133aa466](https://github.com/Lob2018/SudokuFX/commit/acf781c133aa466e66602d5a32b5045ddd897026))

Doc README : badges

([cb86f44f02eb948](https://github.com/Lob2018/SudokuFX/commit/cb86f44f02eb948fd952f0bcb597a5c325047d08))

Fix CI : cache wix311.exe Harden GitHub Actions

([aab2afcd5ee3233](https://github.com/Lob2018/SudokuFX/commit/aab2afcd5ee3233bce02d150d6df6dd440d8f71b))

Fix CI : cache wix311.exe

([62dec026cac20e6](https://github.com/Lob2018/SudokuFX/commit/62dec026cac20e64aa16c4a4a88391c1e1441419))

Fix CI : cache wix311.exe

([a560d50b6f6cfdc](https://github.com/Lob2018/SudokuFX/commit/a560d50b6f6cfdcb4a3a1cb4ef18841cef1323af))

CI release : harden Github Actions

([4dc942ff6bccc07](https://github.com/Lob2018/SudokuFX/commit/4dc942ff6bccc076b702b8e602143747d8959c48))

CI qodana : cache default branch only

([e32acd575fda5b5](https://github.com/Lob2018/SudokuFX/commit/e32acd575fda5b52d56b52bf3e94d5d14a84b099))

Doc README : from OS badges to workflow status badge for Packages (Windows, Linux, MacOS)

([b1b6b65d9d0f69a](https://github.com/Lob2018/SudokuFX/commit/b1b6b65d9d0f69a063a0cc01dc48657867f9ffbc))

CI/CD optimize with cache

([727255b231896fe](https://github.com/Lob2018/SudokuFX/commit/727255b231896fef3433a5a3d4a39b1094b55e2b))

CI/CD feat :
-Jacoco report on summary
-Code coverage badge

([f1fb522b88a973a](https://github.com/Lob2018/SudokuFX/commit/f1fb522b88a973ac8275005172cc07298f29f052))

CI/CD fix : branches from &quot;main,develop&quot; to &quot;main&quot;,&quot;develop&quot;

([fa5db4b1f46ecd0](https://github.com/Lob2018/SudokuFX/commit/fa5db4b1f46ecd0ff24ba5309eb04dbf6d246c9f))

CI/CD : renaming

([bb01773cd7cdc21](https://github.com/Lob2018/SudokuFX/commit/bb01773cd7cdc21c5243348765672fc07a077e64))

CI JaCoCo report

([f32879581fe3124](https://github.com/Lob2018/SudokuFX/commit/f32879581fe312418b1c005c0f76337dcb909171))

Initial commit

([052f4c3e8089715](https://github.com/Lob2018/SudokuFX/commit/052f4c3e80897150d29504d8bfcd09ee6a2eb36f))
