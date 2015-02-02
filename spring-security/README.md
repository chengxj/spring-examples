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
cfd0a934c188a10a59c31c522999f363bd587d2c570a9deeb93a8487dd75e1cc27a308757db5e271
```

## Access Protected Path

```sh
curl "http://localhost:8080/auth/greeting/jeoygin?user=admin&key=cfd0a934c188a10a59c31c522999f363bd587d2c570a9deeb93a8487dd75e1cc27a308757db5e271"
```

Replace the key with the token you get. And you will get:

```sh
{"id":1,"content":"Hello, jeoygin!"}
```
