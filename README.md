## How to - tested on ubuntu 18.04

# Configure enviroment variables

This program uses system environment variables. To configure this variables is recommended to edit ~/.bashrc file or ~/.profile and to add the next commands at the end of file:

export CLIENT_ID="your_client_id"
export CLIENT_SECRET="your_client_secret"
export SLUG="your_instance_slug"

Close the terminal and open a new one

## Compile and run project

cd /path/to/project

# compile a testclass and put all binaries in src/classes
javac -cp .:./src/main/resources/okio-1.13.0.jar:./src/main/resources/okhttp-3.9.0.jar ./src/main/java/*.java ./src/test/TestCredential.java -d ./src/classes

# add binaries to classpath and run testclass
java -cp .:./src/main/resources/okio-1.13.0.jar:./src/main/resources/okhttp-3.9.0.jar:./src/classes TestCredential

