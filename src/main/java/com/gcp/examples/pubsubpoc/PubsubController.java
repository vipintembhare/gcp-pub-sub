package com.gcp.examples.pubsubpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.examples.pubsubpoc.PubsubpocApplication.PubsubOutboundGateway;


@RestController
public class PubsubController {
	@Autowired
	private PubsubOutboundGateway messagingGateway;

	@PostMapping("/publishMessage")
	public String publishMessage(@RequestBody CustomMessage message) {
		messagingGateway.sendToPubsub(message.toString());
		return "Message Published Successfully";
	}
}
