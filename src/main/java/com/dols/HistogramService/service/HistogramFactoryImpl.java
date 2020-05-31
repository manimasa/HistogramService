package com.dols.HistogramService.service;

import java.util.ArrayList;
import java.util.List;

import com.dols.HistogramService.model.Histogram;
import com.dols.HistogramService.model.HistogramData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class HistogramFactoryImpl implements HistogramFactory {
	private final int LOAD_PER_THREAD = 800;
	private List<String> threadsResource = new ArrayList<>();
	private Histogram histogram = new Histogram();
	private final int MAX_WORD_LENGTH = 3;

	private static final Logger log = LoggerFactory.getLogger(HistogramService.class);

	@Override
	public Histogram buildAndGetHistogramForUrlData(List<String> urlData) {
		histogram.getData().clear();

		threadsResource = urlData;
		final int totalLoad = threadsResource.size();

		int threadNum = 0;

		List<java.lang.Thread> threads = new ArrayList<>();

		for (int i = 0; i < totalLoad; i += LOAD_PER_THREAD) {
			//Create as many evenly distributed thread resource for the workerThreads
			List<String> threadLoad =
					new ArrayList<>(threadsResource.subList(i, Math.min(totalLoad, i + LOAD_PER_THREAD)));

			threads.add(new java.lang.Thread(new Thread(threadLoad, threadNum)));
			threads.get(threadNum).start();
			threadNum++;
		}

		try {
			for (java.lang.Thread t : threads) {
				t.join();
			}
		} catch (InterruptedException e) {
			log.error("An exception occurred when waiting for threads", e);
		}
		return getDistinctWordsFrom(histogram);
	}

	public Histogram buildAndGetMaxWordLengthFromHistogram(int max, Histogram hst) {
		 int maxCounter = Math.min(hst.getData().size(), max);

		List<HistogramData> dynamicHistogramList = new ArrayList<>();
		final List<HistogramData> finalHistogramList = hst.getData();
		Histogram resultHistogram = new Histogram();

		for (HistogramData data : finalHistogramList) {
			if (!dynamicHistogramList.contains(data)) {
				HistogramData toReturn = data;
				for (int i = 0; i < finalHistogramList.size(); i++) {
					if (toReturn.getFrequency() < finalHistogramList.get(i).getFrequency()
							&& !finalHistogramList.contains(data)) {
						toReturn = finalHistogramList.get(i);
					}
				}

				dynamicHistogramList.add(toReturn);
			}
			maxCounter--;
			if (maxCounter == 0) {break;}
		}

		resultHistogram.setData(dynamicHistogramList);
		log.info("Successfully compiled histogram.");
		return resultHistogram;
	}

	@Data
	private class Thread implements Runnable {
		private List<String> load;
		private List<HistogramData> histogramData = new ArrayList<>();
		private int id;

		Thread(List<String> load, int id) {
			this.load = load;
			this.id = id;
		}

		@Override
		public void run() {
			log.info("Thread {} is running", id);
			long startTime = System.currentTimeMillis();

			for (String mainWord : load) {
				int freq = 1;

				for (int j = 0; j < histogramData.size(); j++) {

					if (histogramData.get(j).getWord().equals(mainWord)) {
						freq++;
						int theFrq = histogramData.get(j).getFrequency();
						HistogramData tmpWord = HistogramData.builder().word(mainWord).frequency(theFrq + freq).build();
						histogramData.set(j, tmpWord);
					}
				}

				if (freq == 1 && mainWord.length() >= MAX_WORD_LENGTH) {
					histogramData.add(HistogramData.builder().word(mainWord).frequency(freq).build());
				}
			}

			long taskTime = (System.currentTimeMillis() - startTime);
			log.info("Thread {} completed in {} ms ", id, taskTime);
			histogram.append(histogramData);
		}
	}

	private Histogram getDistinctWordsFrom(Histogram hst) {
		List<HistogramData> dynamicHistogramList = new ArrayList<>();
		Histogram resultHistogram = new Histogram();

		for (int i = 0; i < hst.getData().size(); i++) {
			HistogramData mainWord = hst.getData().get(i);

			boolean exist = false;

			for (int j = 0; j < dynamicHistogramList.size(); j++) {

				if (dynamicHistogramList.get(j).getWord().equals(mainWord.getWord())) {
					int theFrq = dynamicHistogramList.get(j).getFrequency() + mainWord.getFrequency();
					HistogramData tmpWord = HistogramData.builder().word(mainWord.getWord()).frequency(theFrq).build();
					dynamicHistogramList.set(j, tmpWord);
					exist = true;
				}
			}

			if (!exist) {
				dynamicHistogramList.add(mainWord);
			}
		}

		resultHistogram.setData(dynamicHistogramList);
		log.info("Successfully compiled distinct words.");
		return resultHistogram;
	}

}
