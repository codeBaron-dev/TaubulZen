package org.netpos.tabulmobile.customer.data.remote.repository.otp_verification

import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.payload.OtpVerificationPayloadModel
import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.response.OtpVerificationResponseModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface OtpVerificationRepositoryInterface {

    suspend fun otpVerification(
        otpVerificationPayloadModel: OtpVerificationPayloadModel
    ): TabulResult<OtpVerificationResponseModel, ErrorDataTypes.Remote>
}