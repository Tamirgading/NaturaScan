package com.farez.naturascan.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: Boolean
)
