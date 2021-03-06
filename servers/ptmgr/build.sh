#!/bin/bash

# Simple tool to build the code from the repository
# Written by Joseph Telaak

# Display title
cat /QueryGen/dev/Distributed-Automation-2/DA2/src/rsc/banners/Redemption.txt

# Display message
echo ''
echo ''
echo ''
echo "Building Point Manager Code."

# Remove old jar
cd /PtMgr/
sudo rm PtMgr.jar

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
sudo javac -d output -cp ".:lib/*" "com/jtelaa/bwbot/bw_manager/Main.java"

# Done
echo "Done with compilation"
echo "See output directory for classes to run"

# Jar
echo "Building jar"
sudo cp manifests/bw_manager/manifest.txt manifest.txt
sudo jar cfm PtMgr.jar manifest.txt -C output com
sudo mv PtMgr.jar output/
sudo cp output/PtMgr.jar /PtMgr/

# Done
echo "Done building JAR"
echo "See PtMgr directory"

# Clear output
# sudo rm -r output