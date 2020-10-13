package com.brc.etl.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brc.etl.config.Constants;
import com.brc.etl.domain.ETLDataLoad;
import com.brc.etl.repository.ETLDataLoadRepository;
import com.brc.etl.service.dto.ETLDataLoadDTO;
import com.brc.etl.web.rest.errors.BadRequestAlertException;

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

	@GetMapping("/listEtlData")
	public List<ETLDataLoad> loadEtlData() {
		System.out.println("hi");
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
	public Map<String, String> loadEtlDataCheck() {
		System.out.println("hi");
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
		ETLDataLoad maxDateRecord=sdsDataList.get(0);
		Map<String, String> map=new HashMap<String, String>();
		if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_HOURLY)) {
			
		}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_DAILY)) {
				if(LocalDate.parse(maxDateRecord.getLastDataLoadDate(), formatter).equals(LocalDate.now())) {
					map.put(maxDateRecord.getDataFlowType(), "success");
				}else {
					map.put(maxDateRecord.getDataFlowType(), "failed");
				}
		}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_MONTHLY)) {
			
		}else if(maxDateRecord.getDataLoadFrequency().equalsIgnoreCase(Constants.LOAD_FREQUENCY_YEARLY)) {
			
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
