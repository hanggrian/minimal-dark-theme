Minimal Theme CLI
=================

Instead of manually writing HTML file, `deploy_page.sh` pulls data from GitHub and automatically creates HTML file along with CSS and JS directories. When running the command, parent directory of `cli` must be unchanged.

Requirements
------------

These executables must be installed and accessible from system environment.

- [jq](https://stedolan.github.io/jq/)
- [Pandoc](https://pandoc.org/)

Options
-------

### -i INPUT

GitHub username and repository, separated by slash (e.g. `-i user/repo`).

### -o OUTPUT

Output directory, cannot be empty.

### -m MARKDOWN

Relative path to the markdown file under the repository. This flag is optional, leaving it empty will result in using repository's default README file.

### --clearoutput

Remove existing files and folder in the output directory before generating.
