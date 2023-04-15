/**
 * Runs all threads
 */

public class BridgeRunner {
	public static void main(String[] args) {
		if(args.length != 2){
			System.err.println("Please provide program arguments in the form of:\n\tjava BridgeRunner <bridge limit> <num cars>");
			return;
		}

		int bridgeLimit = Integer.parseInt(args[0]);
		int numCars = Integer.parseInt(args[1]);
		
		if(bridgeLimit <= 0){
			System.err.println("Your bridge needs to be stable enough to hold at least one car!");
			return;	
		}
		if(numCars < 0){
			System.err.println("Negative cars don't make sense!");
			return;
		}

		Bridge bridge = new OneLaneBridge(bridgeLimit);
		
		Thread[] threads = new Thread[numCars];
		for(int i = 0; i < numCars; i++){
			threads[i] = new Thread(new Car((int)(Math.random() * Integer.MAX_VALUE), bridge));
			threads[i].start();
		}

		for(int i = 0; i < numCars; i++){try{threads[i].join();}catch(InterruptedException e){System.err.println("Threads broke :P");}}	

		System.out.println("All cars have crossed!!");
	}
}
