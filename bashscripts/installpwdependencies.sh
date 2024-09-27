echo "Current Directory"
pwd
echo "Installing playwright browser binaries and os dependencies"
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"