package com.verify.HstService.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.verify.HstService.model.Hist;
import com.verify.HstService.model.HstData;

import lombok.Data;

@Service
public class ProHstServiceImpl implements ProHstService {
	private final int LOAD_PER_WORKER_THREAD = 800;
	private List<String> workerThreadsResource = new ArrayList<>();
	private Hist hst = new Hist();
	private final int MAX_WORD_LENGTH = 3;

	private static final Logger log = LoggerFactory.getLogger(HstService.class);

	@Override
	public Hist getHistogram(List<String> urlData) {
		hst.getData().clear();
		
		workerThreadsResource = urlData;
		final int totalLoad = workerThreadsResource.size();
		
		int threadNr = 0;
		
		List<Thread> workerThreads = new ArrayList<>();
		
		for (int i = 0; i < totalLoad; i += LOAD_PER_WORKER_THREAD) {
			//Create as many evenly distributed workerThread resource for the workerThreads
			List<String> workerThreadLoad = 
					new ArrayList<String>(workerThreadsResource.subList(i, Math.min(totalLoad, i + LOAD_PER_WORKER_THREAD)));
			
			workerThreads.add(new Thread(new WorkerThread(workerThreadLoad, threadNr)));
			workerThreads.get(threadNr).start();
			threadNr++;
		}
		
		try {
			for (Thread t : workerThreads) {
				t.join();
			}
		} catch (InterruptedException e) {
			log.error("An exception occured when waiting for threads", e);
		}
		return getDistinctWordsOf(hst);
	}

	public Hist getTopHistogramData(int max, Hist hst) {
		List<HstData> dynHst = new ArrayList<>();
		final List<HstData> finalHst = hst.getData();		
		Hist resultHst = new Hist();

		for (HstData data : finalHst) {
			if (!dynHst.contains(data)) {
				HstData toReturn = data;
				for (int i = 0; i < finalHst.size(); i++) {
					if (toReturn.getFrequency() < finalHst.get(i).getFrequency()
							&& !finalHst.contains(data)) {
						toReturn = finalHst.get(i);
					}
				}
				
				dynHst.add(toReturn);
			}
			max--;
			if (max == 0) {break;}
		}

		resultHst.setData(dynHst);
		log.info("Succefully compiled histogram.");
		return resultHst;
	}
	
	@Data
	private class WorkerThread implements Runnable {
		private List<String> load = new ArrayList<>();
		private List<HstData> hstData = new ArrayList<>();
		private int id;

		WorkerThread(List<String> load, int id) {
			this.load = load;
			this.id = id;
		}

		@Override
		public void run() {
			log.info("WorkerThread {} is running", id);
			long startTime = System.currentTimeMillis();
			
			for (int i = 0; i < load.size(); i++) {
				String mainWord = load.get(i);

				int freq = 1;

				for (int j = 0; j < hstData.size(); j++) {

					if (hstData.get(j).getWord().equals(mainWord)) {
						freq++;
						int theFrq = hstData.get(j).getFrequency();
						HstData tmpWord = HstData.builder().word(mainWord).frequency(theFrq + freq).build();
						hstData.set(j, tmpWord);
					}
				}

				if (freq == 1 && mainWord.length() >= MAX_WORD_LENGTH) {
					hstData.add(HstData.builder().word(mainWord).frequency(freq).build());
				}
			}
			
			long taskTime = (System.currentTimeMillis() - startTime);
			log.info("WorkerThread {} completed in {} ms ", id, taskTime);
			hst.append(hstData);
		}
	}

	private Hist getDistinctWordsOf(Hist hst) {
		List<HstData> dynHst = new ArrayList<>();
		Hist resultHst = new Hist();

		for (int i = 0; i < hst.getData().size(); i++) {
			HstData mainWord = hst.getData().get(i);

			boolean exist = false;

			for (int j = 0; j < dynHst.size(); j++) {

				if (dynHst.get(j).getWord().equals(mainWord.getWord())) {
					int theFrq = dynHst.get(j).getFrequency() + mainWord.getFrequency();
					HstData tmpWord = HstData.builder().word(mainWord.getWord()).frequency(theFrq).build();
					dynHst.set(j, tmpWord);
					exist = true;
				}
			}

			if (!exist) {
				dynHst.add(mainWord);
			}
		}

		resultHst.setData(dynHst);
		log.info("Succefully compiled distinct words.");
		return resultHst;
	}

}
