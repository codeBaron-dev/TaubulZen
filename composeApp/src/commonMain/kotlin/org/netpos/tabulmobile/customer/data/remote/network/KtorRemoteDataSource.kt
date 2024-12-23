package org.netpos.tabulmobile.customer.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
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
import org.netpos.tabulmobile.customer.data.remote.network.ApiConfig.FORGOT_PASSWORD_ENDPOINT
import org.netpos.tabulmobile.customer.data.remote.network.ApiConfig.OTP_VERIFICATION_ENDPOINT
import org.netpos.tabulmobile.customer.data.remote.network.ApiConfig.REGISTER_ENDPOINT
import org.netpos.tabulmobile.customer.data.remote.safeCall
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun login(
        loginPayloadModel: LoginPayloadModel
    ): TabulResult<LoginResponseModel, ErrorDataTypes.Remote> {
        return safeCall<LoginResponseModel> {
            httpClient.post {
                url(urlString = ApiConfig.LOGIN_ENDPOINT)
                setBody(loginPayloadModel)
            }
        }
    }

    override suspend fun register(
        registrationPayloadModel: RegistrationPayloadModel
    ): TabulResult<RegistrationResponseModel, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.post {
                url(urlString = REGISTER_ENDPOINT)
                setBody(registrationPayloadModel)
            }
        }
    }

    override suspend fun forgotPassword(
        passwordResetPayloadModel: PasswordResetPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.post {
                url(urlString = FORGOT_PASSWORD_ENDPOINT)
                setBody(body = passwordResetPayloadModel)
            }
        }
    }

    override suspend fun otpVerification(
        otpVerificationPayloadModel: OtpVerificationPayloadModel
    ): TabulResult<OtpVerificationResponseModel, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.post {
                url(urlString = OTP_VERIFICATION_ENDPOINT)
                setBody(body = otpVerificationPayloadModel)
            }
        }
    }

    override suspend fun createNewPassword(
        createNewPasswordPayloadModel: CreateNewPasswordPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.post {
                url(urlString = ApiConfig.CREATE_NEW_PASSWORD_ENDPOINT)
                setBody(body = createNewPasswordPayloadModel)
            }
        }
    }

    override suspend fun uploadUserLocation(
        locationPayloadModel: LocationPayloadModel
    ): TabulResult<LocationResponseModel, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.post {
                url(urlString = ApiConfig.UPLOAD_USER_LOCATION_ENDPOINT)
                setBody(body = locationPayloadModel)
            }
        }
    }

    override suspend fun homeScreenRestaurantsInfo():
            TabulResult<HomeSpecialRestaurantResponse, ErrorDataTypes.Remote> {
        return safeCall {
            httpClient.get {
                url(urlString = ApiConfig.HOME_SCREEN_RESTAURANT_ENDPOINT)
            }
        }
    }
}