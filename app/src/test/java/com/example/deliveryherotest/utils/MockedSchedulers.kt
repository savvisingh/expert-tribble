package com.example.deliveryherotest.utils

import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MockedSchedulers: ISchedulers {
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }
}