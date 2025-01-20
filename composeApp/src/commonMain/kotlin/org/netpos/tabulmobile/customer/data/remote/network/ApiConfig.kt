package org.netpos.tabulmobile.customer.data.remote.network

object ApiConfig {
    private const val BASE_URL = "https://consumer-rest-tabul.netpluspay.com/api/v1"
    const val LOGIN_ENDPOINT = "$BASE_URL/login"
    const val REGISTER_ENDPOINT = "$BASE_URL/register"
    const val FORGOT_PASSWORD_ENDPOINT = "$BASE_URL/password-reset/send-otp"
    const val OTP_VERIFICATION_ENDPOINT = "$BASE_URL/password-reset/verify-otp"
    const val CREATE_NEW_PASSWORD_ENDPOINT = "$BASE_URL/password-reset/reset"
    const val UPLOAD_USER_LOCATION_ENDPOINT = "$BASE_URL/locations"
    const val RETRIEVE_USER_SAVED_LOCATION_ENDPOINT = "$BASE_URL/locations?email="
    const val HOME_SCREEN_RESTAURANT_ENDPOINT = "$BASE_URL/restaurants/special-categories"
    const val RESTAURANT_DETAILS_ENDPOINT = "$BASE_URL/restaurants"
    const val RESTAURANT_MENU_ENDPOINT = "$BASE_URL/restaurants"
    const val RESTAURANT_OPENING_HOURS_ENDPOINT = "$BASE_URL/restaurants"
    const val RESTAURANT_MENU_ITEM_ENDPOINT = "$BASE_URL/restaurants"
}

object TabulConstants {
    const val SERVER_ERROR_MESSAGE = "Don't Fret, try again later"
    const val DUMMY_IMAGE = "https://www.peninsula.com/en/-/media/pch-offers/cny-brunch-1.jpg?mw=905&hash=954243BA55FF7E1B2B271E9256EA6900"
}