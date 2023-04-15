# crfa-metadata-service-reloaded

## native image

Create native image (GraalVM 22.3+ is required)
```
$ ./gradlew nativeCompile
```

then, you can run the app as follows:
```
$ build/native/nativeCompile/crfa-metadata-service-reloaded
```

## Create docker image
```
$ ./gradlew bootBuildImage
```
and then run via
 
```
$ docker run --rm -p 8080:8080 crfa-metadata-service-reloaded:0.0.1-SNAPSHOT
```
