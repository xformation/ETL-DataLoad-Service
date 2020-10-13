package com.brc.etl.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brc.etl.config.Constants;
import com.brc.etl.domain.ETLDataLoad;
import com.brc.etl.repository.ETLDataLoadRepository;

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
		List<ETLDataLoad> list = etlDataLoadRepository.findAll();
		List<ETLDataLoad> govtDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> navDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> sednaDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> xerfDataList = new ArrayList<ETLDataLoad>();
		List<ETLDataLoad> dcsDataList = new ArrayList<ETLDataLoad>();
		for (ETLDataLoad etlDataLoad : list) {
			if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_GOVT)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_NAV)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_SEDNA)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_XREF)) {
				govtDataList.add(etlDataLoad);
			} else if (etlDataLoad.getDataFlowType().equalsIgnoreCase(Constants.TYPE_DCS)) {
				govtDataList.add(etlDataLoad);
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
		govtDataList.sort((ETLDataLoad e1, ETLDataLoad e2) -> LocalDate.parse(e1.getLastDataLoadDate(), formatter)
				.compareTo(LocalDate.parse(e2.getLastDataLoadDate(), formatter)));
		return govtDataList;
	}
}
