RockLobster
===========

Incredibly simple static Blog generator for Git hosted Markdown (plus metadata) files. 

It creates single pages, paginated pages and filtered paginated pages.

It parses metadata at the top of your markdown files, tags: blar,blar for instance, for access in posts and plugins.

More functionality is supported through plugins (TODO).

Running (from the repository)
=============================

1. Install gradle (apt-get install gradle, or homebrew install gradle or http://www.gradle.org/installation).
2. gradle clean build
3. java -jar build/libs/RockLobster.jar "git@github.com:denevell/BlogPosts.git" "outputDir/"

You also need to have singepages.template and pagination.template (actually optional) template files in your working directory. See the examples in this repository.

Then the HTML output files, single pages and paginated pages, will appear in your output directory.

Specifying the template files
=============================

Single page template
--------------------

You need one singlepages.template file in your current working directory. It uses the Mustache templating syntax. See the example file in this repository. Here is a sample template where you access the title metadata in your markdown file and the post content:

        {{#attr}}{{title}}{{/attr}}
        {{& post}}
            
Paginated page template
-----------------------

If you have any template files of the format 'somename.10.pagination.template', then it will create a paginated page for all your posts with 10 pages on each page. See the example file in this repository. WIthin your template file, your Mustache syntax will look like:

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

A filtered paginated file looks like 'somename_[metadata].10.pagination.template'. This is the same as above, but in the tempalte you can access {{metadata_filter}} which refers the the value for which the [metadata] tag refered. 

In the case where you have [tags] in the filename and you have tags metadata which contains the tag 'stuff', you'd generated 'somename_stuff.10.pagination.template' and {{metadata_filter}} would refer to 'stuff'. You'd generate paginated files for all your tags.

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
* Plugins 
 * Tags plugin
 * Pretty date plugin 
 * Content abbreviator for index.html posts
* Integrate disqus?
* Allow markdown files to be in sub folders
* Specifying leading and ending text for text around paginated number in filename
