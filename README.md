# Zonky connector to ElasticSearch

Simple tool which uses public Zonky.cz API and uploads all data to local ElasticSearch for further analysis.

### How to install/configure ElasticSearch and Kibana

```bash
bin/install.sh
bin/createIndex.sh
```

### How to configure Kibana index

- Open browser with URL localhost:5601
- Add `zonky` in Settings > Indices as index name
- Use `internalDateCreated` as Time-field name
- Save index

### How to run application

```bash
./gradlew clean test # for tests
./gradlew clean bootRun # run application
```

### How to analyze data
- Open browser with URL localhost:5601
- Use `Discover` or `Visualize` tabs to see data
