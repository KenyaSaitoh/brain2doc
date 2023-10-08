brain2docの実行に必要なファイルはJARファイルのみであり、依存ライブラリも含めて1つのJARファイルにパッケージングされています。
JARファイルは、releasesディレクトリ下の最新VERをダウンロードして使用してください。
ただし実行環境には、あらかじめJavaランタイムがインストールされている必要があります（Java 11以上）。

ツールの起動方法・オプションの指定方法などは、[Wikiページ](https://github.com/KenyaSaitoh/brain2doc/wiki)を参照してください。

以下は、このツールによって生成した、このツール自体の仕様サマリーです。

# pro.kensait.brain2doc.common.**Const**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/common/Const.java

## クラス概要

このクラスは、プログラム全体で共有される定数を提供します。

#### メンバ概要

|メンバ名|説明|
|---|---|
|OUTPUT_FILE_EXT|出力ファイルの拡張子を示す定数。値は ".md"|
|LINE_SEP|システムの行区切り文字を示す定数。値は `System.getProperty("line.separator")` の結果|
|MARKDOWN_HEADING|マークダウンの見出しを示す定数。値は "# "|
|MARKDOWN_HORIZON|マークダウンの水平線を示す定数。値は "---"|

---
# pro.kensait.brain2doc.common.**Util**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/common/Util.java

## クラス概要

`pro.kensait.brain2doc.common.Util`クラスは便利なユーティリティメソッドを提供します。

#### メソッド概要

| メソッド名 | アクセス修飾子 | 引数 | 返り値 | 概要 |
|:---------:|:--------------:|:---:|:-----:|:----|
| sleepAWhile | public static | int retryInterval | void | 指定した秒数だけ処理を停止します。処理の中断が発生した場合は、RuntimeException をスローします。 |


---
# pro.kensait.brain2doc.config.**ConstMapHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/ConstMapHolder.java

## クラス概要

このクラスは、`const.yaml`というYAMLファイルを読み込み、その内容をマップとして保持します。マップは静的に保持され、`getConstMap`メソッドを通じて取得することができます。

#### メンバ概要

| メンバ名 | 型 | 概要 |
| --- | --- | --- |
| map | Map | `const.yaml`の内容を保持するマップ |
| classloader | ClassLoader | スレッドコンテキストのクラスローダー |
| yaml | Yaml | YAMLファイルを操作するためのオブジェクト |

#### メソッド概要

| メソッド名 | 返却値 |概要 |
| --- | --- | --- |
| getConstMap | Map | `const.yaml`の内容を保持するマップを返す |

---
# pro.kensait.brain2doc.config.**DefaultValueHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/DefaultValueHolder.java

## クラス概要

### DefaultValueHolder

このクラスは、プロパティファイル（default.properties）からプロパティを読み込み、アプリケーションで使用するデフォルト値を保持します。プロパティファイルはUTF-8形式でエンコードされています。

#### メンバの概要

|メンバ名|説明|
|---|---|
|props|プロパティファイルから読み込んだプロパティを保持します。|
|DefaultValueHolder|DefaultValueHolderクラスの静的初期化ブロックで、プロパティファイルからプロパティを読み込みます。|
|getProperty|指定したキーに対応するプロパティ値を取得します。キーが存在しない場合はnullを返します。|

---
# pro.kensait.brain2doc.config.**HelpMessageHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/HelpMessageHolder.java

## クラス概要

`HelpMessageHolder`クラスはヘルプメッセージを管理するクラスです。メッセージはテキストファイルから読み込まれ、指定した言語のヘルプメッセージを返します。シングルトンパターンが採用されています。

#### メンバ概要

| メンバ | 概要 |
|---|---|
| `HELP_FILE_PREFIX` | ヘルプファイルのプレフィックス |
| `HELP_FILE_EXT` | ヘルプファイルの拡張子 |
| `helpHolder` | `HelpMessageHolder`のインスタンス |
| `helpMessage` | ヘルプメッセージのリスト |
| `getInstance()` | `HelpMessageHolder`のインスタンスを返す |
| `getHelpMessage(String lang)` | 指定した言語のヘルプメッセージを返す |

---
# pro.kensait.brain2doc.config.**TemplateHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/TemplateHolder.java

## クラス概要

該当のJavaクラスは `TemplateHolder` という名前で、設定テンプレートの保持と操作に関する機能を提供します。

#### メンバ

|名前|型|説明|
|---|---|---|
|TEMPLATE_FILE_PREFIX|String|テンプレートファイルの接頭辞|
|TEMPLATE_FILE_EXT|String|テンプレートファイルの拡張子|
|templateHolder|TemplateHolder|TemplateHolderのインスタンス|
|templateMap|Map|テンプレートのマップ|

#### メソッド

|名前|戻り値|説明|
|---|---|---|
|getInstance()|TemplateHolder|TemplateHolderのインスタンスを返す|
|getTemplateMap(Locale,Path)|Map|指定されたロケールとパスに対するテンプレートマップを返す|
|merge(Map,Map)|Map|二つのマップをマージする|

このクラスはSingletonパターンを使用しており、`getInstance()`メソッドを通じて唯一のインスタンスにアクセスできます。また、`getTemplateMap()`メソッドは指定されたロケールとパスに対応するテンプレートマップを返し、もし外部からテンプレートファイルが指定された場合は、既存のテンプレートマップとマージします。

---
# pro.kensait.brain2doc.exception.**OpenAIClientException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIClientException.java

## クラス概要

このクラスは`OpenAIClientException`というRuntimeExceptionのサブクラスです。OpenAIのクライアントとの通信でエラーが発生した場合にこの例外クラスがスローされます。

#### メンバ概要

| メソッド名 | 概要 |
|:-----------|:-----|
| OpenAIClientException() | デフォルトのコンストラクタ。何も引数を受け取らない |
| OpenAIClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) | メッセージ、原因、抑制可能な例外、スタックトレース書き込み可能かのフラグを引数に持つコンストラクタ |
| OpenAIClientException(String message, Throwable cause) | メッセージと原因を引数に持つコンストラクタ |
| OpenAIClientException(String message) | メッセージのみを引数に持つコンストラクタ |
| OpenAIClientException(Throwable cause) | 原因のみを引数に持つコンストラクタ |
| OpenAIClientException(ClientErrorBody clientErrorBody) | ClientErrorBodyオブジェクトを引数に持つコンストラクタ |
| getClientErrorBody() | clientErrorBodyフィールドのGetterメソッド |

---
# pro.kensait.brain2doc.exception.**OpenAIInsufficientQuotaException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIInsufficientQuotaException.java

## クラス概要

このクラスは、OpenAIのクオータが不足した場合に発生する例外を表す`OpenAIInsufficientQuotaException`です。このクラスは`OpenAIClientException`を継承しています。

#### メンバの概要

|メンバ|説明|
|---|---|
|private ClientErrorBody clientErrorBody|エラー情報を保持するクライアントエラーボディ|
|public OpenAIInsufficientQuotaException()|デフォルトコンストラクタ|
|public OpenAIInsufficientQuotaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)|メッセージ、原因、抑制許可フラグ、書き込み可能スタックトレースを引数にとるコンストラクタ|
|public OpenAIInsufficientQuotaException(String message, Throwable cause)|メッセージと原因を引数にとるコンストラクタ|
|public OpenAIInsufficientQuotaException(String message)|メッセージを引数にとるコンストラクタ|
|public OpenAIInsufficientQuotaException(Throwable cause)|原因を引数にとるコンストラクタ|
|public OpenAIInsufficientQuotaException(ClientErrorBody clientErrorBody)|クライアントエラーボディを引数にとるコンストラクタ|
|public ClientErrorBody getClientErrorBody()|エラーボディを取得するメソッド|

---
# pro.kensait.brain2doc.exception.**OpenAIInvalidAPIKeyException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIInvalidAPIKeyException.java

## クラス概要

`OpenAIInvalidAPIKeyException`は、OpenAIのクライアントエラーを表すクラスです。`OpenAIClientException`を継承しています。

#### メンバの概要

|メンバ名|説明|
|:---|:---|
|`clientErrorBody`|クライアントエラーメッセージを保持するオブジェクト|

#### メソッドの概要

|メソッド名|説明|
|:---|:---|
|`OpenAIInvalidAPIKeyException()`|デフォルトコンストラクタ|
|`OpenAIInvalidAPIKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)`|メッセージ、原因、抑制の有効化、スタックトレースの書き込み可能性を指定して例外を構築するコンストラクタ|
|`OpenAIInvalidAPIKeyException(String message, Throwable cause)`|指定された詳細メッセージと原因を使用して新しい例外を構築するコンストラクタ|
|`OpenAIInvalidAPIKeyException(String message)`|指定された詳細メッセージを持つ新しい例外を構築するコンストラクタ|
|`OpenAIInvalidAPIKeyException(Throwable cause)`|指定された原因を持つ新しい例外を構築するコンストラクタ|
|`OpenAIInvalidAPIKeyException(ClientErrorBody clientErrorBody)`|クライアントエラーメッセージを保持するオブジェクトを指定して例外を構築するコンストラクタ|
|`getClientErrorBody()`|クライアントエラーメッセージを保持するオブジェクトを取得するメソッド|


---
# pro.kensait.brain2doc.exception.**OpenAIRateLimitExceededException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIRateLimitExceededException.java

## クラス概要

このクラスは、OpenAIのAPIを使用中にレート制限を超えた場合にスローされる例外を表現しています。

#### メンバ一覧

| メンバ名 | 説明 |
| --- | --- |
| `OpenAIRateLimitExceededException()` | 引数なしのコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `OpenAIRateLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)` | メッセージと原因と抑制可能性と書き込み可能スタックトレースを引数にとるコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `OpenAIRateLimitExceededException(String message, Throwable cause)` | メッセージと原因を引数にとるコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `OpenAIRateLimitExceededException(String message)` | メッセージを引数にとるコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `OpenAIRateLimitExceededException(Throwable cause)` | 原因を引数にとるコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `OpenAIRateLimitExceededException(ClientErrorBody clientErrorBody)` | ClientErrorBodyを引数にとるコンストラクタ。スーパークラスの同名コンストラクタを呼び出す。 |
| `getClientErrorBody()` | ClientErrorBodyを取得するメソッド。 |

---
# pro.kensait.brain2doc.exception.**OpenAITokenLimitOverException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAITokenLimitOverException.java

## クラス概要

`OpenAITokenLimitOverException`は、OpenAIのクライアント例外を表すクラスで、`OpenAIClientException`を継承しています。

#### メンバー概要

| メンバー名 | 概要 |
|---|---|
| `clientErrorBody` | クライアントのエラーボディを管理する `ClientErrorBody` オブジェクト |
| `OpenAITokenLimitOverException()` | デフォルトコンストラクタ |
| `OpenAITokenLimitOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)` | 例外メッセージ、原因、抑制可能な例外、スタックトレース書き込み許可を引数に取るコンストラクタ |
| `OpenAITokenLimitOverException(String message, Throwable cause)` | 例外メッセージと原因を引数に取るコンストラクタ |
| `OpenAITokenLimitOverException(String message)` | 例外メッセージを引数に取るコンストラクタ |
| `OpenAITokenLimitOverException(Throwable cause)` | 原因を引数に取るコンストラクタ |
| `OpenAITokenLimitOverException(ClientErrorBody clientErrorBody)` | `ClientErrorBody`オブジェクトを引数に取るコンストラクタ |
| `getClientErrorBody()` | `clientErrorBody`オブジェクトを取得するメソッド |

各コンストラクタは親クラスの同名コンストラクタを呼び出すことで、例外の詳細情報を設定します。`ClientErrorBody`オブジェクトについては、例外が発生した際の詳細なクライアントエラー情報を保持します。

---
# pro.kensait.brain2doc.exception.**RetryCountOverException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/RetryCountOverException.java

## クラス概要

`RetryCountOverException`クラスは、`RuntimeException`クラスを継承しています。このクラスは、リトライ回数の上限を超えた際にスローされる例外を定義します。

#### メソッド詳細

| メソッド名 | 説明 |
| ------ | ----------- |
| RetryCountOverException() | 引数なしのコンストラクタ。 |
| RetryCountOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) | メッセージ、原因、抑制可能なスタックトレースの有無を指定できるコンストラクタ。 |
| RetryCountOverException(String message, Throwable cause) | メッセージと原因を指定できるコンストラクタ。 |
| RetryCountOverException(String message) | メッセージを指定できるコンストラクタ。 |
| RetryCountOverException(Throwable cause) | 原因を指定できるコンストラクタ。 |


---
# pro.kensait.brain2doc.exception.**TimeoutException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/TimeoutException.java

## クラス概要

`TimeoutException`は`RuntimeException`を継承した、タイムアウトに関する例外を扱うクラスです。以下の5つのコンストラクタが存在します。

|メンバ名|概要|
|---|---|
|`TimeoutException()`|メッセージや原因なしに例外を生成します。|
|`TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)`|メッセージ、原因、抑制の有効化、スタックトレースの書き込み可能性を指定して例外を生成します。|
|`TimeoutException(String message, Throwable cause)`|メッセージと原因を指定して例外を生成します。|
|`TimeoutException(String message)`|メッセージを指定して例外を生成します。|
|`TimeoutException(Throwable cause)`|原因を指定して例外を生成します。|

---
# pro.kensait.brain2doc.**Main**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/Main.java

## クラス概要

このクラスは、`Brain2doc`のメインクラスで、プログラムのエントリーポイントである`main`メソッドを含んでいます。異なる例外をキャッチして適切なメッセージを表示することで、オープンAIのAPI使用に関する問題を処理します。

#### メンバ概要

|メンバ|説明|
|---|---|
|main|プログラムのエントリーポイント。コマンドライン引数を処理し、適切なメソッドを呼び出します。|
|printHelpMessage|指定された言語のヘルプメッセージを表示します。|
|printBanner|ウェルカムバナーを表示します。|
|printReport|報告リストを表示します。報告リストが空の場合は、「対象リソースがありません」と表示します。|

以上が、このクラスが持つ主要なメンバの概要です。



---
# pro.kensait.brain2doc.openai.**ApiClient**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ApiClient.java

## クラス概要

このクラスは、OpenAIのGPT-3を使用した会話型AIのためのAPIクライアントを提供します。

#### メンバの概要

| メンバ | 概要 |
|:-----------|:------------|
| TEMPERATURE | レスポンス生成時のランダム性を調整するための定数。 |
| INVALID_API_KEY_CODE | APIキーが無効であることを表すエラーコード。 |
| INSUFFICIENT_QUOTA_CODE | クオータが不足していることを表すエラーコード。 |
| CONTEXT_LENGTH_EXCEEDED_CODE | トークンが上限を超えたことを表すエラーコード。 |
| RATE_LIMIT_EXCEEDED_CODE | レートリミットが超過したことを表すエラーコード。 |
| ask | OpenAI APIにリクエストを送信し、レスポンスを受け取るメソッド。 |
| sendRequest | HTTPリクエストを送信し、HTTPサーバーからのレスポンスを受け取るメソッド。 |
| getRequestJson | リクエストボディをJSON形式に変換するメソッド。 |
| getResponseBody | レスポンスボディを指定したクラスのインスタンスに変換するメソッド。 |
| createHttpClient | プロキシ設定を考慮したHTTPクライアントを生成するメソッド。 |

---
# pro.kensait.brain2doc.openai.**ApiResult**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ApiResult.java

## クラス概要

`ApiResult`クラスは、APIからの戻り値を取り扱うためのクラスです。`responseBody`と`interval`という2つのメンバ変数を持っています。

#### メンバの概要

| メンバ名 | データ型 | 説明 |
| -------- | -------- | ---- |
| responseBody | SuccessResponseBody | APIからのレスポンスボディを保持 |
| interval | long | APIからの応答時間を保持 |
  
#### メソッドの概要

| メソッド名 | 戻り値 | 説明 |
| -------- | -------- | ---- |
| getResponseBody | SuccessResponseBody | `responseBody`を返す |
| getInterval | long | `interval`を返す |
| toString | String | `ApiResult`の状態を文字列として返す |

---
# pro.kensait.brain2doc.openai.**Choice**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Choice.java

## クラス概要

`Choice`クラスは、Jacksonライブラリを用いてJSONオブジェクトとして扱うためのクラスです。このクラスは以下のメンバを持っています。

#### メンバ概要

| メンバ名 | データ型 | 説明 |
|:---|:---|:---|
| index | Integer | 選択肢のインデックスを表します。 |
| message | Message | メッセージの内容を表すMessageオブジェクトです。 |
| finishReason | String | 終了理由を示す文字列です。 |

各メンバには、getterとsetterのメソッドが用意されており、それぞれのメンバの値を取得したり変更したりすることができます。

また、`toString()`メソッドをオーバーライドしており、インスタンスの状態を文字列として表現することができます。

---
# pro.kensait.brain2doc.openai.**ClientErrorBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ClientErrorBody.java

## クラス概要

このクラスは、「pro.kensait.brain2doc.openai」パッケージに属する`ClientErrorBody`という名前のクラスです。このクラスは、エラーメッセージを取り扱うためのもので、エラー情報を保持し、取得、設定するためのメソッドが存在します。

#### メンバの概要

|メンバ名|型|概要|
|---|---|---|
|error|Error|エラー情報を保持するフィールド。JsonPropertyアノテーションにより、JSONの"error"キーにバインドされます。|

#### メソッドの概要

|メソッド名|引数|戻り値|概要|
|---|---|---|---|
|ClientErrorBody|(なし)|(なし)|デフォルトコンストラクタ。|
|ClientErrorBody|Error error|(なし)|エラー情報を引数として受け取り、それをフィールドにセットするコンストラクタ。|
|getError|(なし)|Error|エラー情報を返すメソッド。|
|setError|Error error|(なし)|エラー情報を設定するメソッド。|
|toString|(なし)|String|オブジェクトの文字列表現を返すメソッド。エラー情報を含む。|

---
# pro.kensait.brain2doc.openai.**Error**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Error.java

## クラス概要

`Error`クラスは、エラーメッセージ、エラータイプ、パラメータ、コードをフィールドとして持つJavaクラスです。各フィールドは、プライベート変数として定義されており、それぞれに対応するgetterとsetterが用意されています。また、オーバーライドされた`toString`メソッドを通じて、フィールドの値を文字列として取得することができます。

#### メンバ概要

|メンバ名|タイプ|説明|
|---|---|---|
|message|String|エラーメッセージを保持|
|type|String|エラータイプを保持|
|param|String|パラメータを保持|
|code|String|エラーコードを保持|
|Error()|コンストラクタ|デフォルトコンストラクタ|
|Error(String, String, String, String)|コンストラクタ|メッセージ、タイプ、パラメータ、コードを引数に取るコンストラクタ|
|getMessage()|メソッド|メッセージを取得|
|setMessage(String)|メソッド|メッセージを設定|
|getType()|メソッド|タイプを取得|
|setType(String)|メソッド|タイプを設定|
|getParam()|メソッド|パラメータを取得|
|setParam(String)|メソッド|パラメータを設定|
|getCode()|メソッド|コードを取得|
|setCode(String)|メソッド|コードを設定|
|toString()|メソッド|オブジェクトの文字列表現を取得|

以上のメンバを通じて、`Error`クラスのインスタンスはエラー情報をカプセル化し、それを操作する機能を提供します。

---
# pro.kensait.brain2doc.openai.**Message**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Message.java

## クラス概要

`Message`クラスは、文字列形式の`role`と`content`をフィールドとして保持します。これらのフィールドは、getterとsetterメソッドを通じてアクセス可能です。また、引数なしのデフォルトコンストラクタと、`role`と`content`を引数とするコンストラクタがあります。最後に、`toString`メソッドをオーバーライドして、`Message`オブジェクトの文字列表現を返します。

#### メンバの概要

| メンバ名 | 概要 |
|---|---|
| role | メッセージの役割を表す文字列 |
| content | メッセージの内容を表す文字列 |
| Message() | デフォルトコンストラクタ。フィールドは初期化されない |
| Message(String role, String content) | `role`と`content`を引数にとるコンストラクタ |
| getRole() | `role`フィールドのゲッター |
| setRole(String role) | `role`フィールドのセッター |
| getContent() | `content`フィールドのゲッター |
| setContent(String content) | `content`フィールドのセッター |
| toString() | `Message`オブジェクトの文字列表現を返す |

---
# pro.kensait.brain2doc.openai.**RequestBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/RequestBody.java

## クラス概要

**パッケージ名**: pro.kensait.brain2doc.openai

**クラス名**: RequestBody

このクラスは、OpenAIのAPIリクエストボディを表現するためのクラスです。JSON形式のメッセージを含み、Jacksonライブラリを用いてシリアライズとデシリアライズを行います。

#### メンバ概要

|メンバ名|型|説明|
|---|---|---|
|model|String|OpenAIのモデル名を表す。|
|messages|List<Message>|メッセージのリスト。各メッセージはMessageクラスのインスタンスである。|
|temperature|Float|生成するテキストの「ランダム性」を制御するパラメータ。値が高いほど予測はランダムになり、低いほど予測は決定的になる。|

#### メソッド概要

- **public RequestBody()**: デフォルトコンストラクタ。
- **public RequestBody(String model, List<Message> messages, Float temperature)**: モデル名、メッセージのリスト、温度パラメータを引数に取るコンストラクタ。
- **public String getModel()**: モデル名を取得するメソッド。
- **public void setModel(String model)**: モデル名を設定するメソッド。
- **public List<Message> getMessages()**: メッセージのリストを取得するメソッド。
- **public void setMessages(List<Message> messages)**: メッセージのリストを設定するメソッド。
- **public Float getTemperature()**: 温度パラメータを取得するメソッド。
- **public void setTemperature(Float temperature)**: 温度パラメータを設定するメソッド。
- **public String toString()**: クラスの内容を文字列化したものを返すメソッド。

---
# pro.kensait.brain2doc.openai.**RequestFullBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/RequestFullBody.java

## クラス概要

`RequestFullBody`クラスは、OpenAIのエンドポイントに送信するリクエストの全体を表現します。以下は、このクラスのメンバー一覧とその概要です。

| メンバー名 | 型 | 説明 |
| --- | --- | --- |
| model | String | 使用するモデルの名前 |
| messages | List<Message> | メッセージのリスト |
| temperature | Float | ランダム性を制御するパラメータ |
| topP | Float | トップPサンプリングを制御するパラメータ |
| n | Integer | 生成するメッセージの数 |
| stream | Boolean | ストリーミングモードかどうか |
| stop | List<String> | 生成を停止するトークンのリスト |
| maxTokens | Integer | 生成するトークンの最大数 |
| presencePenalty | Float | 存在ペナルティ |
| frequencyPenalty | Float | 頻度ペナルティ |
| logitBias | Map<Object, Object> | ロジットバイアス |

各メンバーは、コンストラクタで初期化したり、ゲッターとセッターを通じて値を取得・設定したりできます。また、`toString`メソッドをオーバーライドしているため、クラスの状態を文字列で取得できます。

---
# pro.kensait.brain2doc.openai.**StaticProxySelector**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/StaticProxySelector.java

## クラス概要

このクラスは、`ProxySelector`を継承した`StaticProxySelector`という名前のクラスです。このクラスは、特定のプロキシを使用するためのクラスです。

#### メンバの概要

| メンバ名 | 概要 |
| :--- | :--- |
| proxyList | このリストはプロキシのリストを保持します。コンストラクタ`StaticProxySelector`で設定されます。 |
| StaticProxySelector() | このクラスのコンストラクタです。引数としてプロキシのタイプとアドレスを受け取り、この情報を元にプロキシリストを作成します。 |
| select() | このメソッドはプロキシリストを返します。 |
| connectFailed() | このメソッドはプロキシへの接続エラーが発生した場合に呼び出されます。エラーが発生したら、ランタイムエクセプションをスローします。 |

---
# pro.kensait.brain2doc.openai.**SuccessResponseBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/SuccessResponseBody.java

## クラス概要

`SuccessResponseBody`クラスはレスポンスボディの成功時の内容を保持するためのクラスです。このクラスは、以下のメンバを持っています。

#### メンバ概要

| メンバ名 | 型 | 説明 |
| --- | --- | --- |
| id | String | レスポンスのID |
| object | String | レスポンスのオブジェクト |
| created | Long | 作成日時 |
| model | String | モデル名 |
| choices | List<Choice> | 選択肢のリスト |
| usage | Usage | 使用状況 |
| error | Error | エラー内容 |

このクラスは、メンバのゲッター、セッターなどのメソッドを持ちます。メンバの詳細な説明は以下の通りです。

- `id`: レスポンスのIDを格納します。
- `object`: レスポンスのオブジェクトを格納します。
- `created`: 作成日時を格納します。
- `model`: モデル名を格納します。
- `choices`: 選択肢のリストを格納します。
- `usage`: 使用状況を格納します。
- `error`: エラー内容を格納します。

---
# pro.kensait.brain2doc.openai.**Usage**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Usage.java

## クラス概要

このクラスは「Usage」で、プロンプトトークン、コンプリーショントークン、合計トークンを管理します。それぞれの値はInteger型で表現され、getterおよびsetterを通じて操作が可能です。また、デフォルトコンストラクタと全フィールドの値を引数に取るコンストラクタがあります。さらに、オブジェクトの等価性をチェックするためのequalsメソッド、ハッシュコードを計算するためのhashCodeメソッド、オブジェクトの文字列表現を提供するためのtoStringメソッドが実装されています。

#### メンバの概要

| メソッド名 | 解説 |
| --- | --- |
| getPromptTokens | プロンプトトークンを返します |
| setPromptTokens | プロンプトトークンを設定します |
| getCompletionTokens | コンプリーショントークンを返します |
| setCompletionTokens | コンプリーショントークンを設定します |
| getTotalTokens | 合計トークンを返します |
| setTotalTokens | 合計トークンを設定します |
| equals | オブジェクトの等価性をチェックします |
| hashCode | ハッシュコードを計算します |
| toString | オブジェクトの文字列表現を提供します |

---
# pro.kensait.brain2doc.params.**GenerateType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/GenerateType.java

## クラス概要

`GenerateType`は、Javaの列挙型（enum）で、ドキュメント生成の種類を表します。4つの定数`SPEC`、`SUMMARY`、`REVIEW`、`OTHERS`を持ち、それぞれが生成するドキュメントの種類を表します。

#### 各メンバの概要

| メンバ名 | 説明 |
|---|---|
| SPEC | 仕様書の生成を示す定数 |
| SUMMARY | 要約の生成を示す定数 |
| REVIEW | レビューの生成を示す定数 |
| OTHERS | その他のドキュメントの生成を示す定数 |
| name | ドキュメントの生成タイプを表す文字列 |
| GenerateType(String name) | 列挙型のコンストラクタ。生成タイプの文字列をパラメータとして受け取ります |
| getName() | 生成タイプの名前を返すメソッド |
| getGenerateTypeByName(String name) | 生成タイプの名前から対応する列挙型を返す静的メソッド。存在しない名前が指定された場合はnullを返します。 |

---
# pro.kensait.brain2doc.params.**OutputScaleType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/OutputScaleType.java

## クラス概要

このクラスは、出力スケールタイプを表す列挙型(Enums)で、出力の規模を「SMALL」、「MEDIUM」、「LARGE」、「NOLIMIT」の4種類で表現します。各タイプは、名前と文字サイズをフィールドとして持ち、それぞれのタイプに対応する名前と文字サイズを返すメソッドを提供します。

#### メンバの概要

| メンバ名 | 型 | 説明 |
| --- | --- | --- |
| SMALL | OutputScaleType | 文字サイズが50の出力スケールタイプ |
| MEDIUM | OutputScaleType | 文字サイズが200の出力スケールタイプ |
| LARGE | OutputScaleType | 文字サイズが500の出力スケールタイプ |
| NOLIMIT | OutputScaleType | 文字サイズが制限なしの出力スケールタイプ |
| name | String | 出力スケールタイプの名前 |
| charSize | Integer | 出力スケールタイプの文字サイズ |
| getName() | method | 出力スケールタイプの名前を取得する |
| getCharSize() | method |出力スケールタイプの文字サイズを取得する |
| getOutputScaleTypeByName(String name) | method | 名前に応じた出力スケールタイプを取得する |

---
# pro.kensait.brain2doc.params.**Parameter**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/Parameter.java

## クラス概要

`Parameter`クラスは、APIのパラメータ情報を管理するためのクラスです。

#### メンバ一覧

|メンバ名|概要|
|---|---|
|openaiURL|OpenAIのURL。デフォルト値はプロパティファイルから取得|
|openaiModel|OpenAIのモデル。デフォルト値はプロパティファイルから取得|
|openaiApikey|OpenAIのAPIキー。デフォルト値は環境変数から取得|
|resourceType|リソースタイプ。デフォルト値はプロパティファイルから取得|
|generateType|生成タイプ。デフォルト値はプロパティファイルから取得|
|genTable|生成するテーブル名。任意指定|
|fields|フィールド名。任意指定|
|outputScaleType|出力スケールタイプ。デフォルト値はプロパティファイルから取得|
|srcPath|ソースパス。指定必須|
|srcRegex|ソースの正規表現。任意指定|
|destFilePath|出力先のファイルパス。デフォルト値はソースパスと同じディレクトリの固定ファイル名|
|locale|ロケール。デフォルト値はプロパティファイルから取得|
|templateFile|テンプレートファイルのパス。任意指定|
|maxSplitCount|最大分割数|
|proxyURL|プロキシURL。任意指定|
|connectTimeout|接続タイムアウト。デフォルト値はプロパティファイルから取得|
|requestTimeout|リクエストタイムアウト。デフォルト値はプロパティファイルから取得|
|retryCount|リトライ回数。デフォルト値はプロパティファイルから取得|
|retryInterval|リトライ間隔。デフォルト値はプロパティファイルから取得|
|isAutoSplitMode|自動分割モードかどうか。デフォルト値false|
|printPrompt|プロンプトを表示するかどうか|

#### 主要メソッド

- `setUp(String[] args)`: パラメータの設定を行う静的メソッドです。コマンドライン引数を受け取り、各パラメータを設定します。

- `getDefaultOutputFileName(ResourceType resourceType, GenerateType generateType)`: デフォルトの出力ファイル名を取得する静的メソッドです。

- `getCurrentDateTimeStr()`: 現在の日時を文字列で取得する静的メソッドです。

- 各メンバの`get`メソッド: 対応するメンバの値を取得するためのメソッドです。

- `toString()`: クラスの文字列表現を返すメソッドです。

---
# pro.kensait.brain2doc.params.**PathType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/PathType.java

## クラス概要

クラス名：`PathType`

パッケージ名：`pro.kensait.brain2doc.params`

種類：`enum` 

#### メンバ一覧

|メンバ名|概要|
|---|---|
|FILE|ファイルパスを表す要素|
|DIRECTORY|ディレクトリパスを表す要素|
|ZIP|ZIPファイルパスを表す要素|

---
# pro.kensait.brain2doc.params.**ResourceType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/ResourceType.java

## クラス概要

`ResourceType`は、リソースの種類を表すenumクラスです。各リソースの種類は、その名前(`name`)と、その種類に関連する拡張子(`exts`)を持っています。

#### メンバ概要

| メンバ名 | 概要 |
|:--|:--|
| `JAVA` | Javaのリソースを表します。 |
| `JAVASCRIPT` | JavaScriptのリソースを表します。 |
| `PYTHON` | Pythonのリソースを表します。 |
| `SQL` | SQLのリソースを表します。 |
| `PAGE` | HTML, XHTML, JSPなどのページリソースを表します。 |
| `SHELLSCRIPT` | シェルスクリプトのリソースを表します。 |
| `OTHERS` | 上記以外のリソースを表します。 |

#### メソッド概要

| メソッド名 | 概要 |
|:--|:--|
| `getName()` | リソースの名前を返します。 |
| `getResourceTypeByName(String name)` | 名前からリソースの種類を取得します。一致するものがない場合はnullを返します。 |
| `getExts()` | リソースの拡張子を返します。 |
| `matchesExt(String target)` | 指定された拡張子がリソースの拡張子と一致するかどうかを返します。 |
| `getMatchExt(String target)` | 指定された拡張子がリソースの拡張子と一致する場合、その拡張子を返します。一致するものがない場合はnullを返します。 |


---
# pro.kensait.brain2doc.process.**ConsoleProgressTask**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/ConsoleProgressTask.java

## クラス概要

`ConsoleProgressTask`は、コンソールに進行状況を表示するためのクラスです。`Runnable`インターフェースを実装しており、マルチスレッドでの実行を可能にしています。開始信号の後に、進行状況が終了するまで等間隔でバー(`=`)を表示します。進行状況が終了した際には、終了バー(`=> `)と`done!`を表示します。

#### メンバ概要

|メンバ名|型|説明|
|---|---|---|
| `DONE_BAR` | `String` | 進行状況が終了した際に表示する文字列 |
| `BAR` | `String` | 進行状況を表示する際に使用するバーの文字列 |
| `INTERVAL` | `int` | バーを表示する間隔（秒） |
| `startSignal` | `CountDownLatch` | 開始信号を管理するオブジェクト |
| `isDone` | `boolean` | 進行状況が終了しているかどうかを判定するフラグ |

#### メソッド概要

|メソッド名|説明|
|---|---|
| `ConsoleProgressTask(CountDownLatch startSignal, boolean isDone)` | コンストラクタ。開始信号と進行状況の終了フラグを初期化 |
| `getStartSignal()` | 現在の開始信号を取得 |
| `setStartSignal(CountDownLatch startSignal)` | 開始信号を設定 |
| `isDone()` | 進行状況が終了しているかどうかを返す |
| `setDone(boolean isDone)` | 進行状況の終了フラグを設定 |
| `run()` | 開始信号の後に、進行状況が終了するまで等間隔でバーを表示。進行状況が終了した際には、終了バーと`done!`を表示 |

---
# pro.kensait.brain2doc.process.**Flow**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/Flow.java

## クラス概要

### クラス名: Flow
このクラスは、ファイルの読み取りからOpenAIへの問い合わせ、結果の処理といった一連の流れを制御します。

#### メンバ概要

| メンバ名 | 説明 |
| --- | --- |
| `ZIP_FILE_EXT` | .zipファイルの拡張子 |
| `SUCCESS_MESSAGE` | 成功メッセージ |
| `TIMEOUT_MESSAGE` | タイムアウトメッセージ |
| `CLIENT_ERROR_MESSAGE` | クライアントエラーメッセージ |
| `INVALID_API_KEY_MESSAGE` | APIキー無効メッセージ |
| `INSUFFICIENT_QUOTA_MESSAGE` | クオータ不足メッセージ |
| `TOKEN_LIMIT_OVER_MESSAGE` | トークンリミット超過メッセージ |
| `RATE_LIMIT_EXCEEDED_MESSAGE` | レート制限超過メッセージ |
| `EXTRACT_TOKEN_COUNT_REGEX` | トークン数抽出の正規表現 |
| `PROMPT_HEADING` | プロンプトの見出し |
| `PROCESS_PROGRESS_HEADING` | プロセス進行の見出し |
| `REPORT_TITLE` | レポートのタイトル |
| `REPORT_TABLE_DIVIDER` | レポートテーブルの区切り |
| `param` | パラメータオブジェクト |
| `reportList` | レポートリスト |

#### メソッド概要

| メソッド名 | 説明 |
| --- | --- |
| `init(Parameter paramValues)` | パラメータの初期化とレポートリストのクリア |
| `getReportList()` | レポートリストの取得 |
| `startAndFork()` | ファイル処理の開始と分岐 |
| `walkDirectory(Path srcPath)` | ディレクトリの走査 |
| `readNormalFile(Path inputFilePath)` | 通常のファイルの読み取り |
| `readZipFile(Path srcPath)` | .zipファイルの読み取り |
| `mainProcess(Path inputFilePath, List<String> inputFileLines)` | メインプロセス |
| `askToOpenAi(List<String> inputFileLines, String inputFileContent, int splitConut, int prevSplitCount, int tryCount, CountDownLatch startSignal, Runnable startProgressTask)` | OpenAIへの問い合わせ |
| `extractToken(String content, String message)` | トークンの抽出 |
| `extractNameWithoutExt(String fileName, String ext)` | 拡張子なしのファイル名の抽出 |
| `toStringFromStrList(List<String> strList)` | 文字列リストから文字列への変換 |
| `toChoicesFromResponce(SuccessResponseBody responseBody)` | レスポンスボディから選択肢への変換 |
| `toLineListFromChoices(List<String> responseChoices)` | 選択肢から行リストへの変換 |
| `write(String responseContent)` | 書き込み |
| `printPrompt(Prompt prompt)` | プロンプトの表示 |
| `addReport(Path inputFilePath, String message, int resuestTokenCount, int responseTokenCount, long interval)` | レポートの追加 |

---
# pro.kensait.brain2doc.process.**SplitUtil**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/SplitUtil.java

## クラス概要

`SplitUtil`クラスは、リストを分割するユーティリティクラスです。このクラスは以下のメソッドを持っています。

| メソッド名 | 概要 |
|---|---|
| `split` | 引数として受け取ったリストを指定された数で分割し、分割後のリストをリストとして返します。 |
| `calcSplitCount` | トークン数とトークン数の上限を引数にとり、トークン数と上限から分割数を計算し、分割数を返します。 |

### splitメソッド
このメソッドは、リストと分割数を引数にとり、リストを分割します。分割後の各リストの最大行数は、元のリストの行数を分割数で割った数に1を加えたものです。分割後のリストは元のリストの順番を保持します。

### calcSplitCountメソッド
このメソッドは、トークン数とトークン数の上限を引数にとり、これらから分割数を計算します。トークン数と上限から計算した数に0.5を加算し、さらに切り上げることで、分割数を計算します。

---
# pro.kensait.brain2doc.process.**TemplateAttacher**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/TemplateAttacher.java

## クラス概要

`TemplateAttacher`は、特定のテンプレートをファイルにアタッチするためのクラスです。

このクラスには、次のメソッドと内部クラスが含まれます。

#### メソッド

| メソッド名 | 概要 |
| --- | --- |
| `attach` | テンプレートをファイルにアタッチします。 |
| `getScaleString` | 出力サイズタイプに対応する文字列を取得します。 |
| `getTableTemplateStr` | テーブルテンプレート文字列を取得します。 |

#### 内部クラス

| クラス名 | 概要 |
| --- | --- |
| `Prompt` | システムメッセージ、アシスタントメッセージ、ユーザーメッセージ、ユーザーメッセージラインを保持します。 |


---
# pro.kensait.brain2doc.transform.**GenericTransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/GenericTransformStrategy.java

## クラス概要

`GenericTransformStrategy`は`TransformStrategy`インターフェースを実装したクラスです。

### メソッド

|メソッド名|説明|
|---------|----|
|transform|入力ファイルパス、リクエスト内容、レスポンス行のリスト、シーケンス番号を引数に取り、Markdown形式の出力内容を返します。この出力内容は、ファイル名、ファイルパス、レスポンス内容を含んでいます。また、シーケンス番号が1以外の場合、ファイル名の後にシーケンス番号を追加します。|


---
# pro.kensait.brain2doc.transform.**JavaGeneralTransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/JavaGeneralTransformStrategy.java

## クラス概要

クラス名: `JavaGeneralTransformStrategy`

パッケージ名: `pro.kensait.brain2doc.transform`

このクラスは`TransformStrategy`インターフェースを実装しており、Javaソースコードをマークダウン形式に変換する役割を持っています。主な処理は`transform`メソッド内で行われ、入力されたファイルパスとリクエストコンテンツ、レスポンスラインのリスト、シーケンス番号を元にマークダウン形式の文字列を生成します。

#### メンバの概要

| メソッド名 | 概要 |
|---|---|
| `transform` | 入力されたファイルパス、リクエストコンテンツ、レスポンスラインのリスト、シーケンス番号を元にマークダウン形式の文字列を生成します。 |
| `getPackageName` | リクエストコンテンツからパッケージ名を取得します。正規表現を使用してパッケージ宣言の行を探し、その行からパッケージ名を抽出します。 |


---
# pro.kensait.brain2doc.transform.**TransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/TransformStrategy.java

## クラス概要

クラス名: TransformStrategyこのインターフェースは、リソースのタイプと生成タイプに基づいて、適切な変換戦略を提供します。

### メソッドの概要

|メソッド名|説明|
|---|---|
|transform|指定された入力ファイルパス、リクエストコンテンツ、レスポンスコンテンツリスト、シーケンス番号を使用して変換を行います。|
|getOutputStrategy|リソースタイプと生成タイプに基づいて適切なTransformStrategyを返します。リソースタイプがJAVAの場合はJavaGeneralTransformStrategyを、それ以外の場合はGenericTransformStrategyを返します。|

---
