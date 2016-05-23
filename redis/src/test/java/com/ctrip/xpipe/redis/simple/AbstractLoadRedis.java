package com.ctrip.xpipe.redis.simple;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wenchao.meng
 *
 * May 23, 2016
 */
public abstract class AbstractLoadRedis extends AbstractRedis{

	protected long total = 1 << 30;
	
	protected final AtomicLong current = new AtomicLong();

	public AbstractLoadRedis(InetSocketAddress master) {
		super(master);
	}
	
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		
		scheduled.scheduleWithFixedDelay(new Runnable() {
			
			private long lastNum = 0;
			private long lastTimeMili = System.currentTimeMillis();
			@Override
			public void run() {
				
				long currentNum = current.get();
				long currentTime = System.currentTimeMillis();
				
				long deltaSeconds = (currentTime - lastTimeMili)/1000; 
				if(deltaSeconds > 0){
					logger.info("[SEND RATE]{}", (currentNum - lastNum)/deltaSeconds);
				}
				
				lastNum = currentNum;
				lastTimeMili = currentTime;
			}
		}, 5, 5, TimeUnit.SECONDS);
		
		
	}

	public long increase(){
		
		long next = current.incrementAndGet();
		if(next > total){
			current.decrementAndGet();
			return -1;
		}
		return next; 
	}
	


}
