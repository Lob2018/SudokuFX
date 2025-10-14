# 🎲 SudokuFX

Dive into the world of Sudoku with a game that offers:
- 🧩 **Play challenging 9x9 puzzles**: Enjoy grids ranging from beginner to expert levels.
- 🤖️ **Solve any 9x9 Sudoku grid**: Let the game solve your puzzles or input custom ones.
- ✨ **Create profiles**: Save progress and manage personalized profiles for each player.
- 💾 **Save anytime**: Effortlessly continue your puzzle-solving journey.

Challenge your mind and enjoy hours of logical fun with SudokuFX!

[![License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme)<br>
[![Accessibility](https://img.shields.io/badge/accessibility-WCAG%202.1%20AA-brightgreen)](https://www.w3.org/WAI/WCAG21/quickref/)<br>
[![OpenSSF Best Practices](https://www.bestpractices.dev/projects/10875/badge)](https://www.bestpractices.dev/projects/10875)
[![OpenSSF Scorecard](https://api.scorecard.dev/projects/github.com/Lob2018/SudokuFX/badge)](https://scorecard.dev/viewer/?uri=github.com/Lob2018/SudokuFX)
[![Snyk Scan](https://img.shields.io/badge/Snyk%20Scan-authentication%20required-brightgreen?logo=snyk)](https://app.snyk.io/org/lob2018/project/eb67e66c-32ab-4713-af25-6438d35db490)
[![CodeQL Analysis](https://github.com/lob2018/SudokuFX/actions/workflows/codeql.yml/badge.svg)](https://github.com/lob2018/SudokuFX/actions/workflows/codeql.yml)<br>
[![Dependabot Updates](https://github.com/Lob2018/SudokuFX/actions/workflows/dependabot/dependabot-updates/badge.svg)](https://github.com/Lob2018/SudokuFX/actions/workflows/dependabot/dependabot-updates)
[![Qodana](https://github.com/Lob2018/SudokuFX/actions/workflows/qodana_code_quality.yml/badge.svg)](https://github.com/Lob2018/SudokuFX/actions/workflows/qodana_code_quality.yml)<br>
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Lob2018_SudoFX2024&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Lob2018_SudoFX2024)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Lob2018_SudoFX2024&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Lob2018_SudoFX2024)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Lob2018_SudoFX2024&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Lob2018_SudoFX2024)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Lob2018_SudoFX2024&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Lob2018_SudoFX2024)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Lob2018_SudoFX2024&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Lob2018_SudoFX2024)<br>
[![JaCoCo line covered ratio (pom.xml)](https://github.com/Lob2018/SudokuFX/actions/workflows/coverage_report.yml/badge.svg)](https://github.com/Lob2018/SudokuFX/actions/workflows/coverage_report.yml)
[![Open Issues](https://img.shields.io/github/issues/lob2018/SudokuFX)](https://github.com/Lob2018/SudokuFX/issues)
[![Open Pull Requests](https://img.shields.io/github/issues-pr/lob2018/SudokuFX)](https://github.com/Lob2018/SudokuFX/pulls)
[![GitHub release](https://img.shields.io/github/v/release/lob2018/SudokuFX)](https://github.com/Lob2018/SudokuFX/releases)

# [![SudokuFX in action](https://raw.githubusercontent.com/Lob2018/SudokuFX/master/.myresources/design_and_modeling/images/SudokuFX_in_action.jpg)](https://github.com/Lob2018/SudokuFX/releases/latest)

## Contents

- [System requirements](#system-requirements)
- [Installation](#installation)
  - [Verifying downloaded assets](#verifying-downloaded-assets)
- [Use](#use)
- [Examples](#examples)
- [Update](#update)
- [Uninstallation](#uninstallation)
- [Documentation](https://lob2018.github.io/SudokuFX/)
- [Security](https://github.com/Lob2018/SudokuFX?tab=security-ov-file#readme)
- [Project](#project)
  - [Overview](#overview)
  - [Package structure](#package-structure)
  - [Roadmap](#roadmap)
  - [Mockup](#mockup)
  - [Build with](#build-with)
  - [Required Application Properties to Run](#required-application-properties-to-run)
  - [How to develop on Windows with IntelliJ IDEA](#how-to-develop-on-windows-with-intellij-idea)
    - [DEV Runtime monitoring with VisualVM](#dev-runtime-monitoring-with-visualvm)
    - [DEV Diagnostic Commands Examples](#dev-diagnostic-commands-examples)
- [Contributing](#contributing)
- [Code of Conduct](#code-of-conduct)
- [Contributors](#contributors)
- [Licence](https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme)

## System requirements

### Minimum specifications
- **Operating System:** Windows 10/11, macOS 11 (Big Sur) or later, Ubuntu 20.04 LTS (64-bit)
- **Processor:** Dual-core 2.0 GHz (Intel x86_64 or Apple Silicon ARM64)
- **Memory:** 4 GB RAM
- **Storage:** 1 GB free disk space
- **Audio:** Sound card (integrated or dedicated)
- **Display:** 1024 × 768 pixels
- **Other:** Java JRE (included in MSI/DEB/DMG versions)

### Recommended specifications
- **Processor:** Quad-core 2.5 GHz or higher (Intel x86_64 or Apple Silicon ARM64)
- **Memory:** 8 GB RAM
- **Storage:** 2 GB free disk space (SSD recommended)
- **Display:** 1920 × 1080 pixels
- **Audio:** Stereo audio system for optimal experience

## Installation

[![Windows](https://img.shields.io/badge/Windows-Compatible-brightgreen)](https://github.com/Lob2018/SudokuFX/releases/latest)
[![Linux](https://img.shields.io/badge/Linux-Compatible-brightgreen)](https://github.com/Lob2018/SudokuFX/releases/latest)
[![MacOS_Arm64,_x86__64](https://img.shields.io/badge/MacOS_Arm64,_x86__64-Compatible-brightgreen)](https://github.com/Lob2018/SudokuFX/releases/latest)


- Windows
  - Application with Java Runtime Environment (JRE) included
    - Download and install the latest Windows version of the MSI file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)
    - The MSI file does not have a code signing certificate, Microsoft Defender SmartScreen can inform you of this during installation; to continue the installation click on **additional information**, then **Run anyway**.
  - Application without Java Runtime Environment (JRE) included
    - [The latest Adoptium Temurin JRE](https://adoptium.net/temurin/releases/?package=jre) must be installed on your machine with the corresponding JAVA_HOME environment variable set
    - Download, unzip, and keep all the files together, from the latest Windows version of the ZIP file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)

- Linux (Debian-based distributions)
  - Application with Java Runtime Environment (JRE) included
    - Download and install the latest Linux version of the DEB file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)
    - Run `sudo apt install ./sudokufx-jvm_v.v.v_amd64.deb`
  - Application without Java Runtime Environment (JRE) included
    - [The latest Adoptium Temurin JRE](https://adoptium.net/temurin/releases/?package=jre) must be installed on your machine with the corresponding JAVA_HOME environment variable set
    - Download, untar, and keep all the files together, from the latest Linux version of the TAR file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)

- MacOS
  - Application with Java Runtime Environment (JRE) included
    - Download and install the latest MacOS version of the DMG file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)
  - Application without Java Runtime Environment (JRE) included
    - [The latest Adoptium Temurin JRE](https://adoptium.net/temurin/releases/?package=jre) must be installed on your machine with the corresponding JAVA_HOME environment variable set
    - Download, unzip, and keep all the files together, from the latest MacOS version of the ZIP file, [available in Assets.](https://github.com/Lob2018/SudokuFX/releases/latest)

### Verifying downloaded assets

To ensure the integrity of downloaded assets, import the GPG public key with `gpg --import sudokufx-public-key.asc`, then verify the files, e.g., the MSI file, using `gpg --verify SudokuFX_JVM-v.v.v.msi.asc SudokuFX_JVM-v.v.v.msi`. For more information, refer to the [GnuPG Manual](https://www.gnupg.org/gph/en/manual.html).

## Use

### Launch and play SudokuFX

#### Windows
- **Application with Java Runtime Environment (JRE) included**: Launch SudokuFX from the Start Menu after installing the MSI file.
- **Application without Java Runtime Environment (JRE) included**: Run from the extracted ZIP folder:
```
./SudokuFX-v.v.v.bat
```

#### Linux
- **Application with Java Runtime Environment (JRE) included**: Launch from the applications menu or run from the terminal in the folder where the DEB file was installed:
```
/opt/sudokufx-jvm/bin/SudokuFX-JVM
```
- **Application without Java Runtime Environment (JRE) included**: Navigate to the extracted TAR folder and run:
```
./SudokuFX-v.v.v.sh
```

#### MacOS
- **Application with Java Runtime Environment (JRE) included**: Launch from the Applications folder or Launchpad after installing the DMG file.
- **Application without Java Runtime Environment (JRE) included**: Navigate to the extracted ZIP folder and run:
```
./SudokuFX-v.v.v.sh
```

### Basic gameplay

1. **Select difficulty**: Choose from Easy, Medium, or Difficult levels to start a new game.
2. **Fill the grid**: Click on any empty cell and enter numbers 1-9.
3. **Check progress**: The game automatically validates your entries.
4. **Save and load**:
    - Progress is automatically saved for the **current player**, so ongoing games are preserved.
    - You can also **switch between players** and **resume previously saved games**.

### Features

- **Multiple difficulty levels**: From beginner-friendly to difficult challenges.
- **Auto save**: Your progress is automatically saved.
- **Clean interface**: Modern, intuitive design.
- **Cross-platform**: Works seamlessly on Windows, Linux, and MacOS.

### Troubleshooting

If you encounter any issues:

1. **Game won't start**: Ensure you have the correct Java version if using the non-JRE version.
2. **Performance issues**: Try closing other applications to free up system resources.
3. **Display problems**: Check your system's display scaling settings.
4. **Save and load issues**: Verify that SudokuFX has write permissions to your user directory.

For additional help, check the application logs or [file an issue](https://github.com/Lob2018/SudokuFX/issues) on GitHub.

## Examples

## Update

- Windows
  - Application with Java Runtime Environment (JRE) included (from MSI file)
    - [Follow the installation instructions](#installation)
  - Application without Java Runtime Environment (JRE) included (ZIP file with the .bat file and the JAR)
    - Delete your old unzipped folder from the ZIP file, and follow [the installation instructions](#installation)

- Linux
  - Application with Java Runtime Environment (JRE) included (from .deb file)
    - [Follow the installation instructions](#installation)
  - Application without Java Runtime Environment (JRE) included (TAR file with the .sh file and the JAR)
    - Delete your old untarred folder from the TAR, and follow [the installation instructions](#installation)

- MacOS
  - Application with Java Runtime Environment (JRE) included (from .dmg file)
    - [Follow the installation instructions](#installation)
  - Application without Java Runtime Environment (JRE) included (ZIP file with the .sh file and the JAR)
    - Delete your old unzipped folder from the ZIP file, and follow [the installation instructions](#installation)

## Uninstallation

- Windows
  - Application with Java Runtime Environment (JRE) included (from MSI file)
    - **Uninstall from the Control Panel (for programs)**
        1. In the search box on the taskbar, type **Control Panel** and select it from the results.
        2. Select **Programs > Programs and Features**.
        3. Press and hold (or right-click) on the program you want to remove and select **Uninstall** or *
           *Uninstall/Change**. Then follow the directions on the screen.
  - Application without Java Runtime Environment (JRE) included (ZIP file with the .bat file and the JAR)
    - **Delete your unzipped folder from SudokuFX-v.v.v_windows.zip**

- Linux
  - Application with Java Runtime Environment (JRE) included (from .deb file)
    - Run `sudo apt purge sudokufx-jvm`
  - Application without Java Runtime Environment (JRE) included (TAR file with the .sh file and the JAR)
    - **Delete your untarred folder from SudokuFX-v.v.v_linux.tar.gz**

- MacOS
  - Application with Java Runtime Environment (JRE) included (from .dmg file)
    - Drag the application to the Trash
  - Application without Java Runtime Environment (JRE) included (ZIP file with the .sh file and the JAR)
    - **Delete your unzipped folder from SudokuFX-v.v.v_macos.zip**

> [!IMPORTANT]
> **To completely remove your application data and logs, delete the following folder (this action is irreversible):**
>- Windows:
   >
   >     C:/Users/\<USERNAME\>**[^1]**/AppData/Local/Soft64.fr/SudokuFX
>- Linux:
   >
   >     /home/\<USERNAME\>**[^1]**/.local/share/Soft64.fr/SudokuFX **[^2]**
>- MacOS:
   >
   >     /Users/\<USERNAME\>**[^1]**/Library/Application Support/Soft64.fr/SudokuFX

[^1]:Replace \<USERNAME\> with your actual username on the system.
[^2]: `.local/share` may be replaced by `$XDG_DATA_HOME` if defined.

## Project

### Overview

Cross-platform desktop application developed in Java using JavaFX, Spring Boot, HSQLDB, Maven, and SonarCloud, following the Model-View-ViewModel-Coordinator (MVVM-C) architecture.

### Package structure

```
.
├── benchmark           // performance and load testing utilities
├── common              // shared utilities, annotations, enums, exceptions, interfaces
│   ├── annotation      // custom annotations
│   ├── enums           // shared enums and constants
│   ├── exception       // common exception classes
│   ├── interfaces      // reusable interfaces
│   │   └── mapper      // data mapping interfaces
│   └── util            // general utility classes
│       └── sudoku      // sudoku-related utilities
├── config              // application configuration (database, OS settings)
│   ├── database        // database configurations
│   └── os              // operating system specific configs
├── dto                 // data transfer objects
│   └── github          // github-specific DTOs
├── model               // domain/business models
├── navigation          // navigation management for the Coordinator
├── repository          // data access layer
├── service             // business services and logic
│   ├── business        // core business services
│   ├── external        // integration with external APIs or systems
│   └── ui              // services interacting with the UI (audio, file processing, etc.)
├── view                // UI views and components
│   ├── main            // MainView
│   └── component       // reusable UI components
│   │   ├── list        // list components
│   │   └── toaster     // toaster notifications
│   └── util            // utilities for bindings and factories
└── viewmodel           // view models for MVVM pattern
    └── state           // state holders (observable state for ViewModels, e.g., PlayerStateHolder)
```

### Roadmap

- [The project roadmap](https://github.com/users/Lob2018/projects/4)

### Mockup

- [The application mockup (Figma)](https://www.figma.com/design/GiSwlg2mZofXalf1Quaa5w/SudokuFX?node-id=0-1&t=smJqt7CQuD0zZuUP-1)

> [!IMPORTANT]
>
>### Required Application Properties to Run
>
>For the application to work properly, the following application properties must be set at the JVM level:
>
>- **app.name**: This property specifies the name of the application.
>- **app.version**: Specifies the application version in stable SemVer format with numeric MAJOR, MINOR, and PATCH only (e.g. 1.2.3; not 1.2.3-beta.1 or 1.2.3+build.5).
>- **app.organization**: Specifies the organization responsible for the application.
>- **app.license**: Specifies the license under which the application is distributed.

### Build with

- Java LTS (e.g. 21)
- JavaFX
- WiX Toolset v3.11
- Dependencies:
    - Development
        - javafx-controls
        - javafx-fxml
        - commons-lang3 (utility classes for strings, objects, numbers, etc.)
    - DTOs
        - MapStruct
    - SGBDR & SPRING BOOT
        - HSQLDB
        - Spring boot
            - Starter
            - Gluon Ignite with Spring
            - Starter data JPA
            - Starter validation
        - flyway (database migration)
        - passay (generate and validate secrets)
        - datasource-proxy-spring-boot-starter (intercepts and logs SQL queries for debugging and performance analysis)
    - Logs
        - logback from Spring Boot
    - Build dependencies:
        - spotless-maven-plugin (ensures consistent code formatting across the project)
        - maven-checkstyle-plugin (static code analysis to enforce code style rules)
        - maven-compiler-plugin
            - annotationProcessorPaths:
                - MapStruct processor (for code generation)
        - maven-enforcer-plugin (to define the minimum Maven version)
        - javafx-maven-plugin
        - spring-boot-maven-plugin (create the uber JAR)
        - exec-maven-plugin (for scripts generating the packages)
        - jmh (for temporary performance evaluation)
    - Test dependencies:
        - spring boot starter test (JUnit, Mockito, Hamcrest)
        - surefire
        - jacoco
        - testfx-junit5 (ex.:FxRobot to execute actions within the UI, or custom Hamcrest matchers org.testfx.matcher.*.)

### How to develop on Windows with IntelliJ IDEA

- Download and install [the LTS version of the Adoptium Temurin JDK Downloads](https://adoptium.net/temurin/releases/?package=jdk)
- Download and install [WiX Toolset v3.11](https://github.com/wixtoolset/wix3/releases/tag/wix3112rtm) (in order to package the application)
    - Activate .NET framework 3.5.1 (Control Panel > Programs > Programs and Features > Turn Windows features on or off)
    - Launch wix311.exe
- Configured the necessary environment variables
    - JDK
        - name:JAVA_HOME
        - value LTS (e.g. 21):C:\Program Files\Eclipse Adoptium\jdk-21.0.3.9-hotspot
    - WiX
        - name:WIX
        - value:C:\Program Files (x86)\WiX Toolset v3.11\
- IntelliJ IDEA
    - Clone the repository
    - Select the project's JDK
        - File > Project Structure > SDK > Add SDK from disk (select the JDK)
    - Run Maven configurations (in the top right corner)
        - SudoMain.java is the main class
        - Maven run configurations are saved as project files in .idea/runConfigurations
            - Temporary performance evaluation with Java Microbenchmark Harness (JMH):
                1. Comment out `<excludeGroupIds>org.openjdk.jmh</excludeGroupIds>`
                   and `<exclude>fr/softsf/sudokufx/benchmark/**/*.java</exclude>` in the `pom.xml`
                2. Run `mvn clean` and execute the `[Jmh init.]` configuration
                3. Manage your benchmark tests in the `fr.softsf.sudokufx.benchmark` package
                4. **Once benchmarking is complete, uncomment `<excludeGroupIds>org.openjdk.jmh</excludeGroupIds>`
                   and `<exclude>fr/softsf/sudokufx/benchmark/**/*.java</exclude>` in the `pom.xml`**

#### DEV Runtime monitoring with VisualVM

- Install [VisualVM](https://visualvm.github.io/) and the following plugins:
    - **VisualVM-MBeans**
      Enables real-time monitoring of JMX-exposed metrics (e.g., internal HikariCP connection pool metrics) via the MBeans tab
      - Add `config.setRegisterMbeans(true)` in HikariCP configuration or `config.properties`
      - Plugin adds a new **MBeans** tab
    - **VisualGC**
      Visualizes JVM memory regions and garbage collection activity; useful for diagnosing memory usage and detecting leaks
      - Plugin adds a new **Visual GC** tab

- Use the dedicated Maven profile `visualvm-monitoring` to launch the application with JMX and GC logging enabled
    - Profile declared in `pom.xml`
    - Run Configuration: `SudokuFX run with VisualVM Monitoring`
      - Command: `clean javafx:run -Pvisualvm-monitoring -f pom.xml`
      - Enables VisualVM sampling and MBeans access via JMX (`localhost:9010`)
      - In VisualVM, **Add JMX Connection for localhost:9010** in order to access full JMX metrics, otherwise only a local jvmstat connection is available.

#### DEV Diagnostic Commands – VisualVM Monitoring

Detecting **classloader leaks**, **Metaspace growth**, and **heap retention patterns** in JVM applications. Each command is paired with actionable indicators to support reproducible analysis.

Run Configuration: `SudokuFX run with VisualVM Monitoring`

| Component | Commands | Purpose | Diagnostic Indicators |
|:--|:--|:--|:--|
| **🧠 Native Memory** | `jcmd <pid> VM.native_memory summary` | Monitor off-heap growth and Metaspace usage | **Off-Heap Leak:** Sustained increase in `Internal` or `Unknown` regions<br>**Metaspace Growth:** Continued increase in committed size and class count after application has reached steady state |
| **📦 Classloaders** | `jcmd <pid> GC.run` **THEN** `jmap -clstats <pid>` (requires full JDK) | Trigger GC and validate loader retention | **Leak Evidence:** Loader count remains stable (e.g., 80) after forced GC |
| **🧮 Heap Objects** | `jcmd <pid> GC.class_histogram` **OR** `jmap -histo:live <pid> \| head -50` (requires full JDK) | Identify dominant heap objects and retention | **Retention Alert:** 32,000+ `SimpleBooleanProperty` instances persist post-cleanup |

## Contributing

We welcome all contributions to SudokuFX — whether it's bug fixes, new features, documentation, or ideas.

Please read our [Contributing Guide](./CONTRIBUTING.md) to get started.
It includes setup instructions, coding standards, commit conventions, and more.

## Code of Conduct

We are committed to fostering a welcoming and respectful community.
Please read our [Code of Conduct](./CODE_OF_CONDUCT.md) before participating.

## Contributors

[Lob2018](https://github.com/Lob2018)
