RockLobster
===========

A simple static Blog generator for Git hosted Markdown (plus metadata) files. 

It creates single pages, paginated pages and filtered paginated pages. In other words myblogpost.html, index.html and tags_yourtag.html.

It parses metadata at the top of your markdown files, tags: blar,blar for instance, for access in posts and plugins.

More functionality is supported through plugins.

Running (from the repository)
=============================

1. Install gradle (apt-get install gradle, homebrew install gradle or http://www.gradle.org/installation).
2. gradle clean build
3. java -jar build/libs/RockLobster.jar "git@github.com:denevell/BlogPosts.git" "outputDir/"

You also need to have singepages.template and pagination.template (actually optional) template files in your working directory. See the examples in this repository.

Then the HTML output files, single pages and paginated pages, will appear in your output directory.

Specifying the template files
=============================

There are example templates in the repository.

Single page template
--------------------

'singlepages.template' - Required file.

The template file uses the Mustache templating syntax. See the example file in this repository. Here is a sample template where you access the title metadata in your markdown file and the post content:

        	{{#attr}}{{title}}{{/attr}}
        	{{& post}}
            
Paginated page template
-----------------------

'SOMENAME.10.pagination.template' - The number refers to how many post per page.

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

Filtered paginated page template
--------------------------------

'SOMENAME_[metadata].10.pagination.template' - The same as above except the [metadata] part is replaced for all the values of the metadata, 'tags' for example.

In the case where you have [tags] in the filename and you have blogpost metadata which contains the tags 'stuff', and 'blar', you'd generated 'somename_stuff.html' and 'SOMENAME_blar.html'. 

And in the templates {{metadata_filter}} would refer to 'stuff' and 'blar' respectively.

Plugins
=======

Most things - i.e. anything other than processing a single markdown-with-metadata file, paginated files and filtered paginated files  - are achieved through plugins.

In the templates you can specify:

		{{#plugins}}TAGNAME||argument-1||argument-2{{/plugins}}

The TAGNAME will be a registered plugin, the the rest of the line will be the arguments, separated by a double vertical bar.

All the plugins live in org.denevell.rocklobster.plugins and any classes which extend Plugin will be automatically added to the list of plugins available. TODO: Ability to add your own plugin easily.

All tags plugin
---------------

This gets all the 'tags' metadata from all the blog posts and outputs them.

* Arguments 1 and 2: beginning and end of the wrapper around the whole block.
* Arguments 3 and 4: beginning and end of the wrapper around each tag
* Argument 5: The divider inbetween each element.

At argument 3 any text with [tagname] is replaced for the current tag name.

E.g:

                {{#plugins}}all-tags||<div>||</div>||<a href="category_[tagname].html">||</a>|| &#124; {{/plugins}}

TODO
====
* Sort blog posts by git date or metadata date
* ~~Specify blog url from the command line~~
* ~~Specify where to put the output html post files~~
* ~~Remove .md from output html filename~~
* ~~Optional template files found in CWD to use.~~
* ~~Capitalisation in blog attributes - make them all lowercase on parsing?~~
* ~~Ant build.xml / Gradle? - sod Eclipse~~
* Gradle task to run the jar after compilation
* ~~Pagination for index.html etc~~
* ~~Pagination filter based on blog post attributes - category_[metadata.tags].15.pages.template ?~~
* ~~Turn off pagination when specified as 0 - just set it really high, the first page will be just index.html anyway.~~
* ~~Plugins~~
 * ~~Tags plugin~~
 * Pretty date plugin 
 * Content abbreviator for index.html posts
 * Ability to easily add a new plugin
* Integrate disqus?
* Allow markdown files to be in sub folders
* Specifying leading and ending text for text around paginated number in filename
* Composting so there's a master template file which would contain either posts or paginated content?
