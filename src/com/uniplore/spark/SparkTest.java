package com.uniplore.spark;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SparkTest {
	private Properties kafkaProps = new Properties();

	public SparkTest() {
		
		
	}
	
	public static void main(String[] args) {
		SparkTest tjr = new SparkTest();
		try {
			tjr.init();
			tjr.sendMessage("test","record","GJBS01;ZDBS02;YXDNB5624991");
			tjr.consumerMessage("test");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void init(){
		// 指定broker（这里指定了2个，1个备用），如果你是集群更改主机名即可，如果不是只写运行的主机名
		kafkaProps.put("bootstrap.servers", "192.168.103.88:9092");
		// 消费者群组
		kafkaProps.put("group.id", "kafka-test-group");	
		// 设置序列化（自带的StringSerializer，如果消息的值为对象，就需要使用其他序列化方式，如Avro ）
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}
	
	public void consumerMessage(String topic){
		/**
         * 自动提交偏移量的提交频率
         */
		kafkaProps.put("auto.commit.interval.ms", "1000");
		/**
         * 默认值latest.
         * latest:在偏移量无效的情况下，消费者将从最新的记录开始读取数据
         * erliest:偏移量无效的情况下，消费者将从起始位置读取分区的记录。
         */
		kafkaProps.put("auto.offset.reset", "earliest");
		kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps);
		consumer.subscribe(Collections.singletonList(topic));
		try{
			while(true){
				ConsumerRecords<String,String> records = consumer.poll(1000);
				for(ConsumerRecord<String,String> record:records){
					System.out.printf("offset = %d, value = %s", record.offset(), record.value());
	                System.out.println();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			consumer.close();
		}
	}
	
	public void sendMessage(String topic,String msgKey,String msgValue){
		// 实例化出producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);
				
		// 创建ProducerRecord对象
		ProducerRecord<String, String>  record = new ProducerRecord<String, String>(topic,
				msgKey, msgValue);
		try {
			// 发送消息
			producer.send(record,new DemoProducerCallback());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	private class DemoProducerCallback implements Callback {

		@Override
		public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			if (e != null) {
				// 如果消息发送失败，打印异常
				e.printStackTrace();
			} else {
				System.out.println("成功发送消息！");
			}
		}

	}
	

}
