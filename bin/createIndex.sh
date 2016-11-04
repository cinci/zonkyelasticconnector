#!/bin/bash

curl -XDELETE http://localhost:9200/zonky
curl -XPUT http://localhost:9200/zonky -d '
{
    "mappings" : {
        "loan" : {
            "properties" : {
                "id" :                  { "type" : "long", "index" : "not_analyzed" },
                "name" :                { "type" : "string", "index" : "not_analyzed" },
                "story" :               { "type" : "string", "index" : "not_analyzed" },
                "purpose" :             { "type" : "string", "index" : "not_analyzed" },
                "photos" :              {
                                            "properties" : {
                                                "name" : { "type" : "string", "index" : "not_analyzed" },
                                                "url" : { "type" : "string", "index" : "not_analyzed" }
                                            }
                                        },
                "nickName" :            { "type" : "string", "index" : "not_analyzed" },
                "termInMonths" :        { "type" : "integer", "index" : "not_analyzed" },
                "interestRate" :        { "type" : "double", "index" : "not_analyzed" },
                "rating" :              { "type" : "string", "index" : "not_analyzed" },
                "amount" :              { "type" : "double", "index" : "not_analyzed" },
                "remainingInvestment" : { "type" : "double", "index" : "not_analyzed" },
                "investmentRate" :      { "type" : "double", "index" : "not_analyzed" },
                "covered" :             { "type" : "boolean", "index" : "not_analyzed" },
                "datePublished" :       { "format" : "strict_date_optional_time||epoch_millis", "type" : "date" },
                "published" :           { "type" : "boolean", "index" : "not_analyzed" },
                "deadline" :            { "format" : "strict_date_optional_time||epoch_millis", "type" : "date" },
                "investmentsCount" :    { "type" : "integer", "index" : "not_analyzed" },
                "questionsCount" :      { "type" : "integer", "index" : "not_analyzed" },
                "region" :              { "type" : "string", "index" : "not_analyzed" },
                "regionName" :          { "type" : "string", "index" : "not_analyzed" },
                "regionGeo" :           { "type" : "geo_point" },
                "mainIncomeType" :      { "type" : "string", "index" : "not_analyzed" },
                "internalDateCreated" : { "format" : "strict_date_optional_time||epoch_millis", "type" : "date" },
                "internalBulkId" :      { "type" : "long", "index" : "not_analyzed" }
            }
        }
    }
}
'

echo ""
echo "Index created"
