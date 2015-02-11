package service

import java.util.concurrent.Executors

import com.firebase.client.Firebase
import com.redis.RedisClient

import scala.concurrent.{ExecutionContext, Future}

trait MeterService {
    val redisClient = new RedisClient("localhost", 6379)
    val fireBaseClient = new Firebase("https://blinding-heat-5589.firebaseio.com/")
    val executorService = Executors.newFixedThreadPool(4)
    val executionContext = ExecutionContext.fromExecutorService(executorService)

    def stream_update(meterUUID: String, usage: Int) = Future {
        val currentUsage = redisClient.incrby(meterUUID, usage).get
        fireBaseClient.child(meterUUID).setValue(currentUsage)
    }
}
