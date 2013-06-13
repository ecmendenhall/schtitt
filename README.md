# Schtitt
>Schtitt, whose knowledge of formal math is probably about equivalent
to that of a Taiwanese kindergartner, nevertheless seemed to know what
Hopman and van der Meer and Bollettieri seemed not to know: that
locating beauty and art and magic and improvement and keys to
excellence and victory in the prolix flux of match play is not a
fractal matter of reducing chaos to pattern. Seemed intuitively to
sense that it was a matter not of reduction at all, but—perversely–of
expansion, the aleatory flutter of uncontrolled, metastatic
growth–each well-shot ball admitting of n possible responses, n^2
possible responses to those responses and on..."

Schtitt is a simple HTTP server. It is named after Gerhardt Schtitt, a
guy who lobbed a lot of practice serves.

To build, make sure you've got ant installed:
```
$ which ant
```
To build a jar, navigate to the root directory of this project and run
```
$ ant
```
To run bundled JUnit tests:
```
$ ant test
```
To run the server, navigate to build/jar and run:
```
$ java -jar HTTPServer
```
to serve files from your current working directory.

