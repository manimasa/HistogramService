package com.doubleverify.HstService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.doubleverify.HstService.api.HstServiceResponse;
import com.doubleverify.HstService.model.Hist;
import com.doubleverify.HstService.model.HstData;

@Service
@RequiredArgsConstructor
public class HstServiceImpl implements HstService {
	private static final Logger log = LoggerFactory.getLogger(HstService.class);
	private final int MAX_HST_DATA = 100;

	private final ProHstService service;


	@Override
	public List<HstServiceResponse> getHst(String url) {

		try {
			final String html = Jsoup.connect(url).get().html();
			Document doc = Jsoup.parse(html);
			String data =(doc.body().text());
			List<String> urlData = Arrays.asList(data.split(" "));

			log.info("I got {} words in total from the URL {}", urlData.size(), url);

			return buildHistResponse(service.getTopHistogramData(MAX_HST_DATA, service.getHistogram(urlData)));
		} catch (IOException e) {
			log.error("An exception occurred while requesting url data", e);
		}

		return null;
	}

	private List<HstServiceResponse> buildHistResponse(Hist hst){
		final List<HstServiceResponse> hstRespLst = new ArrayList<>();

		for(HstData hstDat : hst.getData()) {
			hstRespLst.add(HstServiceResponse
					.builder()
					.frequency(hstDat.getFrequency())
					.word(hstDat.getWord()).build());
		}

		return hstRespLst;
	}

}
