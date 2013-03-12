RockLobster
===========

A simple static Blog generator for Git-hosted Markdown (plus metadata) files.

It takes files like:

		tags: random, story
		title: And here's a story about... being free
		date: 2013-01-01 01:01:01 +500
		some-other-metadata: hiya
		
		Some Markdown text here

And creates pages like myblogpost.html, index.html and tags_YOURTAG.html.

More functionality is supported through plugins.

Features
========

* Automatically fetches posts from your git repository
* Automatically updates your blog (via git hooks)
* Page pagintion for index.html etc, and filtered pagination (e.g 'tags_stories.2.html')
* Manipulate the posts and metadata using the Mustache templating language and plugins.
* Automatically adds dates as the git commit date, unless 'date' metadata exists
* Fuzzy date metadata matching in the posts (using jchronic)
* Comments via disqus (edit the JS in the template files)
* Plugins:
 * Tags (via plugins)
 * Pretty dates (via plugins)

Running (from the repository)
=============================

1. Install gradle (apt-get install gradle, homebrew install gradle or http://www.gradle.org/installation).
2. gradle clean build
3. Create the rock.lobster configuration file:
       
               git_repo   = https://github.com/denevell/BlogPosts.git
               output_dir = somedirectory/ 

4. java -jar build/libs/RockLobster.jar 

You also need to have singepages.template and pagination.template (actually optional) template files in your working directory. See the examples in this repository.

Then the HTML output files, single pages and paginated pages, will appear in your output directory. The disqus comments will only work when the files are hosted online.

Specifying the template files
=============================

There are example templates in the repository.

Single page template
--------------------

**'singlepages.template'** - Required file.

The template file uses the Mustache templating syntax. See the example file in this repository. Here is a sample template where you access the title metadata in your markdown file and the post content:

        	{{basefilename}} // I.e. If you markdown file was sup.md it would be 'sup'.
        	{{#attr}}{{title}}{{/attr}}
        	{{& post}}
            
Paginated page template
-----------------------

**'SOMENAME.10.pagination.template'** - The number refers to how many post per page.

It will create a paginated page for all your posts with 10 pages on each page. This also includes metadata pertaining the pagination (see the example). Within your template file, your Mustache syntax will look like:

		{{#posts}}
			<div class="blog-posts-entry-title">
				{{#metadata}}{{& title}}{{/metadata}}
			</div>
			<div class="blog-posts-entry-post">
				{{& post}}
			</div>
		{{/posts}}
		...
		Page {{num_pages_current}} of {{num_pages_total}}
		{{#next_page_relative_url}}<a href="{{next_page_relative_url}}">Next</a>{{/next_page_relative_url}} 
		{{#previous_page_relative_url}}<a href="{{previous_page_relative_url}}">Previous</a>{{/previous_page_relative_url}}

With the {{#posts}} {{/posts}} block you can put in everything you put in the single page template above.

Filtered paginated page template
--------------------------------

**'SOMENAME_[metadata-key].10.pagination.template'** - This creates multiple paginated pages. [metadata-key] related to all the values of that metadata key.

In the case where you have [tags] in the filename, and you have blogpost metadata which contains the tags 'stuff' and 'blar', you'd generated 'SOMENAME_stuff.html' and 'SOMENAME_blar.html'. 

And in the templates {{metadata_filter}} would refer to 'stuff' and 'blar' respectively.

Plugins
=======

Most things - i.e. anything other than processing a single markdown-with-metadata file, paginated files and filtered paginated files  - are achieved through plugins.

In the templates you can specify:

		{{#plugins}}TAGNAME||argument-1||argument-2{{/plugins}}

The TAGNAME will be a registered plugin, the the rest of the line will be the arguments, separated by a double vertical bar.

All the plugins live in org.denevell.rocklobster.plugins and any classes which extend Plugin will be automatically added to the list of plugins available. TODO: Ability to add your own plugin easily.

'all-tags' plugin
---------------

This gets all the 'tags' metadata from all the blog posts and outputs them.

* Arguments 1 and 2: beginning and end of the wrapper around the whole block.
* Arguments 3 and 4: beginning and end of the wrapper around each tag
* Argument 5: The divider inbetween each element.
* Argument 6: Number to add to the occurrences number, which is accessbile using [occurrences] in argument 3.

At argument 3, any text with [tagname] is replaced for the current tag name, and [occurrences] is replaces for the occurrences of the tagname.

E.g:

                {{#plugins}}all-tags||<div>||</div>||<a href="category_[tagname].html">||</a>|| &#124; ||13{{/plugins}}

'single-page-tags' plugin
---------------

The outputs the tags that are passed into it, but formatted using the arguments

* Argument 1: the tags. Usually found via {{#attr}}{{tags}}{{/attr}}
* Arguments 2 and 3: beginning and end of the wrapper around the whole block.
* Arguments 4 and 5: beginning and end of the wrapper around each tag
* Argument 6: The divider inbetween each element.

At argument 4, any text with [tagname] is replaced for the current tag name.

E.g:

		{{#plugins}}single-page-tags||{{#attr}}{{tags}}{{/attr}}||<div>||</div>||<a href="category_[tagname].html">||</a>|| &#124; {{/plugins}}

'pretty-date' plugin
---------------

This uses the jchronic natural language parsing algorithm to convert text to a date. 

* Argument 1: the date string, usually {{#attr}}{{date}}{{/attr}}.
* Argument 2: the date format string for output, conforming to http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html 

		Posted: {{#plugins}}pretty-date||{{#attr}}{{date}}{{/attr}}||EEEE d MMMM yyyy, h:ma{{/plugins}}

Automatic publishing
====

If you setup your web server to run the Java jar file as a cgi binary, then you can use git hooks to automatically recreate your blog on each new commit.

1. Setup your HTTPD server to accept cgi binary files. (See [this](http://httpd.apache.org/docs/2.2/howto/cgi.html) for apache)
2. Place the jar file with (the 'rock.lobster' configuration file and the template files) in a directory and create a **executable** shell script in the same directory:

		#!/bin/sh
		
		JAVA="/usr/bin/java"
		$JAVA -jar RockLobster.jar

3. Ensure this directory has write permissions for the HTTPD user (www-data in Debian's case).
4. Create a git hook on the location of your markdown repository to call this binary.  	

Release plan
====

0.8
* ~~Sort blog posts by git date or metadata date~~
* ~~Specify blog url from the command line~~
* ~~Specify where to put the output html post files~~
* ~~Remove .md from output html filename~~
* ~~Optional template files found in CWD to use.~~
* ~~Capitalisation in blog attributes - make them all lowercase on parsing?~~
* ~~Ant build.xml / Gradle? - sod Eclipse~~
* ~~Pagination for index.html etc~~
* ~~Pagination filter based on blog post attributes - category_[metadata.tags].15.pages.template ?~~
* ~~Turn off pagination when specified as 0 - just set it really high, the first page will be just index.html anyway.~~
* ~~Integrate disqus?~~
* ~~Plugins~~
 * ~~Tags plugin~~
 * ~~Plugin: Pretty date~~
 * ~~Plugin: Single post tags~~
 * ~~Ability to easily add a new plugin~~
* ~~Log problems especially for problems parsing the date with jchronic, log4j?~~

0.9
* ~~Allow '.', and '..' for the output directory.~~
* Upload to mvnrepository
* ~~Automatically call binary from github hooks~~
* Better disqus integration instructions
* Only parse .md files -- configurable

1.0
* Themes
* Gradle task to run the jar after compilation
* Allow markdown files to be in sub folders
* Instructions (plus dev package?) for plugin development.

1.0.x
* Turn off looking for git repo updates
* Specifying leading and ending text for text around paginated number in filename

1.x
* Compositing so there's a master template file which would contain either posts or paginated content?
* Refactor blog parsing code to be less procedural
* Support YAML metdata to help converting from Jekyll?

Plugins:
* Plugin: Content abbreviator for index.html posts
* Sort tags alphabetically in tags plugins

