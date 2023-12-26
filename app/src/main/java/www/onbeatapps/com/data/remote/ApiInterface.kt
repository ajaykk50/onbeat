package www.onbeatapps.com.data.remote


import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*
import www.onbeatapps.com.data.ErrorBody
import www.onbeatapps.com.data.model.api.response.*
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
typealias GenericResponse<S> = NetworkResponse<S, ErrorBody>

interface ApiInterface {

    @GET("api/v3/event")
    suspend fun getEventID(): GenericResponse<EvenRESPONSE>

//    @FormUrlEncoded
    @POST("/api/v3/verify/{event_id}/sms")
    suspend fun setOtp(
    @Path("event_id")
    eventId: Int,
    @Body params: JsonObject
    ): GenericResponse<SendOtpRESPONSE>

//    @FormUrlEncoded
    @POST("/api/v3/verify/{event_id}/otp")
    suspend fun checkOtp(
        @Path("event_id")
        eventId: Int,
        @Body params: JsonObject
    ): GenericResponse<BaseResponse>

//    @FormUrlEncoded
    @POST("/api/v3/auth/events/{event_id}/signup")
    suspend fun getUser(
        @Path("event_id")
        eventId: Int,
        @Body params: JsonObject
    ): GenericResponse<RegisterRESPONSE>

    @POST("/api/v3/auth/{event_id}/{id}/login")
    suspend fun loginUser(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        Id: Int,
        @Body params: JsonObject
    ): GenericResponse<BaseResponse>

    @GET("/api/v3/auth/token")
    suspend fun auth(
        @Header("Authorization") data:String
    ): GenericResponse<AuthToken>

    @POST("/api/v3/events/{event_id}/customers/{id}/assign_gtag")
    suspend fun addBant(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        Id: Int,
        @Body params: JsonObject
    ): GenericResponse<AddBandRESPONSE>

    // Delete note
    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") noteId: Int): ListCardRESPONSE

    fun getTop(
        @Query("limit") limit: Int
    ): Observable<ListCardRESPONSE?>?

    @POST("/api/v3/payment/events/{event_id}/customers/{id}/auth")
    suspend fun addSpentLimit(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
        @Body params: JsonObject
    ): GenericResponse<AddSpentLimitRESPONSE>

    @GET("/api/v3/events/{event_id}/customers/{id}")
    suspend fun getUserDt(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
    ): GenericResponse<UserDetailsRESPONSE>

    @POST("/api/v3/events/{event_id}/customers/{id}/gtags/ban")
    suspend fun banBand(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
    ): GenericResponse<BaseResponse>

    @PATCH("/api/v3/events/{event_id}/customers/{id}/ticket/unlink")
    suspend fun unlinkTicket(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
    ): GenericResponse<BaseResponse>

    @POST("/api/v3/payment/events/{event_id}/customers/{id}")
    suspend fun cardSave(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
    ): GenericResponse<SaveCardRESPONSE>

    @PATCH("/api/v3/events/{event_id}/customers/{id}")
    suspend fun editProfile(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
        @Body params: JsonObject
    ): GenericResponse<BaseResponse>
    @PATCH("/api/v3/events/{event_id}/customers/{id}")
    suspend fun fireTokenUpdate(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
        @Header("Authorization") Authorization: String,
        @Body params: JsonObject
    ): GenericResponse<BaseResponse>

    @DELETE("/api/v3/payment/{payment_id}/events/{event_id}/customers/{id}")
    suspend fun cardDelete(
        @Path("payment_id")
        paymentID: String,
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
//        @Body params: JsonObject
    ): GenericResponse<BaseResponse>

    @GET("/api/v3/{event_id}/customers/{id}/pdf")
    suspend fun DownloadPDF(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int
//        @Body params: JsonObject
    ): GenericResponse<DownloadPDFRESPONSE>

    @GET("/api/v3/events/{event_id}")
    suspend fun eventInfo(
        @Path("event_id")
        eventId: Int
    ): GenericResponse<EventInfoRESPONSE>

    @POST("/api/v3/payment/events/{event_id}/customers/{id}/settle")
    suspend fun settle(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
        @Body params: JsonObject
    ): GenericResponse<BaseResponse>

    @POST("/api/v3/payment/events/{event_id}/customers/{id}/list")
    suspend fun listCard(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
    ): GenericResponse<ListCardRESPONSE>

    @GET("/api/v3/events/{event_id}/customers/{id}/pokes")
    suspend fun transationList(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
    ): GenericResponse<TransationListRESPONSE>

    @GET("/api/v3/events/{event_id}/customers/{id}/pokes/pdf")
    suspend fun transationListDpwnload(
        @Path("event_id")
        eventId: Int,
        @Path("id")
        id: Int,
    ): GenericResponse<pdfRESPONS>

    @POST("/api/v3/auth/events/{event_id}/forgot/{uuid}")
    suspend fun forgotPassword(
        @Path("event_id")
        eventId: Int,
        @Path("uuid")
        uuId: String,
        @Body params: JsonObject
    ): GenericResponse<ForgotPassRESPONSE>


    @POST("/api/v3/auth/events/{event_id}/ticket/search")
    suspend fun tickectScan(
        @Path("event_id")
        eventId: Int,
        @Body params: JsonObject
    ): GenericResponse<scanTicketRESPONSE>

    @GET("/api/v3/lineup")
    suspend fun getLineUp(
    ): GenericResponse<LineUpRESPONSE>

    @GET("/api/v3/{code}/artist_details")
    suspend fun getArtistDetails(
        @Path("code")
        cose: String,
    ): GenericResponse<ArtistDetailsRESPONSE>

    @GET("/api/v3/artist_list")
    suspend fun getArtist(
    ): GenericResponse<ArtistListRESPONSE>

//    @GET("/api/v3/{event_id}/lineup")
//    suspend fun getLineUp(
//        @Path("event_id")
//        eventId: Int
//    ): GenericResponse<LineUpRESPONSE>
//
//    @GET("/api/v3/{event_id}/artist_details/{code}")
//    suspend fun getArtistDetails(
//        @Path("event_id")
//        eventId: Int,
//        @Path("code")
//        cose: String
//    ): GenericResponse<ArtistDetailsRESPONSE>
//
//    @GET("/api/v3/{event_id}/artist_list")
//    suspend fun getArtist(
//        @Path("event_id")
//        eventId: Int
//    ): GenericResponse<ArtistListRESPONSE>

}