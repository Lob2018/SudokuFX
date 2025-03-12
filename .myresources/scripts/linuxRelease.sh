#!/bin/bash

# LINUX RELEASE
echo -e "\033[0;32m"
echo "          ▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"
echo "    ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓"
echo " ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓"
echo " ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓"
echo "  ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓"
echo "    ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓"
echo "        ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒"
echo "        ▒▒▒▒▒"
echo -e "\033[0m"

echo "###################################################################################################"
echo "#                                     JPACKAGE CREATE THE DEB                                     #"
echo "###################################################################################################"

echo "# Arguments from pom.xml :"
echo "# This script's path   : $0"
echo "# App's name           : $1"
echo "# Current version      : $2"
echo "# Organization's name  : $3"
echo "# Main's path          : $4"
echo "# JRE version          : $5"
echo "# Deployment folder    : $6"

jarName="$1-$2.jar"
year=$(date +%Y)
appNameWithTheJVM="${1}-JVM"

echo "# OUTPUT   : CLEAN"
rm -rf "$6" 2>/dev/null

echo "# TARGET/INPUT   : CREATE"
cd ./target || exit
mkdir -p input

echo "# TARGET/INPUT   : PASTE UBERJAR"
cp "$jarName" "input/$jarName"

echo "# OUTPUT   : CREATING THE DEB FROM TARGET/INPUT..."
cd ..
jpackage --input ./target/input --dest "$6" --name "$appNameWithTheJVM" --type deb --main-jar "$jarName" --main-class org.springframework.boot.loader.launch.JarLauncher --linux-shortcut --linux-menu-group "$1" --java-options "-Xmx2048m -Dapp.name=$1 -Dapp.version=$2" --vendor "$3" --copyright "Copyright © $year $3" --icon src/main/resources/fr/softsf/sudokufx/images/Sudoku.png --app-version "$2" --description "$1 $year" --license-file LICENSE.txt --verbose

echo "# TARGET   : THE SHELL SCRIPT TO LAUNCH THE UBERJAR"
cd ./target || exit
cat << EOF > "$1-$2.sh"
#!/bin/bash
echo -e "\033[0;32m"
echo "          ▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒"
echo "         ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"
echo "    ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓"
echo " ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓"
echo " ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓"
echo "  ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓"
echo "    ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓"
echo "        ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒"
echo "       ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒"
echo "        ▒▒▒▒▒"
echo
echo
echo "████ Please do not close this window ████"
echo
echo -e "\033[0m"

sleep 1

JAVA_VERSION=\$(java -version 2>&1 | awk -F '"' '/version/ {print \$2}' | cut -d'.' -f1)
JAVA_REQUIRED=$5
FOLDER=$1

if [[ -z "\$JAVA_VERSION" ]]; then
    echo " ██ Java minimum version $5 is required to run this application."
    echo " ██ Please install the latest Java Adoptium Temurin JRE available at ▒▒ https://adoptium.net/temurin/releases/?package=jre ▒▒."
    echo " ████ You can now close this window ████"
    exit 1
fi

if [[ "\$JAVA_VERSION" < "$5" ]]; then
    echo " ██ A newer version of Java is required to run this application."
    echo " ██ Your Java version is \$JAVA_VERSION, and requires version $5."
    echo " ██ Please install the latest Java Adoptium Temurin JRE available at ▒▒ https://adoptium.net/temurin/releases/?package=jre ▒▒."
    echo " ████ You can now close this window ████"
    exit 1
fi

if [[ ! -d "$1" ]]; then
    mkdir -p "$1"
    echo "Extracting the contents of the SudokuFX JAR file..."
    java -Djarmode=tools -jar "$1-$2.jar" extract --destination "$1"
    echo "Delete the SudokuFX JAR file..."
    rm "$1-$2.jar"
    echo "Training the SudokuFX application..."
    cd "$1" || exit
    java -Xmx2048m -XX:ArchiveClassesAtExit="$1.jsa" -Dspring.profiles.active=cds -Dspring.context.exit=onRefresh -Dapp.name="$1" -Dapp.version="$2" -jar "$1-$2.jar" > /dev/null && \
    java -Xmx2048m -XX:SharedArchiveFile="$1.jsa" -Dapp.name="$1" -Dapp.version="$2" -jar "$1-$2.jar" > /dev/null &
fi

if [[ -d "$1" ]]; then
    echo "Running the SudokuFX application..."
    cd "$1" || exit
    java -Xmx2048m -XX:SharedArchiveFile="$1.jsa" -Dapp.name="$1" -Dapp.version="$2" -jar "$1-$2.jar" > /dev/null &
fi
EOF

chmod +x "$1-$2.sh"

echo "# TARGET   : COPY THE SHELL SCRIPT AND THE UBERJAR TO OUTPUT AS A TAR FILE"
tarName="$1-$2_linux.tar.gz"
tar -czf "../$6/$tarName" "$1-$2.sh" "$jarName"

echo "# OUTPUT   : THE HASH FILE"
debFile="${appNameWithTheJVM,,}_${2}_amd64.deb"
cd "../$6" || exit
{
    echo
    md5sum --tag "$debFile"
    echo
    sha256sum --tag "$debFile"
    echo
    md5sum --tag "$tarName"
    echo
    sha256sum --tag "$tarName"
    echo
} > hash_linux.txt
cd ..

echo "###################################################################################################"
echo "#                                             DONE !                                              #"
echo "###################################################################################################"
