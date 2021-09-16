#!/bin/bash

# Simple tool to build the code from the repository
# Written by Joseph Telaak

# Display title
cat /QueryGen/dev/Distributed-Automation-2/DA2/src/rsc/banners/QueryGen.txt

# Display message
echo ''
echo ''
echo ''
echo "Building QueryGenerator Code."

# Remove old jar
cd /QueryGen/
sudo rm QueryGen.jar

# Change directory to to the code and update
cd dev/Distributed-Automation-2/
sudo git pull

# Make system directory (Temporary)
cd ../..
sudo mkdir sys
sudo mkdir sys/rsc
sudo cp -r dev/Distributed-Automation-2/DA2/src/rsc/ sys/
cd dev/Distributed-Automation-2/

# Build code using the java compiler
cd DA2/src/
mkdir output
sudo javac -d output -cp ".:lib/*" "com/jtelaa/bwbot/querygen/Main.java"

# Done
echo "Done with compilation"
echo "See output directory for classes to run"

# Jar
echo "Building jar"
sudo cp manifests/querygen/manifest.txt manifest.txt
sudo jar cfm QueryGen.jar manifest.txt -C output com
sudo mv QueryGen.jar output/
sudo cp output/QueryGen.jar /QueryGen/

# Done
echo "Done building JAR"
echo "See QueryGen directory"

# Clear output
# sudo rm -r output