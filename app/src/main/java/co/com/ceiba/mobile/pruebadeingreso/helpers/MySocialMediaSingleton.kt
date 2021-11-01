package co.com.ceiba.mobile.pruebadeingreso.helpers

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

open class MySocialMediaSingleton {

    private var singleton: MySocialMediaSingleton? = null
    private var requestQueue: RequestQueue? = null

    constructor(context: Context) {
        this.requestQueue = getRequestQueue(context)
    }

    fun getInstance(context: Context): MySocialMediaSingleton? {
        if (singleton == null) {
            singleton = MySocialMediaSingleton(context)
        }
        return singleton
    }

    fun getRequestQueue(context: Context): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.applicationContext)

        }
        return requestQueue
    }

    fun addToRequestQueue( req: Request<String>, context: Context) {
        getRequestQueue(context)?.add(req)
    }

    fun consultaGet(url: String?, parametros: Map<String, String>?, stringListener: Response.Listener<String>, errorListener: Response.ErrorListener?): StringRequest? {
        return object : StringRequest(Method.GET, url, stringListener, errorListener) {
            override fun getParams(): Map<String, String>? = parametros
            override fun setRetryPolicy(retryPolicy: RetryPolicy?): Request<*> {
                var default = DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return super.setRetryPolicy(default)
            }
        }
    }

}