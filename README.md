Install kafka, Zookeeper

Install java 8

Install spark 3.5.X

Set environment variables  as needed on your machine for HADDOP_HOME, SPARK_HOME


start zookeepr
.\zookeeper-server-start.bat ..\..\config\zookeeper.properties

start kafka server
.\zookeeper-server-start.bat ..\..\config\zookeeper.properties

create topic
.\kafka-topics.bat --create --topic my-test-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3

watch for consumer
 .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my-test-topic --from-beginning

 list topics
 .\kafka-topics.bat --bootstrap-server localhost:9092 --list

 describe particular topic
  .\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic my-test-topic
  


  
.\kafka-topics.bat --create --topic sink-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3

 .\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic sink-topic
 
.\kafka-console-consumer.bat --from-beginning  --bootstrap-server localhost:9092 --property print.key=true --property print.value=false --property print.partition --topic my-test-topic --timeout-ms 5000

 .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic sink-topic --from-beginning
