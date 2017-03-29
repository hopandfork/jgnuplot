# jGNUplot #

JGNUplot is a graphical user interface for gnuplot. So why would somebody
want to have a user interface for something which is so great because it has
none?

Gnuplot is a fairly mighty plotting software. But when one has to deal with a
large number of datasets, the typing of the plot commands over the keyboard
can become fairly tedious. Sometimes the fields in the datasets have to be
combined using more complex formula. In such cases you end up with very
complicated plot commands.

Sometimes one may want to quickly add or delete a certain dataset from the
plot. Or maybe pick a different color or linestyle.

jGNUplot is a JAVA written interface which uses gnuplot as plotting engine.

jGNUPlot hasn't been actively mantained for several years; it was ported
on GitHub in order to migrate the GUI from AWT to Swing, and to develop new
features that will be defined in time.

## Installation ##
### Prerequisites ###
- A working installation of [gnuplot](http://www.gnuplot.info).
- Java Runtime Environment (5+)

### Compiling from source  ###
jGNUplot can be built using *Maven*. Enter the root directory and run:

	mvn clean install package

### Installing from a release archive ###
1. Unzip the "tar.gz" or "zip" archive into a directory of your choice
2. Make the file `jgp` executable: `chmod +x jgp`
3. Execute `./jgp`. You may also start JGP with `java -cp <JARFILE.jar>
jgp.gui.JGP`

## Usage ##
### Plotting a function ###
- Start jGNUplot using the script

```
./jgp
```

- Click on **Add**.
- Select **function**, replace **1:2** in the function field by a function like
  **sin(x)**.
- Enter a title and click **Ok**.
- Click on **Plot**, a sine should appear on your screen. If not, check that
  gnuplot is installed and that it is in your path. 

### Plotting a datafile ###
- Create an ASCII data file: using a text editor, create two colums comma
  separated data.
- Click on **Add**.
- Make sure that **file** is selected, enter the path to the file or use the
  file browser in order to search for it (button next to the *file* textfield).
- Enter a the datastring **1:2** into the datastring textfield (it should
  actually be already there).
- Enter a title and click **Ok**.
- Click on **Plot**, and the datafile should appear on your screen. 

### Next steps ###
Click on the **preview plotsting** button. It lets you examine the plotstring
which is passed to gnuplot. It might be a good idea to start becoming familiar
with the commands in the plot string, i.e. reading the gnuplot manual or
starting gnuplot using the help command.

## Contributing ##
If you want to contribute in the development of jGNUplot, don't forget to check
out our [CONTRIBUTING](https://github.com/hopandfork/jgnuplot/blob/master/CONTRIBUTING.md)
page.

## Credits ##
jGNUplot has been developed up to version 0.1.2 by Maximilian H. Fabricius, who
originally released the source code on
[SourceForge](http://jgp.sourceforge.net).

The project is now actively mantained by 
[Hop and Fork](https://www.hopandfork.org) Open Source dev team.
