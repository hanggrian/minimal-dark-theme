#!/bin/bash
# Executable to create `index.html` from GitHub repository by manually inputting:
# 1. Username.
# 2. Repository ID.
# 3. Optional README file path relative to repository url. If left empty, default README will be used.

fail() {
    local message=$1

    echo
    echo $RED$message$END
    echo
    exit 1
}

END=[0m
RED=[91m
GREEN=[92m
YELLOW=[93m

if ! command -v jq &> /dev/null; then fail "jq is not installed."; fi
if ! command -v pandoc &> /dev/null; then fail "pandoc is not installed."; fi

echo
echo Enter GitHub username:
read username
echo fetching...
fullname=$(curl -s "https://api.github.com/users/$username" | jq ".name" | tr -d '"')

if [[ "$fullname" == null ]]
then
    fail "Username not found."
else
    echo Full name: $GREEN$fullname$END
fi

echo
echo Enter GitHub repository:
read repo
echo fetching...
repo_description=$(curl -s "https://api.github.com/repos/$username/$repo" | jq ".description" | tr -d '"')

if [[ "$repo_description" == null ]]
then
    echo ${YELLOW}No description.$END
else
    echo Repository description: $GREEN$repo_description$END
fi

echo
echo Enter Optional README relative path:
read repo_readme
echo fetching...

if [[ -z "$repo_readme" ]]
then
    repo_readme=$(curl -s "https://api.github.com/repos/$username/$repo/readme" | jq ".download_url" | tr -d '"')
else
    repo_readme="https://raw.githubusercontent.com/$username/$repo/$repo_readme"
fi
repo_readme_content=$(pandoc -f gfm -t html $repo_readme)

echo
echo writing...
{
echo "<!doctype html>"
echo "<html>"
echo "  <head>"
echo "    <meta charset=\"utf-8\"/>"
echo "    <meta http-equiv=\"X-UA-Compatible\" content=\"chrome=1\"/>"
echo "    <title>$repo by $fullname</title>"
echo
echo "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200\"/>"
echo "    <link rel=\"stylesheet\" href=\"stylesheets/styles.css\"/>"
echo "    <link rel=\"stylesheet\" href=\"stylesheets/styles_dark.css\" id=\"styles_dark\" disabled=\"true\"/>"
echo "    <link rel=\"stylesheet\" href=\"stylesheets/pygment_trac.css\"/>"
echo "    <meta name=\"viewport\" content=\"width=device-width\"/>"
echo "    <script>"
echo "      function toggleDark() {"
echo "        var styles_dark = document.getElementById(\"styles_dark\");"
echo "        styles_dark.disabled = !styles_dark.disabled;"
echo "      }"
echo "      if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {"
echo "        toggleDark();"
echo "      }"
echo "    </script>"
echo "    <!--[if lt IE 9]>"
echo "    <script src=\"//html5shiv.googlecode.com/svn/trunk/html5.js\"/>"
echo "    <![endif]-->"
echo "  </head>"
echo "  <body>"
echo "    <div class=\"wrapper\">"
echo "      <header>"
echo "        <h1>$repo</h1>"
echo "        <p>$repo_description</p>"
echo "        <p class=\"view\"><a href=\"http://github.com/$username/$repo\">View the Project on GitHub <small>$username/$repo</small></a></p>"
echo "        <ul>"
echo "          <li><a href=\"https://github.com/$username/$repo/zipball/master\">Download <strong>ZIP File</strong></a></li>"
echo "          <li><a href=\"https://github.com/$username/$repo/tarball/master\">Download <strong>TAR Ball</strong></a></li>"
echo "          <li><a href=\"http://github.com/$username/$repo\">Fork On <strong>GitHub</strong></a></li>"
echo "        </ul>"
echo "      </header>"
echo "      <section>"
echo "$repo_readme_content"
echo "      </section>"
echo "      <footer>"
echo "        <p><button class=\"material-symbols-outlined\" title=\"Toggle Dark Mode\" onclick=\"toggleDark()\">lightbulb</button></p>"
echo "        <p>This project is maintained by <a href=\"http://github.com/$username\">$fullname</a></p>"
echo "        <small>Hosted on GitHub Pages &mdash; Theme by <a href=\"https://github.com/hendraanggrian/minimal\">minimal</a></small>"
echo "      </footer>"
echo "    </div>"
echo "    <script src=\"javascripts/scale.fix.js\"></script>"
echo "  </body>"
echo "</html>"
} > index.html
echo done
echo

exit 0