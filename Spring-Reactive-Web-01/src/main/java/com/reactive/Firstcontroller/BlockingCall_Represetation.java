package com.reactive.Firstcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


//This is Blocking call , Where Each Msg will wait for given time then all the List will retrun at same time 
//It will block the Thread ...--> Once all the Value completes in List then it will Unblock the Thread
//But it is not suitable for Big-Project ....we need Non-Blocking Execution For thta will use Spring Reactive Web Flux....
@RestController
public class BlockingCall_Represetation {

	@GetMapping("/msg")
	public List<String> getMsg() throws InterruptedException{
		List<String> stringList= Arrays.asList("ram","krishna","prashant","Aditi","ShreeNiddi");
		
		List<String> returnList =new ArrayList<>();
		for(String  value : stringList) {
			returnList.add(value);
			Thread.sleep(1000);
		}
		return returnList;
		
	}
}
