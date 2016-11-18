#!/bin/bash

# If there is problem with file descriptors then edit /etc/security/limits.conf and add:
# * soft nofile 100000
# * hard nofile 100000

# If there is problem with memory then run:
# sudo sysctl -w vm.max_map_count=262144

# Kill all processes (mainly for dynamic re-creation)
ps aux | grep "./../node/bin/node ./../src/cli" | awk -F ' ' '{ print $2 }' | xargs kill -9
ps aux | grep elasticsearch | awk -F ' ' '{ print $2 }' | xargs kill -9

# Root directory
BASE_DIR=zonky-data

# Versions
ELASTICSEARCH_VERSION=5.0.0
KIBANA_VERSION=5.0.0

# Clean up
rm -rf ~/${BASE_DIR}
mkdir ~/${BASE_DIR}

# Download and unpack binaries
echo "Preparing ElasticSearch"
wget -q -P ~/${BASE_DIR}/ https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-${ELASTICSEARCH_VERSION}.tar.gz
tar -xvzf ~/${BASE_DIR}/elasticsearch-${ELASTICSEARCH_VERSION}.tar.gz -C ~/${BASE_DIR} > /dev/null

echo "Preparing Kibana"
wget -q -P ~/${BASE_DIR} https://artifacts.elastic.co/downloads/kibana/kibana-${KIBANA_VERSION}-linux-x86_64.tar.gz
tar -xvzf ~/${BASE_DIR}/kibana-${KIBANA_VERSION}-linux-x86_64.tar.gz -C ~/${BASE_DIR} > /dev/null

# For virtualization purposes
echo "" >> ~/${BASE_DIR}/elasticsearch-${ELASTICSEARCH_VERSION}/config/elasticsearch.yml
echo "network.host: 0.0.0.0" >> ~/${BASE_DIR}/elasticsearch-${ELASTICSEARCH_VERSION}/config/elasticsearch.yml
echo "http.port: 9200" >> ~/${BASE_DIR}/elasticsearch-${ELASTICSEARCH_VERSION}/config/elasticsearch.yml

echo "" >> ~/${BASE_DIR}/kibana-${KIBANA_VERSION}-linux-x86_64/config/kibana.yml
echo "server.host: 0.0.0.0" >> ~/${BASE_DIR}/kibana-${KIBANA_VERSION}-linux-x86_64/config/kibana.yml
echo "server.port: 5601" >> ~/${BASE_DIR}/kibana-${KIBANA_VERSION}-linux-x86_64/config/kibana.yml

# Start ElasticSearch
# export ES_HEAP_SIZE=256m
nohup ~/${BASE_DIR}/elasticsearch-${ELASTICSEARCH_VERSION}/bin/elasticsearch &
echo "Starting ElasticSearch"
sleep 20

curl -s http://localhost:9200/_cluster/health | grep "number_of_nodes\":1" > /dev/null
if [ $? != 0 ] ; then
    echo "Unable to start ElasticSearch! See nohup.out for more information."
    exit 1
fi

# Start Kibana
nohup ~/${BASE_DIR}/kibana-${KIBANA_VERSION}-linux-x86_64/bin/kibana &
echo "Starting Kibana"
sleep 20

curl -s http://localhost:5601 | grep "/app/kibana" > /dev/null
if [ $? != 0 ] ; then
    echo "Unable to start Kibana! See nohup.out for more information."
    exit 1
fi

echo ""
echo "ElasticSearch default port: 9200 (9300)"
echo "Kibana default port: 5601"
echo ""
echo "Done"
