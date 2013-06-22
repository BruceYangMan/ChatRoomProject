package Test;

public class ShutdownHookSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShutdownHookSample sample = new ShutdownHookSample();
		sample.attachShutDownHook();
		System.out.println("Last instruction of Program....");
		//System.exit(0);
	}
	
	public void attachShutDownHook(){
		  Runtime.getRuntime().addShutdownHook(new Thread() {
		   @Override
		   public void run() {
		    System.out.println("Inside Add Shutdown Hook");
		    
		   }
		  });
		  System.out.println("Shut Down Hook Attached.");
		  
		 }

}
