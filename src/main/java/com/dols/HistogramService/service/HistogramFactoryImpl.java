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
			//Create as many evenly distributed thread resource for the threads
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
			log.error("An exception occurred while waiting for threads", e);
		}
		return getDistinctWordsFrom(histogram);
	}

	public Histogram buildAndGetMaxWordLengthFromHistogram(int max, Histogram histogram) {
		 int maxCounter = Math.min(histogram.getData().size(), max);

		List<HistogramData> dynamicHistogramList = new ArrayList<>();
		final List<HistogramData> finalHistogramList = histogram.getData();
		Histogram resultHistogram = new Histogram();

		for (HistogramData histogramDataToCompareWith : finalHistogramList) {
			if (!dynamicHistogramList.contains(histogramDataToCompareWith)) {
				HistogramData finalHistogramData = histogramDataToCompareWith;
				for (int index = 0; index < finalHistogramList.size(); index++) {
					if (finalHistogramData.getFrequency() < finalHistogramList.get(index).getFrequency()
							&& !finalHistogramList.contains(histogramDataToCompareWith)) {
						finalHistogramData = finalHistogramList.get(index);
					}
				}

				dynamicHistogramList.add(finalHistogramData);
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
		private List<HistogramData> histogramDataList = new ArrayList<>();
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

				for (int currentIndex = 0; currentIndex < histogramDataList.size(); currentIndex++) {

					if (histogramDataList.get(currentIndex).getWord().equals(mainWord)) {
						freq++;
						int histogramDataFrq = histogramDataList.get(currentIndex).getFrequency();
						HistogramData updatedHistogramData = HistogramData.builder().word(mainWord).frequency(histogramDataFrq + freq).build();
						histogramDataList.set(currentIndex, updatedHistogramData);
					}
				}

				if (freq == 1 && mainWord.length() >= MAX_WORD_LENGTH) {
					histogramDataList.add(HistogramData.builder().word(mainWord).frequency(freq).build());
				}
			}

			long taskTime = (System.currentTimeMillis() - startTime);
			log.info("Thread {} completed in {} ms ", id, taskTime);
			histogram.append(histogramDataList);
		}
	}

	private Histogram getDistinctWordsFrom(Histogram histogram) {
		List<HistogramData> dynamicHistogramList = new ArrayList<>();
		Histogram compiledHistogramList = new Histogram();

		for (int i = 0; i < histogram.getData().size(); i++) {
			HistogramData currentHistogramData = histogram.getData().get(i);

			boolean histogramDataExists = false;

			for (int currentIndex = 0; currentIndex < dynamicHistogramList.size(); currentIndex++) {

				if (dynamicHistogramList.get(currentIndex).getWord().equals(currentHistogramData.getWord())) {
					int histogramDataFreq = dynamicHistogramList.get(currentIndex).getFrequency() + currentHistogramData.getFrequency();
					HistogramData updatedHistogramData = HistogramData.builder().word(currentHistogramData.getWord()).frequency(histogramDataFreq).build();
					dynamicHistogramList.set(currentIndex, updatedHistogramData);
					histogramDataExists = true;
				}
			}

			if (!histogramDataExists) {
				dynamicHistogramList.add(currentHistogramData);
			}
		}

		compiledHistogramList.setData(dynamicHistogramList);

		log.info("Successfully compiled distinct words.");

		return compiledHistogramList;
	}

}
