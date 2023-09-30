# brain2doc

### パラメータの指定方法

```
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
    js     : JavaScriptのソースコード（拡張子".js"が対象）
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

--src
    読み込み対象となるソースのファイルまたはディレクトリを指定する（指定必須）
    ディレクトリを指定すると、再帰的にファイルを一括処理する
    ZIPファイルを指定した場合も、ファイル内を再帰的に処理する

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

--proxyURL
    API呼び出しを行うときのプロキシサーバーのURLを指定する

--connect-timeout
    API呼び出しにおける接続タイムアウト時間を秒で指定する（デフォルトは10秒）

--timeout
    API呼び出しにおける読み込みタイムアウト時間を秒で指定する（デフォルトは300秒）

--retry-count
    API呼び出しで読み込みタイムアウトが発生した場合の、最大リトライ回数を指定する（デフォルトは3回）

--retry-interval
    API呼び出しで読み込みタイムアウトが発生した場合の、リトライ間隔を秒で指定する（デフォルトは60秒）

--auto-split
    このオプションを指定すると、1回のAPI呼び出しがトークンリミットに達した場合に、自動的に適切なサイズに分割して再送するモードに切り替わる
    指定がなかった場合は当該ソースをスキップする
```
