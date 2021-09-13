#!/bin/sh

# gets the current directory of the script (assumes in project directory)
DIRECTORY=$(cd `dirname $0` && pwd)

# removes target directory for a clean build
rm -rf $DIRECTORY/target/

# compiles the code into taget
javac -d $DIRECTORY/target -classpath "$DIRECTORY/." $DIRECTORY/src/net/thesyndicate/io/*.java

# runs the compiled code
java -ea -classpath "$DIRECTORY/target/." net.thesyndicate.io.Receiver

# package into a jar file
mkdir $DIRECTORY/jar
jar -cfev $DIRECTORY/jar/Multicast.jar net.thesyndicate.io.Multicast -C $DIRECTORY/target net/thesyndicate/io/

# runs the package code
java -jar $DIRECTORY/jar/Multicast.jar

#javac -d target -classpath ".:lib/*" src/*.java && java -classpath "target/.:lib/*" Server 8080
