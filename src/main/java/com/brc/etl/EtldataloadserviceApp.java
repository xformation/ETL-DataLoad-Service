package com.brc.etl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.brc.etl.config.ApplicationProperties;

import io.github.jhipster.config.DefaultProfileUtil;
import io.github.jhipster.config.JHipsterConstants;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class EtldataloadserviceApp {

    private static final Logger log = LoggerFactory.getLogger(EtldataloadserviceApp.class);

    private final Environment env;
    private final boolean runFlag=false;

    public EtldataloadserviceApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes etldataloadservice.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        if(args.length == 1 && args[0].contains("--RUN_AS_SPRING_BOOT=YES")) {
        	SpringApplication app = new SpringApplication(EtldataloadserviceApp.class);
            DefaultProfileUtil.addDefaultProfile(app);
            Environment env = app.run(args).getEnvironment();
            logApplicationStartup(env);
		}else {
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("-start_scheduler")) {
				scheduler(args);
			}else if(args.length >= 2 && args[0].equalsIgnoreCase("-check_data")){
				checkData(args);
			}else {
				System.out.println("Invalid options.");
				System.exit(0);
			}
		
			
		}
        
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }
    
    public static void checkData(String[] args) {
		try {

            
//            if(args.length >= 2 && !args[0].equalsIgnoreCase("-check_data")) {
//            	throw new ParseException("Missing required option: -checkdata");
//			}
		
            if(args.length >= 2 && !args[1].equalsIgnoreCase("-rule=rule.json")) {
            	throw new ParseException("Missing required option: -rule");
			}
            
            String ruleOption = args[1]; 
            
			String ary [] =  ruleOption.split("=");
            String rule = "files/"+ary[1];
			
			FileReader fileReader= null;
			try {
				fileReader=new FileReader(rule);
			}catch(Exception e) {
				throw new FileNotFoundException("rule.json file not found");
			}
			
			JSONParser jsonParser=new JSONParser();
			Object obj=jsonParser.parse(fileReader);
			JSONObject  jsonObject=(JSONObject)obj;
			String frequency=jsonObject.get("frequency").toString();
			if(frequency.equalsIgnoreCase("daily")) {
				RestTemplate restTemplate = new RestTemplate();
				Map<String, String> map = restTemplate.getForObject("http://localhost:7272/api/EtlDataCheck", Map.class);
				Set set = map.keySet();
				for(Object objKey: set) {
					String key = (String)objKey;
					String keyAry []= key.split("##");
					if(keyAry[1].equalsIgnoreCase("failed")) {
						String value = (String)map.get(key);
						System.out.println();
						System.out.println();
						System.out.println("Daily frequency rule faild for type : "+keyAry[0]);
//						System.out.println("A new alert generated. Alert name : "+ (String)map.get("alertname"));
						System.out.println("A new alert generated. Alert name : "+map.get(key));
					}
//					String value = (String)map.get(key);
//					if(!key.equalsIgnoreCase("alertname")) {
//						if(value.equalsIgnoreCase("failed")) {
//							System.out.println();
//							System.out.println();
//							System.out.println("Daily frequency rule faild for type : "+key);
//							System.out.println("A new alert generated. Alert name : "+ (String)map.get("alertname"));
//						}
//					}
				}
			}else {
				System.out.println("Rule not yet implemented for frequency : "+frequency);
			}
				
		} catch (Exception e) {
			System.out.println("Execution failed. Exception : "+e.getMessage());
		}
	}

    public static void scheduler(String[] args) {
    	
		try {

            if(args.length >= 2 && !args[1].equalsIgnoreCase("-rule=rule.json")) {
            	throw new ParseException("Missing required option: -rule");
			}
            
            String ruleOption = args[1]; 
            
			String ary [] =  ruleOption.split("=");
            String rule = "files/"+ary[1];
			
			FileReader fileReader= null;
			try {
				fileReader=new FileReader(rule);
			}catch(Exception e) {
				throw new FileNotFoundException("rule.json file not found");
			}
			
			JSONParser jsonParser=new JSONParser();
			Object obj=jsonParser.parse(fileReader);
			JSONObject  jsonObject=(JSONObject)obj;
			
			System.out.println("Scheduler starting");
            while(true) {
            	try {
            		String frequency=jsonObject.get("frequency").toString();
        			if(frequency.equalsIgnoreCase("daily")) {
        				RestTemplate restTemplate = new RestTemplate();
        				Map<String, String> map = restTemplate.getForObject("http://100.64.108.25:7272/api/EtlDataCheck", Map.class);
        				Set set = map.keySet();
        				for(Object objKey: set) {
        					String key = (String)objKey;
        					String keyAry []= key.split("##");
        					if(keyAry[1].equalsIgnoreCase("failed")) {
        						String value = (String)map.get(key);
        						System.out.println();
        						System.out.println();
        						System.out.println("Daily frequency rule faild for type : "+keyAry[0]);
//        						System.out.println("A new alert generated. Alert name : "+ (String)map.get("alertname"));
        						System.out.println("A new alert generated. Alert name : "+map.get(key));
        					}
        				}
        			}else {
        				System.out.println("Rule not yet implemented for frequency : "+frequency);
        			}
        			Thread.sleep(60*1000*60);
            	}catch (InterruptedException ex) {
					log.warn("InterruptedException :",ex);
				} catch (Exception e) {
					log.warn("Exception: ",e);
				}
            }
            
				
		} catch (Exception e) {
			System.out.println("Execution failed. Exception : "+e.getMessage());
		}
	}
    
	private static Options buildOptions() {
		Options options = new Options();

		Option checkDataOption = new Option("checkdata", "checkdata", true, "check Data Flag.");
		checkDataOption.setRequired(false);
		options.addOption(checkDataOption);

		Option rule = new Option("rule", "rule", true, "rule.json file.");
		rule.setRequired(false);
		options.addOption(rule);

		return options;
	}
	
	
}
