RockLobster
===========

Static Blog generator for Git hostsed Markdown files. It creates single pages, paginated pages and filtered paginated pages.

RUNNING
========

Run 'ant' to get all the dependencies (all 4.2MB of them...) from maven via Apache Ivy (which you also need to have installed). I'm soon moving to Gradle.

Then you can run it as a normal Java program in eclipse. 

It takes two arguments, your git repository and the output directory, i.e

    "git@github.com:denevell/BlogPosts.git" "outputDir/"
    
You also need to specify a singepages.template and pagination.template template files in your working directory. See the examples in this repository.

Then the HTML output files, single pages and paginated pages, will appear in your output directory.

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
* Pagination filter based on blog post attribute - category_[metadata.tags].15.pages.template ?
* ~~Turn off pagination when specified as 0 - just set it really high, the first page will be just index.html anyway.~~
* Plugins 
 * Tags plugin
 * Pretty date plugin 
 * Content abbreviator for index.html posts
* Integrate disqus?
* Allow markdown files to be in sub folders
