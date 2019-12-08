package com.artins.TextAnalizer.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.model.WordHistogram;
import com.artins.TextAnalizer.model.WordScore;

import lombok.Data;

@Service
public class WordFreqencyHistogramServiceImpl implements WordFreqencyHistogramService {
	private final int WORDS_PER_WORKER_THREAD = 1000;
	private List<String> workerThreadsResource = new ArrayList<>();
	private WordHistogram wordHistogram = new WordHistogram();

	private static final Logger log = LoggerFactory.getLogger(TextAnalizer.class);

	@Override
	public WordHistogram getHistogram(List<String> text) {
		workerThreadsResource = text;
		final int resourceSize = workerThreadsResource.size();
		
		log.info("Resource size is {}", resourceSize);

		int nrOfWorkerThreads = 0;
		
		List<Thread> workerThreads = new ArrayList<>();
		for (int i = 0; i < resourceSize; i += WORDS_PER_WORKER_THREAD) {
			List<String> workerThreadResource = new ArrayList<String>(workerThreadsResource.subList(i, Math.min(resourceSize, i + WORDS_PER_WORKER_THREAD)));
			workerThreads.add(new Thread(new WorkerThread(workerThreadResource, nrOfWorkerThreads)));
			workerThreads.get(nrOfWorkerThreads).start();
			nrOfWorkerThreads++;
		}
		

		return wordHistogram;
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
				String mainWord = resource.remove(i);
				int freq = 1;

				for (int k = 0; k < resource.size(); k++) {
					String tmpWord = resource.get(k);
					if (mainWord.equals(tmpWord)) {
						resource.remove(k);
						freq++;
					}
				}

				wordScores.add(WordScore.builder().word(mainWord).frequency(freq).build());
			}

			log.info("WorkerThread {} ends at {} removed", id, System.currentTimeMillis());

			wordHistogram.append(wordScores);
		}
	}

}
