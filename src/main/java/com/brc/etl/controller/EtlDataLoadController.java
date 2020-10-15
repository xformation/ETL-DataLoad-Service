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
			logger.error("sdsDataList exception : ",e);
		}
		
		try {
			dcsDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
//			ETLDataLoad maxDateRecord=dcsDataList.get(0);
			if(dcsDataList.size() > 0) {
				dailyMaxList.add(dcsDataList.get(0));
			}
		}catch(Exception e) {
			logger.error("dcsDataList exception : ",e);
		}
		
		try {
			xerfDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(xerfDataList.size() > 0) {
				dailyMaxList.add(xerfDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("xerfDataList exception : ",e);
		}
		
		try {
			sednaDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(sednaDataList.size() > 0) {
				dailyMaxList.add(sednaDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("sednaDataList exception : ",e);
		}
		
		try {
			navDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(navDataList.size() >0 ) {
				dailyMaxList.add(navDataList.get(0));
			}
			
		}catch(Exception e) {
			logger.error("navDataList exception : ",e);
		}
		
		try {
			govtDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e2.getLastDataLoadDate(), formatter)
					.compareTo(LocalDate.parse(e1.getLastDataLoadDate(), formatter)));
			if(govtDataList.size() > 0) {
				dailyMaxList.add(govtDataList.get(0));
			}
		}catch(Exception e) {
			logger.error("govtDataList exception : ",e);
		}
		
		Map<String, String> map=new HashMap<String, String>();
		for(ETLDataLoad maxDateRecord: dailyMaxList) {
			if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_HOURLY)) {
				
			}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_DAILY)) {
					if(LocalDate.parse(maxDateRecord.getLastDataLoadDate(), formatter).equals(LocalDate.now())) {
						map.put(maxDateRecord.getDataFlowType(), "success");
					}else {
						JSONObject jsonObject=new JSONObject();
//						jsonObject.put("recordId", maxDateRecord.getRecordId());
//						jsonObject.put("dataFlowType",maxDateRecord.getDataFlowType() );
//						jsonObject.put("message","failed");
						UUID uuid=UUID.randomUUID();
						String alertName = maxDateRecord.getDataFlowType() + "_" + maxDateRecord.getDataLoadFrequency() + "_failed_"+System.currentTimeMillis();
						jsonObject.put("guid", uuid.toString());
						jsonObject.put("name", alertName);
						jsonObject.put("severity", "Hight");
						jsonObject.put("monitorcondition", "Fired");
						jsonObject.put("affectedresource", "ETL DataLoad Service");
						jsonObject.put("monitorservice", "Synectiks");
						jsonObject.put("signaltype", "Metrics");
						jsonObject.put("brcsubscription", "Alert Management");
						jsonObject.put("suppressionstate", "None");
						jsonObject.put("resourcegroup", "Compute");
						jsonObject.put("resources", "App");
						jsonObject.put("firedtime",Instant.now());
						jsonObject.put("createdon", Instant.now());
						jsonObject.put("updatedon", Instant.now());
						jsonObject.put("alert_state", "New");
						jsonObject.put("client", "EXTERNAL SERVICE");
						jsonObject.put("client_url", "");
						jsonObject.put("description", "");
						jsonObject.put("details", "Alert generated by external service");
						jsonObject.put("incident_key", uuid);
						map.put(maxDateRecord.getDataFlowType(), "failed");
						map.put("alertname", alertName);
//						System.out.println(jsonObject.toString());
						try {
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
							UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://100.64.108.25:8190/kafka/send")
									.queryParam("topic", "alert").queryParam("msg", jsonObject.toString());
							restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity,String.class);
							}catch(Exception e) {
								logger.error("Exception in sending aler to kafka: ",e);
								map.put(maxDateRecord.getDataFlowType(), "failed");
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


}
