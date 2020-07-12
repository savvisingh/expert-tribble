package com.example.deliveryherotest.utils.scheduler

import io.reactivex.Scheduler
interface ISchedulers {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}