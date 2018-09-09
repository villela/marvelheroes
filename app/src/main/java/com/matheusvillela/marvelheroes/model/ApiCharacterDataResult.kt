package com.matheusvillela.marvelheroes.model

data class ApiCharacterDataResult(val id: Int,
                                  val name: String,
                                  val description: String,
                                  val thumbnail: ApiCharacterDataResultThumbnail)