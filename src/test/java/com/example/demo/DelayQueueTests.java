package com.example.demo;

import com.example.demo.delay_queue.ExpirationMessagePostProcessor;
import com.example.demo.delay_queue.ProcessReceiver;
import com.example.demo.delay_queue.QueueConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DelayQueueTests
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDelayQueuePerMessageTTL() throws InterruptedException
    {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 3; i >= 1; i--)
        {
            long expiration = i * 1000;
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                    (Object)("Message From delay_queue_per_message_ttl with expiration " + expiration), new ExpirationMessagePostProcessor(expiration));
        }
        ProcessReceiver.latch.await();
    }

    @Test
    public void testDelayQueuePerQueueTTL() throws InterruptedException
    {
        ProcessReceiver.latch = new CountDownLatch(3);
        System.out.println(System.currentTimeMillis());
        for (int i = 1; i <= 3; i++)
        {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    15);
        }
        ProcessReceiver.latch.await();
    }

    @Test
    public void testFailMessage() throws InterruptedException
    {
        ProcessReceiver.latch = new CountDownLatch(6);
        for (int i = 1; i <= 3; i++)
        {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_PROCESS_QUEUE_NAME, ProcessReceiver.FAIL_MESSAGE);
        }
        ProcessReceiver.latch.await();
    }
}
