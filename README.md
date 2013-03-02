RockLobster
===========

Blog generator for Markdown files on Github (Hacked up)

Run 'ant' to get all the dependencies (all 4.2MB of them...) from maven via Apache Ivy (which you also need to have installed).

Then just run the program as normal Java file, and it will create HTML pages for the markdown files on my blog.

TODO
====
* Sort blog posts by git date or metadata date
* ~~Specify blog url from the command line~~
* ~~Specify where to put the output html post files~~
* ~~Remove .md from output html filename~~
* ~~Optional template files found in CWD to use.~~
* ~~Capitalisation in blog attributes - make them all lowercase on parsing?~~
* Ant build.xml / Gradle? - sod Eclipse
* ~~Pagination for index.html etc~~
* Pagination filter based on blog post attribute
* ~~Turn off pagination when specified as 0 - just set it really high, the first page will be just index.html anyway.~~
* Plugins 
 * Tags plugin
 * Pretty date plugin 
* Integrate disqus?
