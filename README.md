# The thread pool executing tasks periodically and terminating them safely
In this pool, programmers can setup pool size, make task execute periodically, and even terminate all tasks in the pool safely. If the programmers ensure that all of tasks assigned to the thread pool do not have deadlock and would finish their works in an acceptable time interval, they can just send a shutdown signal to terminate all of the tasks in the pool.

# Why use it
Java has provided a convenient API, scheduleAtFixedRate, which executes tasks with given period. However, the disadvantage of this API is that it cannot terminate the tasks, which is executing in thread pool. To fix this issue, I propose this thread pool.

# How to use it
Please refer to the example code in the example folder. It will display the message similar as below while being executed.

[PeriodicThread] Create!!

2016-03-21 17:50:10.025 The task is alive.

2016-03-21 17:50:15.042 The task is alive.

2016-03-21 17:50:18.022 Shutdown now !!

2016-03-21 17:50:18.022 Receiving shutdown signal, Ending the process.

2016-03-21 17:50:28.022 Ending



# The next step
I am keeping improving this thread pool for making it better. If you have any suggestion for it, please tell me without hesitation.
