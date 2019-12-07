package com.artins.TextAnalizer.service;

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

import com.artins.TextAnalizer.api.TextAnalizerResponse;
import com.artins.TextAnalizer.model.WordHistogram;

@Service
public class TextAnalizerImpl implements TextAnalizer {
	private static final Logger log = LoggerFactory.getLogger(TextAnalizer.class);
	
	@Autowired
	private WordFreqencyHistogramService service;
	
	@Override
	public List<TextAnalizerResponse> getScore(String url) {
		final String html;
		try {
			html = Jsoup.connect(url).get().html();
			Document doc = Jsoup.parse(html);
			String data =(doc.body().text());
			List<String> words = Arrays.asList(data.split(" "));
			log.info("Size of word is {}", words.size());
			return buildResponseObj(service.getHistogram(words));
		} catch (MalformedURLException e) {
			//TODO: How to respond to the controller
				e.printStackTrace();
		} catch (IOException e) {
			//TODO: How to respond to the controller
			e.printStackTrace();
		}
		//TODO: Return score
		return null;
	}
	
	private List<TextAnalizerResponse> buildResponseObj(WordHistogram wh){
		
		final List<TextAnalizerResponse> list = new ArrayList<>();
		log.info("WordHistogram size of word is {}", wh.getHistogram().size());

		for(int i = 0; i < wh.getHistogram().size(); i++) {
			list.add(TextAnalizerResponse.builder().frequency(wh.getHistogram().get(i).getFrequency()).word(wh.getHistogram().get(i).getWord()).build());
		}
		
		return list;
	}

}
