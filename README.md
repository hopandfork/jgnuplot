#JGNUplot#
JGP JAVA GNUplot was originally created by Maximilian H. Fabricius.
He, on the official site (see <http://jgp.sourceforge.net>), explained:
> "JGNUplot is a graphical user interface for gnuplot. So why would somebody want to have a user interface for something which is so great because it has none?

> Gnuplot is a fairly mighty plotting software. But when one has to deal with a large number of datasets, the typing of the plot commands over the keyboard can become fairly tedious. Sometimes the fields in the datasets have to be combined using more complex formula. In such cases you end up with very complicated plot commands.

> Sometimes one may want to quickly add or delete a certain dataset from the plot. Or maybe pick a different color or linestyle.

> JGNUplot is a JAVA written interface which uses gnuplot as plotting engine."

##Prerequisites##
- You need to have gnuplot installed (see <http://www.gnuplot.info>)
*(It is recommended to install version 4.1 to get all features)*
- You need the Java Runtime Environment

##Installation##
1. Unzip the "tar.gz" or "zip" archive into a directory of your choice
2. Make the file jgp executeable `chmod +x jgp`
3. Execute `./jgp`. You may also start JGP with `java -cp ./classes jgp.gui.JGP`
*(Do not background the process, JGP was found to crash if backgrounded)*

##Compiling##
The source is provided along with the JAVA binaries. To compile JGP:
1. Make the file makeJGNUplot executeable `chmod +x makeJGNUplot`
2. Execute `./makeJGNUplot` or run `javac  -d classes  ./src/jgp/*.java ./src/jgp/gui/*.java`
