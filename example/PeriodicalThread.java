import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PeriodicalThread implements Runnable {
	private AtomicBoolean flag_shutdown = new AtomicBoolean(false);
	private Lock lock = new ReentrantLock();
	private Condition shutdownNow = lock.newCondition();
	
	private int interval; // in sec
	private PeriodicalTask task;

	public PeriodicalThread(int interval, PeriodicalTask task) {
		System.out.println("[PeriodicThread] Create!!");
		this.interval = interval;
		this.task = task;
	}

	public void SetShutdown(boolean val) {
		flag_shutdown.set(val);
		try{
			lock.lock();
			shutdownNow.signal();
		} finally {
			lock.unlock();
		}
	}

	public PeriodicalTask getTask() {
		return task;
	}

	synchronized private void Delay(int time) {
		if (time < 0) {
			return;
		} else if (flag_shutdown.get()) {
			System.out.println("[PeriodicThread] Receiving shutdown signal, Ending Delay.");
			return;
		}
		
		try {
			lock.lock();
			shutdownNow.await(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		if (interval <= 0) {
			System.out.println("[PeriodicThread] Invalid time4start/interval. Ending the process.");
			return;
		}

		int time4start;
		while (true) {
			time4start = interval - (int) (System.currentTimeMillis() / 1000) % interval;
			Delay(time4start);
			if (flag_shutdown.get()) {
				System.out.println(new Timestamp(System.currentTimeMillis()) + " Receiving shutdown signal, Ending the process.");
				SetShutdown(false);
				return;
			}
			// run the task
			task.task();
		}
	}
}
