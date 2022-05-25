Generator
---------

Instead of manually writing HTML file, `generate.sh` pulls data from GitHub and automatically creates HTML file along with CSS and JS directories.

Requirements
------------

These executables must be installed and accessible from system environment.

* [jq](https://stedolan.github.io/jq)
* [Pandoc](https://pandoc.org)

Options
-------

### -i INPUT

GitHub username and repository, separated by slash (e.g. `-i user/repo`).

### -o OUTPUT

Output directory, cannot be empty.

### -m MARKDOWN

Relative path to the markdown file under the repository. This flag is optional, leaving it empty will use repository's default README file.

### --clearoutput

Remove existing files and folder in the output directory before generating.