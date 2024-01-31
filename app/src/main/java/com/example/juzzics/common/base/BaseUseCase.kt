package com.example.juzzics.common.base

interface BaseUseCase<Arg, ReturnType> {
    suspend operator fun invoke(arg: Arg): Result<ReturnType>
}

interface BaseOnlyReturnUseCase<ReturnType> {
    suspend operator fun invoke(): Result<ReturnType>
}

interface BaseOnlyArgUseCase<Arg> {
    suspend operator fun invoke(arg: Arg)
}

interface BaseEmptyUseCase {
    suspend operator fun invoke()
}