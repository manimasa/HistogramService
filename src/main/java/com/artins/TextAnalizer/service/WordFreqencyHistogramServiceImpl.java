package com.artins.TextAnalizer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.model.WordHistogram;
import com.artins.TextAnalizer.model.WordScore;
import com.artins.TextAnalizer.utils.SortByWordScore;

import lombok.Data;

@Service
public class WordFreqencyHistogramServiceImpl implements WordFreqencyHistogramService {
	private final int WORDS_PER_WORKER_THREAD = 1000;
	private List<String> workerThreadsResource = new ArrayList<>();
	private WordHistogram wordHistogram = new WordHistogram();
	private final int MAX_WORD_LENGTH = 3;

	private static final Logger log = LoggerFactory.getLogger(TextAnalizer.class);

	@Override
	public WordHistogram getHistogram(List<String> text) {
		workerThreadsResource = text;
		final int resourceSize = workerThreadsResource.size();
		// Should be cleared
		wordHistogram.getHistogram().clear();

		log.info("Resource size is {} and length of workerThreadsResource is {}", resourceSize,
				workerThreadsResource.size());

		int nrOfWorkerThreads = 0;

		List<Thread> workerThreads = new ArrayList<>();
		for (int i = 0; i < resourceSize; i += WORDS_PER_WORKER_THREAD) {
			List<String> workerThreadResource = new ArrayList<String>(
					workerThreadsResource.subList(i, Math.min(resourceSize, i + WORDS_PER_WORKER_THREAD)));
			workerThreads.add(new Thread(new WorkerThread(workerThreadResource, nrOfWorkerThreads)));
			workerThreads.get(nrOfWorkerThreads).start();
			nrOfWorkerThreads++;
		}

		try {

			for (Thread t : workerThreads) {
				t.join();
			}

		} catch (InterruptedException e) {

		}

		// addFrequencies();
		return getDistinctWord(wordHistogram);
		//return wordHistogram;
	}

	@Data
	private class WorkerThread implements Runnable {
		private List<String> resource = new ArrayList<>();
		private List<WordScore> wordScores = new ArrayList<>();
		private int id;

		WorkerThread(List<String> resource, int id) {
			this.resource = resource;
			this.id = id;
		}

		@Override
		public void run() {
			log.info("WorkerThread {} starts at {} ", id, System.currentTimeMillis());

			for (int i = 0; i < resource.size(); i++) {
				String mainWord = resource.get(i);

				int freq = 1;

				for (int j = 0; j < wordScores.size(); j++) {
					// List contains and frequency here?
					if (wordScores.get(j).getWord().equals(mainWord)) {
						freq++;
						int theFrq = wordScores.get(j).getFrequency();
						WordScore tmpWord = WordScore.builder().word(mainWord).frequency(theFrq + freq).build();
						wordScores.set(j, tmpWord);
					}
				}

				if (freq == 1 && mainWord.length() >= MAX_WORD_LENGTH) {
					wordScores.add(WordScore.builder().word(mainWord).frequency(freq).build());
				}
			}



			wordHistogram.append(wordScores);
			
			
			if(id == 1) {
				for(int i = 0; i  < wordScores.size(); i++) {
					System.out.println(wordScores.get(i));
				}
			}
		}
	}

	public WordHistogram getTop(int max, WordHistogram lst) {
		WordHistogram result = new WordHistogram();
		log.info("Here is the result size should be 0 {} ", result.getHistogram().size());

		for (WordScore word : lst.getHistogram()) {
			int freq = word.getFrequency();
			WordScore topWord = word;

			for (WordScore tmp : lst.getHistogram()) {
				if (tmp.getFrequency() > freq) {
					topWord = tmp;
				}
			}
			result.getHistogram().add(topWord);

			max--;

			if (max == 0) {
				break;
			}

		}

		Collections.sort(result.getHistogram(), new SortByWordScore());

		log.info("Here is the result size should be hundred {} ", result.getHistogram().size());

		return result;
	}

	private void addFrequencies() {
		log.info("Size before {} starts at {} ", wordHistogram.getHistogram().size());

		for (int i = 0; i < wordHistogram.getHistogram().size() - 1; i++) {
			WordScore wordScore = wordHistogram.getHistogram().get(i);
			int wordFrequencies = wordScore.getFrequency();

			for (int j = 0; j < wordHistogram.getHistogram().size(); j++) {
				WordScore anotherWordScore = wordHistogram.getHistogram().get(j);

				if (wordScore.getWord().equals(anotherWordScore.getWord())) {
					// wordFrequencies += anotherWordScore.getFrequency();

					wordHistogram.getHistogram().remove(j);
				}
			}
			// wordHistogram.getHistogram().get(i).setFrequency(wordFrequencies);
		}

		log.info("Size after {}", wordHistogram.getHistogram().size());

	}
	
	private WordHistogram getDistinctWord(WordHistogram list){
		 List<WordScore> wordScores = new ArrayList<>();
		 WordHistogram result = new WordHistogram();
		
		for (int i = 0; i < list.getHistogram().size(); i++) {
			WordScore mainWord = list.getHistogram().get(i);

			boolean exist = false;

			for (int j = 0; j < wordScores.size(); j++) {
				// List contains and frequency here?
				if (wordScores.get(j).getWord().equals(mainWord.getWord())) {
					int theFrq = wordScores.get(j).getFrequency() + mainWord.getFrequency();
					WordScore tmpWord = WordScore.builder().word(mainWord.getWord()).frequency(theFrq).build();
					wordScores.set(j, tmpWord);
					exist = true;
				}
			}

			if (!exist) {
				wordScores.add(mainWord);
			}
		}

		result.setHistogram(wordScores);
		return result;
	}

}
