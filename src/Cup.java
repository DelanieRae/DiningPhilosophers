class Cup {
	final int totalChopsticks = 5;
	private int chopsticksInUse = 0;

	/**
	 * If there is more then two chop sticks return true or if there is less then two return false
	 * @return
	 */
	public synchronized boolean acquireChopsticks() {
		if (chopsticksInUse <= totalChopsticks) {
			chopsticksInUse += 2;
			//System.out.println("Phil has some chop sticks");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Puts the chop sticks back into the cup.  Throws an error if the counts are wrong.
	 */
	public synchronized void releaseChopsticks() {
		if (chopsticksInUse >= totalChopsticks-2) {
			throw new Error("Somehow, there are more than 5 chopsticks.");
		}
		else {
			chopsticksInUse -= 2;
			//System.out.println("Phil returned the chop sticks");
			notifyAll();
		}
	}
			
}
