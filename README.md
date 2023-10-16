Click [here](https://github.com/KenyaSaitoh/brain2doc/blob/main/README-ja.md) for the Japanese page.

# brain2doc

brain2doc is a tool that generates documentation from source code in bulk using the OpenAI API.

# Features

- Generating documentation from source code using the OpenAI API does not require an interaction like Chat GPT. A single API call can yield effective answers, making it possible to efficiently generate documentation in bulk with this tool.
- You can specify a single file as the source, or even a directory. In the case of a directory, files stored there will be processed recursively. If you specify a ZIP file, the contents of the ZIP file will be processed recursively.
- The generated results are output in Markdown format. For directories or ZIP files, the documentation generated from each source code will be concatenated into one Markdown file.
- The supported source code types include:
  - **Java** source code (with the ".java" extension)
  - **JavaScript** or **TypeScript** source code (with ".js" or ".ts" extensions)
  - **Python** source code (with the ".py" extension)
  - **SQL** code (with the ".sql" extension)
  - **Page files** (with extensions "page", ".html", ".htm", ".xhtml", ".jsp")
  - **Shell scripts** (with ".sh", ".bash", ".ksh", ".bash" extensions)
  - Other general-purpose resources
- The supported generation processes include, with the results output in Markdown format:
  - **Specifications**
  - **Summary**
  - **Review results & improvement suggestions**
  - **Various tables (constants, messages, REST APIs, etc.)**
  - Other general-purpose outputs (default)
- The API Key is set in the environment variable "OPENAI_API_KEY", but it can also be specified as a command parameter.
- If an error occurs due to token limit during API call, the source can be automatically split into appropriate sizes and resent.
- If a rate limit error occurs during an API call, it will automatically retry after a set interval.
- During API calls, parameters such as connection timeout duration, read timeout duration, retry count, retry interval, and proxy server can be specified.
- By default, the OpenAI model is "gpt-4", but you can switch to models like "gpt-3.5-turbo" using a parameter.
- The output size can be selected from four options: small, medium, large, or unlimited.
- It's possible to filter target source files by name using regular expressions.
- The default language is Japanese, but English is also supported.

# Requirement

To operate this tool, you need an environment with Java 11 or higher.

# Installation

The only file required to run brain2doc is a JAR file, which is packaged with all its dependencies.
Please download the latest version from the releases directory and use it.

# Usage

```
java -jar <path-of-your-choice>/brain2doc.jar [source] [options]
```

- For [source], specify the file or directory of the source to be read (mandatory).
  - If a directory is specified, all files are processed recursively.
  - If a ZIP file is specified, the contents are also processed recursively.

The available options are:

```
--help
    Display how to specify parameters.

--url
    Specify the API URL (default is "https://api.openai.com/v1/chat/completions").

--model           
    Specify the gpt model (default is "gpt-4").

--apikey
    Specify the API key (if not specified, it's fetched from the environment variable "OPENAI_API_KEY").

--resource
    Specify the type of input resource (default is "others").
    Available types include:
    java   : Java source code (targets files with ".java" extension)
    js     : JavaScript or TypeScript source code (targets files with ".js", ".ts" extensions)
    python : Python source code (targets files with ".py" extension)
    sql    : SQL code (targets files with ".sql" extension)
    page   : Page files (targets files with "page", ".html", ".htm", ".xhtml", ".jsp" extensions)
    shell  : Shell script (targets files with ".sh", ".bash", ".ksh", ".bash" extensions)
    others : General resources other than the above

--gen
    Specify what you want to generate from the resource ("--gen" and "--gen-table" are mutually exclusive).
    Available types are:
    spec    : Specifications
    summary : Summary
    review  : Review results & improvement suggestions
    others  : General outputs other than the above (default when neither "--gen" nor "--gen-table" is specified)

--gen-table
    If you want to generate a list from the resource, specify its name ("--gen" and "--gen-table" are mutually exclusive).

--fields
    When generating a list from the resource, enumerate its column names (mandatory when using "--gen-table").

--output-scale
    Specify the desired size of the output (default is "nolimit").
    Available sizes are:
    small   : Small size (approximately 50 characters)
    medium  : Medium size (approximately 200 characters)
    large   : Large size (approximately 500 characters)
    nolimit : No limit

--regex
    Specify a regular expression to filter the source to be read (optional).

--dest
    Specify the full path of the directory or file name where the Markdown file will be output.
    The default file name format is "brain2doc-{resource-type}-{output-type}-{yMMddHHmmss}.md", for example: "brain2doc-java-review-20230924104914.md".
    If not specified, output will be in the same directory as the source file.

--lang
    Specify the input/output language (default is Japanese).
    Options are:
    ja (Japanese), en (English).

--template
    Specify the full path of a custom template file (optional).
    Only differences from the default template will be overwritten.

--max-split-count
    Specify the maximum number of file splits.
    If token or rate limit is exceeded, files are automatically split.
    If the number of splits exceeds this option, the source file processing is skipped (default is 10).

--proxyURL
    Specify the URL of the proxy server to use when making API calls.

--connect-timeout
    Specify the connection timeout duration in milliseconds (default is 5000).

--read-timeout
    Specify the read timeout duration in milliseconds (default is 10000).

--retry-count
    Specify the number of retries in the event of an error (default is 3).

--retry-interval
    Specify the retry interval in seconds (default is 5).

--auto-split
    When this option is specified, if a single API call reaches the token limit, it will automatically switch to a mode that splits the data into appropriate sizes and resends it.
    If not specified, the relevant source will be skipped.
```

# Example input command

```
java -jar brain2doc-0.1.1.jar \
c://src-target/jackson/src/main/java/com/fasterxml/jackson/core/base \
--resource java \
--gen spec \
--dest C:/tmp/output \
--auto-split
```

# Console Display Contents

When you launch this tool, the following information will be displayed on the console:

1. **PROMPT CONTENT (without source)**
   This shows the content that will be input into the prompt. The source files are not displayed here (although in actual use, the source will be included in the prompt).

2. **PROGRESS**
   The progress of each source file is displayed in real-time. In auto-split mode, if a token limit or rate limit is exceeded, "[FILE_SPLIT]" will be displayed and the process will continue.

3. **REPORT**
   Once all file calls are completed, a report will be output. The report includes:
   (1) Source file name
   (2) Status
   (3) Number of request tokens
   (4) Number of response tokens
   (5) Processing time

Here's a console display example:

```
##############################
#                            #
#    Welcome to Brain2doc    #
#                            #
##############################

### PROMPT CONTENT (without source)

You are a "Professional Engineer".

[Constraints]
Please answer in English.
Keep answers within 50 characters.
For Markdown headings, use level 2 for titles and level 4 or higher for others.

[Instructions]
Based on the constraints and input source, please output the best class specification in Markdown format.
Please title it "Class Specifications".
Please output each member specification in Markdown table format.

### PROGRESS

- processing [GeneratorBase.java] ===========> done!
- processing [package-info.java] ========> done!
- processing [ParserBase.java] =[FILE_SPLIT]===========================================> done!
- processing [ParserMinimalBase.java] ======================================> done!

### REPORT

|SOURCE|STATUS|REQUEST TOKEN|RESPONSE TOKEN|PROCESS TIME|
|-|-|-|-|-|
|GeneratorBase.java|SUCCESS|4604|272|17|
|package-info.java|SUCCESS|297|169|13|
|ParserBase.java|SUCCESS|5674|919|79|
|ParserBase.java|SUCCESS|7093|883|77|
|ParserMinimalBase.java|SUCCESS|5849|1463|129|
```

# Author

- Kenya Saitoh
- [Twitter Profile](https://twitter.com/KenyaSaitoh)
- Email: sky_diamonds_2022@yahoo.co.jp

# License

"brain2doc" is licensed under the [MIT license](https://en.wikipedia.org/wiki/MIT_License).

# Specific Use Cases

- [01. Creating specifications from Java source code](https://github.com/KenyaSaitoh/brain2doc/wiki/01.-Java%E3%82%BD%E3%83%BC%E3%82%B9%E3%82%B3%E3%83%BC%E3%83%89%E3%81%AE%E4%BB%95%E6%A7%98%E6%9B%B8%E4%BD%9C%E6%88%90)
- [02. Generating a summary from Python source code](https://github.com/KenyaSaitoh/brain2doc/wiki/02.-Python%E3%82%B3%E3%83%BC%E3%83%89%E3%81%AE%E3%82%B5%E3%83%9E%E3%83%AA%E3%83%BC%E4%BD%9C%E6%88%90)
- [03. Creating review results & improvement suggestions from JavaScript source code](https://github.com/KenyaSaitoh/brain2doc/wiki/03.-JavaScript%E3%82%B3%E3%83%BC%E3%83%89%E3%81%AE%E3%83%AC%E3%83%93%E3%83%A5%E3%83%BC%E3%83%BB%E6%94%B9%E5%96%84%E6%8F%90%E6%A1%88%E4%BD%9C%E6%88%90)
- [04. Generating an error message list from Java source code](https://github.com/KenyaSaitoh/brain2doc/wiki/04.-Java%E3%82%BD%E3%83%BC%E3%82%B9%E3%82%B3%E3%83%BC%E3%83%89%E3%81%8B%E3%82%89%E3%82%A8%E3%83%A9%E3%83%BC%E3%83%A1%E3%83%83%E3%82%BB%E3%83%BC%E3%82%B8%E4%B8%80%E8%A6%A7%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B%E5%A0%B4%E5%90%88)
