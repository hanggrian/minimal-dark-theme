package com.hendraanggrian.pages.minimal.resources

private const val HEADER_UL_WIDTH = 270

fun getMainCss(accent: String, accentLightHover: String, accentDarkHover: String, headerButtonSize: Int) = """
    :root {
      --background: #fafafa; /* grey_50 */

      --accent: $accent;
      --accent-hover: $accentLightHover;

      --text-heavy: #212121; /* grey_900 */
      --text-medium: #424242; /* grey_800 */
      --text-body: #616161; /* grey_700 */
      --text-caption: #757575; /* grey_600 */
      --text-caption-hover: #494949; /* grey_600_dark */
      --text-code: #eeeeee; /* grey_200 */

      --border-regular: #e0e0e0; /* grey_300 */
      --border-bold: #bdbdbd; /* grey_400 */

      --button-background: #eeeeee; /* grey_200 */
      --button-background-active: #bcbcbc; /* grey_200_dark */
      --button-border: #bdbdbd; /* grey_400 */
      --button-text1: #9e9e9e; /* grey_500 */
      --button-text1-hover: #707070; /* grey_500_dark */
      --button-text2: #212121; /* grey_900 */
      --button-text2-hover: #000000; /* grey_900 */

      --theme-toggle-image: url('../images/dark_mode.svg');
    }

    :root.theme-dark {
      --background: #212121; /* grey_900*/

      --accent-hover: $accentDarkHover;

      --text-heavy: #fafafa; /* grey_50 */
      --text-medium: #f5f5f5; /* grey_100 */
      --text-body: #bdbdbd; /* grey_400 */
      --text-caption: #9e9e9e; /* grey_500 */
      --text-caption-hover: #cfcfcf; /* grey_500_light */
      --text-code: #424242; /* grey_800 */

      --border-regular: #424242; /* grey_800 */
      --border-bold: #616161; /* grey_700 */

      --table-border: #616161; /* grey_700 */

      --theme-toggle-image: url('../images/light_mode.svg');
    }

    body {
      background-color: var(--background);
      padding: 50px;
      font: 14px/1.5 -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Fira Sans', 'Droid Sans', 'Helvetica Neue', Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
      color: var(--text-body);
      font-weight: 400;
    }

    h1, h2, h3, h4, h5, h6 {
      color: var(--text-heavy);
      margin: 0 0 20px;
    }

    p, ul, ol, table, pre, dl {
      margin: 0 0 20px;
    }

    h1, h2, h3 {
      line-height: 1.1;
    }

    h1 {
      font-size: 28px;
      font-weight: 500;
    }

    h2 {
      color: var(--text-heavy);
      font-weight: 500;
    }

    h3, h4, h5, h6 {
      color: var(--text-heavy);
      font-weight: 500;
    }

    a {
      color: var(--accent);
      text-decoration: none;
    }

    a:hover {
      color: var(--accent-hover);
    }

    a small {
      font-size: 11px;
      color: var(--text-caption);
      margin-top: -0.3em;
      display: block;
    }

    a:hover small {
      color: var(--text-caption-hover);
    }

    p small {
      color: var(--text-caption);
    }

    .wrapper {
      width: 860px;
      margin: 0 auto;
    }

    blockquote {
      border-left: 4px solid var(--border-regular);
      margin: 0;
      padding: 0 0 0 20px;
      font-style: italic;
    }

    blockquote p {
      color: var(--text-caption);
    }

    code, pre {
      font-family: Monaco, Bitstream Vera Sans Mono, Lucida Console, Terminal, Consolas, Liberation Mono, DejaVu Sans Mono, Courier New, monospace;
      background-color: var(--text-code);
    }

    pre {
      padding: 8px 15px;
      background: var(--text-code);
      border-radius: 5px;
      overflow-x: auto;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th, td {
      text-align: left;
      padding: 5px 10px;
      border-bottom: 1px solid var(--border-bold);
    }

    dt {
      color: var(--text-medium);
      font-weight: 500;
    }

    th {
      color: var(--text-medium);
    }

    img {
      max-width: 100%;
    }

    header {
      width: ${HEADER_UL_WIDTH}px;
      float: left;
      position: fixed;
      -webkit-font-smoothing: subpixel-antialiased;
    }

    header ul {
      list-style: none;
      height: 40px;
      padding: 0;
      background: var(--button-background);
      border-radius: 5px;
      border: 1px solid var(--button-border);
      width: 270px;
    }

    header li {
      width: ${if (headerButtonSize == 0) HEADER_UL_WIDTH else HEADER_UL_WIDTH / headerButtonSize - 1}px;
      float: left;
      border-right: 1px solid var(--button-border);
      height: 40px;
    }

    header li:first-child a {
      border-radius: 5px 0 0 5px;
    }

    header li:last-child a {
      border-radius: 0 5px 5px 0;
    }

    header ul a {
      line-height: 1;
      font-size: 11px;
      color: var(--button-text1);
      display: block;
      text-align: center;
      padding-top: 6px;
      height: 34px;
    }

    header ul a:hover {
      color: var(--button-text1-hover);
    }

    header ul a:active {
      background-color: var(--button-background-active);
    }

    strong {
      color: var(--text-heavy);
      font-weight: 500;
    }

    header ul li + li + li {
      border-right: none;
      width: 89px;
    }

    header ul a strong {
      font-size: 14px;
      display: block;
      color: var(--button-text2);
    }

    section {
      width: 500px;
      float: right;
      padding-bottom: 50px;
    }

    small {
      font-size: 11px;
    }

    hr {
      border: 0;
      background: var(--border-bold);
      height: 1px;
      margin: 0 0 20px;
    }

    footer {
      width: 270px;
      float: left;
      position: fixed;
      bottom: 50px;
      -webkit-font-smoothing: subpixel-antialiased;
    }

    footer button {
      height: 40px;
      padding: 0;
      background: var(--button-background);
      border-radius: 5px;
      border: 1px solid #e0e0e0;
      width: 40px;
      color: var(--button-text2);
      justify-content: center;
    }

    footer button:hover {
      color: var(--button-text2-hover);
    }

    footer button:active {
      background-color: var(--button-background-active);
    }

    #theme-toggle {
      content: var(--theme-toggle-image);
    }

    @media print, screen and (max-width: 960px) {
      div.wrapper {
        width: auto;
        margin: 0;
      }

      header, section, footer {
        float: none;
        position: static;
        width: auto;
      }

      header {
        padding-right: 320px;
      }

      footer {
        padding-right: 60px;
      }

      section {
        border: 1px solid #e0e0e0;
        border-width: 1px 0;
        padding: 20px 0;
        margin: 0 0 20px;
      }

      header a small {
        display: inline;
      }

      header ul {
        position: absolute;
        right: 50px;
        top: 52px;
      }

      footer button {
        position: fixed;
        right: 50px;
        bottom: 52px;
      }
    }

    @media print, screen and (max-width: 720px) {
      body {
        word-wrap: break-word;
      }

      header {
        padding: 0;
      }

      header ul, header p.view {
        position: static;
      }

      pre, code {
        word-wrap: normal;
      }
    }

    @media print, screen and (max-width: 480px) {
      body {
        padding: 15px;
      }

      header ul {
        width: 99%;
      }

      header li, header ul li + li + li {
        width: 33%;
      }

      footer button {
        position: fixed;
        right: 25px;
        bottom: 26px;
      }
    }

    @media print {
      body {
        padding: 0.4in;
        font-size: 12pt;
        background-color: white;
        color: black;
      }
    }
""".trimIndent()

val pygment_trac_css = """
    .highlight  { background: #ffffff; }
    .highlight .c { color: #999988; font-style: italic } /* Comment */
    .highlight .err { color: #a61717; background-color: #e3d2d2 } /* Error */
    .highlight .k { font-weight: bold } /* Keyword */
    .highlight .o { font-weight: bold } /* Operator */
    .highlight .cm { color: #999988; font-style: italic } /* Comment.Multiline */
    .highlight .cp { color: #999999; font-weight: bold } /* Comment.Preproc */
    .highlight .c1 { color: #999988; font-style: italic } /* Comment.Single */
    .highlight .cs { color: #999999; font-weight: bold; font-style: italic } /* Comment.Special */
    .highlight .gd { color: #000000; background-color: #ffdddd } /* Generic.Deleted */
    .highlight .gd .x { color: #000000; background-color: #ffaaaa } /* Generic.Deleted.Specific */
    .highlight .ge { font-style: italic } /* Generic.Emph */
    .highlight .gr { color: #aa0000 } /* Generic.Error */
    .highlight .gh { color: #999999 } /* Generic.Heading */
    .highlight .gi { color: #000000; background-color: #ddffdd } /* Generic.Inserted */
    .highlight .gi .x { color: #000000; background-color: #aaffaa } /* Generic.Inserted.Specific */
    .highlight .go { color: #888888 } /* Generic.Output */
    .highlight .gp { color: #555555 } /* Generic.Prompt */
    .highlight .gs { font-weight: bold } /* Generic.Strong */
    .highlight .gu { color: #800080; font-weight: bold; } /* Generic.Subheading */
    .highlight .gt { color: #aa0000 } /* Generic.Traceback */
    .highlight .kc { font-weight: bold } /* Keyword.Constant */
    .highlight .kd { font-weight: bold } /* Keyword.Declaration */
    .highlight .kn { font-weight: bold } /* Keyword.Namespace */
    .highlight .kp { font-weight: bold } /* Keyword.Pseudo */
    .highlight .kr { font-weight: bold } /* Keyword.Reserved */
    .highlight .kt { color: #445588; font-weight: bold } /* Keyword.Type */
    .highlight .m { color: #009999 } /* Literal.Number */
    .highlight .s { color: #d14 } /* Literal.String */
    .highlight .na { color: #008080 } /* Name.Attribute */
    .highlight .nb { color: #0086B3 } /* Name.Builtin */
    .highlight .nc { color: #445588; font-weight: bold } /* Name.Class */
    .highlight .no { color: #008080 } /* Name.Constant */
    .highlight .ni { color: #800080 } /* Name.Entity */
    .highlight .ne { color: #990000; font-weight: bold } /* Name.Exception */
    .highlight .nf { color: #990000; font-weight: bold } /* Name.Function */
    .highlight .nn { color: #555555 } /* Name.Namespace */
    .highlight .nt { color: #000080 } /* Name.Tag */
    .highlight .nv { color: #008080 } /* Name.Variable */
    .highlight .ow { font-weight: bold } /* Operator.Word */
    .highlight .w { color: #bbbbbb } /* Text.Whitespace */
    .highlight .mf { color: #009999 } /* Literal.Number.Float */
    .highlight .mh { color: #009999 } /* Literal.Number.Hex */
    .highlight .mi { color: #009999 } /* Literal.Number.Integer */
    .highlight .mo { color: #009999 } /* Literal.Number.Oct */
    .highlight .sb { color: #d14 } /* Literal.String.Backtick */
    .highlight .sc { color: #d14 } /* Literal.String.Char */
    .highlight .sd { color: #d14 } /* Literal.String.Doc */
    .highlight .s2 { color: #d14 } /* Literal.String.Double */
    .highlight .se { color: #d14 } /* Literal.String.Escape */
    .highlight .sh { color: #d14 } /* Literal.String.Heredoc */
    .highlight .si { color: #d14 } /* Literal.String.Interpol */
    .highlight .sx { color: #d14 } /* Literal.String.Other */
    .highlight .sr { color: #009926 } /* Literal.String.Regex */
    .highlight .s1 { color: #d14 } /* Literal.String.Single */
    .highlight .ss { color: #990073 } /* Literal.String.Symbol */
    .highlight .bp { color: #999999 } /* Name.Builtin.Pseudo */
    .highlight .vc { color: #008080 } /* Name.Variable.Class */
    .highlight .vg { color: #008080 } /* Name.Variable.Global */
    .highlight .vi { color: #008080 } /* Name.Variable.Instance */
    .highlight .il { color: #009999 } /* Literal.Number.Integer.Long */

    .type-csharp .highlight .k { color: #0000FF }
    .type-csharp .highlight .kt { color: #0000FF }
    .type-csharp .highlight .nf { color: #000000; font-weight: normal }
    .type-csharp .highlight .nc { color: #2B91AF }
    .type-csharp .highlight .nn { color: #000000 }
    .type-csharp .highlight .s { color: #A31515 }
    .type-csharp .highlight .sc { color: #A31515 }
""".trimIndent()
