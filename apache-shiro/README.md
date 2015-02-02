## Run

```sh
$ mvn spring-boot:run
```

or

```sh
$ mvn tomcat7:run
```

## Get Token

```sh
$ curl "http://localhost:8080/login?username=admin&password=pwd"
```

Then you can get a token associated with user 'admin'.

```
hOLoDFeIXsRbtXLGvOFrXL792O2ToywKShawgyQeOtc
```

## Access Protected Path

```sh
curl "http://localhost:8080/auth/greeting/jeoygin?user=admin&key=hOLoDFeIXsRbtXLGvOFrXL792O2ToywKShawgyQeOtc"
```

Replace the key with the token you get. And you will get:

```sh
{"id":1,"content":"Hello, jeoygin!"}
```
