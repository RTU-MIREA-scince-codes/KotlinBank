package com.rodyapal.utils

private val chars = (('a'..'z') + ('A'..'Z') + ('0'..'9'))
fun randomString(size: Int) = buildList<Char>(size) {
	chars.random()
}.joinToString()