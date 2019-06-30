# jackrabbit-boot

Apache Jackrabbit Boot project

## Work in progress!

This project is yet under the construction.

## How to run

Run it with `spring-boot:run`:

```
mvn spring-boot:run
```

## JCR over WebDAV access

The JCR over WebDAV Server Servlet becomes accessible through `http://localhost:8080/server`.
For example,

```
curl --user admin:admin http://localhost:8080/server/default/jcr:root
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
