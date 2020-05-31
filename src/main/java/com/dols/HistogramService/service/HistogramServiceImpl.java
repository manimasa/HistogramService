package com.dols.HistogramService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dols.HistogramService.api.HistogramServiceResponse;
import com.dols.HistogramService.model.Histogram;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dols.HistogramService.model.HistogramData;

@Service
@RequiredArgsConstructor
public class HistogramServiceImpl implements HistogramService {
	private static final Logger log = LoggerFactory.getLogger(HistogramService.class);
	private final int MAX_HST_DATA = 100;

	private final HistogramFactory factory;


	@Override
	public List<HistogramServiceResponse> getHistogram(String url) {

		try {
			final String html = Jsoup.connect(url).get().html();
			Document doc = Jsoup.parse(html);
			String data =(doc.body().text());
			List<String> urlData = Arrays.asList(data.split(" "));

			log.info("Got {} words in total from the URL {}", urlData.size(), url);

			return buildHistogramResponse(factory.buildAndGetMaxWordLengthFromHistogram(MAX_HST_DATA, factory.buildAndGetHistogramForUrlData(urlData)));
		} catch (IOException e) {
			log.error("An exception occurred while requesting url data", e);
		}

		return null;
	}

	private List<HistogramServiceResponse> buildHistogramResponse(Histogram histogram){
		final List<HistogramServiceResponse> histogramResponseList = new ArrayList<>();

		for(HistogramData histogramData : histogram.getData()) {
			histogramResponseList.add(HistogramServiceResponse
					.builder()
					.frequency(histogramData.getFrequency())
					.word(histogramData.getWord()).build());
		}

		return histogramResponseList;
	}

}
