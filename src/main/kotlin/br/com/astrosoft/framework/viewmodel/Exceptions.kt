package br.com.astrosoft.framework.viewmodel

abstract class EViewModel(msg: String): Exception(msg)

abstract class EViewModelError(msg: String): EViewModel(msg)

abstract class EViewModelWarning(msg: String): EViewModel(msg)