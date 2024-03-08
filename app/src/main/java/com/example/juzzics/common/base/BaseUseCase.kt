package com.example.juzzics.common.base

interface BaseUseCase<Arg, ReturnType> : suspend (Arg) -> Result<ReturnType>
interface ReturnUseCase<ReturnType> : suspend () -> Result<ReturnType>
interface ArgUseCase<Arg> : suspend (Arg) -> Unit
interface EmptyUseCase : suspend () -> Unit