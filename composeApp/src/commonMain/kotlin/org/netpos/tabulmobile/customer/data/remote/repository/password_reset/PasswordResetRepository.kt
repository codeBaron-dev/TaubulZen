package org.netpos.tabulmobile.customer.data.remote.repository.password_reset

import org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload.PasswordResetPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class PasswordResetRepository(
    private val remoteDataSource: RemoteDataSource
): PasswordResetRepositoryInterface {
    override suspend fun forgotPassword(
        passwordResetPayloadModel: PasswordResetPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.forgotPassword(passwordResetPayloadModel = passwordResetPayloadModel).map { it }
    }
}