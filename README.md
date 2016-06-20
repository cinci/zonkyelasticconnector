# Zonky connector to ElasticSearch

Simple tool which uses public Zonky.cz API and uploads all data to local ElasticSearch for analysing.

### How to install/configure ElasticSearch and Kibana

```bash
bin/install-elasticsearch-kibana.sh
bin/create-elasticsearch-index.sh
```

### How to analyze data

- Open browser with URL localhost:5601
- Add `zonky` in Settings > Indices as index name
- Use `internalDateCreated` as Time-field name
- Save index

### How to run application

```bash
./gradlew clean bootRun
```
