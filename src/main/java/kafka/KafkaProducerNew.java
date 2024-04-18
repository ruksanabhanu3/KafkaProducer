package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class KafkaProducerNew {
    private static Logger LOG = LoggerFactory.getLogger(KafkaProducerNew.class);

    private static String INFO_PREFIX = "mashreq-spark-kafka-info-log-";
    private static String ERROR_PREFIX = "mashreq-spark-kafka-error-log-";
    private static String WARN_PREFIX = "mashreq-spark-kafka-warn-log-";
    private static String KafkaBrokerEndpoint = "localhost:9092";
    private static String KafkaTopic = "my-test-topic";
    private static String CsvFile = "produceCsvDataToKafkaTopic.csv";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerNew");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            LOG.info(INFO_PREFIX+"Successfully connected to Kafka broker!");
            InputStream inputStream = KafkaProducerNew.class.getClassLoader().getResourceAsStream(CsvFile);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = csvReader.readLine()) != null) {
                ProducerRecord<String, String> record = new ProducerRecord<>(KafkaTopic, line);
                producer.send(record, (metadata, exception) -> {
                    if (metadata != null) {
                        LOG.info(INFO_PREFIX+"Published record on topic - {} , to partition - {} , at offset - {}",metadata.topic(),metadata.partition(),metadata.offset());
                    } else {
                        LOG.error(ERROR_PREFIX+"Error while publishing record to kafka topic - {}, with exception -{}",KafkaTopic,exception.getMessage());
                    }
                });
            }
            csvReader.close();
        } catch (Exception e) {
            LOG.error(ERROR_PREFIX+"Error connecting to Kafka broker... {}",e.getMessage());
        }
    }
}
