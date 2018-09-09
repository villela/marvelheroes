package com.matheusvillela.marvelheroes.model

data class ApiCharacterData(val offset: Int,
                            val limit: Int,
                            val total: Int,
                            val count: Int,
                            val results: List<ApiCharacterDataResult>)