@echo off
	chcp 65001
	color 0A
	:: WINDOWS GENERATING LICENSE.TXT
	title GENERATING LICENSE.TXT
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
	echo ####################################################################################################
	echo #                                      GENERATING LICENSE.TXT                                      #
	echo ####################################################################################################
	echo.
    if not exist LICENSE_TO_EDIT.txt (
        echo Missing: LICENSE_TO_EDIT.txt
    ) else if not exist target\generated-sources\license\THIRD-PARTY.txt (
        echo Missing: THIRD-PARTY.txt
    ) else (
        del /f /q LICENSE.txt >nul 2>&1
        copy /b LICENSE_TO_EDIT.txt + target\generated-sources\license\THIRD-PARTY.txt LICENSE.txt >nul
        echo LICENSE.txt successfully generated.
    )
	echo.
    echo ####################################################################################################
	echo #                                              DONE !                                              #
	echo ####################################################################################################
	echo.

exit