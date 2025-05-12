# 🎲 SudokuFX

Dive into the world of Sudoku with a game that offers:
- 🧩 **Play challenging 9x9 puzzles**: Enjoy grids ranging from beginner to expert levels.
- 🤖️ **Solve any 9x9 Sudoku grid**: Let the game solve your puzzles or input custom ones.
- ✨ **Create profiles**: Save progress and manage personalized profiles for each player.
- 💾 **Save anytime**: Effortlessly continue your puzzle-solving journey.

Challenge your mind and enjoy hours of logical fun with SudokuFX! 🚀

[![License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme)<br>
[![OpenSSF Scorecard](https://api.scorecard.dev/projects/github.com/Lob2018/SudokuFX/badge)](https://scorecard.dev/viewer/?uri=github.com/Lob2018/SudokuFX)
[![Known Vulnerabilities](https://snyk.io/test/github/Lob2018/SudokuFX/badge.svg)](https://snyk.io/test/github/Lob2018/SudokuFX)
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
  - [Roadmap](#roadmap)
  - [Mockup](#mockup)
  - [Build with](#build-with)
  - [Required Application Properties to Run](#required-application-properties-to-run)
  - [How to develop on Windows with IntelliJ IDEA](#how-to-develop-on-windows-with-intellij-idea)
- [Contributors](#contributors)
- [Feedback](#feedback)
- [Licence](https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme)

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
   >     /home/\<USERNAME\>**[^1]**/.local/share/Soft64.fr/SudokuFX
>- MacOS:
   >
   >     /Users/\<USERNAME\>**[^1]**/Library/Application Support/Soft64.fr/SudokuFX

[^1]:Replace \<USERNAME\> with your currently logged-in username.

## Project

### Overview

Cross-platform desktop application developed in Java using JavaFX, Spring Boot, HSQLDB, Maven, and SonarCloud, following the Model-View-ViewModel (MVVM) architecture.

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
>- app.name:This property specifies the name of the application.
>- app.version:This property specifies the version of the application.
   >   - This SemVer-like format is only numeric MAJOR.MINOR.PATCH (e.g., 1.0.0, 2.1.3).
>- app.organization: Specifies the organization responsible for the application.
>- app.license: Specifies the license under which the application is distributed.

### Build with

- Java LTS (e.g. 21)
- JavaFX
- WiX Toolset v3.11
- Dependencies:
    - Development
        - javafx-controls
        - javafx-fxml
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
    - Logs
        - logback from Spring Boot
    - Build dependencies:
        - spotless-maven-plugin (ensures consistent code formatting across the project)
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

## Contributors

[Lob2018](https://github.com/Lob2018)

## Feedback

- [File an issue](https://github.com/Lob2018/SudokuFX/issues)
    - If you want you can attach the application logs you find:
        - Windows
          - Inside C:/Users/\<USERNAME\>**[^1]**/AppData/Local/Soft64.fr/SudokuFX/logs-sudokufx
        - Linux
          - Inside /home/\<USERNAME\>**[^1]**/.local/share/Soft64.fr/SudokuFX/logs-sudokufx
        - MacOS
          - Inside /Users/\<USERNAME\>**[^1]**/Library/Application Support/Soft64.fr/SudokuFX/logs-sudokufx
