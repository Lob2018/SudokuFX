@echo off
	chcp 65001
	color 0A
	:: WINDOWS RELEASE
	title WINDOWS RELEASE
    echo.
    echo          ▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
    echo    ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓
    echo ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓
    echo ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓
    echo  ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓
    echo    ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓
    echo        ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒
    echo        ▒▒▒▒▒
    echo.
	echo ###################################################################################################
	echo #                                     JPACKAGE CREATE THE MSI                                     #
	echo ###################################################################################################
	echo.
	echo # WiX Toolset is installed
	echo # WiX's bin path is added to PATH environment variable
    echo.
    echo # Arguments from pom.xml :
    echo # This batch's path   :"%0"
	echo # App's name          :"%1"
	echo # Current version     :"%2"
	echo # Organization's name :"%3"
	echo # Main's path         :"%4"
	echo # JRE version         :"%5"
	echo # Deployment folder   :"%6"
	echo # Build timestamp     :"%7"
    echo.
	set "jarName=%1-%2.jar"
	set "timestamp=%7"
	set "year=%timestamp:~0,4%"
	set "appNameWithTheJVM=%1_JVM"
	echo.
	echo # OUTPUT   : CLEAN
	rmdir /s /q %6 2>nul
	echo.
    echo # TARGET/INPUT   : CREATE
    cd ./target
    mkdir input
    echo.
    echo # TARGET/INPUT   : PASTE UBERJAR
    copy "%jarName%" "input\%jarName%"
	echo.
	echo # OUTPUT   : CREATING THE MSI FROM TARGET/INPUT...
	cd ../
    jpackage --input ./target/input --dest %6 --name %appNameWithTheJVM% --type msi --main-jar %jarName% --main-class org.springframework.boot.loader.launch.JarLauncher --win-shortcut --win-menu --win-menu-group %1 --java-options "-Xmx2048m -Dapp.name=%1 -Dapp.version=%2" --vendor %3 --copyright "Copyright © %year% %3" --icon src/main/resources/fr/softsf/sudokufx/images/icon.ico --app-version %2 --description "%1 %year%" --license-file LICENSE.txt --verbose
    echo.
    echo # TARGET   : THE BATCH TO LAUNCH THE UBERJAR
    cd ./target
    (
        echo @echo off
        echo     chcp 65001
        echo     color 0A
        echo     echo.
        echo     echo           ▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒
        echo     echo           ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
        echo     echo     ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓
        echo     echo  ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓
        echo     echo  ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓
        echo     echo   ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓
        echo     echo     ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓
        echo     echo         ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒
        echo     echo         ▒▒▒▒▒
        echo     echo.
        echo     echo.
        echo     timeout /t 1 /nobreak ^>nul
        echo     set JAVA_VERSION=0
        echo     for /f "tokens=3" %%%%g in ('java -version 2^^^>^^^&1 ^^^| findstr /i "version"'^) do (
        echo         set JAVA_VERSION=%%%%g
        echo     ^)
        echo     set JAVA_VERSION=%%JAVA_VERSION:"=%%
        echo     set /a JAVA_REQUIRED=%5
        echo     set FOLDER=%1
        echo     if %%JAVA_VERSION%% EQU 0 (
        echo         echo.
        echo         echo  ██ Java minimum version %%JAVA_REQUIRED%% is required to run this application.
        echo         echo  ██ Please install the latest Java JRE available at ▒▒ https://adoptium.net ▒▒.
        echo         echo.
        echo         pause
        echo         exit /b 1
        echo     ^)
        echo     if %%JAVA_VERSION%% LSS %%JAVA_REQUIRED%% (
        echo         echo.
        echo         echo  ██ A newer version of Java is required to run this application.
        echo         echo  ██ Your Java version is %%JAVA_VERSION%%, and requires version %%JAVA_REQUIRED%%.
        echo         echo  ██ Please install the latest Java JRE available at ▒▒ https://adoptium.net ▒▒.
        echo         echo.
        echo         pause
        echo         exit /b 1
        echo     ^)
        echo     if not exist %%FOLDER%% (
        echo         mkdir %%FOLDER%%
        echo         echo ████ Please do not close this window ████
        echo         echo Extracting the contents of the SudokuFX JAR file...
        echo         java -Djarmode=tools -jar %1-%2.jar extract --destination %%FOLDER%%
        echo         echo Delete the SudokuFX JAR file...
        echo         del %1-%2.jar
        echo         echo Training the SudokuFX application...
        echo         cd %%FOLDER%%
        echo         cmd /c "java -Xmx2048m -XX:ArchiveClassesAtExit=%%FOLDER%%.jsa -Dspring.profiles.active=cds -Dspring.context.exit=onRefresh -Dapp.name=%1 -Dapp.version=%2 -jar %1-%2.jar > nul"
        echo         cmd /c "java -Xmx2048m -XX:SharedArchiveFile=%%FOLDER%%.jsa -Dapp.name=%1 -Dapp.version=%2 -jar %1-%2.jar > nul"
        echo     ^)
        echo     if exist %%FOLDER%% (
        echo         echo Running the SudokuFX application...
        echo         cd %%FOLDER%%
        echo         start /min cmd /c "java -Xmx2048m -XX:SharedArchiveFile=%%FOLDER%%.jsa -Dapp.name=%1 -Dapp.version=%2 -jar %1-%2.jar > nul & exit"
        echo     ^)
        echo     exit
    ) > %1-%2.bat
    echo.
    echo # TARGET   : COPY THE BATCH AND THE UBERJAR TO OUTPUT AS A ZIP FILE
    set "zipName=%1-%2_windows.zip"

    powershell -command "& {Compress-Archive -Path '%1-%2.bat', '%jarName%' -DestinationPath '..\%6\%zipName%'}"

    echo.
    echo # OUTPUT   : THE HASH FILE
    set "msiFile=%appNameWithTheJVM%-%2.msi"
	cd ../
	cd ./%6
	(
		echo.
		CertUtil -hashfile %msiFile% MD5
	    echo.
	    CertUtil -hashfile %msiFile% SHA256
	    echo.
        CertUtil -hashfile %zipName% MD5
        echo.
	    CertUtil -hashfile %zipName% SHA256
        echo.
	) > hash_windows.txt
	cd ../
	echo.
    echo ###################################################################################################
	echo #                                             DONE !                                              #
	echo ###################################################################################################
	echo.

exit