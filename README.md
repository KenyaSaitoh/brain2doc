# brain2doc

brain2docは、OpenAIのAPIによって、ソースコードからドキュメントを一括生成するツールです。

# DEMO



# Features

- OpenAI APIによるソースコードからのドキュメント生成は、Chat GPTのようなインタラクションを必要としない、ワンショットでのAPI呼び出しで有効な回答を引き出せるため、このツールによって、ドキュメントを一括して効率的に生成可能です。
- ソースには、単独のファイルはもちろん、ディレクトリを指定するも可能です。その場合、そこに格納されるファイルに対して再帰的に処理を行います。またZIPファイルを指定すると、ZIPファイル内を再帰的に処理します。
- 生成結果はマークダウン形式で出力されます。ディレクトリやZIPファイルの場合は、各ソースコードの生成結果が1つのマークダウンファイルに連結されます。
- 対応するソースコードには、以下の種類があります。
  - **Java**のソースコード（拡張子".java"が対象）
  - **JavaScript**または**TypeScript**のソースコード（拡張子".js", ".ts"が対象）
  - **Python**のソースコード（拡張子".py"が対象）
  - **SQL**コード（拡張子".sql"が対象）
  - **ページファイル**（拡張子"page", ".html", ".htm", ".xhtml", ".jsp"が対象）
  - **シェルスクリプト**（拡張子".sh", ".bash", ".ksh", ".bash"が対象）
  - 上記以外の汎用的なリソース
- 対応する生成処理には、以下の種類があります。生成結果はマークダウン形式で出力されます。
  - **仕様書**
  - **サマリー**
  - **レビュー結果・改善提案**
  - **何らかの一覧のテーブル（定数、メッセージ、REST APIなど）**
  - 上記以外の汎用的な出力（デフォルト）
- API Keyは環境変数"OPENAI_API_KEY"に設定しますが、コマンドにパラメータとして指定することもできます。
- API呼び出し時にトークンリミットに達してエラーが発生すると、自動的にソースを適切なサイズに分割して再送することが可能です。
- API呼び出し時にレートリミットに達してエラーが発生すると、一定間隔を置いてから自動的にリトライします。
- API呼び出しでは、接続タイムアウト時間、読み込みタイムアウト時間、リトライ回数、リトライ間隔、プロキシサーバーなどをパラメータで指定可能です。
- OpenAIのモデルは、デフォルトでは"gpt-4"ですが、"gpt-3.5-turbo"などにパラメータで切り替えることができます。
- 出力結果の大きさを、小、中、大、制限なしの4種類から選択できます。
- 対象のソースファイルの名前を、正規表現で絞り込むことが可能です。
- 言語はデフォルトでは日本語ですが、英語にも対応しています。

# Requirement

本ツールを動作させるには、Java 11以上の環境が必要です。

# Installation

brain2docの実行に必要なファイルはJARファイルのみであり、依存ライブラリも含めて1つのJARファイルにパッケージングされています。
JARファイルは、releasesディレクトリ下の最新VERをダウンロードして使用してください。

# Usage

```
java -jar <任意の場所>/brain2doc.jar [ソース] [オプション]
```

- [ソース]には、読み込み対象となるソースのファイルまたはディレクトリを指定する（指定必須）
  - ディレクトリを指定すると、再帰的にファイルを一括処理する
  - ZIPファイルを指定した場合も、ファイル内を再帰的に処理する

オプションの指定方法は、以下のとおりです。

```
--help
    パラメータの指定方法を表示する

--url
    APIのURLを指定する（デフォルトは"https://api.openai.com/v1/chat/completions"）

--model           
    gptのモデルを指定する（デフォルトは"gpt-4"）

--apikey
    APIキーを指定する（指定がない場合は環境変数"OPENAI_API_KEY"より取得）

--resource
    入力となるリソースの種別を指定する（デフォルトは"others"）
    以下の種別あり
    java   : Javaのソースコード（拡張子".java"が対象）
    js     : JavaScriptまたはTypeScriptのソースコード（拡張子".js", ".ts"が対象）
    python : Pythonのソースコード（拡張子".py"が対象）
    sql    : SQLコード（拡張子".sql"が対象）
    page   : ページファイル（拡張子"page", ".html", ".htm", ".xhtml", ".jsp"が対象）
    shell  : シェルスクリプト（拡張子".sh", ".bash", ".ksh", ".bash"が対象）
    others : 上記以外の汎用的なリソース

--gen
    リソースから何を生成したいかを指定する（"--gen"と"--gen-table"の2つは指定不可）
    以下の種別あり
    spec    : 仕様書
    summary : サマリー
    review  : レビュー結果・改善提案
    others  : 上記以外の汎用的な出力（"--gen"、"--gen-table"の指定がなかった場合のデフォルト）

--gen-table
    リソースから何かの一覧を生成したい場合、その名前を指定する（"--gen"と"--gen-table"の2つは指定不可）

--fields
    リソースから一覧を生成する場合、その列名を列挙する（"--gen-table"の場合は指定必須）

--output-scale
    出力結果の大きさ（目安）を指定する（デフォルトは"nolimit"）
    small   : スモールサイズ（50文字程度）
    medium  : ミディアムサイズ（200文字程度）
    large   : ラージサイズ（500文字程度）
    nolimit : 制限なし

--regex
    読み込み対象となるソースを絞り込むための正規表現文字列を指定する（任意指定）

--dest
    出力するマークダウンファイルのディレクトリ名またはファイル名をフルパスで指定する
    ディレクトリ名を指定した場合は、デフォルトのファイル名は"brain2doc-{リソース名}-{出力種別名}-{yMMddHHmmss}.md"
    （例："brain2doc-java-review-20230924104914.md"）
    このオプションの指定がなかった場合は、ソースファイルと同じディレクトに、デフォルトのファイル名で出力される

--lang
    入出力する言語を指定する（デフォルトは日本語）
    ja : 日本語
    en : 英語

--template
    任意のテンプレートファイルをフルパスで指定する（任意指定）
    デフォルトのテンプレートファイルと、差分のみが上書きされる

--max-split-count
    最大ファイル分割数を指定する
    トークンリミットオーバーやレートリミットオーバーが発生した場合、自動的にファイルを分割するが、
    分割数がこのオプションに指定された数値を超えた場合は、当該ソースファイルの処理はスキップする（デフォルト10）

--proxyURL
    API呼び出しを行うときのプロキシサーバーのURLを指定する

--connect-timeout
    API呼び出しにおける接続タイムアウト時間を秒で指定する（デフォルトは10秒）

--timeout
    API呼び出しにおける読み込みタイムアウト時間を秒で指定する（デフォルトは300秒）

--retry-count
    API呼び出しでレートリミットオーバーや読み込みタイムアウトが発生した場合の、最大リトライ回数を指定する（デフォルトは3回）

--retry-interval
    API呼び出しでレートリミットオーバーや読み込みタイムアウトが発生した場合の、リトライ間隔を秒で指定する（デフォルトは60秒）

--auto-split
    このオプションを指定すると、1回のAPI呼び出しがトークンリミットに達した場合に、自動的に適切なサイズに分割して再送するモードに切り替わる
    指定がなかった場合は当該ソースをスキップする
```

# コンソール表示内容

本ツールを起動すると、以下の情報がコンソールに表示されます

1. PROMPT CONTENT (without source)
  プロンプトに投入する内容。ただしソースファイルはここでは表示しません（実際にはソースを含むプロンプトが投入されます）。

2. PROGRESS
  各ソースファイルの進捗状況がリアルタイムに表示されます。
  自動分割モードの場合、トークンリミットオーバーやレートリミットオーバーが発生すると、"[FILE_SPLIT]"と表示されて処理が先に進みます。

3. REPORT
  全ファイルの呼び出しが終了すると、レポートを出力します。
  レポートの内容は、(1)ソースファイル名、(2)ステータス、(3)リクエストのトークン数、(4)レスポンスのトークン数、(5)処理時間

以下にコンシール表示の例を示します。

```
##############################
#                            #
#    Welcome to Brain2doc    #
#                            #
##############################

### PROMPT CONTENT (without source)

あなたは『プロのエンジニア』です。

[制約条件]
回答は日本語でお願いします。
回答は50文字以内でお願いします。
マークダウンの見出しは、タイトルにレベル2を使用し、それ以外はレベル4以上にしてください。

[命令書]
制約条件と入力ソースをもとに最高のクラス仕様書を、マークダウン形式で出力してください。
タイトルは「クラス仕様書」でお願いします。
各メンバの仕様はマークダウンのテーブル形式で出力してください。        
Based on the constraints and input source, please output the best class specification in Markdown format.
Please title it "Class Specifications".
Please output each member specification in Markdown table format.

### PROGRESS

- processing [GeneratorBase.java] ===========> done!
- processing [package-info.java] ========> done!
- processing [ParserBase.java] =[FILE_SPLIT]========================================================> done!
- processing [ParserMinimalBase.java] ==================================================================> done!

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