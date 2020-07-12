package com.example.deliveryherotest.repository.api.helper

interface ITransformer<T, R> {
    fun transform(t: T): R?
}