# jGNUplot #
[![Build Status](https://travis-ci.org/hopandfork/jgnuplot.svg?branch=master)](https://travis-ci.org/hopandfork/jgnuplot)
[![Coverage](https://codecov.io/gh/hopandfork/jgnuplot/branch/master/graphs/badge.svg?branch=master)](https://codecov.io/gh/hopandfork/jgnuplot)

jGNUplot is a graphical frontend for *gnuplot*, written in Java. So why would
somebody want to have a GUI for something which is so great because it has
none?

Gnuplot is a fairly mighty plotting software. But when one has to deal with a
large number of datasets, typing the plot commands over the keyboard
can become fairly tedious. 
Sometimes one may want to quickly add or delete a certain dataset from the
plot, or just pick a different color or linestyle, getting immediate visual
feedback.

jGNUPlot hasn't been actively mantained for several years; it was ported
on GitHub in order to refactor the project, and to develop new
features that will be defined in time. 

## Installation ##
### Prerequisites ###
- [gnuplot](http://www.gnuplot.info).
- a Java Runtime Environment (7+)

### Compiling from source  ###
jGNUplot can be built using *Maven*. Enter the project root directory and run:

	mvn clean install package

This command will produce a JAR file in `target` directory.

### Installing from a release archive ###
It is enough to extract the "tar.gz" or "zip" archive into a directory of your choice.

## Usage ##
Having downloaded (and compiled) jGNUplot, you can run it through the `jgp`
script available in the project's root directory:

	chmod +x jgp
	./jgp


## Contributing ##
If you want to contribute in the development of jGNUplot, don't forget to check
out our [CONTRIBUTING](https://github.com/hopandfork/jgnuplot/blob/master/CONTRIBUTING.md)
page.

## Credits ##
jGNUplot has been developed up to version 0.1.2 by Maximilian H. Fabricius, who
originally released the source code on
[SourceForge](http://jgp.sourceforge.net).

The project is now actively mantained by 
[Hop and Fork](https://www.hopandfork.org) Open Source dev team, and released
under the terms of GPLv2 license.

We rely on [substance](https://github.com/kirill-grouchnikov/substance) for
improving the look-and-feel of the UI. This library is released under the terms of
 BSD 3-clause license, as reported in `LICENSE-3D-PARTY.txt`.
