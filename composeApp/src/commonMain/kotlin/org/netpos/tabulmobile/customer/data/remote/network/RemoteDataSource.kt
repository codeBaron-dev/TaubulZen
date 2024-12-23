package org.netpos.tabulmobile.customer.data.remote.network

import org.netpos.tabulmobile.customer.data.models.create_new_password.payload.CreateNewPasswordPayloadModel
import org.netpos.tabulmobile.customer.data.models.home_screen.response.HomeSpecialRestaurantResponse
import org.netpos.tabulmobile.customer.data.models.location.payload.LocationPayloadModel
import org.netpos.tabulmobile.customer.data.models.location.response.LocationResponseModel
import org.netpos.tabulmobile.customer.data.models.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.customer.data.models.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.payload.OtpVerificationPayloadModel
import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.response.OtpVerificationResponseModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload.PasswordResetPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse
import org.netpos.tabulmobile.customer.data.models.register.remote.payload.RegistrationPayloadModel
import org.netpos.tabulmobile.customer.data.models.register.remote.response.RegistrationResponseModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface RemoteDataSource {

    suspend fun login(
        loginPayloadModel: LoginPayloadModel
    ): TabulResult<LoginResponseModel, ErrorDataTypes.Remote>

    suspend fun register(
        registrationPayloadModel: RegistrationPayloadModel
    ): TabulResult<RegistrationResponseModel, ErrorDataTypes.Remote>

    suspend fun forgotPassword(
        passwordResetPayloadModel: PasswordResetPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote>

    suspend fun otpVerification(
        otpVerificationPayloadModel: OtpVerificationPayloadModel
    ): TabulResult<OtpVerificationResponseModel, ErrorDataTypes.Remote>

    suspend fun createNewPassword(
        createNewPasswordPayloadModel: CreateNewPasswordPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote>

    suspend fun uploadUserLocation(
        locationPayloadModel: LocationPayloadModel
    ): TabulResult<LocationResponseModel, ErrorDataTypes.Remote>

    suspend fun homeScreenRestaurantsInfo():
            TabulResult<HomeSpecialRestaurantResponse, ErrorDataTypes.Remote>
}