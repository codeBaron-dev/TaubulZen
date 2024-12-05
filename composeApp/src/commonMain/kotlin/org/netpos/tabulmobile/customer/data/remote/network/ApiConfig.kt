package org.netpos.tabulmobile.customer.data.remote.network

object ApiConfig {
    private const val BASE_URL = "https://consumer-rest-tabul.netpluspay.com/api/v1"
    const val LOGIN_ENDPOINT = "$BASE_URL/login"
    const val REGISTER_ENDPOINT = "$BASE_URL/register"
    const val FORGOT_PASSWORD_ENDPOINT = "$BASE_URL/password-reset/send-otp"
    const val OTP_VERIFICATION_ENDPOINT = "$BASE_URL/password-reset/verify-otp"
    const val CREATE_NEW_PASSWORD_ENDPOINT = "$BASE_URL/password-reset/reset"
    const val UPLOAD_USER_LOCATION_ENDPOINT = "$BASE_URL/locations"
}

object TabulConstants {
    const val SERVER_ERROR_MESSAGE = "Don't Fret, try again later"
}