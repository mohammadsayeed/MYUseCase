package com.klm.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.klm.model.ChikenEgg;
import com.klm.model.ChikenEggResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
@RequestMapping("/api/{userId}")
public class CollectAfterFeedController {

	@PostMapping(path="/eggs_collect", produces="application/json")
	public @ResponseBody ChikenEgg eggsCollect(@PathVariable("userId") String userId)
	{
		ChikenEgg dm = new ChikenEgg();
		Random rand = new Random(); 
		int value = rand.nextInt(10);
		dm.setAction("eggs_collect");
		String massagevalue = "Hey look at that, " +value +" eggs have been collected!";
		dm.setMassage(massagevalue);
		dm.setData(String.valueOf(value));
		dm.setSuccess("true");
		return dm;
	}

	@PostMapping(path="/chickens_feed", produces="application/json")
	public @ResponseBody ChikenEgg chickenFeed(@PathVariable("userId") String userId)
	{
		ChikenEgg domain = new ChikenEgg();
		domain.setAction("Chicken-feed");
		domain.setSuccess("true");
		domain.setMassage("Your chickens are now full and happy");
		return domain;
	}

	
	
	@PostMapping(path="/collect_after_feed", produces="application/json")

	public @ResponseBody ChikenEggResult CollectAfterFeed (@PathVariable("userId") String userId)
	{
		String eggs_collect_service ="http://localhost:8080/api/"+userId+"/eggs_collect";
		
		System.out.println("eggs_collect_service "+eggs_collect_service);
		 String chickens_feed_service="http://localhost:8080/api/"+userId+"/chickens_feed";
		ChikenEggResult  klm = new ChikenEggResult();
		
		// Client call for chicken feed
		Client client1= Client.create();
		WebResource wr = client1.resource(chickens_feed_service);
		ClientResponse clientResponse1 = wr.accept("application/json").post(ClientResponse.class);
		int feedChickenResponseCode =clientResponse1.getClientResponseStatus().getStatusCode();
		
		// client call for eggs collect service
		
		Client client2= Client.create();
		WebResource wr2= client2.resource(eggs_collect_service);
		ClientResponse clientResponse = wr2.accept("application/json").post(ClientResponse.class);

		int collectEggsResponseCode = clientResponse.getClientResponseStatus().getStatusCode();

		if((feedChickenResponseCode == 200)&&(collectEggsResponseCode == 200))
		{
			klm = new ChikenEggResult();
			klm.setCollect("success");
			klm.setFeed("success");
		}
		else if((feedChickenResponseCode == 200)&&(collectEggsResponseCode == 202))
		{
			klm = new ChikenEggResult();
			klm.setCollect("success");
			klm.setFeed("failed");
		}
		else if((feedChickenResponseCode == 202)&&(collectEggsResponseCode == 200))
		{
			klm = new ChikenEggResult();
			klm.setCollect("failed");
			klm.setFeed("success");
		}
		else if((feedChickenResponseCode == 400)&&(collectEggsResponseCode == 400))
		{
			klm = new ChikenEggResult();
			klm.setCollect("failed");
			klm.setFeed("failed");
		}

		return klm;


	}


}
