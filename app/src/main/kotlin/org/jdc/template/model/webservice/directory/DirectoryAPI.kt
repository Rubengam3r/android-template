package org.jdc.template.model.webservice.directory

import org.jdc.template.model.webservice.directory.data.PersonsDta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DirectoryAPI {

    @GET("/mobile/interview/v2/directory")
    suspend fun directoryAPI(): Response<PersonsDta>

    @GET
    suspend fun directoryByFullURL(@Url url: String): Response<PersonsDta>

    @Streaming
    @GET("/mobile/interview/v2/directory")
    suspend fun directoryToFile()



    companion object {
        const val BASE_URL = "https://ldscdn.org"
        private const val SUB_URL = "/mobile/interview/v2/directory"
        const val FULL_URL = BASE_URL + SUB_URL
    }
}