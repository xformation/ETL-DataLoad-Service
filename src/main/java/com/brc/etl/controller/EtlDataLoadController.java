package com.brc.etl.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.brc.etl.config.Constants;
import com.brc.etl.domain.Alert;
import com.brc.etl.domain.ETLDataLoad;
import com.brc.etl.repository.ETLDataLoadRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class EtlDataLoadController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ENTITY_NAME = "etl_data_loader";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private ETLDataLoadRepository etlDataLoadRepository;
	
	@Autowired
    RestTemplate restTemplate;

	@GetMapping("/listEtlData")
	public List<ETLDataLoad> loadEtlData() {
		List<ETLDataLoad> list = etlDataLoadRepository.findAll();
		List<ETLDataLoad> govtDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> navDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> sednaDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> xerfDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> dcsDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> sdsDataList = new ArrayList<ETLDataLoad>();
		for (ETLDataLoad etlDataLoad : list) {
			if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_GOVT)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_NAV)) {
				navDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_SEDNA)) {
				sednaDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_XREF)) {
				xerfDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_DCS)) {
				dcsDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_SDS)) {
				sdsDataList.add(etlDataLoad);
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
		
		sdsDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
				.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
		return sdsDataList;
	}
	@GetMapping("/EtlDataCheck")
	public Map<String, String> loadEtlDataCheck() throws JSONException {
		List<ETLDataLoad> list = etlDataLoadRepository.findAll();
		List<ETLDataLoad> govtDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> navDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> sednaDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> xerfDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> dcsDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> sdsDataList = new ArrayList<ETLDataLoad>();
		for (ETLDataLoad etlDataLoad : list) {
			if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_GOVT)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_NAV)) {
				navDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_SEDNA)) {
				sednaDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_XREF)) {
				xerfDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_DCS)) {
				dcsDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_SDS)) {
				sdsDataList.add(etlDataLoad);
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
		
		List<ETLDataLoad> dailyMaxList = new ArrayList<>();
		
		try {
			sdsDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
//			ETLDataLoad maxDateRecord=sdsDataList.get(0);
			if(sdsDataList.size() > 0) {
				dailyMaxList.add(sdsDataList.get(0));
			}
		}catch(Exception e) {
			logger.error("sdsDataList exception : "+e.getMessage());
		}
		
		try {
			dcsDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
//			ETLDataLoad maxDateRecord=dcsDataList.get(0);
			if(dcsDataList.size() > 0) {
				dailyMaxList.add(dcsDataList.get(0));
			}
		}catch(Exception e) {
			logger.error("dcsDataList exception : "+e.getMessage());
		}
		
		try {
			xerfDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(xerfDataList.size() > 0) {
				dailyMaxList.add(xerfDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("xerfDataList exception : "+e.getMessage());
		}
		
		try {
			sednaDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(sednaDataList.size() > 0) {
				dailyMaxList.add(sednaDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("sednaDataList exception : "+e.getMessage());
		}
		
		try {
			navDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(navDataList.size() >0 ) {
				dailyMaxList.add(navDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("navDataList exception : "+e.getMessage());
		}
		
		try {
			govtDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(govtDataList.size() > 0) {
				dailyMaxList.add(govtDataList.get(0));
			}
		}catch(Exception e) {
			logger.error("govtDataList exception : "+e.getMessage());
		}
		
		Map<String, String> map=new HashMap<String, String>();
		for(ETLDataLoad maxDateRecord: dailyMaxList) {
			if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_HOURLY)) {
				
			}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_DAILY)) {
					if(LocalDate.parse(maxDateRecord.getLastDataLoadDate(), formatter).equals(LocalDate.now())) {
						map.put(maxDateRecord.getDataFlowType()+"##success","");
					}else {
						JSONObject jsonObject=new JSONObject();
//						jsonObject.put("recordId", maxDateRecord.getRecordId());
//						jsonObject.put("dataFlowType",maxDateRecord.getDataFlowType() );
//						jsonObject.put("message","failed");
						UUID uuid=UUID.randomUUID();
						String alertName = maxDateRecord.getDataFlowType() + "_" + maxDateRecord.getDataLoadFrequency() + "_failed_"+System.currentTimeMillis();
						jsonObject.put("guid", uuid.toString());
						jsonObject.put("name", alertName);
						jsonObject.put("severity", "High");
						jsonObject.put("monitorcondition", "Fired");
						jsonObject.put("affectedresource", "ETL DataLoad Service");
						jsonObject.put("monitorservice", "Synectiks");
						jsonObject.put("signaltype", "Metrics");
						jsonObject.put("brcsubscription", "Alert Management");
						jsonObject.put("suppressionstate", "None");
						jsonObject.put("resourcegroup", "Compute");
						jsonObject.put("resources", "App");
						jsonObject.put("firedtime", Instant.now().toString());
						jsonObject.put("created_on", System.currentTimeMillis());
						jsonObject.put("updated_on", System.currentTimeMillis());
						jsonObject.put("alert_state", "New");
						jsonObject.put("client", "EXTERNAL SERVICE");
						jsonObject.put("client_url", "");
						jsonObject.put("description", "");
						jsonObject.put("details", "New alert generated from ETL data load service");
						jsonObject.put("incident_key", uuid);
//						map.put(maxDateRecord.getDataFlowType(), "failed");
						map.put(maxDateRecord.getDataFlowType()+"##failed", alertName);
//						System.out.println(jsonObject.toString());
						try {
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
							UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8190/kafka/send")
									.queryParam("topic", "alert").queryParam("msg", jsonObject.toString());
							restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity,String.class);
							sendAlertActivity(uuid.toString(), alertName);
							}catch(Exception e) {
								logger.error("Exception in sending aler to kafka: ",e);
								map.put(maxDateRecord.getDataFlowType()+"##failed", "");
							}
						}
					
						
			}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_MONTHLY)) {
				
			}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_YEARLY)) {
				
			}
		}
		
		return map;
	}
	 @PostMapping("/addDataToElt")
	    public ResponseEntity<ETLDataLoad> createETLDataLoad(@RequestBody ETLDataLoad eTLDataLoad) throws URISyntaxException {
	        logger.debug("REST request to save ETLDataLoad : {}", eTLDataLoad);
	        
	        ETLDataLoad result = etlDataLoadRepository.save(eTLDataLoad);
	        return ResponseEntity.created(new URI("/api/addDataToElt/" + result.getRecordId()))
	            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,"hi"))
	            .body(result);
	    }


	 public void sendAlertActivity(String guid, String alertName) throws JSONException {
		logger.info("Sending data to kafka alert_activity "); 
		String url = "http://100.64.108.25:5055/api/getAlert/"+guid;
		Alert alert = restTemplate.getForObject(url, Alert.class);
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("guid", guid);
		jsonObject.put("name", alertName);
		jsonObject.put("action","New alert");
		jsonObject.put("action_description", "New alert generated from ETL data load service");
		jsonObject.put("created_on", alert.getCreatedOn().toEpochMilli());
		jsonObject.put("updated_on", Instant.now().toEpochMilli());
		jsonObject.put("alert_state", alert.getAlertState());
		jsonObject.put("ticket_id", 0);
		jsonObject.put("ticket_name", "");
		jsonObject.put("ticket_url", "");
		jsonObject.put("ticket_description", "");
		jsonObject.put("user_name", "Automated");
		jsonObject.put("event_type", "Update");
		jsonObject.put("change_log", "");
		jsonObject.put("fired_time", Instant.now().toEpochMilli());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString("http://100.64.108.25:8190/kafka/send")
				.queryParam("topic", "alert_activity").queryParam("msg", jsonObject.toString());
		restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
		logger.info("Data sent to kafka alert_activity queue");
	}
}
