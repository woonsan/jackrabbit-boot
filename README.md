# jackrabbit-boot

Apache Jackrabbit Boot project

## Work in progress!

This project is yet under the construction.

## How to build

```
mvn clean package
```

## How to run

For example,

```
java \
    -Drepository.home=target/jackrabbit-repository \
    -Drepository.config=conf/simple-repository.xml \
    -jar target/jackrabbit-boot-0.0.1-SNAPSHOT.jar
```

Or with remote debugging,

```
java \
    -Drepository.home=target/jackrabbit-repository \
    -Drepository.config=conf/simple-repository.xml \
    -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n \
    -jar target/jackrabbit-boot-0.0.1-SNAPSHOT.jar
```

## JCR over WebDAV access

The JCR over WebDAV Server Servlet becomes accessible through `http://localhost:8080/server`.

For example,

```
curl -i --user admin:admin http://localhost:8080/server/default/jcr:root
```

## Other Services

The Statistics Servlet becomes accessible through `http://localhost:8080/statistics`.

For example,

```
curl -i http://localhost:8080/statistics
```

## References

- Jukka Zitting, [Jackrabbit over HTTP](https://jukkaz.wordpress.com/2009/11/24/jackrabbit-over-http/)
- [Hippo Repository JCR/WebDAV Support - Command Line Examples](https://bloomreach-forge.github.io/hippo-jcr-over-webdav/cmd-examples.html)
