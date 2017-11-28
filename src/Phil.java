public class Phil implements Runnable {
	
	static boolean running = false;
	static final int NUM_PHIL = 5;

	Cup c = new Cup();
	public static final long PROCESSING_TIME = 5 * 1000;
	static Phil[] phil = new Phil[NUM_PHIL];
	
	/**
	 * The main method creates the threads, spins up the threads, and stops threads.
	 * @param args
	 */
	public static void main(String[] args) {
		running = true;
		for (int i = 0; i < NUM_PHIL; i++) {
			phil[i] = new Phil();
		}
		delay(PROCESSING_TIME, "Philospher malfunction");
		for (Phil p : phil) {
			p.stopPhil();
			p.waitToStop();
		}
	}

	/**
	 * Makes the main method sleep for alloted amount of time
	 * @param time
	 * @param errMsg
	 */
	private static void delay(long time, String errMsg) {
		long sleepTime = Math.max(1, time);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			System.err.println(errMsg);
		}
		
		System.out.println("Philsopher 1 ate: " + phil[0].getCounter() + " times.");
		System.out.println("Philsopher 2 ate: " + phil[1].getCounter() + " times.");
		System.out.println("Philsopher 3 ate: " + phil[2].getCounter() + " times.");
		System.out.println("Philsopher 4 ate: " + phil[3].getCounter() + " times.");
		System.out.println("Philsopher 5 ate: " + phil[4].getCounter() + " times.");
	}
	

	public Thread thread;
	int eatCounter = 0;
	
	/**
	 * Creates threads
	 */
	public Phil() {
		thread = new Thread(this);
		thread.start();

	}
	
	/**
	 * returns eatCounter
	 * @return
	 */
	public int getCounter() {
		return eatCounter;
	}
	
	/**
	 * sets running to false
	 */
	public void stopPhil() {
		//System.out.println("Phil stops");
		running = false;
	}

	/**
	 * waits for the previous phil thread to stop
	 */
	public void waitToStop() {
			try {
				//System.out.println("waiting to stop");
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(thread.getName() + " Error");
			}		
	}

	/**
	 * Makes the philosopher sleep for a random amount of time 
	 */
	public void think() {
		long timeToSleep = (long) (Math.random() * 10);
		try {
			//System.out.println(Thread.currentThread().getName()+" thinking");
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			System.err.println("Something went wrong when a Philosipher tried to think.");
		}
	}
	
	/**
	 * Makes the philosopher eat for a random amount of time
	 */
	public void TrytoEat() {
		long timeToEat = (long) (Math.random() * 10);
		if (c.acquireChopsticks()) {
			try {
				//System.out.println(Thread.currentThread().getName()+" eating all the stuffs");
				Thread.sleep(timeToEat);
				eatCounter++;
				c.releaseChopsticks();
				think();
			} catch (InterruptedException e) {
				System.err.println("Tried to grab the chopsticks and something went wrong");
			}
		} else {
			think();
		}
	}

	/**
	 * While running is true the philosopher tries to eat
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			TrytoEat();
		}

	}

}
