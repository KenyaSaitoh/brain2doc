# pro.kensait.brain2doc.common.**Const**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/common/Const.java

## クラス概要

```java
package pro.kensait.brain2doc.common;

public class Const {
    public static final String OUTPUT_FILE_EXT = ".md";
    public static final String LINE_SEP = System.getProperty("line.separator");
    public static final String MARKDOWN_HEADING = "# ";
    public static final String MARKDOWN_HORIZON = "---";
}
```

#### メンバ概要

|メンバ|概要|
|---|---|
|OUTPUT_FILE_EXT|".md"を表す定数。Markdownファイルの拡張子を示します。|
|LINE_SEP|システムプロパティから取得した行区切り文字を表す定数。|
|MARKDOWN_HEADING|"# "を表す定数。Markdownの見出しを示します。|
|MARKDOWN_HORIZON|"---"を表す定数。Markdownの水平線を示します。|

---
# pro.kensait.brain2doc.common.**Util**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/common/Util.java

## クラス概要

```java
package pro.kensait.brain2doc.common;

public class Util {
    public  static void sleepAWhile(int retryInterval) {
        try {
            Thread.sleep(retryInterval * 1000);
        } catch(InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}
```

上記のJavaクラス「Util」は、特定の時間だけスリープするためのメソッドを持つユーティリティクラスです。

#### メンバ概要

|メンバ名|説明|
|---|---|
|sleepAWhile(int retryInterval)|引数として与えられたretryInterval（秒）だけスリープします。スリープ中に割り込みが発生した場合は、RuntimeExceptionをスローします。|


---
# pro.kensait.brain2doc.config.**ConstMapHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/ConstMapHolder.java

## クラス概要

このクラスは`pro.kensait.brain2doc.config`パッケージに属しており、Yamlフォーマットの設定ファイルを読み込み、その内容を保持する役割を持っています。

#### メンバの概要

| メンバ名 | メンバタイプ | 概要 |
|:----------|:-------------|:-----|
| map | static Map | Yamlファイルの内容を保持するためのマップです。`Thread.currentThread().getContextClassLoader().getResourceAsStream("const.yaml")`によってYamlファイルを読み込み、その内容をマップとして保持します。|
| getConstMap() | static method | 保持しているマップを返すメソッドです。 |

---
# pro.kensait.brain2doc.config.**DefaultValueHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/DefaultValueHolder.java

## クラス概要

**クラス名:** DefaultValueHolder

**パッケージ:** pro.kensait.brain2doc.config

**概要:** パラメータのデフォルト値をプロパティから読み込み、保持するクラスです。

**メソッド:**

|メソッド名|説明|
|:---|:---|
|getProperty|指定されたキーに対応するプロパティの値を取得します。|

**例外:**

|例外|説明|
|:---|:---|
|IOException|プロパティファイルの読み込みに失敗した場合にスローされます。|

**注意:** プロパティファイルは "default.properties" として、同一クラスローダー下に存在する必要があります。

---
# pro.kensait.brain2doc.config.**HelpMessageHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/HelpMessageHolder.java

## クラス概要

`HelpMessageHolder`クラスは、ヘルプメッセージを所定のテキストファイルから読み込んで、言語種別ごとに保持するクラスです。

#### メンバ概要

| メンバ | 説明 |
| --- | --- |
| HELP_FILE_PREFIX | ヘルプファイルのプレフィックス |
| HELP_FILE_EXT | ヘルプファイルの拡張子 |
| helpHolder | HelpMessageHolderのインスタンス |
| helpMessage | ヘルプメッセージを保持するList |
| getInstance() | HelpMessageHolderのインスタンスを取得する |
| getHelpMessage(String lang) | 指定した言語のヘルプメッセージを取得する |

特に `getHelpMessage(String lang)` メソッドでは、指定した言語のヘルプメッセージをテキストファイルから読み込み、List形式で返却します。この際、ヘルプメッセージは初回読み込み時のみファイルから取得し、2回目以降は保持したメッセージを返却します。

---
# pro.kensait.brain2doc.config.**TemplateHolder**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/config/TemplateHolder.java

## クラス概要

`TemplateHolder`クラスは、プロンプトのテンプレートを保持するクラスです。テンプレートはyaml形式のファイルから読み込まれ、マップとして保持されます。テンプレートファイルはロケールに基づいて選ばれ、外部から指定されたテンプレートファイルがある場合はそれとマージされます。

#### メンバの概要

| メンバ名 | 概要 |
| :--- | :--- |
| TEMPLATE_FILE_PREFIX | テンプレートファイル名のプレフィクス |
| TEMPLATE_FILE_EXT | テンプレートファイルの拡張子 |
| templateHolder | TemplateHolderクラスのインスタンス |
| templateMap | テンプレートの内容を保持するマップ |
| getInstance() | TemplateHolderのインスタンスを取得するメソッド |
| getTemplateMap() | テンプレートの内容を保持するマップを取得するメソッド |
| merge() | 2つのマップをマージするメソッド |

---
# pro.kensait.brain2doc.exception.**OpenAIClientException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIClientException.java

## クラス概要

本クラスは、OpenAIのクライアントエラーを処理する例外クラス「OpenAIClientException」です。RuntimeExceptionを継承し、ClientErrorBodyオブジェクトを持つことが特徴です。

#### メンバの概要

|メソッド名|説明|
|:--|:--|
|`OpenAIClientException()`|引数なしのコンストラクタ。|
|`OpenAIClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)`|メッセージ、原因、抑制有効、スタックトレース書き込み可能かを引数に持つコンストラクタ。|
|`OpenAIClientException(String message, Throwable cause)`|メッセージと原因を引数に持つコンストラクタ。|
|`OpenAIClientException(String message)`|メッセージを引数に持つコンストラクタ。|
|`OpenAIClientException(Throwable cause)`|原因を引数に持つコンストラクタ。|
|`OpenAIClientException(ClientErrorBody clientErrorBody)`|ClientErrorBodyを引数に持つコンストラクタ。|
|`getClientErrorBody()`|ClientErrorBodyオブジェクトを取得する。|

---
# pro.kensait.brain2doc.exception.**OpenAIInsufficientQuotaException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIInsufficientQuotaException.java

## クラス概要

`OpenAIInsufficientQuotaException`クラスは、OpenAIのクライアントでエラーが発生した際に投げられる例外を扱います。このクラスは、`OpenAIClientException`クラスを継承しています。

#### メンバの概要

| メンバ | 説明 |
| --- | --- |
| `ClientErrorBody clientErrorBody` | エラーメッセージとエラーコードを含むオブジェクト |
| `OpenAIInsufficientQuotaException()` | 引数なしのコンストラクタ |
| `OpenAIInsufficientQuotaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)` | メッセージ、原因、抑制の有効無効、スタックトレースの書き込みの有無を引数に取るコンストラクタ |
| `OpenAIInsufficientQuotaException(String message, Throwable cause)` | メッセージと原因を引数に取るコンストラクタ |
| `OpenAIInsufficientQuotaException(String message)` | メッセージを引数に取るコンストラクタ |
| `OpenAIInsufficientQuotaException(Throwable cause)` | 原因を引数に取るコンストラクタ |
| `OpenAIInsufficientQuotaException(ClientErrorBody clientErrorBody)` | エラーメッセージとエラーコードを含むオブジェクトを引数に取るコンストラクタ |
| `ClientErrorBody getClientErrorBody()` | エラーメッセージとエラーコードを含むオブジェクトを取得するメソッド |


---
# pro.kensait.brain2doc.exception.**OpenAIInvalidAPIKeyException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIInvalidAPIKeyException.java

## クラス概要

OpenAIInvalidAPIKeyExceptionクラスは、OpenAIClientExceptionクラスを継承したクラスです。OpenAIのAPIキーが無効な場合にこの例外がスローされます。

#### メンバの概要

| メソッド名 | メソッドの概要 |
|:---|:---|
| OpenAIInvalidAPIKeyException() | デフォルトのコンストラクタ。スーパークラスのデフォルトコンストラクタを呼び出します。 |
| OpenAIInvalidAPIKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) | message, cause, enableSuppression, writableStackTraceを引数に取るコンストラクタ。スーパークラスの同名のコンストラクタを呼び出します。 |
| OpenAIInvalidAPIKeyException(String message, Throwable cause) | messageとcauseを引数に取るコンストラクタ。スーパークラスの同名のコンストラクタを呼び出します。 |
| OpenAIInvalidAPIKeyException(String message) | messageを引数に取るコンストラクタ。スーパークラスの同名のコンストラクタを呼び出します。 |
| OpenAIInvalidAPIKeyException(Throwable cause) | causeを引数に取るコンストラクタ。スーパークラスの同名のコンストラクタを呼び出します。 |
| OpenAIInvalidAPIKeyException(ClientErrorBody clientErrorBody) | clientErrorBodyを引数に取るコンストラクタ。clientErrorBodyフィールドに引数を設定します。 |
| getClientErrorBody() | clientErrorBodyフィールドの値を取得します。 |


---
# pro.kensait.brain2doc.exception.**OpenAIRateLimitExceededException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAIRateLimitExceededException.java

## クラス概要

このクラスは`OpenAIRateLimitExceededException`という名前で、`OpenAIClientException`クラスを継承しています。OpenAI使用時にレート制限を超えた場合に使用される例外クラスです。

#### メンバ概要

| メンバ | 説明 |
| --- | --- |
| `ClientErrorBody clientErrorBody` | ClientErrorBody クラスのインスタンス。エラー情報を保持しています。 |
| `OpenAIRateLimitExceededException()` | デフォルトコンストラクタ。スーパークラスの同名コンストラクタを呼び出します。 |
| `OpenAIRateLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)` | スーパークラスの同名コンストラクタを呼び出します。引数はエラーメッセージ、原因となる例外、例外抑制有効フラグ、スタックトレース書き込み可能フラグです。 |
| `OpenAIRateLimitExceededException(String message, Throwable cause)` | スーパークラスの同名コンストラクタを呼び出します。引数はエラーメッセージと原因となる例外です。 |
| `OpenAIRateLimitExceededException(String message)` | スーパークラスの同名コンストラクタを呼び出します。引数はエラーメッセージです。 |
| `OpenAIRateLimitExceededException(Throwable cause)` | スーパークラスの同名コンストラクタを呼び出します。引数は原因となる例外です。 |
| `OpenAIRateLimitExceededException(ClientErrorBody clientErrorBody)` | ClientErrorBody型の引数を持つコンストラクタ。clientErrorBodyフィールドに値をセットします。 |
| `ClientErrorBody getClientErrorBody()` | clientErrorBodyフィールドのゲッターメソッド。clientErrorBodyの値を取得します。 |


---
# pro.kensait.brain2doc.exception.**OpenAITokenLimitOverException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/OpenAITokenLimitOverException.java

## クラス概要

`OpenAITokenLimitOverException`は、OpenAIのトークン制限超過を表す例外クラスです。`OpenAIClientException`を継承しており、クライアントエラーボディを保持することが可能です。

#### メンバ概要

| メンバ名 | 説明 |
|:--------:|:----|
| clientErrorBody | クライアントエラーボディを保持します。|

#### メソッド概要

| メソッド名 | 説明 |
|:--------:|:----|
| OpenAITokenLimitOverException() | デフォルトコンストラクタ。|
| OpenAITokenLimitOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) | 例外メッセージ、原因、抑制の有効化、書き込み可能スタックトレースを指定して例外を生成します。|
| OpenAITokenLimitOverException(String message, Throwable cause) | 例外メッセージと原因を指定して例外を生成します。|
| OpenAITokenLimitOverException(String message) | 例外メッセージを指定して例外を生成します。|
| OpenAITokenLimitOverException(Throwable cause) | 原因を指定して例外を生成します。|
| OpenAITokenLimitOverException(ClientErrorBody clientErrorBody) | クライアントエラーボディを指定して例外を生成します。|
| getClientErrorBody() | 保持しているクライアントエラーボディを取得します。|

---
# pro.kensait.brain2doc.exception.**RetryCountOverException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/RetryCountOverException.java

## クラス概要

パッケージ名: pro.kensait.brain2doc.exception

クラス名: RetryCountOverException

継承元: RuntimeException

### メンバの概要

|メンバ名|種類|概要|
|---|---|---|
|RetryCountOverException()|コンストラクタ|何も引数を取らない|
|RetryCountOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)|コンストラクタ|メッセージ、原因、抑制の有無、スタックトレースの書き込み可能性を引数に取る|
|RetryCountOverException(String message, Throwable cause)|コンストラクタ|メッセージと原因を引数に取る|
|RetryCountOverException(String message)|コンストラクタ|メッセージのみを引数に取る|
|RetryCountOverException(Throwable cause)|コンストラクタ|原因のみを引数に取る|

---
# pro.kensait.brain2doc.exception.**TimeoutException**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/exception/TimeoutException.java

## クラス概要

- **パッケージ名:** pro.kensait.brain2doc.exception
- **クラス名:** TimeoutException
- **親クラス:** RuntimeException

#### メンバの概要

| メソッド名 | パラメータ | 概要 |
| ------ | ------ | ------ |
| TimeoutException() | - | 基本コンストラクタ。何も特別な動作を行わない。 |
| TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) | メッセージ:エラーメッセージ、原因:スロー可能な原因、抑制可能:例外抑制の有効/無効、スタックトレース書き込み可能:スタックトレースの書き込み可/不可 | メッセージ、原因、例外抑制の有効/無効、およびスタックトレースの書き込み可/不可に基づいて、新しいランタイム例外を構築します。 |
| TimeoutException(String message, Throwable cause) | メッセージ:エラーメッセージ、原因:スロー可能な原因 | 指定された詳細メッセージと原因を持つ新しいランタイム例外を構築します。 |
| TimeoutException(String message) | メッセージ:エラーメッセージ | 指定された詳細メッセージを持つ新しいランタイム例外を構築します。 |
| TimeoutException(Throwable cause) | 原因:スロー可能な原因 | 指定された原因を持つ新しいランタイム例外を構築します。 |


---
# pro.kensait.brain2doc.**Main**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/Main.java

## クラス概要

`Main`クラスは、`brain2doc`アプリケーションを起動するメインクラスです。

#### メンバの概要

| メンバ名  | 概要 |
|:------------- |:---------------|
| `main(String[] args)`      | メインメソッドです。引数がない場合やヘルプが求められた場合はヘルプメッセージを表示します。また、パラメータをセットアップし、処理フローを初期化して開始します。OpenAI関連のエラーが発生した場合やリトライカウントがオーバーした場合はエラーメッセージを表示して終了します。 |
| `printHelpMessage(String lang)`      | ヘルプメッセージを表示するメソッドです。指定された言語でヘルプメッセージを表示します。      |
| `printBanner()` | バナーメッセージを表示するメソッドです。|
| `printReport()` | 処理結果のレポートを表示するメソッドです。レポートがない場合はその旨を表示します。|


---
# pro.kensait.brain2doc.openai.**ApiClient**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ApiClient.java

## クラス概要

以下の表は、`ApiClient` クラスの各メンバの概要を説明しています。

| メンバ名 | 説明 |
|------|------|
| ask | OpenAIのAPIを呼び出すメソッドです。引数には各種メッセージ、APIのURL、モデル名、APIキー、プロキシURL、接続タイムアウト、リクエストタイムアウト、リトライカウント、リトライ間隔が必要です。APIキーが無効、クォータ不足、トークンリミットオーバー、レートリミットオーバーの場合は例外を投げます。 |
| sendRequest | OpenAIのAPIを実際に呼び出すメソッドです。引数にはHttpClientオブジェクトとHttpRequestオブジェクトが必要です。このメソッドはHttpRequestを送信し、HTTPサーバーを同期で呼び出します。 |
| getRequestJson | リクエストのJSONを文字列に変換するメソッドです。引数にはRequestBodyオブジェクトが必要です。 |
| getResponseBody | レスポンスのJSONをJavaオブジェクトに変換するメソッドです。引数にはクラス型とレスポンス文字列が必要です。 |
| createHttpClient | HttpClientを生成して返すメソッドです。引数にはプロキシURLと接続タイムアウトが必要です。プロキシの設定があった場合はそれも考慮します。 |

このクラスはOpenAIのAPIを呼び出すために使用されます。メソッドを呼び出す際には適切な引数が必要で、例外が発生する可能性があります。また、このクラスはプロキシ設定も考慮してHttpClientを生成します。

---
# pro.kensait.brain2doc.openai.**ApiResult**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ApiResult.java

## クラス概要

ApiResultクラスは、APIの結果を表すクラスです。このクラスは、成功したレスポンスの本文と、インターバル（長さ）の2つのメンバーを持っています。

#### メンバの概要

| メンバ名 | タイプ | 説明 |
| :--- | :--- | :--- |
| responseBody | SuccessResponseBody | 成功したレスポンスの本文 |
| interval | long | インターバル（長さ） |

#### メソッドの概要

| メソッド名 | 戻り値のタイプ | 説明 |
| :--- | :--- | :--- |
| getResponseBody() | SuccessResponseBody | responseBodyメンバの値を取得 |
| getInterval() | long | intervalメンバの値を取得 |
| toString() | String | クラスの文字列表現を返す |

---
# pro.kensait.brain2doc.openai.**Choice**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Choice.java

## クラス概要

`Choice`クラスは、インデックス(`index`)、メッセージ(`message`)、終了理由(`finishReason`)の3つのメンバを持つクラスです。各メンバは独自のゲッターとセッターを持ち、オブジェクトの状態を管理します。

#### メンバ概要

| メンバ名 | 型 | 説明 |
| --- | --- | --- |
| index | Integer | 選択肢のインデックス |
| message | Message | メッセージオブジェクト |
| finishReason | String | 終了理由 |

#### メソッド概要

このクラスには以下の一般的なメソッドが含まれています。

- `getIndex()`: インデックスを取得します。
- `setIndex(Integer index)`: インデックスを設定します。
- `getMessage()`: メッセージオブジェクトを取得します。
- `setMessage(Message message)`: メッセージオブジェクトを設定します。
- `getFinishReason()`: 終了理由を取得します。
- `setFinishReason(String finishReason)`: 終了理由を設定します。
- `toString()`: オブジェクトの文字列表現を取得します。

---
# pro.kensait.brain2doc.openai.**ClientErrorBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/ClientErrorBody.java

## クラス概要

このクラスは、`ClientErrorBody`という名前で、クライアントエラーの情報を持つ構造体です。このクラスは、`Error`型の`error`というフィールドを持っています。また、このクラスは`getError`と`setError`というメソッドを持っており、それぞれエラーの取得と設定を行います。

#### メンバ概要

| メンバ名 | メンバタイプ | 概要 |
|---|---|---|
| error | `Error`型のフィールド | エラー情報を保持するフィールド |
| ClientErrorBody() | コンストラクタ | errorフィールドを初期化しないコンストラクタ |
| ClientErrorBody(Error error) | コンストラクタ | errorフィールドを引数で初期化するコンストラクタ |
| getError() | メソッド | errorフィールドの値を取得するメソッド |
| setError(Error error) | メソッド | errorフィールドの値を設定するメソッド |
| toString() | メソッド | クラスの文字列表現を返すメソッド |


---
# pro.kensait.brain2doc.openai.**Error**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Error.java

## クラス概要

`Error`クラスは、エラーメッセージやエラータイプ、パラメータ、コードを保持するクラスです。それぞれのフィールドには、対応するgetterとsetterメソッドが提供されています。また、`toString`メソッドをオーバーライドして、エラー情報を文字列として出力します。

#### メンバの概要

| メンバ名 | 役割 |
|:-----------|:------|
| message | エラーメッセージを保持するフィールド |
| type | エラータイプを保持するフィールド |
| param | パラメータを保持するフィールド |
| code | コードを保持するフィールド |
| getMessage | messageフィールドの値を取得するメソッド |
| setMessage | messageフィールドの値を設定するメソッド |
| getType | typeフィールドの値を取得するメソッド |
| setType | typeフィールドの値を設定するメソッド |
| getParam | paramフィールドの値を取得するメソッド |
| setParam | paramフィールドの値を設定するメソッド |
| getCode | codeフィールドの値を取得するメソッド |
| setCode | codeフィールドの値を設定するメソッド |
| toString | エラー情報を文字列として出力するメソッド |

---
# pro.kensait.brain2doc.openai.**Message**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Message.java

## クラス概要

このクラスはメッセージを表現するためのもので、roleとcontentの二つのフィールドを持っています。また、このクラスは以下のメソッドを提供します。

#### メンバ概要

|メンバ名|概要|
|---|---|
|role|メッセージの役割を表すフィールドです。|
|content|メッセージの内容を表すフィールドです。|
|Message()|デフォルトコンストラクタです。|
|Message(String role, String content)|roleとcontentを引数に取るコンストラクタです。|
|getRole()|roleフィールドのゲッターメソッドです。|
|setRole(String role)|roleフィールドのセッターメソッドです。|
|getContent()|contentフィールドのゲッターメソッドです。|
|setContent(String content)|contentフィールドのセッターメソッドです。|
|toString()|メッセージのroleとcontentを文字列として返すメソッドです。|


---
# pro.kensait.brain2doc.openai.**RequestBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/RequestBody.java

## クラス概要

`RequestBody`クラスは、`model`、`messages`、`temperature`の三つのメンバ変数をもつJavaのクラスです。各メンバ変数は、以下のとおりです。

#### メンバ変数

|メンバ|型|説明|
|:--|:--|:--|
|model|String|モデル名称|
|messages|List<Message>|メッセージリスト|
|temperature|Float|温度|

#### メソッド

各メンバ変数に対して、getterおよびsetterが定義されています。また、`toString`メソッドがオーバーライドされており、クラスの内容を文字列として返します。

- `getModel()`: modelの値を取得します。
- `setModel(String model)`: modelの値を設定します。
- `getMessages()`: messagesの値を取得します。
- `setMessages(List<Message> messages)`: messagesの値を設定します。
- `getTemperature()`: temperatureの値を取得します。
- `setTemperature(Float temperature)`: temperatureの値を設定します。
- `toString()`: クラスの内容を文字列として返します。

---
# pro.kensait.brain2doc.openai.**RequestFullBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/RequestFullBody.java

## クラス概要

`RequestFullBody`クラスは、OpenAIのリクエストボディを表現するクラスです。以下は、クラスのメンバ変数とその概要です。

#### メンバ変数

| メンバ名 | データ型 | 概要 |
| --- | --- | --- |
| model | String | モデルの名称をセットする。 |
| messages | List<Message> | メッセージのリストをセットする。 |
| temperature | Float | テンプレートの温度をセットする。 |
| topP | Float | トップPの値をセットする。 |
| n | Integer | nの値をセットする。 |
| stream | Boolean | ストリームの値をセットする。 |
| stop | List<String> | ストップのリストをセットする。 |
| maxTokens | Integer | 最大トークン数をセットする。 |
| presencePenalty | Float | プレゼンスペナルティの値をセットする。 |
| frequencyPenalty | Float | フリーケンシーペナルティの値をセットする。 |
| logitBias | Map<Object, Object> | ロジットバイアスのマップをセットする。 |

各メンバには、ゲッターとセッターのメソッドが提供されています。

---
# pro.kensait.brain2doc.openai.**StaticProxySelector**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/StaticProxySelector.java

## クラス概要

`StaticProxySelector`クラスは、特定のプロキシ設定を強制するためのクラスです。Javaの`ProxySelector`を拡張し、選択したプロキシ設定を返すことができます。

#### メンバの概要

| メンバ | 概要 |
| --- | --- |
| `proxyList` | プロキシ設定のリスト。コンストラクタで受け取ったプロキシタイプとアドレスを元に作成されます。 |
| `StaticProxySelector(Proxy.Type proxyType, InetSocketAddress proxyAddress)` | コンストラクタ。プロキシタイプとアドレスを受け取り、その情報を元にプロキシリストを作成します。 |
| `select(URI uri)` | オーバーライドメソッド。URIに関係なく、常に同じプロキシ設定のリストを返します。 |
| `connectFailed(URI uri, SocketAddress sa, IOException ioe)` | オーバーライドメソッド。プロキシへの接続が失敗した場合に呼び出され、実行時例外をスローします。 |

---
# pro.kensait.brain2doc.openai.**SuccessResponseBody**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/SuccessResponseBody.java

## クラス概要

`SuccessResponseBody`クラスは、レスポンスのボディ部分を表すクラスです。以下のメンバを持っています。

#### メンバの概要

|メンバ名|型|説明|
|---|---|---|
|id|String|IDを表すフィールド|
|object|String|オブジェクトを表すフィールド|
|created|Long|作成日時を表すフィールド|
|model|String|モデルを表すフィールド|
|choices|List<Choice>|選択肢を表すフィールド|
|usage|Usage|使用方法を表すフィールド|
|error|Error|エラーを表すフィールド|

各メンバへのアクセスは、getterおよびsetterメソッドを通じて行います。また、`toString`メソッドでオブジェクトの文字列表現を取得できます。

---
# pro.kensait.brain2doc.openai.**Usage**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/openai/Usage.java

## クラス概要

`Usage`クラスは、トークンの使用状況を表すクラスです。以下の3つのフィールドを持っています。

| メンバ名 | データ型 | 概要 |
| --- | --- | --- |
| promptTokens | Integer | プロンプトトークンの数 |
| completionTokens | Integer | 完了トークンの数 |
| totalTokens | Integer | トータルトークンの数 |

### メソッド概要

- `getPromptTokens` : プロンプトトークンの数を取得します。
- `setPromptTokens` : プロンプトトークンの数を設定します。
- `getCompletionTokens` : 完了トークンの数を取得します。
- `setCompletionTokens` : 完了トークンの数を設定します。
- `getTotalTokens` : トータルトークンの数を取得します。
- `setTotalTokens` : トータルトークンの数を設定します。
- `equals` : 他のオブジェクトと比較し、等価性を判断します。
- `hashCode` : ハッシュコードを生成します。
- `toString` : オブジェクトの文字列表現を生成します。

---
# pro.kensait.brain2doc.params.**GenerateType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/GenerateType.java

## クラス概要

`GenerateType`は、生成するドキュメントの種類を表すEnumクラスです。以下の4つの種類が定義されています。

- SPEC
- SUMMARY
- REVIEW
- OTHERS

#### メンバの概要

| メンバ名 | メンバ種別 | 概要 |
| :------- | :--------- | :--- |
| SPEC     | Enum定数   | 仕様書を生成するための定数です |
| SUMMARY  | Enum定数   | 要約を生成するための定数です |
| REVIEW   | Enum定数   | レビューを生成するための定数です |
| OTHERS   | Enum定数   | その他のドキュメントを生成するための定数です |
| name     | インスタンス変数 | 定数の名前を保持します |
| getName  | メソッド   | 定数の名前を取得します |
| getGenerateTypeByName | メソッド | 引数に指定された名前の定数を取得します。該当する定数がなければnullを返します |

---
# pro.kensait.brain2doc.params.**OutputScaleType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/OutputScaleType.java

## クラス概要

`pro.kensait.brain2doc.params`パッケージに属する`OutputScaleType`という名前のenumクラスです。このクラスは、出力スケールのサイズを制御するためのもので、SMALL、MEDIUM、LARGE、NOLIMITの4つの値を持ちます。各値は名称と文字サイズを保持しています。

#### メンバ一覧

| メンバ名 | データ型 | 概要 |
| --- | --- | --- |
| SMALL | OutputScaleType | 名称が"small"、文字サイズが50の出力スケールタイプ |
| MEDIUM | OutputScaleType | 名称が"medium"、文字サイズが200の出力スケールタイプ |
| LARGE | OutputScaleType | 名称が"large"、文字サイズが500の出力スケールタイプ |
| NOLIMIT | OutputScaleType | 名称が"nolimit"、文字サイズがnullの出力スケールタイプ |
| name | String | 出力スケールタイプの名称 |
| charSize | Integer | 出力スケールタイプの文字サイズ |

#### メソッド一覧

| メソッド名 | 戻り値の型 | 概要 |
| --- | --- | --- |
| getName() | String | 出力スケールタイプの名称を取得 |
| getCharSize() | Integer | 出力スケールタイプの文字サイズを取得 |
| getOutputScaleTypeByName(String name) | OutputScaleType | 引数に指定した名称に対応する出力スケールタイプを取得 |


---
# pro.kensait.brain2doc.params.**Parameter**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/Parameter.java

## クラス概要

該当クラスは`Parameter`という名前で、各種設定やパラメータを管理するクラスです。

#### メンバ一覧

| メンバ名            | 概要                                                                                     |
|---------------------|------------------------------------------------------------------------------------------|
| openaiURL           | OpenAIのURL（デフォルト値はプロパティファイルから取得）                                     |
| openaiModel         | OpenAIのモデル（デフォルト値はプロパティファイルから取得）                                   |
| openaiApikey        | OpenAIのAPIキー（デフォルト値は環境変数から取得）                                             |
| resourceType        | リソースタイプ（デフォルト値はプロパティファイルから取得）                                    |
| generateType        | 生成タイプ（デフォルト値はプロパティファイルから取得）                                        |
| genTable            | 生成テーブル名（任意指定）                                                                   |
| fields              | フィールド名（任意指定）                                                                     |
| outputScaleType     | 出力スケールタイプ（デフォルト値はプロパティファイルから取得）                                 |
| srcPath             | ソースパス（指定必須）                                                                       |
| srcRegex            | ソースパスの正規表現（任意指定）                                                              |
| destFilePath        | 出力ファイルパス（デフォルト値はソースパスと同じディレクトリの固定ファイル名）                   |
| locale              | ロケール（デフォルト値はプロパティファイルから取得）                                           |
| templateFile        | テンプレートファイル（任意指定）                                                              |
| maxSplitCount       | 最大分割数                                                                                    |
| proxyURL            | プロキシURL（任意指定）                                                                       |
| connectTimeout      | 接続タイムアウト（デフォルト値はプロパティファイルから取得）                                    |
| requestTimeout      | リクエストタイムアウト（デフォルト値はプロパティファイルから取得）                               |
| retryCount          | 再試行回数（デフォルト値はプロパティファイルから取得）                                          |
| retryInterval       | 再試行間隔（デフォルト値はプロパティファイルから取得）                                          |
| isAutoSplitMode     | 自動分割モード（デフォルト値false）                                                            |
| printPrompt         | プロンプト表示フラグ                                                                          |

#### メソッド一覧

- `setUp(String[] args)`：パラメータを設定するための静的メソッド。引数は文字列配列。
- `getDefaultOutputFileName(ResourceType resourceType, GenerateType generateType)`：デフォルトの出力ファイル名を取得するための静的メソッド。
- `getCurrentDateTimeStr()`：現在の日時を文字列で取得するための静的メソッド。
- 各メンバのgetterメソッド

クラスのインスタンスは、静的メソッド`setUp(String[] args)`を通じて生成され、その後はgetterメソッドを利用して各メンバの値を取得します。

---
# pro.kensait.brain2doc.params.**PathType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/PathType.java

## クラス概要

`PathType`は、**pro.kensait.brain2doc.params**パッケージに属するenum型クラスです。以下の3つの定数を持っています。

#### メンバ概要

|メンバ名|概要|
|:---:|:---:|
|FILE| ファイルを表す定数|
|DIRECTORY| ディレクトリを表す定数|
|ZIP| ZIPファイルを表す定数|

---
# pro.kensait.brain2doc.params.**ResourceType**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/params/ResourceType.java

## クラス概要

`ResourceType`は、複数のプログラミング言語やファイル形式を表現するための列挙型です。それぞれの`ResourceType`は名前と、それに関連付けられたファイル拡張子の配列を持っています。

#### メソッド

|メソッド名|概要|
|---|---|
|getName|列挙型の名前を返す|
|getResourceTypeByName|指定した名前に一致する列挙型を返す|
|getExts|関連付けられたファイル拡張子の配列を返す|
|matchesExt|指定した拡張子が関連付けられているかどうかを返す|
|getMatchExt|指定した拡張子が関連付けられている場合、その拡張子を返す|

#### 列挙型の値と関連する拡張子

|列挙型の値|関連する拡張子|
|---|---|
|JAVA| .java|
|JAVASCRIPT| .js, .ts|
|PYTHON| .py|
|SQL| .sql|
|PAGE| .html, .htm, .xhtml, .jsp|
|SHELLSCRIPT| .sh, .bash, .ksh, .bash|
|OTHERS| .*|

---
# pro.kensait.brain2doc.process.**ConsoleProgressTask**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/ConsoleProgressTask.java

## クラス概要

`ConsoleProgressTask`クラスは、進捗バーをコンソールに表示するタスクを実行するクラスです。このクラスは`Runnable`インターフェースを実装しており、多重スレッドで実行することが可能です。

#### メンバ概要

|メンバ|型|概要|
|---|---|---|
|DONE_BAR|String|処理が完了したときに表示する文字列|
|BAR|String|進捗バーとして表示する文字列|
|INTERVAL|int|進捗バーの更新間隔（秒）|
|startSignal|CountDownLatch|開始信号を管理するCountDownLatchオブジェクト|
|isDone|boolean|処理が完了したかどうかを示すフラグ|

#### メソッド概要

|メソッド|戻り値|概要|
|---|---|---|
|ConsoleProgressTask(CountDownLatch, boolean)|-|コンストラクタ。開始信号と完了フラグを設定する|
|getStartSignal()|CountDownLatch|開始信号を取得する|
|setStartSignal(CountDownLatch)|void|開始信号を設定する|
|isDone()|boolean|処理が完了したかどうかを確認する|
|setDone(boolean)|void|処理が完了したかどうかを設定する|
|run()|void|進捗バーを表示しながら処理を実行する|


---
# pro.kensait.brain2doc.process.**Flow**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/Flow.java

## クラス概要

`Flow`クラスはプログラムの処理フローを表しています。このクラスは、ファイルやディレクトリを読み込み、OpenAIのAPIに問い合わせを行い、結果を書き出します。

### メンバの概要

|メンバ|説明|
|---|---|
|ZIP_FILE_EXT|ZIPファイルの拡張子を表す定数|
|SUCCESS_MESSAGE|成功時のメッセージを表す定数|
|TIMEOUT_MESSAGE|タイムアウト時のメッセージを表す定数|
|CLIENT_ERROR_MESSAGE|クライアントエラー時のメッセージを表す定数|
|INVALID_API_KEY_MESSAGE|APIキー無効時のメッセージを表す定数|
|INSUFFICIENT_QUOTA_MESSAGE|クオータ不足時のメッセージを表す定数|
|TOKEN_LIMIT_OVER_MESSAGE|トークン制限超過時のメッセージを表す定数|
|RATE_LIMIT_EXCEEDED_MESSAGE|レート制限超過時のメッセージを表す定数|
|EXTRACT_TOKEN_COUNT_REGEX|トークン数を抽出するための正規表現|
|PROMPT_HEADING|プロンプトの見出しを表す定数|
|PROCESS_PROGRESS_HEADING|処理進捗の見出しを表す定数|
|REPORT_TITLE|レポートのタイトルを表す定数|
|REPORT_TABLE_DIVIDER|レポートの表の区切りを表す定数|
|param|グローバルなParameterオブジェクト|
|reportList|レポートの結果を格納するリスト|

### メソッドの概要

|メソッド|説明|
|---|---|
|init(Parameter paramValues)|パラメータを初期化する|
|getReportList()|レポートのリストを返す|
|startAndFork()|処理を開始し、ファイルまたはディレクトリに応じて処理を分岐する|
|walkDirectory(Path srcPath)|ディレクトリを探索し、各ファイルを読み込む|
|readNormalFile(Path inputFilePath)|通常のファイルを読み込む|
|readZipFile(Path srcPath)|ZIPファイルを読み込み、各エントリを処理する|
|mainProcess(Path inputFilePath, List<String> inputFileLines)|メインの処理を行う|
|askToOpenAi(...)|OpenAIのAPIに問い合わせを行う|
|extractToken(String content, String message)|レスポンスメッセージからトークン数を抽出する|
|extractNameWithoutExt(String fileName, String ext)|ファイル名から拡張子を取り除く|
|toStringFromStrList(List<String> strList)|文字列リストから文字列を生成する|
|toChoicesFromResponce(SuccessResponseBody responseBody)|レスポンスから選択肢を抽出する|
|toLineListFromChoices(List<String> responseChoices)|選択肢を行リストに変換する|
|write(String responseContent)|結果を書き出す|
|printPrompt(Prompt prompt)|プロンプトをコンソールに表示する|
|addReport(Path inputFilePath, String message, int resuestTokenCount, int responseTokenCount, long interval)|レポートに処理結果を追加する|

---
# pro.kensait.brain2doc.process.**SplitUtil**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/SplitUtil.java

## クラス概要

`SplitUtil`クラスは、リストの分割や分割数の計算などを行うユーティリティクラスです。このクラスは`pro.kensait.brain2doc.process`パッケージに属しています。

#### メンバの概要

|メンバ名|詳細|
|---|---|
|`MARKUP_FOR_SPLIT_COUNT`|分割数の計算に使用する定数（0.5）。|
|`split`|指定した数にリストを分割します。分割数を超えないようにリストの最大行数を計算し、その数を使ってリストを分割します。|
|`calcSplitCount`|トークン数の上限と現在のトークン数から分割数を計算します。トークン数比率と文字列比率が同じであるとは限らないこと、またテンプレートの追加なども考慮して、安全に倒すため`MARKUP_FOR_SPLIT_COUNT`を加算し、さらに切り上げています。|

---
# pro.kensait.brain2doc.process.**TemplateAttacher**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/process/TemplateAttacher.java

## クラス概要

クラス名: TemplateAttacher

所属パッケージ: pro.kensait.brain2doc.process

このクラスはテンプレートの統合を行うためのクラスです。メインメソッドであるattachは、テンプレートを作成し、それをメッセージに変換し、Promptというユーザーメッセージラインを含むオブジェクトを返します。

#### メソッド一覧

| メソッド名 | 引数 | 戻り値 | 概要 |
|---|---|---|---|
| attach | List<String> inputFileLines,<br>ResourceType resourceType,<br>GenerateType generateType,<br>String genTable,<br>String fields,<br>OutputScaleType outputSizeType,<br>Locale locale,<br>Path templateFile,<br>int count | Prompt | テンプレートを作成し、それをメッセージに変換し、Promptを返します。 |
| getScaleString | Map messageMap, <br>OutputScaleType outputSizeType | String | 出力内容の大きさを指定するための文字列を取得します。 |
| getTableTemplateStr | Map messageMap, <br>String genTable, <br>String fields | String | 一覧形式のテンプレートを返します。 |

#### 内部クラス

| クラス名 | 概要 |
|---|---|
| Prompt | テンプレートのメッセージを保持するクラスです。systemMessage、assistantMessage、userMessage、userMessageLinesの4つのメンバを持っています。 |

---
# pro.kensait.brain2doc.transform.**GenericTransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/GenericTransformStrategy.java

## クラス概要

以下に、`GenericTransformStrategy`クラスのメソッド概要を示します。

| メソッド名 | 引数 | 戻り値 | 概要 |
|:----------|:-----|:------|:-----|
| transform | inputFilePath: Path, requestContent: String, responseLines: List<String>, seqNum: int | String | 入力ファイルのパス、リクエスト内容、レスポンス行のリスト、シーケンス番号を引数に取り、Markdown形式でファイル名、URL、レスポンス内容を整形して返す。 |

### クラスの詳細

このクラスは、TransformStrategyインターフェースを実装しています。具体的な処理としては、引数で受け取った入力ファイルのパス、リクエスト内容、レスポンス行のリスト、シーケンス番号をもとに、Markdown形式の文字列を生成して返します。生成される文字列は、ファイル名、URL、レスポンス内容を含みます。ファイル名とシーケンス番号はヘッダとして表示され、URLとレスポンス内容は本文として表示されます。また、シーケンス番号が1でない場合は、ファイル名の後ろにシーケンス番号を追加します。各部分は改行で区切られ、全体は水平線で閉じられます。

---
# pro.kensait.brain2doc.transform.**JavaGeneralTransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/JavaGeneralTransformStrategy.java

## クラス概要

「JavaGeneralTransformStrategy」は、「TransformStrategy」インターフェースを実装したクラスです。このクラスは、与えられた入力情報をもとに、特定の形式（マークダウン形式）で整形します。

#### メソッド

| メソッド名 | 概要 |
|------------|------|
| transform | 入力ファイルパス、リクエスト内容、レスポンス行のリスト、シーケンス番号を引数に取り、整形した内容を返します。 |
| getPackageName | リクエスト内容からパッケージ名を抽出します。 |

#### フィールド

| フィールド名 | 概要 |
|--------------|------|
| PACKAGE_REGEX | パッケージ名を抽出するための正規表現のパターンです。 |

#### 処理の詳細

- transformメソッドでは、レスポンス行のリストを一つの文字列に結合し、クラス名とURLを抽出します。その後、マークダウン形式に整形します。
- getPackageNameメソッドでは、与えられたリクエスト内容からパッケージ名を抽出します。具体的には、PACKAGE_REGEXにマッチする部分を取り出します。マッチする部分がない場合は空文字を返します。

---
# pro.kensait.brain2doc.transform.**TransformStrategy**

ファイルパス: file:///D:/GitHubRepos/brain2doc/src/main/java/pro/kensait/brain2doc/transform/TransformStrategy.java

## クラス概要

### TransformStrategy

TransformStrategyは、入力ファイルパス、リクエストコンテンツ、レスポンスコンテンツ、シーケンス番号を引数にとり、変換した結果を文字列で返すインターフェースです。また、リソースタイプと生成タイプを引数にとり、適切な出力ストラテジーを返す静的メソッドを持っています。

#### メソッド概要

| メソッド名 | 説明 |
|---|---|
| transform | 入力ファイルパス、リクエストコンテンツ、レスポンスコンテンツ、シーケンス番号を引数にとり、変換した結果を文字列で返します。 |
| getOutputStrategy | リソースタイプと生成タイプを引数にとり、それに応じた出力ストラテジーを返します。 |
---
