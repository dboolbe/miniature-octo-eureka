#!/bin/sh

# gets the current directory of the script (assumes in project directory)
DIRECTORY=$(cd `dirname $0` && pwd)


# removes target directory for a clean build
rm -rf $DIRECTORY/target/

# compiles the code into taget
javac -d $DIRECTORY/target -classpath "$DIRECTORY/." $DIRECTORY/src/net/thesyndicate/io/*.java

# runs the compiled code
java -ea -classpath "$DIRECTORY/target/." net.thesyndicate.io.Multicast

#javac -d target -classpath ".:lib/*" src/*.java && java -classpath "target/.:lib/*" Server 8080
