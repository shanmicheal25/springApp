package com.g2s.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// controller
@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;

	// method return hello world..
	// GET
	// URI -- helloworld
	// @RequestMapping(method= RequestMethod.GET, path ="/helloworld")
	@GetMapping(path = "/helloworld")
	public String helloWorld() {
		return "helloWorld";
	}

	// hello-world-bean return back..
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean hellowWorldBean() {
		return new HelloWorldBean("helloWorld bEan");
	}
	
	// internatalization..
	@GetMapping(path = "/hello-world-internationalization")
	public String hellowWorldI18(@RequestHeader(name="Accept-Language", required= false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}
	
	
	// internatalization..
		@GetMapping(path = "/hello-world-internationLocale")
		public String hellowWorldI18Locale() {
			return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
		}


	// create url with path variable
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean hellowWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("helloWorld path, %s", name));
	}

}
