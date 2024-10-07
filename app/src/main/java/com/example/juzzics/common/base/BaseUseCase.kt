package com.example.juzzics.common.base

interface BaseUseCase<Arg, ReturnType> : suspend (Arg) -> Result<ReturnType>
interface Base2UseCase<Arg1, Arg2, ReturnType> : suspend (Arg1, Arg2) -> Result<ReturnType>
interface ReturnUseCase<ReturnType> : suspend () -> Result<ReturnType>
interface ArgUseCase<Arg> : suspend (Arg) -> Unit
interface EmptyUseCase : suspend () -> Unit