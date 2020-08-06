package com.app.wiprodemo.data.network

import android.util.Log
import org.json.JSONException
import retrofit2.Response
import java.io.IOException

abstract class SafeRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            Log.d(SafeRequest::class.java.simpleName, "Response: "+response.body().toString())
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let{
                try{
                    message.append(error)
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw IOException(message.toString())
        }
    }

}