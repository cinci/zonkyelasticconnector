#!/bin/bash

# Kill all processes (mainly for dynamic re-creation)
ps aux | grep "./../node/bin/node ./../src/cli" | awk -F ' ' '{ print $2 }' | xargs kill -9
ps aux | grep elasticsearch | awk -F ' ' '{ print $2 }' | xargs kill -9

# Root directory
BASE_DIR=zonky-data

# Clean up
rm -rf ~/${BASE_DIR}
mkdir ~/${BASE_DIR}

# Download and unpack binaries
wget -P ~/${BASE_DIR}/ https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.3.3/elasticsearch-2.3.3.tar.gz
tar -xvzf ~/${BASE_DIR}/elasticsearch-2.3.3.tar.gz -C ~/${BASE_DIR}
wget -P ~/${BASE_DIR} https://download.elastic.co/kibana/kibana/kibana-4.5.1-linux-x64.tar.gz
tar -xvzf ~/${BASE_DIR}/kibana-4.5.1-linux-x64.tar.gz -C ~/${BASE_DIR}

# For virtualization purposes
echo "" >> ~/${BASE_DIR}/elasticsearch-2.3.3/config/elasticsearch.yml
echo "network.host: 0.0.0.0" >> ~/${BASE_DIR}/elasticsearch-2.3.3/config/elasticsearch.yml
echo "http.port: 9200" >> ~/${BASE_DIR}/elasticsearch-2.3.3/config/elasticsearch.yml

# Start ElasticSearch
# export ES_HEAP_SIZE=256m
nohup ~/${BASE_DIR}/elasticsearch-2.3.3/bin/elasticsearch &
echo "Starting ElasticSearch..."
sleep 10

# Start Kibana
nohup ~/${BASE_DIR}/kibana-4.5.1-linux-x64/bin/kibana &
echo "Starting Kibana..."
sleep 10

echo "DONE !"
echo ""
echo "ElasticSearch default port: 9200"
echo "Kibana default port: 5601"
