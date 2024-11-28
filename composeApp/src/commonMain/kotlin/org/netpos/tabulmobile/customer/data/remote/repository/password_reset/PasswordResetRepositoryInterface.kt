package org.netpos.tabulmobile.customer.data.remote.repository.password_reset

import org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload.PasswordResetPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface PasswordResetRepositoryInterface {

    suspend fun forgotPassword(passwordResetPayloadModel: PasswordResetPayloadModel): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote>
}