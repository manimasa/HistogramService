package com.verify.HstService.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verify.HstService.api.HstServiceResponse;
import com.verify.HstService.model.Hist;

@Service
public class HstServiceImpl implements HstService {
	private static final Logger log = LoggerFactory.getLogger(HstService.class);
	
	@Autowired
	private ProcessHstService service;
	
	@Override
	public List<HstServiceResponse> getScore(String url) {
		
		try {
			final String html = Jsoup.connect(url).get().html();
			Document doc = Jsoup.parse(html);
			String data =(doc.body().text());
			List<String> words = Arrays.asList(data.split(" "));
			
			log.info("Got {} words from URL", words.size());
			
			return buildHistResponse(service.getTopHistogramData(100, service.getHistogram(words)));
		} catch (MalformedURLException e) {
			log.error("An exception occured requesting url data", e);
		} catch (IOException e) {
			log.error("An exception occured requesting url data", e);
		}

		return null;
	}
	
	private List<HstServiceResponse> buildHistResponse(Hist hist){
		final List<HstServiceResponse> list = new ArrayList<>();
		
		for(int i = 0; i < hist.getData().size(); i++) {
			list.add(HstServiceResponse.builder().frequency(hist.getData().get(i).getFrequency()).word(hist.getData().get(i).getWord()).build());
		}
		
		return list;
	}

}
