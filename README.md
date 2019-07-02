# jackrabbit-boot

Apache Jackrabbit Boot project: Spring Boot based, simple executable web application running Apache Jackrabbit
Repository with JCR over WebDAV servlet for both JCR clients and Web Remoting clients.

## How to build

```bash
mvn clean package
```

## How to run

For example,

```bash
java \
    -Drepository.home=target/jackrabbit-repository \
    -Drepository.config=conf/simple-repository.xml \
    -Dloader.path=lib/ \
    -jar target/jackrabbit-boot-0.0.1-SNAPSHOT.jar
```

Note:
- If `repository.home` system property is missing, `./jackrabbit-repository` will be used by default.
- If `repository.config` system property is missing, a default `repository.xml` will be copied to the `repository.home` directory.
- The `-Dloader.path` system property is optional. If provided, all the jar files in the directory will be loaded.


With remote debugging:

```bash
java \
    -Drepository.home=target/jackrabbit-repository \
    -Drepository.config=conf/simple-repository.xml \
    -Dloader.path=lib/ \
    -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n \
    -jar target/jackrabbit-boot-0.0.1-SNAPSHOT.jar
```

## JCR over WebDAV access

The JCR over WebDAV Server Servlet becomes accessible through `http://localhost:8080/server`.

For example,

```bash
curl -i --user admin:admin http://localhost:8080/server/default/jcr:root
```

## Other Services

The Statistics Servlet becomes accessible through `http://localhost:8080/statistics`.

For example,

```bash
curl -i http://localhost:8080/statistics
```

## References

- Jukka Zitting, [Jackrabbit over HTTP](https://jukkaz.wordpress.com/2009/11/24/jackrabbit-over-http/)
- [Hippo Repository JCR/WebDAV Support - Command Line Examples](https://bloomreach-forge.github.io/hippo-jcr-over-webdav/cmd-examples.html)
