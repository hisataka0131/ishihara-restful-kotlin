## 使用した技術要素
- Java
- kotlin
- Gradle
- Spring Boot 2.0
- MySQL
- Swagger Editer

##API設計

### API仕様書（Swagger.yaml)

###商品の全件取得

GET /product</li>

###商品をIDから取得

GET /product/{id}

###商品の新規登録

POST /product

###商品の更新

PUT /product/{id}

###商品の削除

DELETE /product/{id}

###商品の検索

GET /product/search/{title}

###商品画像の登録

POST /product/{id}/imageUpload


###DB設計

####Products
| カラム名 | 型 | NULL | KEY | その他 |
| :-----------: |:-----------:| :------: | :------: | :-------:|
| id | int | NO | PRIMARY | auto_increment |
| title | varchar(255) | NO | - | - |
| opinion | text | NO | - | - |
| price | int | NO | - | - |
| imagPath | varchar(255) | YES | - | - |



##開発環境のセットアップ手順

###Java8 インストール
```sh
brew cask install java8 
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
```

###Gradle, Mysql インストール
```sh
brew install mysql gradle
```


###MySQLでデーダベースを用意

最初にMySQL で、apiという名前のデータベースを用意する。
```sh
mysql.server start

CREATE DATABASE api;
```

## 画像の保存先

application.ymlでbucket名とdir名を使うS3に合わせて設定してください
また、intelijのENVにaccessKeyとsecretKeyを設定してください
                
