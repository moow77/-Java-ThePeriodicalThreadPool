import java.sql.Timestamp;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TestTask implements PeriodicalTask {

	@Override
	public void task() {
		// TODO Auto-generated method stub
		System.out.println(
				new Timestamp(System.currentTimeMillis()) 
				+ " The task is alive.");
	}

	public static void main(String[] args) {
		int pool_size = 1;
		ScheduledThreadPoolExecutor exector = new ScheduledThreadPoolExecutor(pool_size);
		PeriodicalThread p_thread = new PeriodicalThread(5, new TestTask());
		exector.execute(p_thread);
		
		try {
			Thread.sleep(11000);
			
			System.out.println(new Timestamp(System.currentTimeMillis()) + " Shutdown now !!");
			p_thread.SetShutdown(true);
			
			Thread.sleep(10000);
			System.out.println(new Timestamp(System.currentTimeMillis()) + " Ending");
		} catch(Exception e) {}
	}
}
