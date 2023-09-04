# 재고시스템으로 알아보는 동시성이슈 해결방법

동시성 이슈란 여러 프로세스 및 스레드가 동시에 동일한 데이터(공유 데이터)를 조작할 때 타이밍이나 접근 순서에 따라 예상했던 결과가 달라질 수 있는 상황입니다.

## Excutors와 ExcutorService

java.util.concurrent.Executors와 java.util.concurrent.ExecutorService를 이용하면 간단히 쓰레드풀을 생성하여 병렬처리를 할 수 있습니다.

### ExecutorService 생성
Executors는 ExecutorService 객체를 생성하며, 다음 메소드를 제공하여 쓰레드 풀을 개수 및 종류를 정할 수 있습니다.

newFixedThreadPool(int) : 인자 개수만큼 고정된 쓰레드풀을 만듭니다.  
newCachedThreadPool(): 필요할 때, 필요한 만큼 쓰레드풀을 생성합니다. 이미 생성된 쓰레드를 재활용할 수 있기 때문에 성능상의 이점이 있을 수 있습니다.  
newScheduledThreadPool(int): 일정 시간 뒤에 실행되는 작업이나, 주기적으로 수행되는 작업이 있다면 ScheduledThreadPool을 고려해볼 수 있습니다.  
newSingleThreadExecutor(): 쓰레드 1개인 ExecutorService를 리턴합니다. 싱글 쓰레드에서 동작해야 하는 작업을 처리할 때 사용합니다.  
다음은 4개의 고정된 쓰레드풀을 갖고 있는 ExecutorService를 생성하는 코드입니다.  

```
ExecutorService executor = Executors.newFixedThreadPool(4);
```

### ExecutorService에 작업(Job) 추가
Executors로 ExecutorService를 생성하였다면, ExecutorService는 작업을 처리할 수 있습니다. ExecutorService.submit() 메소드로 작업을 추가하면 됩니다.

아래 코드에서 `newFixedThreadPool(4)`는 Thread를 4개 생성하겠다는 의미입니다. 그리고 `submit(() -> { })`은 멀티쓰레드로 처리할 작업을 예약합니다. 인자로 람다식을 전달할 수 있습니다.

아래 코드에서 4개의 작업을 예약했고, 예약과 동시에 먼저 생성된 4개의 쓰레드는 작업들을 처리합니다.

```
package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    public static void main(String args[]) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job1 " + threadName);
        });
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job2 " + threadName);
        });
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job3 " + threadName);
        });
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job4 " + threadName);
        });

        // 더이상 ExecutorService에 Task를 추가할 수 없습니다.
        // 작업이 모두 완료되면 쓰레드풀을 종료시킵니다.
        executor.shutdown();

        // shutdown() 호출 전에 등록된 Task 중에 아직 완료되지 않은 Task가 있을 수 있습니다.
        // Timeout을 20초 설정하고 완료되기를 기다립니다.
        // 20초 전에 완료되면 true를 리턴하며, 20초가 지나도 완료되지 않으면 false를 리턴합니다.
        if (executor.awaitTermination(20, TimeUnit.SECONDS)) {
            System.out.println(LocalTime.now() + " All jobs are terminated");
        } else {
            System.out.println(LocalTime.now() + " some jobs are not terminated");

            // 모든 Task를 강제 종료합니다.
            executor.shutdownNow();
        }

        System.out.println("end");
    }
}
```

## CountDownLatch

CountDownLatch는 어떤 쓰레드가 다른 쓰레드에서 작업이 완료될 때 까지 기다릴 수 있도록 해주는 클래스입니다. 

예를 들어, Main thread에서 5개의 쓰레드를 생성하여 어떤 작업을 병렬로 처리되도록 할 수 있습니다.   
이 때 Main thread는 다른 쓰레드가 종료되는 것을 기다리지 않고 다음 코드(statements)를 수행합니다.  
여기서 CountDownLatch를 사용하면 다음 코드(statements)를 실행하지 않고 기다리도록 만들 수 있습니다.  

다른 예로, 어떤 작업을 처리하는데 CPU 리소스가 많이 필요하지 않기 때문에 Main thread에서만 처리하도록 할 수 있습니다.  
하지만 어떤 프로세스가 실행되기를 기다리거나 Network 등의 외부에서 어떤 이벤트가 발생하길 기다린다면, 그런 이벤트가 발생하지 않았을 때 무한히 기다리게 될 수도 있습니다.   
이럴 때, 다른 Thread에서 이 작업을 수행하도록 하고 Main thread는 일정 시간을 초과하면 작업을 기다리지 않도록, Timeout을 설정할 수 있습니다.  

### CountDownLatch 작동 원리
CountDownLatch는 다음과 같이 생성할 수 있습니다. 인자로 Latch의 숫자를 전달합니다.
```CountDownLatch countDownLatch = new CountDownLatch(5);```  

다음과 같이 countDown()을 호출하면 Latch의 숫자가 1개씩 감소합니다.
```countDownLatch.countDown();```  

await()은 Latch의 숫자가 0이 될 때까지 기다리는 코드입니다.
```countDownLatch.await();```  

다른 쓰레드에서 countDown()을 5번 호출하게 된다면 Latch는 0이 되며, await()은 더 이상 기다리지 않고 다음 코드를 실행하게 됩니다.
