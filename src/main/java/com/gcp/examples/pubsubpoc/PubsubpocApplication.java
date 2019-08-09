package com.gcp.examples.pubsubpoc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.threeten.bp.Duration;

import com.google.api.gax.batching.BatchingSettings;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Publisher.Builder;
import com.google.pubsub.v1.PubsubMessage;


@SpringBootApplication
public class PubsubpocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubsubpocApplication.class, args);
	}
	
	//Receiving the message from PubSub
	
	  @Bean 
	  public MessageChannel pubsubInputChannel() {
	  return new DirectChannel();
	  
	  }
	  
	  @Bean 
	  public PubSubInboundChannelAdapter messageChannelAdapter(
	  @Qualifier("pubsubInputChannel") MessageChannel inputChannel, PubSubTemplate
	  pubSubTemplate) { 
	  PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "TestSub");
	  adapter.setOutputChannel(inputChannel); 
	 // adapter.setAckMode(AckMode.MANUAL);
	  return adapter; 
	  }
	  
	
	  @ServiceActivator(inputChannel = "pubsubInputChannel") 
	  public void messageReceiver(String payload) {
	  System.out.println("Message from Subscription:::::::::;::::: " + payload); 
	  }
	 
	//Sending the message to PubSub
	   @Bean
	   @ServiceActivator(inputChannel = "pubsubOutputChannel")
	   public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
	     return new PubSubMessageHandler(pubsubTemplate, "TestTopic");
	     
	   }

	   @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
	   public interface PubsubOutboundGateway {
	     void sendToPubsub(String text);
	   }
	   
	/* @Autowired
	   private MessageChannel pubsubOutputChannel;

	   public void handleMessage(Message<?> msg) throws MessagingException {
	       final Message<?> message = MessageBuilder
	           .withPayload(msg.getPayload())
	           .setHeader(GcpPubSubHeaders.TOPIC, "customTopic").build();
	       pubsubOutputChannel.send(message);
	   } */
	   

	   
	// Batch settings control how the publisher batches messages
	/* long requestBytesThreshold = 5000L; // default : 1 byte
	   long messageCountBatchSize = 10L; // default : 1 message

	   Duration publishDelayThreshold = Duration.ofMillis(100); // default : 1 ms

	   // Publish request get triggered based on request size, messages count & time since last publish
	   BatchingSettings batchingSettings =
	       BatchingSettings.newBuilder()
	           .setElementCountThreshold(messageCountBatchSize)
	           .setRequestByteThreshold(requestBytesThreshold)
	           .setDelayThreshold(publishDelayThreshold)
	           .build();
	   
	   Publisher publisher =
			    Publisher.newBuilder("topicName").setBatchingSettings(batchingSettings).build(); */
	/*
	 * @Bean
	 * 
	 * @ServiceActivator(inputChannel = "pubsubInputChannel") public MessageHandler
	 * messageReceiver() { return message -> {
	 * System.out.println("Message arrived! Payload: " + message.getPayload());
	 * AckReplyConsumer consumer = (AckReplyConsumer)
	 * message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE); consumer.ack();
	 * }; }
	 */
	 
	 
	   
	   //Sending the message to PubSub
	/*
	 * @Bean
	 * 
	 * @ServiceActivator(inputChannel = "pubsubOutputChannel") public MessageHandler
	 * messageSender(PubSubTemplate pubsubTemplate) { return new
	 * PubSubMessageHandler(pubsubTemplate, "TestTopic").set; }
	 * 
	 * @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel") public
	 * interface PubsubOutboundGateway { void sendToPubsub(String text); }
	 */
}
