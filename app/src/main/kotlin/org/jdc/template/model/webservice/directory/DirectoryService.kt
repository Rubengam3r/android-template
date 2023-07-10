package org.jdc.template.model.webservice.directory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jdc.template.model.webservice.directory.data.PersonsDta
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.Exception

class DirectoryService {

    fun getDirectory()
    {
        val api = Retrofit.Builder()
                .baseUrl(DirectoryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DirectoryAPI::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.directoryAPI()
                if (response.isSuccessful)
                {
                    processWebServiceResponse(response)
                }
                else {
                    Timber.e("API Call FAILED [${response.errorBody()}]")
                }
            } catch (e: Exception) {
                Timber.tag("Main").e("Error: %s", e.message)
            }
        }
    }

    fun processWebServiceResponse(response: Response<PersonsDta>)
    {
        if (response.isSuccessful) {
            Timber.tag("Main").d("response: %s", response.isSuccessful)
            response.body()?.let {
                processData(it)
            }
        }
    }

    fun processData(personsDta: PersonsDta)
    {
        for (dtaResult in personsDta.individuals) {
            Timber.i("Results: %s", dtaResult)
        }
    }

}